// WizardPanel.java
// Author Winston Prakash
 
package com.nayaware.netinstaller;

public class WizardPanel extends javax.swing.JPanel {
    
    private String actionDescription="Executing Action. Please wait ...";
    
    public WizardPanel() {
        initComponents();
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {//GEN-BEGIN:initComponents

        setLayout(new java.awt.BorderLayout());

        setPreferredSize(new java.awt.Dimension(450, 350));
    }//GEN-END:initComponents
    
    public void setActionDescription(String actionDesc){
        actionDescription = actionDesc;
    }
    
    public String getActionDescription(){
        return actionDescription;
    }
    
    public boolean executeAction(){
        return true;
    }
    
    public boolean hasAction(){
        return false;
    }
    
    public void displayPanel(){
    }

    public boolean queryEnter(){
        return true;
    }
    
    public boolean queryExit(){
        return true;        
    }
    
    public boolean entered(){
        return true;
    }     
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}