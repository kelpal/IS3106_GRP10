/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.ArtistEntity;
import entity.CustomerEntity;
import entity.CustomisationRequest;
import java.util.List;
import java.util.Set;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import util.exception.ArtistNotFoundException;
import util.exception.CreateNewCustomisationRequestException;
import util.exception.CustomerNotFoundException;
import util.exception.CustomerNameExistException;
import util.exception.CustomisationRequestNotFoundException;
import util.exception.InputDataValidationException;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author harmo
 */
@Stateless
public class CustomisationRequestSessionBean implements CustomisationRequestSessionBeanLocal {

    @EJB
    private CustomerEntitySessionBeanLocal customerEntitySessionBeanLocal;

    @EJB
    private ArtistEntitySessionBeanLocal artistEntitySessionBeanLocal;
    private final ValidatorFactory validatorFactory;
    private final Validator validator;

    public CustomisationRequestSessionBean() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }
    
    
    

    @PersistenceContext(unitName = "SKATEasyV10-ejbPU")
    private EntityManager em;
    

    @Override
    public CustomisationRequest createCustomisationRequest(CustomisationRequest request, Long artistId, Long customerId) throws CreateNewCustomisationRequestException, UnknownPersistenceException,InputDataValidationException
    {
        Set<ConstraintViolation<CustomisationRequest>>constraintViolations = validator.validate(request);
        
        if(constraintViolations.isEmpty())
        {
            try
            {
                CustomerEntity customer = customerEntitySessionBeanLocal.retrieveCustomerById(customerId);
                ArtistEntity artist = artistEntitySessionBeanLocal.retrieveArtistById(artistId);
            
                request.setArtistEntity(artist);
                request.setCustomerEntity(customer);

                em.persist(request);
                em.flush();

                customer.getCustomisationRequests().add(request);
                artist.getCustomisationRequests().add(request);


                return request;
            }
            catch(PersistenceException | ArtistNotFoundException | CustomerNotFoundException ex)
            {
                if(ex.getCause() != null && ex.getCause().getClass().getName().equals("org.eclipse.persistence.exceptions.DatabaseException"))
                {
                    if(ex.getCause().getCause() != null && ex.getCause().getCause().getClass().getName().equals("java.sql.SQLIntegrityConstraintViolationException"))
                    {
                        throw new CreateNewCustomisationRequestException();
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
    public List<CustomisationRequest> retreiveCustomisationRequests(Long artistId) throws ArtistNotFoundException
    {

        ArtistEntity artist = artistEntitySessionBeanLocal.retrieveArtistById(artistId);

        return artist.getCustomisationRequests();

    }
    
    @Override
    public List<CustomisationRequest> retreiveCustomisationRequestsByDate(Long artistId) throws ArtistNotFoundException
    {

        Query query = em.createQuery("SELECT cr FROM CustomisationRequest cr WHERE cr.artistEntity.staffId = :inArtistId ORDER BY cr.requestDate desc");
        query.setParameter("inArtistId", artistId);

        return query.getResultList();

    }
    
    @Override
    public CustomisationRequest retrieveCustomisationRequestById(Long requestId) throws CustomisationRequestNotFoundException
    {
        CustomisationRequest request = em.find(CustomisationRequest.class, requestId);
        
        if(request != null)
        {
            return request;
        }
        else
        {
            throw new CustomisationRequestNotFoundException("Customisation Request ID " + requestId + " does not exist");
        }
    }
    
     @Override
    public CustomisationRequest updateCustomisationRequest(CustomisationRequest newRequest)
    {
        em.merge(newRequest);
        
        return newRequest;
    }
    
    private String prepareInputDataValidationErrorsMessage(Set<ConstraintViolation<CustomisationRequest>> constraintViolations)
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
