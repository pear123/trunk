package com.arvato.cc.dao1.impl;

import com.arvato.cc.dao1.ObjectDao;
import com.arvato.jdf.dao.HibernateDao;
import com.arvato.jdf.dao.Page;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class ObjectDaoImpl extends HibernateDao<Object[], Integer> implements ObjectDao {
	public Page<Object[]> findObj(Page page,String hql) {
		return super.find(page,hql);
	}

	public List<Object[]> findObj(String hql) {
		return super.find(hql);
	}
}
