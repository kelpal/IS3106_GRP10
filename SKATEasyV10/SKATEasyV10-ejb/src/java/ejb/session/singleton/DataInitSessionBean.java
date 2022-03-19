/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.singleton;

import ejb.session.stateless.ProductEntitySessionBeanLocal;
import entity.ProductEntity;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author kel
 */
@Singleton
@LocalBean
@Startup

public class DataInitSessionBean {

    @EJB(name = "ProductEntitySessionBeanLocal")
    private ProductEntitySessionBeanLocal productEntitySessionBeanLocal;

    @PersistenceContext(unitName = "SKATEasyV10-ejbPU")
    private EntityManager em;

       
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
    @PostConstruct
    public void postConstruct()
    {
        if(em.find(ProductEntity.class, 1l) == null)
        {
            //load test data
            productEntitySessionBeanLocal.createNewProduct(new ProductEntity("Product A"));
            productEntitySessionBeanLocal.createNewProduct(new ProductEntity("Product B"));
            productEntitySessionBeanLocal.createNewProduct(new ProductEntity("Product C"));
            productEntitySessionBeanLocal.createNewProduct(new ProductEntity("Product D"));
            productEntitySessionBeanLocal.createNewProduct(new ProductEntity("Product E"));
            
        }
    }
}
