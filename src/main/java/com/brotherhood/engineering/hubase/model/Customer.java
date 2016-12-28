package com.brotherhood.engineering.hubase.model;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Palamayuran on 20/12/2016.
 */
public class Customer implements Serializable{

    private static final AtomicInteger idGen;
    private static MessageDigest sha256Digest;
    private static final Map<Integer, Customer> customers;

    static{
        idGen = new AtomicInteger(1);
        customers = new LinkedHashMap<>();
        try {
            sha256Digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    private final int id;
    private String firstName;
    private String lastName;
    private final String email;
    private byte[] password;

    public Customer(String email, String firstName, String lastName) {
        this.id = idGen.getAndIncrement();
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        customers.put(this.id, this);
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public byte[] getPassword() {
        return password;
    }

    /**
     * Only the SHA-256 digest of the password is saved in the system.
     * @param password
     */
    public void setPassword(String password) {
        sha256Digest.update(password.getBytes());
        byte[] sha256PasswordDigest = sha256Digest.digest();
        this.password = sha256PasswordDigest;
    }

    public String getEmail() {
        return email;
    }

    public static Map<Integer, Customer> getCustomers() {
        return customers;
    }
}
