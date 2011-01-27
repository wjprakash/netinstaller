// FCCPathPanel.java
// Last Modified on Feb 22, 2002 - Winston Prakash

package com.nayaware.netinstaller;

import java.io.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;
import com.installshield.wizard.awt.*;
import com.installshield.product.*;
import com.installshield.product.i18n.*;
import com.installshield.wizardx.ui.*;
import com.installshield.wizardx.panels.ExtendedWizardPanel;
import com.installshield.wizard.*;
import com.installshield.wizard.service.*;
import com.installshield.wizard.service.file.*;
import com.installshield.product.service.product.*;
import com.installshield.util.*;
import com.installshield.wizardx.ui.*;
import com.installshield.wizard.console.*;
import com.installshield.wizard.awt.*;
import javax.swing.*;
import javax.swing.event.*;

public class FCCPathPanel extends ExtendedWizardPanel implements ActionListener{
    
    private javax.swing.JTextField inputTextField;
    private javax.swing.JButton browseButton;
    private javax.swing.JLabel inputlabel;
    private javax.swing.JLabel listLabel;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JPanel inputPanel;
    private javax.swing.JTextArea infoTextArea;
    private javax.swing.JLabel exPathLabel;
  
    public void build(WizardBuilderSupport support) {
        try {
            support.putClass(RunCommand.class.getName());
        } catch (Exception ex){
            logEvent(this, Log.ERROR, ex);
        }
    }
    
    public boolean queryEnter(WizardBeanEvent event) {
        // Find out if Solaris Developer Modules are selected for install
        boolean selected = false;
        try {
            String productURL = ProductService.DEFAULT_PRODUCT_SOURCE;
            ProductService productService = (ProductService)getService(ProductService.NAME);
            ProductTree productTree = productService.getSoftwareObjectTree(productURL);
            StandardProductTreeIterator iterator = new StandardProductTreeIterator(productTree);
            for(ProductBean productbean = iterator.getNext(iterator.begin()); productbean != iterator.end(); productbean = iterator.getNext(productbean)) {
                logEvent(this, Log.DBG, "Product bean Display Name: " + productbean.getDisplayName());                
                if(productbean.getDisplayName().equals("solaris_dev")){
                    if(productbean.isActive()) selected = true;
                }
            }
            
        } catch (ServiceException ex) {
            logEvent(this, Log.ERROR, ex);
        }
        return selected;
    }
    
    public boolean entered(WizardBeanEvent event){
        inputTextField.requestFocus();
        return true;
    }
    
    protected void initialize() {
        super.initialize();
        
        String description = resolveString("$L(com.nayaware.netinstaller.InstallerResources,FCC_PATH_DESCRIPTION)");
        
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
        infoTextArea.setRows(6);
        mainPanel.add(infoTextArea, java.awt.BorderLayout.NORTH);
        
        inputPanel = new javax.swing.JPanel();
        java.awt.GridBagConstraints gridBagConstraints;
        inputPanel.setLayout(new java.awt.GridBagLayout());
        inputPanel.setPreferredSize(new java.awt.Dimension(229, 70));
        
        inputlabel = new javax.swing.JLabel();
        inputlabel.setPreferredSize(new java.awt.Dimension(250, 24));
        String text1 = resolveString("$L(com.nayaware.netinstaller.InstallerResources,FCC_SPECIFY_LABEL)");
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
        
        browseButton = new javax.swing.JButton();
        browseButton.setText(resolveString("$L(com.nayaware.netinstaller.InstallerResources,BUTTON_BROWSE)"));
        //browseButton.setMinimumSize(new java.awt.Dimension(150, 35));
        //browseButton.setPreferredSize(new java.awt.Dimension(250, 35));
        browseButton.addActionListener(this);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 3);
        inputPanel.add(browseButton, gridBagConstraints);
        
        exPathLabel = new javax.swing.JLabel();
        exPathLabel.setFont(new java.awt.Font("Dialog", 0, 13));
        String exPathText = "(e.g. /net/mach1/dist/SUNWspro)";
        exPathLabel.setText(exPathText);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 3);
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        inputPanel.add(exPathLabel, gridBagConstraints);
        
        mainPanel.add(inputPanel, java.awt.BorderLayout.CENTER);
        
        mainPanel.setBackground(getContentPane().getBackground());
        inputPanel.setBackground(getContentPane().getBackground());
        infoTextArea.setBackground(getContentPane().getBackground());
        browseButton.setBackground(getContentPane().getBackground());
        inputlabel.setBackground(getContentPane().getBackground());
        
        getContentPane().add(mainPanel, BorderLayout.CENTER);
    }
 
    public void execute(WizardBeanEvent event) {
        String uiMode = (String) System.getProperties().get("uiMode");
        if(uiMode == null) uiMode="gui";
        
        // Check if user specified fccHome
        
        String fccHome =  resolveString("$J(fccHome)");
        if (fccHome == null) fccHome = "";
        
        if((uiMode.equals("silent"))){
            if (fccHome.equals("")) {
                System.out.println(resolveString("$L(com.nayaware.netinstaller.InstallerResources,FCC_NOT_SPECIFIED_MSG)"));
                System.exit(-1);
            }
            File fccDir = new File(fccHome);
            if (fccDir.exists()){
                if (!fccDir.isDirectory()) {
                    System.out.println(resolveString("$L(com.nayaware.netinstaller.InstallerResources,FCC_INVALID_MSG)"));
                    System.exit(-1);
                }
            }else{
                System.out.println(resolveString("$L(com.nayaware.netinstaller.InstallerResources,FCC_NOT_EXIST_MSG)"));
                System.exit(-1);
            }
        }
        System.getProperties().put("fccPath",fccHome);       
        logEvent(this, Log.DBG, "User specifid FCC Path: " + fccHome);           
    }
    
    public boolean queryExit(WizardBeanEvent event) {
        return checkFCC(inputTextField.getText().trim());
    }
    
    private boolean checkFCC(String fccPath) {
        File fccFile = new File(fccPath);
        if (!fccPath.equals("")){
            if (!fccFile.exists()) {
                String invalidMsg = resolveString("$L(com.nayaware.netinstaller.InstallerResources,INVALID_FCC_PATH_MSG)");
                String invalidTitle = resolveString("$L(com.nayaware.netinstaller.InstallerResources,INVALID_FCC_PATH_TITLE)");
                String okString = "$L(com.installshield.wizard.i18n.WizardResources, ok)";
                Frame parent = ((AWTWizardUI)getWizard().getUI()).getFrame();
                MessageDialog d= new MessageDialog(parent,invalidMsg,invalidTitle, new String[]{resolveString(okString)});
                d.setVisible(true);
                return false;
            }}
        else{
            String notSpecifiedMsg = resolveString("$L(com.nayaware.netinstaller.InstallerResources,FCC_PATH_NOT_SPECIFIED_MSG)");
            String notSpecifiedTitle = resolveString("$L(com.nayaware.netinstaller.InstallerResources,FCC_PATH_NOT_SPECIFIED_TITLE)");
            String okString = "$L(com.installshield.wizard.i18n.WizardResources, ok)";
            Frame parent = ((AWTWizardUI)getWizard().getUI()).getFrame();
            MessageDialog d= new MessageDialog(parent,notSpecifiedMsg,notSpecifiedTitle, new String[]{resolveString(okString)});
            d.setVisible(true);
            return true;
        }
        System.getProperties().put("fccPath",fccPath);
        logEvent(this, Log.DBG, "User specifid FCC Path: " + fccPath); 
        return true;
    }
    
    public void actionPerformed(ActionEvent event) {
        Object obj = event.getSource();
        if(obj instanceof javax.swing.JButton) {
            javax.swing.JFileChooser chooser = new javax.swing.JFileChooser();
            chooser.setFileSelectionMode(javax.swing.JFileChooser.DIRECTORIES_ONLY);
            chooser.setBackground(((javax.swing.JButton)obj).getBackground());
            chooser.setSelectedFile(inputTextField.getText());
            int returnVal = chooser.showOpenDialog(new javax.swing.JFrame());
            if(returnVal == javax.swing.JFileChooser.APPROVE_OPTION) {
                inputTextField.setText(chooser.getSelectedFile().getAbsolutePath());
            }
        }
    }
}
