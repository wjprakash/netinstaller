// RunCommand.java
// Last Modified on Feb 22, 2002 - Winston Prakash

package com.nayaware.netinstaller;
 
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.File;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * RunCommand execute the command text and returns the
 * status of execution and output and error messages
 * associated with the execution
 */
public class RunCommand {
    private BufferedReader      processOutput;
    private BufferedReader      processError;
    private PrintStream	        processInput;
    private Runtime		runTime;
    protected boolean           interrupted;
    protected Process           thisProcess;
    protected String            lineSeparator;
    protected String            cmdString;
    
    public RunCommand() {
        processOutput = null;
        processError = null;
        thisProcess = null;
        runTime = Runtime.getRuntime();
        interrupted = false;
        lineSeparator = System.getProperty("line.separator", "\n");
    }
    
    /**
     * Execute the command line in a separate thread
     * The command parameter is a fully qualified path
     * of the command
     */
    public void execute(String command){
        cmdString = command;
        interrupted = false;
        try {
            thisProcess = runTime.exec(command);
            InputStream os = thisProcess.getInputStream();
            InputStream os_err = thisProcess.getErrorStream();
            OutputStream is = thisProcess.getOutputStream();
            processOutput = new BufferedReader(new InputStreamReader(os));
            processError = new BufferedReader(new InputStreamReader(os_err));
            processInput = new PrintStream(is, true);
        } catch (Exception ex) {
            System.out.println(ex.getLocalizedMessage());
            ex.printStackTrace();            
        }
    }
    
    /**
     * Interrupt the command line process
     */
    public void interrupt() {
        interrupted = true;
        thisProcess.destroy();
        thisProcess = null;
    }
    
    /**
     * Gets the return status of the command line process
     */
    public int getReturnStatus() {
        if(interrupted) {
            return(-1);
        }
        try {
            thisProcess.waitFor();
        } catch (Exception ex) {
            System.out.println(ex.getLocalizedMessage());
            ex.printStackTrace();            
            return(-2);
        }
        return(thisProcess.exitValue());
    }
    
    /**
     * Checks whether the command line process is still running
     */
    public boolean isRunning() {
        try {
            int ev = thisProcess.exitValue();
        } catch (IllegalThreadStateException ie) {
            return(true);
        } catch (Exception ie) {
            return(false);
        }
        return(false);
    }
    
    /**
     * Gets a line from the standard output of the command line process
     */
    public String getOutputLine(){
        String ret = null;
        try{
            ret = processOutput.readLine();
            if(ret != null) ret = ret + "\n";
        }catch(IOException ex) {
            System.out.println(ex.getLocalizedMessage());
            ex.printStackTrace();
        }
        return ret;
    }
    
    
    /**
     * Gets a line from the standard error of the command line process
     */
    public String getErrorLine(){
        
        String ret = null;
        try{
            ret = processError.readLine();
            if(ret != null) ret = ret + "\n";
        }catch(IOException ex) {
            System.out.println(ex.getLocalizedMessage());
            ex.printStackTrace();            
        }
        return ret;
    }
    
    
    /**
     * Prints debug info about the command line process like
     * output,error & return status
     */
    public void print() {
        String line = null;
        System.out.println("------------------------------  TwCommand Print  -----------------------");
        System.out.println(" command: ");
        System.out.println("    " + cmdString);
        System.out.println(" Command Output:");
        while((line = getOutputLine()) != null) {
            System.out.println("    " + line);
        }
        System.out.println(" Command Error:");
        while((line = getErrorLine()) != null) {
            System.out.println("    " + line);
        }
        System.out.println(" Retur Status: " + getReturnStatus());
        System.out.println("------------------------------------------------------------------------");
    }
}
