/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.session.stateless.SaleTransactionEntitySessionBeanLocal;
import entity.CustomerEntity;
import entity.SaleTransactionEntity;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import util.exception.CustomerNotFoundException;
import util.exception.SaleTransactionAlreadyVoidedRefundedException;
import util.exception.SaleTransactionNotFoundException;

/**
 *
 * @author harmo
 */
@Named(value = "viewAllSaleTransactionsManagedBean")
@ViewScoped
public class ViewAllSaleTransactionsManagedBean implements Serializable {

    @EJB
    private SaleTransactionEntitySessionBeanLocal saleTransactionEntitySessionBeanLocal;
    
    private List<SaleTransactionEntity> saleTransactions;

    /**
     * Creates a new instance of ViewAllSaleTransactionsManagedBean
     */
    public ViewAllSaleTransactionsManagedBean() {
    }
    
    @PostConstruct
    public void postConstruct()
    {
        getSaleTransactions();
    }
    
    public List<SaleTransactionEntity> getSaleTransactions()
    {
        this.saleTransactions = saleTransactionEntitySessionBeanLocal.retrieveAllSaleTransactions();
        return this.saleTransactions;

    }
    
    public void doRefund(ActionEvent event)
    {
        try 
        {
            SaleTransactionEntity toRefund = (SaleTransactionEntity)event.getComponent().getAttributes().get("saleTransactionToRefund");
            saleTransactionEntitySessionBeanLocal.voidRefundSaleTransaction(toRefund.getSaleTransactionId());
            
            getSaleTransactions();
            
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Refund Successful!", null));
        } catch(SaleTransactionAlreadyVoidedRefundedException | SaleTransactionNotFoundException  ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Refund Failed! " + ex.getMessage(), null));
        }
    }

    /**
     * @param saleTransactions the saleTransactions to set
     */
    public void setSaleTransactions(List<SaleTransactionEntity> saleTransactions) {
        this.saleTransactions = saleTransactions;
    }
    
}
