// DestinationPanel.java
// Modified on Dec 14,2001 by Winston Prakash

package com.nayaware.netinstaller;

import java.io.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;
import com.installshield.wizard.awt.*;
import com.installshield.product.*;
import com.installshield.product.i18n.*;
import com.installshield.wizardx.ui.*;
import com.installshield.wizardx.panels.ExtendedWizardPanel;
import com.installshield.wizard.*;
import com.installshield.wizard.service.*;
import com.installshield.wizard.service.file.*;
import com.installshield.product.service.product.*;
import com.installshield.util.*;
import com.installshield.wizard.console.*;

public class CustomDestinationPanel extends ExtendedWizardPanel {
    private String productURL = ProductService.DEFAULT_PRODUCT_SOURCE;
    
    private DirectoryInputComponent destinationField = null;
    private FileService fileService = null;
    private WizardUI ui = null;
    
    private static final String YES = "$L(com.installshield.wizard.i18n.WizardResources, yes)";
    private static final String NO = "$L(com.installshield.wizard.i18n.WizardResources, no)";
    private static final String CREATE_DIR = "createDirectoryResponse";
    private static final String YES_RESPONSE = "yes";
    private static final String NO_RESPONSE = "no";
    
    String btnYes="";
    String btnNo="";
    String okString = "$L(com.installshield.wizard.i18n.WizardResources, ok)";
    
    private String destinationToCreate = "";
    
    protected void initialize() {
        super.initialize();
        btnYes = resolveString("$L(com.installshield.wizard.i18n.WizardResources, yes)");
        btnNo = resolveString("$L(com.installshield.wizard.i18n.WizardResources, no)");
        setDescription("$L(com.installshield.product.i18n.ProductResources, " +
        "DestinationPanel.description, $P(displayName))");
        
        try {
            fileService = (FileService)getService(FileService.NAME);
        } catch (ServiceException e) {
            logEvent(this, Log.ERROR, e);
        }
        
        String forteHome=(String) System.getProperties().get("forteHome");
        if ((forteHome == null) || (forteHome.equals(""))){
            String os = System.getProperty("os.name");
            if (os.startsWith("Windows")){
                forteHome="C:\\forte4j";
            } else{
                String userHome = resolveString("$J(user.home)");
                forteHome=userHome+File.separator+"forte4j";
            }
        }
        logEvent(this, Log.DBG, "Default Forte Home: " + forteHome);
        Panel p1 = new Panel(new ColumnLayout());
        getContentPane().add(p1, BorderLayout.CENTER);
        p1.add(Spacing.createVerticalSpacing(10));
        destinationField = new DirectoryInputComponent(forteHome, getParentFrame(getContentPane()), fileService);
        p1.add(destinationField, ColumnConstraints.createHorizontalFill());
    }
    
    protected void createUI(WizardBeanEvent event) {
        super.createUI(event);
        ui = event.getUserInterface();
        destinationField.createComponentUI();
    }
    
    public boolean queryExit(WizardBeanEvent event) {
        boolean canContinue = true;
        if (destinationField != null) {
            canContinue = validateDestination(event);
            if(canContinue) canContinue = updateCheck();
            if (canContinue) updateProductTree(destinationField.getDirectoryName());
        }
        return canContinue;
    }
    
    private boolean validateDestination(WizardBeanEvent event) {
        if (destinationField == null) {
            throw new Error();
        }
        String dirName = destinationField.getDirectoryName();
        StringTokenizer st = new StringTokenizer(dirName);
        if (st.countTokens()>1) {
            String invalidMsg = resolveString("$L(com.nayaware.netinstaller.InstallerResources,INVALID_DESTINATION_MSG)");
            String invalidTitle = resolveString("$L(com.nayaware.netinstaller.InstallerResources,INVALID_DESTINATION_TITLE)");
            Frame parent = ((AWTWizardUI)getWizard().getUI()).getFrame();
            MessageDialog d= new MessageDialog(parent,invalidMsg,invalidTitle, new String[]{resolveString(okString)});
            d.setVisible(true);
            return false;
        }
        boolean dirExists = false;
        if (dirName.trim().length() > 0 && !dirName.equals("")) {
            if(!(new File(dirName)).exists()){
                String message = resolveString("$L(com.nayaware.netinstaller.InstallerResources,INST_DIR_ERRMSG)");
                String txt = resolveString("$L(com.nayaware.netinstaller.InstallerResources,INST_DIR_ERRTITLE)");
                Object[] objs = new Object[] { btnYes, btnNo };
                try {
                    Object obj = getWizard().getServices().getUserInput(txt,message, objs, btnYes);
                    if (obj == btnNo) {
                        return false;
                    }
                } catch (Exception e) {
                    logEvent(this, Log.ERROR, e);
                }
            }
            // check if destination writable
            try {
                if (isDirectoryWritable(dirName, fileService)) {
                    if (dirName.equals(destinationToCreate)) {
                        return true;
                    } else {
                        destinationToCreate = dirName;
                    }
                    return true;
                } else {
                    // prompt directory not writable
                    String writeError = LocalizedStringResolver.resolve(
                    ProductResources.NAME,
                    "notWritable",
                    new String[] { dirName });
                    
                    try {
                        getWizard().getServices().getUserInput(LocalizedStringResolver.resolve(
                        ProductResources.NAME,
                        "DestinationPanel.createTheDirectory"),
                        writeError,
                        new Object[] {
                            resolveString(okString)},
                            resolveString(okString));
                    } catch (ServiceException e) {
                        logEvent(this, Log.ERROR, e);
                    }
                    return false;
                }
            } catch (Exception e) {
                logEvent(this, Log.ERROR, e);
            }
        }else {
            String errorText = LocalizedStringResolver.resolve(
            ProductResources.NAME,
            "DestinationPanel.specifyDirectory");
            if (isConsoleInteraction()){
                TTYDisplay ttyDisplay = new TTYDisplay();
                ttyDisplay.initialize();
                ttyDisplay.showNewline();
                ttyDisplay.showText(errorText);
            }else {
                Frame parent = ((AWTWizardUI)getWizard().getUI()).getFrame();
                MessageDialog d= new MessageDialog(parent,errorText,resolveString(getDisplayName()),
                new String[]{resolveString(okString)});
                d.setVisible(true);
            }
        }
        return false;
    }
    
    private void updateProductTree(String destination) {
        try {
            ProductService service = (ProductService)getService(ProductService.NAME);
            service.setProductBeanProperty(
            productURL,
            null,
            "installLocation",
            resolveString(destination));
        } catch (ServiceException e) {
            logEvent(this, Log.ERROR, e);
        }
        System.getProperties().put("forteHome", resolveString(destination));
        logEvent(this, Log.DBG, "User Specified Forte Home: " + resolveString(destination));
    }
    
    /**
     * Recursive function for finding and getting the parent frame
     * of the given component; returns null to indicate that no
     * parent frame could be found in that recursion of the function.
     *
     * @param baseComponent for start of recursive call
     * @return Frame parent frame, or null
     *
     */
    public Frame getParentFrame(Component baseComponent) {
        if (baseComponent == null) {
            return null;
        }
        Component parentComponent = baseComponent.getParent();
        if (parentComponent == null) {
            return null;
        } else if (parentComponent instanceof Frame) {
            return (Frame) parentComponent;
        } else {
            return getParentFrame(parentComponent);
        }
    }
    
    public void execute(WizardBeanEvent event) {
        try {
            fileService = (FileService)getService(FileService.NAME);
            String resolvedDestination=(String) System.getProperties().get("forteHome");
            boolean dirExists = false;
            if (resolvedDestination.trim().length() > 0 && !resolvedDestination.equals("")) {
                dirExists = fileService.fileExists(resolvedDestination) ? true : false;
                if (!dirExists) {
                    if (getWizard().getServices().getValue(CREATE_DIR) != null) {
                        String response = (String)(getWizard().getServices().getValue(CREATE_DIR));
                        if (response.equals(YES_RESPONSE)) {
                            fileService.createDirectory(resolvedDestination);
                        }
                    }
                }
            }
        } catch (ServiceException e) {
            logEvent(this, Log.ERROR, e);
        }
    }
    
    public void build(WizardBuilderSupport support) {
        // register need for required services
        support.putRequiredService(ProductService.NAME);
        support.putRequiredService(FileService.NAME);
    }
    
    private boolean isDirectoryWritable(String dirName, FileService fileService) {
        try {
            createDirs(dirName, fileService);
            return true;
        } catch (Exception e) {
            logEvent(this, Log.WARNING, e);
            return false;
        }
    }
    
    private void createDirs(String dirName, FileService fileService) throws IOException, ServiceException {
        Stack parents = new Stack();
        String parent = dirName;
        while (parent != null) {
            if (!fileService.fileExists(parent)) {
                parents.push(parent);
            } else {
                break;
            }
            String parentName = fileService.getParent(parent);
            parent = parentName;
        }
        
        // Check if the directory already existed and has write permissions
        if (parents.isEmpty() && dirName != null) {
            if (!fileService.isDirectoryWritable(dirName)) {
                throw new IOException("Directory exists and its read-only");
            }
            return;
        }
        
        Vector created = new Vector();
        while (!parents.isEmpty()) {
            parent = (String)parents.pop();
            if (!fileService.fileExists(parent)) {
                fileService.createDirectory(parent);
                created.addElement(parent);
            } else {
                if (!fileService.isDirectoryWritable(parent)) {
                    throw new IOException("Directory is read-only");
                }
            }
        }
        for (int i = created.size()-1; i >= 0; i--) {
            if (fileService.fileExists((String)created.elementAt(i))) {
                fileService.deleteDirectory((String)created.elementAt(i));
            }
        }
    }
    
    private String mapResponse(String translatedResponse) {
        String response = NO_RESPONSE;
        
        if (translatedResponse.equals(resolveString(YES))) {
            response = YES_RESPONSE;
        } else if (translatedResponse.equals(resolveString(NO))) {
            response = NO_RESPONSE;
        }
        
        return response;
    }
    
    private boolean updateCheck() {
        
        boolean freeDir=true;
        boolean fullInstall=true;
        boolean uninstall=false;
        boolean system=false;
        String forteVersion="";
        
        if (destinationField == null) {
            throw new Error();
        }
        String destination = destinationField.getDirectoryName();
        File f=new File(destination);
        if(!f.exists()) return true;
        String[] fileString=f.list();
        if(fileString == null) return true;
        else if (fileString.length < 1) return true;
        
        try {
            f=new java.io.File(destination+f.separator+"uninstall.class");
            if (f.exists()) { uninstall=true; }
            f=new java.io.File(destination+f.separator+"bin");
            fileString=f.list();
            if (fileString==null || fileString.length<1) fullInstall=false;
            f=new java.io.File(destination+f.separator+"beans");
            fileString=f.list();
            if (fileString==null || fileString.length<1) fullInstall=false;
            f=new java.io.File(destination+f.separator+"docs");
            fileString=f.list();
            if (fileString==null || fileString.length<1) fullInstall=false;
            f=new java.io.File(destination+f.separator+"lib");
            fileString=f.list();
            if (fileString==null || fileString.length<1) fullInstall=false;
            f=new java.io.File(destination+f.separator+"modules");
            fileString=f.list();
            if (fileString==null || fileString.length<1) fullInstall=false;
            f=new java.io.File(destination+f.separator+"sources");
            fileString=f.list();
            if (fileString==null || fileString.length<1) fullInstall=false;
            f=new java.io.File(destination+f.separator+"system");
            fileString=f.list();
            if (fileString.length>0) {
                f=new java.io.File(destination+f.separator+"system"+f.separator+"ide.log");
                if (f.exists()) {
                    //System.out.println("system and ide.log exists");
                    forteVersion=checkSystemDir(destination+f.separator+"system"+f.separator+"ide.log");
                    if (!forteVersion.equals("")) system=true;
                }
                else uninstall=false;
                //System.out.println("system and ide.log not exists");
            } else uninstall=false;
            //System.out.println("system and ide.log not exists");
        }
        catch (Exception e) {
        }
        
        if ((fullInstall && !uninstall) || !system) {
            
            String txt = resolveString("$L(com.nayaware.netinstaller.InstallerResources,CONFIRM_TITLE)");
            String message = resolveString("$L(com.nayaware.netinstaller.InstallerResources,FILES_NO_VERSION)");
            Object[] objs = new Object[] { btnYes, btnNo };
            try {
                Object obj = getWizard().getServices().getUserInput(txt,message, objs, btnYes);
                if (obj == btnYes) {
                    System.getProperties().put("upgrade","yes");
                    System.getProperties().put("fullVersion","no");
                    System.getProperties().put("oldVersion",destination);
                    System.getProperties().put("forteVersion",new String(forteVersion));
                    return true;
                }else return false;
            } catch (Exception e) {
                logEvent(this, Log.ERROR, e);
            }
        }
        
        if (uninstall && system) {
            
            String txt = resolveString("$L(com.nayaware.netinstaller.InstallerResources,CONFIRM_TITLE)");
            String message = resolveString("$L(com.nayaware.netinstaller.InstallerResources,FILES_VERSION_UNINSTALLER)");
            String btnYes = resolveString("$L(com.installshield.wizard.i18n.WizardResources, yes)");
            String btnNo = resolveString("$L(com.installshield.wizard.i18n.WizardResources, no)");
            Object[] objs = new Object[] { btnYes, btnNo };
            try {
                Object obj = getWizard().getServices().getUserInput(txt,message, objs, btnYes);
                if (obj == btnYes) {
                    System.getProperties().put("upgrade","yes");
                    System.getProperties().put("fullVersion","yes");
                    System.getProperties().put("oldVersion",destination);
                    System.getProperties().put("forteVersion",new String(forteVersion));
                    return true;
                }else return false;
            } catch (Exception e) {
                logEvent(this, Log.ERROR, e);
            }
        }
        
        if (!fullInstall && !uninstall && system) {
            String txt = resolveString("$L(com.nayaware.netinstaller.InstallerResources,CONFIRM_TITLE)");
            String message = resolveString("$L(com.nayaware.netinstaller.InstallerResources,FILES_VERSION_NO_UNINSTALLER)");
            String btnYes = resolveString("$L(com.installshield.wizard.i18n.WizardResources, yes)");
            String btnNo = resolveString("$L(com.installshield.wizard.i18n.WizardResources, no)");
            Object[] objs = new Object[] { btnYes, btnNo };
            try {
                Object obj = getWizard().getServices().getUserInput(txt,message, objs, btnYes);
                if (obj == btnYes) {
                    System.getProperties().put("upgrade","yes");
                    System.getProperties().put("fullVersion","no");
                    System.getProperties().put("oldVersion",destination);
                    System.getProperties().put("forteVersion",new String(forteVersion));
                    return true;
                }else return false;
            } catch (Exception e) {
                logEvent(this, Log.ERROR, e);
            }
        }
        return true;
    }
    
    private String checkSystemDir(String s) {
        boolean passed=false;
        String forteVersion = "";
        String[] supVerCE = {"Forte(tm) for Java(tm), release 4.0, Community Edition",
        "Forte(tm) for Java(tm), release 3.0, Community Edition",
        "Forte(tm) for Java(tm), release 2.0, Internet Edition",
        "Forte(tm) for Java(tm), release 2.0, Community Edition",
        "NetBeans IDE 3.3",
        "NetBeans IDE 3.2",
        "NetBeans IDE 3.1",
        "NetBeans IDE v3.0"};
        String[] supVerEE = {"Forte(tm) for Java(tm), release 4.0, Enterprise Edition",
        "Forte(tm) for Java(tm), release 3.0, Enterprise Edition",
        "Forte(tm) for Java(tm), release 3.0, Community Edition",
        "Forte(tm) for Java(tm), release 2.0, Internet Edition",
        "Forte(tm) for Java(tm), release 2.0, Community Edition",
        "NetBeans IDE 3.3",
        "NetBeans IDE 3.2",
        "NetBeans IDE 3.1",
        "NetBeans IDE v3.0"};
        try {
            java.io.BufferedReader in =new java.io.BufferedReader(new java.io.FileReader(s));
            String s2 = new String();
            while((s2 = in.readLine())!= null && !passed) {
                if (s2.lastIndexOf("forteVersion")!=-1) {
                    if (getEdition().equalsIgnoreCase("ee")) {
                        for (int i=0; i<supVerEE.length ; i++) {
                            if (s2.lastIndexOf(supVerEE[i])!=-1) {
                                passed=true;
                                forteVersion=supVerEE[i];
                                break;
                            }
                        }
                    }
                    else {
                        for (int i=0; i<supVerCE.length ; i++) {
                            if (s2.lastIndexOf(supVerCE[i])!=-1) {
                                passed=true;
                                forteVersion=supVerCE[i];
                                break;
                            }
                        }
                    }
                }
            }
            in.close();
        }
        catch (Exception e) {
            return "";
        }
        return forteVersion;
    }
    
    public String getEdition() {
        return this.forteEdition;
    }
    
    public void setEdition() {
        forteEdition=(String)System.getProperties().get("forteEdition");
        if(forteEdition == null) forteEdition="ce";
    }
    
    private String forteEdition;
}
