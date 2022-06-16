package net.hm.seata.order.feign;

import net.hm.seata.common.util.R;
import net.hm.seata.order.dto.Account;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author Yan Jiahong
 * Created on 2021/06/09
 */
@FeignClient(name = "account-service")
public interface RemoteAccountService {
    @GetMapping("/api/account/{username}")
    String getAccount(@PathVariable("username") String username);

    @PostMapping("/api/account/debit")
    R<?> debit(@RequestBody Account dto);
}
