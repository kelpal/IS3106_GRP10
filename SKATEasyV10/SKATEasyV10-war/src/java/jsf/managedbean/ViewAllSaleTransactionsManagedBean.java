/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.session.stateless.SaleTransactionEntitySessionBeanLocal;
import entity.CustomerEntity;
import entity.SaleTransactionEntity;
import entity.SaleTransactionLineItemEntity;
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
    
    private SaleTransactionEntity saleTransactionToView;
    
    private List<SaleTransactionLineItemEntity> stlis;

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
    
    public void viewSaleTransaction(ActionEvent event)
    {
        this.saleTransactionToView = (SaleTransactionEntity)event.getComponent().getAttributes().get("saleTransactionToView");
        System.out.println(saleTransactionToView.getSaleTransactionLineItemEntities());
        this.stlis = saleTransactionToView.getSaleTransactionLineItemEntities();
    }
    
    public void doRefund(ActionEvent event)
    {
        try 
        {
            SaleTransactionEntity toRefund = (SaleTransactionEntity)event.getComponent().getAttributes().get("saleTransactionToRefund");
            saleTransactionEntitySessionBeanLocal.voidRefundSaleTransaction(toRefund.getSaleTransactionId());
            
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

    /**
     * @return the saleTransactionToView
     */
    public SaleTransactionEntity getSaleTransactionToView() {
        return saleTransactionToView;
    }

    /**
     * @param saleTransactionToView the saleTransactionToView to set
     */
    public void setSaleTransactionToView(SaleTransactionEntity saleTransactionToView) {
        this.saleTransactionToView = saleTransactionToView;
    }

    /**
     * @return the stlis
     */
    public List<SaleTransactionLineItemEntity> getStlis() {
        return stlis;
    }

    /**
     * @param stlis the stlis to set
     */
    public void setStlis(List<SaleTransactionLineItemEntity> stlis) {
        this.stlis = stlis;
    }
    
}
