/**********************************************************************
 * FILE : TransactionUtil.java
 * CREATE DATE : 2006-11-13
 * DESCRIPTION :
 *
 * CHANGE HISTORY LOG
 *---------------------------------------------------------------------
 * NO.|    DATE    |     NAME     |     REASON     | DESCRIPTION
 *---------------------------------------------------------------------
 *          
 **********************************************************************
 */
package com.arvato.cc.util;

import org.springframework.transaction.PlatformTransactionManager;


/**
 * 事务操作工具类

 */
public class TransactionUtil {
    
    PlatformTransactionManager tm = null;
   
    public ThreadLocal<TransactionInfo> selfData = new ThreadLocal<TransactionInfo>();
    

    
    public void beginTrans(){
    	TransactionInfo tu = new TransactionInfo();    	
    	tu.status = tm.getTransaction(tu.def);
		selfData.set(tu);
    }
    
    public void errorHappen(){
    	if(null==selfData.get()) return;
    	TransactionInfo tu = selfData.get();
    	tu.result = false;
    }
    
    public void endTrans(){
    	if(null==selfData.get()) return;
    	
    	TransactionInfo tu = selfData.get();
    	if(!tu.result){
			tu.status.setRollbackOnly();
		}
		tm.commit(tu.status);
        selfData.set(null);
    }

	public void setTm(PlatformTransactionManager tm) {
		this.tm = tm;
	}
}
