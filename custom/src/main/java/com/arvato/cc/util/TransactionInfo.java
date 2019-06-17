package com.arvato.cc.util;

import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
/**
 * 事务信息
 * @author shaojie
 *
 */
public class TransactionInfo {

	DefaultTransactionDefinition def = null;
    TransactionStatus status = null;
    boolean result = true;
    
    public TransactionInfo(){
		def = new DefaultTransactionDefinition();
		//def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
		result = true;
    }

}
