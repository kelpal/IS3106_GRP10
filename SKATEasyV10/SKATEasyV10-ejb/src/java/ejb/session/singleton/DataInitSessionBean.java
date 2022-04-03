/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.singleton;

import ejb.session.stateless.ArtistEntitySessionBeanLocal;
import ejb.session.stateless.CategoryEntitySessionBeanLocal;
import ejb.session.stateless.CustomerEntitySessionBeanLocal;
//import ejb.session.stateless.CustomerEntitySessionBeanLocal;
import ejb.session.stateless.ProductEntitySessionBeanLocal;
import ejb.session.stateless.StaffEntitySessionBeanLocal;
import entity.ArtistEntity;
import entity.CategoryEntity;
import entity.CustomerEntity;
//import entity.CustomerEntity;
import entity.ProductEntity;
import entity.StaffEntity;
import java.math.BigDecimal;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import util.enumeration.AccessRightEnum;
import util.exception.ArtistUsernameExistException;
import util.exception.CategoryNotFoundException;
import util.exception.CreateNewCategoryException;
import util.exception.CustomerUsernameExistException;
import util.exception.InputDataValidationException;
import util.exception.StaffNotFoundException;
import util.exception.StaffUsernameExistException;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author kel
 */
@Singleton
@LocalBean
@Startup

public class DataInitSessionBean {

    @EJB(name = "StaffEntitySessionBeanLocal")
    private StaffEntitySessionBeanLocal staffEntitySessionBeanLocal;
    
    

    @EJB
    private ArtistEntitySessionBeanLocal artistEntitySessionBeanLocal;

    @EJB
    private CustomerEntitySessionBeanLocal customerEntitySessionBeanLocal;
    
    @EJB
    private CategoryEntitySessionBeanLocal categoryEntitySessionBeanLocal;

    @EJB
    private ProductEntitySessionBeanLocal productEntitySessionBeanLocal;
           

    @PersistenceContext(unitName = "SKATEasyV10-ejbPU")
    private EntityManager em;

       
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
    @PostConstruct
    public void postConstruct()
    {
        try
        {
            staffEntitySessionBeanLocal.retrieveStaffByUsername("admin1");;
        } catch (StaffNotFoundException ex)
        {
            initializeData();
        }
    }
    
    
    public void initializeData()
    {
        try
        {
            staffEntitySessionBeanLocal.createNewStaff(new StaffEntity("Admin", "One", AccessRightEnum.ADMINISTRATOR, "admin1", "password"));
            
            Long catAId = categoryEntitySessionBeanLocal.createNewCategoryEntity(new CategoryEntity("Category A", "Category A"), null);


            //productEntitySessionBeanLocal.createNewProduct(new ProductEntity("Product A", "Product A", 10, 10, new BigDecimal(10),new BigDecimal(10), 5, categoryEntitySessionBeanLocal.retrieveCategoryByCategoryId(catAId)));
            //productEntitySessionBeanLocal.createNewProduct(new ProductEntity("Product B", "Product B", 10, 10, new BigDecimal(10),new BigDecimal(10), 5, categoryEntitySessionBeanLocal.retrieveCategoryByCategoryId(catAId)));
            //productEntitySessionBeanLocal.createNewProduct(new ProductEntity("Product C", "Product C", 10, 10, new BigDecimal(10),new BigDecimal(10), 5, categoryEntitySessionBeanLocal.retrieveCategoryByCategoryId(catAId)));
            //productEntitySessionBeanLocal.createNewProduct(new ProductEntity("Product D", "Product D", 10, 10, new BigDecimal(10),new BigDecimal(10), 5, categoryEntitySessionBeanLocal.retrieveCategoryByCategoryId(catAId)));
            //productEntitySessionBeanLocal.createNewProduct(new ProductEntity("Product E", "Product E", 10, 10, new BigDecimal(10),new BigDecimal(10), 5, categoryEntitySessionBeanLocal.retrieveCategoryByCategoryId(catAId)));

            artistEntitySessionBeanLocal.createNewArtist(new ArtistEntity("Artist 1", "artist1@email.com", "artist1", "password"));
            customerEntitySessionBeanLocal.createNewCustomer(new CustomerEntity("Customer 1", "customer1@email.com", "customer1", "password"));

        } catch (CreateNewCategoryException | InputDataValidationException | ArtistUsernameExistException | CustomerUsernameExistException | StaffUsernameExistException
                | UnknownPersistenceException  ex) {
            ex.printStackTrace();
        }


    } 
}
