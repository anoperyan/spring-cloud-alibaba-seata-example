package net.hm.seata.biz.feign;

import net.hm.seata.biz.dto.Order;
import net.hm.seata.common.util.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author Yan Jiahong
 * Created on 2021/06/09
 */
@FeignClient(name = "dog-ecommerce-order")
public interface RemoteOrderService {
    @PostMapping("/api/order/create")
    R<Order> create(@RequestBody Order dto);
}
