/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.ProductEntity;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author kel
 */
@Stateless
public class ProductEntitySessionBean implements ProductEntitySessionBeanLocal {

    @PersistenceContext(unitName = "SKATEasyV10-ejbPU")
    private EntityManager em;

    //delete persistence bit
    
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
    @Override
    public List<ProductEntity> retrieveAllProducts() {
        Query query = em.createQuery("SELECT r FROM ProductEntity r");
        return query.getResultList();
    }
    
    @Override
    public Long createNewProduct(ProductEntity newProductEntity) {
        em.persist(newProductEntity);
        em.flush(); //needed because we are using IDENTITY to generate new ID so need to flush to save new record in DB
        
        return newProductEntity.getProductId();
    }
    
    
    
    
}
