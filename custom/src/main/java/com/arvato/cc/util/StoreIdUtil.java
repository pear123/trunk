/**
 * 
 */
package com.arvato.cc.util;

import java.util.Iterator;
import java.util.Map;

/**
 * 项目名称：arvato_oms    
 * 类名称：storeIdUtil    
 * 类描述：    
 * 创建人：Robin.hu
 * 创建时间：2011-7-20 下午01:44:56    
 * 修改人：
 * 修改时间：2011-7-20 下午01:44:56    
 * 修改备注： 
 */
public class StoreIdUtil {
	@SuppressWarnings("unchecked")
	public static String getstordId(Map map){
		StringBuffer stringbuffer = new StringBuffer();
		for(Iterator it = map.keySet().iterator();it.hasNext();){
			String key = it.next().toString();
			stringbuffer.append(key+",");
		}
		return stringbuffer.toString();
	}
}
