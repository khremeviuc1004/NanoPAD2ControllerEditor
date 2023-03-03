/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.com.pwcu.nanopad2controller.midisystem.actions;

/**
 *
 * @author kevin
 */
public interface SysexResponseAction {
    void performAction(byte[] message);
}
