package net.hm.seata.biz.service.impl;

import io.seata.spring.annotation.GlobalTransactional;
import net.hm.seata.biz.dto.Order;
import net.hm.seata.biz.dto.Storage;
import net.hm.seata.biz.feign.RemoteOrderService;
import net.hm.seata.biz.feign.RemoteStorageService;
import net.hm.seata.biz.service.IBusinessService;
import net.hm.seata.common.util.R;
import net.hm.seata.common.util.RespCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Yan Jiahong
 * Created on 2021/06/09
 */
@Service
public class BusinessServiceImpl implements IBusinessService {
    @Autowired
    private RemoteStorageService storageService;
    @Autowired
    private RemoteOrderService orderService;

    @Override
    @GlobalTransactional
    public void purchase(String userId, String commodityCode, int orderCount) {
        Storage storageDTO = new Storage();
        storageDTO.setCommodityCode(commodityCode);
        storageDTO.setCount(orderCount);
        R<Void> storageR = storageService.deduct(storageDTO);
        //try {
        //    Thread.sleep(5000);
        //}catch (Exception e) {
        //    // do nothing
        //}
        if (RespCode.OK.code != storageR.getCode()) {
            throw new RuntimeException("提交库存失败！");
        }
        Order orderDTO = new Order();
        orderDTO.setUserId(userId);
        orderDTO.setCommodityCode(commodityCode);
        orderDTO.setCount(orderCount);
        R<Order> orderR = orderService.create(orderDTO);
        if (RespCode.OK.code != orderR.getCode()) {
            throw new RuntimeException("创建订单失败！");
        }
    }
}
