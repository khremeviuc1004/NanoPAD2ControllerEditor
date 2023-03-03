/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.com.pwcu.nanopad2controller.midisystem;

import au.com.pwcu.nanopad2controller.domain.NanoPad2;
import au.com.pwcu.nanopad2controller.domain.Scene;
import au.com.pwcu.nanopad2controller.events.RequestSceneDataEvent;
import au.com.pwcu.nanopad2controller.events.RequestSceneSetEvent;
import au.com.pwcu.nanopad2controller.events.SceneChangedEvent;
import au.com.pwcu.nanopad2controller.events.SendGlobalDataToNanoPad2Event;
import au.com.pwcu.nanopad2controller.events.SendSceneDataToNanoPad2Event;
import au.com.pwcu.nanopad2controller.events.SendSceneSetEvent;
import au.com.pwcu.nanopad2controller.job.*;
import au.com.pwcu.nanopad2controller.midisystem.actions.GlobalDataDumpResponseAction;
import au.com.pwcu.nanopad2controller.midisystem.actions.NullSysexResponseAction;
import au.com.pwcu.nanopad2controller.midisystem.actions.SceneDumpSysexResponseAction;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author kevin
 */
public class NanoPad2MidiSystem extends Thread {

    private static final String NANO_PAD2 = "nanoPAD2";

    private final NanoPad2 nanoPad2;
    
    private ConcurrentLinkedQueue<SysexJob> jobs = new ConcurrentLinkedQueue<>();
    
    private boolean sendSysexMessagesKeepAlive = true;
    
    private final EventBus eventBus;

    public NanoPad2MidiSystem(EventBus eventBus, NanoPad2 nanoPad2) {
        super("NanoPad2MidiSystem sysex job processer");
        
        this.eventBus = eventBus;
        this.nanoPad2 = nanoPad2;
        
        this.eventBus.register(this);
        
        this.register();

        try {
            for(int sceneNumber = 0; sceneNumber < 4; sceneNumber++)
            {
                changeToScene(sceneNumber);
                requestSceneData(sceneNumber);

            }
            changeToScene(0);
            requestGlobalData();
        } catch (Exception ex) {
            Logger.getLogger(NanoPad2MidiSystem.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.start();
    }
    
    public native void register();
    
    public void receive(byte[] message) {
        dumpSysex(message);
        SysexJob job = getCurrentJob();
        if(job.getStatus() != SysexJob.Status.NEW 
                && job.getStatus() != SysexJob.Status.DONE 
                && job.getStatus() != SysexJob.Status.SYSEX_ERROR )
        {
            job.processSysexResponse(message);
            if( job.getStatus() == SysexJob.Status.DONE || job.getStatus() == SysexJob.Status.SYSEX_ERROR )
            {
                Logger.getLogger(NanoPad2MidiSystem.class.getName()).log(Level.INFO, "Sysex job complete - removing.");
                removeCurrentJob();
            }
        }
    }

    public void dumpSysex(byte[] message) {
        StringBuilder builder = new StringBuilder();
        for (byte data : message) {
            builder.append(Integer.toHexString((int)data));
            builder.append(" ");
        }
        Logger.getLogger(NanoPad2MidiSystem.class.getName()).log(Level.INFO, builder.toString());
    }
    
    public native void sendSystex(byte[] data);

    @Override
    public void run() {
        while(sendSysexMessagesKeepAlive) {
            SysexJob sysexJob = getCurrentJob();
            if(sysexJob != null && sysexJob.getStatus() == SysexJob.Status.NEW)
            {
                Logger.getLogger(NanoPad2MidiSystem.class.getName()).log(Level.INFO, "Found sysex messages to send.");
                
                byte[] data = sysexJob.populateSysexMessage();
                dumpSysex(data);
                try {
                    sendSystex(data);
                    sysexJob.setStatus(SysexJob.Status.SENT);
                } catch (Exception ex) {
                    Logger.getLogger(NanoPad2MidiSystem.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            try {
                Thread.sleep(1000l);
            } catch (InterruptedException ex) {
                Logger.getLogger(NanoPad2MidiSystem.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    SysexJob getCurrentJob() {
        return jobs.peek();
    }

    void removeCurrentJob() {
        jobs.poll();
    }

    NanoPad2 getNanoPad2() {
        return nanoPad2;
    }

    void changeToScene(int sceneNumber)
    {
        SysexJob job = new ChangeToSceneSysexJob(eventBus);
        job.setSceneNumber(sceneNumber);
        job.setOperation(SysexJob.Operation.SCENE_CHANGE_REQUEST);
        NullSysexResponseAction nullResponseAction1 = new NullSysexResponseAction();
        job.addResponseAction(SysexJob.ResponseType.SCENE_CHANGE, nullResponseAction1);
        NullSysexResponseAction nullResponseAction2 = new NullSysexResponseAction();
        job.addResponseAction(SysexJob.ResponseType.ACK, nullResponseAction2);
        NullSysexResponseAction nullResponseAction3 = new NullSysexResponseAction();
        job.addErrorResponseAction(SysexJob.ResponseType.NAK, nullResponseAction3);
        jobs.add(job);
    }

    void requestSceneData(int sceneNumber)
    {
        SysexJob job = new SceneDumpRequestSysexJob(eventBus);
        job.setSceneNumber(sceneNumber);
        job.setOperation(SysexJob.Operation.CURRENT_SCENE_DUMP_REQUEST);
        SceneDumpSysexResponseAction sceneDumpSysexResponseAction = new SceneDumpSysexResponseAction(nanoPad2.getScene(sceneNumber));
        job.addResponseAction(SysexJob.ResponseType.CURRENT_SCENE_DATA_DUMP, sceneDumpSysexResponseAction);
        NullSysexResponseAction nullResponseAction = new NullSysexResponseAction();
        job.addErrorResponseAction(SysexJob.ResponseType.NAK, nullResponseAction);
        jobs.add(job);
    }
    
    void requestGlobalData() {
        SysexJob job = new GlobalDataRequestSysexJob(eventBus);
        job.setOperation(SysexJob.Operation.GLOBAL_DATA_REQUEST);
        GlobalDataDumpResponseAction globalDataDumpSysexResponseAction = new GlobalDataDumpResponseAction(nanoPad2.getGlobalParameters());
        job.addResponseAction(SysexJob.ResponseType.GLOBAL_DATA_DUMP, globalDataDumpSysexResponseAction);
        NullSysexResponseAction nullResponseAction = new NullSysexResponseAction();
        job.addErrorResponseAction(SysexJob.ResponseType.NAK, nullResponseAction);
        jobs.add(job);
    }

    public boolean isSendSysexMessagesKeepAlive() {
        return sendSysexMessagesKeepAlive;
    }

    public void setSendSysexMessagesKeepAlive(boolean sendSysexMessagesKeepAlive) {
        this.sendSysexMessagesKeepAlive = sendSysexMessagesKeepAlive;
    }
    
    @Subscribe
    public void handleSceneChangeFromUI(SceneChangedEvent sceneChangedEvent) {
        changeToScene(sceneChangedEvent.getSceneNumber() - 1);
    }
    
    @Subscribe
    public void handleSendSceneDataToNanoPad2(SendSceneDataToNanoPad2Event sendSceneDataToNanoPad2Event) {
        final int sceneNumber = sendSceneDataToNanoPad2Event.getSceneNumber();
        int sceneIndex = sceneNumber - 1;
        
        sendSceneDataToNanoPad2(sceneIndex);
    }

    private void sendSceneDataToNanoPad2(int sceneIndex) {
        Scene scene = nanoPad2.getScene(sceneIndex);
        SysexJob job = new SendSceneDataToNanoPad2SysexJob(eventBus, scene);
        job.setSceneNumber(sceneIndex);
        job.setOperation(SysexJob.Operation.SEND_SCENE_DUMP);
        NullSysexResponseAction nullResponseAction1 = new NullSysexResponseAction();
        job.addResponseAction(SysexJob.ResponseType.ACK, nullResponseAction1);
        NullSysexResponseAction nullResponseAction2 = new NullSysexResponseAction();
        job.addErrorResponseAction(SysexJob.ResponseType.NAK, nullResponseAction2);
        jobs.add(job);
        
        SysexJob writeJob = new WriteSceneDataToNanoPad2MemorySysexJob(eventBus, sceneIndex);
        writeJob.setOperation(SysexJob.Operation.WRITE_SCENE);
        NullSysexResponseAction nullResponseAction4 = new NullSysexResponseAction();
        writeJob.addResponseAction(SysexJob.ResponseType.WRITE_COMPLETE, nullResponseAction4);
        NullSysexResponseAction nullResponseAction5 = new NullSysexResponseAction();
        writeJob.addErrorResponseAction(SysexJob.ResponseType.WRITE_ERROR, nullResponseAction5);
        jobs.add(writeJob);
    }
    
    @Subscribe
    public void handleSendGlobalDataToNanoPad2(SendGlobalDataToNanoPad2Event event) {
        sendGlobalDataToNanoPad2();
    }

    private void sendGlobalDataToNanoPad2() {
        SysexJob job = new GlobalDataSendToNanoPad2SysexJob(eventBus, nanoPad2.getGlobalParameters());
        job.setOperation(SysexJob.Operation.GLOBAL_DATA_SEND);
        NullSysexResponseAction nullResponseAction1 = new NullSysexResponseAction();
        job.addResponseAction(SysexJob.ResponseType.ACK, nullResponseAction1);
        NullSysexResponseAction nullResponseAction2 = new NullSysexResponseAction();
        job.addErrorResponseAction(SysexJob.ResponseType.NAK, nullResponseAction2);
        jobs.add(job);
    }
    
    @Subscribe
    public void handleSendSceneSetToNanoPad2(SendSceneSetEvent event) {
        sendSceneDataToNanoPad2(0);
        sendSceneDataToNanoPad2(1);
        sendSceneDataToNanoPad2(2);
        sendSceneDataToNanoPad2(3);
        sendGlobalDataToNanoPad2();
    }
    
    @Subscribe
    public void handleRequestSceneSetFromNanoPad2(RequestSceneSetEvent event) {
        requestSceneData(0);
        requestSceneData(1);
        requestSceneData(2);
        requestSceneData(3);
        requestGlobalData();
    }
    
    @Subscribe
    public void handleRequestSceneDataFromNanoPad2(RequestSceneDataEvent event) {
        requestSceneData(nanoPad2.getCurrentScene());
    }
}
