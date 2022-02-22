package org.my.springDemo.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.my.springDemo.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public class CustomerDAOImpl implements CustomerDAO {

    //injecting the session factory
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<Customer> getCustomers() {
        //get the current hibernate session
        Session session = sessionFactory.getCurrentSession();
        //create a query ... sort by last name
        Query<Customer> theQuery =
                session.createQuery("from Customer order by lastName",
                        Customer.class);
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
}
