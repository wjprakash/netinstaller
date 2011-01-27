// PostInstallAction.java
// Last Modified on Feb 22, 2002 - Winston Prakash

package com.nayaware.netinstaller;

import java.io.*;
import java.util.*;
import com.installshield.wizard.*;
import com.installshield.wizard.service.*;
import com.installshield.util.*;
import com.installshield.archive.*;
import java.io.*;

public class PostInstallSolDevAction extends WizardAction {
    
    private String os ;
 
    private String downloadHome;
  
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
 
        downloadHome=(String)System.getProperties().get("downloadHome");
        logEvent(this, Log.DBG, "Download Home" + downloadHome);
        
        os = System.getProperty("os.name");
        if (os.startsWith("SunOS")) {
            createFCCPathLink();
            createSymlinks();
        }
    }
    
    private void createFCCPathLink(){
        String fccPath = (String)System.getProperties().get("fccPath");
        logEvent(this, Log.DBG, "Creating link to FCC Path - " + fccPath);
        if (fccPath != null){
            if(fccPath.equals("")) return;
            String arch = resolveString("$J(os.arch)");
            String linkDir="";
            if(arch.equals("sparc")){
                linkDir = downloadHome + "/platform/sparc-SunOS";
            } else{
                linkDir = downloadHome + "/platform/x86-SunOS";
            }
            RunCommand runCommand = new RunCommand();
            String command = "mkdir -p " + linkDir;
            logEvent(this, Log.DBG, "Running command - " + command);
            runCommand.execute(command);
            if(runCommand.getReturnStatus() < 0)
                logEvent(this, Log.DBG, "Command " + command + " failed.");
            String linkName = linkDir+File.separator+"fortefcc";
            command = "ln -s " + fccPath + " " + linkName;
            logEvent(this, Log.DBG, "Running command - " + command);
            runCommand.execute(command);
            if(runCommand.getReturnStatus() < 0)
                logEvent(this, Log.DBG, "Command " + command + " failed.");
        }
    }
    
    private void createSymlinks(){
        File symlinkList = new File(downloadHome+File.separator+"symlink_list.sh");
        logEvent(this, Log.DBG, "Creating symlinks from file " + symlinkList.getAbsolutePath());
        if(symlinkList.exists()){
            RunCommand runCommand = new RunCommand();
            String command = "sh " + symlinkList.getAbsolutePath();
            logEvent(this, Log.DBG, "Running command - " + command);
            runCommand.execute(command);
            if(runCommand.getReturnStatus() < 0)
                logEvent(this, Log.DBG, "Command " + command + " failed.");
            symlinkList.delete();
        }
    }
}