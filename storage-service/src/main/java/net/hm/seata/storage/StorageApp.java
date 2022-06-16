package net.hm.seata.storage;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author Yan Jiahong
 * Created on 2022/6/16
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@MapperScan("net.hm.seata.storage.mapper")
public class StorageApp {
    public static void main(String[] args) {
        SpringApplication.run(StorageApp.class, args);
    }
}
