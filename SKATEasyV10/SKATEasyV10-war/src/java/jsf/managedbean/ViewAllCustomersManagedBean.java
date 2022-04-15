/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.session.stateless.CustomerEntitySessionBeanLocal;
import entity.CustomerEntity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;

/**
 *
 * @author kel
 */
@Named(value = "viewAllCustomersManagedBean")
@ViewScoped
public class ViewAllCustomersManagedBean implements Serializable{

    @EJB(name = "CustomerEntitySessionBeanLocal")
    private CustomerEntitySessionBeanLocal customerEntitySessionBeanLocal;
    
    private List<CustomerEntity> customerEntities;
    /**
     * Creates a new instance of ViewAllCustomersManagedBean
     */
    public ViewAllCustomersManagedBean() {
        this.customerEntities = new ArrayList<>();
    }
    
    @PostConstruct
    public void postConstruct()
    {
        customerEntities = customerEntitySessionBeanLocal.retrieveAllCustomers();
    }
    
    public void sendRecoveryEmail(ActionEvent event)
    {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, " Recovery Email Sent!", null));
    }
    
    public void disableCustomer(ActionEvent event)
    {
        CustomerEntity customerToDisable = (CustomerEntity)event.getComponent().getAttributes().get("customerToDisable");
        
        customerToDisable.setIsDisabled(Boolean.TRUE);
        
        customerEntitySessionBeanLocal.updateCustomer(customerToDisable);
        
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Customer successfully disabled! ID: " + customerToDisable.getCustomerId(), null));

    }
    
    public void enableCustomer(ActionEvent event)
    {
        CustomerEntity customerToEnable = (CustomerEntity)event.getComponent().getAttributes().get("customerToEnable");
        
        customerToEnable.setIsDisabled(Boolean.FALSE);
        
        customerEntitySessionBeanLocal.updateCustomer(customerToEnable);
        
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Customer successfully enabled! ID: " + customerToEnable.getCustomerId(), null));

    }
    
    public List<CustomerEntity> getCustomerEntities(){
        return customerEntities;
    }
    
    public void setCustomerEntities(List<CustomerEntity> customerEntities) {
        this.customerEntities = customerEntities;
    }
}
