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
import entity.CreditCard;
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
import util.exception.CreateCreditCardException;
import util.exception.CreateNewCategoryException;
import util.exception.CreateNewCustomisationRequestException;
import util.exception.CreateNewProductException;
import util.exception.CreateNewSaleTransactionException;
import util.exception.CreateNewTagException;
import util.exception.CustomerNameExistException;
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
            
            //CreditCard
            CreditCard  creditCard1 = new CreditCard("1234123412341234", "Customer 1", new Date(), "123");
            
            
            //Customers
            CustomerEntity customer1 = customerEntitySessionBeanLocal.createNewCustomer(new CustomerEntity("Customer 1", "customer1@email.com", "password"));
            CustomerEntity customer2 = customerEntitySessionBeanLocal.createNewCustomer(new CustomerEntity("Customer 2", "customer2@email.com", "password"));
            CustomerEntity customer3 = customerEntitySessionBeanLocal.createNewCustomer(new CustomerEntity("Customer 3", "customer3@email.com", "password"));
            
            customerEntitySessionBeanLocal.addCreditCard(customer1.getCustomerId(), creditCard1);
            
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
            CategoryEntity catLongboardBushings = categoryEntitySessionBeanLocal.createNewCategoryEntity(new CategoryEntity("Longboard Bushings", "Longboard Bushings"), catLongboard.getCategoryId());
            CategoryEntity catLongboardTrucks = categoryEntitySessionBeanLocal.createNewCategoryEntity(new CategoryEntity("Longboard Trucks", "Longboard Trucks"), catLongboard.getCategoryId());
            CategoryEntity catLongboardWheels = categoryEntitySessionBeanLocal.createNewCategoryEntity(new CategoryEntity("Longboard Wheels", "Longboard Wheels"), catLongboard.getCategoryId());
            
            CategoryEntity catSurfskate = categoryEntitySessionBeanLocal.createNewCategoryEntity(new CategoryEntity("Surfskate", "Surfskate"), null);
            CategoryEntity catCompleteSurfskate = categoryEntitySessionBeanLocal.createNewCategoryEntity(new CategoryEntity("Complete Surfskate", "Complete Surfskate"), catSurfskate.getCategoryId());
            CategoryEntity catSurfskateDeck = categoryEntitySessionBeanLocal.createNewCategoryEntity(new CategoryEntity("Surfskate Deck", "Surfskate Deck"), catSurfskate.getCategoryId());
            CategoryEntity catSurfskateAdapters = categoryEntitySessionBeanLocal.createNewCategoryEntity(new CategoryEntity("Surfskate Adapters", "Surfskate Adapters"), catSurfskate.getCategoryId());
            CategoryEntity catSurfskateWheels = categoryEntitySessionBeanLocal.createNewCategoryEntity(new CategoryEntity("Surfskate Wheels", "Surfskate Wheels"), catSurfskate.getCategoryId());
            
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
            ProductEntity productSB1 = productEntitySessionBeanLocal.createNewProduct(new ProductEntity("SB00001", "Bronson Speed Co. Pedro Delfino Pro G3 Bearings",
                    "Bronson Speed Co.", "https://cdn.shopify.com/s/files/1/0626/6876/7468/products/c80c3015-4d75-41de-8162-9d21e69e9591_500x.jpg?v=1645326186",
                    10, 10, new BigDecimal(65.00), new BigDecimal(65.00), 5), catSkateboardBearings.getCategoryId(), null);
            ProductEntity productSB2 = productEntitySessionBeanLocal.createNewProduct(new ProductEntity("SB00002", "Magenta Abec 5 Plant Bearings",
                    "Magenta Skateboards", "https://cdn.shopify.com/s/files/1/0626/6876/7468/products/magenta_plantbearings_abec5_accessories_skate_2022_720x.jpg?v=1647485848",
                    10, 10, new BigDecimal(35.00), new BigDecimal(35.00), 5), catSkateboardBearings.getCategoryId(), null);
            ProductEntity productSB3 = productEntitySessionBeanLocal.createNewProduct(new ProductEntity("SB00003", "BD Skate Co. Silver Skate Bearings",
                    "BD Skate Co.", "https://cdn.shopify.com/s/files/1/0626/6876/7468/products/26b42e06-a490-4982-a7ac-befc403b387d_500x.jpg?v=1645325417",
                    10, 10, new BigDecimal(110.00), new BigDecimal(110.00), 5), catSkateboardBearings.getCategoryId(), null);
            
            //Skateboard Trucks
            ProductEntity productST1 = productEntitySessionBeanLocal.createNewProduct(new ProductEntity("ST00001", "Ace Trucks Mfg. AF1 22 Black 5.2\" Trucks",
                    "All trucks are sold as a pair.", "https://cdn.shopify.com/s/files/1/0626/6876/7468/products/9693fac3-2c83-4479-add2-3a94256f7e64_500x.png?v=1645321576",
                    10, 10, new BigDecimal(90.00), new BigDecimal(90.00), 5), catSkateboardTrucks.getCategoryId(), null);  
            ProductEntity productST2 = productEntitySessionBeanLocal.createNewProduct(new ProductEntity("ST00002", "Ace Trucks Mfg. AF1 66 Gold 6.4\" Trucks",
                    "All trucks are sold as a pair.", "https://cdn.shopify.com/s/files/1/0626/6876/7468/products/212727a9-6bc4-4e50-9feb-06671152b51d_500x.png?v=1645321739",
                    10, 10, new BigDecimal(95.00), new BigDecimal(95.00), 5), catSkateboardTrucks.getCategoryId(), null);
            ProductEntity productST3 = productEntitySessionBeanLocal.createNewProduct(new ProductEntity("ST00003", "Ace Trucks Mfg. Classic 44 Matte Black 5.75\" Trucks",
                    "All trucks are sold as a pair.", "https://cdn.shopify.com/s/files/1/0626/6876/7468/products/7d9892d2-e967-4d0b-92a2-68f21880cdae_500x.png?v=1645321853",
                    10, 10, new BigDecimal(80.00), new BigDecimal(80.00), 5), catSkateboardTrucks.getCategoryId(), null);
            
            //Skateboard Wheels
            ProductEntity productSW1 = productEntitySessionBeanLocal.createNewProduct(new ProductEntity("SW00001", "Magenta Classic Power 56mm 101A Wheel Pack",
                    "Premium urethanes made in USA.", "https://cdn.shopify.com/s/files/1/0626/6876/7468/products/be00311b-1a0a-4ce7-95a6-f65e762bb805_500x.jpg?v=1645334177",
                    10, 10, new BigDecimal(80.00), new BigDecimal(80.00), 5), catSkateboardWheels.getCategoryId(), null);            
            ProductEntity productSW2 = productEntitySessionBeanLocal.createNewProduct(new ProductEntity("SW00002", "BD Skate Co. Splash White 52mm 101A Wheel Pack",
                    "Rock Hard Wheels 101A.", "https://cdn.shopify.com/s/files/1/0626/6876/7468/products/88a6c1df-71a0-4eec-8a45-9f0e6ba59f70_500x.jpg?v=1645325475",
                    10, 10, new BigDecimal(50.00), new BigDecimal(50.00), 5), catSkateboardWheels.getCategoryId(), null);    
            ProductEntity productSW3 = productEntitySessionBeanLocal.createNewProduct(new ProductEntity("SW00003", "Droshky Neon Series: Last Call 52mm 101A Wheel Pack",
                    "High Quality skateboard wheels, perfect for tricks and park riding.", "https://cdn.shopify.com/s/files/1/0626/6876/7468/products/a4c4f49a-5158-448a-927f-e4a937742c93_500x.png?v=1645330759",
                    10, 10, new BigDecimal(50.00), new BigDecimal(50.00), 5), catSkateboardWheels.getCategoryId(), null);    
            
            //Complete Longboards
            ProductEntity productCL1 = productEntitySessionBeanLocal.createNewProduct(new ProductEntity("CL00001", "Long Island Stamp 37\" Kicktail Longboard Complete",
                    "Perfect for all levels from beginners to advanced.", "https://cdn.shopify.com/s/files/1/0626/6876/7468/products/9384147c-b343-4826-a083-105bad6d35c8_500x.png?v=1645333576",
                    10, 10, new BigDecimal(330.00), new BigDecimal(330.00), 5), catCompleteLongboard.getCategoryId(), null);
            ProductEntity productCL2 = productEntitySessionBeanLocal.createNewProduct(new ProductEntity("CL00002", "Arbor Groundswell Mission 35\" Longboard Complete",
                    "Wood material comes from sustainable sources of supply", "https://cdn.shopify.com/s/files/1/0626/6876/7468/products/75699705-f174-4870-9e9e-d8517d1de02a_500x.png?v=1645323945",
                    10, 10, new BigDecimal(350.00), new BigDecimal(350.00), 5), catCompleteLongboard.getCategoryId(), null);
            ProductEntity productCL3 = productEntitySessionBeanLocal.createNewProduct(new ProductEntity("CL00003", "Madrid Trance 40\" Flamingos Drop Through Longboard Complete",
                    "Perfect for new riders who want a stable setup with lots of room to maneuve.", "https://cdn.shopify.com/s/files/1/0626/6876/7468/products/madrid_flamingosdropthrough_40_complete_skate_2022_720x.jpg?v=1648283389",
                    10, 10, new BigDecimal(330.00), new BigDecimal(330.00), 5), catCompleteLongboard.getCategoryId(), null);
            ProductEntity productCL4 = productEntitySessionBeanLocal.createNewProduct(new ProductEntity("CL00004", "Arbor Flagship Axis 40\" Performance Longboard Complete",
                    "A snowboard-inspired drop-through for easy around town cruising, relaxed commutes, and mellow downhill.", "https://cdn.shopify.com/s/files/1/0626/6876/7468/products/2a2e1f92-5861-4b4f-9bb6-9132cd9a0da1_500x.png?v=1645404762",
                    10, 10, new BigDecimal(410.00), new BigDecimal(410.00), 5), catCompleteLongboard.getCategoryId(), null);
            ProductEntity productCL5 = productEntitySessionBeanLocal.createNewProduct(new ProductEntity("CL00005", "Arbor Bamboo Fish 37\" Performance Longboard Complete",
                    "A compact pintail with an extended wheelbase and mellow flex for around town cruising and lower speed carving.", "https://cdn.shopify.com/s/files/1/0626/6876/7468/products/865cf282-71fb-4618-a8e6-a0c08a57b2eb_3e70d71d-4623-4922-b917-c0479eb89eac_500x.png?v=1645404402",
                    10, 10, new BigDecimal(330.00), new BigDecimal(330.00), 5), catCompleteLongboard.getCategoryId(), null);
            
            //Longboard Decks
            ProductEntity productLD1 = productEntitySessionBeanLocal.createNewProduct(new ProductEntity("LD00001", "Odyssey Nalu 40\" Longboard Deck",
                    "Vendor: Odyssey", "https://cdn.shopify.com/s/files/1/0626/6876/7468/products/OdysseyNalu_720x.png?v=1645434312",
                    10, 10, new BigDecimal(155.00), new BigDecimal(155.00), 5), catLongboardDeck.getCategoryId(), null);
            ProductEntity productLD2 = productEntitySessionBeanLocal.createNewProduct(new ProductEntity("LD00002", "Prism Reaver Cop Caller 34\" Longboard Deck",
                    "Vendor: Prism Skate Co.", "https://cdn.shopify.com/s/files/1/0626/6876/7468/products/bec45bee-9b5f-4e9f-b740-faac635e111d_500x.png?v=1645338324",
                    10, 10, new BigDecimal(280.00), new BigDecimal(280.00), 5), catLongboardDeck.getCategoryId(), null);
            ProductEntity productLD3 = productEntitySessionBeanLocal.createNewProduct(new ProductEntity("LD00003", "LUCA BALLAR M",
                    "Vendor: Luca", "https://cdn.shopify.com/s/files/1/0626/6876/7468/products/39fccaeb-98f1-4dc2-991e-84e31423dd4e_500x.jpg?v=1645258035",
                    10, 10, new BigDecimal(390.00), new BigDecimal(390.00), 5), catLongboardDeck.getCategoryId(), null);
            
            //Longboard Bearings
            ProductEntity productLBE1 = productEntitySessionBeanLocal.createNewProduct(new ProductEntity("LBE0001", "Mosaic Super 1 Madars Apse Abec 7 Black Blue White Bearings",
                    "Premium grade, high quality bearings for all skateboard types.", "https://cdn.shopify.com/s/files/1/0626/6876/7468/products/220386d7-9b0b-4cb7-a452-6b0213d5904d_500x.png?v=1645335158",
                    10, 10, new BigDecimal(35.00), new BigDecimal(35.00), 5), catLongboardBearings.getCategoryId(), null);
            ProductEntity productLBE2 = productEntitySessionBeanLocal.createNewProduct(new ProductEntity("LBE0002", "Sk8mafia Super 1 Abec 7 608RS Black Mosaic Bearings",
                    "Premium grade, high quality bearings for all skateboard types.", "https://cdn.shopify.com/s/files/1/0626/6876/7468/products/8ba6c83d-a34a-4dd5-9b25-1f536945900d_500x.png?v=1645340381",
                    10, 10, new BigDecimal(35.00), new BigDecimal(35.00), 5), catLongboardBearings.getCategoryId(), null);
            ProductEntity productLBE3 = productEntitySessionBeanLocal.createNewProduct(new ProductEntity("LBE0003", "Mosaic Titanium Gold Black Bearings",
                    "Premium grade, high quality bearings for all skateboard types.", "https://cdn.shopify.com/s/files/1/0626/6876/7468/products/4f15b711-47c2-4296-84e0-0ff00358931d_500x.jpg?v=1645335183",
                    10, 10, new BigDecimal(60.00), new BigDecimal(60.00), 5), catLongboardBearings.getCategoryId(), null);
            
            //Longboard Bushings
            ProductEntity productLBU1 = productEntitySessionBeanLocal.createNewProduct(new ProductEntity("LBU0001", "RipTide Sports APS Bushings - Canon 80A",
                    "Riptide APS™ Formula: Animated Polymer System", "https://cdn.shopify.com/s/files/1/0626/6876/7468/products/d6eb8604-66d0-4e7e-acfb-15b4059e433a_500x.jpg?v=1645255297",
                    10, 10, new BigDecimal(15.00), new BigDecimal(15.00), 5), catLongboardBushings.getCategoryId(), null);
            ProductEntity productLBU2 = productEntitySessionBeanLocal.createNewProduct(new ProductEntity("LBU0002", "RipTide Sports APS Bushings - Canon 85A",
                    "Riptide APS™ Formula: Animated Polymer System", "https://cdn.shopify.com/s/files/1/0626/6876/7468/products/riptide_canon_80a_bushings_skate_2021_360x.jpg?v=1645238206",
                    10, 10, new BigDecimal(15.00), new BigDecimal(15.00), 5), catLongboardBushings.getCategoryId(), null);
            ProductEntity productLBU3 = productEntitySessionBeanLocal.createNewProduct(new ProductEntity("LBU0003", "RipTide Sports APS Bushings - Canon 90A",
                    "Riptide APS™ Formula: Animated Polymer System", "https://cdn.shopify.com/s/files/1/0626/6876/7468/products/riptide_canon_75a_bushings_skate_2021_360x.jpg?v=1645238207",
                    10, 10, new BigDecimal(15.00), new BigDecimal(15.00), 5), catLongboardBushings.getCategoryId(), null);
            
            //Longboard Trucks
            ProductEntity productLT1 = productEntitySessionBeanLocal.createNewProduct(new ProductEntity("LT00001", "Caliber Satin Gold 184mm 50° Hanger Trucks",
                    "Vendor: Caliber Trucks Co.", "https://cdn.shopify.com/s/files/1/0626/6876/7468/products/2f14add8-93e7-4722-93f6-5b03fa98ccdd_500x.jpg?v=1645328355",
                    10, 10, new BigDecimal(95.00), new BigDecimal(95.00), 5), catLongboardTrucks.getCategoryId(), null);
            ProductEntity productLT2 = productEntitySessionBeanLocal.createNewProduct(new ProductEntity("LT00002", "Caliber Stone Plum 184mm 50° Hanger Trucks",
                    "Vendor: Caliber Trucks Co.", "https://cdn.shopify.com/s/files/1/0626/6876/7468/products/7e930234-c1f0-4287-b2b4-11556c2dccf5_500x.jpg?v=1645328421",
                    10, 10, new BigDecimal(95.00), new BigDecimal(95.00), 5), catLongboardTrucks.getCategoryId(), null);
            ProductEntity productLT3 = productEntitySessionBeanLocal.createNewProduct(new ProductEntity("LT00003", "Long Island Freedom Semipolished Inverted 180mm Truck",
                    "Vendor: Long Island. Inverted trucks give your board more control and stability.", "https://cdn.shopify.com/s/files/1/0626/6876/7468/products/433bb292-e916-4a77-9634-a2a8a0ad0bae_500x.png?v=1645258514",
                    10, 10, new BigDecimal(110.00), new BigDecimal(110.00), 5), catLongboardTrucks.getCategoryId(), null);
            
            //Longboard Wheels
            ProductEntity productLW1 = productEntitySessionBeanLocal.createNewProduct(new ProductEntity("LW00001", "Blood Orange Morgan Pro Series 70mm/84A, Coral Wheel Pack",
                    "Provides the perfect slide/grip balance.", "https://cdn.shopify.com/s/files/1/0626/6876/7468/products/919d34c8-9026-4efc-b7e8-b1f94896e94b_500x.jpg?v=1645325965",
                    10, 10, new BigDecimal(95.00), new BigDecimal(95.00), 5), catLongboardWheels.getCategoryId(), null);            
            ProductEntity productLW2 = productEntitySessionBeanLocal.createNewProduct(new ProductEntity("LW00002", "nameTRAVELOL LONGBOARD WHEELS 60MM - BLUE",
                    "Wheels perfect for Longboards, Cruisers, Surfskates and more", "https://cdn.shopify.com/s/files/1/0626/6876/7468/products/e2f10c84-051d-4261-94f3-adb6ad8933b0_500x.jpg?v=1645257633",
                    10, 10, new BigDecimal(55.00), new BigDecimal(55.00), 5), catLongboardWheels.getCategoryId(), null);            
            ProductEntity productLW3 = productEntitySessionBeanLocal.createNewProduct(new ProductEntity("LW00003", "Arbor Bogart Amber 61mm 78A Wheel Pack",
                    "Arbor's most highly rated Longboard wheels.", "https://cdn.shopify.com/s/files/1/0626/6876/7468/products/617428d1-970b-4395-8128-656cd3c7409c_500x.jpg?v=1645323454",
                    10, 10, new BigDecimal(70.00), new BigDecimal(70.00), 5), catLongboardWheels.getCategoryId(), null);            
            
            
            //Complete Surfskates
            ProductEntity productCSS1 = productEntitySessionBeanLocal.createNewProduct(new ProductEntity("CSS0001", "Yow x Medina Tie Dye 33'' Meraki S5 Surfskate 2021 Complete",
                    "A unique collaboration with the 2x world champion and all-round legend Gabriel Medina.", "https://cdn.shopify.com/s/files/1/0626/6876/7468/products/dfe1c8ff-3e0c-4734-a183-b8e4fde62eb6_500x.jpg?v=1645345265",
                    10, 10, new BigDecimal(500.00), new BigDecimal(500.00), 5), catCompleteSurfskate.getCategoryId(), null);
            ProductEntity productCSS2 = productEntitySessionBeanLocal.createNewProduct(new ProductEntity("CSS0002", "Yow Coxos 31'' Meraki S5 Surfskate 2021 Complete",
                    "One of our top 3 best-sellers due to its versatility.", "https://cdn.shopify.com/s/files/1/0626/6876/7468/products/3bb915fe-ada6-47a4-994f-ab5da84f3164_500x.jpg?v=1645277633",
                    10, 10, new BigDecimal(500.00), new BigDecimal(500.00), 5), catCompleteSurfskate.getCategoryId(), null);
            ProductEntity productCSS3 = productEntitySessionBeanLocal.createNewProduct(new ProductEntity("CSS0003", "Slide Sancheski Heritage 30\" Surfskate Complete 2021",
                    "Designed by the Spanish artist Borja Holke as a tribute to the old days.", "https://cdn.shopify.com/s/files/1/0626/6876/7468/products/JoyfulHeritage_720x.png?v=1645517045",
                    10, 10, new BigDecimal(380.00), new BigDecimal(380.00), 5), catCompleteSurfskate.getCategoryId(), null);
            ProductEntity productCSS4 = productEntitySessionBeanLocal.createNewProduct(new ProductEntity("CSS0004", "Slide Fish Indigo Fade 32\" Surfskate Complete 2021",
                    "Its fish-like shape makes it very popular.", "https://cdn.shopify.com/s/files/1/0626/6876/7468/products/fishindigofade_720x.png?v=1645516514",
                    10, 10, new BigDecimal(380.00), new BigDecimal(380.00), 5), catCompleteSurfskate.getCategoryId(), null);
            ProductEntity productCSS5 = productEntitySessionBeanLocal.createNewProduct(new ProductEntity("CSS0005", "Long Island Summer 33\" Surfskate",
                    "A classic Long Island Longboards staple.", "https://cdn.shopify.com/s/files/1/0626/6876/7468/products/98ef9c0f-d5ac-44c0-a677-5adc65dc9ff8_500x.png?v=1645333592",
                    10, 10, new BigDecimal(350.00), new BigDecimal(350.00), 5), catCompleteSurfskate.getCategoryId(), null);
            
            //Surfskate Decks
            ProductEntity productSSD1 = productEntitySessionBeanLocal.createNewProduct(new ProductEntity("SSD0001", "SOULBOARDIY CARBONYX ADAM - Blue",
                    "Perfect for those looking for decks with a steeper concave, faster responsive and lighter construction.", "https://cdn.shopify.com/s/files/1/0626/6876/7468/products/0001_CARBONYX_ADAM_5_720x.jpg?v=1649824882",
                    10, 10, new BigDecimal(300.00), new BigDecimal(300.00), 5), catSurfskateDeck.getCategoryId(), null);
            ProductEntity productSSD2 = productEntitySessionBeanLocal.createNewProduct(new ProductEntity("SSD0002", "SOULBOARDIY CARBONYX ADAM - Splatter",
                    "Perfect for those looking for decks with a steeper concave, faster responsive and lighter construction.", "https://cdn.shopify.com/s/files/1/0626/6876/7468/products/0003_CARBONYX_ADAM_3_720x.jpg?v=1649825032",
                    10, 10, new BigDecimal(300.00), new BigDecimal(300.00), 5), catSurfskateDeck.getCategoryId(), null);
            ProductEntity productSSD3 = productEntitySessionBeanLocal.createNewProduct(new ProductEntity("SSD0003", "SOULBOARDIY REVOLUTION ADAM - Neutral",
                    "Perfect for those looking for decks with a steeper concave, faster responsive and lighter construction.", "https://cdn.shopify.com/s/files/1/0626/6876/7468/products/0000_REVOLUTION_ADAM_1_720x.jpg?v=1649826263",
                    10, 10, new BigDecimal(300.00), new BigDecimal(300.00), 5), catSurfskateDeck.getCategoryId(), null);
            
            //Surfskate Adapters
            ProductEntity productSSA1 = productEntitySessionBeanLocal.createNewProduct(new ProductEntity("SSA0001", "Slide Surfskate Truck Kit V3\n",
                    "Vendor: Slide Surfskates", "https://cdn.shopify.com/s/files/1/0626/6876/7468/products/d162d952-d0bd-4ca0-8d86-e9de4906ccda_500x.jpg?v=1645340693",
                    10, 10, new BigDecimal(200.00), new BigDecimal(200.00), 5), catSurfskateAdapters.getCategoryId(), null);
            ProductEntity productSSA2 = productEntitySessionBeanLocal.createNewProduct(new ProductEntity("SSA0002", "Yow Meraki System Pack (S5 System)",
                    "Vender: Yow", "https://cdn.shopify.com/s/files/1/0626/6876/7468/products/8e1b92a7-9149-4c85-ab79-40c402f54b77_500x.jpg?v=1645345115",
                    10, 10, new BigDecimal(270.00), new BigDecimal(270.00), 5), catSurfskateAdapters.getCategoryId(), null);
            ProductEntity productSSA3 = productEntitySessionBeanLocal.createNewProduct(new ProductEntity("SSA0003", "Yow Pack V4 S4 System",
                    "Vender: Yow", "https://cdn.shopify.com/s/files/1/0626/6876/7468/products/f8318b3e-5e56-4e3f-831e-4745287dac47_500x.png?v=1645258350",
                    10, 10, new BigDecimal(160.00), new BigDecimal(160.00), 5), catSurfskateAdapters.getCategoryId(), null);
            
            //Surfskate Wheels
            ProductEntity productSSW1 = productEntitySessionBeanLocal.createNewProduct(new ProductEntity("SSW0001", "Blood Orange Morgan Pro Series 65mm/82A Lavender Wheel Pack",
                    "Provides the perfect slide/grip balance.", "https://cdn.shopify.com/s/files/1/0626/6876/7468/products/96490cce-b98c-447c-8b4a-56ce2812135b_500x.jpg?v=1645325887",
                    10, 10, new BigDecimal(85.00), new BigDecimal(85.00), 5), catSurfskateWheels.getCategoryId(), null);
            ProductEntity productSSW2 = productEntitySessionBeanLocal.createNewProduct(new ProductEntity("SSW0002", "Blood Orange Morgan Pro Series 65mm/82A Midnight Maroon Wheel Pack",
                    "Provides the perfect slide/grip balance.", "https://cdn.shopify.com/s/files/1/0626/6876/7468/products/15d62e32-eb51-44ec-b9a9-fbf87ef51731_500x.jpg?v=1645325895",
                    10, 10, new BigDecimal(85.00), new BigDecimal(85.00), 5), catSurfskateWheels.getCategoryId(), null);
            ProductEntity productSSW3 = productEntitySessionBeanLocal.createNewProduct(new ProductEntity("SSW0003", "Slide Surfskates Skate Wheel Set 65mm",
                    "Enhances control and predictability", "https://cdn.shopify.com/s/files/1/0626/6876/7468/products/5c02c7c7-9aa1-4c7f-81ce-9db66ed9df69_500x.jpg?v=1645340702",
                    10, 10, new BigDecimal(85.00), new BigDecimal(85.00), 5), catSurfskateWheels.getCategoryId(), null);
  
            
            
            
            //Sale Transaction Entities
            //ST1
            SaleTransactionLineItemEntity stli11 = new SaleTransactionLineItemEntity(1, productCS1, 1, new BigDecimal("1"), productCS1.getUnitPrice());
            SaleTransactionLineItemEntity stli12 = new SaleTransactionLineItemEntity(1, productCS2, 1, new BigDecimal("2"), productCS2.getUnitPrice());
            
            List<SaleTransactionLineItemEntity> stlis1 = new ArrayList<>();
            stlis1.add(stli11);
            stlis1.add(stli12);
            
            SaleTransactionEntity st1 = new SaleTransactionEntity(3, 2, new BigDecimal("30"), new Date(), stlis1, Boolean.FALSE);
            saleTransactionEntitySessionBeanLocal.createNewSaleTransaction(admin1.getStaffId(), st1);

            //ST2
            
            
            //ST3
            
            //ST4
            
            //ST5
            
            
            
        } catch (CreateNewCategoryException | InputDataValidationException | ArtistUsernameExistException | CustomerNameExistException | StaffUsernameExistException | UnknownPersistenceException | CreateNewTagException | CreateNewProductException
                | ProductSkuCodeExistException | CreateNewCustomisationRequestException | CreateNewSaleTransactionException | CreateCreditCardException ex) {
            ex.printStackTrace();
        }


    } 
}
