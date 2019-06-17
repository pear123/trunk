package com.arvato.cc.service1;

import com.arvato.cc.model.CResource;
import com.arvato.cc.model.Resource;
import com.arvato.cc.util.ExtTreeNode;
import com.arvato.cc.util.ExtTreeNodeSingle;
import com.arvato.jdf.dao.Page;
import com.arvato.jdf.dao.PropertyFilter;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: pang008
 * Date: 10/10/12
 * Time: 14:34
 * To change this template use File | Settings | File Templates.
 */
public interface ResourceService {
    Resource saveOrUpdate(Resource resource);

    Resource save(Resource resource);

    Page<Resource> findResourcePropertyFilter(Page page, PropertyFilter propertyFilter);

    Page<Resource> findResource(Page page);

    String saveOmsResource(Resource omsResource, String userName);

    Resource selectResourceByIdToJson(Integer id);

    List<Resource> selectOmsResource();

    List<Resource> selectResourceToJson(List<Resource> oResourceAfter);

    List<ExtTreeNode> buildResourceTreeByParentId(String parentId);


    List<ExtTreeNodeSingle> buildResourceTreeByParentIdSingle(String parentId);
    /**
     * check current resource that is whether reference by role or not.
     *
     * @param sysId
     * @return
     */
    String checkResourceIsReferenceByRole(Integer sysId);

    Boolean remove(Integer sysId);

    List<CResource> getAllMenus(String userId);
}
