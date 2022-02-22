package org.my.springDemo.dao;

import org.my.springDemo.entity.Customer;

import java.util.List;

public interface CustomerDAO {
    List<Customer> getCustomers();
}
