/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.CustomerEntity;
import java.util.List;
import javax.ejb.Local;
import util.exception.CustomerNotFoundException;
import util.exception.CustomerUsernameExistException;
import util.exception.InputDataValidationException;
import util.exception.InvalidLoginCredentialException;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author kel
 */
@Local
public interface CustomerEntitySessionBeanLocal {

    public Long createNewCustomer(CustomerEntity newCustomerEntity) throws CustomerUsernameExistException, UnknownPersistenceException, InputDataValidationException;

    public CustomerEntity customerLogin(String username, String password) throws InvalidLoginCredentialException;

    public CustomerEntity retrieveCustomerByUsername(String username) throws CustomerNotFoundException;

    public List<CustomerEntity> retrieveAllCustomers();
    
}
