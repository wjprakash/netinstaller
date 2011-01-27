// SetSystemPropertyAction.java
// Last Modified on Feb 22, 2002 - Winston Prakash
 

package com.nayaware.netinstaller;

import java.io.*;
import com.installshield.wizard.*;
import com.installshield.wizard.service.*;
import com.installshield.product.service.product.*;
import com.installshield.product.*;
import com.installshield.util.*;
import com.installshield.archive.*;
import java.io.*;
import com.installshield.wizard.awt.*;
import com.installshield.wizard.console.*;


public class SetSystemPropertyAction extends WizardAction {
    
    private String productURL = ProductService.DEFAULT_PRODUCT_SOURCE;
    private String forteEdition = "";
    private String locale = "";
    
    private String prefix = "FJE400";
    
    private com.sun.forte.licen.SerialNumber serialNum = new com.sun.forte.licen.SerialNumber();
    
    public void setEdition(String forteEdition) {
        this.forteEdition = forteEdition;
    }
    
    public String getEdition() {
        return forteEdition;
    }
    
    public void build(WizardBuilderSupport support) {
        try {
            support.putArchive( "serapi.jar", new AllArchiveFilter() );
            support.putClass(JDKSearchAction.class.getName());
        } catch (Exception ex){
            logEvent(this, Log.ERROR, ex);
            logEvent(this, Log.DBG, ex);
        }
    }
    
    public void execute(WizardBeanEvent evt) {
        
        WizardUI ui = getWizard().getUI();
        if (ui instanceof AWTWizardUI){
            System.getProperties().put("uiMode","gui");
            logEvent(this, Log.DBG, "running in AWT mode.");
        } else if (ui instanceof ConsoleWizardUI) {
            System.getProperties().put("uiMode","console");
            logEvent(this, Log.DBG, "running in Console mode.");
        } else {
            System.getProperties().put("uiMode","silent");
            logEvent(this, Log.DBG, "running in Silent mode.");
        }
        
        String uiMode = (String) System.getProperties().get("uiMode");
        if(uiMode == null) uiMode="gui";
        
        // Check if user specified forteHome (alias fh) in the command Line
        // forteHome property always overrides the alias fh
        
        String forteHome =  resolveString("$J(forteHome)");
        if (forteHome == null) forteHome = "";
        if (forteHome.equals("")){
            forteHome =  resolveString("$J(fh)");
            if (forteHome == null) forteHome = "";
        }
        
        if((uiMode.equals("silent"))){
            if (forteHome.equals("")) {
                System.out.println(resolveString("$L(com.nayaware.netinstaller.InstallerResources,DEST_NOT_SPECIFIED_MSG)"));
                System.exit(-1);
            }
            File forteDir = new File(forteHome);
            if (forteDir.exists()){
                if (!forteDir.isDirectory()) {
                    System.out.println(resolveString("$L(com.nayaware.netinstaller.InstallerResources,DEST_INVALID_MSG)"));
                    System.exit(-1);
                }
                if (!(forteDir.canWrite())) {
                    System.out.println(resolveString("$L(com.nayaware.netinstaller.InstallerResources,DEST_NO_WRITE_PERMISSION_MSG)"));
                    System.exit(-1);
                }
            }else{
                if(!forteDir.mkdirs()){
                    System.out.println(resolveString("$L(com.nayaware.netinstaller.InstallerResources,DEST_COULD_NOT_CREATE_MSG)"));
                    System.exit(-1);
                }
            }
            try {
                ProductService service = (ProductService)getService(ProductService.NAME);
                service.setProductBeanProperty(
                productURL,
                null,
                "installLocation",
                resolveString(forteHome));
            } catch (ServiceException ex) {
                logEvent(this, Log.ERROR, ex);
                logEvent(this, Log.DBG, ex);
            }
        }
        
        // Check if user has specified jdkHome (alias jh) in the command Line
        // jdkHome property always overrides the alias jh
        
        String jdkHome = resolveString("$J(jdkHome)");
        if (jdkHome == null) jdkHome = "";
        if(jdkHome.equals("")){
            jdkHome = resolveString("$J(jh)");
            if (jdkHome == null) jdkHome = "";
        }
        
        if((uiMode.equals("silent"))){
            if (jdkHome.equals("")) {
                System.out.println(resolveString("$L(com.nayaware.netinstaller.InstallerResources,JDK_NOT_SPECIFIED_MSG)"));
                System.exit(-1);
            }
            JDKSearchAction jdkSearch = new JDKSearchAction();
            if(!jdkSearch.checkJdkHome(jdkHome)){
                System.out.println(resolveString("$L(com.nayaware.netinstaller.InstallerResources,JDK_INVALID_MSG)"));
                System.exit(-1);
            }
        }
        
        // Check if user has specified serialNumber (alias sn) in the command Line
        // serialNumber property always overrides the alias sn
        
        String serialNumber =  resolveString("$J(serialNumber)");
        if(serialNumber == null) serialNumber = "";
        if (serialNumber.equals("")){
            serialNumber =  resolveString("$J(sn)");
            if(serialNumber == null) serialNumber = "";
        }
        
        // The following code is needed if the installer is
        // invoked in silent mode
        if((uiMode.equals("silent"))){
            if (!serialNumber.equals("")){
                if (serialNumber.equalsIgnoreCase("trial")){
                    serialNumber=getTrialSerialNumber();
                } else {
                    serialNum.setSerialNo(serialNumber);
                    if (!serialNum.isValid()){
                        System.out.println(resolveString("$L(com.nayaware.netinstaller.InstallerResources,SER_NUM_INVALID_MSG)"));
                        System.exit(-1);
                    }
                }
            }else{
                if(getEdition().equals("ee")){
                    System.out.println(resolveString("$L(com.nayaware.netinstaller.InstallerResources,SER_NUM_NOT_SPECIFIED_MSG)"));
                    System.exit(-1);
                }
            }
        }
        
        // Check if user specified j2eeInstall (alias ji) in the command Line
        
        String j2eeInstall =   resolveString("$J(j2eeInstall)");
        if(j2eeInstall == null) j2eeInstall = "";
        if(j2eeInstall.equals("")){
            j2eeInstall =   resolveString("$J(ji)");
            if(j2eeInstall == null) j2eeInstall = "";
        }
        
        // Check if user specified  pointbaseInstall (alias pi) in the command Line
        
        String pointbaseInstall = resolveString("$J(pointbaseInstall)");
        if(pointbaseInstall == null) pointbaseInstall = "";
        if(pointbaseInstall.equals("")){
            pointbaseInstall =   resolveString("$J(pi)");
            if(pointbaseInstall == null) pointbaseInstall = "";
        }
        
        // Check if user specified  soldevInstall (alias si) in the command Line
        
        String soldevInstall = resolveString("$J(soldevInstall)");
        if(soldevInstall == null) soldevInstall = "";
        if(soldevInstall.equals("")){
            soldevInstall =   resolveString("$J(si)");
            if(soldevInstall == null) soldevInstall = "";
        }
        
          // Check if user specified j2eeInstall (alias ji) & pointbaseInstall (alias pi)
        // in the command Line
        try {
            ProductService service = (ProductService) getService(ProductService.NAME);
            String productSource = ProductService.DEFAULT_PRODUCT_SOURCE;
            
            if(j2eeInstall.trim().equals("no")){
                service.setProductBeanProperty(productSource, "j2eeFeature", "Active", "False");
            }else{
                j2eeInstall = "yes";
                service.setProductBeanProperty(productSource, "j2eeFeature", "Active", "True");
            }          
            logEvent(this, Log.DBG, "Is j2eeFeature active - " + service.getProductBeanProperty(productSource, "j2eeFeature", "Active" ));
            
            if(pointbaseInstall.trim().equals("no")){   
                service.setProductBeanProperty(productSource, "pointbaseFeature", "Active", "False");
            } else{
                pointbaseInstall = "yes";
                service.setProductBeanProperty(productSource, "pointbaseFeature", "Active", "True");
            }
                
           logEvent(this, Log.DBG,"Is pointbaseFeature active - " + service.getProductBeanProperty(productSource, "pointbaseFeature", "Active" ));            
            
            if(soldevInstall.trim().equals("no")){
                service.setProductBeanProperty(productSource, "solDevFeature", "Active", "False");
            } else{
                soldevInstall = "yes";
                service.setProductBeanProperty(productSource, "solDevFeature", "Active", "True");
            }         
            logEvent(this, Log.DBG,"Is solDevFeature active - " + service.getProductBeanProperty(productSource, "solDevFeature", "Active" ));            
        } catch (ServiceException e) {
            logEvent(this, Log.ERROR, e);
        }

        
        // J2EE RI requires Pointbase. So it will be installed
        // if RI is installed
        if(getEdition().equals("ee")){
            if(j2eeInstall.equals("yes")){
                if(pointbaseInstall.equals("no")){
                    System.out.println(resolveString("$L(com.nayaware.netinstaller.InstallerResources,POINTBASE_REQUIRED_MSG)"));
                    pointbaseInstall="yes";
                }
            }
        }
        
        System.getProperties().put("forteHome",forteHome);
        System.getProperties().put("jdkHome",jdkHome);
        System.getProperties().put("serialNumber",serialNumber.trim());
        System.getProperties().put("forteEdition",getEdition());
        System.getProperties().put("j2eeInstall",j2eeInstall);
        System.getProperties().put("pointbaseInstall",pointbaseInstall);
        
        logEvent(this, Log.DBG,"forteHome: "+forteHome);
        logEvent(this, Log.DBG,"jdkHome: " + jdkHome);
        logEvent(this, Log.DBG,"serialNumber: "+serialNumber.trim());
        logEvent(this, Log.DBG,"forteEdition: "+getEdition());
        logEvent(this, Log.DBG,"j2eeInstall: "+j2eeInstall);
        logEvent(this, Log.DBG,"pointbaseInstall: "+pointbaseInstall);
        logEvent(this, Log.DBG,"soldevInstall: "+soldevInstall);
    }
    
    // For EA hard code this with serial number that would expire on
    // Aug 31 2002
    
    public String getTrialSerialNumber(){
        return "FJE400-999C9IEN9-028635073";
    }
    
    public String getPrefix() {
        return prefix;
    }
    
    public void setPrefix(String prefix) {
        this.prefix=prefix;
    }
}
