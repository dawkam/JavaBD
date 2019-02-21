package pl.polsl.lab.service;
/**
 * Exception class for object thrown when attempting to create palindrome with given length
 *
 *
 * @author Dawid Kampa
 * @version 2.1
 */
 
public class LengthException extends Exception{
    
    /**
     * Non-parameter constructor
     */
    public LengthException(){
    }
        /**
     * Constructor
     *
     * @param message display message
     */
    public LengthException(String message){
        super(message);
    }
}