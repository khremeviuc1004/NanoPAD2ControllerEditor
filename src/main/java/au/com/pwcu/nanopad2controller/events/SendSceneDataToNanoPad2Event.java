/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.com.pwcu.nanopad2controller.events;

/**
 *
 * @author kevin
 */
public class SendSceneDataToNanoPad2Event {

    private final int sceneNumber;

    public SendSceneDataToNanoPad2Event(int sceneNumber) {
        this.sceneNumber = sceneNumber;
    }

    public int getSceneNumber() {
        return sceneNumber;
    }
}
