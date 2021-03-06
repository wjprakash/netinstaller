// InstallInfoPanel.java
// Author  Winston Prakash

package com.nayaware.netinstaller;

import java.net.*;
import javax.swing.*;
import java.awt.event.*;

public class InstallInfoPanel extends javax.swing.JPanel {
    private int counter = 0;
    private Icon[] icons = new Icon[2];
    private ClassLoader classLoader = getClass().getClassLoader();
    Timer timer;
    
    public InstallInfoPanel() {
        initComponents();
        URL imageURL = classLoader.getResource("com/nayaware/netinstaller/resources/red_ball.jpg");
        //icons[0] = new ImageIcon(imageURL);
        imageURL = classLoader.getResource("com/nayaware/netinstaller/resources/green_ball.jpg");
        icons[0] = new ImageIcon(imageURL);
        imageURL = classLoader.getResource("com/nayaware/netinstaller/resources/yellow_ball.jpg");
        icons[1] = new ImageIcon(imageURL);        
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {//GEN-BEGIN:initComponents
        installLabel = new JLabel("Installing ",icons[0],JLabel.RIGHT);

        setLayout(new java.awt.BorderLayout());

        installLabel.setForeground(new java.awt.Color(0, 0, 0));
        installLabel.setText("Installing Component");
        add(installLabel, java.awt.BorderLayout.CENTER);

    }//GEN-END:initComponents
    
    public void setInstallLabel(final String labelText) {
        javax.swing.SwingUtilities.invokeLater( new Runnable() {
            public void run() {
                installLabel.setText(labelText);
            }
        } );
    }
    
    public void setInstallLabelIcon(final Icon icon) {
        javax.swing.SwingUtilities.invokeLater( new Runnable() {
            public void run() {
                installLabel.setIcon(icon);
            }
        } );
    }
    
    public void startProgressIndicator(){
        int ONE_SECOND = 500;
        timer = new Timer(ONE_SECOND, new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                setInstallLabelIcon(icons[counter]);
                if(++counter == icons.length) counter=0;
            }
        });
 
        timer.start();
    }
    
    public void stopProgressIndicator(){
         timer.stop();
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel installLabel;
    // End of variables declaration//GEN-END:variables
    
}
