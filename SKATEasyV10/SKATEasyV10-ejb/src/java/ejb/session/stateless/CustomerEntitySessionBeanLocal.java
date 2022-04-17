/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.ArtistEntity;
import entity.CreditCard;
import entity.CustomerEntity;
import java.util.List;
import javax.ejb.Local;
import util.exception.CreateCreditCardException;
import util.exception.CustomerNotFoundException;
import util.exception.CustomerNameExistException;
import util.exception.DeleteCreditCardException;
import util.exception.InputDataValidationException;
import util.exception.InvalidLoginCredentialException;
import util.exception.UnknownPersistenceException;
import util.exception.UpdateCreditCardException;

/**
 *
 * @author kel
 */
@Local
public interface CustomerEntitySessionBeanLocal {

    public CustomerEntity createNewCustomer(CustomerEntity newCustomerEntity) throws CustomerNameExistException, UnknownPersistenceException, InputDataValidationException;

    public CustomerEntity customerLogin(String username, String password) throws InvalidLoginCredentialException;

    public CustomerEntity retrieveCustomerByUsername(String username) throws CustomerNotFoundException;


    public CustomerEntity retrieveCustomerById(Long employeeId) throws CustomerNotFoundException;

    public List<CustomerEntity> retrieveAllCustomers();

    public void updateCustomer(CustomerEntity customer);

    public CustomerEntity addCreditCard(Long customerId, CreditCard creditCard) throws CreateCreditCardException;

    public CustomerEntity changeCreditCard(Long customerId, CreditCard newCreditCard) throws CreateCreditCardException, UpdateCreditCardException;

    public CustomerEntity removeCreditCard(Long customerId) throws DeleteCreditCardException;
    
}
