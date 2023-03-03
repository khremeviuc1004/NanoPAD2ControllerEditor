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
public class NanoPad2 {
    
    private int currentScene = 0;
    private Scene[] scenes = new Scene[]{new Scene(), new Scene(), new Scene(), new Scene()};
    private GlobalParameters globalParameters = new GlobalParameters();

    public NanoPad2() {
    }

    public int getCurrentScene() {
        return currentScene;
    }

    public void setCurrentScene(int currentScene) {
        this.currentScene = currentScene;
    }

    public Scene getScene(int index) {
        return scenes[index];
    }

    public GlobalParameters getGlobalParameters() {
        return globalParameters;
    }

    public void setGlobalParameters(GlobalParameters globalParameters) {
        this.globalParameters = globalParameters;
    }

    public void setScene(int index, Scene scene) {
        scenes[index] = scene;
    }

    public Scene[] getScenes() {
        return scenes;
    }

    public void setScenes(Scene[] scenes) {
        this.scenes = scenes;
    }
}
