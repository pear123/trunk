package com.arvato.cc.dao1;

import com.arvato.cc.model.Resource;
import com.arvato.jdf.dao.Page;
import com.arvato.jdf.dao.PropertyFilter;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: pang008
 * Date: 10/10/12
 * Time: 17:19
 * To change this template use File | Settings | File Templates.
 */
public interface ResourceDao {
    void saveOrUpdate(Resource resource);

    Resource save(Resource resource);

    Page<Resource> findPropertyFilter(Page page, PropertyFilter propertyFilter);

    Page<Resource> find(Page page);

    public void saveOmsResource(Resource omsResource);

    public List<Resource> getOmsResourceByResourcePattern(String resourcePattern);

    public List<Resource> getOmsResourceByResourceSystemAndPattern(String resourcePattern, String subSystemName);

    public List<Resource> getOmsResourceByResourceName(String resourceName);

    public Resource get(Integer resourceSysId);

    public List<Resource> findAll();

    List<Resource> findByParentId(String parentId);

    List<Resource> findByUserId(String hql);

    Long countByParentId(String parentId);

    void delete(Integer sysId);
}
