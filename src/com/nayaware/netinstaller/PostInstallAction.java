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

import com.sun.forte.licen.SerNumUtils;

public class PostInstallAction extends WizardAction {
    
    private String os ;
    private boolean pointBaseInstall;
    private String pointBasePath;
    
    private String forteHome;
    private String jdkHome;
    private String forteEdition;
    private String forteVersion = "4.0";
    
    public void build(WizardBuilderSupport support) {
        try {
            support.putClass(RunCommand.class.getName());
            support.putArchive( "serapi.jar", new AllArchiveFilter() );
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
        
        // forteEdition is set in SetSystemPropertyAction Panel
        forteEdition=(String)System.getProperties().get("forteEdition");
        logEvent(this, Log.DBG, "Forte Edition" + forteEdition);
        
        if (forteEdition.equals("ee")) writeSerialNumber();
        writeForteVersion();
        executeAction();
        storeSystemProperties();
    }
    
    // Store the system properties for debugging purpose
    
    public void storeSystemProperties(){
        // Store all the String system properties to the file <forte home>/system/install.properties
        try{
            String fileName= forteHome+ File.separator + "system" + File.separator + "install.properties";
            logEvent(this, Log.DBG, "Storing System Properties at " + fileName);
            FileOutputStream fos = new FileOutputStream(new File(fileName));
            Properties stringProperties = new Properties();
            Properties systemProperties = System.getProperties();
            Set keySet = systemProperties.keySet();
            Iterator propertiesIterator = keySet.iterator();
            while(propertiesIterator.hasNext()){
                Object key = propertiesIterator.next();
                Object obj = systemProperties.get(key);
                if(obj instanceof String) stringProperties.put(key,obj);
            }
            stringProperties.store(fos, "This file was created by Forte for Java installer for debugging"); //NOI18N
            fos.close();
        }catch (Exception e) {
            logEvent(this, Log.ERROR, e);
        }
    }
    
    private void writeSerialNumber(){
        String serialNo = (String)System.getProperties().get("serialNumber");
        String snFile = forteHome + File.separator + "system" + File.separator + "serial.dat";
        logEvent(this, Log.DBG, "Writing Serial Number to " + snFile);
        SerNumUtils.installSer(serialNo, snFile);
        logEvent(this, Log.DBG, "Serial Number write status " + SerNumUtils.getStatusMsg());
    }
    
    private void writeForteVersion(){
        try {
            String ideLog = forteHome + File.separator + "system" + File.separator + "ide.log";
            PrintWriter logWriter = new PrintWriter(new BufferedWriter(new FileWriter(ideLog)));
            logWriter.println(("Version = Forte(tm) for Java(tm), release " + forteVersion + ", " + forteEdition));
            logEvent(this, Log.DBG, "Version = Forte(tm) for Java(tm), release " + forteVersion + ", " + forteEdition);
            logWriter.close();
        } catch (java.io.IOException e) {
            logEvent(this, Log.ERROR, e);
            logEvent(this, Log.DBG, e);
        }
    }
    
    public void executeAction() {
        
        File settingsFile = new File(forteHome + File.separator + "system", "default.settings");
        logEvent(this, Log.DBG, "Removing " + settingsFile.getAbsolutePath());
        if(settingsFile.exists()) settingsFile.delete();
        File projectFile = new File(forteHome + File.separator + "system", "default.project");
        logEvent(this, Log.DBG, "Removing " + projectFile.getAbsolutePath());
        if( projectFile.exists()) projectFile.delete();
        
        os = System.getProperty("os.name");
        if (os.startsWith("Windows"))
            doWindows();
        else
            doUnix();
    }
    
    private void doWindows() {
        // at first delete files for Unix
        File f = null ;
        try {
            String file = forteHome + File.separator + "bin" + File.separator + "ide.cfg";
            PrintWriter ps = new PrintWriter(new BufferedWriter(new FileWriter(file)));
            ps.println("-jdkhome " + jdkHome + " -J-Xmx96m -J-Xss1024k -J-Xms24m -J-Xverify:none" );
            ps.close();
            
            String dir = forteHome + File.separator + "bin";
            String[] binFiles = { "update.sh", "launcher.exe", "runide.sh", "import.sh",
            "rmid_wrapper.sh", "runide.bat", "runide_multiuser.bat" };
            deleteFiles(dir,binFiles);
            
            dir = forteHome + File.separator + "bin" + File.separator + "unsupported";
            String[] usFiles = { "nbscript.sh" };
            deleteFiles(dir,usFiles);
 
            //String actionDescription1 = resolveString("$L(com.sun.installer.InstallerResources,STARTUP_SCRIPTS)");
            String actionDescription1 = "Preparing startup scripts";
            RunnableWizardBeanState state = getState();
            state.setStatusDescription(actionDescription1);
        }
        catch (Exception e) {
            logEvent(this, Log.ERROR, e);
        }
        
        changeWin32Script(forteHome + File.separator + "bin" + File.separator + "unsupported" + File.separator + "runide_w98.bat");
        changeWin32Script(forteHome + File.separator + "bin" + File.separator + "unsupported" + File.separator + "runide_nt.bat");
    }
    
    public void doUnix() {
        
        String dir = forteHome + File.separator + "bin" + File.separator + "unsupported";
        String[] usFiles = { "add2path.bat", "nbscript.bat", "runide_nt.bat", "runide_w98.bat"};
        deleteFiles(dir,usFiles);
        
        dir = forteHome + File.separator + "bin";
        String[] binFiles = { "runide_multiuser.sh", "import.bat", "rmid_wrapper.exe", "unideopenvms.com",
        "runide.exe", "runidew.exe", "runide_multiuser.exe", "runidew_multiuser.exe" };
        deleteFiles(dir,binFiles);
        
        dir = forteHome + File.separator + "icons";
        String[] iconFiles = {"ide.ico", "web.ico", "readme.ico"};
        deleteFiles(dir,iconFiles);
        
        setChmod(forteHome + "/bin/fastjavac/fastjavac.sun.intel","755");
        setChmod(forteHome + "/bin/fastjavac/fastjavac.sun","755");
        setChmod(forteHome + "/bin/fastjavac/fastjavac.linux","755");
        
        String os = System.getProperty("os.name");
        if (os.equals("Linux") )  {
            dir = forteHome + File.separator + "modules";
            String[] modFiles = {"iplanet_asee.jar"};
            deleteFiles(dir,modFiles);
            dir = forteHome + File.separator + "system" + File.separator + "Modules";
            String[] xmlFiles = {"com-iplanet-ias-tools-forte.xml"};
            deleteFiles(dir,xmlFiles);
        }
        String runIdeScript = new String(forteHome + File.separator + "bin" + File.separator + "runide.sh");
        changeUnixScript(runIdeScript);
        String rmidWrapperScript = new String(forteHome + File.separator + "bin" + File.separator + "rmid_wrapper.sh");
        changeUnixScript(rmidWrapperScript);
    }
    
    
    public void changeWin32Script(String fileName){
        logEvent(this, Log.DBG, "Modifying IDE Win32 Script - " + fileName);        
        File f1 = new File(fileName);
        if (!f1.exists()) return;
        File f2 = new File(f1.getAbsolutePath() + ".new"); // NOI18N
        try{
            BufferedReader reader = new BufferedReader(new FileReader(f1));
            BufferedWriter writer = new BufferedWriter(new FileWriter(f2));
            
            String line;
            int index;
            
            while ((line = reader.readLine()) != null) {
                if ((index = line.indexOf("HOME=..\\..")) != -1) { // NOI18N
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
        } catch (IOException ex){
            logEvent(this, Log.ERROR, ex);
            logEvent(this, Log.DBG, ex);
        }
    }
    
    public void changeUnixScript(String fileName) {
        logEvent(this, Log.DBG, "Modifying IDE Unix Script - " + fileName);         
        File f1 = new File(fileName);
        if (!f1.exists()) return;
        File f2 = new File(f1.getAbsolutePath() + ".new"); // NOI18N
        try{
            BufferedReader reader = new BufferedReader(new FileReader(f1));
            BufferedWriter writer = new BufferedWriter(new FileWriter(f2));
            
            String line;
            int index;
            
            while ((line = reader.readLine()) != null) {
                if ((index = line.indexOf("jdkhome=\"\"")) != -1) { // NOI18N
                    StringBuffer buf = new StringBuffer(line);
                    buf.replace(index, index + 10, "jdkhome=" + jdkHome); // NOI18N
                    line = buf.toString();
                }
                writer.write(line + System.getProperty("line.separator")); // NOI18N
            }
            
            reader.close();
            if (!f1.delete()) {
                logEvent(this, Log.DBG, "Could not rename - " + f2.getAbsolutePath());
                return;
            }
            
            writer.close();
            if (!f2.renameTo(new File(fileName))) {
                logEvent(this, Log.DBG, "Could not rename - " + f2.getAbsolutePath()); 
                return;
            }
        } catch (IOException ex){
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
    
    public void deleteFiles(String dir, String[] fileNames){
        File file;
        for (int i=0; i< fileNames.length;i++) {
            file = new File(dir + File.separator + fileNames[i]);
            if(file.exists()) {
                logEvent(this, Log.DBG, "Removing - " + file.getAbsolutePath());
                file.delete();
            }
        }
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