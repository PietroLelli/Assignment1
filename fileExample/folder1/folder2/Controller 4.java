package it.unibo.oop.lab.mvcio;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * 
 */
public class Controller{
    

    private File currentFile = null;
    private String path = System.getProperty("user.home")
            + System.getProperty("file.separator")
            + "output.txt" ;
    
    
    
    
    public Controller() {
        current();
        
    }
    
    
    public void current() {
        String nameFile = path;
        try {
            File file = new File(nameFile);
            if (file.exists()) {
                System.out.println("Il file " + nameFile + " esiste");
                currentFile = file; 
            }
                else if (file.createNewFile()) { 
                    System.out.println("Il file " + nameFile + " è stato creato");
                    currentFile = file; 
                }               
                //System.out.println("Il file " + nameFile + " non può essere creato");
                
            } catch (IOException e) {
                e.printStackTrace();
                
        }
        
    }
    public String getPathFile() {
        return this.path;
    }
    
    public File getFile() {
        return currentFile;
    }
    
    public void setPath(String toPath) {
        this.path = toPath;
    }
    
    public void insertString(String s) throws IOException {
        current();
        try {
            FileWriter fw = new FileWriter(getFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(s);
            bw.flush();
            bw.close();
        } catch(IOException e) {
            System.out.println(e.getMessage());
        }
    }
    /*
     * This class must implement a simple controller responsible of I/O access. It
     * considers a single file at a time, and it is able to serialize objects in it.
     * 
     * Implement this class with:
     * 
     * 1) A method for setting a File as current file
     * 
     * 2) A method for getting the current File
     * 
     * 3) A method for getting the path (in form of String) of the current File
     * 
     * 4) A method that gets a String as input and saves its content on the current
     * file. This method may throw an IOException.
     * 
     * 5) By default, the current file is "output.txt" inside the user home folder.
     * A String representing the local user home folder can be accessed using
     * System.getProperty("user.home"). The separator symbol (/ on *nix, \ on
     * Windows) can be obtained as String through the method
     * System.getProperty("file.separator"). The combined use of those methods leads
     * to a software that runs correctly on every platform.
     */

}
