// DownloadInfoPanel.java
// Author  Winston Prakash


package com.nayaware.netinstaller;
 
public class DownloadInfoPanel extends javax.swing.JPanel {
    
    private String downloadLabelText = "";
    private String sizeLabelText = "";
    private String rateLabelText = "";
    
    public DownloadInfoPanel() {
        initComponents();
    }
    
    public void displayPanel(){
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {//GEN-BEGIN:initComponents
        java.awt.GridBagConstraints gridBagConstraints;

        downloadLabel = new javax.swing.JLabel();
        downloadProgressBar = new javax.swing.JProgressBar();
        sizeLabel = new javax.swing.JLabel();
        rateLabel = new javax.swing.JLabel();

        setLayout(new java.awt.GridBagLayout());

        setBorder(new javax.swing.border.EmptyBorder(new java.awt.Insets(1, 10, 1, 1)));
        downloadLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        downloadLabel.setText(downloadLabelText);
        downloadLabel.setPreferredSize(new java.awt.Dimension(450, 16));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 11, 0);
        add(downloadLabel, gridBagConstraints);

        downloadProgressBar.setMinimumSize(new java.awt.Dimension(430, 20));
        downloadProgressBar.setPreferredSize(new java.awt.Dimension(435, 20));
        downloadProgressBar.setStringPainted(true);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 0);
        add(downloadProgressBar, gridBagConstraints);

        sizeLabel.setText(sizeLabelText);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 0);
        add(sizeLabel, gridBagConstraints);

        rateLabel.setText(rateLabelText);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 4, 0);
        add(rateLabel, gridBagConstraints);

    }//GEN-END:initComponents
    
    public void setGaugeValue(final int gaugeValue) {
        javax.swing.SwingUtilities.invokeLater( new Runnable() {
            public void run() {
                downloadProgressBar.setValue( gaugeValue );
            }
        } );
    }
    
    public void setGaugeBounds(final int gaugeMin, final int gaugeMax) {
        javax.swing.SwingUtilities.invokeLater( new Runnable() {
            public void run() {
                downloadProgressBar.setMinimum( gaugeMin );
                downloadProgressBar.setMaximum( gaugeMax );
            }
        } );
    }
    
    public void setDownloadLabel(final String labelText) {
        javax.swing.SwingUtilities.invokeLater( new Runnable() {
            public void run() {
                downloadLabel.setText( labelText );
            }
        } );
    }
    
    public void setSizeLabel(final String labelText) {
        javax.swing.SwingUtilities.invokeLater( new Runnable() {
            public void run() {
                sizeLabel.setText(labelText);
            }
        } );
    }
    
    public void setRateLabel(final String labelText) {
        javax.swing.SwingUtilities.invokeLater( new Runnable() {
            public void run() {
                rateLabel.setText(labelText);
            }
        } );
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JProgressBar downloadProgressBar;
    private javax.swing.JLabel rateLabel;
    private javax.swing.JLabel sizeLabel;
    private javax.swing.JLabel downloadLabel;
    // End of variables declaration//GEN-END:variables
    
}
