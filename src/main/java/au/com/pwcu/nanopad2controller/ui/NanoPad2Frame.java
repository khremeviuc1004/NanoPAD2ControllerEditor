/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.com.pwcu.nanopad2controller.ui;

import au.com.pwcu.nanopad2controller.appn.Preferences;
import au.com.pwcu.nanopad2controller.domain.*;
import au.com.pwcu.nanopad2controller.events.GlobalDataDumpReceivedEvent;
import au.com.pwcu.nanopad2controller.events.RequestSceneDataEvent;
import au.com.pwcu.nanopad2controller.events.RequestSceneSetEvent;
import au.com.pwcu.nanopad2controller.events.SceneChangedEvent;
import au.com.pwcu.nanopad2controller.events.SceneDataDumpReceivedEvent;
import au.com.pwcu.nanopad2controller.events.SendGlobalDataToNanoPad2Event;
import au.com.pwcu.nanopad2controller.events.SendSceneDataToNanoPad2Event;
import au.com.pwcu.nanopad2controller.events.SendSceneSetEvent;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author kevin
 */
public class NanoPad2Frame extends javax.swing.JFrame {
    
    private final EventBus eventBus;
    private NanoPad2 nanoPad2;
    private Preferences preferences;
    private File currentNanoPad2File = null;
    private String[] notes = new String[]{
        "0   C-1",
        "1   C#Db-1",
        "2   D-1",
        "3   D#Eb-1",
        "4   E-1",
        "5   F-1",
        "6   F#Gb-1",
        "7   G-1",
        "8   G#Ab-1",
        "9   A-1",
        "10  A#Bb-1",
        "11  B-1",
        "12  C0",
        "13  C#Db0",
        "14  D0",
        "15  D#Eb0",
        "16  E0",
        "17  F0",
        "18  F#Gb0",
        "19  G0",
        "20  G#Ab0",
        "21  A0",
        "22  A#Bb0",
        "23  B0",
        "24  C1",
        "25  C#Db1",
        "26  D1",
        "27  D#Eb1",
        "28  E1",
        "29  F1",
        "30  F#Gb1",
        "31  G1",
        "32  G#Ab1",
        "33  A1",
        "34  A#Bb1",
        "35  B1",
        "36  C2",
        "37  C#Db2",
        "38  D2",
        "39  D#Eb2",
        "40  E2",
        "41  F2",
        "42  F#Gb2",
        "43  G2",
        "44  G#Ab2",
        "45  A2",
        "46  A#Bb2",
        "47  B2",
        "48  C3",
        "49  C#Db3",
        "50  D3",
        "51  D#Eb3",
        "52  E3",
        "53  F3",
        "54  F#Gb3",
        "55  G3",
        "56  G#Ab3",
        "57  A3",
        "58  A#Bb3",
        "59  B3",
        "60  C4",
        "61  C#Db4",
        "62  D4",
        "63  D#Eb4",
        "64  E4",
        "65  F4",
        "66  F#Gb4",
        "67  G4",
        "68  G#Ab4",
        "69  A4",
        "70  A#Bb4",
        "71  B4",
        "72  C5",
        "73  C#Db5",
        "74  D5",
        "75  D#Eb5",
        "76  E5",
        "77  F5",
        "78  F#Gb5",
        "79  G5",
        "80  G#Ab5",
        "81  A5",
        "82  A#Bb5",
        "83  B5",
        "84  C6",
        "85  C#Db6",
        "86  D6",
        "87  D#Eb6",
        "88  E6",
        "89  F6",
        "90  F#Gb6",
        "91  G6",
        "92  G#Ab6",
        "93  A6",
        "94  A#Bb6",
        "95  B6",
        "96  C7",
        "97  C#Db7",
        "98  D7",
        "99  D#Eb7",
        "100 E7",
        "101 F7",
        "102 F#Gb7",
        "103 G7",
        "104 G#Ab7",
        "105 A7",
        "106 A#Bb7",
        "107 B7",
        "108 C8",
        "109 C#Db8",
        "110 D8",
        "111 D#Eb8",
        "112 E8",
        "113 F8",
        "114 F#Gb8",
        "115 G8",
        "116 G#Ab8",
        "117 A8",
        "118 A#Bb8",
        "119 B8",
        "120 C9",
        "121 C#Db9",
        "122 D9",
        "123 D#Eb9",
        "124 E9",
        "125 F9",
        "126 F#Gb9",
        "127 G9",
        "---"
    };
    private JFileChooser sceneSetFileChooser = new JFileChooser(System.getProperty("user.home"));
    JFileChooser sceneFileChooser = new JFileChooser(System.getProperty("user.home"));
    



    /**
     * Creates new form NanoPad2Frame
     * @param eventBus
     * @param nanoPad2
     */
    public NanoPad2Frame(EventBus eventBus, NanoPad2 nanoPad2, Preferences preferences) {
        initComponents();
        this.eventBus = eventBus;
        this.nanoPad2 = nanoPad2;
        this.preferences = preferences;
        sceneSetFileChooser.setFileFilter(new FileNameExtensionFilter("NanoPAD2", "np2"));
        sceneFileChooser.setFileFilter(new FileNameExtensionFilter("NanoPAD2 scene", "scn"));
    }
    
    @Subscribe
    public void handleSceneDataDumpReceived(SceneDataDumpReceivedEvent event) {
        Logger.getLogger(NanoPad2Frame.class.getName()).log(Level.INFO, "SceneDataDumpReceivedEvent received");
        int sceneNumber = event.getSceneNumber() - 1;
        if( sceneNumber == nanoPad2.getCurrentScene() ) {
            updateCurrentTriggerPad();
            updateNanoPad2DisplayPanel();
        }
    }
    
    @Subscribe
    public void handleGlobalDataDumpReceived(GlobalDataDumpReceivedEvent event) {
        Logger.getLogger(NanoPad2Frame.class.getName()).log(Level.INFO, "GlobalDataDumpReceivedEvent received");
        updateGlobalData();
    }
    
    private void updateGlobalData() {
        GlobalParameters globalParameters = nanoPad2.getGlobalParameters();
        
        globalCommonBpmSpinCtrl.setValue(globalParameters.getBpm());
        globalCommonConstantVelocityValueSpinCtrl.setValue(globalParameters.getConstantVelocityValue());
        globalCommonMidiChannelChoice.setSelectedIndex(globalParameters.getGlobalMidiChannel());
        globalCommonMidiClockChoice.setSelectedIndex(globalParameters.getMidiClock().getVal());
        globalCommonVelocityCurveChoice.setSelectedIndex(globalParameters.getVelocityCurve().getVal());
        globalXYPadCCModeMidiChannelChoice.setSelectedIndex(globalParameters.getXyPadCCModeMidiChannel());
        globalXYPadCCModeOffValueSpinCtrl.setValue(globalParameters.getXyPadCCModeTouchOffValue());
        globalXYPadCCModeOnValueSpinCtrl.setValue(globalParameters.getXyPadCCModeTouchOnValue());
        globalXYPadCCModeTouchEnableChoice.setSelectedIndex(globalParameters.isXyPadCCModeTouchEnabled() ? 1 : 0);
        globalXYPadCCModeTouchEnableCCNumberSpinCtrl.setValue(globalParameters.getXyPadCCModeTouchCCNumber());
        globalXYPadCCModeXAssignTypeChoice.setSelectedIndex(globalParameters.getXyPadCCModeXAxisAssignType().getVal());
        globalXYPadCCModeXCCNumberSpinCtrl.setValue(globalParameters.getXyPadCCModeXAxisCCNumber());
        globalXYPadCCModeXPolarityChoice.setSelectedIndex(globalParameters.getXyPadCCModeXAxisPolarity().getVal());
        globalXYPadCCModeYAssignTypeChoice.setSelectedIndex(globalParameters.getXyPadCCModeYAxisAssignType().getVal());
        globalXYPadCCModeYCCNumberSpinCtrl.setValue(globalParameters.getXyPadCCModeYAxisCCNumber());
        globalXYPadCCModeYPolarityChoice.setSelectedIndex(globalParameters.getXyPadCCModeYAxisPolarity().getVal());
        globalXYPadTouchScaleGateSpeedChoice.setSelectedIndex(globalParameters.getXyPadTouchScaleGateSpeed());
        globalXYPadTouchScaleMidiChannelChoice.setSelectedIndex(globalParameters.getXyPadTouchScaleMidiChannel());
        globalXYPadTouchScaleNoteOnVelocitySpinCtrl.setValue(globalParameters.getXyPadTouchScaleNoteOnVelocity());
        globalXYPadTouchScaleYAxisCCEnableChoice.setSelectedIndex(globalParameters.isXyPadTouchScaleYAxisCCEnabled() ? 1 : 0);
        globalXYPadTouchScaleYAxisCCNumberSpinCtrl.setValue(globalParameters.getXyPadTouchScaleYAxisCCNumber());
        globalXYPadTouchScaleYAxisPolarityChoice.setSelectedIndex(globalParameters.getXyPadTouchScaleYAxisPolarity().getVal());
        userScaleNoteLengthSpinCtrl.setValue(globalParameters.getUserScaleLength());
        userScaleNoteOffset01Spinner.setValue(globalParameters.getNoteOffset01());
        userScaleNoteOffset02Spinner.setValue(globalParameters.getNoteOffset02());
        userScaleNoteOffset03Spinner.setValue(globalParameters.getNoteOffset03());
        userScaleNoteOffset04Spinner.setValue(globalParameters.getNoteOffset04());
        userScaleNoteOffset05Spinner.setValue(globalParameters.getNoteOffset05());
        userScaleNoteOffset06Spinner.setValue(globalParameters.getNoteOffset06());
        userScaleNoteOffset07Spinner.setValue(globalParameters.getNoteOffset07());
        userScaleNoteOffset08Spinner.setValue(globalParameters.getNoteOffset08());
        userScaleNoteOffset09Spinner.setValue(globalParameters.getNoteOffset09());
        userScaleNoteOffset10Spinner.setValue(globalParameters.getNoteOffset10());
        userScaleNoteOffset11Spinner.setValue(globalParameters.getNoteOffset11());
        userScaleNoteOffset12Spinner.setValue(globalParameters.getNoteOffset12());
    }

    private void updateCurrentTriggerPad() {
        Scene currentScene = nanoPad2.getScene(nanoPad2.getCurrentScene());
        int triggerPadNumber = controlsList.getSelectedIndex();
        TriggerPad currentTriggerPad = currentScene.getTriggerPad(triggerPadNumber);
        triggerPadMidiChoice.setSelectedIndex(currentTriggerPad.getMidiChannel());
        triggerPadGateArpEnableCheckBox.setSelected(currentTriggerPad.isGateArpEnabled());
        triggerPadAssignTypeChoice.setSelectedItem(currentTriggerPad.getAssignType().name());
        triggerPadTouchScaleGateArpEnableCheckBox.setSelected(currentTriggerPad.isTouchScaleGateArpEnable());
        triggerPadBehaviourCheckBox.setSelected(currentTriggerPad.getPadBehaviour() == TriggerPad.PadBehaviour.Momentary ? true : false);
        triggerPadNoteCCProgNumber1Choice.setSelectedIndex(currentTriggerPad.getNote1());
        triggerPadNoteCCProgNumber2Choice.setSelectedIndex(currentTriggerPad.getNote2());
        triggerPadNoteCCProgNumber3Choice.setSelectedIndex(currentTriggerPad.getNote3());
        triggerPadNoteCCProgNumber4Choice.setSelectedIndex(currentTriggerPad.getNote4());
    }
    
    private void updateNanoPad2DisplayPanel() {
        Scene currentScene = nanoPad2.getScene(nanoPad2.getCurrentScene());
        for(TriggerPad triggerPad : currentScene.getTriggerPads()) {
            switch(triggerPad.getName()) {
                case "Pad 01":
                    updateTriggerPadDisplay(triggerPad01Button, triggerPad);
                    break;
                case "Pad 02":
                    updateTriggerPadDisplay(triggerPad02Button, triggerPad);
                    break;
                case "Pad 03":
                    updateTriggerPadDisplay(triggerPad03Button, triggerPad);
                    break;
                case "Pad 04":
                    updateTriggerPadDisplay(triggerPad04Button, triggerPad);
                    break;
                case "Pad 05":
                    updateTriggerPadDisplay(triggerPad05Button, triggerPad);
                    break;
                case "Pad 06":
                    updateTriggerPadDisplay(triggerPad06Button, triggerPad);
                    break;
                case "Pad 07":
                    updateTriggerPadDisplay(triggerPad07Button, triggerPad);
                    break;
                case "Pad 08":
                    updateTriggerPadDisplay(triggerPad08Button, triggerPad);
                    break;
                case "Pad 09":
                    updateTriggerPadDisplay(triggerPad09Button, triggerPad);
                    break;
                case "Pad 10":
                    updateTriggerPadDisplay(triggerPad10Button, triggerPad);
                    break;
                case "Pad 11":
                    updateTriggerPadDisplay(triggerPad11Button, triggerPad);
                    break;
                case "Pad 12":
                    updateTriggerPadDisplay(triggerPad12Button, triggerPad);
                    break;
                case "Pad 13":
                    updateTriggerPadDisplay(triggerPad13Button, triggerPad);
                    break;
                case "Pad 14":
                    updateTriggerPadDisplay(triggerPad14Button, triggerPad);
                    break;
                case "Pad 15":
                    updateTriggerPadDisplay(triggerPad15Button, triggerPad);
                    break;
                case "Pad 16":
                    updateTriggerPadDisplay(triggerPad16Button, triggerPad);
                    break;
            }
        }
    }
    
    private void updateTriggerPadDisplay(JButton triggerPadDisplay, TriggerPad triggerPad) {
        switch((String)parameterDisplayChoice.getSelectedItem()) {
            case "MIDI Channel":
                triggerPadDisplay.setText("<html>Midi Ch:<br />" + triggerPad.getMidiChannel() + "</html>");
                break;
            case "Assign Type":
                triggerPadDisplay.setText("<html>Assign Type:<br />" + triggerPad.getAssignType().name() + "</html>");
                break;
            case "Pad Behaviour":
                triggerPadDisplay.setText("<html>Pad Behaviour:<br />" + triggerPad.getPadBehaviour().name() + "</html>");
                break;
            case "Note/CC/Prog Number 1":
                triggerPadDisplay.setText("<html>Note CC Prog number 1:<br />" + notes[triggerPad.getNote1()] + "</html>");
                break;
            case "Note/CC/Prog Number 2":
                triggerPadDisplay.setText("<html>Note CC Prog number 2:<br />" + notes[triggerPad.getNote2()] + "</html>");
                break;
            case "Note/CC/Prog Number 3":
                triggerPadDisplay.setText("<html>Note CC Prog number 3:<br />" + notes[triggerPad.getNote3()] + "</html>");
                break;
            case "Note/CC/Prog Number 4":
                triggerPadDisplay.setText("<html>Note CC Prog number 4:<br />" + notes[triggerPad.getNote4()] + "</html>");
                break;
            case "Gate Arp Enable":
                triggerPadDisplay.setText("<html>Gate Arp:<br />" + (triggerPad.isGateArpEnabled() ? "Enabled" : "Disabled") + "</html>");
                break;
            case "Touch Scale Gate Arp Enable":
                triggerPadDisplay.setText("<html>Touch Scale Gate Arp:<br />" + (triggerPad.isTouchScaleGateArpEnable() ? "Enabled" : "Disabled") + "</html>");
                break;
        }
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        sceneSelectorButtonGroup = new javax.swing.ButtonGroup();
        sceneButtonPanel = new javax.swing.JPanel();
        scene1RadioButton = new javax.swing.JRadioButton();
        scene2RadioButton = new javax.swing.JRadioButton();
        scene3RadioButton = new javax.swing.JRadioButton();
        scene4RadioButton = new javax.swing.JRadioButton();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 0));
        nanopad2DisplayPanel = new javax.swing.JPanel();
        displaySelectionPanel = new javax.swing.JPanel();
        parameterDisplayChoice = new javax.swing.JComboBox<>();
        filler2 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 0));
        nanopad2InstrumentPanel = new javax.swing.JPanel();
        touchSurfacePanel = new javax.swing.JPanel();
        triggerPadsPanel = new javax.swing.JPanel();
        triggerPad01Button = new javax.swing.JButton();
        triggerPad02Button = new javax.swing.JButton();
        triggerPad03Button = new javax.swing.JButton();
        triggerPad04Button = new javax.swing.JButton();
        triggerPad05Button = new javax.swing.JButton();
        triggerPad06Button = new javax.swing.JButton();
        triggerPad07Button = new javax.swing.JButton();
        triggerPad08Button = new javax.swing.JButton();
        triggerPad09Button = new javax.swing.JButton();
        triggerPad10Button = new javax.swing.JButton();
        triggerPad11Button = new javax.swing.JButton();
        triggerPad12Button = new javax.swing.JButton();
        triggerPad13Button = new javax.swing.JButton();
        triggerPad14Button = new javax.swing.JButton();
        triggerPad15Button = new javax.swing.JButton();
        triggerPad16Button = new javax.swing.JButton();
        parameterPanel = new javax.swing.JPanel();
        parameterTabbedPane = new javax.swing.JTabbedPane();
        browseTabPanel = new javax.swing.JPanel();
        browseFilesScrollPane = new javax.swing.JScrollPane();
        browseFilesChooser = new javax.swing.JFileChooser();
        controlParamsTabPanel = new javax.swing.JPanel();
        controlsScrollPane = new javax.swing.JScrollPane();
        controlsList = new javax.swing.JList<>();
        jPanel9 = new javax.swing.JPanel();
        triggerPadParameterGridSizerPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        triggerPadMidiChoice = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        triggerPadGateArpEnableCheckBox = new javax.swing.JCheckBox();
        jLabel3 = new javax.swing.JLabel();
        triggerPadAssignTypeChoice = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        triggerPadTouchScaleGateArpEnableCheckBox = new javax.swing.JCheckBox();
        jLabel5 = new javax.swing.JLabel();
        triggerPadBehaviourCheckBox = new javax.swing.JCheckBox();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        triggerPadNoteCCProgNumber1Choice = new javax.swing.JComboBox<>();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        triggerPadNoteCCProgNumber2Choice = new javax.swing.JComboBox<>();
        jPanel5 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        triggerPadNoteCCProgNumber3Choice = new javax.swing.JComboBox<>();
        jPanel7 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        triggerPadNoteCCProgNumber4Choice = new javax.swing.JComboBox<>();
        filler3 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 200), new java.awt.Dimension(0, 300), new java.awt.Dimension(32767, 200));
        filler5 = new javax.swing.Box.Filler(new java.awt.Dimension(100, 0), new java.awt.Dimension(200, 0), new java.awt.Dimension(100, 32767));
        globalParamsTabPanel = new javax.swing.JPanel();
        globalParamsTabbedPane = new javax.swing.JTabbedPane();
        globalCommonParamsPanel = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        globalMidiChannelLabel = new javax.swing.JLabel();
        globalCommonMidiChannelChoice = new javax.swing.JComboBox<>();
        velocityCurveLabel = new javax.swing.JLabel();
        globalCommonVelocityCurveChoice = new javax.swing.JComboBox<>();
        constantVelocityValueLabel = new javax.swing.JLabel();
        globalCommonConstantVelocityValueSpinCtrl = new javax.swing.JSpinner();
        bpmLabel = new javax.swing.JLabel();
        globalCommonBpmSpinCtrl = new javax.swing.JSpinner();
        midiClockLabel = new javax.swing.JLabel();
        globalCommonMidiClockChoice = new javax.swing.JComboBox<>();
        filler4 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 400), new java.awt.Dimension(0, 400), new java.awt.Dimension(0, 32767));
        filler6 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(400, 0), new java.awt.Dimension(32767, 0));
        globalXYPadParamsPanel = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        xyPadMidiChannelLabel = new javax.swing.JLabel();
        globalXYPadCCModeMidiChannelChoice = new javax.swing.JComboBox<>();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        globalXYPadCCModeXAssignTypeLabel = new javax.swing.JLabel();
        globalXYPadCCModeXAssignTypeChoice = new javax.swing.JComboBox<>();
        yAssignTypeLabel = new javax.swing.JLabel();
        globalXYPadCCModeYAssignTypeChoice = new javax.swing.JComboBox<>();
        xCCNumberLabel = new javax.swing.JLabel();
        globalXYPadCCModeXCCNumberSpinCtrl = new javax.swing.JSpinner();
        yCCNumberLabel = new javax.swing.JLabel();
        globalXYPadCCModeYCCNumberSpinCtrl = new javax.swing.JSpinner();
        xPolarityLabel = new javax.swing.JLabel();
        globalXYPadCCModeXPolarityChoice = new javax.swing.JComboBox<>();
        yPolarityLabel = new javax.swing.JLabel();
        globalXYPadCCModeYPolarityChoice = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        touchEnableLabel = new javax.swing.JLabel();
        globalXYPadCCModeTouchEnableChoice = new javax.swing.JComboBox<>();
        onValueLabel = new javax.swing.JLabel();
        globalXYPadCCModeOnValueSpinCtrl = new javax.swing.JSpinner();
        touchEnableOffValueLabel = new javax.swing.JLabel();
        globalXYPadCCModeTouchEnableCCNumberSpinCtrl = new javax.swing.JSpinner();
        offValueLabel = new javax.swing.JLabel();
        globalXYPadCCModeOffValueSpinCtrl = new javax.swing.JSpinner();
        filler8 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 400), new java.awt.Dimension(0, 400), new java.awt.Dimension(0, 32767));
        filler7 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 0));
        globalTouchScaleParamsPanel = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        jPanel15 = new javax.swing.JPanel();
        midiChannelLabel = new javax.swing.JLabel();
        globalXYPadTouchScaleMidiChannelChoice = new javax.swing.JComboBox<>();
        noteOnVelocityLabel = new javax.swing.JLabel();
        globalXYPadTouchScaleNoteOnVelocitySpinCtrl = new javax.swing.JSpinner();
        yAxisCCEnableLabel = new javax.swing.JLabel();
        globalXYPadTouchScaleYAxisCCEnableChoice = new javax.swing.JComboBox<>();
        yAxisCCNumberLabel = new javax.swing.JLabel();
        globalXYPadTouchScaleYAxisCCNumberSpinCtrl = new javax.swing.JSpinner();
        yAxisPolarityLabel = new javax.swing.JLabel();
        globalXYPadTouchScaleYAxisPolarityChoice = new javax.swing.JComboBox<>();
        gateSpeedLabel = new javax.swing.JLabel();
        globalXYPadTouchScaleGateSpeedChoice = new javax.swing.JComboBox<>();
        filler10 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 400), new java.awt.Dimension(0, 400), new java.awt.Dimension(0, 32767));
        filler9 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(400, 0), new java.awt.Dimension(32767, 0));
        globalUserScaleParamsPanel = new javax.swing.JPanel();
        jPanel16 = new javax.swing.JPanel();
        jPanel17 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        userScaleNoteLengthSpinCtrl = new javax.swing.JSpinner();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        userScaleNoteOffset01Spinner = new javax.swing.JSpinner();
        jLabel20 = new javax.swing.JLabel();
        userScaleNoteOffset07Spinner = new javax.swing.JSpinner();
        jLabel21 = new javax.swing.JLabel();
        userScaleNoteOffset02Spinner = new javax.swing.JSpinner();
        jLabel22 = new javax.swing.JLabel();
        userScaleNoteOffset08Spinner = new javax.swing.JSpinner();
        jLabel23 = new javax.swing.JLabel();
        userScaleNoteOffset03Spinner = new javax.swing.JSpinner();
        jLabel24 = new javax.swing.JLabel();
        userScaleNoteOffset09Spinner = new javax.swing.JSpinner();
        jLabel25 = new javax.swing.JLabel();
        userScaleNoteOffset04Spinner = new javax.swing.JSpinner();
        jLabel26 = new javax.swing.JLabel();
        userScaleNoteOffset10Spinner = new javax.swing.JSpinner();
        jLabel27 = new javax.swing.JLabel();
        userScaleNoteOffset05Spinner = new javax.swing.JSpinner();
        jLabel28 = new javax.swing.JLabel();
        userScaleNoteOffset11Spinner = new javax.swing.JSpinner();
        jLabel29 = new javax.swing.JLabel();
        userScaleNoteOffset06Spinner = new javax.swing.JSpinner();
        jLabel30 = new javax.swing.JLabel();
        userScaleNoteOffset12Spinner = new javax.swing.JSpinner();
        filler12 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 400), new java.awt.Dimension(0, 32767));
        filler11 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(400, 0), new java.awt.Dimension(32767, 0));
        preferencesTabPanel = new javax.swing.JPanel();
        midiPortsPanel = new javax.swing.JPanel();
        manualMidiPortSelectionCheckBox = new javax.swing.JCheckBox();
        jLabel31 = new javax.swing.JLabel();
        midiInLabel = new javax.swing.JLabel();
        midiInChoice = new javax.swing.JComboBox<>();
        midiOutLabel = new javax.swing.JLabel();
        midiOutChoice = new javax.swing.JComboBox<>();
        miscellaneousPanel = new javax.swing.JPanel();
        jPanel18 = new javax.swing.JPanel();
        receiveSceneSetAutomaticallyCheckBox = new javax.swing.JCheckBox();
        warnBeforeWritingCheckBox = new javax.swing.JCheckBox();
        filler13 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 300), new java.awt.Dimension(0, 32767));
        menuBar = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        selectTargetDeviceMenuItem = new javax.swing.JMenuItem();
        newMenuItem = new javax.swing.JMenuItem();
        openMenuItem = new javax.swing.JMenuItem();
        saveMenuItem = new javax.swing.JMenuItem();
        saveasMenuItem = new javax.swing.JMenuItem();
        loadSceneDataMenuItem = new javax.swing.JMenuItem();
        saveSceneDataMenuItem = new javax.swing.JMenuItem();
        editMenu = new javax.swing.JMenu();
        undoMenuItem = new javax.swing.JMenuItem();
        redoMenuItem = new javax.swing.JMenuItem();
        cutMenuItem = new javax.swing.JMenuItem();
        copyMenuItem = new javax.swing.JMenuItem();
        pasteMenuItem = new javax.swing.JMenuItem();
        clearMenuItem = new javax.swing.JMenuItem();
        communicateMenu = new javax.swing.JMenu();
        receiveSceneSetMenuItem = new javax.swing.JMenuItem();
        writeSceneSetMenuItem = new javax.swing.JMenuItem();
        receiveSceneDataMenuItem = new javax.swing.JMenuItem();
        writeSceneDataMenuItem = new javax.swing.JMenuItem();
        sendGlobalDataMenuItem = new javax.swing.JMenuItem();
        helpMenu = new javax.swing.JMenu();
        aboutMenuItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("NanoPAD2 Controller");
        setMinimumSize(new java.awt.Dimension(800, 600));
        setPreferredSize(new java.awt.Dimension(1200, 1000));
        getContentPane().setLayout(new javax.swing.BoxLayout(getContentPane(), javax.swing.BoxLayout.PAGE_AXIS));

        sceneButtonPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Scene"));
        sceneButtonPanel.setLayout(new javax.swing.BoxLayout(sceneButtonPanel, javax.swing.BoxLayout.LINE_AXIS));

        sceneSelectorButtonGroup.add(scene1RadioButton);
        scene1RadioButton.setSelected(true);
        scene1RadioButton.setLabel("Scene 1");
        scene1RadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                scene1RadioButtonActionPerformed(evt);
            }
        });
        sceneButtonPanel.add(scene1RadioButton);

        sceneSelectorButtonGroup.add(scene2RadioButton);
        scene2RadioButton.setLabel("Scene 2");
        scene2RadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                scene2RadioButtonActionPerformed(evt);
            }
        });
        sceneButtonPanel.add(scene2RadioButton);

        sceneSelectorButtonGroup.add(scene3RadioButton);
        scene3RadioButton.setLabel("Scene 3");
        scene3RadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                scene3RadioButtonActionPerformed(evt);
            }
        });
        sceneButtonPanel.add(scene3RadioButton);

        sceneSelectorButtonGroup.add(scene4RadioButton);
        scene4RadioButton.setLabel("Scene 4");
        scene4RadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                scene4RadioButtonActionPerformed(evt);
            }
        });
        sceneButtonPanel.add(scene4RadioButton);
        sceneButtonPanel.add(filler1);

        getContentPane().add(sceneButtonPanel);

        nanopad2DisplayPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("NanoPAD 2"));
        nanopad2DisplayPanel.setLayout(new javax.swing.BoxLayout(nanopad2DisplayPanel, javax.swing.BoxLayout.PAGE_AXIS));

        displaySelectionPanel.setLayout(new javax.swing.BoxLayout(displaySelectionPanel, javax.swing.BoxLayout.LINE_AXIS));

        parameterDisplayChoice.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "MIDI Channel", "Assign Type", "Pad Behaviour", "Note/CC/Prog Number 1", "Note/CC/Prog Number 2", "Note/CC/Prog Number 3", "Note/CC/Prog Number 4", "Gate Arp Enable", "Touch Scale Gate Arp Enable" }));
        parameterDisplayChoice.setSelectedIndex(3);
        parameterDisplayChoice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                parameterDisplayChoiceActionPerformed(evt);
            }
        });
        displaySelectionPanel.add(parameterDisplayChoice);
        displaySelectionPanel.add(filler2);

        nanopad2DisplayPanel.add(displaySelectionPanel);

        nanopad2InstrumentPanel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        nanopad2InstrumentPanel.setLayout(new javax.swing.BoxLayout(nanopad2InstrumentPanel, javax.swing.BoxLayout.LINE_AXIS));

        touchSurfacePanel.setBackground(java.awt.Color.black);
        touchSurfacePanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        touchSurfacePanel.setMaximumSize(new java.awt.Dimension(200, 170));
        touchSurfacePanel.setMinimumSize(new java.awt.Dimension(200, 170));
        touchSurfacePanel.setPreferredSize(new java.awt.Dimension(200, 170));

        javax.swing.GroupLayout touchSurfacePanelLayout = new javax.swing.GroupLayout(touchSurfacePanel);
        touchSurfacePanel.setLayout(touchSurfacePanelLayout);
        touchSurfacePanelLayout.setHorizontalGroup(
            touchSurfacePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 180, Short.MAX_VALUE)
        );
        touchSurfacePanelLayout.setVerticalGroup(
            touchSurfacePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 150, Short.MAX_VALUE)
        );

        nanopad2InstrumentPanel.add(touchSurfacePanel);

        triggerPadsPanel.setMaximumSize(new java.awt.Dimension(32767, 170));
        triggerPadsPanel.setLayout(new java.awt.GridLayout(2, 8, 5, 5));

        triggerPad01Button.setText("Pad 01");
        triggerPadsPanel.add(triggerPad01Button);

        triggerPad02Button.setText("Pad 02");
        triggerPadsPanel.add(triggerPad02Button);

        triggerPad03Button.setText("Pad 03");
        triggerPadsPanel.add(triggerPad03Button);

        triggerPad04Button.setText("Pad 04");
        triggerPadsPanel.add(triggerPad04Button);

        triggerPad05Button.setText("Pad 05");
        triggerPadsPanel.add(triggerPad05Button);

        triggerPad06Button.setText("Pad 06");
        triggerPadsPanel.add(triggerPad06Button);

        triggerPad07Button.setText("Pad 07");
        triggerPadsPanel.add(triggerPad07Button);

        triggerPad08Button.setText("Pad 08");
        triggerPadsPanel.add(triggerPad08Button);

        triggerPad09Button.setText("Pad 09");
        triggerPadsPanel.add(triggerPad09Button);

        triggerPad10Button.setText("Pad 10");
        triggerPadsPanel.add(triggerPad10Button);

        triggerPad11Button.setText("Pad 11");
        triggerPadsPanel.add(triggerPad11Button);

        triggerPad12Button.setText("Pad 12");
        triggerPadsPanel.add(triggerPad12Button);

        triggerPad13Button.setText("Pad 13");
        triggerPadsPanel.add(triggerPad13Button);

        triggerPad14Button.setText("Pad 14");
        triggerPadsPanel.add(triggerPad14Button);

        triggerPad15Button.setText("Pad 15");
        triggerPadsPanel.add(triggerPad15Button);

        triggerPad16Button.setText("Pad 16");
        triggerPadsPanel.add(triggerPad16Button);

        nanopad2InstrumentPanel.add(triggerPadsPanel);

        nanopad2DisplayPanel.add(nanopad2InstrumentPanel);

        getContentPane().add(nanopad2DisplayPanel);

        parameterPanel.setLayout(new java.awt.BorderLayout());

        browseTabPanel.setLayout(new java.awt.BorderLayout());

        browseFilesScrollPane.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        browseFilesScrollPane.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        browseFilesScrollPane.setViewportView(browseFilesChooser);

        browseFilesChooser.setAcceptAllFileFilterUsed(false);
        browseFilesChooser.setControlButtonsAreShown(false);
        browseFilesScrollPane.setViewportView(browseFilesChooser);

        browseTabPanel.add(browseFilesScrollPane, java.awt.BorderLayout.CENTER);

        parameterTabbedPane.addTab("Browse", browseTabPanel);

        controlParamsTabPanel.setLayout(new javax.swing.BoxLayout(controlParamsTabPanel, javax.swing.BoxLayout.LINE_AXIS));

        controlsScrollPane.setMaximumSize(new java.awt.Dimension(150, 32767));
        controlsScrollPane.setMinimumSize(new java.awt.Dimension(150, 22));
        controlsScrollPane.setPreferredSize(new java.awt.Dimension(150, 188));

        controlsList.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Trigger Pad 1", "Trigger Pad 2", "Trigger Pad 3", "Trigger Pad 4", "Trigger Pad 5", "Trigger Pad 6", "Trigger Pad 7", "Trigger Pad 8", "Trigger Pad 9", "Trigger Pad 10", "Trigger Pad 11", "Trigger Pad 12", "Trigger Pad 13", "Trigger Pad 14", "Trigger Pad 15", "Trigger Pad 16" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        controlsList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        controlsList.setMaximumSize(new java.awt.Dimension(200, 368));
        controlsList.setMinimumSize(new java.awt.Dimension(200, 368));
        controlsList.setPreferredSize(new java.awt.Dimension(200, 368));
        controlsList.setSelectedIndex(0);
        controlsList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                controlsListValueChanged(evt);
            }
        });
        controlsScrollPane.setViewportView(controlsList);

        controlParamsTabPanel.add(controlsScrollPane);

        jPanel9.setLayout(new javax.swing.BoxLayout(jPanel9, javax.swing.BoxLayout.PAGE_AXIS));

        triggerPadParameterGridSizerPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(20, 20, 20, 20));
        triggerPadParameterGridSizerPanel.setLayout(new java.awt.GridLayout(0, 4, 10, 10));

        jLabel1.setText("Midi Channel");
        triggerPadParameterGridSizerPanel.add(jLabel1);

        triggerPadMidiChoice.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "Global" }));
        triggerPadMidiChoice.setSelectedIndex(16);
        triggerPadMidiChoice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                triggerPadMidiChoiceActionPerformed(evt);
            }
        });
        triggerPadParameterGridSizerPanel.add(triggerPadMidiChoice);

        jLabel2.setText("Gate Arp Enable");
        triggerPadParameterGridSizerPanel.add(jLabel2);

        triggerPadGateArpEnableCheckBox.setSelected(true);
        triggerPadGateArpEnableCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                triggerPadGateArpEnableCheckBoxActionPerformed(evt);
            }
        });
        triggerPadParameterGridSizerPanel.add(triggerPadGateArpEnableCheckBox);

        jLabel3.setText("Assign Type");
        triggerPadParameterGridSizerPanel.add(jLabel3);

        triggerPadAssignTypeChoice.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "NoAssign", "ControlChange", "Note", "ProgramChange" }));
        triggerPadAssignTypeChoice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                triggerPadAssignTypeChoiceActionPerformed(evt);
            }
        });
        triggerPadParameterGridSizerPanel.add(triggerPadAssignTypeChoice);

        jLabel4.setText("Touch Scale Gate Arp Enable");
        triggerPadParameterGridSizerPanel.add(jLabel4);

        triggerPadTouchScaleGateArpEnableCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                triggerPadTouchScaleGateArpEnableCheckBoxActionPerformed(evt);
            }
        });
        triggerPadParameterGridSizerPanel.add(triggerPadTouchScaleGateArpEnableCheckBox);

        jLabel5.setText("Pad Behaviour");
        triggerPadParameterGridSizerPanel.add(jLabel5);

        triggerPadBehaviourCheckBox.setSelected(true);
        triggerPadBehaviourCheckBox.setText("Momentary/Toggle");
        triggerPadBehaviourCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                triggerPadBehaviourCheckBoxActionPerformed(evt);
            }
        });
        triggerPadParameterGridSizerPanel.add(triggerPadBehaviourCheckBox);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 182, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 37, Short.MAX_VALUE)
        );

        triggerPadParameterGridSizerPanel.add(jPanel1);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 182, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 37, Short.MAX_VALUE)
        );

        triggerPadParameterGridSizerPanel.add(jPanel2);

        jLabel6.setText("Note/CC/Prog number 1");
        triggerPadParameterGridSizerPanel.add(jLabel6);

        triggerPadNoteCCProgNumber1Choice.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "0   C-1", "1   C#Db-1", "2   D-1", "3   D#Eb-1", "4   E-1", "5   F-1", "6   F#Gb-1", "7   G-1", "8   G#Ab-1", "9   A-1", "10  A#Bb-1", "11  B-1", "12  C0", "13  C#Db0", "14  D0", "15  D#Eb0", "16  E0", "17  F0", "18  F#Gb0", "19  G0", "20  G#Ab0", "21  A0", "22  A#Bb0", "23  B0", "24  C1", "25  C#Db1", "26  D1", "27  D#Eb1", "28  E1", "29  F1", "30  F#Gb1", "31  G1", "32  G#Ab1", "33  A1", "34  A#Bb1", "35  B1", "36  C2", "37  C#Db2", "38  D2", "39  D#Eb2", "40  E2", "41  F2", "42  F#Gb2", "43  G2", "44  G#Ab2", "45  A2", "46  A#Bb2", "47  B2", "48  C3", "49  C#Db3", "50  D3", "51  D#Eb3", "52  E3", "53  F3", "54  F#Gb3", "55  G3", "56  G#Ab3", "57  A3", "58  A#Bb3", "59  B3", "60  C4", "61  C#Db4", "62  D4", "63  D#Eb4", "64  E4", "65  F4", "66  F#Gb4", "67  G4", "68  G#Ab4", "69  A4", "70  A#Bb4", "71  B4", "72  C5", "73  C#Db5", "74  D5", "75  D#Eb5", "76  E5", "77  F5", "78  F#Gb5", "79  G5", "80  G#Ab5", "81  A5", "82  A#Bb5", "83  B5", "84  C6", "85  C#Db6", "86  D6", "87  D#Eb6", "88  E6", "89  F6", "90  F#Gb6", "91  G6", "92  G#Ab6", "93  A6", "94  A#Bb6", "95  B6", "96  C7", "97  C#Db7", "98  D7", "99  D#Eb7", "100 E7", "101 F7", "102 F#Gb7", "103 G7", "104 G#Ab7", "105 A7", "106 A#Bb7", "107 B7", "108 C8", "109 C#Db8", "110 D8", "111 D#Eb8", "112 E8", "113 F8", "114 F#Gb8", "115 G8", "116 G#Ab8", "117 A8", "118 A#Bb8", "119 B8", "120 C9", "121 C#Db9", "122 D9", "123 D#Eb9", "124 E9", "125 F9", "126 F#Gb9", "127 G9", "---" }));
        triggerPadNoteCCProgNumber1Choice.setSelectedIndex(60);
        triggerPadNoteCCProgNumber1Choice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                triggerPadNoteCCProgNumber1ChoiceActionPerformed(evt);
            }
        });
        triggerPadParameterGridSizerPanel.add(triggerPadNoteCCProgNumber1Choice);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 182, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 37, Short.MAX_VALUE)
        );

        triggerPadParameterGridSizerPanel.add(jPanel3);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 182, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 37, Short.MAX_VALUE)
        );

        triggerPadParameterGridSizerPanel.add(jPanel4);

        jLabel7.setText("Note/CC/Prog number 2");
        triggerPadParameterGridSizerPanel.add(jLabel7);

        triggerPadNoteCCProgNumber2Choice.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "0   C-1", "1   C#Db-1", "2   D-1", "3   D#Eb-1", "4   E-1", "5   F-1", "6   F#Gb-1", "7   G-1", "8   G#Ab-1", "9   A-1", "10  A#Bb-1", "11  B-1", "12  C0", "13  C#Db0", "14  D0", "15  D#Eb0", "16  E0", "17  F0", "18  F#Gb0", "19  G0", "20  G#Ab0", "21  A0", "22  A#Bb0", "23  B0", "24  C1", "25  C#Db1", "26  D1", "27  D#Eb1", "28  E1", "29  F1", "30  F#Gb1", "31  G1", "32  G#Ab1", "33  A1", "34  A#Bb1", "35  B1", "36  C2", "37  C#Db2", "38  D2", "39  D#Eb2", "40  E2", "41  F2", "42  F#Gb2", "43  G2", "44  G#Ab2", "45  A2", "46  A#Bb2", "47  B2", "48  C3", "49  C#Db3", "50  D3", "51  D#Eb3", "52  E3", "53  F3", "54  F#Gb3", "55  G3", "56  G#Ab3", "57  A3", "58  A#Bb3", "59  B3", "60  C4", "61  C#Db4", "62  D4", "63  D#Eb4", "64  E4", "65  F4", "66  F#Gb4", "67  G4", "68  G#Ab4", "69  A4", "70  A#Bb4", "71  B4", "72  C5", "73  C#Db5", "74  D5", "75  D#Eb5", "76  E5", "77  F5", "78  F#Gb5", "79  G5", "80  G#Ab5", "81  A5", "82  A#Bb5", "83  B5", "84  C6", "85  C#Db6", "86  D6", "87  D#Eb6", "88  E6", "89  F6", "90  F#Gb6", "91  G6", "92  G#Ab6", "93  A6", "94  A#Bb6", "95  B6", "96  C7", "97  C#Db7", "98  D7", "99  D#Eb7", "100 E7", "101 F7", "102 F#Gb7", "103 G7", "104 G#Ab7", "105 A7", "106 A#Bb7", "107 B7", "108 C8", "109 C#Db8", "110 D8", "111 D#Eb8", "112 E8", "113 F8", "114 F#Gb8", "115 G8", "116 G#Ab8", "117 A8", "118 A#Bb8", "119 B8", "120 C9", "121 C#Db9", "122 D9", "123 D#Eb9", "124 E9", "125 F9", "126 F#Gb9", "127 G9", "---" }));
        triggerPadNoteCCProgNumber2Choice.setSelectedIndex(128);
        triggerPadNoteCCProgNumber2Choice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                triggerPadNoteCCProgNumber2ChoiceActionPerformed(evt);
            }
        });
        triggerPadParameterGridSizerPanel.add(triggerPadNoteCCProgNumber2Choice);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 182, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 37, Short.MAX_VALUE)
        );

        triggerPadParameterGridSizerPanel.add(jPanel5);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 182, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 37, Short.MAX_VALUE)
        );

        triggerPadParameterGridSizerPanel.add(jPanel6);

        jLabel8.setText("Note/CC/Prog number 3");
        triggerPadParameterGridSizerPanel.add(jLabel8);

        triggerPadNoteCCProgNumber3Choice.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "0   C-1", "1   C#Db-1", "2   D-1", "3   D#Eb-1", "4   E-1", "5   F-1", "6   F#Gb-1", "7   G-1", "8   G#Ab-1", "9   A-1", "10  A#Bb-1", "11  B-1", "12  C0", "13  C#Db0", "14  D0", "15  D#Eb0", "16  E0", "17  F0", "18  F#Gb0", "19  G0", "20  G#Ab0", "21  A0", "22  A#Bb0", "23  B0", "24  C1", "25  C#Db1", "26  D1", "27  D#Eb1", "28  E1", "29  F1", "30  F#Gb1", "31  G1", "32  G#Ab1", "33  A1", "34  A#Bb1", "35  B1", "36  C2", "37  C#Db2", "38  D2", "39  D#Eb2", "40  E2", "41  F2", "42  F#Gb2", "43  G2", "44  G#Ab2", "45  A2", "46  A#Bb2", "47  B2", "48  C3", "49  C#Db3", "50  D3", "51  D#Eb3", "52  E3", "53  F3", "54  F#Gb3", "55  G3", "56  G#Ab3", "57  A3", "58  A#Bb3", "59  B3", "60  C4", "61  C#Db4", "62  D4", "63  D#Eb4", "64  E4", "65  F4", "66  F#Gb4", "67  G4", "68  G#Ab4", "69  A4", "70  A#Bb4", "71  B4", "72  C5", "73  C#Db5", "74  D5", "75  D#Eb5", "76  E5", "77  F5", "78  F#Gb5", "79  G5", "80  G#Ab5", "81  A5", "82  A#Bb5", "83  B5", "84  C6", "85  C#Db6", "86  D6", "87  D#Eb6", "88  E6", "89  F6", "90  F#Gb6", "91  G6", "92  G#Ab6", "93  A6", "94  A#Bb6", "95  B6", "96  C7", "97  C#Db7", "98  D7", "99  D#Eb7", "100 E7", "101 F7", "102 F#Gb7", "103 G7", "104 G#Ab7", "105 A7", "106 A#Bb7", "107 B7", "108 C8", "109 C#Db8", "110 D8", "111 D#Eb8", "112 E8", "113 F8", "114 F#Gb8", "115 G8", "116 G#Ab8", "117 A8", "118 A#Bb8", "119 B8", "120 C9", "121 C#Db9", "122 D9", "123 D#Eb9", "124 E9", "125 F9", "126 F#Gb9", "127 G9", "---" }));
        triggerPadNoteCCProgNumber3Choice.setSelectedIndex(128);
        triggerPadNoteCCProgNumber3Choice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                triggerPadNoteCCProgNumber3ChoiceActionPerformed(evt);
            }
        });
        triggerPadParameterGridSizerPanel.add(triggerPadNoteCCProgNumber3Choice);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 182, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 37, Short.MAX_VALUE)
        );

        triggerPadParameterGridSizerPanel.add(jPanel7);

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 182, Short.MAX_VALUE)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 37, Short.MAX_VALUE)
        );

        triggerPadParameterGridSizerPanel.add(jPanel8);

        jLabel9.setText("Note/CC/Prog number 4");
        triggerPadParameterGridSizerPanel.add(jLabel9);

        triggerPadNoteCCProgNumber4Choice.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "0   C-1", "1   C#Db-1", "2   D-1", "3   D#Eb-1", "4   E-1", "5   F-1", "6   F#Gb-1", "7   G-1", "8   G#Ab-1", "9   A-1", "10  A#Bb-1", "11  B-1", "12  C0", "13  C#Db0", "14  D0", "15  D#Eb0", "16  E0", "17  F0", "18  F#Gb0", "19  G0", "20  G#Ab0", "21  A0", "22  A#Bb0", "23  B0", "24  C1", "25  C#Db1", "26  D1", "27  D#Eb1", "28  E1", "29  F1", "30  F#Gb1", "31  G1", "32  G#Ab1", "33  A1", "34  A#Bb1", "35  B1", "36  C2", "37  C#Db2", "38  D2", "39  D#Eb2", "40  E2", "41  F2", "42  F#Gb2", "43  G2", "44  G#Ab2", "45  A2", "46  A#Bb2", "47  B2", "48  C3", "49  C#Db3", "50  D3", "51  D#Eb3", "52  E3", "53  F3", "54  F#Gb3", "55  G3", "56  G#Ab3", "57  A3", "58  A#Bb3", "59  B3", "60  C4", "61  C#Db4", "62  D4", "63  D#Eb4", "64  E4", "65  F4", "66  F#Gb4", "67  G4", "68  G#Ab4", "69  A4", "70  A#Bb4", "71  B4", "72  C5", "73  C#Db5", "74  D5", "75  D#Eb5", "76  E5", "77  F5", "78  F#Gb5", "79  G5", "80  G#Ab5", "81  A5", "82  A#Bb5", "83  B5", "84  C6", "85  C#Db6", "86  D6", "87  D#Eb6", "88  E6", "89  F6", "90  F#Gb6", "91  G6", "92  G#Ab6", "93  A6", "94  A#Bb6", "95  B6", "96  C7", "97  C#Db7", "98  D7", "99  D#Eb7", "100 E7", "101 F7", "102 F#Gb7", "103 G7", "104 G#Ab7", "105 A7", "106 A#Bb7", "107 B7", "108 C8", "109 C#Db8", "110 D8", "111 D#Eb8", "112 E8", "113 F8", "114 F#Gb8", "115 G8", "116 G#Ab8", "117 A8", "118 A#Bb8", "119 B8", "120 C9", "121 C#Db9", "122 D9", "123 D#Eb9", "124 E9", "125 F9", "126 F#Gb9", "127 G9", "---" }));
        triggerPadNoteCCProgNumber4Choice.setSelectedIndex(128);
        triggerPadNoteCCProgNumber4Choice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                triggerPadNoteCCProgNumber4ChoiceActionPerformed(evt);
            }
        });
        triggerPadParameterGridSizerPanel.add(triggerPadNoteCCProgNumber4Choice);

        jPanel9.add(triggerPadParameterGridSizerPanel);
        jPanel9.add(filler3);

        controlParamsTabPanel.add(jPanel9);
        controlParamsTabPanel.add(filler5);

        parameterTabbedPane.addTab("Control", controlParamsTabPanel);

        globalParamsTabPanel.setLayout(new java.awt.BorderLayout());

        globalCommonParamsPanel.setLayout(new javax.swing.BoxLayout(globalCommonParamsPanel, javax.swing.BoxLayout.LINE_AXIS));

        jPanel10.setLayout(new javax.swing.BoxLayout(jPanel10, javax.swing.BoxLayout.PAGE_AXIS));

        jPanel11.setLayout(new java.awt.GridLayout(0, 2, 10, 10));

        globalMidiChannelLabel.setText("Global Midi Channel");
        jPanel11.add(globalMidiChannelLabel);

        globalCommonMidiChannelChoice.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16" }));
        globalCommonMidiChannelChoice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                globalCommonMidiChannelChoiceActionPerformed(evt);
            }
        });
        jPanel11.add(globalCommonMidiChannelChoice);

        velocityCurveLabel.setText("Velocity Curve");
        jPanel11.add(velocityCurveLabel);

        globalCommonVelocityCurveChoice.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Light", "Normal", "Heavy", "Constant" }));
        globalCommonVelocityCurveChoice.setSelectedIndex(1);
        globalCommonVelocityCurveChoice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                globalCommonVelocityCurveChoiceActionPerformed(evt);
            }
        });
        jPanel11.add(globalCommonVelocityCurveChoice);

        constantVelocityValueLabel.setText("Constant Velocity Value");
        jPanel11.add(constantVelocityValueLabel);

        globalCommonConstantVelocityValueSpinCtrl.setModel(new javax.swing.SpinnerNumberModel(100, 0, 127, 1));
        globalCommonConstantVelocityValueSpinCtrl.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                globalCommonConstantVelocityValueSpinCtrlAncestorAdded(evt);
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        jPanel11.add(globalCommonConstantVelocityValueSpinCtrl);

        bpmLabel.setText("BPM");
        jPanel11.add(bpmLabel);

        globalCommonBpmSpinCtrl.setModel(new javax.swing.SpinnerNumberModel(Float.valueOf(107.2f), Float.valueOf(20.0f), Float.valueOf(3000.0f), Float.valueOf(0.1f)));
        globalCommonBpmSpinCtrl.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                globalCommonBpmSpinCtrlAncestorAdded(evt);
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        jPanel11.add(globalCommonBpmSpinCtrl);

        midiClockLabel.setText("MIDI Clock");
        jPanel11.add(midiClockLabel);

        globalCommonMidiClockChoice.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Auto", "Internal", "External" }));
        globalCommonMidiClockChoice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                globalCommonMidiClockChoiceActionPerformed(evt);
            }
        });
        jPanel11.add(globalCommonMidiClockChoice);

        jPanel10.add(jPanel11);
        jPanel10.add(filler4);

        globalCommonParamsPanel.add(jPanel10);
        globalCommonParamsPanel.add(filler6);

        globalParamsTabbedPane.addTab("Common", globalCommonParamsPanel);

        globalXYPadParamsPanel.setLayout(new javax.swing.BoxLayout(globalXYPadParamsPanel, javax.swing.BoxLayout.LINE_AXIS));

        jPanel12.setLayout(new javax.swing.BoxLayout(jPanel12, javax.swing.BoxLayout.PAGE_AXIS));

        jPanel13.setLayout(new java.awt.GridLayout(0, 4, 10, 10));

        xyPadMidiChannelLabel.setText("MIDI Channel");
        jPanel13.add(xyPadMidiChannelLabel);

        globalXYPadCCModeMidiChannelChoice.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "Global" }));
        globalXYPadCCModeMidiChannelChoice.setSelectedIndex(16);
        globalXYPadCCModeMidiChannelChoice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                globalXYPadCCModeMidiChannelChoiceActionPerformed(evt);
            }
        });
        jPanel13.add(globalXYPadCCModeMidiChannelChoice);
        jPanel13.add(jLabel11);
        jPanel13.add(jLabel12);

        globalXYPadCCModeXAssignTypeLabel.setText("X Assign Type");
        jPanel13.add(globalXYPadCCModeXAssignTypeLabel);

        globalXYPadCCModeXAssignTypeChoice.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "No Assign", "CC", "Pitch Bend" }));
        globalXYPadCCModeXAssignTypeChoice.setSelectedIndex(1);
        globalXYPadCCModeXAssignTypeChoice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                globalXYPadCCModeXAssignTypeChoiceActionPerformed(evt);
            }
        });
        jPanel13.add(globalXYPadCCModeXAssignTypeChoice);

        yAssignTypeLabel.setText("Y Assign Type");
        jPanel13.add(yAssignTypeLabel);

        globalXYPadCCModeYAssignTypeChoice.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "No Assign", "CC", "Pitch Bend" }));
        globalXYPadCCModeYAssignTypeChoice.setSelectedIndex(1);
        globalXYPadCCModeYAssignTypeChoice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                globalXYPadCCModeYAssignTypeChoiceActionPerformed(evt);
            }
        });
        jPanel13.add(globalXYPadCCModeYAssignTypeChoice);

        xCCNumberLabel.setText("X CC Number");
        jPanel13.add(xCCNumberLabel);

        globalXYPadCCModeXCCNumberSpinCtrl.setModel(new javax.swing.SpinnerNumberModel(1, 0, 127, 1));
        globalXYPadCCModeXCCNumberSpinCtrl.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                globalXYPadCCModeXCCNumberSpinCtrlAncestorAdded(evt);
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        jPanel13.add(globalXYPadCCModeXCCNumberSpinCtrl);

        yCCNumberLabel.setText("Y CC Number");
        jPanel13.add(yCCNumberLabel);

        globalXYPadCCModeYCCNumberSpinCtrl.setModel(new javax.swing.SpinnerNumberModel(2, 0, 127, 1));
        globalXYPadCCModeYCCNumberSpinCtrl.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                globalXYPadCCModeYCCNumberSpinCtrlAncestorAdded(evt);
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        jPanel13.add(globalXYPadCCModeYCCNumberSpinCtrl);

        xPolarityLabel.setText("X Polarity");
        jPanel13.add(xPolarityLabel);

        globalXYPadCCModeXPolarityChoice.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Normal", "Reverse" }));
        globalXYPadCCModeXPolarityChoice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                globalXYPadCCModeXPolarityChoiceActionPerformed(evt);
            }
        });
        jPanel13.add(globalXYPadCCModeXPolarityChoice);

        yPolarityLabel.setText("Y Polarity");
        jPanel13.add(yPolarityLabel);

        globalXYPadCCModeYPolarityChoice.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Normal", "Reverse" }));
        globalXYPadCCModeYPolarityChoice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                globalXYPadCCModeYPolarityChoiceActionPerformed(evt);
            }
        });
        jPanel13.add(globalXYPadCCModeYPolarityChoice);
        jPanel13.add(jLabel10);
        jPanel13.add(jLabel13);
        jPanel13.add(jLabel14);
        jPanel13.add(jLabel15);

        touchEnableLabel.setText("Touch Enable");
        jPanel13.add(touchEnableLabel);

        globalXYPadCCModeTouchEnableChoice.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Enable", "Disable" }));
        globalXYPadCCModeTouchEnableChoice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                globalXYPadCCModeTouchEnableChoiceActionPerformed(evt);
            }
        });
        jPanel13.add(globalXYPadCCModeTouchEnableChoice);

        onValueLabel.setText("On Value");
        jPanel13.add(onValueLabel);

        globalXYPadCCModeOnValueSpinCtrl.setModel(new javax.swing.SpinnerNumberModel(127, 0, 127, 1));
        globalXYPadCCModeOnValueSpinCtrl.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                globalXYPadCCModeOnValueSpinCtrlAncestorAdded(evt);
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        jPanel13.add(globalXYPadCCModeOnValueSpinCtrl);

        touchEnableOffValueLabel.setText("CC Number");
        jPanel13.add(touchEnableOffValueLabel);

        globalXYPadCCModeTouchEnableCCNumberSpinCtrl.setModel(new javax.swing.SpinnerNumberModel(16, 0, 127, 1));
        globalXYPadCCModeTouchEnableCCNumberSpinCtrl.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                globalXYPadCCModeTouchEnableCCNumberSpinCtrlAncestorAdded(evt);
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        jPanel13.add(globalXYPadCCModeTouchEnableCCNumberSpinCtrl);

        offValueLabel.setText("Off Value");
        jPanel13.add(offValueLabel);

        globalXYPadCCModeOffValueSpinCtrl.setModel(new javax.swing.SpinnerNumberModel(0, 0, 127, 1));
        globalXYPadCCModeOffValueSpinCtrl.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                globalXYPadCCModeOffValueSpinCtrlAncestorAdded(evt);
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        jPanel13.add(globalXYPadCCModeOffValueSpinCtrl);

        jPanel12.add(jPanel13);
        jPanel12.add(filler8);

        globalXYPadParamsPanel.add(jPanel12);
        globalXYPadParamsPanel.add(filler7);

        globalParamsTabbedPane.addTab("X-Y Pad", globalXYPadParamsPanel);

        globalTouchScaleParamsPanel.setLayout(new javax.swing.BoxLayout(globalTouchScaleParamsPanel, javax.swing.BoxLayout.LINE_AXIS));

        jPanel14.setLayout(new javax.swing.BoxLayout(jPanel14, javax.swing.BoxLayout.PAGE_AXIS));

        jPanel15.setLayout(new java.awt.GridLayout(0, 2, 10, 10));

        midiChannelLabel.setText("MIDI Channel");
        jPanel15.add(midiChannelLabel);

        globalXYPadTouchScaleMidiChannelChoice.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "Global" }));
        globalXYPadTouchScaleMidiChannelChoice.setSelectedIndex(16);
        globalXYPadTouchScaleMidiChannelChoice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                globalXYPadTouchScaleMidiChannelChoiceActionPerformed(evt);
            }
        });
        jPanel15.add(globalXYPadTouchScaleMidiChannelChoice);

        noteOnVelocityLabel.setText("Note On Velocity");
        jPanel15.add(noteOnVelocityLabel);

        globalXYPadTouchScaleNoteOnVelocitySpinCtrl.setModel(new javax.swing.SpinnerNumberModel(100, 0, 127, 1));
        globalXYPadTouchScaleNoteOnVelocitySpinCtrl.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                globalXYPadTouchScaleNoteOnVelocitySpinCtrlAncestorAdded(evt);
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        jPanel15.add(globalXYPadTouchScaleNoteOnVelocitySpinCtrl);

        yAxisCCEnableLabel.setText("Y-axis CC Enable");
        jPanel15.add(yAxisCCEnableLabel);

        globalXYPadTouchScaleYAxisCCEnableChoice.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Enable", "Disable" }));
        globalXYPadTouchScaleYAxisCCEnableChoice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                globalXYPadTouchScaleYAxisCCEnableChoiceActionPerformed(evt);
            }
        });
        jPanel15.add(globalXYPadTouchScaleYAxisCCEnableChoice);

        yAxisCCNumberLabel.setText("Y-axis CC Number");
        jPanel15.add(yAxisCCNumberLabel);

        globalXYPadTouchScaleYAxisCCNumberSpinCtrl.setModel(new javax.swing.SpinnerNumberModel(2, 0, 127, 1));
        globalXYPadTouchScaleYAxisCCNumberSpinCtrl.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                globalXYPadTouchScaleYAxisCCNumberSpinCtrlAncestorAdded(evt);
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        jPanel15.add(globalXYPadTouchScaleYAxisCCNumberSpinCtrl);

        yAxisPolarityLabel.setText("Y-axis Polarity");
        jPanel15.add(yAxisPolarityLabel);

        globalXYPadTouchScaleYAxisPolarityChoice.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Normal", "Reverse" }));
        globalXYPadTouchScaleYAxisPolarityChoice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                globalXYPadTouchScaleYAxisPolarityChoiceActionPerformed(evt);
            }
        });
        jPanel15.add(globalXYPadTouchScaleYAxisPolarityChoice);

        gateSpeedLabel.setText("Gate Speed");
        jPanel15.add(gateSpeedLabel);

        globalXYPadTouchScaleGateSpeedChoice.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1/48", "1/32", "1/16", "1/12", "1/8", "1/6", "3/16", "1/4", "1/3", "3/8", "1/2" }));
        globalXYPadTouchScaleGateSpeedChoice.setSelectedIndex(4);
        globalXYPadTouchScaleGateSpeedChoice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                globalXYPadTouchScaleGateSpeedChoiceActionPerformed(evt);
            }
        });
        jPanel15.add(globalXYPadTouchScaleGateSpeedChoice);

        jPanel14.add(jPanel15);
        jPanel14.add(filler10);

        globalTouchScaleParamsPanel.add(jPanel14);
        globalTouchScaleParamsPanel.add(filler9);

        globalParamsTabbedPane.addTab("Touch Scale", globalTouchScaleParamsPanel);

        globalUserScaleParamsPanel.setLayout(new javax.swing.BoxLayout(globalUserScaleParamsPanel, javax.swing.BoxLayout.LINE_AXIS));

        jPanel16.setLayout(new javax.swing.BoxLayout(jPanel16, javax.swing.BoxLayout.PAGE_AXIS));

        jPanel17.setLayout(new java.awt.GridLayout(0, 4, 10, 10));

        jLabel16.setText("Length");
        jPanel17.add(jLabel16);

        userScaleNoteLengthSpinCtrl.setModel(new javax.swing.SpinnerNumberModel(12, 0, 12, 1));
        userScaleNoteLengthSpinCtrl.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                userScaleNoteLengthSpinCtrlAncestorAdded(evt);
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        jPanel17.add(userScaleNoteLengthSpinCtrl);
        jPanel17.add(jLabel17);
        jPanel17.add(jLabel18);

        jLabel19.setText("Note Offset 1");
        jPanel17.add(jLabel19);

        userScaleNoteOffset01Spinner.setModel(new javax.swing.SpinnerNumberModel(0, 0, 12, 1));
        userScaleNoteOffset01Spinner.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                userScaleNoteOffset01SpinnerAncestorAdded(evt);
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        jPanel17.add(userScaleNoteOffset01Spinner);

        jLabel20.setText("Note Offset 7");
        jPanel17.add(jLabel20);

        userScaleNoteOffset07Spinner.setModel(new javax.swing.SpinnerNumberModel(6, 0, 12, 1));
        userScaleNoteOffset07Spinner.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                userScaleNoteOffset07SpinnerAncestorAdded(evt);
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        jPanel17.add(userScaleNoteOffset07Spinner);

        jLabel21.setText("Note Offset 2");
        jPanel17.add(jLabel21);

        userScaleNoteOffset02Spinner.setModel(new javax.swing.SpinnerNumberModel(1, 0, 12, 1));
        userScaleNoteOffset02Spinner.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                userScaleNoteOffset02SpinnerAncestorAdded(evt);
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        jPanel17.add(userScaleNoteOffset02Spinner);

        jLabel22.setText("Note Offset 8");
        jPanel17.add(jLabel22);

        userScaleNoteOffset08Spinner.setModel(new javax.swing.SpinnerNumberModel(7, 0, 12, 1));
        userScaleNoteOffset08Spinner.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                userScaleNoteOffset08SpinnerAncestorAdded(evt);
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        jPanel17.add(userScaleNoteOffset08Spinner);

        jLabel23.setText("Note Offset 3");
        jPanel17.add(jLabel23);

        userScaleNoteOffset03Spinner.setModel(new javax.swing.SpinnerNumberModel(2, 0, 12, 1));
        userScaleNoteOffset03Spinner.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                userScaleNoteOffset03SpinnerAncestorAdded(evt);
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        jPanel17.add(userScaleNoteOffset03Spinner);

        jLabel24.setText("Note Offset 9");
        jPanel17.add(jLabel24);

        userScaleNoteOffset09Spinner.setModel(new javax.swing.SpinnerNumberModel(8, 0, 12, 1));
        userScaleNoteOffset09Spinner.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                userScaleNoteOffset09SpinnerAncestorAdded(evt);
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        jPanel17.add(userScaleNoteOffset09Spinner);

        jLabel25.setText("Note Offset 4");
        jPanel17.add(jLabel25);

        userScaleNoteOffset04Spinner.setModel(new javax.swing.SpinnerNumberModel(3, 0, 12, 1));
        userScaleNoteOffset04Spinner.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                userScaleNoteOffset04SpinnerAncestorAdded(evt);
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        jPanel17.add(userScaleNoteOffset04Spinner);

        jLabel26.setText("Note Offset 10");
        jPanel17.add(jLabel26);

        userScaleNoteOffset10Spinner.setModel(new javax.swing.SpinnerNumberModel(9, 0, 12, 1));
        userScaleNoteOffset10Spinner.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                userScaleNoteOffset10SpinnerAncestorAdded(evt);
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        jPanel17.add(userScaleNoteOffset10Spinner);

        jLabel27.setText("Note Offset 5");
        jPanel17.add(jLabel27);

        userScaleNoteOffset05Spinner.setModel(new javax.swing.SpinnerNumberModel(4, 0, 12, 1));
        userScaleNoteOffset05Spinner.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                userScaleNoteOffset05SpinnerAncestorAdded(evt);
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        jPanel17.add(userScaleNoteOffset05Spinner);

        jLabel28.setText("Note Offset 11");
        jPanel17.add(jLabel28);

        userScaleNoteOffset11Spinner.setModel(new javax.swing.SpinnerNumberModel(10, 0, 12, 1));
        userScaleNoteOffset11Spinner.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                userScaleNoteOffset11SpinnerAncestorAdded(evt);
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        jPanel17.add(userScaleNoteOffset11Spinner);

        jLabel29.setText("Note Offset 6");
        jPanel17.add(jLabel29);

        userScaleNoteOffset06Spinner.setModel(new javax.swing.SpinnerNumberModel(5, 0, 12, 1));
        userScaleNoteOffset06Spinner.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                userScaleNoteOffset06SpinnerAncestorAdded(evt);
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        jPanel17.add(userScaleNoteOffset06Spinner);

        jLabel30.setText("Note Offset 12");
        jPanel17.add(jLabel30);

        userScaleNoteOffset12Spinner.setModel(new javax.swing.SpinnerNumberModel(11, 0, 12, 1));
        userScaleNoteOffset12Spinner.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                userScaleNoteOffset12SpinnerAncestorAdded(evt);
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        jPanel17.add(userScaleNoteOffset12Spinner);

        jPanel16.add(jPanel17);
        jPanel16.add(filler12);

        globalUserScaleParamsPanel.add(jPanel16);
        globalUserScaleParamsPanel.add(filler11);

        globalParamsTabbedPane.addTab("User Scale", globalUserScaleParamsPanel);

        globalParamsTabPanel.add(globalParamsTabbedPane, java.awt.BorderLayout.PAGE_START);

        parameterTabbedPane.addTab("Global", globalParamsTabPanel);

        preferencesTabPanel.setLayout(new javax.swing.BoxLayout(preferencesTabPanel, javax.swing.BoxLayout.PAGE_AXIS));

        midiPortsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Midi Ports"));
        midiPortsPanel.setLayout(new java.awt.GridLayout(0, 2, 10, 10));

        manualMidiPortSelectionCheckBox.setText("Set midi ports manually");
        manualMidiPortSelectionCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                manualMidiPortSelectionCheckBoxActionPerformed(evt);
            }
        });
        midiPortsPanel.add(manualMidiPortSelectionCheckBox);
        midiPortsPanel.add(jLabel31);

        midiInLabel.setText("MIDI IN");
        midiPortsPanel.add(midiInLabel);

        midiInChoice.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        midiInChoice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                midiInChoiceActionPerformed(evt);
            }
        });
        midiPortsPanel.add(midiInChoice);

        midiOutLabel.setText("MIDI OUT");
        midiPortsPanel.add(midiOutLabel);

        midiOutChoice.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        midiOutChoice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                midiOutChoiceActionPerformed(evt);
            }
        });
        midiPortsPanel.add(midiOutChoice);

        preferencesTabPanel.add(midiPortsPanel);

        miscellaneousPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Misc."));
        miscellaneousPanel.setLayout(new java.awt.GridLayout(1, 0));

        jPanel18.setLayout(new javax.swing.BoxLayout(jPanel18, javax.swing.BoxLayout.PAGE_AXIS));

        receiveSceneSetAutomaticallyCheckBox.setSelected(true);
        receiveSceneSetAutomaticallyCheckBox.setText("Receive scene set automatically");
        receiveSceneSetAutomaticallyCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                receiveSceneSetAutomaticallyCheckBoxActionPerformed(evt);
            }
        });
        jPanel18.add(receiveSceneSetAutomaticallyCheckBox);

        warnBeforeWritingCheckBox.setSelected(true);
        warnBeforeWritingCheckBox.setText("Warn before writing");
        warnBeforeWritingCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                warnBeforeWritingCheckBoxActionPerformed(evt);
            }
        });
        jPanel18.add(warnBeforeWritingCheckBox);

        miscellaneousPanel.add(jPanel18);

        preferencesTabPanel.add(miscellaneousPanel);
        preferencesTabPanel.add(filler13);

        parameterTabbedPane.addTab("Preferences", preferencesTabPanel);

        parameterTabbedPane.setSelectedIndex(1);

        parameterPanel.add(parameterTabbedPane, java.awt.BorderLayout.PAGE_START);

        getContentPane().add(parameterPanel);

        fileMenu.setText("File");

        selectTargetDeviceMenuItem.setLabel("Select target device");
        fileMenu.add(selectTargetDeviceMenuItem);

        newMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        newMenuItem.setLabel("New");
        newMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(newMenuItem);

        openMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        openMenuItem.setLabel("Open");
        openMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(openMenuItem);

        saveMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        saveMenuItem.setLabel("Save");
        saveMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(saveMenuItem);

        saveasMenuItem.setLabel("Save as");
        saveasMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveasMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(saveasMenuItem);

        loadSceneDataMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_L, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        loadSceneDataMenuItem.setLabel("Load Scene Data");
        loadSceneDataMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadSceneDataMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(loadSceneDataMenuItem);

        saveSceneDataMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_D, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        saveSceneDataMenuItem.setLabel("Save Scene Data");
        saveSceneDataMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveSceneDataMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(saveSceneDataMenuItem);

        menuBar.add(fileMenu);

        editMenu.setText("Edit");

        undoMenuItem.setLabel("Undo");
        editMenu.add(undoMenuItem);

        redoMenuItem.setLabel("Redo");
        editMenu.add(redoMenuItem);

        cutMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_X, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        cutMenuItem.setLabel("Cut");
        editMenu.add(cutMenuItem);

        copyMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        copyMenuItem.setLabel("Copy");
        editMenu.add(copyMenuItem);

        pasteMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_V, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        pasteMenuItem.setLabel("Paste");
        editMenu.add(pasteMenuItem);

        clearMenuItem.setLabel("Clear");
        editMenu.add(clearMenuItem);

        menuBar.add(editMenu);

        communicateMenu.setLabel("Communicate");

        receiveSceneSetMenuItem.setLabel("Receive Scene Set");
        receiveSceneSetMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                receiveSceneSetMenuItemActionPerformed(evt);
            }
        });
        communicateMenu.add(receiveSceneSetMenuItem);

        writeSceneSetMenuItem.setLabel("Write Scene Set");
        writeSceneSetMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                writeSceneSetMenuItemActionPerformed(evt);
            }
        });
        communicateMenu.add(writeSceneSetMenuItem);

        receiveSceneDataMenuItem.setLabel("Receive Scene Data");
        receiveSceneDataMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                receiveSceneDataMenuItemActionPerformed(evt);
            }
        });
        communicateMenu.add(receiveSceneDataMenuItem);

        writeSceneDataMenuItem.setLabel("Write Scene Data");
        writeSceneDataMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                writeSceneDataMenuItemActionPerformed(evt);
            }
        });
        communicateMenu.add(writeSceneDataMenuItem);

        sendGlobalDataMenuItem.setText("Send Global Data");
        sendGlobalDataMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendGlobalDataMenuItemActionPerformed(evt);
            }
        });
        communicateMenu.add(sendGlobalDataMenuItem);

        menuBar.add(communicateMenu);

        helpMenu.setLabel("Help");

        aboutMenuItem.setLabel("About");
        helpMenu.add(aboutMenuItem);

        menuBar.add(helpMenu);

        setJMenuBar(menuBar);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void parameterDisplayChoiceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_parameterDisplayChoiceActionPerformed
        updateNanoPad2DisplayPanel();
    }//GEN-LAST:event_parameterDisplayChoiceActionPerformed

    private void scene1RadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_scene1RadioButtonActionPerformed
        JRadioButton btn = (JRadioButton)evt.getSource();
        if(btn.isSelected()) {
            nanoPad2.setCurrentScene(0);
            updateCurrentTriggerPad();
            updateNanoPad2DisplayPanel();
            eventBus.post(new SceneChangedEvent(1));
        }
    }//GEN-LAST:event_scene1RadioButtonActionPerformed

    private void scene2RadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_scene2RadioButtonActionPerformed
        JRadioButton btn = (JRadioButton)evt.getSource();
        if(btn.isSelected()) {
            nanoPad2.setCurrentScene(1);
            updateCurrentTriggerPad();
            updateNanoPad2DisplayPanel();
            eventBus.post(new SceneChangedEvent(2));
        }
    }//GEN-LAST:event_scene2RadioButtonActionPerformed

    private void scene3RadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_scene3RadioButtonActionPerformed
        JRadioButton btn = (JRadioButton)evt.getSource();
        if(btn.isSelected()) {
            nanoPad2.setCurrentScene(2);
            updateCurrentTriggerPad();
            updateNanoPad2DisplayPanel();
            eventBus.post(new SceneChangedEvent(3));
        }
    }//GEN-LAST:event_scene3RadioButtonActionPerformed

    private void scene4RadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_scene4RadioButtonActionPerformed
        JRadioButton btn = (JRadioButton)evt.getSource();
        if(btn.isSelected()) {
            nanoPad2.setCurrentScene(3);
            updateCurrentTriggerPad();
            updateNanoPad2DisplayPanel();
            eventBus.post(new SceneChangedEvent(4));
        }
    }//GEN-LAST:event_scene4RadioButtonActionPerformed

    private void controlsListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_controlsListValueChanged
        updateCurrentTriggerPad();
    }//GEN-LAST:event_controlsListValueChanged

    private void writeSceneDataMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_writeSceneDataMenuItemActionPerformed
        eventBus.post(new SendSceneDataToNanoPad2Event(nanoPad2.getCurrentScene() + 1));
    }//GEN-LAST:event_writeSceneDataMenuItemActionPerformed

    private void triggerPadMidiChoiceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_triggerPadMidiChoiceActionPerformed
        Scene scene = nanoPad2.getScene(nanoPad2.getCurrentScene());
        int triggerPadIndex = controlsList.getSelectedIndex();
        TriggerPad triggerPad = scene.getTriggerPad(triggerPadIndex);
        triggerPad.setMidiChannel(triggerPadMidiChoice.getSelectedIndex());
        updateNanoPad2DisplayPanel();
    }//GEN-LAST:event_triggerPadMidiChoiceActionPerformed

    private void triggerPadGateArpEnableCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_triggerPadGateArpEnableCheckBoxActionPerformed
        Scene scene = nanoPad2.getScene(nanoPad2.getCurrentScene());
        int triggerPadIndex = controlsList.getSelectedIndex();
        TriggerPad triggerPad = scene.getTriggerPad(triggerPadIndex);
        triggerPad.setGateArpEnabled(triggerPadGateArpEnableCheckBox.isSelected());
        updateNanoPad2DisplayPanel();
    }//GEN-LAST:event_triggerPadGateArpEnableCheckBoxActionPerformed

    private void triggerPadTouchScaleGateArpEnableCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_triggerPadTouchScaleGateArpEnableCheckBoxActionPerformed
        Scene scene = nanoPad2.getScene(nanoPad2.getCurrentScene());
        int triggerPadIndex = controlsList.getSelectedIndex();
        TriggerPad triggerPad = scene.getTriggerPad(triggerPadIndex);
        triggerPad.setTouchScaleGateArpEnable(triggerPadTouchScaleGateArpEnableCheckBox.isSelected());
        updateNanoPad2DisplayPanel();
    }//GEN-LAST:event_triggerPadTouchScaleGateArpEnableCheckBoxActionPerformed

    private void triggerPadBehaviourCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_triggerPadBehaviourCheckBoxActionPerformed
        Scene scene = nanoPad2.getScene(nanoPad2.getCurrentScene());
        int triggerPadIndex = controlsList.getSelectedIndex();
        TriggerPad triggerPad = scene.getTriggerPad(triggerPadIndex);
        triggerPad.setPadBehaviour(triggerPadBehaviourCheckBox.isSelected() ? TriggerPad.PadBehaviour.Momentary : TriggerPad.PadBehaviour.Toggle);
        updateNanoPad2DisplayPanel();
    }//GEN-LAST:event_triggerPadBehaviourCheckBoxActionPerformed

    private void triggerPadNoteCCProgNumber1ChoiceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_triggerPadNoteCCProgNumber1ChoiceActionPerformed
        Scene scene = nanoPad2.getScene(nanoPad2.getCurrentScene());
        int triggerPadIndex = controlsList.getSelectedIndex();
        TriggerPad triggerPad = scene.getTriggerPad(triggerPadIndex);
        triggerPad.setNote1(triggerPadNoteCCProgNumber1Choice.getSelectedIndex());
        updateNanoPad2DisplayPanel();
    }//GEN-LAST:event_triggerPadNoteCCProgNumber1ChoiceActionPerformed

    private void triggerPadNoteCCProgNumber2ChoiceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_triggerPadNoteCCProgNumber2ChoiceActionPerformed
        Scene scene = nanoPad2.getScene(nanoPad2.getCurrentScene());
        int triggerPadIndex = controlsList.getSelectedIndex();
        TriggerPad triggerPad = scene.getTriggerPad(triggerPadIndex);
        triggerPad.setNote2(triggerPadNoteCCProgNumber2Choice.getSelectedIndex());
        updateNanoPad2DisplayPanel();
    }//GEN-LAST:event_triggerPadNoteCCProgNumber2ChoiceActionPerformed

    private void triggerPadNoteCCProgNumber3ChoiceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_triggerPadNoteCCProgNumber3ChoiceActionPerformed
        Scene scene = nanoPad2.getScene(nanoPad2.getCurrentScene());
        int triggerPadIndex = controlsList.getSelectedIndex();
        TriggerPad triggerPad = scene.getTriggerPad(triggerPadIndex);
        triggerPad.setNote3(triggerPadNoteCCProgNumber3Choice.getSelectedIndex());
        updateNanoPad2DisplayPanel();
    }//GEN-LAST:event_triggerPadNoteCCProgNumber3ChoiceActionPerformed

    private void triggerPadNoteCCProgNumber4ChoiceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_triggerPadNoteCCProgNumber4ChoiceActionPerformed
        Scene scene = nanoPad2.getScene(nanoPad2.getCurrentScene());
        int triggerPadIndex = controlsList.getSelectedIndex();
        TriggerPad triggerPad = scene.getTriggerPad(triggerPadIndex);
        triggerPad.setNote4(triggerPadNoteCCProgNumber4Choice.getSelectedIndex());
        updateNanoPad2DisplayPanel();
    }//GEN-LAST:event_triggerPadNoteCCProgNumber4ChoiceActionPerformed

    private void triggerPadAssignTypeChoiceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_triggerPadAssignTypeChoiceActionPerformed
        Scene scene = nanoPad2.getScene(nanoPad2.getCurrentScene());
        int triggerPadIndex = controlsList.getSelectedIndex();
        TriggerPad triggerPad = scene.getTriggerPad(triggerPadIndex);
        triggerPad.setAssignType(TriggerPad.AssignType.valueOf((String)triggerPadAssignTypeChoice.getSelectedItem()));
        updateNanoPad2DisplayPanel();
    }//GEN-LAST:event_triggerPadAssignTypeChoiceActionPerformed

    private void saveMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveMenuItemActionPerformed
        if(currentNanoPad2File != null) {
            try {
                ObjectMapper mapper = new ObjectMapper(new JsonFactory());
                mapper.writeValue(currentNanoPad2File, nanoPad2);
            } catch (IOException ex) {
                Logger.getLogger(NanoPad2Frame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else {
            JOptionPane.showMessageDialog(this, "You need to use 'Save as'.", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_saveMenuItemActionPerformed

    private void saveasMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveasMenuItemActionPerformed
        int returnValue = sceneSetFileChooser.showSaveDialog(this);
        if(returnValue == JFileChooser.APPROVE_OPTION) {
            try {
                currentNanoPad2File = sceneSetFileChooser.getSelectedFile();
                ObjectMapper mapper = new ObjectMapper(new JsonFactory());
                mapper.writeValue(currentNanoPad2File, nanoPad2);
            } catch (IOException ex) {
                Logger.getLogger(NanoPad2Frame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_saveasMenuItemActionPerformed

    private void openMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openMenuItemActionPerformed
        int returnValue = sceneSetFileChooser.showOpenDialog(this);
        if(returnValue == JFileChooser.APPROVE_OPTION) {
            try {
                currentNanoPad2File = sceneSetFileChooser.getSelectedFile();
                ObjectMapper mapper = new ObjectMapper(new JsonFactory());
                nanoPad2 = mapper.readValue(currentNanoPad2File, NanoPad2.class);
                updateCurrentTriggerPad();
                updateNanoPad2DisplayPanel();
                updateGlobalData();
            } catch (IOException ex) {
                Logger.getLogger(NanoPad2Frame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_openMenuItemActionPerformed

    private void saveSceneDataMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveSceneDataMenuItemActionPerformed
        int returnValue = sceneFileChooser.showSaveDialog(this);
        if(returnValue == JFileChooser.APPROVE_OPTION) {
            try {
                File selectedFile = sceneFileChooser.getSelectedFile();
                ObjectMapper mapper = new ObjectMapper(new JsonFactory());
                mapper.writeValue(selectedFile, nanoPad2.getScene(nanoPad2.getCurrentScene()));
            } catch (IOException ex) {
                Logger.getLogger(NanoPad2Frame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_saveSceneDataMenuItemActionPerformed

    private void loadSceneDataMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loadSceneDataMenuItemActionPerformed
        int returnValue = sceneFileChooser.showOpenDialog(this);
        if(returnValue == JFileChooser.APPROVE_OPTION) {
            try {
                File selectedFile = sceneFileChooser.getSelectedFile();
                ObjectMapper mapper = new ObjectMapper(new JsonFactory());
                Scene scene = mapper.readValue(selectedFile, Scene.class);
                int currentSceneIndex = nanoPad2.getCurrentScene();
                nanoPad2.setScene(currentSceneIndex, scene);
                updateCurrentTriggerPad();
                updateNanoPad2DisplayPanel();
            } catch (IOException ex) {
                Logger.getLogger(NanoPad2Frame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_loadSceneDataMenuItemActionPerformed

    private void globalCommonMidiChannelChoiceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_globalCommonMidiChannelChoiceActionPerformed
        GlobalParameters globalParameters = nanoPad2.getGlobalParameters();
        globalParameters.setGlobalMidiChannel(globalCommonMidiChannelChoice.getSelectedIndex());
    }//GEN-LAST:event_globalCommonMidiChannelChoiceActionPerformed

    private void globalCommonVelocityCurveChoiceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_globalCommonVelocityCurveChoiceActionPerformed
        GlobalParameters globalParameters = nanoPad2.getGlobalParameters();
        globalParameters.setVelocityCurve(GlobalParameters.VelocityCurve.values()[globalCommonVelocityCurveChoice.getSelectedIndex()]);
    }//GEN-LAST:event_globalCommonVelocityCurveChoiceActionPerformed

    private void globalCommonConstantVelocityValueSpinCtrlAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_globalCommonConstantVelocityValueSpinCtrlAncestorAdded
        GlobalParameters globalParameters = nanoPad2.getGlobalParameters();
        globalParameters.setConstantVelocityValue((int)globalCommonConstantVelocityValueSpinCtrl.getValue());
    }//GEN-LAST:event_globalCommonConstantVelocityValueSpinCtrlAncestorAdded

    private void globalCommonBpmSpinCtrlAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_globalCommonBpmSpinCtrlAncestorAdded
        GlobalParameters globalParameters = nanoPad2.getGlobalParameters();
        globalParameters.setBpm((float)globalCommonBpmSpinCtrl.getValue());
    }//GEN-LAST:event_globalCommonBpmSpinCtrlAncestorAdded

    private void globalCommonMidiClockChoiceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_globalCommonMidiClockChoiceActionPerformed
        GlobalParameters globalParameters = nanoPad2.getGlobalParameters();
        globalParameters.setMidiClock(GlobalParameters.MidiClock.values()[globalCommonMidiClockChoice.getSelectedIndex()]);
    }//GEN-LAST:event_globalCommonMidiClockChoiceActionPerformed

    private void globalXYPadCCModeXAssignTypeChoiceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_globalXYPadCCModeXAssignTypeChoiceActionPerformed
        GlobalParameters globalParameters = nanoPad2.getGlobalParameters();
        globalParameters.setXyPadCCModeXAxisAssignType(GlobalParameters.AssignType.values()[globalXYPadCCModeXAssignTypeChoice.getSelectedIndex()]);
    }//GEN-LAST:event_globalXYPadCCModeXAssignTypeChoiceActionPerformed

    private void globalXYPadCCModeXCCNumberSpinCtrlAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_globalXYPadCCModeXCCNumberSpinCtrlAncestorAdded
        GlobalParameters globalParameters = nanoPad2.getGlobalParameters();
        globalParameters.setXyPadCCModeXAxisCCNumber((int)globalXYPadCCModeXCCNumberSpinCtrl.getValue());
    }//GEN-LAST:event_globalXYPadCCModeXCCNumberSpinCtrlAncestorAdded

    private void globalXYPadCCModeXPolarityChoiceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_globalXYPadCCModeXPolarityChoiceActionPerformed
        GlobalParameters globalParameters = nanoPad2.getGlobalParameters();
        globalParameters.setXyPadCCModeXAxisPolarity(GlobalParameters.Polarity.values()[globalXYPadCCModeXPolarityChoice.getSelectedIndex()]);
    }//GEN-LAST:event_globalXYPadCCModeXPolarityChoiceActionPerformed

    private void globalXYPadCCModeTouchEnableChoiceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_globalXYPadCCModeTouchEnableChoiceActionPerformed
        GlobalParameters globalParameters = nanoPad2.getGlobalParameters();
        globalParameters.setXyPadCCModeTouchEnabled(globalXYPadCCModeTouchEnableChoice.getSelectedIndex() == 0 ? true : false);
    }//GEN-LAST:event_globalXYPadCCModeTouchEnableChoiceActionPerformed

    private void globalXYPadCCModeTouchEnableCCNumberSpinCtrlAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_globalXYPadCCModeTouchEnableCCNumberSpinCtrlAncestorAdded
        GlobalParameters globalParameters = nanoPad2.getGlobalParameters();
        globalParameters.setXyPadCCModeTouchCCNumber((int)globalXYPadCCModeTouchEnableCCNumberSpinCtrl.getValue());
    }//GEN-LAST:event_globalXYPadCCModeTouchEnableCCNumberSpinCtrlAncestorAdded

    private void globalXYPadCCModeYAssignTypeChoiceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_globalXYPadCCModeYAssignTypeChoiceActionPerformed
        GlobalParameters globalParameters = nanoPad2.getGlobalParameters();
        globalParameters.setXyPadCCModeYAxisAssignType(GlobalParameters.AssignType.values()[globalXYPadCCModeYAssignTypeChoice.getSelectedIndex()]);
    }//GEN-LAST:event_globalXYPadCCModeYAssignTypeChoiceActionPerformed

    private void globalXYPadCCModeYCCNumberSpinCtrlAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_globalXYPadCCModeYCCNumberSpinCtrlAncestorAdded
        GlobalParameters globalParameters = nanoPad2.getGlobalParameters();
        globalParameters.setXyPadCCModeYAxisCCNumber((int)globalXYPadCCModeYCCNumberSpinCtrl.getValue());
    }//GEN-LAST:event_globalXYPadCCModeYCCNumberSpinCtrlAncestorAdded

    private void globalXYPadCCModeYPolarityChoiceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_globalXYPadCCModeYPolarityChoiceActionPerformed
        GlobalParameters globalParameters = nanoPad2.getGlobalParameters();
        globalParameters.setXyPadCCModeYAxisPolarity(GlobalParameters.Polarity.values()[globalXYPadCCModeYPolarityChoice.getSelectedIndex()]);
    }//GEN-LAST:event_globalXYPadCCModeYPolarityChoiceActionPerformed

    private void globalXYPadCCModeOnValueSpinCtrlAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_globalXYPadCCModeOnValueSpinCtrlAncestorAdded
        GlobalParameters globalParameters = nanoPad2.getGlobalParameters();
        globalParameters.setXyPadCCModeTouchOnValue((int)globalXYPadCCModeOnValueSpinCtrl.getValue());
    }//GEN-LAST:event_globalXYPadCCModeOnValueSpinCtrlAncestorAdded

    private void globalXYPadCCModeOffValueSpinCtrlAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_globalXYPadCCModeOffValueSpinCtrlAncestorAdded
        GlobalParameters globalParameters = nanoPad2.getGlobalParameters();
        globalParameters.setXyPadCCModeTouchOffValue((int)globalXYPadCCModeOffValueSpinCtrl.getValue());
    }//GEN-LAST:event_globalXYPadCCModeOffValueSpinCtrlAncestorAdded

    private void globalXYPadTouchScaleMidiChannelChoiceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_globalXYPadTouchScaleMidiChannelChoiceActionPerformed
        GlobalParameters globalParameters = nanoPad2.getGlobalParameters();
        globalParameters.setXyPadTouchScaleMidiChannel(globalXYPadTouchScaleMidiChannelChoice.getSelectedIndex());
    }//GEN-LAST:event_globalXYPadTouchScaleMidiChannelChoiceActionPerformed

    private void globalXYPadTouchScaleNoteOnVelocitySpinCtrlAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_globalXYPadTouchScaleNoteOnVelocitySpinCtrlAncestorAdded
        GlobalParameters globalParameters = nanoPad2.getGlobalParameters();
        globalParameters.setXyPadTouchScaleNoteOnVelocity((int)globalXYPadTouchScaleNoteOnVelocitySpinCtrl.getValue());
    }//GEN-LAST:event_globalXYPadTouchScaleNoteOnVelocitySpinCtrlAncestorAdded

    private void globalXYPadTouchScaleYAxisCCEnableChoiceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_globalXYPadTouchScaleYAxisCCEnableChoiceActionPerformed
        GlobalParameters globalParameters = nanoPad2.getGlobalParameters();
        globalParameters.setXyPadTouchScaleYAxisCCEnabled(globalXYPadTouchScaleYAxisCCEnableChoice.getSelectedIndex() == 1 ? false : true);
    }//GEN-LAST:event_globalXYPadTouchScaleYAxisCCEnableChoiceActionPerformed

    private void globalXYPadTouchScaleYAxisCCNumberSpinCtrlAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_globalXYPadTouchScaleYAxisCCNumberSpinCtrlAncestorAdded
        GlobalParameters globalParameters = nanoPad2.getGlobalParameters();
        globalParameters.setXyPadTouchScaleYAxisCCNumber((int)globalXYPadTouchScaleYAxisCCNumberSpinCtrl.getValue());
    }//GEN-LAST:event_globalXYPadTouchScaleYAxisCCNumberSpinCtrlAncestorAdded

    private void globalXYPadTouchScaleYAxisPolarityChoiceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_globalXYPadTouchScaleYAxisPolarityChoiceActionPerformed
        GlobalParameters globalParameters = nanoPad2.getGlobalParameters();
        globalParameters.setXyPadTouchScaleYAxisPolarity(globalXYPadTouchScaleYAxisPolarityChoice.getSelectedIndex() == 0 ? GlobalParameters.Polarity.NORMAL : GlobalParameters.Polarity.REVERSE);
    }//GEN-LAST:event_globalXYPadTouchScaleYAxisPolarityChoiceActionPerformed

    private void globalXYPadTouchScaleGateSpeedChoiceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_globalXYPadTouchScaleGateSpeedChoiceActionPerformed
        GlobalParameters globalParameters = nanoPad2.getGlobalParameters();
        globalParameters.setXyPadTouchScaleGateSpeed(globalXYPadTouchScaleGateSpeedChoice.getSelectedIndex());
    }//GEN-LAST:event_globalXYPadTouchScaleGateSpeedChoiceActionPerformed

    private void userScaleNoteLengthSpinCtrlAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_userScaleNoteLengthSpinCtrlAncestorAdded
        GlobalParameters globalParameters = nanoPad2.getGlobalParameters();
        globalParameters.setUserScaleLength((int)userScaleNoteLengthSpinCtrl.getValue());
    }//GEN-LAST:event_userScaleNoteLengthSpinCtrlAncestorAdded

    private void userScaleNoteOffset01SpinnerAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_userScaleNoteOffset01SpinnerAncestorAdded
        GlobalParameters globalParameters = nanoPad2.getGlobalParameters();
        globalParameters.setNoteOffset01((int)userScaleNoteOffset01Spinner.getValue());
    }//GEN-LAST:event_userScaleNoteOffset01SpinnerAncestorAdded

    private void userScaleNoteOffset02SpinnerAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_userScaleNoteOffset02SpinnerAncestorAdded
        GlobalParameters globalParameters = nanoPad2.getGlobalParameters();
        globalParameters.setNoteOffset02((int)userScaleNoteOffset02Spinner.getValue());
    }//GEN-LAST:event_userScaleNoteOffset02SpinnerAncestorAdded

    private void userScaleNoteOffset03SpinnerAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_userScaleNoteOffset03SpinnerAncestorAdded
        GlobalParameters globalParameters = nanoPad2.getGlobalParameters();
        globalParameters.setNoteOffset03((int)userScaleNoteOffset03Spinner.getValue());
    }//GEN-LAST:event_userScaleNoteOffset03SpinnerAncestorAdded

    private void userScaleNoteOffset04SpinnerAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_userScaleNoteOffset04SpinnerAncestorAdded
        GlobalParameters globalParameters = nanoPad2.getGlobalParameters();
        globalParameters.setNoteOffset04((int)userScaleNoteOffset04Spinner.getValue());
    }//GEN-LAST:event_userScaleNoteOffset04SpinnerAncestorAdded

    private void userScaleNoteOffset05SpinnerAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_userScaleNoteOffset05SpinnerAncestorAdded
        GlobalParameters globalParameters = nanoPad2.getGlobalParameters();
        globalParameters.setNoteOffset05((int)userScaleNoteOffset05Spinner.getValue());
    }//GEN-LAST:event_userScaleNoteOffset05SpinnerAncestorAdded

    private void userScaleNoteOffset06SpinnerAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_userScaleNoteOffset06SpinnerAncestorAdded
        GlobalParameters globalParameters = nanoPad2.getGlobalParameters();
        globalParameters.setNoteOffset06((int)userScaleNoteOffset06Spinner.getValue());
    }//GEN-LAST:event_userScaleNoteOffset06SpinnerAncestorAdded

    private void userScaleNoteOffset07SpinnerAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_userScaleNoteOffset07SpinnerAncestorAdded
        GlobalParameters globalParameters = nanoPad2.getGlobalParameters();
        globalParameters.setNoteOffset07((int)userScaleNoteOffset07Spinner.getValue());
    }//GEN-LAST:event_userScaleNoteOffset07SpinnerAncestorAdded

    private void userScaleNoteOffset08SpinnerAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_userScaleNoteOffset08SpinnerAncestorAdded
        GlobalParameters globalParameters = nanoPad2.getGlobalParameters();
        globalParameters.setNoteOffset08((int)userScaleNoteOffset08Spinner.getValue());
    }//GEN-LAST:event_userScaleNoteOffset08SpinnerAncestorAdded

    private void userScaleNoteOffset09SpinnerAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_userScaleNoteOffset09SpinnerAncestorAdded
        GlobalParameters globalParameters = nanoPad2.getGlobalParameters();
        globalParameters.setNoteOffset09((int)userScaleNoteOffset09Spinner.getValue());
    }//GEN-LAST:event_userScaleNoteOffset09SpinnerAncestorAdded

    private void userScaleNoteOffset10SpinnerAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_userScaleNoteOffset10SpinnerAncestorAdded
        GlobalParameters globalParameters = nanoPad2.getGlobalParameters();
        globalParameters.setNoteOffset10((int)userScaleNoteOffset10Spinner.getValue());
    }//GEN-LAST:event_userScaleNoteOffset10SpinnerAncestorAdded

    private void userScaleNoteOffset11SpinnerAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_userScaleNoteOffset11SpinnerAncestorAdded
        GlobalParameters globalParameters = nanoPad2.getGlobalParameters();
        globalParameters.setNoteOffset11((int)userScaleNoteOffset11Spinner.getValue());
    }//GEN-LAST:event_userScaleNoteOffset11SpinnerAncestorAdded

    private void userScaleNoteOffset12SpinnerAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_userScaleNoteOffset12SpinnerAncestorAdded
        GlobalParameters globalParameters = nanoPad2.getGlobalParameters();
        globalParameters.setNoteOffset12((int)userScaleNoteOffset12Spinner.getValue());
    }//GEN-LAST:event_userScaleNoteOffset12SpinnerAncestorAdded

    private void manualMidiPortSelectionCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_manualMidiPortSelectionCheckBoxActionPerformed
        preferences.setManualMidiPortSelection(manualMidiPortSelectionCheckBox.isSelected());
    }//GEN-LAST:event_manualMidiPortSelectionCheckBoxActionPerformed

    private void midiInChoiceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_midiInChoiceActionPerformed
        preferences.setMidiInPortName((String)midiInChoice.getSelectedItem());
    }//GEN-LAST:event_midiInChoiceActionPerformed

    private void midiOutChoiceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_midiOutChoiceActionPerformed
        preferences.setMidiOutPortName((String)midiOutChoice.getSelectedItem());
    }//GEN-LAST:event_midiOutChoiceActionPerformed

    private void receiveSceneSetAutomaticallyCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_receiveSceneSetAutomaticallyCheckBoxActionPerformed
        preferences.setReceiveSceneSetAutomatically(receiveSceneSetAutomaticallyCheckBox.isSelected());
    }//GEN-LAST:event_receiveSceneSetAutomaticallyCheckBoxActionPerformed

    private void warnBeforeWritingCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_warnBeforeWritingCheckBoxActionPerformed
        preferences.setWarnBeforeWriting(warnBeforeWritingCheckBox.isSelected());
    }//GEN-LAST:event_warnBeforeWritingCheckBoxActionPerformed

    private void globalXYPadCCModeMidiChannelChoiceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_globalXYPadCCModeMidiChannelChoiceActionPerformed
        GlobalParameters globalParameters = nanoPad2.getGlobalParameters();
        globalParameters.setXyPadCCModeMidiChannel(globalXYPadCCModeMidiChannelChoice.getSelectedIndex());
    }//GEN-LAST:event_globalXYPadCCModeMidiChannelChoiceActionPerformed

    private void writeSceneSetMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_writeSceneSetMenuItemActionPerformed
        eventBus.post(new SendSceneSetEvent());
    }//GEN-LAST:event_writeSceneSetMenuItemActionPerformed

    private void receiveSceneSetMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_receiveSceneSetMenuItemActionPerformed
        eventBus.post(new RequestSceneSetEvent());
    }//GEN-LAST:event_receiveSceneSetMenuItemActionPerformed

    private void receiveSceneDataMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_receiveSceneDataMenuItemActionPerformed
        eventBus.post(new RequestSceneDataEvent());
    }//GEN-LAST:event_receiveSceneDataMenuItemActionPerformed

    private void newMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newMenuItemActionPerformed
        nanoPad2.setScenes(new Scene[]{new Scene(), new Scene(), new Scene(), new Scene()});
        nanoPad2.setGlobalParameters(new GlobalParameters());
        updateCurrentTriggerPad();
        updateNanoPad2DisplayPanel();
        updateGlobalData();
    }//GEN-LAST:event_newMenuItemActionPerformed

    private void sendGlobalDataMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sendGlobalDataMenuItemActionPerformed
        eventBus.post(new SendGlobalDataToNanoPad2Event());
    }//GEN-LAST:event_sendGlobalDataMenuItemActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem aboutMenuItem;
    private javax.swing.JLabel bpmLabel;
    private javax.swing.JFileChooser browseFilesChooser;
    private javax.swing.JScrollPane browseFilesScrollPane;
    private javax.swing.JPanel browseTabPanel;
    private javax.swing.JMenuItem clearMenuItem;
    private javax.swing.JMenu communicateMenu;
    private javax.swing.JLabel constantVelocityValueLabel;
    private javax.swing.JPanel controlParamsTabPanel;
    private javax.swing.JList<String> controlsList;
    private javax.swing.JScrollPane controlsScrollPane;
    private javax.swing.JMenuItem copyMenuItem;
    private javax.swing.JMenuItem cutMenuItem;
    private javax.swing.JPanel displaySelectionPanel;
    private javax.swing.JMenu editMenu;
    private javax.swing.JMenu fileMenu;
    private javax.swing.Box.Filler filler1;
    private javax.swing.Box.Filler filler10;
    private javax.swing.Box.Filler filler11;
    private javax.swing.Box.Filler filler12;
    private javax.swing.Box.Filler filler13;
    private javax.swing.Box.Filler filler2;
    private javax.swing.Box.Filler filler3;
    private javax.swing.Box.Filler filler4;
    private javax.swing.Box.Filler filler5;
    private javax.swing.Box.Filler filler6;
    private javax.swing.Box.Filler filler7;
    private javax.swing.Box.Filler filler8;
    private javax.swing.Box.Filler filler9;
    private javax.swing.JLabel gateSpeedLabel;
    private javax.swing.JSpinner globalCommonBpmSpinCtrl;
    private javax.swing.JSpinner globalCommonConstantVelocityValueSpinCtrl;
    private javax.swing.JComboBox<String> globalCommonMidiChannelChoice;
    private javax.swing.JComboBox<String> globalCommonMidiClockChoice;
    private javax.swing.JPanel globalCommonParamsPanel;
    private javax.swing.JComboBox<String> globalCommonVelocityCurveChoice;
    private javax.swing.JLabel globalMidiChannelLabel;
    private javax.swing.JPanel globalParamsTabPanel;
    private javax.swing.JTabbedPane globalParamsTabbedPane;
    private javax.swing.JPanel globalTouchScaleParamsPanel;
    private javax.swing.JPanel globalUserScaleParamsPanel;
    private javax.swing.JComboBox<String> globalXYPadCCModeMidiChannelChoice;
    private javax.swing.JSpinner globalXYPadCCModeOffValueSpinCtrl;
    private javax.swing.JSpinner globalXYPadCCModeOnValueSpinCtrl;
    private javax.swing.JSpinner globalXYPadCCModeTouchEnableCCNumberSpinCtrl;
    private javax.swing.JComboBox<String> globalXYPadCCModeTouchEnableChoice;
    private javax.swing.JComboBox<String> globalXYPadCCModeXAssignTypeChoice;
    private javax.swing.JLabel globalXYPadCCModeXAssignTypeLabel;
    private javax.swing.JSpinner globalXYPadCCModeXCCNumberSpinCtrl;
    private javax.swing.JComboBox<String> globalXYPadCCModeXPolarityChoice;
    private javax.swing.JComboBox<String> globalXYPadCCModeYAssignTypeChoice;
    private javax.swing.JSpinner globalXYPadCCModeYCCNumberSpinCtrl;
    private javax.swing.JComboBox<String> globalXYPadCCModeYPolarityChoice;
    private javax.swing.JPanel globalXYPadParamsPanel;
    private javax.swing.JComboBox<String> globalXYPadTouchScaleGateSpeedChoice;
    private javax.swing.JComboBox<String> globalXYPadTouchScaleMidiChannelChoice;
    private javax.swing.JSpinner globalXYPadTouchScaleNoteOnVelocitySpinCtrl;
    private javax.swing.JComboBox<String> globalXYPadTouchScaleYAxisCCEnableChoice;
    private javax.swing.JSpinner globalXYPadTouchScaleYAxisCCNumberSpinCtrl;
    private javax.swing.JComboBox<String> globalXYPadTouchScaleYAxisPolarityChoice;
    private javax.swing.JMenu helpMenu;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JMenuItem loadSceneDataMenuItem;
    private javax.swing.JCheckBox manualMidiPortSelectionCheckBox;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JLabel midiChannelLabel;
    private javax.swing.JLabel midiClockLabel;
    private javax.swing.JComboBox<String> midiInChoice;
    private javax.swing.JLabel midiInLabel;
    private javax.swing.JComboBox<String> midiOutChoice;
    private javax.swing.JLabel midiOutLabel;
    private javax.swing.JPanel midiPortsPanel;
    private javax.swing.JPanel miscellaneousPanel;
    private javax.swing.JPanel nanopad2DisplayPanel;
    private javax.swing.JPanel nanopad2InstrumentPanel;
    private javax.swing.JMenuItem newMenuItem;
    private javax.swing.JLabel noteOnVelocityLabel;
    private javax.swing.JLabel offValueLabel;
    private javax.swing.JLabel onValueLabel;
    private javax.swing.JMenuItem openMenuItem;
    private javax.swing.JComboBox<String> parameterDisplayChoice;
    private javax.swing.JPanel parameterPanel;
    private javax.swing.JTabbedPane parameterTabbedPane;
    private javax.swing.JMenuItem pasteMenuItem;
    private javax.swing.JPanel preferencesTabPanel;
    private javax.swing.JMenuItem receiveSceneDataMenuItem;
    private javax.swing.JCheckBox receiveSceneSetAutomaticallyCheckBox;
    private javax.swing.JMenuItem receiveSceneSetMenuItem;
    private javax.swing.JMenuItem redoMenuItem;
    private javax.swing.JMenuItem saveMenuItem;
    private javax.swing.JMenuItem saveSceneDataMenuItem;
    private javax.swing.JMenuItem saveasMenuItem;
    private javax.swing.JRadioButton scene1RadioButton;
    private javax.swing.JRadioButton scene2RadioButton;
    private javax.swing.JRadioButton scene3RadioButton;
    private javax.swing.JRadioButton scene4RadioButton;
    private javax.swing.JPanel sceneButtonPanel;
    private javax.swing.ButtonGroup sceneSelectorButtonGroup;
    private javax.swing.JMenuItem selectTargetDeviceMenuItem;
    private javax.swing.JMenuItem sendGlobalDataMenuItem;
    private javax.swing.JLabel touchEnableLabel;
    private javax.swing.JLabel touchEnableOffValueLabel;
    private javax.swing.JPanel touchSurfacePanel;
    private javax.swing.JButton triggerPad01Button;
    private javax.swing.JButton triggerPad02Button;
    private javax.swing.JButton triggerPad03Button;
    private javax.swing.JButton triggerPad04Button;
    private javax.swing.JButton triggerPad05Button;
    private javax.swing.JButton triggerPad06Button;
    private javax.swing.JButton triggerPad07Button;
    private javax.swing.JButton triggerPad08Button;
    private javax.swing.JButton triggerPad09Button;
    private javax.swing.JButton triggerPad10Button;
    private javax.swing.JButton triggerPad11Button;
    private javax.swing.JButton triggerPad12Button;
    private javax.swing.JButton triggerPad13Button;
    private javax.swing.JButton triggerPad14Button;
    private javax.swing.JButton triggerPad15Button;
    private javax.swing.JButton triggerPad16Button;
    private javax.swing.JComboBox<String> triggerPadAssignTypeChoice;
    private javax.swing.JCheckBox triggerPadBehaviourCheckBox;
    private javax.swing.JCheckBox triggerPadGateArpEnableCheckBox;
    private javax.swing.JComboBox<String> triggerPadMidiChoice;
    private javax.swing.JComboBox<String> triggerPadNoteCCProgNumber1Choice;
    private javax.swing.JComboBox<String> triggerPadNoteCCProgNumber2Choice;
    private javax.swing.JComboBox<String> triggerPadNoteCCProgNumber3Choice;
    private javax.swing.JComboBox<String> triggerPadNoteCCProgNumber4Choice;
    private javax.swing.JPanel triggerPadParameterGridSizerPanel;
    private javax.swing.JCheckBox triggerPadTouchScaleGateArpEnableCheckBox;
    private javax.swing.JPanel triggerPadsPanel;
    private javax.swing.JMenuItem undoMenuItem;
    private javax.swing.JSpinner userScaleNoteLengthSpinCtrl;
    private javax.swing.JSpinner userScaleNoteOffset01Spinner;
    private javax.swing.JSpinner userScaleNoteOffset02Spinner;
    private javax.swing.JSpinner userScaleNoteOffset03Spinner;
    private javax.swing.JSpinner userScaleNoteOffset04Spinner;
    private javax.swing.JSpinner userScaleNoteOffset05Spinner;
    private javax.swing.JSpinner userScaleNoteOffset06Spinner;
    private javax.swing.JSpinner userScaleNoteOffset07Spinner;
    private javax.swing.JSpinner userScaleNoteOffset08Spinner;
    private javax.swing.JSpinner userScaleNoteOffset09Spinner;
    private javax.swing.JSpinner userScaleNoteOffset10Spinner;
    private javax.swing.JSpinner userScaleNoteOffset11Spinner;
    private javax.swing.JSpinner userScaleNoteOffset12Spinner;
    private javax.swing.JLabel velocityCurveLabel;
    private javax.swing.JCheckBox warnBeforeWritingCheckBox;
    private javax.swing.JMenuItem writeSceneDataMenuItem;
    private javax.swing.JMenuItem writeSceneSetMenuItem;
    private javax.swing.JLabel xCCNumberLabel;
    private javax.swing.JLabel xPolarityLabel;
    private javax.swing.JLabel xyPadMidiChannelLabel;
    private javax.swing.JLabel yAssignTypeLabel;
    private javax.swing.JLabel yAxisCCEnableLabel;
    private javax.swing.JLabel yAxisCCNumberLabel;
    private javax.swing.JLabel yAxisPolarityLabel;
    private javax.swing.JLabel yCCNumberLabel;
    private javax.swing.JLabel yPolarityLabel;
    // End of variables declaration//GEN-END:variables
}
