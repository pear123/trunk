package com.arvato.cc.util;

import java.util.List;

public class CacheManager {
	private BaseCache paramsCache;
	private static CacheManager instance;
	private static Object lock = new Object();
	private CacheManager() {
		//根据配置文件来，初始BaseCache;
		paramsCache = new BaseCache("params",3600);
	}
	public static CacheManager getInstance(){
		if (instance == null){
			synchronized( lock ){
				if (instance == null){
					instance = new CacheManager();
				}
			}
		}
		return instance;
	}
	public void putList(String key, List<?> list) { paramsCache.put(key, list); }
	public void removeList(String key) { paramsCache.remove(key); }

	@SuppressWarnings("unchecked")
	public List<?> getValue(String key) throws Exception{
			return (List<?>) paramsCache.get(key);
	}

	public void removeAllLists() {
		paramsCache.removeAll();
	}

}
