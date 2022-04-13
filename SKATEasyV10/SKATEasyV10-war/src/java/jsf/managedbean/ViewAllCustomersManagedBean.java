/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.session.stateless.CustomerEntitySessionBeanLocal;
import entity.CustomerEntity;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author kel
 */
@Named(value = "viewAllCustomersManagedBean")
@RequestScoped
public class ViewAllCustomersManagedBean {

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
    
    public List<CustomerEntity> getCustomerEntities(){
        return customerEntities;
    }
    
    public void setCustomerEntities(List<CustomerEntity> customerEntities) {
        this.customerEntities = customerEntities;
    }
}
