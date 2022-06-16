package net.hm.seata.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import net.hm.seata.order.entity.Order;

/**
 * @author Yan Jiahong
 * Created on 2021/06/09
 */
public interface IOrderService extends IService<Order> {
    Order create(String userId, String commodityCode, int orderCount);
}
