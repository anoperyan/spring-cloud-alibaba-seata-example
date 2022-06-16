package net.hm.seata.order.controller;

import net.hm.seata.common.util.R;
import net.hm.seata.order.entity.Order;
import net.hm.seata.order.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Yan Jiahong
 * Created on 2021/06/09
 */
@RestController
@RequestMapping("/api/order")
public class OrderController {
    @Autowired
    private IOrderService orderService;

    @PostMapping("/create")
    public R<Order> create(@RequestBody Order dto) {
        Order order = orderService.create(dto.getUserId(), dto.getCommodityCode(), dto.getCount());
        return R.ok(order);
    }
    //@Autowired
    //private RemoteAccountService accountService;
    //@Autowired
    //private TestService testService;
    //private final Random random = new Random();

    //@GetMapping("/{orderId}")
    //@SentinelResource(value = "get_order", fallback = "getOrderFallback")
    //public String getOrder(@PathVariable("orderId") String orderId) {
    //    if (random.nextInt(10) % 2 == 0) {
    //        throw new RuntimeException("获取订单ID失败！");
    //    }
    //    return "order_id=" + orderId + ", username=" + accountService.getAccount("xxx");
    //}
    //
    //public String getOrderFallback(String orderId) {
    //    return "Fallback  " + orderId;
    //}
}
