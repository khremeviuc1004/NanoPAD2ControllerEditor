/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.com.pwcu.nanopad2controller.job;

import au.com.pwcu.nanopad2controller.domain.Scene;
import au.com.pwcu.nanopad2controller.domain.TriggerPad;
import au.com.pwcu.nanopad2controller.midisystem.actions.SceneDumpSysexResponseAction;
import com.google.common.eventbus.EventBus;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author kevin
 */
public class SendSceneDataToNanoPad2SysexJob extends SysexJob {
    
    private Scene scene;

    public SendSceneDataToNanoPad2SysexJob(EventBus eventBus, Scene scene) {
        super(eventBus);
        this.scene = scene;
    }

    @Override
    public byte[] populateSysexMessage() {
        byte[] message = new byte[122];
        
        message[0] = (byte)0xf0;
        message[1] = (byte)0x42;
        message[2] = (byte)0x40;
        message[3] = (byte)0x00;
        message[4] = (byte)0x01;
        message[5] = (byte)0x12;
        message[6] = (byte)0x00;
        message[7] = (byte)0x7f;
        message[8] = (byte)0x70;
        message[9] = (byte)0x40;

        
        int triggerPadIndex = 0;
        byte[] triggerPadData = new byte[6];
        
        //repeated filler bytes 1c 0e 47 63 71 38

        createTriggerPadSceneDumpData(triggerPadIndex++, triggerPadData);
        int data = 10;
        message[data] = (byte)0x1c;
        data++;
        message[data] = triggerPadData[0];
        data++;
        message[data] = triggerPadData[1];
        data++;
        message[data] = triggerPadData[2];
        data++;
        message[data] = triggerPadData[3];
        data++;
        message[data] = triggerPadData[4];
        data++;
        message[data] = triggerPadData[5];

        createTriggerPadSceneDumpData(triggerPadIndex++, triggerPadData);
        data++;
        message[data] = triggerPadData[0];
        data++;
        message[data] = (byte)0x0e;
        data++;
        message[data] = triggerPadData[1];
        data++;
        message[data] = triggerPadData[2];
        data++;
        message[data] = triggerPadData[3];
        data++;
        message[data] = triggerPadData[4];
        data++;
        message[data] = triggerPadData[5];

        createTriggerPadSceneDumpData(triggerPadIndex++, triggerPadData);
        data++;
        message[data] = triggerPadData[0];
        data++;
        message[data] = triggerPadData[1];
        data++;
        message[data] = (byte)0x47;
        data++;
        message[data] = triggerPadData[2];
        data++;
        message[data] = triggerPadData[3];
        data++;
        message[data] = triggerPadData[4];
        data++;
        message[data] = triggerPadData[5];

        createTriggerPadSceneDumpData(triggerPadIndex++, triggerPadData);
        data++;
        message[data] = triggerPadData[0];
        data++;
        message[data] = triggerPadData[1];
        data++;
        message[data] = triggerPadData[2];
        data++;
        message[data] = (byte)0x63;
        data++;
        message[data] = triggerPadData[3];
        data++;
        message[data] = triggerPadData[4];
        data++;
        message[data] = triggerPadData[5];

        createTriggerPadSceneDumpData(triggerPadIndex++, triggerPadData);
        data++;
        message[data] = triggerPadData[0];
        data++;
        message[data] = triggerPadData[1];
        data++;
        message[data] = triggerPadData[2];
        data++;
        message[data] = triggerPadData[3];
        data++;
        message[data] = (byte)0x71;
        data++;
        message[data] = triggerPadData[4];
        data++;
        message[data] = triggerPadData[5];

        createTriggerPadSceneDumpData(triggerPadIndex++, triggerPadData);
        data++;
        message[data] = triggerPadData[0];
        data++;
        message[data] = triggerPadData[1];
        data++;
        message[data] = triggerPadData[2];
        data++;
        message[data] = triggerPadData[3];
        data++;
        message[data] = triggerPadData[4];
        data++;
        message[data] = (byte)0x38;
        data++;
        message[data] = triggerPadData[5];

        createTriggerPadSceneDumpData(triggerPadIndex++, triggerPadData);
        data++;
        message[data] = triggerPadData[0];
        data++;
        message[data] = triggerPadData[1];
        data++;
        message[data] = triggerPadData[2];
        data++;
        message[data] = triggerPadData[3];
        data++;
        message[data] = triggerPadData[4];
        data++;
        message[data] = triggerPadData[5];

        createTriggerPadSceneDumpData(triggerPadIndex++, triggerPadData);
        data++;
        message[data] = (byte)0x1c;
        data++;
        message[data] = triggerPadData[0];
        data++;
        message[data] = triggerPadData[1];
        data++;
        message[data] = triggerPadData[2];
        data++;
        message[data] = triggerPadData[3];
        data++;
        message[data] = triggerPadData[4];
        data++;
        message[data] = triggerPadData[5];

        createTriggerPadSceneDumpData(triggerPadIndex++, triggerPadData);
        data++;
        message[data] = triggerPadData[0];
        data++;
        message[data] = (byte)0x0e;
        data++;
        message[data] = triggerPadData[1];
        data++;
        message[data] = triggerPadData[2];
        data++;
        message[data] = triggerPadData[3];
        data++;
        message[data] = triggerPadData[4];
        data++;
        message[data] = triggerPadData[5];

        createTriggerPadSceneDumpData(triggerPadIndex++, triggerPadData);
        data++;
        message[data] = triggerPadData[0];
        data++;
        message[data] = triggerPadData[1];
        data++;
        message[data] = (byte)0x47;
        data++;
        message[data] = triggerPadData[2];
        data++;
        message[data] = triggerPadData[3];
        data++;
        message[data] = triggerPadData[4];
        data++;
        message[data] = triggerPadData[5];

        createTriggerPadSceneDumpData(triggerPadIndex++, triggerPadData);
        data++;
        message[data] = triggerPadData[0];
        data++;
        message[data] = triggerPadData[1];
        data++;
        message[data] = triggerPadData[2];
        data++;
        message[data] = (byte)0x63;
        data++;
        message[data] = triggerPadData[3];
        data++;
        message[data] = triggerPadData[4];
        data++;
        message[data] = triggerPadData[5];

        createTriggerPadSceneDumpData(triggerPadIndex++, triggerPadData);
        data++;
        message[data] = triggerPadData[0];
        data++;
        message[data] = triggerPadData[1];
        data++;
        message[data] = triggerPadData[2];
        data++;
        message[data] = triggerPadData[3];
        data++;
        message[data] = (byte)0x71;
        data++;
        message[data] = triggerPadData[4];
        data++;
        message[data] = triggerPadData[5];

        createTriggerPadSceneDumpData(triggerPadIndex++, triggerPadData);
        data++;
        message[data] = triggerPadData[0];
        data++;
        message[data] = triggerPadData[1];
        data++;
        message[data] = triggerPadData[2];
        data++;
        message[data] = triggerPadData[3];
        data++;
        message[data] = triggerPadData[4];
        data++;
        message[data] = (byte)0x38;
        data++;
        message[data] = triggerPadData[5];

        createTriggerPadSceneDumpData(triggerPadIndex++, triggerPadData);
        data++;
        message[data] = triggerPadData[0];
        data++;
        message[data] = triggerPadData[1];
        data++;
        message[data] = triggerPadData[2];
        data++;
        message[data] = triggerPadData[3];
        data++;
        message[data] = triggerPadData[4];
        data++;
        message[data] = triggerPadData[5];

        createTriggerPadSceneDumpData(triggerPadIndex++, triggerPadData);
        data++;
        message[data] = (byte)0x1c;
        data++;
        message[data] = triggerPadData[0];
        data++;
        message[data] = triggerPadData[1];
        data++;
        message[data] = triggerPadData[2];
        data++;
        message[data] = triggerPadData[3];
        data++;
        message[data] = triggerPadData[4];
        data++;
        message[data] = triggerPadData[5];

        createTriggerPadSceneDumpData(triggerPadIndex++, triggerPadData);
        data++;
        message[data] = triggerPadData[0];
        data++;
        message[data] = (byte)0x0e;
        data++;
        message[data] = triggerPadData[1];
        data++;
        message[data] = triggerPadData[2];
        data++;
        message[data] = triggerPadData[3];
        data++;
        message[data] = triggerPadData[4];
        data++;
        message[data] = triggerPadData[5];

        data++;
        message[data] = (byte)0x0;

        data++;
        message[data] = (byte)0xf7;
        
        scene = null;
        
        return message;
    }
    
    
    void createTriggerPadSceneDumpData(int triggerPadIndex, byte[] triggerPadData) {
        TriggerPad triggerPad = scene.getTriggerPad(triggerPadIndex);

        Logger.getLogger(SceneDumpSysexResponseAction.class.getName()).log(Level.INFO, "createTriggerPadSceneDumpData");

        int triggerPadDataZeroElement = 0;
        switch(triggerPad.getAssignType()) {
            case ControlChange:
                triggerPadDataZeroElement = (byte)(1 << 5); 
                break;
            case Note:
                triggerPadDataZeroElement = (byte)(2 << 5); 
                break;
            case ProgramChange:
                triggerPadDataZeroElement = (byte)(3 << 5); 
                break;
            case NoAssign:
                triggerPadDataZeroElement = (byte)(4 << 5); 
                break;
        }

        int gateArpEnableBit = (triggerPad.isGateArpEnabled() ? 1 : 0) << 3;
        triggerPadDataZeroElement |= gateArpEnableBit;
        
        int padBehaviourBit = (triggerPad.getPadBehaviour() == TriggerPad.PadBehaviour.Momentary ? 0 : 1) << 2;
        triggerPadDataZeroElement |= padBehaviourBit;
        
        int touchScaleGateArpEnableBit = triggerPad.isTouchScaleGateArpEnable() ? 1 : 0;
        triggerPadDataZeroElement |= touchScaleGateArpEnableBit;
        
        triggerPadData[0] = (byte)triggerPadDataZeroElement;
        triggerPadData[1] = (byte)triggerPad.getNote1();
        triggerPadData[2] = (byte)triggerPad.getNote2();
        triggerPadData[3] = (byte)triggerPad.getNote3();
        triggerPadData[4] = (byte)triggerPad.getNote4();
        triggerPadData[5] = (byte)triggerPad.getMidiChannel();
    }
}
