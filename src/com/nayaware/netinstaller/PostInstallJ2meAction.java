// PostInstallJ2meAction.java
// Last Modified on Feb 22, 2002 - Winston Prakash
// Based on the code sent by Martin Ryzel


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
import java.text.*;


public class PostInstallJ2meAction extends WizardAction {
    
    private  final String DEFAULT_STARTUP_WIN = "{0}\\bin\\java -Dkvem.home={1} -cp {1}/wtklib/kenv.zip;{1}/wtklib/ktools.zip {2}\n";
    
    private  final String DEFAULT_STARTUP_UNIX = "#!/bin/sh\njavapathtowtk={0}/bin\n\nPRG=$0\n\n# Resolve soft links\n" +
    "while [ -h \"$PRG\" ]; do\n    ls=`/bin/ls -ld \"$PRG\"`\n    link=`/usr/bin/expr \"$ls\" : ''.*-> \\(.*\\)$''`\n" +
    "    if /usr/bin/expr \"$link\" : ''^/'' > /dev/null 2>&1; then\n        PRG=\"$link\"\n    else\n        PRG=\"`/usr/bin/dirname $PRG`/$link\"\n    fi\n" +
    "done\nKVEM_BIN=`dirname $PRG`\nKVEM_HOME=`cd $'{'KVEM_BIN'}'/.. ; pwd`\nKVEM_LIB=$'{'KVEM_HOME'}'/wtklib\n" +
    "$'{'javapathtowtk'}'/java -Dkvem.home=$'{'KVEM_HOME'}' \\\n    -cp $'{'KVEM_LIB'}'/kenv.zip:$'{'KVEM_LIB'}'/ktools.zip \\\n    {1} $*{2}\n";
    
    /** The operating system on which NetBeans runs */
    private  int operatingSystem = -1;
    
    /** Operating system is IBM AIX.  */
    public  final int OS_AIX = 64;
    
    /** Operating system is HP-UX.  */
    public  final int OS_HP = 32;
    
    /** Operating system is SGI IRIX.  */
    public  final int OS_IRIX = 128;
    
    /** Operating system is Linux.  */
    public  final int OS_LINUX = 16;
    
    /** Operating system is Mac.  */
    public  final int OS_MAC = 2048;
    
    /** Operating system is OS/2.  */
    public  final int OS_OS2 = 1024;
    
    /** Operating system is unknown.  */
    public  final int OS_OTHER = 65536;
    
    /** Operating system is Solaris.  */
    public  final int OS_SOLARIS = 8;
    
    /** Operating system is Sun OS.  */
    public  final int OS_SUNOS = 256;
    
    /** Operating system is Compaq TRU64 Unix  */
    public  final int OS_TRU64 = 512;
    
    /** A mask for Unix platforms.  */
    public  final int OS_UNIX_MASK = OS_SOLARIS | OS_LINUX | OS_HP | OS_AIX | OS_IRIX | OS_SUNOS | OS_TRU64 | OS_MAC;
    
    /** Operating system is Compaq OpenVMS  */
    public  final int OS_VMS = 8192;
    
    /** Operating system is Windows 2000.  */
    public  final int OS_WIN2000 = 4096;
    
    /** Operating system is Windows 95.  */
    public  final int OS_WIN95 = 2;
    
    /** Operating system is Windows 98.  */
    public  final int OS_WIN98 = 4;
    
    /** Operating system is Windows NT.  */
    public  final int OS_WINNT = 1;
    
    /**
     * Operating system is one of the Windows variants but we don't know which
     * one it is
     */
    public  final int OS_WIN_OTHER = 16384;
    
    /** A mask for Windows platforms.  */
    public  final int OS_WINDOWS_MASK = OS_WINNT | OS_WIN95 | OS_WIN98 | OS_WIN2000 | OS_WIN_OTHER;
    
    
    private String os ;
    private String j2meInstall;
    private String j2mePath;
    
    private String forteHome;
    private String jdkHome;
    
    private String script = "";
    
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
        
        forteHome=(String)System.getProperties().get("forteHome");
        
        // Find out if pointbase is installed
        try {
            String productURL = ProductService.DEFAULT_PRODUCT_SOURCE;
            ProductService productService = (ProductService)getService(ProductService.NAME);
            ProductTree productTree = productService.getSoftwareObjectTree(productURL);
            StandardProductTreeIterator iterator = new StandardProductTreeIterator(productTree);
            for(ProductBean productbean = iterator.getNext(iterator.begin()); productbean != iterator.end(); productbean = iterator.getNext(productbean)) {
                if(productbean.getDisplayName().equals("j2me")){
                    if(productbean.isActive())
                        j2meInstall="yes";
                    else
                        j2meInstall="no";
                }
            }
            
        } catch (ServiceException e) {
        }
        System.getProperties().put("j2meInstall",j2meInstall);
        logEvent(this, Log.DBG, "J2ME Install " + j2meInstall);
        
        if(j2meInstall.equals("yes")){
            //state.setStatusDescription(actionText);
            os = System.getProperty("os.name");
            if (os.startsWith("Windows")) {
                j2mePath=forteHome+File.separator+"emulator"+File.separator+"j2mewtk-1_0_3-win";
            }
            if (os.startsWith("Linux")) {
                j2mePath=forteHome+File.separator+"emulator"+File.separator+"j2mewtk-1_0_3-linux";
            }
            if (os.startsWith("SunOS")) {
                j2mePath=forteHome+File.separator+"emulator"+File.separator+"j2mewtk-1_0_3-solsparc";
            }
            try{
                install(j2mePath);
            }catch(IOException ex){
                logEvent(this, Log.ERROR, ex);
            }
        }
    }
    
    public  void install(String targetDir) throws IOException {
        if (isWindows()) installWindows(targetDir);
        else if (isUnix()) installUnix(targetDir);
    }
    private  void installWindows(String targetDir) throws IOException {
        File folder = new File(targetDir);
        File binFolder = new File(folder, "bin");
        //String java = System.getProperty("java.home");
        String java = jdkHome;
        writeFile(binFolder, "emulator.bat",
        DEFAULT_STARTUP_WIN,
        new Object[] {java, quoteString(targetDir), "com.sun.kvem.environment.EmulatorWrapper" }
        );
        writeFile(binFolder, "DefaultDevice.bat",
        DEFAULT_STARTUP_WIN,
        new Object[] {java, quoteString(targetDir), "com.sun.kvem.preferences.DefaultDeviceWindow" }
        );
        writeFile(binFolder, "prefs.bat",
        DEFAULT_STARTUP_WIN,
        new Object[] {java, quoteString(targetDir), "com.sun.kvem.preferences.Preferences" }
        );
        writeFile(binFolder, "utils.bat",
        DEFAULT_STARTUP_WIN,
        new Object[] {java, quoteString(targetDir), "com.sun.kvem.preferences.Utilities" }
        );
    }
    
    private  void installUnix(String targetDir) throws IOException {
        File folder = new File(targetDir);
        File binFolder = new File(folder, "bin");
        //String java = System.getProperty("java.home");
        String java = jdkHome;
        writeFile(binFolder, "emulator",
        DEFAULT_STARTUP_UNIX,
        new Object[] {java, "com.sun.kvem.environment.EmulatorWrapper", " 0" }
        );
        setPermission(binFolder, "emulator");
        writeFile(binFolder, "defaultdevice",
        DEFAULT_STARTUP_UNIX,
        new Object[] {java, "com.sun.kvem.preferences.DefaultDeviceWindow", "" }
        );
        setPermission(binFolder,  "defaultdevice");
        writeFile(binFolder, "prefs",
        DEFAULT_STARTUP_UNIX,
        new Object[] {java, "com.sun.kvem.preferences.Preferences", "" }
        );
        setPermission(binFolder,  "prefs");
        writeFile(binFolder, "utils",
        DEFAULT_STARTUP_UNIX,
        new Object[] {java, "com.sun.kvem.preferences.Utilities", "" }
        );
        setPermission(binFolder, "utils");
        setPermission(binFolder, "preverify");
        setPermission(binFolder, "zayit");
    }
    
    private  void setPermission(File folder, String file) throws IOException {
        File f= new File(folder, file);
        logEvent(this, Log.DBG, "Setting Permission " + f.getCanonicalPath());
        if (f.isFile()) {
            Runtime.getRuntime().exec(new String[] {
                "chmod", // NOI18N
                "+x", // NOI18N,
                f.getCanonicalPath()
            });
        }
    }
    
    private  String quoteString(String s) {
        if (s.indexOf(' ') != -1) return '\"' + s + '\"';
        return s;
    }
    
    /** Write file.
     * @param folder folder
     * @param name name of the file
     * @param text text, it can be a format suitable for java.text.MessageFormat
     * @param params additional parameter for MessageFormat
     */
    private  void writeFile(File folder, String name, String text, Object[] params) throws IOException {
        File f = new File(folder, name);
        logEvent(this, Log.DBG, "Modifying J2ME Script - " + f.getAbsolutePath());
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(new FileWriter(f));
            String s = MessageFormat.format(text, params);
            pw.write(s);
        } finally {
            if (pw != null) pw.close();
        }
    }
    /** Test whether the IDE is running on some variant of Unix.
     * Linux is included as well as the commercial vendors.
     * @return <code>true</code> some sort of Unix, <code>false</code> if some other manner of operating system
     */
    public  final boolean isUnix() {
        return (getOperatingSystem() & OS_UNIX_MASK) != 0;
    }
    
    /** Test whether the IDE is running on some variant of Windows.
     * @return <code>true</code> if Windows, <code>false</code> if some other manner of operating system
     */
    public  final boolean isWindows() {
        return (getOperatingSystem() & OS_WINDOWS_MASK) != 0;
    }
    
    /** Get the operating system on which the IDE is running.
     * @return one of the <code>OS_*</code> constants (such as {@link #OS_WINNT})
     */
    public  final int getOperatingSystem() {
        if (operatingSystem == -1) {
            String osName = System.getProperty("os.name");
            if ("Windows NT".equals(osName)) // NOI18N
                operatingSystem = OS_WINNT;
            else if ("Windows 95".equals(osName)) // NOI18N
                operatingSystem = OS_WIN95;
            else if ("Windows 98".equals(osName)) // NOI18N
                operatingSystem = OS_WIN98;
            else if ("Windows 2000".equals(osName)) // NOI18N
                operatingSystem = OS_WIN2000;
            else if (osName.startsWith("Windows ")) // NOI18N
                operatingSystem = OS_WIN_OTHER;
            else if ("Solaris".equals(osName)) // NOI18N
                operatingSystem = OS_SOLARIS;
            else if (osName.startsWith("SunOS")) // NOI18N
                operatingSystem = OS_SOLARIS;
            // JDK 1.4 b2 defines os.name for me as "Redhat Linux" -jglick
            else if (osName.endsWith("Linux")) // NOI18N
                operatingSystem = OS_LINUX;
            else if ("HP-UX".equals(osName)) // NOI18N
                operatingSystem = OS_HP;
            else if ("AIX".equals(osName)) // NOI18N
                operatingSystem = OS_AIX;
            else if ("Irix".equals(osName)) // NOI18N
                operatingSystem = OS_IRIX;
            else if ("SunOS".equals(osName)) // NOI18N
                operatingSystem = OS_SUNOS;
            else if ("Digital UNIX".equals(osName)) // NOI18N
                operatingSystem = OS_TRU64;
            else if ("OS/2".equals(osName)) // NOI18N
                operatingSystem = OS_OS2;
            else if ("OpenVMS".equals(osName)) // NOI18N
                operatingSystem = OS_VMS;
            else if (osName.equals("Mac OS X")) // NOI18N
                operatingSystem = OS_MAC;
            else if (osName.startsWith("Darwin")) // NOI18N
                operatingSystem = OS_MAC;
            else
                operatingSystem = OS_OTHER;
        }
        return operatingSystem;
    }
    
}