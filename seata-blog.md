# Spring Cloud Alibaba使用Seata实现分布式事务

## 资源准备

1. Nacos Server v2.1.0 [点击下载](https://github.com/alibaba/nacos/releases/tag/2.1.0)
2. Seata Server v1.4.2 [点击下载](https://github.com/seata/seata/releases/download/v1.4.2/seata-server-1.4.2.zip)

## 启动步骤

### 1. 配置并启动Nacos Server(自行百度).

### 2. 配置并启动Seata Server:

#### 2.1 配置Seata Server注册信息

修改服务器根目录的conf/registry.conf文件进行注册配置，配置信息如下:

```shell

registry {
  # file 、nacos 、eureka、redis、zk、consul、etcd3、sofa
  #  将seata服务器注册到nacos 
  type = "nacos"

  # nacos注册配置
  nacos {
    # nacos服务的显示名称（seata-server是一个java服务）
    application = "seata-server"
    # Nacos服务器的注册地址
    serverAddr = "127.0.0.1:8848"
    # Nacos的服务组
    group = "DEFAULT_GROUP"
    namespace = ""
    cluster = "default"
    username = "nacos"
    password = "nacos"
  }
}

# seata-server的配置信息，seata-server服务会读取配置
config {
  # file、nacos 、apollo、zk、consul、etcd3
  # 配置方式为Nacos，也就是seata服务的配置信息会从Nacos动态读取
  type = "nacos"

  # Nacos配置服务的信息
  nacos {
    # 配置服务器地址
    serverAddr = "127.0.0.1:8848"
    namespace = ""
    group = "DEFAULT_GROUP"
    username = ""
    password = ""
    # 配置信息的data-id
    dataId = "seata-server.properties"
  }
}
```

2.2 在Nacos配置中新建`seata-server.properties`文件，并且配置如下信息（中文注释是重点）：

```properties
#For details about configuration items, see https://seata.io/zh-cn/docs/user/configurations.html
#Transport configuration, for client and server
transport.type=TCP
transport.server=NIO
transport.heartbeat=true
transport.enableTmClientBatchSendRequest=false
transport.enableRmClientBatchSendRequest=true
transport.enableTcServerBatchSendResponse=false
transport.rpcRmRequestTimeout=30000
transport.rpcTmRequestTimeout=30000
transport.rpcTcRequestTimeout=30000
transport.threadFactory.bossThreadPrefix=NettyBoss
transport.threadFactory.workerThreadPrefix=NettyServerNIOWorker
transport.threadFactory.serverExecutorThreadPrefix=NettyServerBizHandler
transport.threadFactory.shareBossWorker=false
transport.threadFactory.clientSelectorThreadPrefix=NettyClientSelector
transport.threadFactory.clientSelectorThreadSize=1
transport.threadFactory.clientWorkerThreadPrefix=NettyClientWorkerThread
transport.threadFactory.bossThreadSize=1
transport.threadFactory.workerThreadSize=default
transport.shutdown.wait=3
transport.serialization=seata
transport.compressor=none
#Transaction routing rules configuration, only for the client
# 默认的事务分组
service.vgroupMapping.default_tx_group=default
#If you use a registry, you can ignore it
service.default.grouplist=127.0.0.1:8091
service.enableDegrade=false
service.disableGlobalTransaction=false
#Transaction rule configuration, only for the client
client.rm.asyncCommitBufferLimit=10000
client.rm.lock.retryInterval=10
client.rm.lock.retryTimes=30
client.rm.lock.retryPolicyBranchRollbackOnConflict=true
client.rm.reportRetryCount=5
client.rm.tableMetaCheckEnable=true
client.rm.tableMetaCheckerInterval=60000
client.rm.sqlParserType=druid
client.rm.reportSuccessEnable=false
client.rm.sagaBranchRegisterEnable=false
client.rm.sagaJsonParser=fastjson
client.rm.tccActionInterceptorOrder=-2147482648
client.tm.commitRetryCount=5
client.tm.rollbackRetryCount=5
client.tm.defaultGlobalTransactionTimeout=60000
client.tm.degradeCheck=false
client.tm.degradeCheckAllowTimes=10
client.tm.degradeCheckPeriod=2000
client.tm.interceptorOrder=-2147482648
client.undo.dataValidation=true
client.undo.logSerialization=jackson
client.undo.onlyCareUpdateColumns=true
server.undo.logSaveDays=7
server.undo.logDeletePeriod=86400000
client.undo.logTable=undo_log
client.undo.compress.enable=true
client.undo.compress.type=zip
client.undo.compress.threshold=64k
#For TCC transaction mode
tcc.fence.logTableName=tcc_fence_log
tcc.fence.cleanPeriod=1h
#Log rule configuration, for client and server
log.exceptionRate=100
#Transaction storage configuration, only for the server. The file, DB, and redis configuration values are optional.
# Seata服务器的数据存储方式设置为数据库存储
store.mode=db
store.lock.mode=db
store.session.mode=file
#Used for password encryption
store.publicKey=
#If `store.mode,store.lock.mode,store.session.mode` are not equal to `file`, you can remove the configuration block.
store.file.dir=file_store/data
store.file.maxBranchSessionSize=16384
store.file.maxGlobalSessionSize=512
store.file.fileWriteBufferCacheSize=16384
store.file.flushDiskMode=async
store.file.sessionReloadReadSize=100
#These configurations are required if the `store mode` is `db`. If `store.mode,store.lock.mode,store.session.mode` are not equal to `db`, you can remove the configuration block.
store.db.datasource=druid
store.db.dbType=mysql
store.db.driverClassName=com.mysql.jdbc.Driver
### 设置数据库得连接信息
store.db.url=jdbc:mysql://localhost:3306/seata?useUnicode=true&characterEncoding=utf-8&serverTimezone=Asia/Shanghai
store.db.user=root
store.db.password=root
store.db.minConn=5
store.db.maxConn=30
store.db.globalTable=global_table
store.db.branchTable=branch_table
store.db.distributedLockTable=distributed_lock
store.db.queryLimit=100
store.db.lockTable=lock_table
store.db.maxWait=5000
#Transaction rule configuration, only for the server
server.recovery.committingRetryPeriod=1000
server.recovery.asynCommittingRetryPeriod=1000
server.recovery.rollbackingRetryPeriod=1000
server.recovery.timeoutRetryPeriod=1000
server.maxCommitRetryTimeout=-1
server.maxRollbackRetryTimeout=-1
server.rollbackRetryTimeoutUnlockEnable=false
server.distributedLockExpireTime=10000
server.xaerNotaRetryTimeout=60000
server.session.branchAsyncQueueSize=5000
server.session.enableBranchAsyncRemove=false
#Metrics configuration, only for the server
metrics.enabled=false
metrics.registryType=compact
metrics.exporterList=prometheus
metrics.exporterPrometheusPort=9898
```

2.3 为seata服务器创建数据库和表，SQL如下：

注意：数据库名称需要创建为`seata`

```sql
/*
 Navicat Premium Data Transfer

 Source Server         : 腾讯云服务器
 Source Server Type    : MySQL
 Source Server Version : 50736
 Source Host           : 106.55.155.77:3306
 Source Schema         : seata

 Target Server Type    : MySQL
 Target Server Version : 50736
 File Encoding         : 65001

 Date: 16/06/2022 14:20:27
*/

SET NAMES utf8mb4;
SET
FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for branch_table
-- ----------------------------
DROP TABLE IF EXISTS `branch_table`;
CREATE TABLE `branch_table`
(
    `branch_id`         bigint(20) NOT NULL,
    `xid`               varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
    `transaction_id`    bigint(20) NULL DEFAULT NULL,
    `resource_group_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `resource_id`       varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `branch_type`       varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `status`            tinyint(4) NULL DEFAULT NULL,
    `client_id`         varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `application_data`  varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `gmt_create`        datetime(6) NULL DEFAULT NULL,
    `gmt_modified`      datetime(6) NULL DEFAULT NULL,
    PRIMARY KEY (`branch_id`) USING BTREE,
    INDEX               `idx_xid`(`xid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of branch_table
-- ----------------------------

-- ----------------------------
-- Table structure for distributed_lock
-- ----------------------------
DROP TABLE IF EXISTS `distributed_lock`;
CREATE TABLE `distributed_lock`
(
    `lock_key`   char(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci    NOT NULL,
    `lock_value` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
    `expire`     bigint(20) NULL DEFAULT NULL,
    PRIMARY KEY (`lock_key`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of distributed_lock
-- ----------------------------
INSERT INTO `distributed_lock`
VALUES ('AsyncCommitting', ' ', 0);
INSERT INTO `distributed_lock`
VALUES ('RetryCommitting', ' ', 0);
INSERT INTO `distributed_lock`
VALUES ('RetryRollbacking', ' ', 0);
INSERT INTO `distributed_lock`
VALUES ('TxTimeoutCheck', ' ', 0);

-- ----------------------------
-- Table structure for global_table
-- ----------------------------
DROP TABLE IF EXISTS `global_table`;
CREATE TABLE `global_table`
(
    `xid`                       varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
    `transaction_id`            bigint(20) NULL DEFAULT NULL,
    `status`                    tinyint(4) NOT NULL,
    `application_id`            varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `transaction_service_group` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `transaction_name`          varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `timeout`                   int(11) NULL DEFAULT NULL,
    `begin_time`                bigint(20) NULL DEFAULT NULL,
    `application_data`          varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `gmt_create`                datetime NULL DEFAULT NULL,
    `gmt_modified`              datetime NULL DEFAULT NULL,
    PRIMARY KEY (`xid`) USING BTREE,
    INDEX                       `idx_status_gmt_modified`(`status`, `gmt_modified`) USING BTREE,
    INDEX                       `idx_transaction_id`(`transaction_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of global_table
-- ----------------------------

-- ----------------------------
-- Table structure for lock_table
-- ----------------------------
DROP TABLE IF EXISTS `lock_table`;
CREATE TABLE `lock_table`
(
    `row_key`        varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
    `xid`            varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `transaction_id` bigint(20) NULL DEFAULT NULL,
    `branch_id`      bigint(20) NOT NULL,
    `resource_id`    varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `table_name`     varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `pk`             varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `status`         tinyint(4) NOT NULL DEFAULT 0 COMMENT '0:locked ,1:rollbacking',
    `gmt_create`     datetime NULL DEFAULT NULL,
    `gmt_modified`   datetime NULL DEFAULT NULL,
    PRIMARY KEY (`row_key`) USING BTREE,
    INDEX            `idx_status`(`status`) USING BTREE,
    INDEX            `idx_branch_id`(`branch_id`) USING BTREE,
    INDEX            `idx_xid_and_branch_id`(`xid`, `branch_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of lock_table
-- ----------------------------

SET
FOREIGN_KEY_CHECKS = 1;
```

2.4 创建业务数据库和表

注意：当前模拟的是每个微服务都单独使用各自的一个数据库，每个数据库中都需要新建一个undo_log表。

undo_log建表语句：

```sql
SET NAMES utf8mb4;
SET
FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for undo_log
-- ----------------------------
DROP TABLE IF EXISTS `undo_log`;
CREATE TABLE `undo_log`
(
    `id`            bigint(20) NOT NULL AUTO_INCREMENT,
    `branch_id`     bigint(20) NOT NULL,
    `xid`           varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `context`       varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `rollback_info` longblob                                                NOT NULL,
    `log_status`    int(11) NOT NULL,
    `log_created`   datetime                                                NOT NULL,
    `log_modified`  datetime                                                NOT NULL,
    `ext`           varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `ux_undo_log`(`xid`, `branch_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 23 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET
FOREIGN_KEY_CHECKS = 1;
```

t_account表建表语句：

```sql
SET NAMES utf8mb4;
SET
FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_account
-- ----------------------------
DROP TABLE IF EXISTS `t_account`;
CREATE TABLE `t_account`
(
    `id`      int(11) NOT NULL AUTO_INCREMENT,
    `user_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
    `amount`  int(11) NULL DEFAULT 0,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 123457 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET
FOREIGN_KEY_CHECKS = 1;
```

t_order建表语句：

```sql
SET NAMES utf8mb4;
SET
FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_order
-- ----------------------------
DROP TABLE IF EXISTS `t_order`;
CREATE TABLE `t_order`
(
    `id`             int(11) NOT NULL AUTO_INCREMENT,
    `user_id`        varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
    `commodity_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
    `count`          int(11) NULL DEFAULT 0,
    `money`          int(11) NULL DEFAULT 0,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 28 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET
FOREIGN_KEY_CHECKS = 1;
```

t_storage建表语句

```sql
SET NAMES utf8mb4;
SET
FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_storage
-- ----------------------------
DROP TABLE IF EXISTS `t_storage`;
CREATE TABLE `t_storage`
(
    `id`             int(11) NOT NULL AUTO_INCREMENT,
    `commodity_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
    `count`          int(11) NULL DEFAULT 0,
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `commodity_code`(`commodity_code`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET
FOREIGN_KEY_CHECKS = 1;
```

2.5 点击bin目录中的`seata-server.bat`启动seata-server。

启动完成之后可以在Nacos的服务列表中，看到名为`seata-server`的服务。

## 3.创建客户端应用并配置应用

示例项目Github主页：
[spring-cloud-alibaba-seata-example](https://github.com/anoperyan/spring-cloud-alibaba-seata-example)

3.1 每个微服务项目本地配置

```yaml
server:
  port: 9006

spring:
  application:
    name: @artifactId@
  cloud:
    nacos:
      discovery:
        # nacos服务注册地址
        server-addr: ${NOCAS_HOST:localhost}:${NACOS_PORT:8848}
      config:
        # nacos数据配置地址
        server-addr: ${spring.cloud.nacos.discovery.server-addr}
  config:
    import:
      # 导入所有微服务应用的通用配置
      - nacos:application-@profiles.active@.yml
      # 导入当前应用的当前环境的配置
      - nacos:${spring.application.name}-@profiles.active@.yml
```

3.2 所有微服务的通用配置（配置在Nacos的`application-dev.yml`文件）

```yaml
spring:
  datasource:
    # 请自行建立一个dog_test数据，里面不需要任何表
    url: jdbc:mysql://localhsost:3306/dog_test?useUnicode=true&characterEncoding=utf-8&serverTimezone=Asia/Shanghai
    username: root
    password: root
    driver-class-name: com.mysql.cj.jdbc.Driver
    druid:
      initial-size: 5 #连接池初始化大小9004
      min-idle: 1 #最小空闲连接数
      max-active: 5 #最大连接数
      max-wait: 2000 # 获取连接等待超时的时间
  thymeleaf:
    cache: false
    mode: HTML5
    suffix: .html
    prefix: classpath:/static/
  http:
    encoding:
      force: true
      charset: UTF-8
  mvc:
    throw-exception-if-no-handler-found: true  #  出现错误时, 直接抛出异常
  # 默认的时间格式
  jackson:
    date-format: yyyy-MM-dd HH:mm:ss
    time-zone: GMT+8
  redis:
    database: 0
    host: localhost
    port: 6379
    jedis:
      pool:
        max-active: 10
        max-idle: 2
        min-idle: 1
logging:
  level:
    org.springframework.*: debug
    net.hm.exam.*: debug
    org.ibatis.*: debug

# seata通用配置
# 当前服务需要这些信息去读取seata服务器的某些配置信息
seata:
  # 默认启动（将这两个配置关闭后，将不使用分布式事务）
  enabled: true
  # 默认启动（将这两个配置关闭后，将不使用分布式事务）
  enable-auto-data-source-proxy: true
  registry:
    # Seata服务注册方式为Nacos
    type: nacos
    # Seata服务器服务在Nacos的注册地址和分组信息
    nacos:
      server-addr: localhost:8848
      group: DEFAULT_GROUP
  config:
    # Seata服务器服务的配置方式为Nacos
    type: nacos
    nacos:
      server-addr: localhost:8848
      group: DEFAULT_GROUP
      # Seata服务器服务的配置信息文件的data-id
      data-id: seata-server.properties
  # 当前的分布式服务的逻辑分组
  tx-service-group: default_tx_group
  application-id: ${spring.application.name}
```

3.3 各个微服务的各自配置数据（Nacos端）
`account-service-dev.yml`文件：

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/dog_ec_account?useUnicode=true&characterEncoding=utf-8&serverTimezone=Asia/Shanghai
```

`order-service-dev.yml`文件：

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/dog_ec_order?useUnicode=true&characterEncoding=utf-8&serverTimezone=Asia/Shanghai
```

`storage-service-dev.yml`文件：

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/dog_ec_storage?useUnicode=true&characterEncoding=utf-8&serverTimezone=Asia/Shanghai
```