// PostUinstallAction.java
// Last Modified on Feb 22, 2002 - Winston Prakash

package com.nayaware.netinstaller;

import java.io.*;
import java.util.*;
import com.installshield.wizard.*;
import com.installshield.wizard.service.*;
import com.installshield.util.*;
import com.installshield.archive.*;
import java.io.*;
import com.installshield.product.service.product.*;

import com.sun.forte.licen.SerNumUtils;

public class PostUninstallAction extends WizardAction {
    private String productURL = ProductService.DEFAULT_PRODUCT_SOURCE;
    
    public void build(WizardBuilderSupport support) {
        try {
            support.putClass(RunCommand.class.getName());
        } catch (Exception ex){
            logEvent(this, Log.ERROR, ex);
        }
    }
    
    public void execute(WizardBeanEvent evt) {
        
        try {
            ProductService pservice = (ProductService)getService(ProductService.NAME);
            String dest = (String)pservice.getProductBeanProperty(productURL,null,"absoluteInstallLocation");
            String forteHome = resolveString(dest);
            String os = System.getProperty("os.name");
            if (os.startsWith("SunOS")) removeSymlinks(forteHome);
            deleteDirectory(new File(forteHome));
        }catch (ServiceException e) {
            logEvent(this, Log.ERROR, e);
        }
    }
    
    public void removeSymlinks(String forteHome){
        logEvent(this, Log.DBG, "Removing symlinks from " + forteHome);
        RunCommand runCommand = new RunCommand();
        String command = "find "+ forteHome +" -type l -print";
        logEvent(this, Log.DBG, "Running command - " + command);
        runCommand.execute(command);
        String line="";
        while((line = runCommand.getOutputLine()) != null) {
            File file = new File(line.trim());
            logEvent(this, Log.DBG, "Deleting Symlinkfile - " + file.getAbsolutePath());
            file.delete();
            if(file.exists()){
                logEvent(this, Log.DBG, "Symlink not deleted - " + file.getAbsolutePath());
            }
        }
        if(runCommand.getReturnStatus() < 0)
            logEvent(this, Log.DBG, "Command " + command + " failed.");
    }
    
    public void deleteDirectory(java.io.File dir) {
        if (!dir.delete()) {
            if (dir.isDirectory()) {
                java.io.File list[] = dir.listFiles();
                for (int i=0; i < list.length ; i++) {
                    deleteDirectory(list[i]);
                }
            }
            dir.delete();
            logEvent(this, Log.DBG, "Removing  - " + dir.getAbsolutePath());
        }
    }
}