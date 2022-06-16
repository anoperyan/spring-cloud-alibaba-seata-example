package net.hm.seata.order.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lab.anoper.dog.common.util.R;
import lab.anoper.dog.common.util.RespCode;
import lab.anoper.dog.ec.order.dto.Account;
import lab.anoper.dog.ec.order.entity.Order;
import lab.anoper.dog.ec.order.feign.RemoteAccountService;
import lab.anoper.dog.ec.order.mapper.OrderMapper;
import lab.anoper.dog.ec.order.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Yan Jiahong
 * Created on 2021/06/09
 */
@Service
public class OrderService extends ServiceImpl<OrderMapper, Order> implements IOrderService {
    @Autowired
    private RemoteAccountService accountService;

    @Override
    public Order create(String userId, String commodityCode, int orderCount) {
        int total = calculate(commodityCode, orderCount);
        Account accDTO = new Account();
        accDTO.setUserId(userId);
        accDTO.setAmount(total);
        R<?> debitR = accountService.debit(accDTO);
        if (RespCode.OK.code != debitR.getCode()) {
            throw new RuntimeException("创建订单失败！原因：" + debitR.getMessage());
        }
        Order order = new Order();
        order.setUserId(userId);
        order.setCommodityCode(commodityCode);
        order.setCount(orderCount);
        order.setMoney(total);
        boolean saved = save(order);
        if (!saved) {
            throw new RuntimeException("创建订单失败！");
        }
        return getById(order.getId());
    }

    private int calculate(String commodityId, int orderCount) {
        return 40 * orderCount;
    }
}
