package org.my.springDemo.dao;

import org.my.springDemo.entity.Customer;

import java.util.List;

public interface CustomerDAO {
    List<Customer> getCustomers(int theSortField);

    void saveCustomer(Customer theCustomer);

    Customer getCustomer(int id);

    void deleteCustomer(int id);

    List<Customer> searchCustomers(String theSearchName);
}