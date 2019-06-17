package com.arvato.cc.dao1;

import com.arvato.jdf.dao.Page;

import java.util.List;

public interface ObjectDao {
	Page<Object[]> findObj(Page page,String hql);
	public List<Object[]> findObj(String hql);
}
