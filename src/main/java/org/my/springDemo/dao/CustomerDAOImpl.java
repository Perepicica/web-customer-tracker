package org.my.springDemo.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.my.springDemo.entity.Customer;
import org.my.springDemo.util.SortUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CustomerDAOImpl implements CustomerDAO {

    //injecting the session factory
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<Customer> getCustomers(int theSortField) {
        //get the current hibernate session
        Session session = sessionFactory.getCurrentSession();
        // determine sort field
        String theFieldName;
        switch (theSortField) {
            case SortUtils.FIRST_NAME:
                theFieldName = "firstName";
                break;
            case SortUtils.LAST_NAME:
                theFieldName = "lastName";
                break;
            case SortUtils.EMAIL:
                theFieldName = "email";
                break;
            default:
                // if nothing matches the default to sort by lastName
                theFieldName = "lastName";
        }
        //create a query ... sort by last name
        Query<Customer> theQuery =
                session.createQuery("from Customer order by "+ theFieldName, Customer.class);
        //execute query and get result list
        return theQuery.getResultList();
    }

    @Override
    public void saveCustomer(Customer theCustomer) {
        //get the current hibernate session
        Session session = sessionFactory.getCurrentSession();
        //save/update the customer
        session.saveOrUpdate(theCustomer);
    }

    @Override
    public Customer getCustomer(int id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Customer.class,id);
    }

    @Override
    public void deleteCustomer(int id) {
        Session session = sessionFactory.getCurrentSession();
        Query theQuery =
                session.createQuery("delete from Customer where id=:customerId");
        theQuery.setParameter("customerId",id);
        theQuery.executeUpdate();
    }

    @Override
    public List<Customer> searchCustomers(String theSearchName) {
        Session session = sessionFactory.getCurrentSession();
        Query theQuery;

        // only search by name if theSearchName is not empty
        if (theSearchName != null && theSearchName.trim().length() > 0) {
            // search for firstName or lastName ... case insensitive
            theQuery = session.createQuery("from Customer where lower(firstName) like :theName " +
                    "or lower(lastName) like :theName", Customer.class);
            theQuery.setParameter("theName", "%" + theSearchName.toLowerCase() + "%");
        } else {
            // theSearchName is empty ... so just get all customers
            theQuery = session.createQuery("from Customer", Customer.class);
        }
        return theQuery.getResultList();
    }
}
