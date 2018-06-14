package com.qfedu.ssh.dao.Impl;

import com.qfedu.ssh.bean.Product;
import com.qfedu.ssh.dao.ProductDao;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.springframework.orm.hibernate5.HibernateCallback;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

@Repository("productDao")
public class ProductDaoImpl extends HibernateDaoSupport implements ProductDao {

    @Resource(name="sessionFactory")
    public void injectSessionFactory(SessionFactory sessionFactory){
        super.setSessionFactory(sessionFactory);
    }


    @Override
    public void insertToProduct(Product product) {
        getHibernateTemplate().save(product);
    }

    @Override
    public void deleteToProduct(Product product) {
        getHibernateTemplate().delete(product);
    }


    @Override
    public Product selectOneById(Integer id) {
        return getHibernateTemplate().get(Product.class,id);
    }

    @Override
    public void updateProduct(Product product) {
        getHibernateTemplate().update(product);
    }

    @Override
    public List<Product> selectProductByPage(int pageNo, int pageSize, String category) {
        //select * from product limit 0,10
        //模糊查询  _  %
        //查询姓张的人，已知姓张的没有复姓
        // name like '张_%'
        //要查询的是Product
        DetachedCriteria dc=DetachedCriteria.forClass(Product.class);
        dc.add(Restrictions.like("category","%"+category+"%"));
        List<Product> list= (List<Product>) getHibernateTemplate().findByCriteria(dc,(pageNo-1)*pageSize,pageSize);
        return list;
    }


    @Override
    public long selectProductCount(String category) {

       Long count=getHibernateTemplate().execute(new HibernateCallback<Long>() {
           @Override
           public Long doInHibernate(Session session) throws HibernateException {
               Query<?> query=session.createQuery("select count(id) from Product where category like ?");
               query.setParameter(0,"%"+category+"%");
               List<?> list=query.list();
               long count=(Long)list.get(0);
               return count;
           }
       }) ;
        return count;
    }
}
