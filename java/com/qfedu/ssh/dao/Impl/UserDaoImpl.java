package com.qfedu.ssh.dao.Impl;


import com.qfedu.ssh.bean.User;
import com.qfedu.ssh.dao.UserDao;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Repository("userDao")
public class UserDaoImpl extends HibernateDaoSupport implements UserDao {
    /*
     * 注入sessionFactoy
     * */
    @Resource(name = "sessionFactory")
    public void injectSessionFactory(SessionFactory sessionFactory) {
        super.setSessionFactory(sessionFactory);

    }

    @Override
    public User selectUserByLoginInfo(String loginInfo, String password) {
        //loginInfo有可能是username或者是email或者是telephone
        //暂时当做username去使用
        List<?> list = getHibernateTemplate().find("from com.qfedu.ssh.bean.User where (username=? or email=? or telephone=?) and password=?", loginInfo, loginInfo, loginInfo, password);

        if (list != null && list.size() > 0) {
            return (User) list.get(0);
        } else {
            return null;
        }

    }

    @Override
    public void insertIntoUser(User user) {

        Date data = new Date();
        //创建日期格式化对象(把日期转成字符串)
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-s");
        String str = sdf.format(data);
        user.setRegistertime("" + str);

        getHibernateTemplate().save(user);
    }

    @Override
    public boolean selectUsername(String username) {
        List<?> list = getHibernateTemplate().find("from com.qfedu.ssh.bean.User where username=?", username);
        if ((list) != null && list.size() > 0) {
            return true;
        }
        return false;
    }

    @Override
    public boolean selectEmail(String email) {
        List<?> list = getHibernateTemplate().find("from com.qfedu.ssh.bean.User where email=? ", email);
        if ((list) != null && list.size() > 0) {
            return true;
        }

        return false;
    }

    @Override
    public boolean selectTelephone(String telephone) {
        List<?> list = getHibernateTemplate().find("from com.qfedu.ssh.bean.User where telephone=?", telephone);
        if ((list) != null && list.size() > 0) {
            return true;
        }

        return false;
    }

    @Override
    public void updateUser(User user) {
      getHibernateTemplate().update(user);
    }


}
