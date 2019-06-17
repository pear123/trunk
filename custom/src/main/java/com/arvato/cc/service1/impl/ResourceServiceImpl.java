package com.arvato.cc.service1.impl;

import com.arvato.cc.dao1.ResourceDao;
import com.arvato.cc.dao1.RoleResourceDao;
import com.arvato.cc.model.CResource;
import com.arvato.cc.model.Resource;
import com.arvato.cc.model.RoleResource;
import com.arvato.cc.service1.ResourceService;
import com.arvato.cc.util.CommonHelper;
import com.arvato.cc.util.ExtTreeNode;
import com.arvato.cc.util.ExtTreeNodeSingle;
import com.arvato.cc.util.Validate;
import com.arvato.jdf.dao.Page;
import com.arvato.jdf.dao.PropertyFilter;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: pang008
 * Date: 10/10/12
 * Time: 14:35
 * To change this template use File | Settings | File Templates.
 */
@Service
public class ResourceServiceImpl implements ResourceService {
    @Autowired
    private ResourceDao resourceDao;

    @Autowired
    private RoleResourceDao roleResourceDao;

    public Resource saveOrUpdate(Resource resource) {
        Integer sysId = 0;
        if (resource.getResourceSysId() != null) {
            sysId = resource.getResourceSysId();
        }

        Resource omsResource = null;
        if (sysId > 0) {
            omsResource = resourceDao.get(sysId);
            if (null != omsResource) {
                omsResource.setResourceName(resource.getResourceName());
                omsResource.setResourceType(resource.getResourceType());
                omsResource.setResourcePattern(resource.getResourcePattern());
                omsResource.setResourceCode(resource.getResourceCode());
                omsResource.setResourceDescription(resource.getResourceDescription());
                omsResource.setResourceStatus(resource.getResourceStatus());
                omsResource.setUpdateOp(resource.getCreateOp());
                omsResource.setUpdateTime(CommonHelper.getThisTimestamp());
            } else {
                omsResource = resource;
            }
        } else {
            omsResource = resource;
        }

//        if (!StringUtils.isBlank(resource.getOmsParentResourceSysId())) {
//            omsResource.setOmsResource(resourceDao.get(resource.getOmsParentResourceSysId()));
//        }

        return resourceDao.save(omsResource);
    }

    public Resource save(Resource resource) {
        return resourceDao.save(resource);  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Page<Resource> findResourcePropertyFilter(Page page, PropertyFilter propertyFilter) {
        return resourceDao.findPropertyFilter(page, propertyFilter);  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Page<Resource> findResource(Page page) {
        return resourceDao.find(page);  //To change body of implemented methods use File | Settings | File Templates.
    }

    /*
     * 根据条件查询资源
	 */
    public List<Resource> selectOmsResource() {
        List<Resource> list = resourceDao.findAll();
        return list;
    }

    /**
     * 资源信息列表
     * kobs
     */
    public List<Resource> selectResourceToJson(List<Resource> omsResourceList) {
        List<Resource> resourceFormList = new ArrayList<Resource>();
        for (Resource or : omsResourceList) {
            Resource r = new Resource();
            r.setResourceDescription(or.getResourceDescription());
            r.setResourceName(or.getResourceName());
            r.setResourcePattern(or.getResourcePattern());
//			r.setOmsResourceStatus(or.getOmsResourceStatus());
            r.setResourceSysId(or.getResourceSysId());
            if (or.getResourceStatus().equals("0")) {
//                r.setResourceStatus("禁用");
            } else {
//                r.setResourceStatus("启用");
            }
            resourceFormList.add(r);
        }
        return resourceFormList;
    }

    public List<ExtTreeNode> buildResourceTreeByParentId(String parentId) {
        List<ExtTreeNode> result = null;
        List<Resource> resourceList = resourceDao.findByParentId(parentId);
        if (null != resourceList && resourceList.size() > 0) {
            result = new ArrayList<ExtTreeNode>(resourceList.size());
            ExtTreeNode treeNode = null;
            for (Resource resource : resourceList) {
                treeNode = resource.convertTreeNode();
                if (resourceDao.countByParentId(treeNode.getId()) > 0L) {
                    treeNode.setExpanded(true);
                    treeNode.setLeaf(false);
                } else {
                    treeNode.setExpanded(false);
                    treeNode.setLeaf(true);
                }
                result.add(treeNode);
            }
        }
        return result;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public List<CResource> getAllMenus(String userName) {
        List<CResource> cResourceList = new ArrayList<CResource>();
        List<Resource> resourceList = resourceDao.findByUserId(userName);
        //根据username得到user_sys_id
        if (!CollectionUtils.isEmpty(resourceList)) {
            //取出parentId为0的id listLis
            List<CResource> parentResourcesList = new ArrayList<CResource>();
            Map<Integer, List<Resource>> subResourcesMap = new HashMap<Integer, List<Resource>>();

            for (Resource resource : resourceList) {
                if ("0".equals(resource.getParentResourceSysId())) {
                    CResource cResource = new CResource();
                    cResource.setResourceSysId(resource.getResourceSysId());
                    cResource.setResourceName(resource.getResourceName());
                    cResource.setParentResourceSysId(resource.getParentResourceSysId());
                    cResource.setResourcePattern(resource.getResourcePattern());
                    cResource.setResourceCode(resource.getResourceCode());
                    parentResourcesList.add(cResource);
                }
            }
            for (CResource cResource : parentResourcesList) {
                List<Resource> subResourceList = new ArrayList<Resource>();
                for (Resource resource : resourceList) {
                    if (resource.getParentResourceSysId().equals(cResource.getResourceSysId().toString())) {
                        subResourceList.add(resource);
                    }
                }
                subResourcesMap.put(cResource.getResourceSysId(), subResourceList);
            }
            if (!CollectionUtils.isEmpty(parentResourcesList)) {
                for (CResource cResourceObj : parentResourcesList) {
                    //根据parentid获取子模块
                    Integer subResourceId = cResourceObj.getResourceSysId();
                    cResourceObj.setSubResourceNodes(subResourcesMap.get(subResourceId));
                    cResourceList.add(cResourceObj);
                }
            }
        }
        return cResourceList;
    }

    public List<ExtTreeNodeSingle> buildResourceTreeByParentIdSingle(String parentId) {
        List<ExtTreeNodeSingle> result = null;
        List<Resource> resourceList = resourceDao.findByParentId(parentId);
        if (null != resourceList && resourceList.size() > 0) {
            result = new ArrayList<ExtTreeNodeSingle>(resourceList.size());
            ExtTreeNodeSingle treeNode = null;
            for (Resource resource : resourceList) {
                if ("useroperationmanage".equals(resource.getResourceCode())) {
                    continue;
                }
                treeNode = resource.convertTreeNodeSingle();
                if (resourceDao.countByParentId(treeNode.getId()) > 0L) {
                    treeNode.setExpanded(true);
                    treeNode.setLeaf(false);
                } else {
                    treeNode.setExpanded(false);
                    treeNode.setLeaf(true);
                }
                result.add(treeNode);
            }
        }
        return result;  //To change body of implemented methods use File | Settings | File Templates.
    }


    public String checkResourceIsReferenceByRole(Integer sysId) {
        Resource resource = resourceDao.get(sysId);
        if (null == resource) {
            return "SUCCESS";
        }
        Set<RoleResource> roleResourceSet = resource.getRoleResources();
        if (null == roleResourceSet || roleResourceSet.size() == 0) {
            return "SUCCESS";
        }

        StringBuffer sb = new StringBuffer(roleResourceSet.size());
        for (RoleResource roleResource : roleResourceSet) {
            sb.append(roleResource.getRole().getRoleName());
            sb.append("<br/>");
        }

        return sb.toString();  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Boolean remove(Integer sysId) {
        roleResourceDao.deleteByResource(sysId);
        resourceDao.delete(sysId);
        return true;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /*
     * 添加资源
	 */
    public String saveOmsResource(Resource ccResource, String userName) {
        String result = "success";
        //如果Id不为空，则为修改
        List<Resource> ors_name = resourceDao.getOmsResourceByResourceName(ccResource.getResourceName().trim());
//		List<OmsResource> ors_pattern = resourceDao.getOmsResourceByResourcePattern(omsResource.getOmsResourcePattern().trim());
        List<Resource> ors_patternAndSystem = resourceDao.getOmsResourceByResourceSystemAndPattern(ccResource.getResourcePattern(), null);
        if (!Validate.isNullOrEmpty(ccResource.getResourceSysId())) {
            //根据资源名和Id一起查找能找到，说明是当前对象本身（没有对resourceName进行修改）
            if (ors_name.size() > 0) {//数据库中有相同的名字
                if (ors_name.get(0).getResourceSysId() != ccResource.getResourceSysId()) {//如果名字相同但是Id却不相同说明不知当前修改的对象，报名字重复错误
                    return "resourceNameError";
                } else { //名字没有修改的情况
                    updateOmsResource(ccResource, userName);
                }
            }
            if (ors_patternAndSystem.size() > 0) { //没有重复的名字提交
                if (ors_patternAndSystem.get(0).getResourceSysId() != ccResource.getResourceSysId()) {//如果名字相同但是Id却不相同说明不知当前修改的对象，报名字重复错误
                    return "resourcePatternAndSystemError";
                } else { //名字没有修改的情况
                    updateOmsResource(ccResource, userName);
                }
            }
            updateOmsResource(ccResource, userName);
        } else {//否则为添加
            //根据资源名查找时候有重复
            if (ors_name.size() > 0)
                return "resourceNameError";
            if (ors_patternAndSystem.size() > 0)
                return "resourcePatternAndSystemError";

            ccResource.setCreateTime(CommonHelper.getThisTimestamp());
            ccResource.setUpdateTime(CommonHelper.getThisTimestamp());
            ccResource.setUpdateOp(userName);
            ccResource.setCreateOp(userName);
            ccResource.setVersion(0);
            this.resourceDao.saveOmsResource(ccResource); // 不重复就添加
        }
        return result;
    }

    /*
      * 执行修改操作
      */
    private void updateOmsResource(Resource ccResource, String userName) {
        Resource cc = this.resourceDao.get(ccResource.getResourceSysId());  //根据id得到数据库中的原始数据
        cc.setUpdateOp(userName);
        cc.setResourceName(ccResource.getResourceName());  //给对象的属性进行赋值
        cc.setResourceDescription(ccResource.getResourceDescription());
        cc.setResourcePattern(ccResource.getResourcePattern());
        cc.setResourceStatus(ccResource.getResourceStatus());
        cc.setUpdateTime(CommonHelper.getThisTimestamp());
        this.resourceDao.save(cc);   //执行保存
    }


    public Resource selectResourceByIdToJson(Integer id) {
        Resource or = this.resourceDao.get(id);
        Resource r = new Resource();
        r.setResourceDescription(or.getResourceDescription());
        r.setResourceName(or.getResourceName());
        r.setResourcePattern(or.getResourcePattern());
        r.setResourceStatus(or.getResourceStatus());
        r.setResourceSysId(or.getResourceSysId());
        return r;
    }


}
