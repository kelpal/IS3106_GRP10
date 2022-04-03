/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.session.stateless.ProductEntitySessionBeanLocal;
import entity.ProductEntity;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

/**
 *
 * @author kel
 */
@Named(value = "createNewProductManagedBean")
@RequestScoped
public class CreateNewProductManagedBean {

    @EJB
    private ProductEntitySessionBeanLocal productEntitySessionBeanLocal;

    private ProductEntity newProductEntity;
    
    /**
     * Creates a new instance of CreateNewProductManagedBean
     */
    public CreateNewProductManagedBean() {
        newProductEntity = new ProductEntity();
    }

    //action listener method
    public void doCreateNewProduct(ActionEvent event)
    {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "New product created: ", null));
    }
    
    
    /**
     * @return the newProductEntity
     */
    public ProductEntity getNewProductEntity() {
        return newProductEntity;
    }

    /**
     * @param newProductEntity the newProductEntity to set
     */
    public void setNewProductEntity(ProductEntity newProductEntity) {
        this.newProductEntity = newProductEntity;
    }
    
}
