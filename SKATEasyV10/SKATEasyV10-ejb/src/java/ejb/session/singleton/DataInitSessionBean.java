/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.singleton;

import ejb.session.stateless.ArtistEntitySessionBeanLocal;
import ejb.session.stateless.CategoryEntitySessionBeanLocal;
import ejb.session.stateless.CustomerEntitySessionBeanLocal;
import ejb.session.stateless.CustomisationRequestSessionBeanLocal;
//import ejb.session.stateless.CustomerEntitySessionBeanLocal;
import ejb.session.stateless.ProductEntitySessionBeanLocal;
import ejb.session.stateless.StaffEntitySessionBeanLocal;
import ejb.session.stateless.TagEntitySessionBeanLocal;
import entity.ArtistEntity;
import entity.CategoryEntity;
import entity.CustomerEntity;
import entity.CustomisationRequest;
//import entity.CustomerEntity;
import entity.ProductEntity;
import entity.StaffEntity;
import entity.TagEntity;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
import util.enumeration.StatusEnum;
import util.exception.ArtistUsernameExistException;
import util.exception.CategoryNotFoundException;
import util.exception.CreateNewCategoryException;
import util.exception.CreateNewCustomisationRequestException;
import util.exception.CreateNewProductException;
import util.exception.CreateNewTagException;
import util.exception.CustomerUsernameExistException;
import util.exception.InputDataValidationException;
import util.exception.ProductSkuCodeExistException;
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

    @EJB
    private CustomisationRequestSessionBeanLocal customisationRequestSessionBeanLocal;

    @EJB
    private TagEntitySessionBeanLocal tagEntitySessionBeanLocal;

    @EJB
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
            StaffEntity admin = staffEntitySessionBeanLocal.createNewStaff(new StaffEntity("Admin", "One", AccessRightEnum.ADMINISTRATOR, "admin1", "password"));
            StaffEntity staff = staffEntitySessionBeanLocal.createNewStaff(new StaffEntity("Staff", "One", AccessRightEnum.STAFF, "staff1", "password"));
            
            CategoryEntity catA = categoryEntitySessionBeanLocal.createNewCategoryEntity(new CategoryEntity("Category A", "Category A"), null);
            CategoryEntity catB = categoryEntitySessionBeanLocal.createNewCategoryEntity(new CategoryEntity("Category B", "Category B"), null);

            TagEntity tagA = tagEntitySessionBeanLocal.createNewTagEntity(new TagEntity("Tag A"));
            TagEntity tagB = tagEntitySessionBeanLocal.createNewTagEntity(new TagEntity("Tag B"));  
            
            List<Long> tagIds = new ArrayList<>();
            tagIds.add(tagA.getTagId());
            tagIds.add(tagB.getTagId());
            System.out.println(tagIds);
            
            productEntitySessionBeanLocal.createNewProduct(new ProductEntity("0000001","Product A", "Product A", 10, 10, new BigDecimal(10),new BigDecimal(10), 5), catA.getCategoryId(),null);
            productEntitySessionBeanLocal.createNewProduct(new ProductEntity("0000002","Product B", "Product B", 10, 10, new BigDecimal(10),new BigDecimal(10), 5), catA.getCategoryId(),null);
            productEntitySessionBeanLocal.createNewProduct(new ProductEntity("0000003","Product C", "Product C", 10, 10, new BigDecimal(10),new BigDecimal(10), 5), catA.getCategoryId(),null);
            productEntitySessionBeanLocal.createNewProduct(new ProductEntity("0000004","Product D", "Product D", 10, 10, new BigDecimal(10),new BigDecimal(10), 5), catA.getCategoryId(),null);
            productEntitySessionBeanLocal.createNewProduct(new ProductEntity("0000005","Product E", "Product E", 10, 10, new BigDecimal(10),new BigDecimal(10), 5), catA.getCategoryId(),null);
            
            ArtistEntity artist1 = artistEntitySessionBeanLocal.createNewArtist(new ArtistEntity("Artist", "1",AccessRightEnum.ARTIST, "artist1", "password"));

            CustomerEntity customer1 = customerEntitySessionBeanLocal.createNewCustomer(new CustomerEntity("Customer 1", "customer1@email.com", "customer1", "password"));

            customisationRequestSessionBeanLocal.createCustomisationRequest(new CustomisationRequest("First Request", new Date(), StatusEnum.PENDING), artist1.getStaffId(), customer1.getCustomerId());

        } catch (CreateNewCategoryException | InputDataValidationException | ArtistUsernameExistException | CustomerUsernameExistException | StaffUsernameExistException | UnknownPersistenceException | CreateNewTagException | CreateNewProductException
                | ProductSkuCodeExistException | CreateNewCustomisationRequestException ex) {
            ex.printStackTrace();
        }


    } 
}
