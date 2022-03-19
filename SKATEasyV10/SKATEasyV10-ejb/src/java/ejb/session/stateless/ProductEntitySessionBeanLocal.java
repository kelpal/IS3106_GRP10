/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.ProductEntity;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author kel
 */
@Local
public interface ProductEntitySessionBeanLocal {

    public List<ProductEntity> retrieveAllProducts();

    public Long createNewProduct(ProductEntity newProductEntity);
    
}
