package net.hm.seata.account.controller;

import net.hm.seata.account.entity.Account;
import net.hm.seata.account.service.IAccountService;
import net.hm.seata.common.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * @author Yan Jiahong
 * Created on 2021/06/09
 */
@RestController
@RequestMapping("/api/account")
public class AccountController {
    @Autowired
    private IAccountService accountService;

    @GetMapping("/{username}")
    public String getAccount(@PathVariable("username") String username) {
        return UUID.randomUUID() + "_" + username;
    }

    @PostMapping("/debit")
    public R<Void> debit(@RequestBody Account dto) {
        accountService.debit(dto.getUserId(), dto.getAmount());
        return R.ok();
    }
}
