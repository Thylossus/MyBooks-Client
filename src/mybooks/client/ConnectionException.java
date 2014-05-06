/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mybooks.client;

import java.util.HashMap;

/**
 * Exception wrapper for the WebServiceConnector class.
 * @author Tobias Kahse <tobias.kahse@outlook.com>
 */
public class ConnectionException extends Exception{

    /**
     * The connection's command.
     */
    private String command;
    /**
     * The used parameters.
     */
    private HashMap<String, String> params;

    /**
     * Construct ConnectionException.
     */
    public ConnectionException() {
    }

    /**
     * Construct ConnectionException.
     * @param message exception message.
     */
    public ConnectionException(String message) {
        super(message);
    }

    /**
     * Construct ConnectionException.
     * @param message exception message.
     * @param command exception command.
     */
    public ConnectionException(String message, String command) {
        super(message);
        this.command = command;
    }
    
    /**
     * Construct ConnectionException.
     * @param message exception message.
     * @param command exception command.
     * @param params exception parameters.
     */
    public ConnectionException(String message, String command, HashMap<String,String> params) {
        super(message);
        this.command = command;
        this.params = params;
    }
    
    /**
     * Construct ConnectionException.
     * @param message exception message.
     * @param command exception command.
     * @param params exception parameters.
     * @param cause exception's cause.
     */
    public ConnectionException(String message, String command, HashMap<String,String> params, Throwable cause) {
        super(message, cause);
        this.command = command;
        this.params = params;
    }

    /**
     * Construct ConnectionException.
     * @param message exception message.
     * @param cause exception's cause.
     */
    public ConnectionException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Construct ConnectionException.
     * @param message exception message.
     * @param command exception command.
     * @param cause exception's cause.
     */
    public ConnectionException(String command, String message, Throwable cause) {
        super(message, cause);
        this.command = command;
    }

    /**
     * Construct ConnectionException.
     * @param message exception message.
     * @param params exception parameters.
     * @param cause exception's cause.
     */
    public ConnectionException(HashMap<String, String> params, String message, Throwable cause) {
        super(message, cause);
        this.params = params;
    }

    /**
     * Get the exception command.
     * @return the exeption command.
     */
    public String getCommand() {
        return command;
    }

    /**
     * Get the exception params.
     * @return the exception params.
     */
    public HashMap<String, String> getParams() {
        return params;
    }
    
}
