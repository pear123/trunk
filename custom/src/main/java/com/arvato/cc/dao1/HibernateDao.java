/**
 * Copyright (c) 2000-2010 arvato systems (Shanghai)
 *
 * $Id: HibernateDao.java 2011-3-7 10:12:00 Justin $
 */
package com.arvato.cc.dao1;

import com.arvato.jdf.dao.Page;
import com.arvato.jdf.dao.Property;
import com.arvato.jdf.dao.Property.MatchType;
import com.arvato.jdf.dao.PropertyFilter;
import com.arvato.jdf.dao.PropertyFilter.JunctionType;
import com.arvato.jdf.dao.PropertyOrder;
import com.arvato.jdf.util.ReflectionHelper;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.*;
import org.hibernate.impl.CriteriaImpl;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.transform.ResultTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.*;

/**
 * 基于Hibernat的泛型DAO实现
 * @param <T> DAO进行操作的对象类型
 * @param <ID> 主键类型
 * @author Justin
 */
public class HibernateDao<T, ID extends Serializable> {

    protected final Logger logger = LoggerFactory.getLogger(getClass());
    protected Class<T> persistentClass;
    protected SessionFactory sessionFactory;

    /**
     * HibernateDao 构造函数
     * <p>用于Dao层继承HibernateDao来使用.
     * eg.
     * <code>public class UserDao extends HibernateDao<User, Long></code>
     */
    public HibernateDao() {
        this.persistentClass = ReflectionHelper.getSuperClassGenricType(getClass());
    }

    /**
     * HibernateDao 构造函数
     * <p>用于直接实例化HibernateDao来使用
     * eg.
     * <code>HibernateDao<User, Long> userDao = new HibernateDao<User, Long>(sessionFactory, User.class);</code>
     * @param sessionFactory
     * @param persistentClass
     */
    public HibernateDao(SessionFactory sessionFactory, Class<T> persistentClass) {
        this.persistentClass = persistentClass;
        this.sessionFactory = sessionFactory;
    }

    /**
     * 自动注入容器中标识为<code>sessionFactory</code>的SessionFactory实例
     * @param sessionFactory
     */
    @Autowired
    public void setSessionFactory(@Qualifier("sessionFactory") SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * @return sessionFactory
     */
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    /**
     * @return session
     */
    public Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    /**
     * 按主键获取对象.
     * @param id 主键
     */
    public T get(ID id) {
        Assert.notNull(id, "id不能为空");
        T entity = (T) getSession().get(persistentClass, id);
        if (entity == null) {
            logger.warn("未找到id为{}的{}对象...", id, getIdName());
        }
        return entity;
    }

    /**
     * 按主键集合获取对象列表.
     */
    public List<T> get(Collection<ID> ids) {
        return findByCriterion(Restrictions.in(getIdName(), ids));
    }

    /**
     * 获取全部对象.
     * @reuturn 对象列表
     */
    public List<T> getAll() {
        return findByCriterion();
    }

    /**
     *	获取全部对象, 支持按属性行序.
     */
    public List<T> getAll(String orderByProperty, boolean isAsc) {
        Criteria c = createCriteria();
        if (isAsc) {
            c.addOrder(Order.asc(orderByProperty));
        } else {
            c.addOrder(Order.desc(orderByProperty));
        }
        return c.list();
    }

    public List<T> getAllDistinct() {
        Collection result = new LinkedHashSet(getAll());
        return new ArrayList(result);
    }

    public T save(T entity) {
        return (T) getSession().merge(entity);
    }

    public void saveOrUpdateAll(Collection<T> entities) {
        for (Object entity : entities) {
            getSession().saveOrUpdate(entity);
        }
    }

    public void delete(T entity) {
        Assert.notNull(entity, "entity不能为空");
        getSession().delete(entity);
        logger.debug("delete entity: {}", entity);
    }

    public void delete(ID id) {
        Assert.notNull(id, "id不能为空");
        delete(get(id));
        logger.debug("delete entity {},id is {}", persistentClass.getSimpleName(), id);
    }

    public void delete(Collection<ID> ids) {
        Assert.notNull(ids, "ids不能为空");
        for (ID id : ids) {
            delete(get(id));
        }
        logger.debug("delete entity {},id is {}", persistentClass.getSimpleName(), StringUtils.join(ids, ","));
    }

    public void deleteAll(Collection<T> entities) {
        for (Object entity : entities) {
            getSession().delete(entity);
        }
    }

    public boolean exists(ID id) {
        T entity = (T) getSession().load(persistentClass, id);
        return entity != null;
    }

    /**
     * 按HQL查询对象列表.
     *
     * @param values 数量可变的参数,按顺序绑定.
     */
    public <X> List<X> find(final String hql, final Object... values) {
        return createQuery(hql, values).list();
    }

    /**
     * 按HQL查询对象列表.
     *
     * @param values 命名参数,按名称绑定.
     */
    public <X> List<X> findByMap(final String hql, final Map<String, ?> values) {
        return createQueryByMap(hql, values).list();
    }

    /**
     * 按HQL查询对象列表.
     *
     * @param values 命名参数,按名称绑定.
     */
    public <X> List<X> findByBean(final String hql, final Object values) {
        return createQueryByBean(hql, values).list();
    }

    /**
     * 按Criteria查询对象列表.
     *
     * @param criterions 数量可变的Criterion.
     */
    public List<T> findByCriterion(final Criterion... criterions) {
        return createCriteria(criterions).list();
    }

    /**
     * 按HQL查询一个对象.
     *
     * @param values 数量可变的参数,按顺序绑定.
     */
    public <X> X getOne(final String hql, final Object... values) {
        List<X> list = find(hql, values);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    /**
     * 按HQL查询一个对象.
     *
     * @param values 命名参数,按名称绑定.
     */
    public <X> X getOneByMap(final String hql, final Map<String, ?> values) {
        List<X> list = findByMap(hql, values);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    /**
     * 按HQL查询一个对象.
     *
     * @param values 命名参数,按名称绑定.
     */
    public <X> X getOneByBean(final String hql, final Object values) {
        List<X> list = findByBean(hql, values);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    /**
     * 按Criteria查询一个对象.
     *
     * @param criterions 数量可变的Criterion.
     */
    public T getOneByCriterion(final Criterion... criterions) {
        List<T> list = findByCriterion(criterions);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    /**
     * 按PropertyFilter查询一个对象.
     *
     * @param filter 属性过滤器
     */
    public T getOneByPropertyFilter(final PropertyFilter filter) {
        List<T> list = findByPropertyFilter(filter);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    /**
     * 按HQL查询唯一对象.
     *
     * @param values 数量可变的参数,按顺序绑定.
     */
    public <X> X findUnique(final String hql, final Object... values) {
        return (X) createQuery(hql, values).uniqueResult();
    }

    /**
     * 按属性查找唯一对象, 匹配方式为相等.
     */
    public T findUniqueByProperty(final String propertyName, final Object propertyValue) {
        Assert.hasText(propertyName, "propertyName不能为空");
        Criterion criterion = Restrictions.eq(propertyName, propertyValue);
        return (T) createCriteria(criterion).uniqueResult();
    }

    /**
     * 按HQL查询唯一对象.
     *
     * @param values 命名参数,按名称绑定.
     */
    public <X> X findUniqueByMap(final String hql, final Map<String, ?> values) {
        return (X) createQueryByMap(hql, values).uniqueResult();
    }

    /**
     * 按HQL查询唯一对象.
     *
     * @param values 命名参数,按名称绑定.
     */
    public <X> X findUniqueByBean(final String hql, final Object values) {
        return (X) createQueryByBean(hql, values).uniqueResult();
    }

    /**
     * 按Criteria查询唯一对象.
     *
     * @param criterions 数量可变的Criterion.
     */
    public T findUniqueByCriterion(final Criterion... criterions) {
        return (T) createCriteria(criterions).uniqueResult();
    }

    /**
     * 按PropertyFilter查询唯一对象.
     *
     * @param filter 属性过滤器
     */
    public T findUniqueByPropertyFilter(final PropertyFilter filter) {
        return (T) createCriteria(filter).uniqueResult();
    }

    /**
     * 执行HQL进行批量修改/删除操作.
     *
     * @param values 数量可变的参数,按顺序绑定.
     * @return 更新记录数.
     */
    public int batchExecute(final String hql, final Object value) {
        return createQuery(hql, new Object[]{value}).executeUpdate();
    }

    /**
     * 执行HQL进行批量修改/删除操作.
     *
     * @param values 数量可变的参数,按顺序绑定.
     * @return 更新记录数.
     */
    public int batchExecute(final String hql, final Object... values) {
        return createQuery(hql, values).executeUpdate();
    }

    /**
     * 执行HQL进行批量修改/删除操作.
     *
     * @param values 命名参数,按名称绑定.
     * @return 更新记录数.
     */
    public int batchExecute(final String hql, final Map<String, ?> values) {
        return createQueryByMap(hql, values).executeUpdate();
    }

    public boolean isPropertyUnique(final String propertyName, final Object propertyValue) {
        Object object = findUnique(propertyName, propertyValue);
        return (object == null);
    }

    /**
     * 判断对象的属性值在数据库内是否唯一.
     *
     * 在修改对象的情景下,如果属性新修改的值(value)等于属性原来的值(orgValue)则不作比较.
     */
    public boolean isPropertyUnique(final String propertyName, final Object newValue, final Object oldValue) {
        if (newValue == null || newValue.equals(oldValue)) {
            return true;
        }
        return isPropertyUnique(propertyName, newValue);
    }

    private String prepareCountHql(String orgHql) {
        String fromHql = orgHql;
        //select子句与order by子句会影响count查询,进行简单的排除.
        fromHql = "from " + StringUtils.substringAfter(fromHql, "from");
        fromHql = StringUtils.substringBefore(fromHql, "order by");

        String countHql = "select count(*) " + fromHql;
        return countHql;
    }

    /**
     * 执行count查询获得本次Hql查询所能获得的对象总数.
     *
     * 本函数只能自动处理简单的hql语句,复杂的hql查询请另行编写count语句查询.
     */
    public long countHqlResult(final String hql, final Object... values) {
        String countHql = prepareCountHql(hql);

        try {
            Long count = findUnique(countHql, values);
            return count;
        } catch (Exception e) {
            throw new RuntimeException("hql can't be auto count, hql is:" + countHql, e);
        }
    }

    /**
     * 执行count查询获得本次Hql查询所能获得的对象总数.
     *
     * 本函数只能自动处理简单的hql语句,复杂的hql查询请另行编写count语句查询.
     */
    public long countHqlResult(final String hql, final Map<String, ?> values) {
        String countHql = prepareCountHql(hql);

        try {
            Long count = findUnique(countHql, values);
            return count;
        } catch (Exception e) {
            throw new RuntimeException("hql can't be auto count, hql is:" + countHql, e);
        }
    }

    /**
     * 执行count查询获得本次Criteria查询所能获得的对象总数.
     */
    @SuppressWarnings("unchecked")
    public long countCriteriaResult(final Criteria c) {
        CriteriaImpl impl = (CriteriaImpl) c;

        // 先把Projection、ResultTransformer、OrderBy取出来,清空三者后再执行Count操作
        Projection projection = impl.getProjection();
        ResultTransformer transformer = impl.getResultTransformer();

        List<CriteriaImpl.OrderEntry> orderEntries = null;
        try {
            orderEntries = (List) ReflectionHelper.getFieldValue(impl, "orderEntries");
            ReflectionHelper.setFieldValue(impl, "orderEntries", new ArrayList());
        } catch (Exception e) {
            logger.error("不可能抛出的异常:{}", e.getMessage());
        }

        // 执行Count查询
        Long totalCountObject = (Long) c.setProjection(Projections.rowCount()).uniqueResult();
        long totalCount = (totalCountObject != null) ? totalCountObject : 0;

        // 将之前的Projection,ResultTransformer和OrderBy条件重新设回去
        c.setProjection(projection);

        if (projection == null) {
            c.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
        }
        if (transformer != null) {
            c.setResultTransformer(transformer);
        }
        try {
            ReflectionHelper.setFieldValue(impl, "orderEntries", orderEntries);
        } catch (Exception e) {
            logger.error("不可能抛出的异常:{}", e.getMessage());
        }

        return totalCount;
    }

    /**
     * @return 对象的主键名
     */
    public String getIdName() {
        ClassMetadata meta = getSessionFactory().getClassMetadata(persistentClass);
        return meta.getIdentifierPropertyName();
    }

    /**
     * 根据Criterion条件创建Criteria.
     * 与find()函数可进行更加灵活的操作.
     *
     * @param criterions 数量可变的Criterion.
     */
    public Criteria createCriteria(final Criterion... criterions) {
        Criteria criteria = getSession().createCriteria(persistentClass);
        for (Criterion c : criterions) {
            criteria.add(c);
        }
        return criteria;
    }

    /**
     * 根据查询HQL与参数列表创建Query对象.
     * 与find()函数可进行更加灵活的操作.
     * 
     * @param values 数量可变的参数,按顺序绑定.
     */
    public Query createQuery(final String queryString, final Object... values) {
        Assert.hasText(queryString, "queryString不能为空");
        Query query = getSession().createQuery(queryString);
        if (values != null) {
            for (int i = 0; i < values.length; i++) {
                query.setParameter(i, values[i]);
            }
        }
        return query;
    }

    /**
     * 根据查询HQL与参数列表创建Query对象.
     * 与find()函数可进行更加灵活的操作.
     *
     * @param values 命名参数,按名称绑定.
     */
    public Query createQueryByMap(final String queryString, final Map<String, ?> values) {
        Assert.hasText(queryString, "queryString不能为空");
        Query query = getSession().createQuery(queryString);
        if (values != null) {
            query.setProperties(values);
        }
        return query;
    }

    /**
     * 根据查询HQL与参数列表创建Query对象.
     * 与find()函数可进行更加灵活的操作.
     *
     * @param values 命名参数,按名称绑定.
     */
    public Query createQueryByBean(final String queryString, final Object values) {
        Assert.hasText(queryString, "queryString不能为空");
        Query query = getSession().createQuery(queryString);
        if (values != null) {
            query.setProperties(values);
        }
        return query;
    }

    /**
     * 按HQL分页查询.
     *
     * @param page 分页参数. 注意不支持其中的orderBy参数.
     * @param hql hql语句.
     * @param values 数量可变的查询参数,按顺序绑定.
     *
     * @return 分页查询结果, 结果列表及所有查询输入参数.
     */
    @SuppressWarnings("unchecked")
    public Page<T> find(final Page<T> page, final String hql, final Object... values) {
        Assert.notNull(page, "page不能为空.");

        Query q = createQuery(setOrderParamToQuery(hql, page), values); //创建Query对象
        if (page.isAutoCount()) {
            page.setRecordCount(countHqlResult(hql, values)); //获取记录总数并设置page
        }
        setPageParamToQuery(q, page); // 设置page分页参数给Query对象
        page.setResult(q.list()); //获取记录结果列表并设置page

        return page;
    }

    /**
     * 按HQL分页查询.
     *
     * @param page 分页参数. 注意不支持其中的orderBy参数.
     * @param hql hql语句.
     * @param values 命名参数,按名称绑定.
     *
     * @return 分页查询结果, 附带结果列表及所有查询输入参数.
     */
    @SuppressWarnings("unchecked")
    public Page<T> findByMap(final Page<T> page, final String hql, final Map<String, ?> values) {
        Assert.notNull(page, "page不能为空");


        Query q = createQueryByMap(setOrderParamToQuery(hql, page), values); //创建Query对象
        if (page.isAutoCount()) {
            page.setRecordCount(countHqlResult(hql, values)); //获取记录总数并设置page
        }
        setPageParamToQuery(q, page); // 设置page分页参数给Query对象
        page.setResult(q.list()); //获取记录结果列表并设置page
        return page;
    }

    /**
     * 按HQL分页查询.
     *
     * @param page 分页参数. 注意不支持其中的orderBy参数.
     * @param hql hql语句.
     * @param values 命名参数,按名称绑定.
     *
     * @return 分页查询结果, 附带结果列表及所有查询输入参数.
     */
    @SuppressWarnings("unchecked")
    public Page<T> findByBean(final Page<T> page, final String hql, final Object values) {
        Assert.notNull(page, "page不能为空");

        Query q = createQueryByBean(setOrderParamToQuery(hql, page), values); //创建Query对象
        if (page.isAutoCount()) {
            page.setRecordCount(countHqlResult(hql, values)); //获取记录总数并设置page
        }
        setPageParamToQuery(q, page); // 设置page分页参数给Query对象
        page.setResult(q.list()); //获取记录结果列表并设置page
        return page;
    }

    /**
     * 按Criteria分页查询.
     *
     * @param page 分页参数.
     * @param criterions 数量可变的Criterion.
     *
     * @return 分页查询结果.附带结果列表及所有查询输入参数.
     */
    @SuppressWarnings("unchecked")
    public Page<T> findByCriterion(final Page<T> page, final Criterion... criterions) {
        Assert.notNull(page, "page不能为空");

        Criteria c = createCriteria(criterions); //创建Criteria对象
        if (page.isAutoCount()) {
            page.setRecordCount(countCriteriaResult(c)); //获取记录总数并设置page
        }
        setPageParamToCriteria(c, page);
        page.setResult(c.list());
        return page;
    }

    /**
     * 按属性过滤条件列表查找对象.
     */
    public List<T> findByPropertyFilter(final PropertyFilter filter) {
        Criteria c = createCriteria(filter); //创建Criteria对象
        return c.list();
    }

    /**
     * 按属性过滤条件列表分页查找对象.
     */
    public Page<T> findByPropertyFilter(final Page<T> page, final PropertyFilter filter) {
        Assert.notNull(page, "page不能为空");

        Criteria c = createCriteria(filter); //创建Criteria对象
        if (page.isAutoCount()) {
            page.setRecordCount(countCriteriaResult(c)); //获取记录总数并设置page
        }
        setPageParamToCriteria(c, page);
        page.setResult(c.list());
        return page;
    }

    /**
     * 设置排序字段参数到HSQL
     */
    protected String setOrderParamToQuery(final String hsql, final Page<T> page) {
        StringBuilder hsqlSb = new StringBuilder(hsql);

        int orderIndex = StringUtils.indexOfIgnoreCase(hsql, "order by");
        if (orderIndex != -1) {
            hsqlSb.substring(0, orderIndex);
        }

        if (page.hasOrder()) {
            PropertyOrder[] orders = page.getOrders();
            String[] orderBlockHql = new String[orders.length];

            for (int i = 0; i < orders.length; i++) {
                orderBlockHql[i] = orders[i].toString();
            }

            hsqlSb.append(" order by ");
            hsqlSb.append(StringUtils.join(orderBlockHql, ","));
        }

        return hsqlSb.toString();
    }

    /**
     * 设置分页参数到Query对象,辅助函数.
     */
    protected Query setPageParamToQuery(final Query q, final Page<T> page) {
        q.setFirstResult(page.getStartIndex());
        q.setMaxResults(page.getPageSize());

        return q;
    }

    /**
     * 设置分页参数到Criteria对象,辅助函数.
     */
    protected Criteria setPageParamToCriteria(final Criteria c, final Page<T> page) {
        c.setFirstResult(page.getStartIndex());
        c.setMaxResults(page.getPageSize());

        if (page.hasOrder()) {
            PropertyOrder[] orders = page.getOrders();

            for (int i = 0; i < orders.length; i++) {
                PropertyOrder order = orders[i];
                String propertyName = order.getPropertyName();
//                System.out.println("propertyName:"+propertyName);
                if(propertyName.indexOf('.')>0){
                    String[] arry = propertyName.split("\\.");
//                    System.out.println("arry:"+arry.length);
                    Criteria criteria = c;
                    for(int j=0;j<arry.length;j++){
                        if(j<arry.length-1)
                            criteria = criteria.createCriteria(arry[j]);
                        else{
                            criteria = criteria.addOrder(order.isAscending()?Order.asc(arry[j]):Order.desc(arry[j]));
                        }
                    }
                }
                else


                    c.addOrder(orders[i].getOrder());



            }
        }
        return c;
    }

    /**
     * 从属性过滤器构建<code>Criteria</code>. 
     * @param filter 属性过滤器.
     * @return <code>Criteria</code>
     */
    public Criteria createCriteria(final PropertyFilter filter) {
        Criteria criteria = getSession().createCriteria(persistentClass);
        Set<String> alias = new HashSet<String>();

        Junction junction = filter.getJunctionType().equals(JunctionType.AND)
                ? Restrictions.conjunction() : Restrictions.disjunction();

        Property[] properties = filter.getProperties();
        if (ArrayUtils.isNotEmpty(properties)) {
            for (Property p : properties) {
                if (StringUtils.contains(p.getPropertyName(), ".")) {
                    String aliaName = StringUtils.substringBefore(p.getPropertyName(), ".");
                    alias.add(aliaName);
                }
                junction.add(createCriterion(p));
            }
        }

        if (filter.hasChildren()) {
            PropertyFilter[] children = filter.getChildren();
            for (PropertyFilter child : children) {
                junction.add(createCriterion(child));
            }
        }

        for (String aliaName : alias) {
            criteria.createAlias(aliaName, aliaName);
        }

        criteria.add(junction);

        if (filter.hasOrder()) {
            for (PropertyOrder order : filter.getOrders()) {
                criteria.addOrder(order.getOrder());
            }
        }
        
        return criteria;
    }

    /**
     * 按属性条件参数创建Criterion,辅助函数.
     */
    protected Criterion createCriterion(final Property property) {
        String propertyName = property.getPropertyName();
        Object propertyValue = property.getPropertyValue();
        MatchType matchType = property.getMatchType();

        Assert.hasText(propertyName, "propertyName不能为空");
        Criterion criterion = null;
        //根据MatchType构造criterion
        switch (matchType) {
            case EQ:
                criterion = Restrictions.eq(propertyName, propertyValue);
                break;
            case LIKE:
                criterion = Restrictions.like(propertyName, (String) propertyValue, MatchMode.ANYWHERE);
                break;
            case LLIKE:
                criterion = Restrictions.like(propertyName, (String) propertyValue, MatchMode.START);
                break;
            case RLIKE:
                criterion = Restrictions.like(propertyName, (String) propertyValue, MatchMode.END);
                break;
            case ELIKE:
                criterion = Restrictions.like(propertyName, (String) propertyValue, MatchMode.EXACT);
                break;
            case LE:
                criterion = Restrictions.le(propertyName, propertyValue);
                break;
            case LT:
                criterion = Restrictions.lt(propertyName, propertyValue);
                break;
            case GE:
                criterion = Restrictions.ge(propertyName, propertyValue);
                break;
            case GT:
                criterion = Restrictions.gt(propertyName, propertyValue);
                break;
            case NE:
                criterion = Restrictions.ne(propertyName, propertyValue);
                break;
        }
        return criterion;
    }

    /**
     * 按属性条件列表创建Criterion数组,辅助函数.
     */
    protected Criterion createCriterion(final PropertyFilter filter) {
        Property[] properties = filter.getProperties();
        Junction junction = filter.getJunctionType().equals(JunctionType.AND)
                ? Restrictions.conjunction() : Restrictions.disjunction();

        for (Property p : properties) {
            junction.add(createCriterion(p));
        }

        if (filter.hasChildren()) {
            PropertyFilter[] children = filter.getChildren();
            for (PropertyFilter child : children) {
                junction.add(createCriterion(child));
            }
        }

        return junction;
    }
}
