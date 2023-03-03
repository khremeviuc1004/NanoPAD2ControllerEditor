#include "midisystem.h"
#include <string>
#include <algorithm>
#include <unistd.h>
#include <math.h>


/*
 * Find Case Insensitive Sub String in a given substring
 */
size_t findCaseInsensitive(string data, string toSearch, size_t pos = 0)
{
    // Convert complete given String to lower case
    transform(data.begin(), data.end(), data.begin(), ::tolower);
    // Convert complete given Sub String to lower case
    transform(toSearch.begin(), toSearch.end(), toSearch.begin(), ::tolower);
    // Find sub string in given string
    return data.find(toSearch, pos);
}

MidiSystem::MidiSystem(): midiLoopThread(nullptr)
{
    // Try open a new client...
    cout << "****************** Try open a new client... *****************" << endl;
    sequencerHandle = nullptr;
    if ( snd_seq_open( &sequencerHandle, "default", SND_SEQ_OPEN_DUPLEX, 0 ) < 0 )
    {
        cerr << "Could not access ALSA. Please ensure ALSA is up and running." << endl;
        return;
    }

    if ( sequencerHandle == nullptr )
    {
        cerr << "Could not create MIDI output port. Please check your ALSA installation." << endl;
        return;
    }

    // Set client name.
    snd_seq_set_client_name( sequencerHandle, "NanoPad Control" );

    // create input/output port
    cout << "****************** create input/output port *****************" << endl;
    midiInOutPortNumber = snd_seq_create_simple_port( sequencerHandle, "midi",
                SND_SEQ_PORT_CAP_WRITE|SND_SEQ_PORT_CAP_SUBS_WRITE|SND_SEQ_PORT_CAP_READ|SND_SEQ_PORT_CAP_SUBS_READ,
                SND_SEQ_PORT_TYPE_APPLICATION );
    if ( midiInOutPortNumber < 0 )
    {
        cerr << "Could not create MIDI input/output port. Please check your ALSA installation." << endl;
        return;
    }

    string searchTerm = "nanoPAD2";
    cout << "****************** Connect midi out to NanoPad *****************" << endl;
    snd_seq_client_info_t *clientInfo;
    snd_seq_client_info_alloca(&clientInfo);
    snd_seq_client_info_set_client(clientInfo, -1);
    while (snd_seq_query_next_client(sequencerHandle, clientInfo) >= 0) {
        int destinationClient = snd_seq_client_info_get_client(clientInfo);
        string midiOutName = snd_seq_client_info_get_name(clientInfo);
        if (midiOutName.find(searchTerm) != string::npos)
        {
            cout << "****************** Connect midi out to NanoPad - match found - " << snd_seq_client_info_get_name(clientInfo) << " **********" << endl;
            snd_seq_connect_to(sequencerHandle, midiInOutPortNumber, destinationClient, 0);
            break;
        }
    }

    cout << "****************** Connect NanoPad to midi in *****************" << endl;
    snd_seq_client_info_set_client(clientInfo, -1);
    while (snd_seq_query_next_client(sequencerHandle, clientInfo) >= 0) {
        int sourceClient = snd_seq_client_info_get_client(clientInfo);
        string midiInName = snd_seq_client_info_get_name(clientInfo);
        if (midiInName.find(searchTerm) != string::npos)
        {
            cout << "****************** Connect midi in to NanoPad - match found - " << snd_seq_client_info_get_name(clientInfo) << " **********" << endl;
            snd_seq_connect_from(sequencerHandle, midiInOutPortNumber, sourceClient, 0);
            break;
        }
    }

    cout << "****************** Start the midi loop *****************" << endl;
    midiLoopThread = new thread(&MidiSystem::midiLoop, this);
}

void MidiSystem::eventSent(int, int)
{

}

void MidiSystem::errorMessage(char*, char*)
{

}

void MidiSystem::chunkArrived()
{

}

void MidiSystem::eventArrived(vector<unsigned char*>* eventBuffer)
{
    cout << "**************************** eventArrived ***********" << endl;
    JNIEnv * env;
    int getEnvStat = javaVirtualMachine->GetEnv((void **)&env, JNI_VERSION_10);
    if (getEnvStat == JNI_EDETACHED)
    {
        cout << "GetEnv: not attached" << endl;
        if (javaVirtualMachine->AttachCurrentThread((void **) &env, NULL) != 0)
        {
            cout << "Failed to attach" << endl;
        }
    }
    else if (getEnvStat == JNI_OK)
    {
    }
    else if (getEnvStat == JNI_EVERSION)
    {
            cout << "GetEnv: version not supported" << endl;
    }

    size_t nBytes = eventBuffer->size();
    jbyteArray val = env->NewByteArray(static_cast<jsize>(nBytes));
    for ( size_t i = 0; i < nBytes; i++ )
    {
        cout << hex << static_cast<int>(*(eventBuffer->at(i))) << " ";
        jbyte data[1];
        data[0] = static_cast<jbyte>(*(eventBuffer->at(i)));
        env->SetByteArrayRegion(val, static_cast<jsize>(i), 1, data);
    }
    env->CallVoidMethod(globalObjectRef, globalMethodID, val);

    if (env->ExceptionCheck()) {
            env->ExceptionDescribe();
    }

    javaVirtualMachine->DetachCurrentThread();
}

void MidiSystem::midiLoop()
{
    cout << "Starting midi loop" << endl;
    while(true)
    {
        usleep( 512 );
        processInput();
        sendEvents();
    }
    cout << "Finished midi loop" << endl;
}

void MidiSystem::processInput()
{
    while ( snd_seq_event_input_pending( sequencerHandle, 1 ) > 0 )
    {
        snd_seq_event_input( sequencerHandle, &sequencerEvent );
        if ( sequencerEvent->type != SND_SEQ_EVENT_SYSEX )
        {
            snd_seq_free_event( sequencerEvent );
            continue;
        }
        // Stolen from aseqmm/alsaevent.cpp by Pedro Lopez-Cabanillas
        unsigned char* data = static_cast<unsigned char *>(sequencerEvent->data.ext.ptr);
        int numberOfBytes = static_cast<int>(sequencerEvent->data.ext.len);
        for(int index = 0; index < numberOfBytes; index++)
        {
            this->tempBuffer.push_back(data[index]);
        }

        // ALSA splits sysex messages into chunks, currently of 256 max bytes in size
        // Therefore the data needs collection before sending it to the master thread
        if ( tempBuffer.front() == static_cast<unsigned char>(0xF0) && tempBuffer.back() == static_cast<unsigned char>(0xF7) )
        {
            vector<unsigned char*>* sysexMessage = new vector<unsigned char*>();
            for(size_t index = 0; index < tempBuffer.size(); index++)
            {
                unsigned char* data = new unsigned char;
                *data = tempBuffer.at(index);
                sysexMessage->push_back(data);
            }
            eventArrived( sysexMessage );
            tempBuffer.clear();
        }
        else
        {
            chunkArrived();
        }
        snd_seq_free_event( sequencerEvent );
    }
}

void MidiSystem::determineChunkAmount()
{
    chunkAmount = 0;
    for ( size_t i = 0; i < sysexMessageQueue.size(); i++ )
    {
        chunkAmount += ceil(static_cast<double>(sysexMessageQueue.at(i)->size()) / static_cast<double>(chunkMaxSize));
    }
}

void MidiSystem::sendEvents()
{
    chunkCount = 0;
    determineChunkAmount();

    for ( size_t i = 0; i < sysexMessageQueue.size(); i++ )
    {
        eventCount = i + 1;
        send( sysexMessageQueue.front() );
        sysexMessageQueue.pop_front();

        unsigned int delay = 250000;
        usleep( delay );
    }
    eventCount = 0;
    usleep(1000);
}

void MidiSystem::send( vector<unsigned char*>*& sysexMessageBytes )
{
    snd_seq_event_t event;
    snd_seq_ev_clear(&event);
    snd_seq_ev_set_source(&event, midiInOutPortNumber);
    snd_seq_ev_set_subs(&event);
    snd_seq_ev_set_direct(&event);

    // Also see
    // http://www.mail-archive.com/mixxx-devel@lists.sourceforge.net/msg01503.html
    // http://doxygen.scummvm.org/de/d2a/alsa_8cpp-source.html
    // In case of SysEx data, ALSA takes a data pointer (even an array) and the data size.
    // ALSA's buffer is limited to 16356 bytes anyway.

    vector<unsigned char> chunk;
    for ( unsigned int i = 0; i < sysexMessageBytes->size(); i++ )
    {
        unsigned char currentByte = *(sysexMessageBytes->at(i));
        chunk.push_back( currentByte );
        if ( chunk.size() == chunkMaxSize || chunk.back() == 0xF7 )
        {
            chunkCount ++;
            snd_seq_ev_set_sysex(&event, chunk.size(), &chunk.front());
            if( snd_seq_event_output_direct(sequencerHandle, &event) < 0 )
            {
                return;
            }
            // The baud rate of MIDI is 31250 bits/s = 32 microseconds per bit.
            // Per byte, an additional start and one or two stop bits are used.
            // The following delay should avoid data loss in case more data gets sent as fits into the ALSA buffers.
            usleep( static_cast<useconds_t>(chunk.size() * 352) );
            chunk.clear();
        }
    }
}
