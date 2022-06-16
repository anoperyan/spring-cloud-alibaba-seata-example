package net.hm.seata.biz.feign;

import net.hm.seata.biz.dto.Storage;
import net.hm.seata.common.util.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author Yan Jiahong
 * Created on 2021/06/09
 */
@FeignClient(name = "storage-service")
public interface RemoteStorageService {
    @PostMapping("/api/storage/deduct")
    R<Void> deduct(@RequestBody Storage dto);
}
