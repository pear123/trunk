package com.arvato.cc.dao1.impl;

import com.arvato.cc.dao1.AreaDao;
import com.arvato.cc.dao1.JdbcTemplateExtend;
import com.arvato.cc.dao1.StoreDao;
import com.arvato.cc.model.Area;
import com.arvato.cc.model.Store;
import com.arvato.cc.util.Validate;
import com.arvato.jdf.dao.HibernateDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: caol005
 * Date: 15-9-8
 * Time: 下午6:33
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class StoreDaoImpl extends HibernateDao<Store, Integer> implements StoreDao {


    @Autowired
    private JdbcTemplateExtend jdbcTemplateExtend;


    @Override
    public String findByCode(String code) {
        String sql = "select name from store,area where store.parent_store = oname and store.order_store = '"+code+"'";
        List<Map<String,Object>> list = jdbcTemplateExtend.query(sql);
        Map<String,Object> map = (null == list || list.isEmpty()) ? null:list.get(0);
        if(!Validate.isNullOrEmpty(map)){
            return map.get("name").toString();
        }
        return "";  //To change body of implemented methods use File | Settings | File Templates.
    }
}
