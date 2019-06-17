package com.arvato.cc.dao1.impl;

import com.arvato.cc.dao1.SequenceDao;
import com.arvato.jdf.dao.HibernateDao;
import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Vector;

/**
 * Created with IntelliJ IDEA.
 * User: pang008
 * Date: 11/10/12
 * Time: 18:00
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class SequenceDaoImpl extends HibernateDao implements SequenceDao {
    public Vector<Integer> generate(int capacity, String seqName) {
        Vector<Integer> list = new Vector<Integer>(capacity);
        for (int i = 0; i < capacity; i++) {
            list.add(generate(seqName));
        }
        return list;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Integer generate(String seqName) {
        SQLQuery sqlQuery = getSession().createSQLQuery("select " + seqName + ".NEXTVAL from dual");
        List result = sqlQuery.list();
        Integer no = 0;
        if (null != result) {
            BigDecimal seqNo = (BigDecimal) result.get(0);
            no = seqNo.intValue();
        }
        return no;
    }
}
