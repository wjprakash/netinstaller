// MainFrame.java
// Author  Winston Prakash


package com.nayaware.netinstaller;

import java.net.*;
import java.io.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class MainFrame extends javax.swing.JFrame implements ActionListener {
    
    Vector wizardPanelList = new Vector();
    
    NavigationPanel navigationPanel = new NavigationPanel();
    
    int currentIndex = 0;
    
    WizardPanel currentPanel = null;
    WizardPanel previousPanel = null;
    
    /** Creates new form RemoteInstaller */
    public MainFrame() {
        initComponents();
        setImagePanel(new LogoPanel());
        setNavigationToForward();
        setNavigationPanel(navigationPanel);
        addWizardPanels();
    }
    
    private void addWizardPanels(){
        wizardPanelList.add(new WelcomePanel());
        //wizardPanelList.add(new JDKSelectionPanel());
        wizardPanelList.add(new EditionSelectionPanel());
        //wizardPanelList.add(new LicensePanel());
        //wizardPanelList.add(new SerialNumberPanel());
        //wizardPanelList.add(new DestinationPanel());
        wizardPanelList.add(new ComponentSelectionPanel());
        //wizardPanelList.add(new FCCPathPanel());
        wizardPanelList.add(new PreInstallSummaryPanel());
        wizardPanelList.add(new InstallPanel());
        wizardPanelList.add(new PostInstallSummaryPanel());
        setWizardPanel((WizardPanel)wizardPanelList.get(0));
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {//GEN-BEGIN:initComponents
        java.awt.GridBagConstraints gridBagConstraints;

        imagePanel = new javax.swing.JPanel();
        wizardPanel = new javax.swing.JPanel();
        buttonPanel = new javax.swing.JPanel();

        getContentPane().setLayout(new java.awt.GridBagLayout());

        setTitle("Forte(tm) for Java(tm), release 4.0 Installer");
        setIconImage(getIconImage());
        setName("mainFrame\n");
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                exitForm(evt);
            }
        });

        imagePanel.setMinimumSize(new java.awt.Dimension(100, 200));
        imagePanel.setPreferredSize(new java.awt.Dimension(175, 400));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 2;
        getContentPane().add(imagePanel, gridBagConstraints);

        wizardPanel.setLayout(new java.awt.BorderLayout());

        wizardPanel.setMinimumSize(new java.awt.Dimension(400, 200));
        wizardPanel.setPreferredSize(new java.awt.Dimension(450, 350));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        getContentPane().add(wizardPanel, gridBagConstraints);

        buttonPanel.setMinimumSize(new java.awt.Dimension(100, 20));
        buttonPanel.setPreferredSize(new java.awt.Dimension(450, 50));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        getContentPane().add(buttonPanel, gridBagConstraints);

        pack();
    }//GEN-END:initComponents
    
    private void setNavigationToExit(){
        javax.swing.SwingUtilities.invokeLater( new Runnable() {
            public void run() {
                navigationPanel.setExitNavigation();
            }
        } );
    }
    
    private void setNavigationToForward(){
        javax.swing.SwingUtilities.invokeLater( new Runnable() {
            public void run() {
                navigationPanel.setForwardNavigation();
            }
        } );
    }
    
    private void setNavigationToNormal(){
        javax.swing.SwingUtilities.invokeLater( new Runnable() {
            public void run() {
                navigationPanel.setNormalNavigation();
            }
        } );
    }
    
    private void setNavigationPanelVisible(final boolean flag){
        javax.swing.SwingUtilities.invokeLater( new Runnable() {
            public void run() {
                buttonPanel.setVisible(flag);
            }
        } );
    }
    
    private void setNavigationPanel(NavigationPanel panel){
        panel.addActionListener(this);
        buttonPanel.add(panel);
    }
    
    private void setImagePanel(LogoPanel panel){
        imagePanel.add(new LogoPanel());
    }
    
    public Image getIconImage() {
        URL imageURL = this.getClass().getClassLoader().getResource("com/nayaware/netinstaller/resources/ffj_logo.jpg");
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        return toolkit.getImage(imageURL);
    }
    
    /** Exit the Application */
    private void exitForm(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_exitForm
        System.exit(0);
    }//GEN-LAST:event_exitForm
    
    private void setWizardPanel(WizardPanel panel){
        currentPanel=panel;
        if(previousPanel != null){
            previousPanel.setVisible(false);
            wizardPanel.remove(previousPanel);
        }
        currentPanel.setVisible(true);
        wizardPanel.add(currentPanel,java.awt.BorderLayout.CENTER);
        previousPanel=currentPanel;
    }
    
    public void actionPerformed(ActionEvent event) {
        Object obj = event.getSource();
        if(obj instanceof javax.swing.JButton) {
            JButton button = (JButton) obj;
            String buttonText = button.getText();
            if(buttonText.equals("Cancel")){
                String title = "Cancel Installation";
                String message ="Do you want to exit the installation?";
                int selectedValue = JOptionPane.showConfirmDialog(this, message, title, JOptionPane.YES_NO_OPTION);
                if (selectedValue == JOptionPane.YES_OPTION){
                    System.exit(-1);
                }
            }
            if(buttonText.equals("Exit")){
                System.exit(0);
            }
            if(buttonText.equals("Back")){
                boolean entered=false;
                while (!entered){
                    currentIndex--;
                    if(currentIndex <0) {
                        currentIndex=0;
                        return;
                    }
                    if(currentIndex == 0)
                        setNavigationToForward();
                    else
                        setNavigationToNormal();
                    currentPanel = (WizardPanel)wizardPanelList.get(currentIndex);
                    entered=currentPanel.queryEnter();
                }
            }
            if(buttonText.equals("Next")){
                setNavigationToNormal();
                if(!currentPanel.queryExit()) return;
                boolean entered=false;
                while (!entered){
                    currentIndex++;
                    if(currentIndex > wizardPanelList.size()-1) {
                        currentIndex = wizardPanelList.size()-1;
                        return;
                    }
                    if(currentIndex == wizardPanelList.size()-1) setNavigationToExit();
                    currentPanel = (WizardPanel)wizardPanelList.get(currentIndex);
                    entered=currentPanel.queryEnter();
                }
            }
            //currentPanel = (WizardPanel)wizardPanelList.get(currentIndex);
            setWizardPanel(currentPanel);
            
            if(currentPanel.hasAction()){
                setNavigationPanelVisible(false);
                Thread execThread = new Thread( new Runnable() {
                    public void run() {
                        if(currentPanel.executeAction()){
                            javax.swing.SwingUtilities.invokeLater( new Runnable() {
                                public void run() {
                                    currentPanel.displayPanel();
                                }
                            } );
                        }else{
                            navigationPanel.navigateForward();
                        }
                        setNavigationPanelVisible(true);
                        return;
                    }
                });
                execThread.start();
            }else{
                currentPanel.displayPanel();
            }
        }
    }
    
    public static void main(String args[]) {
        String jdkVersion = System.getProperty("java.version");
        if(jdkVersion.startsWith("1.3.1") || jdkVersion.startsWith("1.4")) {
            SplashScreen.show();
            final MainFrame frame = new MainFrame();
            Dimension dlgDim = frame.getSize();
            Dimension frameDim = new Dimension(0,0);
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            Point loc = new Point(screenSize.width/2, screenSize.height/2);
            loc.translate((frameDim.width - dlgDim.width)/2, (frameDim.height - dlgDim.height)/2);
            loc.x = Math.max(0, Math.min(loc.x, screenSize.width  - dlgDim.width));
            loc.y = Math.max(0, Math.min(loc.y, screenSize.height - dlgDim.height));
            frame.setLocation(loc.x, loc.y);
            int ONE_SECOND = 100;
            javax.swing.Timer timer = new javax.swing.Timer(ONE_SECOND, new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    frame.show();
                    SplashScreen.hide();
                }
            });
            timer.setRepeats(false);
            timer.start();
        } else{
            System.out.println("Please run the installer with JDK 1.3.1 or higher version");
        }
    }
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel buttonPanel;
    private javax.swing.JPanel imagePanel;
    private javax.swing.JPanel wizardPanel;
    // End of variables declaration//GEN-END:variables
}
