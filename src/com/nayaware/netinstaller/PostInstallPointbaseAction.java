// PostInstallPointbaseAction.java
// Last Modified on Feb 22, 2002 - Winston Prakash
// Based on the code sent by Pavel Flaska
 
package com.nayaware.netinstaller;

import com.installshield.wizard.service.ServiceException;
import com.installshield.product.service.product.*;
import com.installshield.product.*;
import com.installshield.wizard.WizardBuilderSupport;
import com.installshield.wizard.WizardBeanEvent;
import com.installshield.wizard.WizardAction;
import com.installshield.util.*;
import com.installshield.wizard.*;
import com.installshield.wizard.service.*;
import java.io.*;
import java.util.*;

public class PostInstallPointbaseAction extends WizardAction {
    
    private String os ;
    private String pointbaseInstall;
    private String pointbasePath;
    
    private String forteHome;
    private String jdkHome;
    
    public void build(WizardBuilderSupport support) {
        try {
            support.putClass(RunCommand.class.getName());
        } catch (Exception ex){
            logEvent(this, Log.ERROR, ex);
        }
    }
    
    public void execute(WizardBeanEvent evt) {
        String actionText = resolveString("$L(com.nayaware.netinstaller.InstallerResources,POST_INSTALL_ACTION_DESC)");
        RunnableWizardBeanState state = getState();
        state.setStatusDescription(actionText);
        
        jdkHome=(String)System.getProperties().get("jdkHome");
        logEvent(this, Log.DBG, "Java Home" + jdkHome);
        
        forteHome=(String)System.getProperties().get("forteHome");
        logEvent(this, Log.DBG, "Forte Home" + forteHome);
        
        // Find out if pointbase is installed
        try {
            String productURL = ProductService.DEFAULT_PRODUCT_SOURCE;
            ProductService productService = (ProductService)getService(ProductService.NAME);
            ProductTree productTree = productService.getSoftwareObjectTree(productURL);
            StandardProductTreeIterator iterator = new StandardProductTreeIterator(productTree);
            for(ProductBean productbean = iterator.getNext(iterator.begin()); productbean != iterator.end(); productbean = iterator.getNext(productbean)) {
                if(productbean.getDisplayName().equals("pointbase")){
                    if(productbean.isActive())
                        pointbaseInstall="yes";
                    else
                        pointbaseInstall="no";
                }
            }
            
        } catch (ServiceException ex) {
            logEvent(this, Log.ERROR, ex);            
            logEvent(this, Log.DBG, ex);
        }
        System.getProperties().put("pointbaseInstall",pointbaseInstall);
        logEvent(this, Log.DBG, "PointBase Install " + pointbaseInstall);
        
        if (pointbaseInstall.equals("yes")){
            //state.setStatusDescription(actionText);
            pointbasePath=forteHome+File.separator+"pointbase";
            
            writePointbaseIniProperties(pointbasePath + File.separator + "client" + File.separator + "pointbase.ini");
            writePointbaseIniProperties(pointbasePath + File.separator + "server" + File.separator + "pointbase.ini");
            
            os = System.getProperty("os.name");
            if (os.startsWith("Windows")) {
                changeWin32Script(pointbasePath + File.separator + "client" + File.separator + "commander.bat");
                changeWin32Script(pointbasePath + File.separator + "client" + File.separator + "console.bat");
                changeWin32Script(pointbasePath + File.separator + "server" + File.separator + "server.bat");
            }else {      // UNIX
                changeUnixScript(pointbasePath + File.separator + "client" + File.separator + "Console.lax");
                changeUnixScript(pointbasePath + File.separator + "client" + File.separator + "Commander.lax");
                changeUnixScript(pointbasePath + File.separator + "server" + File.separator + "Server.lax");
                
                File clientDir = new File(pointbasePath + File.separator + "client");
                setChmod(clientDir.getAbsolutePath(),"755");
                String[] fileList = clientDir.list();
                for(int i=0;i<fileList.length;i++)
                    setChmod(clientDir.getAbsolutePath()+fileList[i],"755");
                File serverDir = new File(pointbasePath + File.separator + "server");
                setChmod(serverDir.getAbsolutePath(),"755");
                fileList = serverDir.list();
                for(int i=0;i<fileList.length;i++)
                    setChmod(serverDir.getAbsolutePath()+fileList[i],"755");
            }
        }
    }
    
    // windows bat files settings
    public void changeWin32Script(String fileName){
        logEvent(this, Log.DBG, "Modifying PointBase Win32 Script - " + fileName);        
        File f1 = new File(fileName);
        if (!f1.exists()) return;
        File f2 = new File(f1.getAbsolutePath() + ".new"); // NOI18N
        try {
            BufferedReader reader = new BufferedReader(new FileReader(f1));
            BufferedWriter writer = new BufferedWriter(new FileWriter(f2));
            
            String line;
            int index;
            
            while ((line = reader.readLine()) != null) {
                if ((index = line.indexOf("HOME=../..")) != -1) { // NOI18N
                    StringBuffer buf = new StringBuffer(line);
                    buf.replace(index, index + 10, "HOME=" + forteHome); // NOI18N
                    line = buf.toString();
                }
                else if ((index = line.indexOf("PATH=%JAVA_PATH%")) != -1) { // NOI18N
                    StringBuffer buf = new StringBuffer(line);
                    buf.replace(index, index + 16, "PATH=" + jdkHome); // NOI18N
                    line = buf.toString();
                }
                else if (line.startsWith("REM *** INST")) { // NOI18N
                    line = "REM Forte and Java directories"; // NOI18N
                }
                writer.write(line + System.getProperty("line.separator")); // NOI18N
            }
            reader.close();
            if (!f1.delete()) {
                logEvent(this, Log.DBG, "Could not delete - " + f1.getAbsolutePath());
                return;
            }
            writer.close();
            if (!f2.renameTo(new File(fileName))) {
                logEvent(this, Log.DBG, "Could not rename - " + f2.getAbsolutePath());
                return;
            }
        }catch (Exception ex) {
            logEvent(this, Log.ERROR, ex);
            logEvent(this, Log.DBG, ex);
        }
    }
    
    // Unix lax files settings
    public void changeUnixScript(String fileName){
        logEvent(this, Log.DBG, "Modifying PointBase Unix Script - " + fileName);        
        File f1 = new File(fileName);
        if (!f1.exists()) return;
        File f2 = new File(f1.getAbsolutePath() + ".new"); // NOI18N
        try{
            BufferedReader reader = new BufferedReader(new FileReader(f1));
            BufferedWriter writer = new BufferedWriter(new FileWriter(f2));
            
            String line;
            int index;
            
            while ((line = reader.readLine()) != null) {
                if ((index = line.indexOf("dir=../..")) != -1) { // NOI18N
                    StringBuffer buf = new StringBuffer(line);
                    buf.replace(index, index + 9, "dir=" + forteHome); // NOI18N
                    line = buf.toString();
                }
                writer.write(line + System.getProperty("line.separator")); // NOI18N
            }
            
            reader.close();
            if (!f1.delete()) {
                logEvent(this, Log.DBG, "Could not delete - " + f1.getAbsolutePath());
                return;
            }
            writer.close();
            if (!f2.renameTo(new File(fileName))) {
                logEvent(this, Log.DBG, "Could not rename - " + f2.getAbsolutePath());
                return;
            }
        }catch (Exception ex) {
            logEvent(this, Log.ERROR, ex);
            logEvent(this, Log.DBG, ex);
        }
        setChmod(fileName,"755");
    }
    
    // Ini files properties
    public void writePointbaseIniProperties(String fileName){
        logEvent(this, Log.DBG, "Modifying PointBase .ini file - " + fileName);                
        File f1 = new File(fileName);
        if (!f1.exists()) return;
        File f2 = new File(f1.getAbsolutePath() + ".new"); // NOI18N
        try{
            BufferedReader reader = new BufferedReader(new FileReader(f1));
            BufferedWriter writer = new BufferedWriter(new FileWriter(f2));
            
            String line;
            int index;
            String dbHome= forteHome+ File.separator + "pointbase" + File.separator + "databases";
            String docHome= forteHome+ File.separator + "pointbase" + File.separator + "docs";
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("database.home")) { // NOI18N
                    StringBuffer buf = new StringBuffer(line);
                    index=line.indexOf('=');
                    buf.replace(index+1, index+30, dbHome); // NOI18N
                    line = buf.toString();
                }
                if (line.startsWith("documentation.home")) { // NOI18N
                    StringBuffer buf = new StringBuffer(line);
                    index=line.indexOf('=');
                    buf.replace(index+1, index+30, docHome); // NOI18N
                    line = buf.toString();
                }
                writer.write(line + System.getProperty("line.separator")); // NOI18N
            }
            
            reader.close();
            if (!f1.delete()) {
                logEvent(this, Log.DBG, "Could not delete - " + f1.getAbsolutePath());
                return;
            }
            writer.close();
            if (!f2.renameTo(new File(fileName))) {
                logEvent(this, Log.DBG, "Could not rename - " + f2.getAbsolutePath());
                return;
            }
        }catch (Exception ex) {
            logEvent(this, Log.ERROR, ex);
            logEvent(this, Log.DBG, ex);
        }
    }
    
    public boolean setChmod(String fileName,String flag) {
        try {
            String command = "chmod " + flag + " " + fileName;
            RunCommand runCommand = new RunCommand();
            logEvent(this, Log.DBG, "Running command - " + command);
            runCommand.execute(command);
            if(runCommand.getReturnStatus() < 0)
                logEvent(this, Log.DBG, "Command " + command + " failed.");
            return true;
        } catch (Exception ex) {
            logEvent(this, Log.ERROR, ex);
            logEvent(this, Log.DBG, ex);
            return false;
        }
    }
}
