package com.brotherhood.engineering.hubase.entityproviders;

import com.brotherhood.engineering.hubase.model.Customer;
import org.json.JSONObject;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

/**
 * Created by Palamayuran on 23/12/2016.
 */
@Provider
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CustomerProvider implements MessageBodyReader, MessageBodyWriter{

    @Override
    public boolean isReadable(Class aClass, Type type, Annotation[] annotations, MediaType mediaType) {
        if(Customer.class.equals(aClass))
            return true;
        else
            return false;
    }

    @Override
    public Object readFrom(Class aClass, Type type, Annotation[] annotations, MediaType mediaType, MultivaluedMap multivaluedMap, InputStream inputStream) throws IOException, WebApplicationException {
        final StringBuilder requestBody = new StringBuilder();
        byte[] inputBytes = new byte[4096];
        int noOfBytesRead = 0;
        while((noOfBytesRead = inputStream.read(inputBytes)) != -1){
            requestBody.append(new String(inputBytes, 0, noOfBytesRead));
        }

        final JSONObject customerJSON = new JSONObject(requestBody.toString());

        final String firstName = customerJSON.getString("first_name");
        final String lastName = customerJSON.getString("last_name");
        final String email = customerJSON.getString("email");
        final String password = customerJSON.getString("password");

        final Customer customer = new Customer(email, firstName, lastName);
        customer.setPassword(password);

        return customer;
    }

    @Override
    public boolean isWriteable(Class aClass, Type type, Annotation[] annotations, MediaType mediaType) {
        if(Customer.class.equals(aClass))
            return true;
        else
            return false;
    }

    /**
     * As of JAX-RS 2.0, the method has been deprecated and the value returned by the method is ignored by a JAX-RS runtime.
     * All MessageBodyWriter implementations are advised to return -1 from the method.
     * Responsibility to compute the actual Content-Length header value has been delegated to JAX-RS runtime.
     */
    @Override
    public long getSize(Object o, Class aClass, Type type, Annotation[] annotations, MediaType mediaType) {
        return -1;
    }

    @Override
    public void writeTo(Object o, Class aClass, Type type, Annotation[] annotations, MediaType mediaType, MultivaluedMap multivaluedMap, OutputStream outputStream) throws IOException, WebApplicationException {
        Customer customer;

        if(o instanceof Customer)
            customer = (Customer) o;
        else
            throw new WebApplicationException();

        final JSONObject customerJSON = new JSONObject();
        customerJSON.append("id", customer.getId());
        customerJSON.append("first_name", customer.getFirstName());
        customerJSON.append("last_name", customer.getLastName());
        customerJSON.append("email", customer.getEmail());

        outputStream.write(customerJSON.toString(4).getBytes());
        outputStream.close();
    }
}
