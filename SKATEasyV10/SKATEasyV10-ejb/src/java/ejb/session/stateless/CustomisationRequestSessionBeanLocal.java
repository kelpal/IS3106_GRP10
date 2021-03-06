/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.CustomisationRequest;
import java.util.List;
import javax.ejb.Local;
import util.exception.ArtistNotFoundException;
import util.exception.CreateNewCustomisationRequestException;
import util.exception.CustomisationRequestNotFoundException;
import util.exception.DeleteCustomisationRequestException;
import util.exception.InputDataValidationException;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author harmo
 */
@Local
public interface CustomisationRequestSessionBeanLocal {

    public CustomisationRequest createCustomisationRequest(CustomisationRequest request, Long artistId, Long customerId) throws CreateNewCustomisationRequestException, UnknownPersistenceException,InputDataValidationException;

    public List<CustomisationRequest> retreiveCustomisationRequests(Long artistId) throws ArtistNotFoundException;

    public CustomisationRequest updateCustomisationRequest(CustomisationRequest newRequest);

    public CustomisationRequest retrieveCustomisationRequestById(Long requestId) throws CustomisationRequestNotFoundException;

    public List<CustomisationRequest> retreiveCustomisationRequestsByDate(Long artistId) throws ArtistNotFoundException;

    public void deleteCustomisationRequest(Long customisationReqeuestId) throws CustomisationRequestNotFoundException, DeleteCustomisationRequestException;
    
}
