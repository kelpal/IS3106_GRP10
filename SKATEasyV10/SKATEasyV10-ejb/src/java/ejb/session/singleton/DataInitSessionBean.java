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
import ejb.session.stateless.SaleTransactionEntitySessionBeanLocal;
import ejb.session.stateless.StaffEntitySessionBeanLocal;
import ejb.session.stateless.TagEntitySessionBeanLocal;
import entity.ArtistEntity;
import entity.CategoryEntity;
import entity.CustomerEntity;
import entity.CustomisationRequest;
//import entity.CustomerEntity;
import entity.ProductEntity;
import entity.SaleTransactionEntity;
import entity.SaleTransactionLineItemEntity;
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
import util.exception.CreateNewSaleTransactionException;
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
    private SaleTransactionEntitySessionBeanLocal saleTransactionEntitySessionBeanLocal;

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
            //Admin and Staff
            StaffEntity admin1 = staffEntitySessionBeanLocal.createNewStaff(new StaffEntity("Admin", "One", AccessRightEnum.ADMINISTRATOR, "admin1", "password"));
            StaffEntity staff1 = staffEntitySessionBeanLocal.createNewStaff(new StaffEntity("Staff", "One", AccessRightEnum.STAFF, "staff1", "password"));
            StaffEntity staff2 = staffEntitySessionBeanLocal.createNewStaff(new StaffEntity("Staff", "Two", AccessRightEnum.STAFF, "staff2", "password"));
            StaffEntity staff3 = staffEntitySessionBeanLocal.createNewStaff(new StaffEntity("Staff", "Three", AccessRightEnum.STAFF, "staff3", "password"));
            
            //Customers
            CustomerEntity customer1 = customerEntitySessionBeanLocal.createNewCustomer(new CustomerEntity("Customer 1", "customer1@email.com", "customer1", "password"));
            
            //Artists
            ArtistEntity artist1 = artistEntitySessionBeanLocal.createNewArtist(new ArtistEntity("Leonardo", "da Vinci", AccessRightEnum.ARTIST, "artist1", "password"));
            ArtistEntity artist2 = artistEntitySessionBeanLocal.createNewArtist(new ArtistEntity("Claude", "Monet", AccessRightEnum.ARTIST, "artist2", "password"));
            ArtistEntity artist3 = artistEntitySessionBeanLocal.createNewArtist(new ArtistEntity("Frida", "Kahlo", AccessRightEnum.ARTIST, "artist3", "password"));

            //Customisation Requests
            customisationRequestSessionBeanLocal.createCustomisationRequest(new CustomisationRequest("First Request", new Date(), StatusEnum.PENDING), artist1.getStaffId(), customer1.getCustomerId());
            
            //Categories
            CategoryEntity catSkateboard = categoryEntitySessionBeanLocal.createNewCategoryEntity(new CategoryEntity("Skateboard", "Skateboard"), null);
            CategoryEntity catCompleteSkateboard = categoryEntitySessionBeanLocal.createNewCategoryEntity(new CategoryEntity("Complete Skateboard", "Complete Skateboard"), catSkateboard.getCategoryId());
            CategoryEntity catSkateboardDeck = categoryEntitySessionBeanLocal.createNewCategoryEntity(new CategoryEntity("Skateboard Deck", "Skateboard Deck"), catSkateboard.getCategoryId());
            CategoryEntity catSkateboardBearings = categoryEntitySessionBeanLocal.createNewCategoryEntity(new CategoryEntity("Skateboard Bearings", "Skateboard Bearings"), catSkateboard.getCategoryId());
            CategoryEntity catSkateboardTrucks = categoryEntitySessionBeanLocal.createNewCategoryEntity(new CategoryEntity("Skateboard Trucks", "Skateboard Trucks"), catSkateboard.getCategoryId());
            CategoryEntity catSkateboardWheels = categoryEntitySessionBeanLocal.createNewCategoryEntity(new CategoryEntity("Skateboard Wheels", "Skateboard Wheels"), catSkateboard.getCategoryId());
            
            CategoryEntity catLongboard = categoryEntitySessionBeanLocal.createNewCategoryEntity(new CategoryEntity("Longboard", "Longboard"), null);
            CategoryEntity catCompleteLongboard = categoryEntitySessionBeanLocal.createNewCategoryEntity(new CategoryEntity("Complete Longboard", "Complete Longboard"), catLongboard.getCategoryId());
            CategoryEntity catLongboardDeck = categoryEntitySessionBeanLocal.createNewCategoryEntity(new CategoryEntity("Longboard Deck", "Longboard Deck"), catLongboard.getCategoryId());
            CategoryEntity catLongboardBearings = categoryEntitySessionBeanLocal.createNewCategoryEntity(new CategoryEntity("Longboard Bearings", "Longboard Bearings"), catLongboard.getCategoryId());
            CategoryEntity catLongboardTrucks = categoryEntitySessionBeanLocal.createNewCategoryEntity(new CategoryEntity("Longboard Trucks", "Longboard Trucks"), catLongboard.getCategoryId());
            CategoryEntity catLongboardWheels = categoryEntitySessionBeanLocal.createNewCategoryEntity(new CategoryEntity("Longboard Wheels", "Longboard Wheels"), catLongboard.getCategoryId());
            
            CategoryEntity catPennyBoard = categoryEntitySessionBeanLocal.createNewCategoryEntity(new CategoryEntity("Penny Board", "Penny Board"), null);
            CategoryEntity catCompletePennyBoard = categoryEntitySessionBeanLocal.createNewCategoryEntity(new CategoryEntity("Complete Penny Board", "Complete Penny Board"), catPennyBoard.getCategoryId());
            CategoryEntity catPennyBoardDeck = categoryEntitySessionBeanLocal.createNewCategoryEntity(new CategoryEntity("Penny Board Deck", "Penny Board Deck"), catPennyBoard.getCategoryId());
            CategoryEntity catPennyBoardBearings = categoryEntitySessionBeanLocal.createNewCategoryEntity(new CategoryEntity("Penny Board Bearings", "Penny Board Bearings"), catPennyBoard.getCategoryId());
            CategoryEntity catPennyBoardTrucks = categoryEntitySessionBeanLocal.createNewCategoryEntity(new CategoryEntity("Penny Board Trucks", "Penny Board Trucks"), catPennyBoard.getCategoryId());
            CategoryEntity catPennyBoardWheels = categoryEntitySessionBeanLocal.createNewCategoryEntity(new CategoryEntity("Penny Board Wheels", "Penny Board Wheels"), catPennyBoard.getCategoryId());
            
            //Tags
            TagEntity tagNew = tagEntitySessionBeanLocal.createNewTagEntity(new TagEntity("New"));
            TagEntity tagFeatured = tagEntitySessionBeanLocal.createNewTagEntity(new TagEntity("Featured"));  
            TagEntity tagSale20 = tagEntitySessionBeanLocal.createNewTagEntity(new TagEntity("Sale - 20% Off"));  
            TagEntity tagSale40 = tagEntitySessionBeanLocal.createNewTagEntity(new TagEntity("Sale - 40% Off"));  
            
            List<Long> tagIds = new ArrayList<>();
            tagIds.add(tagNew.getTagId());
            tagIds.add(tagFeatured.getTagId());
            tagIds.add(tagSale20.getTagId());
            tagIds.add(tagSale40.getTagId());
            System.out.println(tagIds);
            
            //Complete Skateboards
            ProductEntity productCS1 = productEntitySessionBeanLocal.createNewProduct(new ProductEntity("CS00001","BD Skate Co. Memphis Yellow 7.75\" Skateboard Complete", 
                    "Complete BD skateboard in yellow!", "https://cdn.shopify.com/s/files/1/0626/6876/7468/products/6447ac9c-b7b9-4668-9bbd-a4475a6d8fb9_500x.png?v=1645325256",
                    10, 10, new BigDecimal(130.00),new BigDecimal(150.00), 5), catCompleteSkateboard.getCategoryId(),null);
            ProductEntity productCS2 = productEntitySessionBeanLocal.createNewProduct(new ProductEntity("CS00002","BD Skate Co. Graffiti White 8.0\" Skateboard Complete", 
                    "Complete BD skateboard in white!", "https://cdn.shopify.com/s/files/1/0626/6876/7468/products/76b5377b-c327-4114-bea0-3192a2b30952_500x.png?v=1646894434",
                    10, 10, new BigDecimal(130.00),new BigDecimal(150.00), 5), catCompleteSkateboard.getCategoryId(),null);
            ProductEntity productCS3 = productEntitySessionBeanLocal.createNewProduct(new ProductEntity("CS00003","BD Skate Co. Modern Cosmos Blue 8.0\" Skateboard Complete", 
                    "Complete BD skateboard in blue!", "https://cdn.shopify.com/s/files/1/0626/6876/7468/products/bd_moderncosmos_blue_8_complete_skate_2021-819x1024_jpg_720x.png?v=1645498034",
                    10, 10, new BigDecimal(130.00),new BigDecimal(150.00), 5), catCompleteSkateboard.getCategoryId(),null);
            ProductEntity productCS4 = productEntitySessionBeanLocal.createNewProduct(new ProductEntity("CS00004","Nomad Horizon Tiffany 7.75\" Skateboard Complete", 
                    "Complete skateboard straight from Nomad.", "https://cdn.shopify.com/s/files/1/0626/6876/7468/products/2d8d6d78-493f-4008-a837-a88b3007a8e9_487x.png?v=1645499514",
                    10, 10, new BigDecimal(150.00),new BigDecimal(150.00), 5), catCompleteSkateboard.getCategoryId(),null);
            ProductEntity productCS5 = productEntitySessionBeanLocal.createNewProduct(new ProductEntity("CS00005","Nomad Krid Kollektive Thunder 8.0\" Skateboard Complete", 
                    "Complete skateboard straight from Nomad.", "https://cdn.shopify.com/s/files/1/0626/6876/7468/products/af03787f-2daa-42f9-a214-8e2e3515a41a_jpg_500x.png?v=1645499628",
                    10, 10, new BigDecimal(150.00),new BigDecimal(150.00), 5), catCompleteSkateboard.getCategoryId(),null);
            
            //Skateboard Decks
            ProductEntity productSD1 = productEntitySessionBeanLocal.createNewProduct(new ProductEntity("SD00001", "Sovrn Pluie 8.5\" Skateboard Deck",
                    "Designed by Alexandre Souêtre.", "https://cdn.shopify.com/s/files/1/0626/6876/7468/products/7c1da073-1f88-4a05-9f3d-d3f8a300a1cc.jpg?v=1645341292",
                    10, 10, new BigDecimal(110.00), new BigDecimal(110.00), 5), catSkateboardDeck.getCategoryId(), null);
            ProductEntity productSD2 = productEntitySessionBeanLocal.createNewProduct(new ProductEntity("SD00002", "Sovrn Dauphin 8.25\" Skateboard Deck",
                    "Designed by Alexandre Souêtre.", "https://cdn.shopify.com/s/files/1/0626/6876/7468/products/e989ab24-3c43-4da5-9be8-7b2cd2bfdbbb_500x.jpg?v=1645341061",
                    10, 10, new BigDecimal(110.00), new BigDecimal(110.00), 5), catSkateboardDeck.getCategoryId(), null);
            ProductEntity productSD3 = productEntitySessionBeanLocal.createNewProduct(new ProductEntity("SD00003", "Polar ROMAN GONZALEZ - Soldier 8.38\" Skateboard Deck",
                    "Polar Skate.", "https://cdn.shopify.com/s/files/1/0626/6876/7468/products/afaa82b1-d249-428e-a1b2-2a3bbf64ee89_500x.jpg?v=1645337962",
                    10, 10, new BigDecimal(100.00), new BigDecimal(100.00), 5), catSkateboardDeck.getCategoryId(), null);
            
            //Skateboard Bearings
            
            //Skateboard Trucks
            
            //Skateboard Wheels
            
            
            //Complete Longboards
            
            //Longboard Decks
            
            //Longboard Bearings
            
            //Longboard Trucks
            
            //Longboard Wheels
            
            
            //Complete Penny Boards
            
            //Penny Board Decks
            
            //Penny Board Bearings
            
            //Penny Board Trucks
            
            //Penny Board Wheels
           
            
            //Sale Transaction Entities
            //ST1
            SaleTransactionLineItemEntity stli11 = new SaleTransactionLineItemEntity(1, productCS1, 1, new BigDecimal("10"), new BigDecimal("10"));
            SaleTransactionLineItemEntity stli12 = new SaleTransactionLineItemEntity(1, productCS2, 1, new BigDecimal("20"), new BigDecimal("20"));
            
            List<SaleTransactionLineItemEntity> stlis1 = new ArrayList<>();
            stlis1.add(stli11);
            stlis1.add(stli12);
            
            SaleTransactionEntity st1 = new SaleTransactionEntity(3, 2, new BigDecimal("30"), new Date(), stlis1, Boolean.FALSE);
            saleTransactionEntitySessionBeanLocal.createNewSaleTransaction(admin1.getStaffId(), st1);

            //ST2
            
            
            //ST3
            
            //ST4
            
            //ST5
            
            
            
        } catch (CreateNewCategoryException | InputDataValidationException | ArtistUsernameExistException | CustomerUsernameExistException | StaffUsernameExistException | UnknownPersistenceException | CreateNewTagException | CreateNewProductException
                | ProductSkuCodeExistException | CreateNewCustomisationRequestException | CreateNewSaleTransactionException ex) {
            ex.printStackTrace();
        }


    } 
}
