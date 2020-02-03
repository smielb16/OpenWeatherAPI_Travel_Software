/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package api_call;

/**
 *
 * @author elisc
 */
public class DestinationNotFoundException extends Exception {

    public DestinationNotFoundException(String errorMessage) {
        super(errorMessage);
    }
    
}
