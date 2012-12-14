/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.java.web.core.broker;

import com.java.web.core.domain.access.BaseEntity;
import com.java.web.core.domain.access.Product;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.naming.NamingException;
import org.hibernate.Criteria;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Marija
 */
public abstract class AbstractBroker {
    
    private static String testHibernateConfigurationLocation;
    public abstract SSessionUtil getSessionUtil();
    /**
     * @return the testHibernateConfigurationLocation
     */
    public static String getTestHibernateConfigurationLocation() {
        return testHibernateConfigurationLocation;
    }

    /**
     * @param aTestHibernateConfigurationLocation the testHibernateConfigurationLocation to set
     */
    public static void setTestHibernateConfigurationLocation(String aTestHibernateConfigurationLocation) {
        testHibernateConfigurationLocation = aTestHibernateConfigurationLocation;
    }
    protected ThreadLocal session;

    public static SessionFactory factory;
   
    protected AbstractBroker() {
        session = new ThreadLocal();
    }

    public boolean openMasterSessionIfNeeded()
            throws NamingException {
        boolean isOpened = this.getSessionUtil().getCurrentSession() == null;
        if (isOpened) {
            this.getSessionUtil().setSession(retreiveMasterSession());
        }
        return isOpened;
    }

    public boolean openSessionIfNeeded() {
        boolean isOpened = this.getSessionUtil().getCurrentSession() == null;
        if (isOpened) {
            this.getSessionUtil().setSession(retreiveSession());
        }
        return isOpened;
    }

    public abstract Session retreiveSession();

    public abstract Session retreiveMasterSession();

    public void closeSession() {
        this.getSessionUtil().closeSession();
    }

    public void beginTransaction() {
        this.getSessionUtil().getCurrentSession().beginTransaction();
    }

    public void commitTransaction() {
        this.getSessionUtil().getCurrentSession().getTransaction().commit();
    }

    public void rollbackTransaction() {
        this.getSessionUtil().getCurrentSession().getTransaction().rollback();
    }

    public Product getProductInstanceFromDB(String name) {
        Product result = null;
        Query query = this.getSessionUtil().getCurrentSession().createQuery("from com.java.web.core.domain.access.Product as p where p.url like :name ");
        query.setString("name", name);
        List list = query.list();
        if (!list.isEmpty()) {
            result = (Product) list.get(0);
        }
        return result;
    }

     protected  void buildTestFactory() {
        if (factory == null) {
            File file = new File(getTestHibernateConfigurationLocation());
            factory = new Configuration().configure(file).buildSessionFactory();
            this.getSessionUtil().setSession(factory.openSession());
        }
        return;
    }
     public void attachClean(BaseEntity instance) {
        this.getSessionUtil().getCurrentSession().lock(instance, LockMode.NONE);
    }

    public void attachDirty(BaseEntity instance) {
        BaseEntity mergedInstance = this.merge(instance);
        this.getSessionUtil().getCurrentSession().saveOrUpdate(mergedInstance);
    }

    public void delete(BaseEntity persistentInstance) {
        BaseEntity mergedInstance = this.merge(persistentInstance);
        this.getSessionUtil().getCurrentSession().delete(mergedInstance);
    }

    public List<BaseEntity> findEntitiesBasedOnCriteria(HashMap<String,Criteria> criteria,String rootCriterionName){
        //Re implement this method to add aditional metamodel restrictions to each criterion
        Criteria rootCriteria = criteria.get(rootCriterionName);
        List<BaseEntity> result = rootCriteria.list();
        return result;
    }

    public BaseEntity findById(String klasa, Object id) {
        BaseEntity instance = (BaseEntity) this.getSessionUtil().getCurrentSession().get(klasa, (Serializable) id);
        return instance;
    }

    public BaseEntity merge(BaseEntity detachedInstance) {
        BaseEntity result = (BaseEntity) this.getSessionUtil().getCurrentSession().merge(detachedInstance);
        return result;
    }

    public void persist(BaseEntity transientInstance) {
        this.getSessionUtil().getCurrentSession().persist(transientInstance);
    }
}
