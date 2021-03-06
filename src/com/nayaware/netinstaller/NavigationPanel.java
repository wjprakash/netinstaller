// NavigationPanel.java
// Author  Winston Prakash
 
package com.nayaware.netinstaller;

import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class NavigationPanel extends javax.swing.JPanel {
    
    public NavigationPanel() {
        initComponents();
    }
    
    public void addActionListener(ActionListener listener){
        backButton.addActionListener(listener);
        nextButton.addActionListener(listener);
        cancelButton.addActionListener(listener);
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {//GEN-BEGIN:initComponents
        java.awt.GridBagConstraints gridBagConstraints;

        naviagtionSeparater = new javax.swing.JSeparator();
        buttonPanel = new javax.swing.JPanel();
        backButton = new javax.swing.JButton();
        nextButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();

        setLayout(new java.awt.BorderLayout());

        setPreferredSize(new java.awt.Dimension(450, 45));
        setMinimumSize(new java.awt.Dimension(450, 45));
        add(naviagtionSeparater, java.awt.BorderLayout.NORTH);

        buttonPanel.setLayout(new java.awt.GridBagLayout());

        buttonPanel.setPreferredSize(new java.awt.Dimension(450, 40));
        backButton.setMnemonic('B');
        backButton.setFont(new java.awt.Font("Times New Roman", 1, 14));
        backButton.setText("Back");
        backButton.setPreferredSize(new java.awt.Dimension(100, 25));
        backButton.setBorder(new javax.swing.border.BevelBorder(javax.swing.border.BevelBorder.RAISED));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 5);
        buttonPanel.add(backButton, gridBagConstraints);

        nextButton.setMnemonic('N');
        nextButton.setFont(new java.awt.Font("Times New Roman", 1, 14));
        nextButton.setText("Next");
        nextButton.setPreferredSize(new java.awt.Dimension(100, 25));
        nextButton.setBorder(new javax.swing.border.BevelBorder(javax.swing.border.BevelBorder.RAISED));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 5);
        buttonPanel.add(nextButton, gridBagConstraints);

        cancelButton.setMnemonic('C');
        cancelButton.setFont(new java.awt.Font("Times New Roman", 1, 14));
        cancelButton.setText("Cancel");
        cancelButton.setPreferredSize(new java.awt.Dimension(100, 25));
        cancelButton.setBorder(new javax.swing.border.BevelBorder(javax.swing.border.BevelBorder.RAISED));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 3);
        buttonPanel.add(cancelButton, gridBagConstraints);

        add(buttonPanel, java.awt.BorderLayout.CENTER);

    }//GEN-END:initComponents
    
    public void setExitNavigation(){
        backButton.setVisible(false);
        nextButton.setVisible(false);
        cancelButton.setText("Exit");
    }
    
    public void setForwardNavigation(){
        backButton.setEnabled(false);
    }
    
    public void setBackwardNavigation(){
        nextButton.setEnabled(false);
    }
    
    public void setNormalNavigation(){
        backButton.setEnabled(true);
        nextButton.setEnabled(true);        
    }
    
    public void navigateForward(){
        nextButton.doClick();
    }
    
    public void navigateBackward(){
        backButton.doClick();
    }    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel buttonPanel;
    private javax.swing.JButton nextButton;
    private javax.swing.JButton cancelButton;
    private javax.swing.JSeparator naviagtionSeparater;
    private javax.swing.JButton backButton;
    // End of variables declaration//GEN-END:variables
    
}
