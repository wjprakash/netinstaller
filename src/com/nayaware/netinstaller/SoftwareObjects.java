// SoftwareObjects.java
// Author  Winston Prakash

package com.nayaware.netinstaller;

import java.util.*;

public class SoftwareObjects {
    
    private static Vector objectList = new Vector();
    private static String edition="ce";
    
    public SoftwareObjects() {
    }
    
    public static void addObject(ComponentDescriptor comp){
        //System.out.println("Adding Object :" + comp.getName());
        objectList.add(comp);
    }
    
    public static int getSize(){
        return objectList.size();
    }
    
    public static void setEdition(String editionx){
        edition = editionx;
    }
    
    public static String getEdition(){
        return edition;
    }
    
    public static ComponentDescriptor getObject(int i){
        return (ComponentDescriptor) objectList.get(i);
    }
    
    public static void setObjectList(){
        String os = System.getProperty("os.name");
        String arch = System.getProperty("os.arch");
        objectList = new Vector();
        //System.out.println("Edition: " + getEdition());
        objectList.removeAllElements();
        ComponentDescriptor coreComponent = new ComponentDescriptor();
        coreComponent.setDisplayName("Core Platform Modules");
        coreComponent.setName("core");
        coreComponent.setRequired(true);
        addObject(coreComponent);
        if(getEdition().equals("ce") || getEdition().equals("ee")){
            ComponentDescriptor pointbaseComponent = new ComponentDescriptor();
            pointbaseComponent.setDisplayName("PointBase Database");
            pointbaseComponent.setName("pointbase");
            pointbaseComponent.setInstallerName("ffj_pointbase.jar");
            addObject(pointbaseComponent);
            if(getEdition().equals("ee")){
                coreComponent.setInstallerName("ffj_ee.jar");
                ComponentDescriptor j2eeComponent = new ComponentDescriptor();
                j2eeComponent.setDisplayName("J2EE Reference Implementation");
                j2eeComponent.setName("j2ee");
                j2eeComponent.setRequiredComponent(pointbaseComponent);
                addObject(j2eeComponent);
                if (os.startsWith("SunOS")) {
                    j2eeComponent.setInstallerName("ffj_j2ee_sol.jar");
                    ComponentDescriptor soldevComponent = new ComponentDescriptor();
                    soldevComponent.setDisplayName("Solaris Developer Modules");
                    soldevComponent.setName("soldev");
                    addObject(soldevComponent);
                    if(arch.startsWith("sparc"))
                        soldevComponent.setInstallerName("ffj_soldev_sparc.jar");
                    else
                        soldevComponent.setInstallerName("ffj_soldev_x86.jar");
                }
                if (os.startsWith("Windows")){
                    j2eeComponent.setInstallerName("ffj_j2ee_win.jar");
                }
                if (os.startsWith("Linux")){
                    j2eeComponent.setInstallerName("ffj_j2ee_lin.jar");
                }
            }
            if(getEdition().equals("ce")){
                coreComponent.setInstallerName("ffj_ce.jar");
            }
        }
        if(getEdition().equals("me")){
            coreComponent.setInstallerName("ffj_me.jar");
            ComponentDescriptor j2meComponent = new ComponentDescriptor();
            j2meComponent.setDisplayName("J2ME Wireless Toolkit");
            j2meComponent.setName("j2me");
            j2meComponent.setRequired(true);
            addObject(j2meComponent);
            if (os.startsWith("SunOS")) {
                j2meComponent.setInstallerName("ffj_j2me_sol.jar");
            }
            if (os.startsWith("Windows")){
                j2meComponent.setInstallerName("ffj_j2me_win.jar");
            }
            if (os.startsWith("Linux")){
                j2meComponent.setInstallerName("ffj_j2me_lin.jar");
            }
        }
    }
}
