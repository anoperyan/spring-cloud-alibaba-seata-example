package net.hm.seata.storage.controller;

import net.hm.seata.common.util.R;
import net.hm.seata.storage.entity.Storage;
import net.hm.seata.storage.service.IStorageService;
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
@RequestMapping("/api/storage")
public class StorageController {
    @Autowired
    private IStorageService storageService;

    @PostMapping("/deduct")
    public R<Void> deduct(@RequestBody Storage dto) {
        storageService.deduct(dto.getCommodityCode(), dto.getCount());
        return R.ok();
    }
}
