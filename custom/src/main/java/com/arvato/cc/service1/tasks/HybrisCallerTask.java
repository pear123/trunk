package com.arvato.cc.service1.tasks;

//import com.arvato.oms.service.HybrisInterfaceService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


@Component("hybrisCallerTask")
@Scope("prototype")
public class HybrisCallerTask implements Runnable {

    private final Logger logger = LoggerFactory.getLogger(HybrisCallerTask.class);

//    @Autowired
//    private HybrisInterfaceService hybrisInterfaceService;

    private String sourceOrderNo;

    private String status;

    private String shippingNo;

    public HybrisCallerTask(String sourceOrderNo, String status, String shippingNo) {
        this.sourceOrderNo = sourceOrderNo;
        this.status = status;
        this.shippingNo = shippingNo;
    }

    public void run() {
//        logger.debug("start the thread[name:" + Thread.currentThread().getName() + "] to call the interface released by Hybris by parameters[sourceOrderNo:" + sourceOrderNo + "], status:" + status + ", shippingNo:" + shippingNo + "]");
//        try {
//            hybrisInterfaceService.pushOrdersStatus(sourceOrderNo, status, shippingNo);
//        } catch (IOException e) {
//            logger.error("Exception[message:" + e.toString() + "] occurred.");
//        } catch (Exception e) {
//            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//        }
    }
}
