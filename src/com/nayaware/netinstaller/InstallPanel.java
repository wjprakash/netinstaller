// InstallPanel.java
// Author Winston Prakash

package com.nayaware.netinstaller;

import java.net.*;
import java.io.*;
import java.util.*;
import javax.swing.*;

public class InstallPanel extends WizardPanel {
    
    String descriptionText="Installer is now installing the following Forte for Java Components";
    public DownloadInfoPanel downloadInfoPanel = new DownloadInfoPanel();
    public InstallInfoPanel installInfoPanel = new InstallInfoPanel();
    
    HashMap compList = new HashMap();
    JLabel label;
    /** Creates new form InstallPanel */
    public InstallPanel() {
        initComponents();
        installInfoPanel.setVisible(false);
        downloadInfoPanel.setVisible(false);
        statusPanel.add(installInfoPanel, java.awt.BorderLayout.SOUTH);
        statusPanel.add(downloadInfoPanel, java.awt.BorderLayout.SOUTH);
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {//GEN-BEGIN:initComponents
        descTextArea = new javax.swing.JTextArea();
        componentPanel = new javax.swing.JPanel();
        statusPanel = new javax.swing.JPanel();

        descTextArea.setBackground(new java.awt.Color(204, 204, 204));
        descTextArea.setText(descriptionText);
        descTextArea.setMargin(new java.awt.Insets(10, 10, 10, 10));
        add(descTextArea, java.awt.BorderLayout.NORTH);

        componentPanel.setPreferredSize(new java.awt.Dimension(450, 150));
        add(componentPanel, java.awt.BorderLayout.CENTER);

        statusPanel.setPreferredSize(new java.awt.Dimension(450, 175));
        add(statusPanel, java.awt.BorderLayout.SOUTH);

    }//GEN-END:initComponents
    
    public void addComponent(ComponentDescriptor comp){
        java.awt.GridBagConstraints gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        //gridBagConstraints.gridy = ;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        URL imageURL = this.getClass().getClassLoader().getResource("com/nayaware/netinstaller/resources/red_ball.jpg");
        Icon icon = new ImageIcon(imageURL);
        JLabel compLabel = new JLabel("",icon,JLabel.LEFT);
        compLabel.setText(comp.getDisplayName());
        compLabel.setMinimumSize(new java.awt.Dimension(200, 24));
        compLabel.setPreferredSize(new java.awt.Dimension(300, 24));
        componentPanel.add(compLabel, gridBagConstraints);
        compList.put(comp,compLabel);
    }
    
    public boolean hasAction(){
        return true;
    }
    
    public boolean executeAction(){
        componentPanel.removeAll();
        ComponentDescriptor comp;
        for(int i=0;i<SoftwareObjects.getSize();i++){
            comp = SoftwareObjects.getObject(i);
            if(comp.getActive()) addComponent(comp);
        }
        installAction();
        
        /*Runnable install = new Runnable(){
            public void run() {
                installAction();
            }
        };
         
        Thread installThread = new Thread(install);
        installThread.start();*/
        
        return false;
    }
    
    public boolean needsInteraction(){
        return false;
    }
    
    private void installAction(){
        ComponentDescriptor comp;
        for(int i=0;i<SoftwareObjects.getSize();i++){
            comp = SoftwareObjects.getObject(i);
            if(comp.getActive()){
                label = (JLabel)compList.get(comp);
                javax.swing.SwingUtilities.invokeLater( new Runnable() {
                    public void run() {
                        URL imageURL = getClass().getClassLoader().getResource("com/nayaware/netinstaller/resources/rightArrow.jpg");
                        Icon rightArrowIcon = new ImageIcon(imageURL);
                        label.setIcon(rightArrowIcon);
                    }
                } );
                //String forteHome = (String)System.getProperties().get("forteHome");
                try{
                    //String forteHome = "C:\\forte4j1";
                    //File tmpFile =  new File("C:\\ffj_j2me_win.jar");
                    File tmpFile =  File.createTempFile("ffj",".jar");
                    download(comp,tmpFile);
                    install(comp,tmpFile);
                    tmpFile.delete();
                    URL imageURL = this.getClass().getClassLoader().getResource("com/nayaware/netinstaller/resources/green_ball.jpg");
                    Icon icon = new ImageIcon(imageURL);
                    label.setIcon(icon);
                }catch(Exception ex){
                    ex.printStackTrace();
                }
            }
        }
    }
    
    public void download(ComponentDescriptor comp, File tmpFile){
        installInfoPanel.setVisible(false);
        downloadInfoPanel.setVisible(true);
        try {
            String instName = comp.getInstallerName();
            URL url = new URL("http","sample.com", 80, "/installers/"+instName);
            //System.out.println(url);
            URLConnection urlConnection = url.openConnection();
            int downloadSize = urlConnection.getContentLength();
            BufferedInputStream bis =  new BufferedInputStream(urlConnection.getInputStream());
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(tmpFile));
            downloadInfoPanel.setGaugeBounds(0,downloadSize);
            downloadInfoPanel.setDownloadLabel("Downloading "+ comp.getDisplayName());
            int c=0,downloadedSize=0,oldDownloadedSize=0;
            String sizeText="",rateText="";
            int len=4096;
            byte[] bytes = new byte[len];
            long oldTime = System.currentTimeMillis();
            long newTime = System.currentTimeMillis();
            int diffSize=0,rate=0,avgRate=0;
            int samples=1,noSamples=0,scale=100;
            int remMin=0,remSec=0;
            while((c = bis.read(bytes,0,len)) != -1) {
                bos.write(bytes,0,c);
                downloadedSize+=c;
                /*if(downloadedSize > 500000){
                    bis.close();
                    bos.close();
                    return;
                }*/
                downloadInfoPanel.setGaugeValue(downloadedSize);
                diffSize += c;
                /*if(diffSize > (len*scale)){
                    newTime=System.currentTimeMillis();
                    double diffKb = (double)diffSize/1024.;
                    double timeSec = (double)(newTime-oldTime)/1000.;
                    rate = (int)(diffKb/timeSec);
                    oldTime=newTime;
                    diffSize-=(len*scale);
                }*/
                if((System.currentTimeMillis()-oldTime) > 1000){
                    oldTime=System.currentTimeMillis();
                    rate=(downloadedSize-oldDownloadedSize)/1024;
                    oldDownloadedSize=downloadedSize;
                    avgRate+=rate;
                    noSamples++;
                    if(noSamples >= samples){
                        avgRate/=samples;
                        if(avgRate > 0){
                            remMin = (downloadSize-downloadedSize)/1024/avgRate/60;
                            remSec = ((downloadSize-downloadedSize)/1024/avgRate)%60;
                            rateText = "Estimated Time: " + remMin + " Mins " + remSec + " Secs (" + avgRate + " Kb/Secs)";
                            downloadInfoPanel.setRateLabel(rateText);
                        }
                        avgRate=0;
                        noSamples=0;
                    }
                }
                sizeText = downloadedSize/1024 + " of " +  downloadSize/1024 + " KB copied.";
                downloadInfoPanel.setSizeLabel(sizeText);
            }
            bis.close();
            bos.close();
        }
        catch (MalformedURLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        downloadInfoPanel.setVisible(false);
    }
    
    public void install(ComponentDescriptor comp, File tmpFile){
        installInfoPanel.setVisible(true);
        downloadInfoPanel.setVisible(false);
        
        String installMsg="Installing " + comp.getName();
        
        installInfoPanel.setInstallLabel(installMsg);
        installInfoPanel.startProgressIndicator();
        
        String forteHome = (String)System.getProperties().get("downloadHome");
        String jdkHome = (String)System.getProperties().get("jdkHome");
        String serNum = (String)System.getProperties().get("serialNumber");
        if(serNum == null){
            serNum="trial";
        }
        
        String os = System.getProperty("os.name");
        String jvm = "java";
        
        String jvmFile = jdkHome+File.separator+"bin"+File.separator+jvm;
        
        String command = jvmFile + " -DdownloadHome=" + forteHome + " -DjdkHome=" +
        jdkHome + " -DserialNumber=" + serNum + " -cp " +
        tmpFile.getAbsolutePath() + " run -silent";
        //System.out.println(command);
        RunCommand runCommand = new RunCommand();
        runCommand.execute(command);
        runCommand.getReturnStatus();
        if (runCommand.getReturnStatus() < 0){
            runCommand.print();
        }
        
        installInfoPanel.stopProgressIndicator();
        installInfoPanel.setVisible(false);
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel statusPanel;
    private javax.swing.JTextArea descTextArea;
    private javax.swing.JPanel componentPanel;
    // End of variables declaration//GEN-END:variables
    
}
