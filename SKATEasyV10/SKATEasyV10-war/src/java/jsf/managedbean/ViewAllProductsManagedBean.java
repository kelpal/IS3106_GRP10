package jsf.managedbean;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import ejb.session.stateless.ProductEntitySessionBeanLocal;
import entity.ProductEntity;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author kel
 */
@Named(value = "viewAllProductsManagedBean")
@RequestScoped
public class ViewAllProductsManagedBean {

    @EJB
    private ProductEntitySessionBeanLocal productEntitySessionBeanLocal;

    private List<ProductEntity> productEntities;
    
    /**
     * Creates a new instance of ViewAllProductsManagedBean
     */
    public ViewAllProductsManagedBean() {
        
    }
    
    @PostConstruct
    public void postConstruct()
    {
        productEntities = productEntitySessionBeanLocal.retrieveAllProducts();
    }

    /**
     * @return the productEntities
     */
    public List<ProductEntity> getProductEntities() {
        return productEntities;
    }

    /**
     * @param productEntities the productEntities to set
     */
    public void setProductEntities(List<ProductEntity> productEntities) {
        this.productEntities = productEntities;
    }
    
}
