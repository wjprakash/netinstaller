// AssociateAction.java
// Last Modified on Feb 22, 2002 - Winston Prakash

package com.nayaware.netinstaller;

import java.io.*;
import com.installshield.wizard.*;
import com.installshield.wizard.service.*;
import com.installshield.util.*;

public class AssociateAction extends WizardAction {
    String associateJava="yes";
    String associateNBM="yes";
    String forteEdition;
    
    private String jvmFile;
    private String forteHome;
    private String jdkHome ;
    private String forteVersion;
    
    public void build(WizardBuilderSupport support) {
        try {
            support.putClass(RunCommand.class.getName());
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }
    
    public void execute(WizardBeanEvent evt) {
        String actionText = resolveString("$L(com.nayaware.netinstaller,POST_INSTALL_ACTION_DESC)");
        RunnableWizardBeanState state = getState();
        state.setStatusDescription(actionText);
        
        associateJava=(String)System.getProperties().get("associateJava");
        if (associateJava == null) associateJava="no";
        associateNBM=(String)System.getProperties().get("associateNBM");
        if (associateNBM == null) associateNBM="no";
        
        // jdkHome & jvmFile are set in JVMPanel
        jdkHome = (String)System.getProperties().get("jdkHome");
        jvmFile = (String)System.getProperties().get("jvmFile");
        
        // forteHome is set in CustomDestination Panel
        forteHome=(String)System.getProperties().get("forteHome");
        
        // forteEdition is set in SetSystemPropertyAction Panel
        forteEdition=(String)System.getProperties().get("forteEdition");
        
        // forteEdition is set in SetSystemPropertyAction Panel
        forteVersion=(String)System.getProperties().get("forteVersion");
        
        logEvent(this, Log.DBG, "Java Home: " + jdkHome);
        logEvent(this, Log.DBG, "Forte Home: " + forteHome);
        logEvent(this, Log.DBG, "Forte Edition: " + forteEdition);
        createOpenFile();
        setWin32Registry();
    }
    
    public void createOpenFile() {
        try {
            String file = new String(forteHome + File.separator + "bin" + File.separator + "openfile.bat");
            logEvent(this, Log.DBG, "Association File: " + file);
            PrintWriter ofWriter = new PrintWriter(new BufferedWriter(new FileWriter(file)));
            ofWriter.println("@echo off");
            ofWriter.println("@echo OPENING : %1 %2 %3 %4 %5 %6 %7 %8 %9");
            ofWriter.println("if not %JAVA_PATH%.==. goto JAVA_OK");
            ofWriter.println("set JAVA_PATH=" +  jdkHome  );
            ofWriter.println(":JAVA_OK");
            ofWriter.println("if not %_IDE_HOME%.==. goto FORTE_OK");
            ofWriter.println("set _IDE_HOME=" + forteHome );
            ofWriter.println(":FORTE_OK");
            ofWriter.println("%JAVA_PATH%\\bin\\java.exe -cp %_IDE_HOME%\\modules\\utilities.jar;. org.netbeans.modules.openfile.Main  %1 %2 %3 %4 %5 %6 %7 %8 %9");
            ofWriter.println("if errorlevel 2 goto ERROR");
            ofWriter.println("goto OK");
            ofWriter.println(":ERROR");
            ofWriter.println("@echo.");
            ofWriter.println("@echo.");
            ofWriter.println("@echo Can't open file .");
            ofWriter.println("@echo In order to open file Forte for Java IDE must be running and this");
            ofWriter.println("@echo capability must be enabled.");
            ofWriter.println("@echo It can be done by setting property Running of Open File Server");
            ofWriter.println("@echo (under Tools|Options) to true.");
            ofWriter.println("@echo pause");
            ofWriter.println(":OK");
            ofWriter.println(" ");
            ofWriter.close();
        }catch (Exception ex){
            logEvent(this, Log.ERROR, ex);
        }
    }
    
    private void setWin32Registry(){
        try {
            File regFile = new File(forteHome + File.separator + "bin" + File.separator + "f4j.reg");
            logEvent(this, Log.DBG, "Temporary Registry File: " + regFile.getAbsolutePath());
            regFile.deleteOnExit();
            PrintWriter regWriter = new PrintWriter(new BufferedWriter(new FileWriter(regFile)));
            
            regWriter.println("REGEDIT4");
            regWriter.println("[HKEY_LOCAL_MACHINE\\SOFTWARE\\<company>\\<product>, "+forteEdition+"\\"+forteVersion+"]");
            
            //convert single slash in forteHome to double slash
            
            char[] chars = forteHome.toCharArray();
            char[] buf = new char[1024];
            int count = 0;
            for(int i=0; i< chars.length; i++)
                if( chars[i] == '\\') {
                    buf[count++] = '\\';
                    buf[count++] = '\\';
                } else buf[count++] = chars[i];
            String tmpForteHome = (new String(buf)).trim();
            
            regWriter.println("\"path\"=\""+tmpForteHome+"\"");
            
            // if association is choosed - associate ide with java files in registry
            // create .reg file, run it and delete
            if (associateJava.equals("yes") || associateNBM.equals("yes")) {
                if (associateJava.equals("yes")) {
                    regWriter.println("[HKEY_LOCAL_MACHINE\\SOFTWARE\\Classes\\.java]");
                    regWriter.println("@=\"javafile\"");
                }
                if (associateNBM.equals("yes")) {
                    regWriter.println("[HKEY_LOCAL_MACHINE\\SOFTWARE\\Classes\\.nbm]");
                    regWriter.println("@=\"javafile\"");
                }
                
                regWriter.println("[HKEY_LOCAL_MACHINE\\SOFTWARE\\Classes\\javafile]");
                regWriter.println("@=\"Java(tm) File (Forte(tm) For Java(tm))\"");
                regWriter.println("[HKEY_LOCAL_MACHINE\\SOFTWARE\\Classes\\javafile\\shell]");
                regWriter.println("[HKEY_LOCAL_MACHINE\\SOFTWARE\\Classes\\javafile\\shell\\open]");
                regWriter.println("[HKEY_LOCAL_MACHINE\\SOFTWARE\\Classes\\javafile\\shell\\open\\command]");
                regWriter.println("@=\"" + tmpForteHome + "\\\\bin\\\\openfile.bat \\\"%1\\\"\"" );
                regWriter.println("[HKEY_LOCAL_MACHINE\\SOFTWARE\\Classes\\javafile\\DefaultIcon]");
                regWriter.println("@=\"" + tmpForteHome + "\\\\bin\\\\icons\\\\ide.ico,0\"" );
            }
            
            regWriter.close();
            String command = "regedit -s" + regFile;
            RunCommand runCommand = new RunCommand();
            runCommand.execute(command);
            
            if(runCommand.getReturnStatus() != 0){
                logEvent(this, Log.DBG, "Error occured while registering " + regFile.getAbsolutePath());
            }
            regFile.delete();
        } catch (Exception e) {
            logEvent(this, Log.ERROR, e);
        }
    }
}
