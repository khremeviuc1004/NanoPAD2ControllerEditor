/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.com.pwcu.nanopad2controller.job;

import au.com.pwcu.nanopad2controller.events.SceneDataDumpReceivedEvent;
import au.com.pwcu.nanopad2controller.midisystem.actions.SysexResponseAction;
import com.google.common.eventbus.EventBus;
import java.util.Map;
import java.util.HashMap;

/**
 *
 * @author kevin
 */
public abstract class SysexJob {

    public enum Status {NEW, SENT, RESPONSE_RECEIVED, DONE, SYSEX_ERROR};
    public enum Operation {NONE, SCENE_CHANGE_REQUEST, CURRENT_SCENE_DUMP_REQUEST, SEND_SCENE_DUMP, WRITE_SCENE, GLOBAL_DATA_REQUEST, GLOBAL_DATA_SEND};
    public enum ResponseType {
        SCENE_CHANGE(0x4f), ACK(0x23), NAK(0x24), CURRENT_SCENE_DATA_DUMP(0x40), WRITE_COMPLETE(0x21), WRITE_ERROR(0x22),
        GLOBAL_DATA_DUMP(0x51);
        
        private final int value;

        private ResponseType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    };

    private Status status = Status.NEW;
    private Operation operation = Operation.NONE;
    private final Map<ResponseType, SysexResponseAction> responseActions = new HashMap<>();
    private final Map<ResponseType, SysexResponseAction> errorResponseActions = new HashMap<>();
    private int sceneNumber = 0;

    private final EventBus eventBus;
    
    public SysexJob(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    public SysexJob(int sceneNumber, EventBus eventBus) {
        this.sceneNumber = sceneNumber;
        this.eventBus = eventBus;
    }

    public void addResponseAction(ResponseType expectedResponseType, SysexResponseAction sysexResponseAction)
    {
        responseActions.put(expectedResponseType, sysexResponseAction);
    }

    public void addErrorResponseAction(ResponseType expectedResponseType, SysexResponseAction sysexResponseAction)
    {
        errorResponseActions.put(expectedResponseType, sysexResponseAction);
    }

    public Operation getOperation()
    {
        return operation;
    }

    public void setOperation(Operation value)
    {
        operation = value;
    }

    public Status getStatus()
    {
        return status;
    }

    public void setStatus(Status value)
    {
        status = value;
    }

    public void processSysexResponse(byte[] message)
    {
        //somehow determine the message type and then call handle response
        if(
                message[0] == (byte)0xf0
                && message[1] == (byte)0x42
                && message[2] == (byte)0x40
                && message[3] == (byte)0x00
                && message[4] == (byte)0x01
                && message[5] == (byte)0x12
                && message[6] == (byte)0x00
                )
        {
            switch((int)message[7])
            {
                case 0x00://native mode in/out request
                {
                    break;
                }
                case 0x1f://data dump request
                {
                    switch ((int)message[9])
                    {
                        case 0x0e://global data dump request
                        {
                            break;
                        }
                        case 0x10://current scene data dump request
                        {
                            break;
                        }
                        case 0x11://scene write request
                        {
                            break;
                        }
                        case 0x12://mode request
                        {
                            break;
                        }
                        case 0x14://scene change request
                        {
                            break;
                        }
                    }
                    break;
                }
                case 0x40://native mode in out
                {
                    break;
                }
                case 0x7f://data dump
                {
                    switch ((int)message[9])
                    {
                        case 0x40://current scene data dump
                        {
                            handleResponse(ResponseType.CURRENT_SCENE_DATA_DUMP, message);
                            eventBus.post(new SceneDataDumpReceivedEvent(sceneNumber, message));
                            break;
                        }
                        case 0x51://global data dump
                        {
                            handleResponse(ResponseType.GLOBAL_DATA_DUMP, message);
                            eventBus.post(new SceneDataDumpReceivedEvent(sceneNumber, message));
                            break;
                        }
                    }
                    break;
                }
                case 0x5f://data dump
                {
                    switch((int)message[8]) {
                        case 0x21://write complete
                        {
                            handleResponse(ResponseType.WRITE_COMPLETE, message);
                            break;
                        }
                        case 0x22://write error
                        {
                            handleResponse(ResponseType.WRITE_ERROR, message);
                            break;
                        }
                        case 0x23://ACK
                        {
                            handleResponse(ResponseType.ACK, message);
                            break;
                        }
                        case 0x24://NAK
                        {
                            handleResponse(ResponseType.NAK, message);
                            break;
                        }
                        case 0x4f://scene change
                        {
                            handleResponse(ResponseType.SCENE_CHANGE, message);
                            break;
                        }
                        case 0x42://mode data
                        {
                            break;
                        }
                    }
                    break;
                }
            }
        }
    }

    public int getSceneNumber()
    {
        return sceneNumber;
    }

    public void setSceneNumber(int value)
    {
        sceneNumber = value;
    }

    public abstract byte[] populateSysexMessage();

    public void handleResponse(ResponseType responseType, byte[] message)
    {
        SysexResponseAction action = responseActions.get(responseType);

        if(action != null)
        {
            action.performAction(message);
            responseActions.remove(responseType);
            if(responseActions.isEmpty())
            {
                setStatus(Status.DONE);
            }
        }
        else
        {
            action = errorResponseActions.get(responseType);
            if(action != null)
            {
                action.performAction(message);
            }

            setStatus(Status.SYSEX_ERROR);
        }
    }
}
