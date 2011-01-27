//JVMPanel.java
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
import com.installshield.wizard.service.jvm.JVMService;
import javax.swing.*;
import javax.swing.event.*;

public class JVMPanel extends ExtendedWizardPanel implements ActionListener, ListSelectionListener{
    
    Vector jdkHomeList = new Vector();
    
    private javax.swing.JTextField inputTextField;
    private javax.swing.JButton browseButton;
    private javax.swing.JLabel inputlabel;
    private javax.swing.JLabel listLabel;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JPanel inputPanel;
    private javax.swing.JTextArea infoTextArea;
    private javax.swing.JTextArea jdkListTextArea;
    
    private javax.swing.JPanel listLabelPanel;
    private javax.swing.JPanel listPanel;
    private javax.swing.JScrollPane listScrollPane;
    private javax.swing.JList jdkList;
    
    JDKSearchAction searchJdk = null;
 
    String jdkHome = "";
    
    public void build(WizardBuilderSupport support) {
        try {
            support.putClass(RunCommand.class.getName());
            //support.putClass(JDKSearchAction.class.getName());
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }
    
    protected void initialize() {
        super.initialize();
        
        // Get the object of JDKSearchAction set in the system property (set by JDKSearchAction)
        // and retrieve the searched JDK Home List
        
        searchJdk = (JDKSearchAction) System.getProperties().get("JDKSearchAction"); //NOI18N
        if (searchJdk != null) jdkHomeList = searchJdk.getJdkList();
        
        String description = "";
        
        if (jdkHomeList.size() != 0){
            description = resolveString("$L(com.nayaware.netinstaller.InstallerResources,JVM_FOUND_DESCRIPTION)");
        } else {
            description = resolveString("$L(com.nayaware.netinstaller.InstallerResources,JVM_NOT_FOUND_DESCRIPTION)");
        }
        
        mainPanel = new javax.swing.JPanel();
        mainPanel.setBorder(new javax.swing.border.EmptyBorder(new java.awt.Insets(10, 10, 10, 10)));
        mainPanel.setLayout(new java.awt.BorderLayout());
        
        infoTextArea = new javax.swing.JTextArea();
        infoTextArea.setFont(new java.awt.Font("Dialog", 0, 14));
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
        String text1 = resolveString("$L(com.nayaware.netinstaller.InstallerResources,JVM_SPECIFY_LABEL)");
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
        
        mainPanel.add(inputPanel, java.awt.BorderLayout.CENTER);
        
        listPanel = new javax.swing.JPanel();
        listPanel.setLayout(new java.awt.BorderLayout());
        listPanel.setBorder(new javax.swing.border.EmptyBorder(new java.awt.Insets(5, 5, 5, 5)));
        listPanel.setPreferredSize(new java.awt.Dimension(400, 150));
        
        listLabelPanel = new javax.swing.JPanel();
        listLabelPanel.setLayout(new java.awt.BorderLayout());
        
        listLabel = new javax.swing.JLabel();
        listLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        listLabel.setText(resolveString("$L(com.nayaware.netinstaller.InstallerResources,JDK_LIST_LABEL)"));
        
        listLabelPanel.add(listLabel, java.awt.BorderLayout.CENTER);
        
        listPanel.add(listLabelPanel, java.awt.BorderLayout.NORTH);
        
        listScrollPane = new javax.swing.JScrollPane();
        listScrollPane.setPreferredSize(new java.awt.Dimension(300, 100));
        
        if (jdkHomeList.size() != 0) {
            jdkList = new javax.swing.JList(jdkHomeList);
            jdkList.setSelectedIndex(0);
            inputTextField.setText((String)jdkList.getSelectedValue());
            logEvent(this, Log.DBG, "Installer Selected Java Home" + jdkList.getSelectedValue());
            jdkList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
            jdkList.addListSelectionListener(this);
            jdkList.setBackground(getContentPane().getBackground());
            listScrollPane.setViewportView(jdkList);
        } else {
            jdkListTextArea = new javax.swing.JTextArea();
            jdkListTextArea.setText(resolveString("$L(com.nayaware.netinstaller.InstallerResources,JDK_SEARCH_FAILED_MESSAGE)"));
            jdkListTextArea.setWrapStyleWord(true);
            jdkListTextArea.setLineWrap(true);
            jdkListTextArea.setEditable(false);
            jdkListTextArea.setColumns(30);
            jdkListTextArea.setRows(2);
            jdkListTextArea.setBackground(getContentPane().getBackground());
            jdkListTextArea.setFont(new java.awt.Font("Dialog", 0, 16));
            jdkListTextArea.setBorder(new javax.swing.border.EmptyBorder(new java.awt.Insets(25, 25, 5, 5)));
            listScrollPane.setViewportView(jdkListTextArea);
        }
        
        listPanel.add(listScrollPane, java.awt.BorderLayout.CENTER);
        
        mainPanel.add(listPanel, java.awt.BorderLayout.SOUTH);
        
        mainPanel.setBackground(getContentPane().getBackground());
        inputPanel.setBackground(getContentPane().getBackground());
        infoTextArea.setBackground(getContentPane().getBackground());
        browseButton.setBackground(getContentPane().getBackground());
        inputlabel.setBackground(getContentPane().getBackground());
        listLabelPanel.setBackground(getContentPane().getBackground());
        listPanel.setBackground(getContentPane().getBackground());
        listScrollPane.setBackground(getContentPane().getBackground());
        getContentPane().add(mainPanel, BorderLayout.CENTER);
    }
    
    public boolean entered(WizardBeanEvent event){
        inputTextField.requestFocus();
        return true;
    }
    
    public boolean queryExit(WizardBeanEvent event) {
        return checkJVM(inputTextField.getText());
    }
    
    private boolean checkJVM(String jdkHome) {
        if (searchJdk != null){
            if (!searchJdk.checkJdkHome(jdkHome)) {
                String invalidMsg = resolveString("$L(com.nayaware.netinstaller.InstallerResources,INVALID_JDK_HOME_MSG)");
                String invalidTitle = resolveString("$L(com.nayaware.netinstaller.InstallerResources,INVALID_JDK_HOME_TITLE)");
                String okString = "$L(com.installshield.wizard.i18n.WizardResources, ok)";
                Frame parent = ((AWTWizardUI)getWizard().getUI()).getFrame();
                MessageDialog d= new MessageDialog(parent,invalidMsg,invalidTitle, new String[]{resolveString(okString)});
                d.setVisible(true);
                return false;
            }
        }
        System.getProperties().put("jdkHome",jdkHome);
        logEvent(this, Log.DBG, "User Selected Java Home" + System.getProperties().get("jdkHome"));
        return true;
    }
    
    public void valueChanged(javax.swing.event.ListSelectionEvent listSelectionEvent) {
        inputTextField.setText((String)jdkList.getSelectedValue());
    }
    
    public void actionPerformed(ActionEvent event) {
        Object obj = event.getSource();
        if(obj instanceof javax.swing.JButton) {
            javax.swing.JFileChooser chooser = new javax.swing.JFileChooser();
            chooser.setFileSelectionMode(javax.swing.JFileChooser.DIRECTORIES_ONLY);
            chooser.setBackground(((javax.swing.JButton)obj).getBackground());
            int returnVal = chooser.showOpenDialog(new javax.swing.JFrame());
            if(returnVal == javax.swing.JFileChooser.APPROVE_OPTION) {
                inputTextField.setText(chooser.getSelectedFile().getAbsolutePath());
            }
        }
    }
}
