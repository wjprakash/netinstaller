// JDKSearchAction.java
// Last Modified on Feb 22, 2002 - Winston Prakash

package com.nayaware.netinstaller;

import java.io.*;
import java.util.*;
import java.io.*;
import com.installshield.wizard.*;
import com.installshield.wizard.service.*;
import com.installshield.util.*;

public class JDKSearchAction extends WizardAction {
    Vector jdkHomeList=new Vector();
    
    public JDKSearchAction() {
        System.getProperties().put("JDKSearchAction",this);
    }
    
    public void build(WizardBuilderSupport support) {
        try {
            support.putClass(RunCommand.class.getName());
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }
    
    public void execute(WizardBeanEvent evt){
        // Return if jdkHomeList is not empty. This is an work around
        // to make the action not to search the JDKs if the user click
        // back button
        if(!jdkHomeList.isEmpty()) return;
        
        // Check if user specified jdkHome (alias jh) in the command Line
        // jdkHome property always overrides the alias jh
        
        String jdkHome = (String)System.getProperties().get("jdkHome");
        if(jdkHome == null) jdkHome = "";
        
        // If JDKHome is specified and is a valid one then
        // donot do the search.
        if(!jdkHome.equals(""))
            if(checkJdkHome(jdkHome)){
                System.getProperties().put("jdkHome",jdkHome);
                addToList(jdkHome);
                System.getProperties().put("JDKSearchAction",this);
                return;
            }
        
        RunnableWizardBeanState state = getState();
        String searchMsg = resolveString("$L(com.nayaware.netinstaller.InstallerResources,JDK_SEARCH_MSG)");
        state.setStatusDescription(searchMsg);
        String os = System.getProperty("os.name");
        if (os.startsWith("Windows"))
            findWindowsJDK();
        else
            findUnixJDK();
        System.getProperties().put("JDKSearchAction",this);
    }
    
    public void findWindowsJDK(){
        
        String jdkHome;
        
        // Try the current jdk
        logEvent(this, Log.DBG, "Checking Current JVM ..." + System.getProperty("java.home"));
        String jdkVersion = System.getProperty("java.version");
        if(jdkVersion.startsWith("1.3.1") || jdkVersion.startsWith("1.4")) {
            jdkHome = System.getProperty("java.home");
            File jHome = new File(jdkHome);
            // If the "java.home" property ends with jre then get the parent directory
            if(jHome.getName().equals("jre"))
                jdkHome = jHome.getParentFile().getAbsolutePath();
            if(checkJdkHome(jdkHome)) addToList(jdkHome);
        }
        
        // Try the Windows Registry
        
        try{
            logEvent(this, Log.DBG,"Checking Win32 Registry ... ");
            File regFile= File.createTempFile("forte",".reg");
            
            String command = "regedit -e " + regFile.getAbsolutePath() + " \"HKEY_LOCAL_MACHINE\\SOFTWARE\\JavaSoft\\Java Development Kit\"";
            RunCommand runCommand = new RunCommand();
            runCommand.execute(command);
            runCommand.getReturnStatus();
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(regFile.getAbsolutePath()),"UTF-16"));
            
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("[HKEY_LOCAL_MACHINE\\SOFTWARE\\JavaSoft\\Java Development Kit")){
                    String version = line.substring(line.lastIndexOf("\\")+1);
                    if( version.startsWith("1.3.1") || version.startsWith("1.4")){
                        line = reader.readLine();
                        StringTokenizer st = new StringTokenizer(line,"\"");
                        String firstToken="",lastToken="";
                        if(st.hasMoreTokens())  firstToken = st.nextToken();
                        if(firstToken.compareTo("JavaHome") == 0){
                            while(st.hasMoreTokens())  lastToken = st.nextToken();
                            StringBuffer stringBuffer = new StringBuffer();
                            int index=0;
                            for (int i=0; i<lastToken.length(); i++) {
                                if ((lastToken.charAt(i) == '\\') && (index==0)) {
                                    index++;
                                }else {
                                    stringBuffer.append(lastToken.charAt(i));
                                }
                            }
                            jdkHome=stringBuffer.toString();
                            if(checkJdkHome(jdkHome)) addToList(jdkHome);
                        }
                    }
                }
            }
            reader.close();
        } catch(Exception ex){
            ex.printStackTrace();
        }
        
        // Try the path
        logEvent(this, Log.DBG,"Checking Path Settings ... ");
        try{
            File tmpFile= File.createTempFile("forte",".bat");
            PrintWriter pWriter = new PrintWriter(new BufferedWriter(new FileWriter(tmpFile.getAbsolutePath())));
            pWriter.println("set");
            pWriter.close();
            RunCommand runCommand = new RunCommand();
            runCommand.execute(tmpFile.getAbsolutePath());
            String line = null;
            while ((line = runCommand.getOutputLine()) != null)
                if(line.startsWith("Path")){
                    String path = line.substring(line.indexOf("=")+1);
                    StringTokenizer st = new StringTokenizer(path.trim(),";");
                    while(st.hasMoreTokens()) {
                        File jvmFile = new File(st.nextToken().toString(),"java.exe");
                        if(jvmFile.exists()) {
                            // Check if jvm.dll exists as needed by ffj
                            File binDir = jvmFile.getParentFile();
                            File jdkDir= binDir.getParentFile();
                            jdkHome = jdkDir.getAbsolutePath();
                            if(checkJdkHome(jdkHome)) addToList(jdkHome);
                        }
                    }
                }
        } catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    public void findUnixJDK(){
        String jdkHome;
        // Try the standard places first
        logEvent(this, Log.DBG,"Checking Standard Places ... ");
        String os = System.getProperty("os.name");
        if (os.startsWith("SunOS")){
            checkDir("/usr");
            checkDir("/opt");
            checkDir("/export/home");
            checkDir(System.getProperty("user.home"));
        }
        if (os.startsWith("Linux")){
            checkDir("/usr");
            checkDir("/opt");
            checkDir("/usr/local");
            checkDir("/usr/java");
            checkDir("/opt/java");
            checkDir("/usr/local/java");            
            checkDir(System.getProperty("user.home"));
        }
        
        // Try the current jdk
        logEvent(this, Log.DBG,"Checking Current JVM ..." + System.getProperty("java.home"));
        String jdkVersion = System.getProperty("java.version");
        if(jdkVersion.startsWith("1.3.1") || jdkVersion.startsWith("1.4")) {
            jdkHome = System.getProperty("java.home");
            File jHome = new File(jdkHome);
            // If the "java.home" property ends with jre then get the parent directory
            if(jHome.getName().equals("jre"))
                jdkHome = jHome.getParentFile().getAbsolutePath();
            if(checkJdkHome(jdkHome)) addToList(jdkHome);
        }
        
        try{
            logEvent(this, Log.DBG,"Checking Path settings & environment variable ...");
            RunCommand runCommand = new RunCommand();
            runCommand.execute("/bin/env");
            
            String line = null;
            while ((line = runCommand.getOutputLine()) != null){
                if(line.startsWith("PATH")){
                    String path = line.substring(line.indexOf("=")+1);
                    StringTokenizer st = new StringTokenizer(path.trim(),":");
                    while(st.hasMoreTokens()) {
                        File jvmFile = (new File(st.nextToken().toString(),"java")).getCanonicalFile();
                        if(jvmFile.exists()) {
                            // Check if jvm.dll exists as needed by ffj
                            File binDir = jvmFile.getParentFile();
                            File jdkDir= binDir.getParentFile();
                            jdkHome = jdkDir.getAbsolutePath();
                            if(checkJdkHome(jdkHome)) addToList(jdkHome);
                        }
                    }
                }
                
                // Check for different possible variable
                if(line.startsWith("JAVA_PATH") || line.startsWith("JAVA_HOME") ||
                line.startsWith("JAVAPATH") || line.startsWith("JAVAHOME") ||
                line.startsWith("JDK_PATH") || line.startsWith("JDK_HOME") ||
                line.startsWith("JDKPATH") || line.startsWith("JDKHOME")){
                    jdkHome = line.substring(line.indexOf("=")+1).trim();
                    if(checkJdkHome(jdkHome)) addToList(jdkHome);
                }
            }
        } catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    public Vector getJdkList(){
        return jdkHomeList;
    }
    
    public boolean checkJdkHome(String jdkHome){
        File jreDir = new File(jdkHome,File.separator+"jre");
        if(jreDir.exists()) {
            String os = System.getProperty("os.name");
            String jvm="";
            if (os.startsWith("Windows"))
                jvm = "java.exe";
            else
                jvm = "java";
            String jvmFile = jdkHome+File.separator+"bin"+File.separator+jvm;
            RunCommand runCommand = new RunCommand();
            runCommand.execute(jvmFile+" -version");
            String line = line = runCommand.getErrorLine();
            StringTokenizer st = new StringTokenizer(line.trim());
            String version="";
            while(st.hasMoreTokens()) version=st.nextToken();
            if(version.startsWith("\"1.3.1") || version.startsWith("\"1.4"))
                return true;
            else
                return false;
        }
        return false;
    }
    
    public void checkDir(String rootDir){
        logEvent(this, Log.DBG,"Checking Directory ... " + rootDir);
        String jdkHome;
        File root = new File(rootDir);
        String[] list = root.list();
        if((list != null)){
            for (int i=0;i<list.length;i++){
                if((list[i].startsWith("j2se") || list[i].startsWith("java") ||
                list[i].startsWith("j2sdk") || list[i].startsWith("jdk"))){
                    if(rootDir.endsWith("\\") || rootDir.endsWith("/"))
                        jdkHome=rootDir+list[i];
                    else
                        jdkHome=rootDir+File.separator+list[i];
                    if((new File(jdkHome)).isDirectory())
                        if(checkJdkHome(jdkHome)) addToList(jdkHome);
                }
            }
        }
    }
    
    public void addToList(String jdkHome){
        boolean found = false;
        for(int i=0;i<jdkHomeList.size();i++){
            if(jdkHomeList.contains(jdkHome)) found = true;
        }
        if (!found) {
            logEvent(this, Log.DBG,"Found Valid JDK Home ... " + jdkHome);
            jdkHomeList.add(jdkHome);
        }
    }
    
    public void print(){
        for(int i=0;i<jdkHomeList.size();i++){
            System.out.println("jdkHome "+ i + ": " + jdkHomeList.get(i));
        }
    }
}

