/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.com.pwcu.nanopad2controller.appn;

/**
 *
 * @author kevin
 */
public class Preferences {
    
    private boolean manualMidiPortSelection = false;
    private String midiInPortName;
    private String midiOutPortName;
    private boolean receiveSceneSetAutomatically = true;
    private boolean warnBeforeWriting = false;

    public Preferences() {
    }

    public Preferences(String midiInPortName, String midiOutPortName) {
        this.midiInPortName = midiInPortName;
        this.midiOutPortName = midiOutPortName;
    }

    public boolean isManualMidiPortSelection() {
        return manualMidiPortSelection;
    }

    public void setManualMidiPortSelection(boolean manualMidiPortSelection) {
        this.manualMidiPortSelection = manualMidiPortSelection;
    }

    public String getMidiInPortName() {
        return midiInPortName;
    }

    public void setMidiInPortName(String midiInPortName) {
        this.midiInPortName = midiInPortName;
    }

    public String getMidiOutPortName() {
        return midiOutPortName;
    }

    public void setMidiOutPortName(String midiOutPortName) {
        this.midiOutPortName = midiOutPortName;
    }

    public boolean isReceiveSceneSetAutomatically() {
        return receiveSceneSetAutomatically;
    }

    public void setReceiveSceneSetAutomatically(boolean receiveSceneSetAutomatically) {
        this.receiveSceneSetAutomatically = receiveSceneSetAutomatically;
    }

    public boolean isWarnBeforeWriting() {
        return warnBeforeWriting;
    }

    public void setWarnBeforeWriting(boolean warnBeforeWriting) {
        this.warnBeforeWriting = warnBeforeWriting;
    }
}
