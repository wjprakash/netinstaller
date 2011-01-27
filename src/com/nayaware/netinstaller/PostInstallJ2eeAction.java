// PostInstallPointbaseAction.java
// Last Modified on Feb 22, 2002 - Winston Prakash
// Based on code sent by Pavel Flaska


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

public class PostInstallJ2eeAction extends WizardAction {
    
    private String os ;
    private String j2eeInstall;
    private String j2eePath;
    
    private String forteHome;
    private String jdkHome;
    
    private String script = "";
    
    
    public void build(WizardBuilderSupport support) {
        try {
            support.putClass(RunCommand.class.getName());
        } catch (Exception ex){
            ex.printStackTrace();
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
                if(productbean.getDisplayName().equals("j2ee")){
                    if(productbean.isActive())
                        j2eeInstall="yes";
                    else
                        j2eeInstall="no";
                }
            }
            
        } catch (ServiceException e) {
        }
        System.getProperties().put("j2eeInstall",j2eeInstall);
        logEvent(this, Log.DBG, "J2EE Install " + j2eeInstall);
        
        if(j2eeInstall.equals("yes")){    
            j2eePath=forteHome+File.separator+"j2sdkee1.3";
            String binPath=j2eePath + File.separator + "bin";
            os = System.getProperty("os.name");
            if (os.startsWith("Windows")) {
                changeWin32Script(binPath + File.separator + "userconfig.bat");
                changeWin32Script(binPath + File.separator + "cleanup.bat");
                changeWin32Script(binPath + File.separator + "deploytool.bat");
                changeWin32Script(binPath + File.separator + "j2ee.bat");
                changeWin32Script(binPath + File.separator + "j2eeadmin.bat");
                changeWin32Script(binPath + File.separator + "keytool.bat");
                changeWin32Script(binPath + File.separator + "packager.bat");
                changeWin32Script(binPath + File.separator + "realmtool.bat");
                changeWin32Script(binPath + File.separator + "runclient.bat");
                changeWin32Script(binPath + File.separator + "setenv.bat");
                changeWin32Script(binPath + File.separator + "verifier.bat");
            }else { // UNIX
                changeUnixScript(binPath + File.separator + "userconfig.sh");
                File binDir = new File(binPath);
                setChmod(binDir.getAbsolutePath(),"755");
                String[] fileList = binDir.list();
                for(int i=0;i<fileList.length;i++)
                    setChmod(binDir.getAbsolutePath()+fileList[i],"755");
            }
        }
    }
    
    // windows bat files settings
    public void changeWin32Script(String fileName){
        logEvent(this, Log.DBG, "Modifying J2EE Win32 Script - " + fileName);
        File f1 = new File(fileName);
        if (!f1.exists()) return;
        File f2 = new File(f1.getAbsolutePath() + ".new"); // NOI18N
        try {
            BufferedReader reader = new BufferedReader(new FileReader(f1));
            BufferedWriter writer = new BufferedWriter(new FileWriter(f2));
            
            String line;
            String lineSeparator = System.getProperty("line.separator");
            int lineNo=0;
            
            // Write value for variables FORTE4J_HOME, JAVA_HOME, J2EE_HOME
            // after any line starting with @echo off
            // Als remove lines starting with "rem set" or "rem" to tidy up
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("@echo off") || (lineNo == 0)) { // NOI18N
                    writer.write("@echo off" + lineSeparator); // NOI18N
                    writer.write(lineSeparator); // NOI18N
                    writer.write("set FORTE4J_HOME=" + forteHome + lineSeparator); // NOI18N
                    writer.write("set JAVA_HOME=" + jdkHome + lineSeparator); // NOI18N
                    writer.write("set J2EE_HOME=" + j2eePath+ lineSeparator); // NOI18N
                }else if (line.startsWith("rem set ") || line.startsWith("rem Set ") || line.trim().equals("rem")) { // NOI18N
                    // skip line
                }else {
                    writer.write(line + lineSeparator); // NOI18N
                }
                lineNo++;
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
    
    public void changeUnixScript(String fileName){
        logEvent(this, Log.DBG, "Modifying J2EE Unix Script - " + fileName);
        File f1 = new File(fileName);
        if (!f1.exists()) return;
        File f2 = new File(f1.getAbsolutePath() + ".new"); // NOI18N
        try {
            BufferedReader reader = new BufferedReader(new FileReader(f1));
            BufferedWriter writer = new BufferedWriter(new FileWriter(f2));
            
            String line;
            String lineSeparator = System.getProperty("line.separator");
            boolean variablesWritten=false;
            
            // Write value for variables FORTE4J_HOME, JAVA_HOME, J2EE_HOME
            // after any line starting with #
            while ((line = reader.readLine()) != null) {
                if (!line.startsWith("#") && !variablesWritten) { // NOI18N
                    writer.write(lineSeparator); // NOI18N
                    writer.write("FORTE4J_HOME="+ forteHome + lineSeparator); // NOI18N
                    writer.write("export FORTE4J_HOME" + lineSeparator); // NOI18N
                    writer.write("JAVA_HOME=" + jdkHome + lineSeparator); // NOI18N
                    writer.write("export JAVA_HOME" + lineSeparator); // NOI18N
                    writer.write("J2EE_HOME=" + j2eePath + lineSeparator); // NOI18N
                    writer.write("export J2EE_HOME" + lineSeparator); // NOI18N
                    writer.write(lineSeparator); // NOI18N
                    writer.write(line + lineSeparator); // NOI18N
                    variablesWritten=true;
                } else
                    writer.write(line + lineSeparator); // NOI18N
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