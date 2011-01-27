package com.nayaware.netinstaller;

import java.util.ListResourceBundle;

public class InstallerResources_en extends ListResourceBundle {
    
    public InstallerResources_en(){
    }
    
    public Object[][] getContents(){
        return contents;
    }
    
    public static final String INVALID_DESTINATION_MSG = "INVALID_DESTINATION_MSG";
    public static final String INVALID_DESTINATION_TITLE = "INVALID_DESTINATION_TITLE";
    public static final String STARTUP_SCRIPTS = "STATRTUP_SCRIPTS";
    public static final String INITIALIZING_POINTBASE = "INITIALIZING_POINTBASE";
    public static final String ASSOCIATION = "ASSOCIATION";
    public static final String ASSOCIATION_QUESTION = "ASSOCIATION_QUESTION";
    public static final String ASSOCIATION_JAVA_RESPONSE = "ASSOCIATION_JAVA_RESPONSE";
    public static final String ASSOCIATION_NBM_RESPONSE = "ASSOCIATION_NBM_RESPONSE";
    public static final String CONFIRM_DIRECTORY_CREATION = "CONFIRM_DIRECTORY_CREATION";
    public static final String CONFIRM_TITLE = "CONFIRM_TITLE";
    public static final String REGISTRATION = "REGISTRATION";
    public static final String UNINSTALL_OLD_VERSION = "UNINSTALL_OLD_VERSION";
    public static final String BACKUP_OLD_VERSION = "BACKUP_OLD_VERSION";
    public static final String PRODUCT_INFORMATION="PRODUCT_INFORMATION";
    public static final String JAVA_LOCATION="JAVA_LOCATION";
    public static final String DESTINATION="DESTINATION";
    public static final String COMPONENTS= "COMPONENTS";
    public static final String CORE_FILES= "CORE_FILES";
    public static final String POINTBASE= "POINTBASE";
    public static final String TEAMWARE= "TEAMWARE";
    public static final String REGISTERING_IDE = "REGISTERING_IDE";
    public static final String JVM_WAIT = "JVM_WAIT";
    public static final String JVM_UNSUPPORT = "JVM_UNSUPPORT";
    public static final String JDK_WRN = "JDK_WRN";
    public static final String INSTALL_FORTE="INSTALL_FORTE";
    public static final String INSTALL_PB="INSTALL_PB";
    public static final String INSTALL_WITH="INSTALL_WITH";
    public static final String INSTALL_TW="INSTALL_TW";
    public static final String COPYRIGHT="COPYRIGHT";
    public static final String ENTER_REG_NUM="ENTER_REG_NUM";
    public static final String TRIAL_NUM_MSG="TRIAL_NUM_MSG";
    public static final String SER_NUM="SER_NUM";
    public static final String INVALID_SER_NUM="INVALID_SER_NUM";
    public static final String ERROR_SER_NUM="ERROR_SER_NUM";
    public static final String SER_NUM_MSG="SER_NUM_MSG";
    public static final String SER_NUM_GENER="SER_NUM_GENER";
    public static final String INFO_UPG_PANEL="INFO_UPG_PANEL";
    public static final String UPG_UNINSTALL_ERROR="UPG_UNINSTALL_ERROR";
    public static final String FILES_NO_VERSION="FILES_NO_VERSION";
    public static final String FILES_VERSION_UNINSTALLER="FILES_VERSION_UNINSTALLER";
    public static final String FILES_VERSION_NO_UNINSTALLER="FILES_VERSION_NO_UNINSTALLER";
    public static final String EDITION_TO_INSTALL="EDITION_TO_INSTALL";
    public static final String COMMUNITY_EDITION="COMMUNITY_EDITION";
    public static final String ENTERPRISE_EDITION="ENTERPRISE_EDITION";
    public static final String EDITION_INSTALLED="EDITION_INSTALLED";
    public static final String EDITION_TO_UNINSTALL="EDITION_TO_UNINSTALL";
    public static final String EDITION_UNINSTALLED="EDITION_UNINSTALLED";
    public static final String JVM_FOUND_DESCRIPTION="JVM_FOUND_DESCRIPTION";
    public static final String JVM_NOT_FOUND_DESCRIPTION="JVM_NOT_FOUND_DESCRIPTION";
    public static final String JVM_SPECIFY_LABEL = "JVM_SPECIFY_LABEL";
    public static final String JDK_SEARCH_MSG = "JDK_SEARCH_MSG";
    public static final String INVALID_JDK_HOME_MSG = "INVALID_JDK_HOME_MSG";
    public static final String INVALID_JDK_HOME_TITLE = "INVALID_JDK_HOME_TITLE";
    public static final String FCC_SPECIFY_LABEL = "FCC_SPECIFY_LABEL";
    public static final String FCC_PATH_DESCRIPTION = "FCC_PATH_DESCRIPTION";
    public static final String INVALID_FCC_PATH_MSG = "INVALID_FCC_PATH_MSG";
    public static final String INVALID_FCC_PATH_TITLE = "INVALID_FCC_PATH_TITLE";  
    public static final String FCC_PATH_NOT_SPECIFIED_MSG = "FCC_PATH_NOT_SPECIFIED_MSG";
    public static final String FCC_PATH_NOT_SPECIFIED_TITLE = "FCC_PATH_NOT_SPECIFIED_TITLE";      
    public static final String JDK_LIST_LABEL = "JDK_LIST_LABEL";   
    public static final String JDK_SEARCH_FAILED_MESSAGE = "JDK_SEARCH_FAILED_MESSAGE";
    public static final String BUTTON_BROWSE  = "BUTTON_BROWSE";
    public static final String POST_INSTALL_TITLE = "POST_INSTALL_TITLE";
    public static final String POST_INSTALL_ACTION_DESC = "POST_INSTALL_ACTION_DESC";
    public static final String POST_INSTALL_POINTBASE_ACTION_DESC = "POST_INSTALL_POINTBASE_ACTION_DESC";
    public static final String POST_INSTALL_J2EE_ACTION_DESC = "POST_INSTALL_J2EE_ACTION_DESC";
    public static final String CE_INSTALLER_TITLE = "CE_INSTALLER_TITLE";
    public static final String EE_INSTALLER_TITLE = "EE_INSTALLER_TITLE";
    public static final String ME_INSTALLER_TITLE = "ME_INSTALLER_TITLE";
    public static final String CE_UNINSTALLER_TITLE = "CE_UNINSTALLER_TITLE";
    public static final String EE_UNINSTALLER_TITLE = "EE_UNINSTALLER_TITLE";
    public static final String ME_UNINSTALLER_TITLE = "ME_UNINSTALLER_TITLE";    
      
    static final Object contents[][] = {
        {"INVALID_DESTINATION_MSG","Path with spaces is not supported!!"},
        {"INVALID_DESTINATION_TITLE","Invalid Destination"},
        {"ASSOCIATION", "Association"},
        {"ASSOCIATION_QUESTION", "Do You want to associate .java files with this IDE ?"},
        {"ASSOCIATION_JAVA_RESPONSE", "Yes I want to associate .java files with this IDE"},
        {"CONFIRM_DIRECTORY_CREATION", "The directory {0} does not exist. Do you want to create it now?"},
        {"CONFIRM_TITLE", "Confirm"}, 
        {"STARTUP_SCRIPTS", "Preparing startup scripts"},
        {"INITIALIZING_POINTBASE","Initializing Pointbase"},
        {"UNINSTALL_OLD_VERSION"," Uninstalling Old Version .. "},
        {"BACKUP_OLD_VERSION"," Backing Up Old Version .. "},
        {"INSTALL_FORTE","Install Forte for Java Platform and Core Modules"},
        {"INSTALL_PB","Install PointBase"},
        {"INSTALL_WITH","Possible programs to install with IDE."},
        {"INSTALL_TW","Install Forte TeamWare"},
        {"COPYRIGHT","Copyright 2001 Sun Microsystems, Inc. All rights reserved\n. Use is subject to license terms."},
        {"REGISTRATION","Serial Number"},
        {"SER_NUM_MSG","Enter the serial number you were given or leave the Serial Number field blank to get a trial serial number that expires on August 31, 2002."},
        {"SER_NUM","Serial Number: "},
        {"INVALID_SER_NUM","Entered serial number is invalid. Please enter a valid serial number."},
        {"ERROR_SER_NUM","Error: Invalid Serial Number"},
        {"SER_NUM_GENER_MSG","The serial number that will be generated expires on August 31, 2002. It will give you access to the Update Center services once you register the product. Please make a  note of this serial number. Would you like to generate a serial number now ?"},
        {"SER_NUM_GENER","Generate a Trial Version Serial Number ?"},
        {"INFO_UPG_PANEL","Please read informations below."},
        {"UPG_UNINSTALL_ERROR","Installer was not able to run the uninstaller of the previous version. Please close this installer, uninstall the previous version manualy and run this installer again."},
        {"FILES_NO_VERSION","The installer found some files in this directory. Either it was not\nable to recognize the version of the previous installation,\nor the previous installation can not be upgraded.\n\nWe strongly recommend that you choose another directory.\n\nDo you want to use selected directory and remove all files ?"},
        {"FILES_VERSION_UNINSTALLER","A previous installation of the IDE exists in the destination\ndirectory. Click Yes to uninstall it before upgrading. You\nwill be able to use your old settings in the new IDE.\nClick No to choose another installation directory."},
        {"FILES_VERSION_NO_UNINSTALLER","Installer has found files from the previous installation.\nYou can upgrade the previous installation and use\nyour old settings and data. If you want to upgrade, click\nYes. Otherwise click No and choose another destination."},
        {"EDITION_TO_INSTALL","Edition to install:"},
        {"ENTERPRISE_EDITION","Enterprise Edition"},
        {"COMMUNITY_EDITION","Community Edition"},
        {"EDITION_INSTALLED","Installed edition:"},
        {"EDITION_TO_UNINSTALL","Edition to uninstall:"},
        {"EDITION_UNINSTALLED","Uninstalled edition:"},
        {"JVM_FOUND_DESCRIPTION", "Installer found the following JDK(tm) to use with <product>. J2SE(tm) 1.4.0 is the recommended JDK. We strongly recommend that you specify it here."},
        {"JVM_NOT_FOUND_DESCRIPTION", "Installer could not find a suitable JDK(tm) to use with  <product>. J2SE(tm) 1.4.0 is the recommended JDK. We strongly recommend that you specify it here."},
        {"JVM_SPECIFY_LABEL", "JDK Home: "}, 
        {"INVALID_JDK_HOME_MSG", "Specified JDK(tm) Home is invalid"}, 
        {"INVALID_JDK_HOME_TITLE", "Invalid JDK(tm) Home"}, 
        {"FCC_PATH_DESCRIPTION", "The Forte(tm) Compiler Collection (FCC) is required by some of the components that you have chosen to install. Provide the path to your FCC software. Include the full path required to access the FCC if it is installed on a shared server. Leave the FCC path field blank if you do not readily know the FCC path."}, 
        {"FCC_PATH_NOT_SPECIFIED_MSG", "Even though you have chosen to install the Solaris Developer Modules, you did not provide the path to your  <product> Collection (FCC) software. You can specify the FCC path to use with the Solaris Developer tools at a later time. Refer to the Getting Started Guide for more details."}, 
        {"FCC_SPECIFY_LABEL", "FCC Path: "}, 
        {"INVALID_FCC_PATH_MSG", "Specified FCC Path is invalid"}, 
        {"INVALID_FCC_PATH_TITLE", "Invalid FCC Path"},         
        {"JDK_LIST_LABEL","List of searched JDK(s) ( 1.3.1 & 1.4)"},
        {"JDK_SEARCH_MSG"," Searching for Suitable JDK. Please wait .."},
        {"JDK_SEARCH_FAILED_MESSAGE","JDK Search failed to find suitable JDK!"},
        {"POST_INSTALL_TITLE","Post Install Process"},
        {"POST_INSTALL_ACTION_DESC","Preparing IDE Startup Scripts"},
        {"POST_INSTALL_POINTBASE_ACTION_DESC","Preparing pointbase Scripts"},
        {"POST_INSTALL_J2EE_ACTION_DESC","Preparing j2ee Scripts"},
              
        {"BUTTON_BROWSE","Browse"}
    };
}

