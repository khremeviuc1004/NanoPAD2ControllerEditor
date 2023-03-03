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
public class SceneDataDumpReceivedEvent {
    
    private final int sceneNumber;
    private final byte[] data;

    public SceneDataDumpReceivedEvent(int sceneNumber, byte[] data) {
        this.sceneNumber = sceneNumber;
        this.data = data;
    }

    public int getSceneNumber() {
        return sceneNumber;
    }

    public byte[] getData() {
        return data;
    }
}
