package net.hm.seata.storage.service;

import com.baomidou.mybatisplus.extension.service.IService;
import net.hm.seata.storage.entity.Storage;

/**
 * @author Yan Jiahong
 * Created on 2021/06/09
 */
public interface IStorageService extends IService<Storage> {
    void deduct(String commodityCode, int count);
}
