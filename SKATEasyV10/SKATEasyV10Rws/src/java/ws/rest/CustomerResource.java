/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.rest;

import ejb.session.stateless.CustomerEntitySessionBeanLocal;
import entity.CustomerEntity;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

/**
 * REST Web Service
 *
 * @author ryany
 */
@Path("Customer")
public class CustomerResource {

    CustomerEntitySessionBeanLocal customerEntitySessionBeanLocal = lookupCustomerEntitySessionBeanLocal();
    
    

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of CustomerResource
     */
    public CustomerResource() {
    }
    
    @Path("customerLogin")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response customerLogin(String email, String password)
    {
    try
    {
	CustomerEntity customer = customerEntitySessionBeanLocal.customerLogin(email, password);

	GenericEntity<CustomerEntity> genericEntity = new GenericEntity<CustomerEntity>(customer) {
	};

	return Response.status(Status.OK).entity(genericEntity).build();
	}			
	catch(Exception ex)
	{
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
	}
    }

    private CustomerEntitySessionBeanLocal lookupCustomerEntitySessionBeanLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (CustomerEntitySessionBeanLocal) c.lookup("java:global/SKATEasyV10/SKATEasyV10-ejb/CustomerEntitySessionBean!ejb.session.stateless.CustomerEntitySessionBeanLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

}
