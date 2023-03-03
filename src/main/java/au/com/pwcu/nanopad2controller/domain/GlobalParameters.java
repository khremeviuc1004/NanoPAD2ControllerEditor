/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.com.pwcu.nanopad2controller.domain;

/**
 *
 * @author kevin
 */
public class GlobalParameters {
    
    public enum VelocityCurve {
        Light(0), Normal(1), Heavy(2), Constant(3);
     
        private final int val;

        private VelocityCurve(int val) {
            this.val = val;
        }

        public int getVal() {
            return val;
        }
    }
    
    public enum MidiClock { 
        AUTO(0), INTERNAL(1), EXTERNAL(2);
     
        private final int val;

        private MidiClock(int val) {
            this.val = val;
        }

        public int getVal() {
            return val;
        }
    }
    
    public enum AssignType { 
        NO_ASSIGN(0), CC(1), PITCH_BEND(2);
     
        private final int val;

        private AssignType(int val) {
            this.val = val;
        }

        public int getVal() {
            return val;
        }
}
    
    public enum Polarity { 
        NORMAL(0), REVERSE(1);
     
        private final int val;

        private Polarity(int val) {
            this.val = val;
        }

        public int getVal() {
            return val;
        }
    }

    //common
    private int globalMidiChannel = 0;
    private VelocityCurve velocityCurve = VelocityCurve.Normal;
    private int constantVelocityValue = 100;
    private float bpm = 120.0f;
    private MidiClock midiClock = MidiClock.AUTO;
    
    // X-Y Pad - CC mode
    private AssignType xyPadCCModeXAxisAssignType = AssignType.CC;
    private int xyPadCCModeXAxisCCNumber = 1;
    private Polarity xyPadCCModeXAxisPolarity = Polarity.NORMAL;
    private AssignType xyPadCCModeYAxisAssignType = AssignType.CC;
    private int xyPadCCModeYAxisCCNumber = 2;
    private Polarity xyPadCCModeYAxisPolarity = Polarity.NORMAL;
    private int xyPadCCModeMidiChannel = 16;
    private boolean xyPadCCModeTouchEnabled = true;
    private int xyPadCCModeTouchCCNumber = 16;
    private int xyPadCCModeTouchOffValue = 0;
    private int xyPadCCModeTouchOnValue = 127;
    
    // X-Y Pad - Touch Scale
    private int xyPadTouchScaleNoteOnVelocity = 100;
    private boolean xyPadTouchScaleYAxisCCEnabled = true;
    private int xyPadTouchScaleYAxisCCNumber = 2;
    private Polarity xyPadTouchScaleYAxisPolarity = Polarity.NORMAL;
    private int xyPadTouchScaleMidiChannel = 16;
    private int xyPadTouchScaleGateSpeed = 4;
    
    // User Scale
    private int userScaleLength = 12;
    private int noteOffset01 = 0;
    private int noteOffset02 = 1;
    private int noteOffset03 = 2;
    private int noteOffset04 = 3;
    private int noteOffset05 = 4;
    private int noteOffset06 = 5;
    private int noteOffset07 = 6;
    private int noteOffset08 = 7;
    private int noteOffset09 = 8;
    private int noteOffset10 = 9;
    private int noteOffset11 = 10;
    private int noteOffset12 = 11;

    public GlobalParameters() {
    }

    public int getGlobalMidiChannel() {
        return globalMidiChannel;
    }

    public void setGlobalMidiChannel(int globalMidiChannel) {
        this.globalMidiChannel = globalMidiChannel;
    }

    public VelocityCurve getVelocityCurve() {
        return velocityCurve;
    }

    public void setVelocityCurve(VelocityCurve velocityCurve) {
        this.velocityCurve = velocityCurve;
    }

    public int getConstantVelocityValue() {
        return constantVelocityValue;
    }

    public void setConstantVelocityValue(int constantVelocityValue) {
        this.constantVelocityValue = constantVelocityValue;
    }

    public float getBpm() {
        return bpm;
    }

    public void setBpm(float bpm) {
        this.bpm = bpm;
    }

    public MidiClock getMidiClock() {
        return midiClock;
    }

    public void setMidiClock(MidiClock midiClock) {
        this.midiClock = midiClock;
    }

    public AssignType getXyPadCCModeXAxisAssignType() {
        return xyPadCCModeXAxisAssignType;
    }

    public void setXyPadCCModeXAxisAssignType(AssignType xyPadCCModeXAxisAssignType) {
        this.xyPadCCModeXAxisAssignType = xyPadCCModeXAxisAssignType;
    }

    public int getXyPadCCModeXAxisCCNumber() {
        return xyPadCCModeXAxisCCNumber;
    }

    public void setXyPadCCModeXAxisCCNumber(int xyPadCCModeXAxisCCNumber) {
        this.xyPadCCModeXAxisCCNumber = xyPadCCModeXAxisCCNumber;
    }

    public Polarity getXyPadCCModeXAxisPolarity() {
        return xyPadCCModeXAxisPolarity;
    }

    public void setXyPadCCModeXAxisPolarity(Polarity xyPadCCModeXAxisPolarity) {
        this.xyPadCCModeXAxisPolarity = xyPadCCModeXAxisPolarity;
    }

    public AssignType getXyPadCCModeYAxisAssignType() {
        return xyPadCCModeYAxisAssignType;
    }

    public void setXyPadCCModeYAxisAssignType(AssignType xyPadCCModeYAxisAssignType) {
        this.xyPadCCModeYAxisAssignType = xyPadCCModeYAxisAssignType;
    }

    public int getXyPadCCModeYAxisCCNumber() {
        return xyPadCCModeYAxisCCNumber;
    }

    public void setXyPadCCModeYAxisCCNumber(int xyPadCCModeYAxisCCNumber) {
        this.xyPadCCModeYAxisCCNumber = xyPadCCModeYAxisCCNumber;
    }

    public Polarity getXyPadCCModeYAxisPolarity() {
        return xyPadCCModeYAxisPolarity;
    }

    public void setXyPadCCModeYAxisPolarity(Polarity xyPadCCModeYAxisPolarity) {
        this.xyPadCCModeYAxisPolarity = xyPadCCModeYAxisPolarity;
    }

    public int getXyPadCCModeMidiChannel() {
        return xyPadCCModeMidiChannel;
    }

    public void setXyPadCCModeMidiChannel(int xyPadCCModeMidiChannel) {
        this.xyPadCCModeMidiChannel = xyPadCCModeMidiChannel;
    }

    public boolean isXyPadCCModeTouchEnabled() {
        return xyPadCCModeTouchEnabled;
    }

    public void setXyPadCCModeTouchEnabled(boolean xyPadCCModeTouchEnabled) {
        this.xyPadCCModeTouchEnabled = xyPadCCModeTouchEnabled;
    }

    public int getXyPadCCModeTouchCCNumber() {
        return xyPadCCModeTouchCCNumber;
    }

    public void setXyPadCCModeTouchCCNumber(int xyPadCCModeTouchCCNumber) {
        this.xyPadCCModeTouchCCNumber = xyPadCCModeTouchCCNumber;
    }

    public int getXyPadCCModeTouchOffValue() {
        return xyPadCCModeTouchOffValue;
    }

    public void setXyPadCCModeTouchOffValue(int xyPadCCModeTouchOffValue) {
        this.xyPadCCModeTouchOffValue = xyPadCCModeTouchOffValue;
    }

    public int getXyPadCCModeTouchOnValue() {
        return xyPadCCModeTouchOnValue;
    }

    public void setXyPadCCModeTouchOnValue(int xyPadCCModeTouchOnValue) {
        this.xyPadCCModeTouchOnValue = xyPadCCModeTouchOnValue;
    }

    public int getXyPadTouchScaleNoteOnVelocity() {
        return xyPadTouchScaleNoteOnVelocity;
    }

    public void setXyPadTouchScaleNoteOnVelocity(int xyPadTouchScaleNoteOnVelocity) {
        this.xyPadTouchScaleNoteOnVelocity = xyPadTouchScaleNoteOnVelocity;
    }

    public boolean isXyPadTouchScaleYAxisCCEnabled() {
        return xyPadTouchScaleYAxisCCEnabled;
    }

    public void setXyPadTouchScaleYAxisCCEnabled(boolean xyPadTouchScaleYAxisCCEnabled) {
        this.xyPadTouchScaleYAxisCCEnabled = xyPadTouchScaleYAxisCCEnabled;
    }

    public int getXyPadTouchScaleYAxisCCNumber() {
        return xyPadTouchScaleYAxisCCNumber;
    }

    public void setXyPadTouchScaleYAxisCCNumber(int xyPadTouchScaleYAxisCCNumber) {
        this.xyPadTouchScaleYAxisCCNumber = xyPadTouchScaleYAxisCCNumber;
    }

    public Polarity getXyPadTouchScaleYAxisPolarity() {
        return xyPadTouchScaleYAxisPolarity;
    }

    public void setXyPadTouchScaleYAxisPolarity(Polarity xyPadTouchScaleYAxisPolarity) {
        this.xyPadTouchScaleYAxisPolarity = xyPadTouchScaleYAxisPolarity;
    }

    public int getXyPadTouchScaleMidiChannel() {
        return xyPadTouchScaleMidiChannel;
    }

    public void setXyPadTouchScaleMidiChannel(int xyPadTouchScaleMidiChannel) {
        this.xyPadTouchScaleMidiChannel = xyPadTouchScaleMidiChannel;
    }

    public int getXyPadTouchScaleGateSpeed() {
        return xyPadTouchScaleGateSpeed;
    }

    public void setXyPadTouchScaleGateSpeed(int xyPadTouchScaleGateSpeed) {
        this.xyPadTouchScaleGateSpeed = xyPadTouchScaleGateSpeed;
    }

    public int getUserScaleLength() {
        return userScaleLength;
    }

    public void setUserScaleLength(int userScaleLength) {
        this.userScaleLength = userScaleLength;
    }

    public int getNoteOffset01() {
        return noteOffset01;
    }

    public void setNoteOffset01(int noteOffset01) {
        this.noteOffset01 = noteOffset01;
    }

    public int getNoteOffset02() {
        return noteOffset02;
    }

    public void setNoteOffset02(int noteOffset02) {
        this.noteOffset02 = noteOffset02;
    }

    public int getNoteOffset03() {
        return noteOffset03;
    }

    public void setNoteOffset03(int noteOffset03) {
        this.noteOffset03 = noteOffset03;
    }

    public int getNoteOffset04() {
        return noteOffset04;
    }

    public void setNoteOffset04(int noteOffset04) {
        this.noteOffset04 = noteOffset04;
    }

    public int getNoteOffset05() {
        return noteOffset05;
    }

    public void setNoteOffset05(int noteOffset05) {
        this.noteOffset05 = noteOffset05;
    }

    public int getNoteOffset06() {
        return noteOffset06;
    }

    public void setNoteOffset06(int noteOffset06) {
        this.noteOffset06 = noteOffset06;
    }

    public int getNoteOffset07() {
        return noteOffset07;
    }

    public void setNoteOffset07(int noteOffset07) {
        this.noteOffset07 = noteOffset07;
    }

    public int getNoteOffset08() {
        return noteOffset08;
    }

    public void setNoteOffset08(int noteOffset08) {
        this.noteOffset08 = noteOffset08;
    }

    public int getNoteOffset09() {
        return noteOffset09;
    }

    public void setNoteOffset09(int noteOffset09) {
        this.noteOffset09 = noteOffset09;
    }

    public int getNoteOffset10() {
        return noteOffset10;
    }

    public void setNoteOffset10(int noteOffset10) {
        this.noteOffset10 = noteOffset10;
    }

    public int getNoteOffset11() {
        return noteOffset11;
    }

    public void setNoteOffset11(int noteOffset11) {
        this.noteOffset11 = noteOffset11;
    }

    public int getNoteOffset12() {
        return noteOffset12;
    }

    public void setNoteOffset12(int noteOffset12) {
        this.noteOffset12 = noteOffset12;
    }
}
