### 系统功能

|     | 功能    | 描述                              |
|-----|-------|---------------------------------|
|     | 用户管理  | 用户是系统操作者，该功能主要完成系统用户配置          |
| ⭐️  | 在线用户  | 当前系统中活跃用户状态监控，支持手动踢下线           |
|     | 角色管理  | 角色菜单权限分配、设置角色按机构进行数据范围权限划分      |
|     | 菜单管理  | 配置系统菜单、操作权限、按钮权限标识等，本地缓存提供性能    |
|     | 部门管理  | 配置系统组织机构（公司、部门、小组），树结构展现支持数据权限  |
|     | 岗位管理  | 配置系统用户所属担任职务                    |
| 🚀  | 租户管理  | 配置系统租户，支持 SaaS 场景下的多租户功能        |
| 🚀  | 租户套餐  | 配置租户套餐，自定每个租户的菜单、操作、按钮的权限       |
|     | 字典管理  | 对系统中经常使用的一些较为固定的数据进行维护          |
| 🚀  | 短信管理  | 短信渠道、短息模板、短信日志，对接阿里云、腾讯云等主流短信平台 |
| 🚀  | 邮件管理  | 邮箱账号、邮件模版、邮件发送日志，支持所有邮件平台       |
| 🚀  | 站内信   | 系统内的消息通知，提供站内信模版、站内信消息          |
| 🚀  | 操作日志  | 系统正常操作日志记录和查询，集成 Swagger 生成日志内容 |
| ⭐️  | 登录日志  | 系统登录日志记录查询，包含登录异常               |
| 🚀  | 错误码管理 | 系统所有错误码的管理，可在线修改错误提示，无需重启服务     |
|     | 通知公告  | 系统通知公告信息发布维护                    |
| 🚀  | 敏感词   | 配置系统敏感词，支持标签分组                  |
| 🚀  | 应用管理  | 管理 SSO 单点登录的应用，支持多种 OAuth2 授权方式 |
| 🚀  | 地区管理  | 展示省份、城市、区镇等城市信息，支持 IP 对应城市      |

![功能图](/.image/common/system-feature.png)

### 基础设施

|    | 功能        | 描述                                           |
|----|-----------|----------------------------------------------|
| 🚀 | 代码生成      | 前后端代码的生成（Java、Vue、SQL、单元测试），支持 CRUD 下载       |
| 🚀 | 系统接口      | 基于 Swagger 自动生成相关的 RESTful API 接口文档          |
| 🚀 | 数据库文档     | 基于 Screw 自动生成数据库文档，支持导出 Word、HTML、MD 格式      |
|    | 表单构建      | 拖动表单元素生成相应的 HTML 代码，支持导出 JSON、Vue 文件         |
| 🚀 | 配置管理      | 对系统动态配置常用参数，支持 SpringBoot 加载                 |
| ⭐️ | 定时任务      | 在线（添加、修改、删除)任务调度包含执行结果日志                     |
| 🚀 | 文件服务      | 支持将文件存储到 S3（MinIO、阿里云、腾讯云、七牛云）、本地、FTP、数据库等   | 
| 🚀 | WebSocket | 提供 WebSocket 接入示例，支持一对一、一对多发送方式              |
| 🚀 | API 日志    | 包括 RESTful API 访问日志、异常日志两部分，方便排查 API 相关的问题   |
|    | MySQL 监控  | 监视当前系统数据库连接池状态，可进行分析SQL找出系统性能瓶颈              |
|    | Redis 监控  | 监控 Redis 数据库的使用情况，使用的 Redis Key 管理           |
| 🚀 | 消息队列      | 基于 Redis 实现消息队列，Stream 提供集群消费，Pub/Sub 提供广播消费 |
| 🚀 | Java 监控   | 基于 Spring Boot Admin 实现 Java 应用的监控           |
| 🚀 | 链路追踪      | 接入 SkyWalking 组件，实现链路追踪                      |
| 🚀 | 日志中心      | 接入 SkyWalking 组件，实现日志中心                      |
| 🚀 | 服务保障      | 基于 Redis 实现分布式锁、幂等、限流功能，满足高并发场景              |
| 🚀 | 日志服务      | 轻量级日志中心，查看远程服务器的日志                           |
| 🚀 | 单元测试      | 基于 JUnit + Mockito 实现单元测试，保证功能的正确性、代码的质量等    |

![功能图](/.image/common/infra-feature.png)



## 🐨 技术栈

### 微服务

| 项目                        | 说明                 |
|---------------------------|--------------------|
| `smartpark-dependencies`  | Maven 依赖版本管理       |
| `smartpark-framework`     | Java 框架拓展          |
| `smartpark-server`        | 管理后台 + 用户 APP 的服务端 |
| `smartpark-module-system` | 系统功能的 Module 模块    |
| `smartpark-module-infra`  | 基础设施的 Module 模块    |
| `smartpark-module-bus`     | 园区业务的 Module 模块    |


### 框架

| 框架                                                                                          | 说明               | 版本          | 补充说明                                                            |
|---------------------------------------------------------------------------------------------|------------------|-------------|-----------------------------------------------------------------|
| [Spring Cloud Alibaba](https://github.com/alibaba/spring-cloud-alibaba)                     | 微服务框架            | 2022.0.0.0  |                                                                 |
| [Nacos](https://github.com/alibaba/nacos)                                                   | 配置中心 & 注册中心      | 2.2.1       |                |
| [RocketMQ](https://github.com/apache/rocketmq)                                              | 消息队列             | 4.9.4       |             |
| [Sentinel](https://github.com/alibaba/sentinel)                                             | 服务保障             | 1.8.6       |             |
| [XXL Job](https://github.com/xuxueli/xxl-job)                                               | 定时任务             | 2.4.0       |        |
| [Spring Cloud Gateway](https://github.com/spring-cloud/spring-cloud-gateway)                | 服务网关             | 4.1.0       |  |
| [Seata](https://github.com/seata/seata)                                                     | 分布式事务            | 1.6.1       |                |
| [MySQL](https://www.mysql.com/cn/)                                                          | 数据库服务器           | 5.7 / 8.0+  |                                                                 |
| [Druid](https://github.com/alibaba/druid)                                                   | JDBC 连接池、监控组件    | 1.2.20      |       |
| [MyBatis Plus](https://mp.baomidou.com/)                                                    | MyBatis 增强工具包    | 3.5.4.1     |              |
| [Dynamic Datasource](https://dynamic-datasource.com/)                                       | 动态数据源            | 4.2.0       |      |
| [Redis](https://redis.io/)                                                                  | key-value 数据库    | 5.0 / 6.0   |                                                                 |
| [Redisson](https://github.com/redisson/redisson)                                            | Redis 客户端        | 3.25.0      |               |
| [Spring MVC](https://github.com/spring-projects/spring-framework/tree/master/spring-webmvc) | MVC 框架           | 6.1.1       |                    |
| [Spring Security](https://github.com/spring-projects/spring-security)                       | Spring 安全框架      | 6.2.0       |       |
| [Hibernate Validator](https://github.com/hibernate/hibernate-validator)                     | 参数校验组件           | 8.0.1       |           |
| [Flowable](https://github.com/flowable/flowable-engine)                                     | 工作流引擎            | 7.0.0       |                                 |
| [Knife4j](https://gitee.com/xiaoym/knife4j)                                                 | Swagger 增强 UI 实现 | 4.4.0       |              |
| [SkyWalking](https://skywalking.apache.org/)                                                | 分布式应用追踪系统        | 9.0.0       |           |
| [Spring Boot Admin](https://github.com/codecentric/spring-boot-admin)                       | Spring Boot 监控平台 | 3.6.1       |                 |
| [Jackson](https://github.com/FasterXML/jackson)                                             | JSON 工具库         | 2.15.3      |                                                                 |
| [MapStruct](https://mapstruct.org/)                                                         | Java Bean 转换     | 1.5.5.Final |             |
| [Lombok](https://projectlombok.org/)                                                        | 消除冗长的 Java 代码    | 1.18.30     |               |
| [JUnit](https://junit.org/junit5/)                                                          | Java 单元测试框架      | 5.10.1      |                                                                 |
| [Mockito](https://github.com/mockito/mockito)                                               | Java Mock 框架     | 5.7.0       |                                                                 |

