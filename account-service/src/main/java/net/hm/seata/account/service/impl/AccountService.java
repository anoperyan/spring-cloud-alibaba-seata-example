package net.hm.seata.account.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.hm.seata.account.entity.Account;
import net.hm.seata.account.mapper.AccountMapper;
import net.hm.seata.account.service.IAccountService;
import org.springframework.stereotype.Service;

import java.util.Random;

/**
 * @author Yan Jiahong
 * Created on 2021/06/09
 */
@Service
public class AccountService extends ServiceImpl<AccountMapper, Account> implements IAccountService {
    private final Random random = new Random();

    @Override
    public void debit(String userId, int amount) {
        //if (random.nextInt(10) < 9) {
        //    throw new RuntimeException("Mock Exception from Account Service!");
        //}
        LambdaUpdateWrapper<Account> updateWrapper = new LambdaUpdateWrapper<Account>()
                .eq(Account::getUserId, userId)
                .setSql("amount=amount-" + amount);
        if (!update(updateWrapper)) {
            throw new RuntimeException("从用户账户借出钱时候出错！");
        }
    }
}
