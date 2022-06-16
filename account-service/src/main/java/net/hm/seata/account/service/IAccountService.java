package net.hm.seata.account.service;

import com.baomidou.mybatisplus.extension.service.IService;
import net.hm.seata.account.entity.Account;

/**
 * @author Yan Jiahong
 * Created on 2021/06/09
 */
public interface IAccountService extends IService<Account> {
    /**
     * 从账户借出钱
     * @param userId 用户ID
     * @param amount 金额
     */
    void debit(String userId, int amount);
}
