package com.arvato.cc.util;

import com.opensymphony.oscache.base.NeedsRefreshException;
import com.opensymphony.oscache.general.GeneralCacheAdministrator;

import java.util.Date;
import java.util.List;

public class BaseCache extends GeneralCacheAdministrator {
	private int refreshPeriod; //过期时间(单位为秒);;
	private String keyPrefix; //关键字前缀字符;
	private static final long serialVersionUID = -4397192926052141162L;
	public BaseCache(String keyPrefix,int refreshPeriod){
		super();
		this.keyPrefix = keyPrefix;
		this.refreshPeriod = refreshPeriod;
	}
	//添加被缓存的对象，为List类型;
	public void put(String key,List<?> value){
		this.putInCache(this.keyPrefix+"."+key,value);
	}
	//删除被缓存的对象;
	public void remove(String key){
		this.flushEntry(this.keyPrefix+"."+key);
	}
	//删除所有被缓存的对象;
	public void removeAll(Date date){
		this.flushAll(date);
	}
	public void removeAll(){
		this.flushAll();
	}
	//获取被缓存的对象;
	public List<?> get(String key) throws Exception{
		try{
			return (List<?>) this.getFromCache(this.keyPrefix+"."+key,this.refreshPeriod);
		} catch (NeedsRefreshException e) {
			this.cancelUpdate(this.keyPrefix+"."+key);
		throw e;
		}
	}
}
