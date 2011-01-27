package com.nayaware.netinstaller;

public class SampleCodes {
    
}

//-----------------------------------------------------------------------------------------------------
// Getting info on OS

/*public void getOsInfo(){
    try {
        WizardServices services = getProductBean().getServices();
        SystemUtilService service = (SystemUtilService)services.getService(SystemUtilService.NAME);
        Properties p = service.getOSProperties();
 
        System.out.println("OS Name"+p.getProperty("os.name"));
        System.out.println("OS Version"+p.getProperty("os.version"));
        System.out.println("OS Arch"+p.getProperty("os.arch"));
    } catch(Exception ex){
        ex.printStackTrace();
    }
 
}*/

//-----------------------------------------------------------------------------------------------------


//Getting Registry from Win32


/*public void install( ProductActionSupport pas ) {
    try {
        Win32RegistryService regserv = (Win32RegistryService)getService(Win32RegistryService.NAME);
        String regValue = regserv.getStringValue(Win32RegistryService.HKEY_LOCAL_MACHINE, "Software\\MyKey", "My_value", false);
        System.out.println("The registry value is: "+regValue);
    }
    catch( ServiceException se ){
        System.out.println( se.getMessage() );
    }
}
 
public void build( ProductBuilderSupport sup) {
    sup.putRequiredService( Win32RegistryService.NAME );
}*/


//-----------------------------------------------------------------------------------------------------

//Adding Resources during build time and accessing at install time


    /*try{
        support.putResource("Resource", "ResourceName");
        //imports the resource with the filename "Resource" into the archive.
        //"Resource name" indicates the name that will be give to the resource when placed in the archive
    }
    catch(IOException ioProb){
        ioProb.printStackTrace();
        //This indicates that there was a problem with brining the file into the archive.
    }
    //The following code should be performed within a method that occurs a runtime
    //(i.e. execute(), install(), uninstall(), initialize(), etc.)
     
    URL url=getResource("ResourceName");
     
    //Next, use CreateTempFile() of the FileUtils class to create a temporary file
    //from the specified source.
     
    String tempfile=FileUtils.createTempFile(url, "ResourceName");
     
    //The file "ResourceName" is now available for use. This file can be
    //accessed using the String value "tempfile", which will contain the path
    //to "ResourceName". For instance, if "ResourceName" was a properties file
    //from which you wished to read a property value, you would then use the
    //following code to read the property value.
     
    FileInputStream in = new FileInputStream(tempfile);
    Properties props = new Properties();
    props.load(in);
    String propertyValue = props.getProperty("PropertyName");
    in.close();*/

//-----------------------------------------------------------------------------------------------------

// How to use the JVM Service

/*try {
    // Get a Reference the JVMService.
    JVMService jvmService = (JVMService)getService(JVMService.NAME);
 
    // pass the JVM ID to the getJVMHome method to get the path to the location of the
    //virtual machine into the String jvmHome.
    String jvmHome = jvmService.getJVMHome(JVMID);
 
    // Check if jvmHome is null. If it is, then either the JVM resolution hasn’t happened yet or
    // the JVM Resolution failed.
    if (jvmHome == null)
        // No JVM Was Found: Take appropriate action
    else
        // A JVM was found and the String jvmHome contains the JVM home path
} catch (ServiceException se) {
    // If an exception came up with the JVMService, log the event.
    logEvent(this,Log.ERROR, se);
}*/

//-----------------------------------------------------------------------------------------------------

// To find if the end user has administrator privileges programmatically during the installation.

/*try {
    SecurityService secService = (SecurityService)getProductBean( ).getServices( ).getService(SecurityService.NAME);
    isAdmin = secService.isCurrentUserAdmin( );
}
catch(ServiceException e) {   }

public void build(ProductBuilderSupport support) { // ensure ISMP builder includes security-service classes in setup.jar
    support.putRequiredService(SecurityService.NAME);
}*/

//-----------------------------------------------------------------------------------------------------

// To get info on installed Features and components

        /*try {
            String productURL = ProductService.DEFAULT_PRODUCT_SOURCE;
            ProductService productService = (ProductService)getService(ProductService.NAME);
            ProductTree productTree = productService.getSoftwareObjectTree(productURL);
            StandardProductTreeIterator iterator = new StandardProductTreeIterator(productTree);
            for(ProductBean productbean = iterator.getNext(iterator.begin()); productbean != iterator.end(); productbean = iterator.getNext(productbean)) {
                if(productbean.getDisplayName().equals("pointbase")){
                    if(productbean.isActive())
                        System.out.println("Poitbase Installed");
                    else
                        System.out.println("Poitbase Not Installed");
                }
                if(productbean.getDisplayName().equals("j2ee")){
                    if(productbean.isActive())
                        System.out.println("J2EE Installed");
                    else
                        System.out.println("J2EE Not Installed");
                }
            }
            
        } catch (ServiceException e) {
        }
    }*/

//-----------------------------------------------------------------------------------------------------
