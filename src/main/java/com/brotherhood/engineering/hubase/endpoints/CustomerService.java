package com.brotherhood.engineering.hubase.endpoints;

import com.brotherhood.engineering.hubase.model.Customer;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by Palamayuran on 21/12/2016.
 */
@Path("/customer")
public class CustomerService {
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createCustomer(final Customer customer){
        System.out.println("ID: " + customer.getId());
        System.out.println("First Name: " + customer.getFirstName());
        System.out.println("Last Name: " + customer.getLastName());
        System.out.println("Email: " + customer.getEmail());
        final Response response = Response.ok().build();
        return response;
    }

    @GET
    @Path("/{id}")
    public Response getCustomer(final @PathParam("id") String id){
        Response response = Response.ok().build();
        return response;
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateCustomer(){
        Response response = Response.ok().build();
        return response;
    }

    @DELETE
    @Path("/{id}")
    public Response deleteCustomer(){
        Response response = Response.ok().build();
        return response;
    }

}
