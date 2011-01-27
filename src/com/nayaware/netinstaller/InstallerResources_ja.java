package com.nayaware.netinstaller;

import java.util.ListResourceBundle;

public class InstallerResources_ja extends ListResourceBundle {
    
    public InstallerResources_ja(){
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
    public static final String SER_NUM_GENER_MSG="SER_NUM_GENER_MSG";
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
//      {"INVALID_DESTINATION_MSG","Path with spaces is not supported!!"},
        {"INVALID_DESTINATION_MSG","\u30b9\u30da\u30fc\u30b9\u3092\u542b\u3080\u30d1\u30b9\u306f\u30b5\u30dd\u30fc\u30c8\u3055\u308c\u3066\u3044\u307e\u305b\u3093!!"},

//      {"INVALID_DESTINATION_TITLE","Invalid Destination"},
        {"INVALID_DESTINATION_TITLE","\u7121\u52b9\u306a\u51fa\u529b\u5148"},

        {"ASSOCIATION", "\u30d5\u30a1\u30a4\u30eb\u306e\u95a2\u9023\u4ed8\u3051"},
        {"ASSOCIATION_QUESTION", ".java \u304a\u3088\u3073 .nbm (Netbeans \u30e2\u30b8\u30e5\u30fc\u30eb) \u62e1\u5f35\u5b50\u306e\u30d5\u30a1\u30a4\u30eb\u3092 IDE \u3068\u95a2\u9023\u4ed8\u3051\u3057\u307e\u3059\u304b ?"},
        {"ASSOCIATION_JAVA_RESPONSE", ".java \u30d5\u30a1\u30a4\u30eb\u3092 IDE \u3068\u95a2\u9023\u4ed8\u3051\u3057\u307e\u3059"},
        {"ASSOCIATION_NBM_RESPONSE", ".nbm \u30d5\u30a1\u30a4\u30eb\u3092 IDE \u3068\u95a2\u9023\u4ed8\u3051\u3057\u307e\u3059"},
        {"CONFIRM_DIRECTORY_CREATION", "\u30c7\u30a3\u30ec\u30af\u30c8\u30ea {0} \u304c\u3042\u308a\u307e\u305b\u3093\u3002\u4eca\u30c7\u30a3\u30ec\u30af\u30c8\u30ea\u3092\u4f5c\u6210\u3057\u307e\u3059\u304b ?"},

//	{"CONFIRM_TITLE", "Confirm"},
        {"CONFIRM_TITLE", "\u78ba\u8a8d"}, 

//      {"STARTUP_SCRIPTS", "Preparing startup scripts"},
        {"STARTUP_SCRIPTS", "\u8d77\u52d5\u30b9\u30af\u30ea\u30d7\u30c8\u3092\u6e96\u5099\u4e2d"},

        {"INITIALIZING_POINTBASE","PointBase \u3092\u521d\u671f\u5316\u4e2d"},

//      {"UNINSTALL_OLD_VERSION"," Uninstalling Old Version"},
        {"UNINSTALL_OLD_VERSION", "\u53e4\u3044\u30d0\u30fc\u30b8\u30e7\u30f3\u3092\u30a2\u30f3\u30a4\u30f3\u30b9\u30c8\u30fc\u30eb\u4e2d"},

//       {"BACKUP_OLD_VERSION"," Backing Up Old Version .. "},
        {"BACKUP_OLD_VERSION","\u53e4\u3044\u30d0\u30fc\u30b8\u30e7\u30f3\u3092\u30d0\u30c3\u30af\u30a2\u30c3\u30d7\u4e2d .. "},

//      {"INSTALL_FORTE","Install Forte for Java Platform and Core Modules"},
        {"INSTALL_FORTE","=Forte for Java \u30d7\u30e9\u30c3\u30c8\u30d5\u30a9\u30fc\u30e0\u304a\u3088\u3073\u30b3\u30a2\u30e2\u30b8\u30e5\u30fc\u30eb\u3092\u30a4\u30f3\u30b9\u30c8\u30fc\u30eb\u4e2d"},

//      {"INSTALL_PB","Install PointBase"},
        {"INSTALL_PB","PointBase \u3092\u30a4\u30f3\u30b9\u30c8\u30fc\u30eb\u4e2d"},

//      {"INSTALL_WITH","Possible programs to install with IDE."},
        {"INSTALL_WITH","IDE \u3067\u30a4\u30f3\u30b9\u30c8\u30fc\u30eb\u53ef\u80fd\u306a\u30d7\u30ed\u30b0\u30e9\u30e0"},

//      {"INSTALL_TW","Install Forte TeamWare"},
        {"INSTALL_TW","Forte TeamWare \u3092\u30a4\u30f3\u30b9\u30c8\u30fc\u30eb\u4e2d"},

        {"COPYRIGHT","Copyright 2001 Sun Microsystems, Inc. All rights reserved\n. \u672c\u88fd\u54c1/\u30c9\u30ad\u30e5\u30e1\u30f3\u30c8\u306e\u4f7f\u7528\u306f\u3001\u30e9\u30a4\u30bb\u30f3\u30b9\u6761\u9805\u306b\u5f93\u3046\u3082\u306e\u3068\u3057\u307e\u3059\u3002"},
        {"REGISTRATION","\u30b7\u30ea\u30a2\u30eb\u756a\u53f7"},
        {"ENTER_REG_NUM","\u30b7\u30ea\u30a2\u30eb\u756a\u53f7\u3092\u5165\u529b\u3057\u3066\u304f\u3060\u3055\u3044\u3002"},

//      {"TRIAL_NUM_MSG","Leave the Serial Number field blank to get a trial license which expires on August 31, 2002."},
        {"TRIAL_NUM_MSG","2002 \u5e74 8 \u6708 31 \u65e5\u306b\u671f\u9650\u5207\u308c\u306b\u306a\u308b\u8a66\u7528\u30e9\u30a4\u30bb\u30f3\u30b9\u3092\u53d6\u5f97\u3059\u308b\u305f\u3081\u306b\u30b7\u30ea\u30a2\u30eb\u756a\u53f7\u30d5\u30a3\u30fc\u30eb\u30c9\u3092\u7a7a\u767d\u306e\u307e\u307e\u306b\u3057\u307e\u3059\u3002"},

        {"SER_NUM","\u30b7\u30ea\u30a2\u30eb\u756a\u53f7: "},
        {"INVALID_SER_NUM","\u30b7\u30ea\u30a2\u30eb\u756a\u53f7\u304c\u7121\u52b9\u3067\u3059\u3002\u6709\u52b9\u306a\u30b7\u30ea\u30a2\u30eb\u756a\u53f7\u3092\u5165\u529b\u3057\u3066\u304f\u3060\u3055\u3044\u3002"},
        {"ERROR_SER_NUM","\u30a8\u30e9\u30fc: \u7121\u52b9\u306a\u30b7\u30ea\u30a2\u30eb\u756a\u53f7\u3067\u3059"},
        {"SER_NUM_GENER_MSG","60 \u65e5\u9593\u306e\u8a66\u7528\u7248\u30e9\u30a4\u30bb\u30f3\u30b9\u3092\u4f7f\u3063\u3066\u88fd\u54c1\u306e\u767b\u9332\u3092\u884c\u3046\u3068\n\u30a2\u30c3\u30d7\u30c7\u30fc\u30c8\u30bb\u30f3\u30bf\u30fc\u306e\u30b5\u30fc\u30d3\u30b9\u306b\u30a2\u30af\u30bb\u30b9\u3059\u308b\u3053\u3068\u304c\u3067\u304d\u307e\u3059\u3002\n\u3053\u306e\u30b7\u30ea\u30a2\u30eb\u756a\u53f7\u3092\u8a18\u9332\u3057\u3066\u304a\u3044\u3066\u304f\u3060\u3055\u3044\u3002\n\n\u30b7\u30ea\u30a2\u30eb\u756a\u53f7\u3092\u4eca\u751f\u6210\u3057\u307e\u3059\u304b ?"},
        {"SER_NUM_GENER","\u8a66\u7528\u7248\u306e\u30b7\u30ea\u30a2\u30eb\u756a\u53f7\u3092\u751f\u6210\u3057\u307e\u3059\u304b ?"},
        {"INFO_UPG_PANEL","\u6b21\u306e\u60c5\u5831\u3092\u304a\u8aad\u307f\u304f\u3060\u3055\u3044\u3002"},
        {"UPG_UNINSTALL_ERROR","\u3059\u3067\u306b\u30a4\u30f3\u30b9\u30c8\u30fc\u30eb\u3055\u308c\u3066\u3044\u308b\u88fd\u54c1\u306e\u30a2\u30f3\u30a4\u30f3\u30b9\u30c8\u30fc\u30e9\u3092\u8d77\u52d5\u3067\u304d\u307e\u305b\u3093\u3067\u3057\u305f\u3002\u3053\u306e\u30a4\u30f3\u30b9\u30c8\u30fc\u30e9\u3092\u505c\u6b62\u3057\u3001\u524d\u306e\u88fd\u54c1\u3092\u624b\u52d5\u3067\u30a2\u30f3\u30a4\u30f3\u30b9\u30c8\u30fc\u30eb\u3057\u305f\u5f8c\u3067\u30b5\u30a4\u30c9\u30a4\u30f3\u30b9\u30c8\u30fc\u30e9\u3092\u5b9f\u884c\u3057\u3066\u304f\u3060\u3055\u3044\u3002"},

//      {"FILES_NO_VERSION","The installer found some files in this directory. Either it was not\nable to recognize the version of the previous installation,\nor the previous installation can not be upgraded.\n\nWe strongly recommend that you choose another directory.\n\nDo you want to use selected directory and remove all files ?"},
        {"FILES_NO_VERSION","\u3053\u306e\u30c7\u30a3\u30ec\u30af\u30c8\u30ea\u306b\u3044\u304f\u3064\u304b\u306e\u30d5\u30a1\u30a4\u30eb\u304c\u3042\u308a\u307e\u3059\u3002\u4ee5\u524d\u306b\u30a4\u30f3\u30b9\u30c8\u30fc\u30eb\n\u3057\u305f\u88fd\u54c1\u3092\u8a8d\u8b58\u3067\u304d\u306a\u3044\u304b\u3001\u30a2\u30c3\u30d7\u30b0\u30ec\u30fc\u30c9\u3067\u304d\u307e\u305b\u3093\u3002\n\n\u4ed6\u306e\u30c7\u30a3\u30ec\u30af\u30c8\u30ea\u306b\u30a4\u30f3\u30b9\u30c8\u30fc\u30eb\u3059\u308b\u3053\u3068\u3092\u63a8\u5968\u3057\u307e\u3059\u3002\n\n\u3053\u306e\u30c7\u30a3\u30ec\u30af\u30c8\u30ea\u3092\u4f7f\u7528\u3057\u3066\u3059\u3079\u3066\u306e\u30d5\u30a1\u30a4\u30eb\u3092\u524a\u9664\u3057\u307e\u3059\u304b ?"},

        {"FILES_VERSION_UNINSTALLER","\u4ee5\u524d\u306b\u30a4\u30f3\u30b9\u30c8\u30fc\u30eb\u3057\u305f IDE \u304c\u30a4\u30f3\u30b9\u30c8\u30fc\u30eb\u5148\u30c7\u30a3\u30ec\u30af\u30c8\u30ea\u306b\n\u3042\u308a\u307e\u3059\u3002\u30a2\u30f3\u30a4\u30f3\u30b9\u30c8\u30fc\u30eb\u3059\u308b\u5834\u5408\u306b\u306f\u3001\u300c\u306f\u3044\u300d\u3092\u9078\u629e\u3057\u3066\n\u304f\u3060\u3055\u3044\u3002\u65b0\u3057\u304f\u30a4\u30f3\u30b9\u30c8\u30fc\u30eb\u3057\u305f IDE \u3067\u4ee5\u524d\u306e\u8a2d\u5b9a\u3092\u4f7f\u7528\n\u3067\u304d\u307e\u3059\u3002\u4ed6\u306e\u30c7\u30a3\u30ec\u30af\u30c8\u30ea\u3092\u4f7f\u7528\u3059\u308b\u5834\u5408\u306f\u300c\u3044\u3044\u3048\u300d\u3092\u9078\u629e\u3057\u3066\n\u304f\u3060\u3055\u3044\u3002"},
        {"FILES_VERSION_NO_UNINSTALLER","\u4ee5\u524d\u30a4\u30f3\u30b9\u30c8\u30fc\u30eb\u3057\u305f\u30d5\u30a1\u30a4\u30eb\u304c\u898b\u3064\u304b\u308a\u307e\u3057\u305f\u3002\u524d\u56de\u304b\u3089\u306e\n\u30a2\u30c3\u30d7\u30b0\u30ec\u30fc\u30c9\u304a\u3088\u3073\u3001\u4ee5\u524d\u306e\u8a2d\u5b9a\u3068\u30c7\u30fc\u30bf\u3092\u5229\u7528\u3067\u304d\u307e\u3059\u3002\n\u30a2\u30c3\u30d7\u30b0\u30ec\u30fc\u30c9\u3059\u308b\u5834\u5408\u306b\u306f\u3001\u300c\u306f\u3044\u300d\u3092\u9078\u629e\u3057\u3066\u304f\u3060\u3055\u3044\u3002\n\u4ed6\u306e\u30c7\u30a3\u30ec\u30af\u30c8\u30ea\u3092\u4f7f\u7528\u3059\u308b\u5834\u5408\u306b\u306f\u300c\u3044\u3044\u3048\u300d\u3092\u9078\u629e\u3057\u3066\u304f\u3060\u3055\u3044\u3002"},

//      {"EDITION_TO_INSTALL","Edition to install:"},
        {"EDITION_TO_INSTALL","\u30a4\u30f3\u30b9\u30c8\u30fc\u30eb\u3059\u308b Edition:"},

//      {"ENTERPRISE_EDITION","Enterprise Edition"},
        {"ENTERPRISE_EDITION","Enterprise Edition"},

//      {"COMMUNITY_EDITION","Community Edition"},
        {"COMMUNITY_EDITION","Community Edition"},

//      {"EDITION_INSTALLED","Installed edition:"},
        {"EDITION_INSTALLED","\u30a4\u30f3\u30b9\u30c8\u30fc\u30eb\u3057\u305f Edition:"},

//      {"EDITION_TO_UNINSTALL","Edition to uninstall:"},
        {"EDITION_TO_UNINSTALL","\u30a2\u30f3\u30a4\u30f3\u30b9\u30c8\u30fc\u30eb\u3059\u308b Edition:"},

//      {"EDITION_UNINSTALLED","Uninstalled edition:"},
        {"EDITION_UNINSTALLED","\u30a2\u30f3\u30a4\u30f3\u30b9\u30c8\u30fc\u30eb\u3057\u305f Edition:"},

//      {"JVM_FOUND_DESCRIPTION", "Installer found the following JDK(s) to use with Forte(tm) for Java(tm) 4.0. J2SE 1.3.1 & 1.4.0 are the officially supported JDKs. We strongly recommend that you specify it here."},
        {"JVM_FOUND_DESCRIPTION", "Forte(tm) for Java(tm) 4.0 \u3067\u4f7f\u7528\u3059\u308b JDK \u304c\u898b\u3064\u304b\u308a\u307e\u3057\u305f\u3002J2SE 1.3.1 \u304a\u3088\u3073 J2SE 1.4.0 \u306f Forte for Java 4.0 \u3067\u516c\u5f0f\u306b\u30b5\u30dd\u30fc\u30c8\u3055\u308c\u3066\u3044\u308b JDK \u3067\u3059\u3002\u3053\u3053\u3067\u6307\u5b9a\u3059\u308b\u3053\u3068\u3092\u63a8\u5968\u3057\u307e\u3059\u3002"},

//      {"JVM_NOT_FOUND_DESCRIPTION", "Installer could not find a suitable JDK to use with Forte for Java 4.0. J2SE 1.3.1 & 1.4.0 are the officially supported JDKs. We strongly recommend that you specify it here."},
        {"JVM_NOT_FOUND_DESCRIPTION", "Forte for Java 4.0 \u3067\u4f7f\u7528\u3059\u308b\u9069\u5207\u306a JDK \u304c\u898b\u3064\u304b\u308a\u307e\u305b\u3093\u3067\u3057\u305f\u3002J2SE 1.3.1 \u304a\u3088\u3073 J2SE 1.4.0 \u306f Forte for Java 4.0 \u3067\u516c\u5f0f\u306b\u30b5\u30dd\u30fc\u30c8\u3055\u308c\u3066\u3044\u308b JDK \u3067\u3059\u3002\u3053\u3053\u3067\u6307\u5b9a\u3059\u308b\u3053\u3068\u3092\u63a8\u5968\u3057\u307e\u3059\u3002"},

//      {"JVM_SPECIFY_LABEL", "JDK Home: "},
        {"JVM_SPECIFY_LABEL", "JDK \u30db\u30fc\u30e0: "},

//      {"INVALID_JDK_HOME_MSG", "Specified JDK Home is invalid"},
        {"INVALID_JDK_HOME_MSG", "\u7121\u52b9\u306a JDK \u30db\u30fc\u30e0\u304c\u6307\u5b9a\u3055\u308c\u307e\u3057\u305f"},

//      {"INVALID_JDK_HOME_TITLE", "Invalid JDK Home"},
        {"INVALID_JDK_HOME_TITLE", "\u7121\u52b9\u306a JDK \u30db\u30fc\u30e0"},

//      {"FCC_PATH_DESCRIPTION", "Some of the components that you have chosen to install require the Forte(tm) Compiler Collection (FCC) to function properly. Please provide the path to your Forte(tm) Compiler Collection (FCC) software. Include the full path to access the FCC software if it is in stalled on a shared server. Please leave the FCC path field blank if you do not readily know the FCC Path."},
        {"FCC_PATH_DESCRIPTION", "\u9078\u629e\u3057\u305f\u30b3\u30f3\u30dd\u30fc\u30cd\u30f3\u30c8\u3092\u30a4\u30f3\u30b9\u30c8\u30fc\u30eb\u3059\u308b\u306b\u306f\u3001Forte(tm) Compiler Collection (FCC) \u304c\u6b63\u3057\u304f\u6a5f\u80fd\u3059\u308b\u5fc5\u8981\u304c\u3042\u308a\u307e\u3059\u3002Forte(tm) Compiler Collection (FCC) \u30bd\u30d5\u30c8\u30a6\u30a7\u30a2\u306b\u30d1\u30b9\u3092\u6307\u5b9a\u3057\u3066\u304f\u3060\u3055\u3044\u3002FCC \u30bd\u30d5\u30c8\u30a6\u30a7\u30a2\u304c\u5171\u6709\u30b5\u30fc\u30d0\u30fc\u306b\u30a4\u30f3\u30b9\u30c8\u30fc\u30eb\u3055\u308c\u3066\u3044\u308b\u5834\u5408\u306f\u3001\u5b8c\u5168\u306a\u30d1\u30b9\u3092\u6307\u5b9a\u3057\u3066\u30a2\u30af\u30bb\u30b9\u3057\u3066\u304f\u3060\u3055\u3044\u3002FCC \u30d1\u30b9\u304c\u4e0d\u660e\u306a\u5834\u5408\u306f\u3001\u305d\u306e\u30d5\u30a3\u30fc\u30eb\u30c9\u3092\u7a7a\u767d\u306e\u307e\u307e\u306b\u3057\u3066\u304f\u3060\u3055\u3044\u3002"},

//      {"FCC_PATH_NOT_SPECIFIED_MSG", "Even though you have chosen to install Solaris Developer Modules, you have not provided the path to your Forte(tm) Compiler Collection (FCC) software. You can specify the FCC path to use with Solaris Developer tools later. See Getting Started Guide for details"},
        {"FCC_PATH_NOT_SPECIFIED_MSG", "Solaris Developer Module \u3092\u30a4\u30f3\u30b9\u30c8\u30fc\u30eb\u3059\u308b\u305f\u3081\u306b\u9078\u629e\u3057\u305f\u5834\u5408\u3067\u3082\u3001Forte(tm) Compiler Collection (FCC) \u30bd\u30d5\u30c8\u30a6\u30a7\u30a2\u3078\u306e\u30d1\u30b9\u306f\u63d0\u4f9b\u3055\u308c\u307e\u305b\u3093\u3002Solaris Developer tool \u3068\u4f7f\u7528\u3059\u308b FCC \u30d1\u30b9\u3092\u5f8c\u306b\u6307\u5b9a\u3067\u304d\u307e\u3059\u3002\u8a73\u7d30\u306f\u3001\u30a4\u30f3\u30b9\u30c8\u30fc\u30eb\u30ac\u30a4\u30c9\u3092\u53c2\u7167\u3057\u3066\u304f\u3060\u3055\u3044\u3002"},

//      {"FCC_SPECIFY_LABEL", "FCC Path: "},
        {"FCC_SPECIFY_LABEL", "FCC \u30d1\u30b9: "},

//      {"INVALID_FCC_PATH_MSG", "Specified FCC Path is invalid"},
        {"INVALID_FCC_PATH_MSG", "\u6307\u5b9a\u3057\u305f FCC \u30d1\u30b9\u306f\u7121\u52b9\u3067\u3059"},

//      {"INVALID_FCC_PATH_TITLE", "Invalid FCC Path"},
        {"INVALID_FCC_PATH_TITLE", "\u7121\u52b9\u306a FCC \u30d1\u30b9"},

//      {"JDK_LIST_LABEL","List of searched JDK(s) ( 1.3.1 & 1.4)"},
        {"JDK_LIST_LABEL","\u691c\u7d22\u3055\u308c\u305f JDK(s) ( 1.3.1 & 1.4) \u306e\u30ea\u30b9\u30c8"},

//      {"JDK_SEARCH_MSG"," Searching for Suitable JDK. Please wait .."},
        {"JDK_SEARCH_MSG"," JDK \u3092\u691c\u7d22\u3057\u3066\u3044\u307e\u3059\u3002 \u304a\u5f85\u3061\u304f\u3060\u3055\u3044 .."},

//      {"JDK_SEARCH_FAILED_MESSAGE","JDK Search failed to find suitable JDK!"},
        {"JDK_SEARCH_FAILED_MESSAGE","JDK \u306e\u691c\u7d22\u306b\u5931\u6557\u3057\u307e\u3057\u305f!"},

//      {"POST_INSTALL_TITLE","Post Install Process"},
        {"POST_INSTALL_TITLE","\u30a4\u30f3\u30b9\u30c8\u30fc\u30eb\u5f8c\u306e\u30d7\u30ed\u30bb\u30b9"},

//      {"POST_INSTALL_ACTION_DESC","Preparing IDE Startup Scripts"},
        {"POST_INSTALL_ACTION_DESC","IDE \u8d77\u52d5\u30b9\u30af\u30ea\u30d7\u30c8\u306e\u6e96\u5099\u4e2d"},

//      {"POST_INSTALL_POINTBASE_ACTION_DESC","Preparing pointbase Scripts"},
        {"POST_INSTALL_POINTBASE_ACTION_DESC","Pointbase \u30b9\u30af\u30ea\u30d7\u30c8\u306e\u6e96\u5099\u4e2d"},

//      {"POST_INSTALL_J2EE_ACTION_DESC","Preparing j2ee Scripts"},
        {"POST_INSTALL_J2EE_ACTION_DESC","j2ee \u30b9\u30af\u30ea\u30d7\u30c8\u306e\u6e96\u5099\u4e2d"},

//      {"CE_INSTALLER_TITLE","Forte(tm) for Java(tm), release 4.0, Community Edition Setup"},
        {"CE_INSTALLER_TITLE","Forte(tm) for Java(tm), release 4.0, Community Edition \u8a2d\u5b9a"},

//      {"EE_INSTALLER_TITLE","Forte(tm) for Java(tm), release 4.0, Enterprise Edition Setup"},
        {"EE_INSTALLER_TITLE","Forte(tm) for Java(tm), release 4.0, Enterprise Edition \u8a2d\u5b9a"},

//      {"ME_INSTALLER_TITLE","Forte(tm) for Java(tm), release 4.0, Mobile Edition Setup"},
        {"ME_INSTALLER_TITLE","Forte(tm) for Java(tm), release 4.0, Mobile Edition \u8a2d\u5b9a"},

//      {"CE_UNINSTALLER_TITLE","Forte(tm) for Java(tm), release 4.0, Community Edition uninstaller"},
        {"CE_UNINSTALLER_TITLE","Forte(tm) for Java(tm), release 4.0, Community Edition \u30a2\u30f3\u30a4\u30f3\u30b9\u30c8\u30fc\u30e9"},

//      {"EE_UNINSTALLER_TITLE","Forte(tm) for Java(tm), release 4.0, Enterprise Edition uninstaller"},
        {"EE_UNINSTALLER_TITLE","Forte(tm) for Java(tm), release 4.0, Enterprise Edition \u30a2\u30f3\u30a4\u30f3\u30b9\u30c8\u30fc\u30e9"},

//      {"ME_UNINSTALLER_TITLE","Forte(tm) for Java(tm), release 4.0, Mobile Edition uninstaller"},
        {"ME_UNINSTALLER_TITLE","Forte(tm) for Java(tm), release 4.0, Mobile Edition \u30a2\u30f3\u30a4\u30f3\u30b9\u30c8\u30fc\u30e9"},

//      {"BUTTON_BROWSE","Browse"}
        {"BUTTON_BROWSE","\u30d6\u30e9\u30a6\u30ba"}

    };
}




/* InstallerResources_ja.java	02.21	1.10 */
