/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.com.pwcu.nanopad2controller.job;

import com.google.common.eventbus.EventBus;

/**
 *
 * @author kevin
 */
public class WriteSceneDataToNanoPad2MemorySysexJob extends SysexJob {

    public WriteSceneDataToNanoPad2MemorySysexJob(EventBus eventBus, int sceneNumber) {
        super(sceneNumber, eventBus);
    }

    @Override
    public byte[] populateSysexMessage() {
        byte[] message = new byte[11];
        message[0] = (byte)0xf0;
        message[1] = (byte)0x42;
        message[2] = (byte)0x40;
        message[3] = (byte)0x00;
        message[4] = (byte)0x01;
        message[5] = (byte)0x12;
        message[6] = (byte)0x00;
        message[7] = (byte)0x1f;
        message[8] = (byte)0x11;
        message[9] = (byte)getSceneNumber();
        message[10] = (byte)0xf7;
        
        return message;
    }
    
}
