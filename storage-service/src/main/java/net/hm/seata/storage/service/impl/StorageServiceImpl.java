package net.hm.seata.storage.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.hm.seata.storage.entity.Storage;
import net.hm.seata.storage.mapper.StorageMapper;
import net.hm.seata.storage.service.IStorageService;
import org.springframework.stereotype.Service;

/**
 * @author Yan Jiahong
 * Created on 2021/06/09
 */
@Service
public class StorageServiceImpl extends ServiceImpl<StorageMapper, Storage>
        implements IStorageService {
    @Override
    public void deduct(String commodityCode, int count) {
        LambdaUpdateWrapper<Storage> updateWrapper = new LambdaUpdateWrapper<Storage>()
                .eq(Storage::getCommodityCode, commodityCode)
                .setSql("count=count-" + count);
        if (!update(updateWrapper)) {
            throw new RuntimeException("更新库存失败！");
        }
    }
}
