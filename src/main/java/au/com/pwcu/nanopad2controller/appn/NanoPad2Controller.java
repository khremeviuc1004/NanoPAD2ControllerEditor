/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.com.pwcu.nanopad2controller.appn;

import au.com.pwcu.nanopad2controller.domain.NanoPad2;
import au.com.pwcu.nanopad2controller.midisystem.NanoPad2MidiSystem;
import au.com.pwcu.nanopad2controller.ui.NanoPad2Frame;
import com.google.common.eventbus.EventBus;

/**
 *
 * @author kevin
 */
public class NanoPad2Controller {
    
    static {
        System.loadLibrary("nativemidisystem");
    }
    
    private final EventBus eventBus = new EventBus("NanoPAD2 main event bus");
    private final NanoPad2 nanoPad2 = new NanoPad2();
    private final Preferences preferences = new Preferences();
    private final NanoPad2MidiSystem midiSystem = new NanoPad2MidiSystem(eventBus, nanoPad2);
    private final NanoPad2Frame nanoPad2Frame = new NanoPad2Frame(eventBus, nanoPad2, preferences);

    public NanoPad2Controller() {
        nanoPad2Frame.setVisible(true);
        eventBus.register(nanoPad2Frame);
    }
    
    public static void main(String[] args) {
        NanoPad2Controller nanoPad2Controller = new NanoPad2Controller();
    }
}
