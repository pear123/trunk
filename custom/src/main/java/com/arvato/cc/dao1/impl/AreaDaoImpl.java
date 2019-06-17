package com.arvato.cc.dao1.impl;

import com.arvato.cc.dao1.AreaDao;
import com.arvato.cc.model.Area;
import com.arvato.jdf.dao.HibernateDao;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: caol005
 * Date: 15-9-8
 * Time: 下午6:33
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class AreaDaoImpl extends HibernateDao<Area, Integer> implements AreaDao {
    @Override
    public Area findByCode(String code) {
        List<Area> list = super.find(" from Area where oname = ?",code);
        return (null == list || list.isEmpty()) ? null:list.get(0);  //To change body of implemented methods use File | Settings | File Templates.
    }
}
