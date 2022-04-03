package ejb.session.stateless;

import entity.SaleTransactionEntity;
import entity.SaleTransactionLineItemEntity;
import entity.StaffEntity;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.exception.CreateNewSaleTransactionException;
import util.exception.ProductInsufficientQuantityOnHandException;
import util.exception.ProductNotFoundException;
import util.exception.SaleTransactionAlreadyVoidedRefundedException;
import util.exception.SaleTransactionNotFoundException;
import util.exception.StaffNotFoundException;



@Stateless

public class SaleTransactionEntitySessionBean implements SaleTransactionEntitySessionBeanLocal
{   
    
    @Resource
    private EJBContext eJBContext;
    
    @EJB
    private StaffEntitySessionBeanLocal staffEntitySessionBeanLocal;
    @EJB
    private ProductEntitySessionBeanLocal productEntitySessionBeanLocal;
    @PersistenceContext(unitName = "SKATEasyV10-ejbPU")
    private EntityManager entityManager;
    
    
    
    public SaleTransactionEntitySessionBean()
    {
    }
    
    
    
    // Updated in v4.1
    
    @Override
    public SaleTransactionEntity createNewSaleTransaction(Long staffId, SaleTransactionEntity newSaleTransactionEntity) throws CreateNewSaleTransactionException
    {
        if(newSaleTransactionEntity != null)
        {
           try
            {
                StaffEntity staffEntity = staffEntitySessionBeanLocal.retrieveStaffByStaffId(staffId);
                newSaleTransactionEntity.setStaffEntity(staffEntity);
                staffEntity.getSaleTransactionEntities().add(newSaleTransactionEntity);

                entityManager.persist(newSaleTransactionEntity);

                for(SaleTransactionLineItemEntity saleTransactionLineItemEntity:newSaleTransactionEntity.getSaleTransactionLineItemEntities())
                {
                    //productEntitySessionBeanLocal.debitQuantityOnHand(saleTransactionLineItemEntity.getProductEntity().getProductId(), saleTransactionLineItemEntity.getQuantity());
                    entityManager.persist(saleTransactionLineItemEntity);
                }

                entityManager.flush();

                return newSaleTransactionEntity;
            }
            catch(StaffNotFoundException ex)
            {
                // The line below rolls back all changes made to the database.
                eJBContext.setRollbackOnly();
                
                throw new CreateNewSaleTransactionException(ex.getMessage());
            }
        }
        else
        {
            throw new CreateNewSaleTransactionException("Sale transaction information not provided");
        }
    }
    
    
    
    @Override
    public List<SaleTransactionEntity> retrieveAllSaleTransactions()
    {
        Query query = entityManager.createQuery("SELECT st FROM SaleTransactionEntity st");
        
        return query.getResultList();
    }
    
    
    
    // Added in v4.1
    
    @Override
    public List<SaleTransactionLineItemEntity> retrieveSaleTransactionLineItemsByProductId(Long productId)
    {
        Query query = entityManager.createNamedQuery("selectAllSaleTransactionLineItemsByProductId");
        query.setParameter("inProductId", productId);
        
        return query.getResultList();
    }
    
    
    
    @Override
    public SaleTransactionEntity retrieveSaleTransactionBySaleTransactionId(Long saleTransactionId) throws SaleTransactionNotFoundException
    {
        SaleTransactionEntity saleTransactionEntity = entityManager.find(SaleTransactionEntity.class, saleTransactionId);
        
        if(saleTransactionEntity != null)
        {
            saleTransactionEntity.getSaleTransactionLineItemEntities().size();
            
            return saleTransactionEntity;
        }
        else
        {
            throw new SaleTransactionNotFoundException("Sale Transaction ID " + saleTransactionId + " does not exist!");
        }                
    }
    
    
    
    @Override
    public void updateSaleTransaction(SaleTransactionEntity saleTransactionEntity)
    {
        entityManager.merge(saleTransactionEntity);
    }
    
    
    
    // Updated in v4.1
    
    @Override
    public void voidRefundSaleTransaction(Long saleTransactionId) throws SaleTransactionNotFoundException, SaleTransactionAlreadyVoidedRefundedException
    {
        SaleTransactionEntity saleTransactionEntity = retrieveSaleTransactionBySaleTransactionId(saleTransactionId);
        
        if(!saleTransactionEntity.getVoidRefund())
        {
            for(SaleTransactionLineItemEntity saleTransactionLineItemEntity:saleTransactionEntity.getSaleTransactionLineItemEntities())
            {
                try
                {
                    productEntitySessionBeanLocal.creditQuantityOnHand(saleTransactionLineItemEntity.getProductEntity().getProductId(), saleTransactionLineItemEntity.getQuantity());
                }
                catch(ProductNotFoundException ex)
                {
                    ex.printStackTrace(); // Ignore exception since this should not happen
                }                
            }
            
            saleTransactionEntity.setVoidRefund(true);
        }
        else
        {
            throw new SaleTransactionAlreadyVoidedRefundedException("The sale transaction has aready been voided/refunded");
        }
    }
    
    
    
    @Override
    public void deleteSaleTransaction(SaleTransactionEntity saleTransactionEntity)
    {
        throw new UnsupportedOperationException();
    }
}
