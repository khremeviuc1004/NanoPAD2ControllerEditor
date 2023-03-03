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
public class TriggerPad {

    public enum AssignType {NoAssign, ControlChange, Note, ProgramChange};
    public enum PadBehaviour {Momentary, Toggle};
    
    private final String name;
    private AssignType assignType = AssignType.Note;
    private boolean gateArpEnabled = false;
    private PadBehaviour padBehaviour = PadBehaviour.Momentary;
    private boolean touchScaleGateArpEnable = false;
    private int note1 = 60;
    private int note2 = 0;
    private int note3 = 0;
    private int note4 = 0;
    private int midiChannel = 16;

    public TriggerPad() {
        this.name = null;
    }

    public TriggerPad(String name) {
        this.name = name;
    }

    public AssignType getAssignType() {
        return assignType;
    }

    public void setAssignType(AssignType assignType) {
        this.assignType = assignType;
    }

    public boolean isGateArpEnabled() {
        return gateArpEnabled;
    }

    public void setGateArpEnabled(boolean gateArpEnabled) {
        this.gateArpEnabled = gateArpEnabled;
    }

    public PadBehaviour getPadBehaviour() {
        return padBehaviour;
    }

    public void setPadBehaviour(PadBehaviour padBehaviour) {
        this.padBehaviour = padBehaviour;
    }

    public boolean isTouchScaleGateArpEnable() {
        return touchScaleGateArpEnable;
    }

    public void setTouchScaleGateArpEnable(boolean touchScaleGateArpEnable) {
        this.touchScaleGateArpEnable = touchScaleGateArpEnable;
    }

    public int getNote1() {
        return note1;
    }

    public void setNote1(int note1) {
        this.note1 = note1;
    }

    public int getNote2() {
        return note2;
    }

    public void setNote2(int note2) {
        this.note2 = note2;
    }

    public int getNote3() {
        return note3;
    }

    public void setNote3(int note3) {
        this.note3 = note3;
    }

    public int getNote4() {
        return note4;
    }

    public void setNote4(int note4) {
        this.note4 = note4;
    }

    public int getMidiChannel() {
        return midiChannel;
    }

    public void setMidiChannel(int midiChannel) {
        this.midiChannel = midiChannel;
    }

    public String getName() {
        return name;
    }
}
