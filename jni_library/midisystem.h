#ifndef MIDISYSTEM_H
#define MIDISYSTEM_H

#include <alsa/asoundlib.h>
#include <queue>
#include <deque>
#include <thread>
#include <iostream>


#include "au_com_pwcu_nanopad2controller_midisystem_NanoPad2MidiSystem.h"

using namespace std;

JavaVM * javaVirtualMachine;
jobject globalObjectRef;
jmethodID globalMethodID;

class MidiSystem
{
public:
    static MidiSystem& getInstance()
    {
        static MidiSystem INSTANCE;
        return INSTANCE;
    }

    virtual ~MidiSystem()
    {}

    deque<vector<unsigned char*>*> sysexMessageQueue;

    virtual void eventSent( int, int );
    virtual void errorMessage( char*, char* );
    virtual void chunkArrived();
    virtual void eventArrived( std::vector<unsigned char*>* eventBuffer );


private:
    MidiSystem();

    const int chunkMaxSize = 256;

    snd_seq_t * sequencerHandle;
    int midiInOutPortNumber;
    snd_seq_event_t* sequencerEvent;
    std::vector<unsigned char> tempBuffer;

    unsigned int chunkAmount;
    size_t eventCount;
    void determineChunkAmount();
    unsigned int chunkCount;

    std::thread* midiLoopThread;

    bool sendSysexMessagesKeepAlive = true;
    void processInput();
    void sendEvents();
    void send( vector<unsigned char*>*& sysexMessageBytes );

    // wxThread interface
protected:
    void midiLoop();
};

MidiSystem& midiSystem = MidiSystem::getInstance();

JNIEXPORT void JNICALL Java_au_com_pwcu_nanopad2controller_midisystem_NanoPad2MidiSystem_register(JNIEnv *env, jobject obj)
{
    globalObjectRef = env->NewGlobalRef(obj);

    jclass globalObjectClass = env->GetObjectClass(globalObjectRef);
    if (globalObjectClass == nullptr)
    {
        cerr << "Failed to find class" << endl;
    }

    globalMethodID = env->GetMethodID(globalObjectClass, "receive", "([B)V");
    if (globalMethodID == nullptr)
    {
        cerr << "Unable to get method ref" << endl;
    }

    jsize vmCount;
    if (JNI_GetCreatedJavaVMs(&javaVirtualMachine, 1, &vmCount) != JNI_OK || vmCount == 0) {
        cerr << "Could not get active VM" << endl;
    }
}

JNIEXPORT void JNICALL Java_au_com_pwcu_nanopad2controller_midisystem_NanoPad2MidiSystem_sendSystex(JNIEnv *env, jobject jobj, jbyteArray data)
{
    cout << "****************** Sending midi bytes via the JNI layer *****************" << endl;
    vector<unsigned char*>* sysexMessageBytes = new vector<unsigned char*>();
    jsize count = env->GetArrayLength(data);
    jbyte *body = env->GetByteArrayElements(data, 0);
    for(jsize index = 0; index < count; index++)
    {
        unsigned char* sysexMessageByte = new unsigned char[1];
        *sysexMessageByte = static_cast<unsigned char>(body[index]);
        sysexMessageBytes->push_back(sysexMessageByte);
    }
    midiSystem.sysexMessageQueue.push_back(sysexMessageBytes);
}

#endif // MIDISYSTEM_H
