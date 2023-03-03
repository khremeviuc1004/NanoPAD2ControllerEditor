/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.com.pwcu.nanopad2controller.job;

import au.com.pwcu.nanopad2controller.domain.GlobalParameters;
import com.google.common.eventbus.EventBus;

/**
 *
 * @author kevin
 */
public class GlobalDataSendToNanoPad2SysexJob extends SysexJob {
    
    private GlobalParameters globalParameters;

    public GlobalDataSendToNanoPad2SysexJob(EventBus eventBus, GlobalParameters globalParameters) {
        super(eventBus);
        this.globalParameters = globalParameters;
    }

    @Override
    public byte[] populateSysexMessage() {
        byte[] message = new byte[65];
    
        message[0] = (byte)0xf0; 
        message[1] = (byte)0x42; 
        message[2] = (byte)0x40; 
        message[3] = (byte)0x00; 
        message[4] = (byte)0x01; 
        message[5] = (byte)0x12; 
        message[6] = (byte)0x00; 
        message[7] = (byte)0x7f; //data dump command
        message[8] = (byte)0x37; //data length - 55 bytes 
        message[9] = (byte)0x51; //global data dump
        
        message[10] = (byte)0x10;// rubbish
        
        message[11] = (byte)globalParameters.getGlobalMidiChannel();// 00 common global midi channel - 1
        message[12] = (byte)globalParameters.getVelocityCurve().getVal();// 01 common velocity curve - normal
        message[13] = (byte)globalParameters.getConstantVelocityValue();// 64 common constant velocity - 100
        
        int[] convertedBpm = processBpm(globalParameters.getBpm());
        message[14] = (byte)convertedBpm[0];
        message[15] = (byte)convertedBpm[1];
        
        message[16] = (byte)globalParameters.getMidiClock().getVal();// 00 common midi clock
        
        message[17] = (byte)0x00;// 00 reserved or rubbish
        message[18] = (byte)0x08;// 08 reserved or rubbishs
        
        message[19] = (byte)globalParameters.getXyPadCCModeXAxisAssignType().getVal();// 01 xy pad cc mode x axis assign type - CC
        message[20] = (byte)globalParameters.getXyPadCCModeXAxisCCNumber();// 01 xy pad cc mode x axis cc number - 2
        message[21] = (byte)globalParameters.getXyPadCCModeXAxisPolarity().getVal();// 00 xy pad cc mode x axis polarity - normal
        
        message[22] = (byte)0x7f;// 7f reserved - supposed to be ff
        
        message[23] = (byte)globalParameters.getXyPadCCModeYAxisAssignType().getVal();// 01 xy pad cc mode y axis assign type - CC
        message[24] = (byte)globalParameters.getXyPadCCModeYAxisCCNumber();// 02 xy pad cc mode y axis cc number - 2
        message[25] = (byte)globalParameters.getXyPadCCModeYAxisPolarity().getVal();// 00 xy pad cc mode y axis polarity - normal
        
        message[26] = (byte)0x60;// 60 rubbish
        
        message[27] = (byte)globalParameters.getXyPadCCModeMidiChannel();// 10 xy pad cc mode midi channel - global
        message[28] = (byte)(globalParameters.isXyPadCCModeTouchEnabled() ? 1 : 0);// 01 xy pad cc mode touch enable - enabled
        message[29] = (byte)globalParameters.getXyPadCCModeTouchCCNumber();// 10 xy pad touch cc number - 16
        message[30] = (byte)globalParameters.getXyPadCCModeTouchOffValue();// 00 xy pad touch off value - 0
        message[31] = (byte)globalParameters.getXyPadCCModeTouchOnValue();// 7f xy pad touch on value - 127
        
        message[32] = (byte)0x7f;// 7f reserved - supposed to be ff
        message[33] = (byte)0x7f;// 7f reserved - supposed to be ff
        message[34] = (byte)0x20;// 20 rubbish
        
        message[35] = (byte)globalParameters.getXyPadTouchScaleNoteOnVelocity();// 64 touch scale note on velocity
        message[36] = (byte)(globalParameters.isXyPadTouchScaleYAxisCCEnabled() ? 1 : 0);// 01 touch scale y axis cc enable
        message[37] = (byte)globalParameters.getXyPadTouchScaleYAxisCCNumber();// 02 touch scale y axis cc number
        message[38] = (byte)globalParameters.getXyPadTouchScaleYAxisPolarity().getVal();// 00 touch scale y axis polarity
        message[39] = (byte)globalParameters.getXyPadTouchScaleMidiChannel();// 10 touch scale midi channel - global midi channel
        
        message[40] = (byte)0x7f;// 7f reserved - supposed to be ff
        message[41] = (byte)0x02;// 02 rubbish??
        
        message[42] = (byte)globalParameters.getXyPadTouchScaleGateSpeed();// 03 gate speed - 0.333 1/8th???
        
        message[43] = (byte)0x7f;// 7f reserved - supposed to be ff
        message[44] = (byte)0x7f;// 7f reserved - supposed to be ff
        
        message[45] = (byte)globalParameters.getUserScaleLength();// 0c note length
        message[46] = (byte)globalParameters.getNoteOffset01();// 00 note offset 1
        message[47] = (byte)globalParameters.getNoteOffset02();// 01 note offset 2
        message[48] = (byte)globalParameters.getNoteOffset03();// 02 note offset 3
        message[49] = (byte)globalParameters.getNoteOffset04();// 03 note offset 4
        
        message[50] = (byte)0x00;// 00 rubbish
        
        message[51] = (byte)globalParameters.getNoteOffset05();// 04 note offset 5
        message[52] = (byte)globalParameters.getNoteOffset06();// 05 note offset 6
        message[53] = (byte)globalParameters.getNoteOffset07();// 06 note offset 7
        message[54] = (byte)globalParameters.getNoteOffset08();// 07 note offset 8
        message[55] = (byte)globalParameters.getNoteOffset09();// 08 note offset 9
        message[56] = (byte)globalParameters.getNoteOffset10();// 09 note offset 10
        message[57] = (byte)globalParameters.getNoteOffset11();// 0a note offset 11
        
        message[58] = (byte)0x1e;// 1e rubbish
        
        message[59] = (byte)globalParameters.getNoteOffset12();// 0b note offset 12
        
        message[60] = (byte)0x7f;// 7f reserved - supposed to be ff
        message[61] = (byte)0x7f;// 7f reserved - supposed to be ff
        message[62] = (byte)0x7f;// 7f reserved - supposed to be ff
        message[63] = (byte)0x7f;// 7f reserved - supposed to be ff
        
        message[64] = (byte)0xf7;// f7 end sysex

        globalParameters = null;
        
        return message;
    }
    
    private int[] processBpm(float bpm) {
        int[] bpmData = new int[]{0, 0};
        int bpmConverted = (int)(bpm * 10.0f);
        
        bpmData[0] = bpmConverted >>> 8;
        bpmData[1] = bpmConverted & 255;

        return bpmData;
    }
}
