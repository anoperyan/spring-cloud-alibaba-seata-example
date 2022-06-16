package net.hm.seata.order.feign;

import lab.anoper.dog.common.util.R;
import lab.anoper.dog.ec.order.dto.Account;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author Yan Jiahong
 * Created on 2021/06/09
 */
@FeignClient(name = "dog-ecommerce-account")
public interface RemoteAccountService {
    @GetMapping("/api/account/{username}")
    String getAccount(@PathVariable("username") String username);

    @PostMapping("/api/account/debit")
    R<?> debit(@RequestBody Account dto);
}
