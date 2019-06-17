package com.arvato.cc.dao1.impl;

import com.arvato.cc.dao1.JdbcTemplateExtend;
import com.arvato.cc.dao1.ResourceDao;
import com.arvato.cc.model.Resource;
import com.arvato.jdf.dao.HibernateDao;
import com.arvato.jdf.dao.Page;
import com.arvato.jdf.dao.PropertyFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: pang008
 * Date: 10/10/12
 * Time: 17:24
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class ResourceDaoImpl extends HibernateDao<Resource, Integer> implements ResourceDao {

    @Autowired
    private JdbcTemplateExtend jdbcTemplateExtend;

    public void saveOrUpdate(Resource resource) {
        getSession().saveOrUpdate(resource);
    }

    public Page<Resource> findPropertyFilter(Page page, PropertyFilter propertyFilter) {
        return super.findByPropertyFilter(page, propertyFilter);  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Page<Resource> find(Page page) {
        return this.find(page, "from Resource");  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void saveOmsResource(Resource ccResource) {
//		this.getHibernateTemplate().flush();
        super.save(ccResource);
//		this.save(omsResource);
    }

    @SuppressWarnings("unchecked")
    public List<Resource> getOmsResourceByResourcePattern(
            String resourcePattern) {
        return this.find("from Resource where resourcePattern = ?", new String[]{resourcePattern});
    }

    @SuppressWarnings("unchecked")
    public List<Resource> getOmsResourceByResourceSystemAndPattern(
            String resourcePattern, String subSystemName) {
        return this.find("from Resource where resourcePattern = ?", new String[]{resourcePattern});
    }

    @SuppressWarnings("unchecked")
    public List<Resource> getOmsResourceByResourceName(String resourceName) {
        return this.find("from Resource where resourceName = ?", new String[]{resourceName});
    }


    public Resource get(Integer resourceSysId) {
        return super.get(resourceSysId);
    }

    public List<Resource> findAll() {
        String HQL = "from Resource where resourceStatus=1";
        return super.find(HQL);
    }

    public List<Resource> findByParentId(String parentId) {
        return find("from Resource where parentResourceSysId=? and resourceStatus=?", parentId, "1");
    }

    public List<Resource> findByUserId(String userId) {
        String hql = "select resource_sys_id,parent_resource_sys_id,resource_name,resource_pattern from resource where resource_sys_id in (" +
                "select resource_sys_id from role_resource where role_sys_id in (" +
                "select role_sys_id from user_role a,user b where user_id='" + userId + "' and a.user_sys_id=b.user_sys_id))" +
                " and resource_code != '"+"all'";
        List resourceList = jdbcTemplateExtend.query(hql);
        List<Resource> resources = new ArrayList<Resource>();
        if (null != resourceList && resourceList.size() > 0) {
            for (int i = 0; i < resourceList.size(); i++) {
                Map<String, Object> resourceMap = (Map<String, Object>) resourceList.get(i);
                Integer resourceSysId = (Integer)resourceMap.get("resource_sys_id");
                String parentResourceSysId = (String) resourceMap.get("parent_resource_sys_id");
                String resourceName = (String) resourceMap.get("resource_name");
                String resourcePattern = (String) resourceMap.get("resource_pattern");
                Resource resourceObj = new Resource();
                resourceObj.setResourceSysId(resourceSysId);
                resourceObj.setParentResourceSysId(parentResourceSysId);
                resourceObj.setResourceName(resourceName);
                resourceObj.setResourcePattern(resourcePattern);
                resources.add(resourceObj);
            }
        }
        return resources;
    }

    public Long countByParentId(String parentId) {

        return countHqlResult("from Resource where parentResourceSysId=? and resourceStatus=?", parentId, "1");
    }
}
