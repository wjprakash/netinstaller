// LogoPanel.java
// Author  Winston Prakash
 
package com.nayaware.netinstaller;

import java.awt.*;
import java.net.*;

public class LogoPanel extends javax.swing.JPanel {
    Image image = null;   
    /** Creates new form LogoPanel */
    public LogoPanel() {
        initComponents();
        URL imageURL = this.getClass().getClassLoader().getResource("com/nayaware/netinstaller/resources/company_logo.jpg");
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        image = toolkit.getImage(imageURL);
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {//GEN-BEGIN:initComponents

        setLayout(new java.awt.BorderLayout());

        setPreferredSize(new java.awt.Dimension(175, 400));
        setMinimumSize(new java.awt.Dimension(175, 400));
    }//GEN-END:initComponents
 
    public void paintComponent(Graphics g) {
        super.paintComponent(g); 
        g.drawImage(image, 5, 0, 165,390,this);  
    }
      
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

}
