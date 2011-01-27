// SerialNumberPanel.java
// Last Modified on Feb 22, 2002 - Winston Prakash

package com.nayaware.netinstaller;
import java.io.*;
import java.awt.*;
import java.util.*;
import java.text.*;
import java.awt.event.*;
import com.installshield.util.*;
import com.installshield.wizard.*;
import com.installshield.wizard.awt.*;
import com.installshield.wizard.console.*;
import com.installshield.wizardx.panels.*;
import com.installshield.wizardx.ui.*;
import com.installshield.wizardx.i18n.*;
import com.installshield.wizard.service.system.*;
import com.installshield.archive.*;
import java.io.*;

public class SerialNumberPanel extends ExtendedWizardPanel {
    private javax.swing.JTextField inputTextField;
    private javax.swing.JLabel inputlabel;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JPanel inputPanel;
    private javax.swing.JTextArea infoTextArea;
    
    private String prefix = "FJE400";
    
    private com.sun.forte.licen.SerialNumber serialNum = new com.sun.forte.licen.SerialNumber();
    
    public void build(WizardBuilderSupport support) {
        try {
            support.putArchive( "serapi.jar", new AllArchiveFilter() );
        } catch (Exception ex){
            logEvent(this, Log.ERROR, ex);
            logEvent(this, Log.DBG, ex);
        }
    }
    
    public void initialize() {
        super.initialize();
        
        String description = resolveString("$L(com.nayaware.netinstaller.InstallerResources,SER_NUM_MSG)");
        mainPanel = new javax.swing.JPanel();
        mainPanel.setBorder(new javax.swing.border.EmptyBorder(new java.awt.Insets(10, 10, 10, 10)));
        mainPanel.setLayout(new java.awt.BorderLayout());
        
        infoTextArea = new javax.swing.JTextArea();
        infoTextArea.setFont(new java.awt.Font("Dialog", 0, 13));
        infoTextArea.setText(description);
        infoTextArea.setWrapStyleWord(true);
        infoTextArea.setLineWrap(true);
        infoTextArea.setEditable(false);
        infoTextArea.setColumns(30);
        infoTextArea.setRows(4);
        mainPanel.add(infoTextArea, java.awt.BorderLayout.NORTH);
        
        inputPanel = new javax.swing.JPanel();
        java.awt.GridBagConstraints gridBagConstraints;
        inputPanel.setLayout(new java.awt.GridBagLayout());
        inputPanel.setPreferredSize(new java.awt.Dimension(229, 70));
        
        inputlabel = new javax.swing.JLabel();
        inputlabel.setPreferredSize(new java.awt.Dimension(250, 24));
        String text1 = resolveString("$L(com.nayaware.netinstaller.InstallerResources,SER_NUM)");
        inputlabel.setText(text1);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 3);
        inputPanel.add(inputlabel, gridBagConstraints);
        
        inputTextField = new javax.swing.JTextField();
        inputTextField.setPreferredSize(new java.awt.Dimension(250, 35));
        inputTextField.setMinimumSize(new java.awt.Dimension(250, 24));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 3);
        inputPanel.add(inputTextField, gridBagConstraints);
        
        
        mainPanel.add(inputPanel, java.awt.BorderLayout.CENTER);
        
        mainPanel.setBackground(getContentPane().getBackground());
        inputPanel.setBackground(getContentPane().getBackground());
        infoTextArea.setBackground(getContentPane().getBackground());
        inputlabel.setBackground(getContentPane().getBackground());
        
        getContentPane().add(mainPanel, BorderLayout.CENTER);
        
        if (System.getProperties().get("serialNumber") != null) {
            String value = (String)System.getProperties().get("serialNumber");
            if (value.equalsIgnoreCase("trial")) value=getTrialSerialNumber();
            inputTextField.setText(value);
        }
    }
    
    public boolean entered(WizardBeanEvent event) {
        inputTextField.requestFocus();
        return true;
    }
    
    public boolean queryExit(WizardBeanEvent event) {
        String serNum = inputTextField.getText().trim();
        serialNum.setSerialNo(serNum);
        if (!serialNum.isValid()) {
            if (serNum.equals("")) {
                String txt = resolveString("$L(com.nayaware.netinstaller.InstallerResources,SER_NUM_GENER)");
                String message = resolveString("$L(com.nayaware.netinstaller.InstallerResources,SER_NUM_GENER_MSG)");
                String btnYes = resolveString("$L(com.nayaware.netinstaller.wizard.i18n.WizardResources, yes)");
                String btnNo = resolveString("$L(com.nayaware.netinstaller.wizard.i18n.WizardResources, no)");
                
                Object[] objs = new Object[] { btnYes, btnNo };
                try {
                    Object obj = getWizard().getServices().getUserInput(txt,message, objs, btnYes);
                    if (obj == btnYes) {
                        serNum=getTrialSerialNumber();
                        logEvent(this, Log.DBG, "Generated Trial Serial Number = " + serNum);
                        inputTextField.setText(serNum);
                    }
                } catch (Exception ex) {
                    logEvent(this, Log.ERROR, ex);
                    logEvent(this, Log.DBG, ex);
                }
            }
            else {
                String message = resolveString("$L(com.nayaware.netinstaller.InstallerResources,INVALID_SER_NUM)");
                String txt = resolveString("$L(com.nayaware.netinstaller.InstallerResources,ERROR_SER_NUM)");
                String btnOk = resolveString("$L(com.nayaware.netinstaller.wizard.i18n.WizardResources, ok)");
                
                Frame parent = ((AWTWizardUI)getWizard().getUI()).getFrame();
                MessageDialog d= new MessageDialog(parent,message,txt, new String[]{btnOk});
                d.setVisible(true);
            }
            return false;
        }
        else {
            System.getProperties().put("serialNumber",serNum);
            logEvent(this, Log.DBG, "Serial Number - " + serNum);
        }
        return true;
    }
    
    // For EA hard code this with serial number that would expire on
    // Aug 31 2002
    
    public String getTrialSerialNumber(){
        return "FJE400-999C9IEN9-028635073";
    }
    
    public String getPrefix() {
        return prefix;
    }
    
    public void setPrefix(String prefix) {
        this.prefix=prefix;
    }
}

