/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import javax.inject.Named;
import javax.faces.view.ViewScoped;
import java.io.Serializable;

/**
 *
 * @author harmo
 */
@Named(value = "viewAllSaleTransactionsManagedBean")
@ViewScoped
public class ViewAllSaleTransactionsManagedBean implements Serializable {
    
    

    /**
     * Creates a new instance of ViewAllSaleTransactionsManagedBean
     */
    public ViewAllSaleTransactionsManagedBean() {
    }
    
}
