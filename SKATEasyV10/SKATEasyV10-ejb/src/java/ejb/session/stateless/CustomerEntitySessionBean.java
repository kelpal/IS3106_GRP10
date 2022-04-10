/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.CustomerEntity;
import java.util.Set;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import util.exception.CustomerNotFoundException;
import util.exception.CustomerUsernameExistException;
import util.exception.InputDataValidationException;
import util.exception.InvalidLoginCredentialException;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author kel
 */
@Stateless
public class CustomerEntitySessionBean implements CustomerEntitySessionBeanLocal {

    @PersistenceContext(unitName = "SKATEasyV10-ejbPU")
    private EntityManager em;
    
    private final ValidatorFactory validatorFactory;
    private final Validator validator;
    
    public CustomerEntitySessionBean() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }
    
    
    @Override
    public CustomerEntity createNewCustomer(CustomerEntity newCustomerEntity) throws CustomerUsernameExistException, UnknownPersistenceException, InputDataValidationException
    {
        Set<ConstraintViolation<CustomerEntity>>constraintViolations = validator.validate(newCustomerEntity);
        
        if(constraintViolations.isEmpty())
        {
            try
            {
                em.persist(newCustomerEntity);
                em.flush();

                return newCustomerEntity;
            }
            catch(PersistenceException ex)
            {
                if(ex.getCause() != null && ex.getCause().getClass().getName().equals("org.eclipse.persistence.exceptions.DatabaseException"))
                {
                    if(ex.getCause().getCause() != null && ex.getCause().getCause().getClass().getName().equals("java.sql.SQLIntegrityConstraintViolationException"))
                    {
                        throw new CustomerUsernameExistException();
                    }
                    else
                    {
                        throw new UnknownPersistenceException(ex.getMessage());
                    }
                }
                else
                {
                    throw new UnknownPersistenceException(ex.getMessage());
                }
            }
        }
        else
        {
            throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
        }
    }
        
        

    @Override
    public CustomerEntity customerLogin(String username, String password) throws InvalidLoginCredentialException
    {
        try
        {
            CustomerEntity customerEntity = retrieveCustomerByUsername(username);
            
            if(customerEntity.getPassword().equals(password))
            {
                //artistEntity.getSaleTransactionEntities().size();                
                return customerEntity;
            }
            else
            {
                throw new InvalidLoginCredentialException("Username does not exist or invalid password!");
            }
        }
        catch(CustomerNotFoundException ex)
        {
            throw new InvalidLoginCredentialException("Username does not exist or invalid password!");
        }
    }
    
    @Override
    public CustomerEntity retrieveCustomerByUsername(String username) throws CustomerNotFoundException
    {
        Query query = em.createQuery("SELECT a FROM CustomerEntity a WHERE a.username = :inUsername");
        query.setParameter(":inUsername", username);
        
        
        try {
            return (CustomerEntity)query.getSingleResult();
        } catch (NoResultException | NonUniqueResultException ex)
        {
            throw new CustomerNotFoundException("Artist Username" + username + " cannot be found!");
        }
    }
    
    @Override
    public CustomerEntity retrieveCustomerById(Long employeeId) throws CustomerNotFoundException
    {
        CustomerEntity customer = em.find(CustomerEntity.class, employeeId);
        
        if(customer != null)
        {
            return customer;
        }
        else
        {
            throw new CustomerNotFoundException("Customer ID " + employeeId + " does not exist");
        }
    }
    
    
    private String prepareInputDataValidationErrorsMessage(Set<ConstraintViolation<CustomerEntity>> constraintViolations)
    {
        String msg = "Input data validation error!:";
            
        for(ConstraintViolation constraintViolation:constraintViolations)
        {
            msg += "\n\t" + constraintViolation.getPropertyPath() + " - " + constraintViolation.getInvalidValue() + "; " + constraintViolation.getMessage();
        }
        
        return msg;
    }
    
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

    
}
