# Jugg

Jugg 是面向业务系统的后端基础框架，基于 Java 21、Spring Boot 3 与 Spring Cloud 体系进行封装，目标是把常见的后台开发能力沉淀成可复用的 starter，减少项目初始化和重复造轮子的成本。

## 仓库结构

- `common`：通用工具、异常、常量与基础能力
- `web-starter`：单体 Web 应用能力，集成权限、租户、缓存、文件、任务、OpenAPI 等
- `cloud-starter`：Spring Cloud 场景下的公共能力封装
- `mq-starter`：消息队列能力，包含 `mq-core`、`activemq-starter`、`rabbitmq-starter`
- `bpm-starter`：流程与工作流相关能力

按需引用即可，不要求业务项目一次性依赖全部模块。

## 内置能力

- 系统管理：菜单、部门、角色、用户、操作日志等基础后台能力
- 数据访问：MyBatis-Plus、动态数据源、分页、租户上下文
- 安全体系：Sa-Token、权限拦截、统一响应与异常处理
- 基础设施：Redis、RabbitMQ、文件上传、定时任务、OpenAPI/Knife4j
- 扩展能力：工作流、消息导出、云端基础集成

## 快速开始

### 环境要求

- JDK 21
- Maven 3.9+
- MySQL 5.7.18+
- Redis 4.0.8+
- 可选：RabbitMQ、Nacos、Seata

### 构建命令

```bash
mvn clean install
mvn -pl web-starter -am test
```

### 业务项目引用

业务项目通常通过父 BOM 统一版本：

```xml
<dependencyManagement>
  <dependencies>
    <dependency>
      <groupId>com.lframework</groupId>
      <artifactId>parent</artifactId>
      <version>5.0.1</version>
      <type>pom</type>
      <scope>import</scope>
    </dependency>
  </dependencies>
</dependencyManagement>
```

然后按需引入 `web-starter`、`cloud-starter`、`mq-starter` 或 `bpm-starter`。

## 自定义与替换

内置系统功能都以 starter Bean 的形式提供，业务项目可以通过继承默认实现或直接注册同类型 Bean 进行替换。例如不使用默认用户体系时，可在业务项目中提供自定义的用户服务实现，覆盖默认注入链。菜单、部门、角色、用户、日志等模块都遵循同样的扩展方式。

## 主要技术栈

- Spring Boot 3.5.0
- Spring Cloud 2025.0.0
- Spring Cloud Alibaba 2025.0.0.0
- MyBatis-Plus 3.5.6
- Sa-Token 1.39.0
- Knife4j 4.5.0
- Lombok 1.18.32
- Hutool 5.7.17
- EasyExcel 4.0.3

## License

项目使用 Apache 2.0 许可证，请遵守许可证约束。
