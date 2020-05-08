package com.hibernate;

import com.hibernate.pojo.Product;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class ProductDao {

        //验证登录
        public boolean checkLogin(Product product){
            boolean flag=true;
            //得到session
            Session session=null;
            try{
                session=HibernateSessionFactory.getSession();
                //hql语句,Users代表是models里的实体类,name和password代表实体类的属性
                String queryString="from Product where name=? and data=?";
                //创建查询
                Query queryObject=session.createQuery(queryString);
                queryObject.setParameter(0, product.getName());
                queryObject.setParameter(1, product.getData());
                //执行查询获得的结果,list中的每一个元素代表一个Users的对象
                List list=queryObject.list();
                if(list.size()==0){
                    flag=false;//登陆不成功
                }
                return flag;
            }catch (Exception e) {
                e.printStackTrace();
                return false;
            }finally{//关闭session
                HibernateSessionFactory.closeSession();//调用HibernateSessionFactory的静态方法关闭Session
            }
        }
        //注册用户的方法
//        public int registerUser(Users user){
//            int num=0;//标识注册是否成功,0表示不成功,>0成功
//            //得到session
//            Session session=null;
//            Transaction transaction=null;
//            try{
//                session=HibernateSessionFactory.getSession();
//                transaction=session.beginTransaction();
//                num=Integer.parseInt(session.save(user).toString());
//                transaction.commit(); //写入数据库，
//            }catch (Exception e) {
//                e.printStackTrace();
//                num=0;
//            }finally{//关闭session
//                HibernateSessionFactory.closeSession();//调用HibernateSessionFactory的静态方法关闭Session
//            }
//            return num;
//        }
    }
