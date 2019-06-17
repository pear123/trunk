package com.arvato.cc.dao1.impl;

import com.arvato.cc.dao1.CurrentOperationDao;
import com.arvato.cc.model.CurrentOperation;
import com.arvato.jdf.dao.HibernateDao;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: caol005
 * Date: 15-8-10
 * Time: 下午12:24
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class CurrentOperationDaoImpl extends HibernateDao<CurrentOperation, Integer> implements CurrentOperationDao {

    public List<CurrentOperation> getByModel(String modelName) {
        return super.find(" from CurrentOperation co where co.model = ?",modelName);
    }

    @Override
    public List<CurrentOperation> getByModel2(String modelName) {
        return super.find(" from CurrentOperation co where co.model = ? and subModel in ('modify','delete')",modelName);
    }

    @Override
    public List<CurrentOperation> getByModelBySysId(String modelName, String sysId) {
        return super.find(" from CurrentOperation co where co.model = ? and sysId=?",new Object[]{modelName,sysId});
    }

    @Override
    public List<CurrentOperation> getByModel(String modelName, String subModel) {
        return super.find(" from CurrentOperation co where co.model = ? and co.subModel=?",new Object[]{modelName,subModel});
    }

    @Override
    public List<CurrentOperation> getByModelByUser(String modelName, String userName) {
        return super.find(" from CurrentOperation co where co.model = ? and co.operateOp=?",new Object[]{modelName,userName});
    }


    public void delete(String modelName) {
        super.getSession().createSQLQuery("delete from current_operation where model = '"+modelName+"'").executeUpdate();
    }

    @Override
    public void deleteBySysId(String modelName, String sysId) {
        super.getSession().createSQLQuery("delete from current_operation where model = '"+modelName+"' and sys_Id='"+sysId+"'").executeUpdate();
    }

    @Override
    public void delete(String modelName, String userName) {
        super.getSession().createSQLQuery("delete from current_operation where model = '"+modelName+"' and operate_op='"+userName+"'").executeUpdate();
    }

    @Override
    public void deleteBySubModel(String modelName, String subModel) {
        super.getSession().createSQLQuery("delete from current_operation where model = '"+modelName+"' and sub_model='"+subModel+"'").executeUpdate();
    }

    public void saveCurrentOperation(CurrentOperation currentOperation) {
        super.save(currentOperation);
    }

    @Override
    public List<CurrentOperation> findAllByN() {
        String hql = "from CurrentOperation cp where cp.subModel = 'N'";
       return super.find(hql);
    }

}
