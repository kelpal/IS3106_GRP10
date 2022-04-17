/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.exception;

/**
 *
 * @author harmo
 */
public class CustomerNameExistException extends Exception {

    public CustomerNameExistException() {
    }

    
    public CustomerNameExistException(String msg) {
        super(msg);
    }
    
    
    
}
