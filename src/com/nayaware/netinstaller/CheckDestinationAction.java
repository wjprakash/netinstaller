// CheckDestinationAction.java
// Last Modified on Feb 22, 2002 - Winston Prakash

package com.nayaware.netinstaller;

import java.io.*;
import com.installshield.util.*;
import com.installshield.wizard.*;
import com.installshield.wizard.service.*;
import com.installshield.wizard.service.jvm.*;

public class CheckDestinationAction extends WizardAction {
    private String upgrade="no";
    private String oldVersion="";
    static String os ;
    static String unixjdkHome;
    private static String jdkHome ;
    static String winjdkHome;
    static java.lang.Runtime curRuntime;
    private String fullVersion="no";
    private String forteVersion = "";
    
    
    public void build(WizardBuilderSupport support) {
        try {
            support.putClass(RunCommand.class.getName());
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }
    
    public void execute(WizardBeanEvent evt) {
        
        upgrade=(String)System.getProperties().get("upgrade");
        if(upgrade == null) upgrade="no";
        
        oldVersion=(String)System.getProperties().get("oldVersion");
        if(oldVersion == null) oldVersion="";
        fullVersion=(String)System.getProperties().get("fullVersion");
        if(fullVersion == null) fullVersion="no";
        
        logEvent(this, Log.DBG, "Upgrade: " + upgrade);
        logEvent(this, Log.DBG, "Old Version: " + oldVersion);
        logEvent(this, Log.DBG, "Full Version: " + fullVersion);
        
        File f = null;
        
        if (fullVersion.equals("yes")) {
            f = new File(oldVersion+File.separator+"uninstall.class");
            logEvent(this, Log.DBG, "previous Uninstall Class: " + f.getAbsolutePath());
            if (f.exists()) uninstall();
        }
        
        if (upgrade.equals("yes")) {
            f= new File(oldVersion);
            logEvent(this, Log.DBG, "Old Version Directory: " + f.getAbsolutePath());
            if (f.exists()) {
                File destination=new File(oldVersion+f.separator+"ffj_backup");
                if (!destination.exists()) destination.mkdir();
                String actionDescription2 = resolveString("$L(com.nayaware.netinstaller.InstallerResources,BACKUP_OLD_VERSION)");
                RunnableWizardBeanState state = getState();
                state.setStatusDescription(actionDescription2);
                backup(oldVersion,oldVersion+f.separator+"ffj_backup");
                logEvent(this, Log.DBG, "previous Version backed up to : " + oldVersion+f.separator+"ffj_backup");
                if (!forteVersion.equals("")) createUpgradeFile();
                deleteFiles(oldVersion);
            }
        }
        
        if (upgrade.equals("no")) {
            f= new File(oldVersion);
            logEvent(this, Log.DBG, "Old Version Directory: " + f.getAbsolutePath());
            if (f.exists()) {
                String dir="system";
                deleteDir(oldVersion+f.separator+dir);
                dir="lib"+File.separator+"patches";
                deleteDir(oldVersion+f.separator+dir);
            }
        }
    }
    
    private void uninstall() {
        String actionDescription1 = resolveString("$L(com.nayaware.netinstaller.InstallerResources,UNINSTALL_OLD_VERSION)");
        RunnableWizardBeanState state = getState();
        state.setTitle(actionDescription1);
        state.setStatusDescription(actionDescription1);
        
        // Set in the JVMPanel Custom Bean
        
        String jdkHome = (String) System.getProperties().get("jdkHome");
        String os = System.getProperty("os.name");
        String jvmPath="";
        if (os.startsWith("Windows"))
            jvmPath = "java.exe";
        else
            jvmPath = "java";
        File jvmFile = new File(jdkHome+File.separator+"bin"+File.separator+jvmPath);
        if (jvmFile.exists()) {
            String command = jvmFile.getAbsolutePath() + " -cp " + oldVersion + "uninstall";
            RunCommand runCommand = new RunCommand();
            runCommand.execute(command);
            System.getProperties().put("uninstalled","yes");
            logEvent(this, Log.DBG, "previous Version Uninstalled using command : " + command);
        }
    }
    
    private void createUpgradeFile() {
        try {
            PrintStream ps = new PrintStream(new BufferedOutputStream(
            new FileOutputStream(new File(oldVersion + File.separator + "upgrade.properties"))));
            ps.println("forteVersion="+forteVersion);
            ps.println("BACKUP_FOLDER="+oldVersion.replace('\\','/')+"/"+"ffj_backup");
            ps.close();
        }
        catch (Exception e) {}
    }
    
    public void backup(String src, String dest) {
        /*try {
            File destination=new File(dest);
            if (!destination.exists()) destination.mkdir();
         
            File source=new File(src);
            java.io.File list[] = source.listFiles();
         
            for (int i=0; i < list.length ; i++) {
                File f=list[i];
                if (f.isDirectory() && !f.getName().equals("ffj_backup")   ) {
                    backup(f.getAbsolutePath(),dest+f.separator+f.getName());
                }
                else if (!f.isDirectory()){
                    BufferedInputStream fis= new BufferedInputStream(new FileInputStream(f));
                    BufferedOutputStream fos=new BufferedOutputStream(new FileOutputStream(dest+f.separator+f.getName()));
                    int s;
                    while((s = fis.read())!= -1)
                        fos.write(s);
                    fis.close();
                    fos.close();
                }
            }
        }  catch (Exception e) {
            e.printStackTrace();
        }*/
        File destination=new File(dest);
        if (!destination.exists()) destination.mkdir();
        
        File source=new File(src);
        java.io.File list[] = source.listFiles();
        
        for (int i=0; i < list.length ; i++) {
            File f=list[i];
            if (!f.getName().equals(destination.getName())   ) {
                File newFile = new File(destination,f.getName());
                f.renameTo(newFile);
                System.out.println("Moving "+f.getAbsolutePath() + " to "+ newFile.getAbsolutePath());
            }
        }
    }
    
    private void deleteFiles(String root) {
        String dir = "lib"+File.separator+"ext";
        String[] libFiles = { "dd2beans.jar", "pbembeddedeval.jar" };
        deleteFiles(root, dir, libFiles);
        
        dir = "modules";
        String[] modFiles = { "txt.jar", "projectx.jar" };
        deleteFiles(root, dir, modFiles);
        
        dir = "system";
        String[] sysFiles = { "ide.log", "project.last" };
        deleteFiles(root, dir, sysFiles);
        
        dir = "system"+File.separator+"Projects";
        String[] prjFiles =  { "cpanel.xml", "loaders.xml", "workspace.ser" };
        deleteFiles(root, dir, prjFiles);
        
        dir = "system"+File.separator+"Projects"+File.separator+"Default";
        String[] defFiles = { "cpanel.xml", "loaders.xml", "workspace.ser",
        "repository.xml", "services.xml" };
        deleteFiles(root, dir, defFiles);
        
        dir = "bin";
        String[] binFiles = { "ide.cfg" };
        deleteFiles(root, dir, binFiles);
    }
    
    public void deleteFiles(String root, String dir, String[] fileNames){
        logEvent(this, Log.DBG, "Deleting files from directory : " + root + File.separator + dir);
        File file;
        for (int i=0; i< fileNames.length;i++) {
            file = new File(root + File.separator + dir + File.separator + fileNames[i]);
            if(file.exists()) file.delete();
        }
    }
    
    public void deleteDir(String dir) {
        logEvent(this, Log.DBG, "Deleting directory : " + dir);
        File f=new File(dir);
        if (!f.delete()) {
            if (f.isDirectory()) {
                java.io.File list[] = f.listFiles();
                for (int i=0; i < list.length ; i++) {
                    deleteDir((list[i]).getAbsolutePath());
                }
            }
            f.delete();
        }
    }
}
