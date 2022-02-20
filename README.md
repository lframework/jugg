### 项目介绍
Jugg名称来源于Dota游戏中的剑圣。
Jugg是用来开发业务系统的脚手架，将常用的开发框架整合并配置，以此来简化业务系统的搭建工作。
#### 注意事项
目前项目的登录主要是使用session而不是jwt，后续会增加jwt-starter提供更多的选择。
### 项目构成
* common
* mybatis-starter
* redis-starter
* security-starter
* session-starter
* web-starter
* gen

以上是项目中所有的module。

所有的module的名称即为主要封装的能力。例如：web-starter表示依赖此starter即可让项目拥有Web后台的能力。

可以根据不同的需求来决定具体使用哪些starter。例如：无需用户登录、授权，那么就不需要使用security-starter。

### 内置功能
* 菜单管理：维护系统菜单、权限。
* 部门管理：维护系统组织机构。
* 岗位管理：维护系统人员所属岗位信息。
* 角色管理：维护角色人员所属角色信息。
* 用户管理：维护系统人员信息。
* 操作日志：查询系统人员的操作日志。
* 代码生成器：代码生成功能。

## 注意事项
以上内置功能均写在了starter里面，因为这些功能的默认数据库表是根据自己需求创建的表，通常会因为各种原因导致开发人员并不想使用这些表，而是使用自己的表，那么这个时候如果不能很好的支持就会比较难受了。出现这种情况时，只需要在业务系统继承、重写内置Bean或直接创建响应的Bean，即可。

例如：现在不想使用内置的用户表，而是使用自定义的用户表，需要进行如下操作：

* 继承DefaultUserServiceImpl并将do*方法全部重写或新建类并实现IUserService接口，然后将自定义的类注册成Bean
* 继承DefaultSysUserServiceImpl并将do*方法全部重写或新建类并实现ISysUserService，然后将自定义的类注册成Bean

完成以上两步操作后，即可将内置的用户表替换成自定义的用户表。其他的内置功能同理。

以下是内置的Bean的介绍：
* 菜单管理：SysMenuController、ISysMenuService（实现类：DefaultSysMenuServiceImpl）
* 部门管理：SysDeptController、ISysDeptService（实现类：DefaultSysDeptServiceImpl）
* 岗位管理：SysPositionController、ISysPositionService（实现类：DefaultSysPositionServiceImpl）
* 角色管理：SysRoleController、ISysRoleService（实现类：DefaultSysRoleServiceImpl）
* 用户管理：SysUserController、ISysUserService（实现类：DefaultSysUserServiceImpl）
* 操作日志：OpLogController、IOpLogsService（实现类：DefaultOpLogsServiceImpl）
* 用户所属部门：ISysUserDeptService（实现类：DefaultSysUserDeptServiceImpl）
* 用户所属角色：ISysUserRoleService（实现类：DefaultSysUserRoleServiceImpl）
* 用户所属岗位：ISysUserPositionService（实现类：DefaultSysUserPositionServiceImpl）
* 角色授权相关：ISysRoleMenuService（实现类：DefaultSysRoleMenuServiceImpl）、IMenuService（实现类：DefaultMenuServiceImpl）

需要自定义哪些功能就重写哪个类并注册成Bean即可。

这些内置功能使用前提是：使用security-starter，因为这些功能需要依赖用户的登录、授权等，这些全部是由security-starter决定的。

### 主要技术框架
* springboot 2.2.2.RELEASE
* myBatis-plus 3.4.2
* spring-session-data-redis 2.2.0.RELEASE
* HuTool 5.7.17 (只依赖了HuTool的core module)
* lombok 1.18.10
* EasyExcel 2.2.10（内置了两种导出excel方式：一次性导出、分段导出（只支持简单表头））

### 开发环境
* JDK 1.8
* Mysql 5.7.18
* Redis 4.0.8（版本可以根据自己的redis进行调整，项目本身依赖Redis的功能很简单，就是两部分：缓存、Session，不会出现大的兼容问题）

### License
项目使用LGPL3.0许可证，请遵守此许可证的限制条件。

### 其他说明
* 目前项目刚刚发布，使用人数很少，暂不提供交流群，Bug请提Issue。
* 作者是一个只有几年开发经验的菜鸡，如有错误之处，望斧正。
* 前端项目Gitee地址：[点此进入][frontGitee]

[frontGitee]: https://gitee.com/lframework/jugg-front
