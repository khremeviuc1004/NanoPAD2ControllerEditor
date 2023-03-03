/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.com.pwcu.nanopad2controller.midisystem.actions;

import au.com.pwcu.nanopad2controller.domain.GlobalParameters;

/**
 *
 * @author kevin
 */
public class GlobalDataDumpResponseAction implements SysexResponseAction {
    
    private GlobalParameters globalParameters;

    public GlobalDataDumpResponseAction(GlobalParameters globalParameters) {
        this.globalParameters = globalParameters;
    }

    @Override
    public void performAction(byte[] message) {
        if(
        message[0] == (byte)0xf0 
        && message[1] == (byte)0x42 
        && message[2] == (byte)0x40 
        && message[3] == (byte)0x00 
        && message[4] == (byte)0x01 
        && message[5] == (byte)0x12 
        && message[6] == (byte)0x00 
        && message[7] == (byte)0x7f //data dump command
        && message[8] == (byte)0x37 //data length - 55 bytes 
        && message[9] == (byte)0x51 //global data dump
                ) {
            //message[10];// 10 rubbish
            globalParameters.setGlobalMidiChannel((int)message[11]);// 00 common global midi channel - 1
            globalParameters.setVelocityCurve(getVelocityCurve((int)message[12]));// 01 common velocity curve - normal
            globalParameters.setConstantVelocityValue((int)message[13]);// 64 common constant velocity - 100
            globalParameters.setBpm(processBpm((int)message[14], (int)message[15]));// 04 30 common bpm - 2 byte
            globalParameters.setMidiClock(getMidiClock(message[16]));// 00 common midi clock
            //message[17];// 00 reserved or rubbish
            //message[18];// 08 reserved or rubbishs
            globalParameters.setXyPadCCModeXAxisAssignType(getAssignTypeForVal((int)message[19]));// 01 xy pad cc mode x axis assign type - CC
            globalParameters.setXyPadCCModeXAxisCCNumber((int)message[20]);// 01 xy pad cc mode x axis cc number - 2
            globalParameters.setXyPadCCModeXAxisPolarity(getPolarityForVal((int)message[21]));// 00 xy pad cc mode x axis polarity - normal
            //message[22];// 7f reserved - supposed to be ff
            globalParameters.setXyPadCCModeYAxisAssignType(getAssignTypeForVal((int)message[23]));// 01 xy pad cc mode y axis assign type - CC
            globalParameters.setXyPadCCModeYAxisCCNumber((int)message[24]);// 02 xy pad cc mode y axis cc number - 2
            globalParameters.setXyPadCCModeYAxisPolarity(getPolarityForVal((int)message[25]));// 00 xy pad cc mode y axis polarity - normal
            //message[26];// 60 rubbish
            globalParameters.setXyPadCCModeMidiChannel((int)message[27]);// 10 xy pad cc mode midi channel - global
            globalParameters.setXyPadCCModeTouchEnabled(((int)message[28]) == 1 ? true : false);// 01 xy pad cc mode touch enable - enabled
            globalParameters.setXyPadCCModeTouchCCNumber((int)message[29]);// 10 xy pad touch cc number - 16
            globalParameters.setXyPadCCModeTouchOffValue((int)message[30]);// 00 xy pad touch off value - 0
            globalParameters.setXyPadCCModeTouchOnValue((int)message[31]);// 7f xy pad touch on value - 127
            //message[32];// 7f reserved - supposed to be ff
            //message[33];// 7f reserved - supposed to be ff
            //message[34];// 20 rubbish
            globalParameters.setXyPadTouchScaleNoteOnVelocity((int)message[35]);// 64 touch scale note on velocity
            globalParameters.setXyPadTouchScaleYAxisCCEnabled(((int)message[36]) == 1 ? true : false);// 01 touch scale y axis cc enable
            globalParameters.setXyPadTouchScaleYAxisCCNumber((int)message[37]);// 02 touch scale y axis cc number
            globalParameters.setXyPadTouchScaleYAxisPolarity(((int)message[38]) == 0 ? GlobalParameters.Polarity.NORMAL : GlobalParameters.Polarity.REVERSE);// 00 touch scale y axis polarity
            globalParameters.setXyPadTouchScaleMidiChannel((int)message[39]);// 10 touch scale midi channel - global midi channel
            //message[40];// 7f reserved - supposed to be ff
            //globalParameters.setmessage[41];// 02 rubbish??
            globalParameters.setXyPadTouchScaleGateSpeed((int)message[42]);// 03 gate speed - 0.333 1/8th???
            //message[43];// 7f reserved - supposed to be ff
            //message[44];// 7f reserved - supposed to be ff
            globalParameters.setUserScaleLength((int)message[45]);// 0c note length
            globalParameters.setNoteOffset01((int)message[46]);// 00 note offset 1
            globalParameters.setNoteOffset02((int)message[47]);// 01 note offset 2
            globalParameters.setNoteOffset03((int)message[48]);// 02 note offset 3
            globalParameters.setNoteOffset04((int)message[49]);// 03 note offset 4
            //message[50];// 00 rubbish
            globalParameters.setNoteOffset05((int)message[51]);// 04 note offset 5
            globalParameters.setNoteOffset06((int)message[52]);// 05 note offset 6
            globalParameters.setNoteOffset07((int)message[53]);// 06 note offset 7
            globalParameters.setNoteOffset08((int)message[54]);// 07 note offset 8
            globalParameters.setNoteOffset09((int)message[55]);// 08 note offset 9
            globalParameters.setNoteOffset10((int)message[56]);// 09 note offset 10
            globalParameters.setNoteOffset11((int)message[57]);// 0a note offset 11
            //message[58];// 1e rubbish
            globalParameters.setNoteOffset12((int)message[59]);// 0b note offset 12
            //message[60];// 7f reserved - supposed to be ff
            //message[61];// 7f reserved - supposed to be ff
            //message[62];// 7f reserved - supposed to be ff
            //message[63];// 7f reserved - supposed to be ff
            //message[64];// f7 end sysex
            
            globalParameters = null;
        }
    }

    private GlobalParameters.AssignType getAssignTypeForVal(int val) {
        return val == 0 ? GlobalParameters.AssignType.NO_ASSIGN : val == 1 ? GlobalParameters.AssignType.CC : GlobalParameters.AssignType.PITCH_BEND;
    }
    
    private GlobalParameters.Polarity getPolarityForVal(int val) {
        return val == 0 ? GlobalParameters.Polarity.NORMAL : GlobalParameters.Polarity.REVERSE;
    }
    
    private float processBpm(int firstByte, int secondByte) {
        int together = (firstByte << 8) | secondByte;
        return ((float)together) / 10.0f;
    }
    
    private GlobalParameters.VelocityCurve getVelocityCurve(int val) {
        switch(val) {
            case 0:
                return GlobalParameters.VelocityCurve.Light;
            case 1:
                return GlobalParameters.VelocityCurve.Normal;
            case 2:
                return GlobalParameters.VelocityCurve.Heavy;
            case 3:
                return GlobalParameters.VelocityCurve.Constant;
        }
        return GlobalParameters.VelocityCurve.Normal;
    }
    
    private GlobalParameters.MidiClock getMidiClock(int val) {
        return val == 0 ? GlobalParameters.MidiClock.AUTO : val == 1 ? GlobalParameters.MidiClock.INTERNAL : GlobalParameters.MidiClock.EXTERNAL;
    }
}
