// JDKSelectionPanel.java
// Author Winston Prakash

package com.nayaware.netinstaller;

import java.io.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class JDKSelectionPanel extends WizardPanel implements ActionListener, ListSelectionListener{
    
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
    
    private javax.swing.JLabel infoLabel;
    
    private javax.swing.JLabel searchLabel;
    
    JDKSearchAction jdkSearch = new JDKSearchAction();
    
    String jdkHome = "";
    String jvmPath = "";
    
    String description1 = "Installer found the following JDK(tm). J2SE(tm) 1.4.0 is the recommended JDK. We strongly recommend that you specify it here.";
    String description2 = "Installer could not find a suitable JDK(tm). J2SE(tm) 1.4.0 is the recommended JDK. We strongly recommend that you specify it here.";
    String description = "";
    String descriptionText = description1;    
    
    boolean initialized = false;
    
    /** Creates a new instance of JVMPanel */
    public JDKSelectionPanel() {
        infoLabel = new JLabel("Searching for suitable JDK. Please wait ...");
        add(infoLabel,BorderLayout.CENTER);
    }
    
    public boolean hasAction(){
        return true;
    }
    
    public boolean executeAction(){
        jdkSearch.execute();
        jdkHomeList = jdkSearch.getJdkList();
        if (jdkHomeList.size() != 0)
            description = description1;
        else
            description = description2;
        return true;
    }
    
    public void displayPanel(){
        if(initialized) return;
        infoLabel.setVisible(false);
        remove(infoLabel);
        initialize();
    }
    
    protected void initialize() {
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
        infoTextArea.setBackground(getBackground());
        mainPanel.add(infoTextArea, java.awt.BorderLayout.NORTH);
        
        inputPanel = new javax.swing.JPanel();
        java.awt.GridBagConstraints gridBagConstraints;
        inputPanel.setLayout(new java.awt.GridBagLayout());
        inputPanel.setPreferredSize(new java.awt.Dimension(229, 70));
        
        inputlabel = new javax.swing.JLabel();
        inputlabel.setPreferredSize(new java.awt.Dimension(250, 24));
        String inputLabelText = "JDK Home";
        inputlabel.setText(inputLabelText);
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
        browseButton.setText("Browse");
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
        listLabel.setText("List of searched JDKs (1.3.1 & 1.4.0)");
        
        listLabelPanel.add(listLabel, java.awt.BorderLayout.CENTER);
        
        listPanel.add(listLabelPanel, java.awt.BorderLayout.NORTH);
        
        listScrollPane = new javax.swing.JScrollPane();
        listScrollPane.setPreferredSize(new java.awt.Dimension(300, 100));
        
        if (jdkHomeList.size() != 0) {
            jdkList = new javax.swing.JList(jdkHomeList);
            jdkList.setSelectedIndex(0);
            inputTextField.setText((String)jdkList.getSelectedValue());
            jdkList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
            jdkList.addListSelectionListener(this);
            jdkList.setBackground(getBackground());
            listScrollPane.setViewportView(jdkList);
        } else {
            jdkListTextArea = new javax.swing.JTextArea();
            jdkListTextArea.setText("JDK search could not find any JDK");
            jdkListTextArea.setWrapStyleWord(true);
            jdkListTextArea.setLineWrap(true);
            jdkListTextArea.setEditable(false);
            jdkListTextArea.setColumns(30);
            jdkListTextArea.setRows(2);
            jdkListTextArea.setFont(new java.awt.Font("Dialog", 0, 16));
            jdkListTextArea.setBackground(getBackground());
            jdkListTextArea.setBorder(new javax.swing.border.EmptyBorder(new java.awt.Insets(25, 25, 5, 5)));
            listScrollPane.setViewportView(jdkListTextArea);
        }
        
        listPanel.add(listScrollPane, java.awt.BorderLayout.CENTER);
        
        mainPanel.add(listPanel, java.awt.BorderLayout.SOUTH);
        add(mainPanel, BorderLayout.CENTER);
        initialized = true;
    }
    
    public boolean queryExit() {
        return checkJVM(inputTextField.getText());
    }
    
    private boolean checkJVM(String jdkHome) {
        if (jdkSearch != null){
            if (!jdkSearch.checkJdkHome(jdkHome)) {
                String invalidMsg = "Specified JDK is invalid";
                String invalidTitle = "Invalid JDK Home";
                javax.swing.JOptionPane.showMessageDialog(this, invalidMsg, invalidTitle, javax.swing.JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }
        System.getProperties().put("jvmFile",jvmPath);
        System.getProperties().put("jdkHome",jdkHome);
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
            chooser.setSelectedFile(new File(inputTextField.getText()));                
            int returnVal = chooser.showOpenDialog(new javax.swing.JFrame());
            if(returnVal == javax.swing.JFileChooser.APPROVE_OPTION) {
                inputTextField.setText(chooser.getSelectedFile().getAbsolutePath());
            }
        }
    }
}
