package com.arvato.cc.service1.impl;

import com.arvato.cc.dao1.JstTradeDao;
import com.arvato.cc.form.JdpTradeBean;
import com.arvato.cc.model.JstTrade;
import com.arvato.cc.service1.JstTradeService;
import com.arvato.cc.util.PropertiesFileReader;
import com.arvato.cc.util.Validate;
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.cxf.jaxrs.client.WebClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.MediaType;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: caol005
 * Date: 15-8-12
 * Time: 上午10:35
 * To change this template use File | Settings | File Templates.
 */
@Service
public class JstTradeServiceImpl implements JstTradeService {

    @Autowired
    private JstTradeDao jstTradeDao;

    public void saveJstTrade(List<JstTrade> jstTradeList) {
        jstTradeDao.saveJstTrade(jstTradeList);
    }

    public void saveJstTrade(JstTrade jstTrade) {
        jstTradeDao.saveJstTrade(jstTrade);
    }




}
