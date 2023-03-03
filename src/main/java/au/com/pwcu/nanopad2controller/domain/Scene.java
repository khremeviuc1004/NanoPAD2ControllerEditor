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
public class Scene {
    
    private final TriggerPad[] triggerPads = new TriggerPad[]{
        new TriggerPad("Pad 01"),
        new TriggerPad("Pad 02"),
        new TriggerPad("Pad 03"),
        new TriggerPad("Pad 04"),
        new TriggerPad("Pad 05"),
        new TriggerPad("Pad 06"),
        new TriggerPad("Pad 07"),
        new TriggerPad("Pad 08"),
        new TriggerPad("Pad 09"),
        new TriggerPad("Pad 10"),
        new TriggerPad("Pad 11"),
        new TriggerPad("Pad 12"),
        new TriggerPad("Pad 13"),
        new TriggerPad("Pad 14"),
        new TriggerPad("Pad 15"),
        new TriggerPad("Pad 16")
    };

    public Scene() {
    }
    
    public TriggerPad getTriggerPad(int index) {
        return triggerPads[index];
    }

    public TriggerPad[] getTriggerPads() {
        return triggerPads;
    }
}
