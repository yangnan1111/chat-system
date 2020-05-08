package com.hibernate;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateSessionFactory {
    //实例化ThreadLocal类
    private static final ThreadLocal<Session> threadLocal = new ThreadLocal<Session>();
    //实例化Configuration类
    private  static Configuration configuration = new Configuration();
    //声明SessionFactory接口,采用工厂模式
    private static SessionFactory sessionFactory;
    //定义configFile变量并赋值
    private static String configFile = "/hibernate.cfg.xml";
    //静态块
    static {
        try {
            //读取默认的配置文件hibernate.cfg.xml, 连接数据库
            configuration.configure(configFile);
            //实例化SessionFactory
            sessionFactory = configuration.buildSessionFactory();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private HibernateSessionFactory() { }//创建无参的HibernateSessionFactory构造函数
    //获取Session
    public static Session getSession() throws HibernateException {
        Session session = (Session) threadLocal.get();
        //判断是否已经存在Session对象
        if (session == null || !session.isOpen()) {
            //如果SessionFactory对象为null，则创建SessionFactory
            if (sessionFactory == null) {
                rebuildSessionFactory();//调用rebuildSessionFactory方法创建SessionFactory
            }
            //判断SessionFactory对象是否为null，如果不是，则打开Session
            session = (sessionFactory != null) ? sessionFactory.openSession(): null;
            threadLocal.set(session);
        }

        return session;
    }
    //重新创建SessionFactory
    public static void rebuildSessionFactory() {
        try {
            configuration.configure(configFile);
            sessionFactory = configuration.buildSessionFactory();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //关闭Session
    public static void closeSession() throws HibernateException {
        Session session = (Session) threadLocal.get();
        threadLocal.set(null);
        if (session != null) {
            session.close();
        }
    }
    //SessionFactory对象的getXXX()方法
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
    //configFile属性的setXXX()方法
    public static void setConfigFile(String configFile) {
        HibernateSessionFactory.configFile = configFile;
        sessionFactory = null;
    }
    //Configuration属性的getXXX()方法
    public static Configuration getConfiguration() {
        return configuration;
    }
}
