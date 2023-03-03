/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.com.pwcu.nanopad2controller.midisystem.actions;

import au.com.pwcu.nanopad2controller.domain.Scene;
import au.com.pwcu.nanopad2controller.domain.TriggerPad;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author kevin
 */
public class SceneDumpSysexResponseAction implements SysexResponseAction {
    
    private Scene scene;

    public SceneDumpSysexResponseAction(Scene scene) {
        this.scene = scene;
    }
    

    @Override
    public void performAction(byte[] message) {
        //check is current scene dump system exclusive
        if(
                message[0] == (byte)0xf0
                && message[1] == (byte)0x42
                && message[2] == (byte)0x40
                && message[3] == (byte)0x00
                && message[4] == (byte)0x01
                && message[5] == (byte)0x12
                && message[6] == (byte)0x00
                && message[7] == (byte)0x7f
                && message[8] == (byte)0x70
                && message[9] == (byte)0x40
                ) {
            int triggerPadIndex = 0;
            byte[] triggerPadData = new byte[6];
            int data = 11;//start at index 11

            triggerPadData[0] = message[data];
            data++;
            triggerPadData[1] = message[data];
            data++;
            triggerPadData[2] = message[data];
            data++;
            triggerPadData[3] = message[data];
            data++;
            triggerPadData[4] = message[data];
            data++;
            triggerPadData[5] = message[data];
            processTriggerPadSceneDumpData(triggerPadIndex++, triggerPadData);

            data++;
            triggerPadData[0] = message[data];
            data+=2;
            triggerPadData[1] = message[data];
            data++;
            triggerPadData[2] = message[data];
            data++;
            triggerPadData[3] = message[data];
            data++;
            triggerPadData[4] = message[data];
            data++;
            triggerPadData[5] = message[data];
            processTriggerPadSceneDumpData(triggerPadIndex++, triggerPadData);

            data++;
            triggerPadData[0] = message[data];
            data++;
            triggerPadData[1] = message[data];
            data+=2;
            triggerPadData[2] = message[data];
            data++;
            triggerPadData[3] = message[data];
            data++;
            triggerPadData[4] = message[data];
            data++;
            triggerPadData[5] = message[data];
            processTriggerPadSceneDumpData(triggerPadIndex++, triggerPadData);

            data++;
            triggerPadData[0] = message[data];
            data++;
            triggerPadData[1] = message[data];
            data++;
            triggerPadData[2] = message[data];
            data+=2;
            triggerPadData[3] = message[data];
            data++;
            triggerPadData[4] = message[data];
            data++;
            triggerPadData[5] = message[data];
            processTriggerPadSceneDumpData(triggerPadIndex++, triggerPadData);

            data++;
            triggerPadData[0] = message[data];
            data++;
            triggerPadData[1] = message[data];
            data++;
            triggerPadData[2] = message[data];
            data++;
            triggerPadData[3] = message[data];
            data+=2;
            triggerPadData[4] = message[data];
            data++;
            triggerPadData[5] = message[data];
            processTriggerPadSceneDumpData(triggerPadIndex++, triggerPadData);

            data++;
            triggerPadData[0] = message[data];
            data++;
            triggerPadData[1] = message[data];
            data++;
            triggerPadData[2] = message[data];
            data++;
            triggerPadData[3] = message[data];
            data++;
            triggerPadData[4] = message[data];
            data+=2;
            triggerPadData[5] = message[data];
            processTriggerPadSceneDumpData(triggerPadIndex++, triggerPadData);

            data++;
            triggerPadData[0] = message[data];
            data++;
            triggerPadData[1] = message[data];
            data++;
            triggerPadData[2] = message[data];
            data++;
            triggerPadData[3] = message[data];
            data++;
            triggerPadData[4] = message[data];
            data++;
            triggerPadData[5] = message[data];
            processTriggerPadSceneDumpData(triggerPadIndex++, triggerPadData);

            data+=2;
            triggerPadData[0] = message[data];
            data++;
            triggerPadData[1] = message[data];
            data++;
            triggerPadData[2] = message[data];
            data++;
            triggerPadData[3] = message[data];
            data++;
            triggerPadData[4] = message[data];
            data++;
            triggerPadData[5] = message[data];
            processTriggerPadSceneDumpData(triggerPadIndex++, triggerPadData);

            data++;
            triggerPadData[0] = message[data];
            data+=2;
            triggerPadData[1] = message[data];
            data++;
            triggerPadData[2] = message[data];
            data++;
            triggerPadData[3] = message[data];
            data++;
            triggerPadData[4] = message[data];
            data++;
            triggerPadData[5] = message[data];
            processTriggerPadSceneDumpData(triggerPadIndex++, triggerPadData);

            data++;
            triggerPadData[0] = message[data];
            data++;
            triggerPadData[1] = message[data];
            data+=2;
            triggerPadData[2] = message[data];
            data++;
            triggerPadData[3] = message[data];
            data++;
            triggerPadData[4] = message[data];
            data++;
            triggerPadData[5] = message[data];
            processTriggerPadSceneDumpData(triggerPadIndex++, triggerPadData);

            data++;
            triggerPadData[0] = message[data];
            data++;
            triggerPadData[1] = message[data];
            data++;
            triggerPadData[2] = message[data];
            data+=2;
            triggerPadData[3] = message[data];
            data++;
            triggerPadData[4] = message[data];
            data++;
            triggerPadData[5] = message[data];
            processTriggerPadSceneDumpData(triggerPadIndex++, triggerPadData);

            data++;
            triggerPadData[0] = message[data];
            data++;
            triggerPadData[1] = message[data];
            data++;
            triggerPadData[2] = message[data];
            data++;
            triggerPadData[3] = message[data];
            data+=2;
            triggerPadData[4] = message[data];
            data++;
            triggerPadData[5] = message[data];
            processTriggerPadSceneDumpData(triggerPadIndex++, triggerPadData);

            data++;
            triggerPadData[0] = message[data];
            data++;
            triggerPadData[1] = message[data];
            data++;
            triggerPadData[2] = message[data];
            data++;
            triggerPadData[3] = message[data];
            data++;
            triggerPadData[4] = message[data];
            data+=2;
            triggerPadData[5] = message[data];
            processTriggerPadSceneDumpData(triggerPadIndex++, triggerPadData);

            data++;
            triggerPadData[0] = message[data];
            data++;
            triggerPadData[1] = message[data];
            data++;
            triggerPadData[2] = message[data];
            data++;
            triggerPadData[3] = message[data];
            data++;
            triggerPadData[4] = message[data];
            data++;
            triggerPadData[5] = message[data];
            processTriggerPadSceneDumpData(triggerPadIndex++, triggerPadData);

            data+=2;
            triggerPadData[0] = message[data];
            data++;
            triggerPadData[1] = message[data];
            data++;
            triggerPadData[2] = message[data];
            data++;
            triggerPadData[3] = message[data];
            data++;
            triggerPadData[4] = message[data];
            data++;
            triggerPadData[5] = message[data];
            processTriggerPadSceneDumpData(triggerPadIndex++, triggerPadData);

            data++;
            triggerPadData[0] = message[data];
            data+=2;
            triggerPadData[1] = message[data];
            data++;
            triggerPadData[2] = message[data];
            data++;
            triggerPadData[3] = message[data];
            data++;
            triggerPadData[4] = message[data];
            data++;
            triggerPadData[5] = message[data];
            processTriggerPadSceneDumpData(triggerPadIndex++, triggerPadData);
        }
        else
        {
            Logger.getLogger(SceneDumpSysexResponseAction.class.getName()).log(Level.INFO, "Not processing message.");
        }
        
        scene = null;
    }
    
    void processTriggerPadSceneDumpData(int triggerPadIndex, byte[] triggerPadData) {
        TriggerPad triggerPad = scene.getTriggerPad(triggerPadIndex);

        Logger.getLogger(SceneDumpSysexResponseAction.class.getName()).log(Level.INFO, "processTriggerPadSceneDumpData");

        int assignType = ((int)triggerPadData[0]) >>> 5;
        switch((int)assignType)
        {
        case 1:
            triggerPad.setAssignType(TriggerPad.AssignType.ControlChange);
            break;
        case 2:
            triggerPad.setAssignType(TriggerPad.AssignType.Note);
            break;
        case 3:
            triggerPad.setAssignType(TriggerPad.AssignType.ProgramChange);
            break;
        case 0:
        default:
            triggerPad.setAssignType(TriggerPad.AssignType.NoAssign);
            break;
        }

        int gateArpEnable = (((int)triggerPadData[0]) & 12) >>> 3;
        triggerPad.setGateArpEnabled(gateArpEnable == 0 ? false : true);
        int padBehaviour = (((int)triggerPadData[0]) & 6) >>> 2;
        triggerPad.setPadBehaviour(padBehaviour == 0 ? TriggerPad.PadBehaviour.Momentary : TriggerPad.PadBehaviour.Toggle);

        int touchScaleGateArpEnable = ((int)triggerPadData[0]) & 1;
        triggerPad.setTouchScaleGateArpEnable(touchScaleGateArpEnable == 0 ? false : true);

        int triggerPadNote1 = triggerPadData[1];
        triggerPad.setNote1(triggerPadNote1);
        Logger.getLogger(SceneDumpSysexResponseAction.class.getName()).log(Level.INFO, "Trigger pad #{}, note: {}", new Object[]{triggerPadIndex, triggerPadNote1});

        int triggerPadNote2 = triggerPadData[2];
        triggerPad.setNote2(triggerPadNote2);

        int triggerPadNote3 = triggerPadData[3];
        triggerPad.setNote3(triggerPadNote3);

        int triggerPadNote4 = triggerPadData[4];
        triggerPad.setNote4(triggerPadNote4);

        int triggerPadMidiChannel = triggerPadData[5];
        triggerPad.setMidiChannel(triggerPadMidiChannel);
    }
}
