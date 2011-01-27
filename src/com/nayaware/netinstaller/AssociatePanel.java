// AssociatePanel.java
// Last Modified on Feb 22, 2002 - Winston Prakash

package com.nayaware.netinstaller;

import java.io.*;
import java.awt.*;
import java.awt.event.*;

import com.installshield.util.*;
import com.installshield.wizard.*;
import com.installshield.wizard.awt.*;
import com.installshield.wizard.console.*;
import com.installshield.wizardx.panels.*;
import com.installshield.wizardx.ui.*;
import com.installshield.wizardx.i18n.*;
import com.installshield.wizard.service.system.*;

public class AssociatePanel extends ExtendedWizardPanel implements ItemListener{
    private boolean associateJava = true;
    private boolean associateNBM = true;
    
    private String result = null;
    private String errorMessage = null;
    
    private javax.swing.JCheckBox cbAssociateNbm;
    private javax.swing.JCheckBox cbAssociateJava;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JPanel jPanel1;
    
    public void initialize( ) {
        super.initialize( );
        
        String text1 = resolveString("$L(com.nayaware.netinstaller.InstallerResources,ASSOCIATION_QUESTION)");
        String text2 = resolveString("$L(com.nayaware.netinstaller.InstallerResources,ASSOCIATION_JAVA_RESPONSE)");
        String text3 = resolveString("$L(com.nayaware.netinstaller.InstallerResources,ASSOCIATION_NBM_RESPONSE)");
        String text = resolveString("$L(com.nayaware.netinstaller.InstallerResources,ASSOCIATION)");
        
        setTitle(text);
        
        java.awt.GridBagConstraints gridBagConstraints;
        
        jPanel1 = new javax.swing.JPanel();
        
        jTextArea1 = new javax.swing.JTextArea();
        cbAssociateJava = new javax.swing.JCheckBox();
        cbAssociateJava.setSelected(true);
        cbAssociateNbm = new javax.swing.JCheckBox();
        cbAssociateNbm.setSelected(true);
        
        jPanel1.setLayout(new java.awt.GridBagLayout());
        
        jPanel1.setBorder(new javax.swing.border.EmptyBorder(new java.awt.Insets(10, 10, 10, 10)));
        jTextArea1.setWrapStyleWord(true);
        jTextArea1.setLineWrap(true);
        jTextArea1.setEditable(false);
        jTextArea1.setColumns(40);
        jTextArea1.setRows(2);
        jTextArea1.setFont(new java.awt.Font("Dialog", 0, 14));
        jTextArea1.setText(text1);
        
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        jPanel1.add(jTextArea1, gridBagConstraints);
        
        cbAssociateJava.setText(text2);
        cbAssociateJava.addItemListener(this);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 0, 0);
        jPanel1.add(cbAssociateJava, gridBagConstraints);
        
        cbAssociateNbm.setText(text3);
        cbAssociateNbm.addItemListener(this);
        jPanel1.add(cbAssociateNbm, new java.awt.GridBagConstraints());
        
        jPanel1.setBackground(getContentPane().getBackground());
        jTextArea1.setBackground(getContentPane().getBackground());
        cbAssociateJava.setBackground(getContentPane().getBackground());
        cbAssociateNbm.setBackground(getContentPane().getBackground());
        
        getContentPane().add(jPanel1, BorderLayout.NORTH);
    }
    
    public void exited(WizardBeanEvent event) {
        if(associateJava)
            System.getProperties().put("associateJava","yes");
        else
            System.getProperties().put("associateJava","no");
        if(associateNBM)
            System.getProperties().put("associateNBM","yes");
        else
            System.getProperties().put("associateNBM","no");
        logEvent(this, Log.DBG, "Associate Java :" + System.getProperties().get("associateJava"));
        logEvent(this, Log.DBG, "Associate Java :" + System.getProperties().get("associateNBM"));
    }
    
    public void itemStateChanged(java.awt.event.ItemEvent event) {
        Object source = event.getSource();
        if (source.equals(cbAssociateJava)) {
            associateJava = cbAssociateJava.isSelected();
        }
        if (source.equals(cbAssociateNbm)) {
            associateNBM = cbAssociateNbm.isSelected();
        }
    }
}