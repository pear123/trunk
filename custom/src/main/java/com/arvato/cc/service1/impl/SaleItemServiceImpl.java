package com.arvato.cc.service1.impl;

import com.arvato.cc.model.JdpTrade;
import com.arvato.cc.model.SaleItem;
import com.arvato.cc.model.TempOrder;
import com.arvato.cc.model.TempTrade;
import com.arvato.cc.service1.RoleService;
import com.arvato.cc.service1.SaleItemService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: caol005
 * Date: 15-9-10
 * Time: 下午4:47
 * To change this template use File | Settings | File Templates.
 */
@Service
public class SaleItemServiceImpl implements SaleItemService {

    @Override
    public void saveSaleItem(TempTrade tempTrade) {
        SaleItem saleItem = new SaleItem();
        saleItem.setTid(tempTrade.getTid());
        saleItem.setStoreId(tempTrade.getStoreId());
        saleItem.setStatus(tempTrade.getStatus());
        saleItem.setCreateTime(tempTrade.getCreated());

        Set<TempOrder> tempOrderSet = tempTrade.getTempOrders();
        for(TempOrder tempOrder : tempOrderSet) {
            saleItem.setCid("");
            saleItem.setSku1("");
            saleItem.setSkus(tempOrder.getMatrn());
            saleItem.setPartMjzDiscount(tempOrder.getPartMjzDiscount());
            saleItem.setPayment(tempOrder.getPayment());
            saleItem.setSapPrice(0.0);
        }

    }
}
