/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.ArtistEntity;
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
import util.exception.ArtistNotFoundException;
import util.exception.ArtistUsernameExistException;
import util.exception.InputDataValidationException;
import util.exception.InvalidLoginCredentialException;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author harmo
 */
@Stateless
public class ArtistEntitySessionBean implements ArtistEntitySessionBeanLocal {

    @PersistenceContext(unitName = "SKATEasyV10-ejbPU")
    private EntityManager em;
    
    private final ValidatorFactory validatorFactory;
    private final Validator validator;
    
    
    
    public ArtistEntitySessionBean()
    {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }
    
    @Override
    public Long createNewArtist(ArtistEntity newArtistEntity) throws ArtistUsernameExistException, UnknownPersistenceException, InputDataValidationException
    {
        Set<ConstraintViolation<ArtistEntity>>constraintViolations = validator.validate(newArtistEntity);
        
        if(constraintViolations.isEmpty())
        {
            try
            {
                em.persist(newArtistEntity);
                em.flush();

                return newArtistEntity.getArtistId();
            }
            catch(PersistenceException ex)
            {
                if(ex.getCause() != null && ex.getCause().getClass().getName().equals("org.eclipse.persistence.exceptions.DatabaseException"))
                {
                    if(ex.getCause().getCause() != null && ex.getCause().getCause().getClass().getName().equals("java.sql.SQLIntegrityConstraintViolationException"))
                    {
                        throw new ArtistUsernameExistException();
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
    public ArtistEntity artistLogin(String username, String password) throws InvalidLoginCredentialException
    {
        try
        {
            ArtistEntity artistEntity = retrieveArtistByUsername(username);
            
            if(artistEntity.getPassword().equals(password))
            {
                //artistEntity.getSaleTransactionEntities().size();                
                return artistEntity;
            }
            else
            {
                throw new InvalidLoginCredentialException("Username does not exist or invalid password!");
            }
        }
        catch(ArtistNotFoundException ex)
        {
            throw new InvalidLoginCredentialException("Username does not exist or invalid password!");
        }
    }
    
    public ArtistEntity retrieveArtistByUsername(String username) throws ArtistNotFoundException
    {
        Query query = em.createQuery("SELECT a FROM ArtistEntity a WHERE a.username = :inUsername");
        query.setParameter(":inUsername", username);
        
        
        try {
            return (ArtistEntity)query.getSingleResult();
        } catch (NoResultException | NonUniqueResultException ex)
        {
            throw new ArtistNotFoundException("Artist Username" + username + " cannot be found!");
        }
    }
    
    private String prepareInputDataValidationErrorsMessage(Set<ConstraintViolation<ArtistEntity>> constraintViolations)
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
