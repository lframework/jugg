DROP TABLE IF EXISTS `op_logs`;
CREATE TABLE `op_logs`
(
    `id`          varchar(32)  NOT NULL COMMENT 'ID',
    `name`        varchar(200) NOT NULL COMMENT '日志名称',
    `log_type`    tinyint(3) NOT NULL COMMENT '类别',
    `create_by`   varchar(32)  NOT NULL COMMENT '创建人',
    `create_time` datetime     NOT NULL COMMENT '创建时间',
    `extra`       longtext COMMENT '补充信息',
    `ip`          varchar(100) NOT NULL COMMENT 'IP地址',
    PRIMARY KEY (`id`),
    KEY           `create_by` (`create_by`),
    KEY           `create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `recursion_mapping`;
CREATE TABLE `recursion_mapping`
(
    `id`        varchar(32) NOT NULL,
    `node_id`   varchar(32) NOT NULL COMMENT '节点ID',
    `node_type` tinyint(3) NOT NULL COMMENT '节点类型',
    `path`      longtext    NOT NULL COMMENT '从顶点到当前结点的路径，用,分割',
    `level`     int(11) NOT NULL COMMENT '节点层级',
    PRIMARY KEY (`id`),
    UNIQUE KEY `node_id` (`node_id`,`node_type`) USING BTREE,
    KEY         `node_type` (`node_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept`
(
    `id`          varchar(32)  NOT NULL COMMENT 'ID',
    `code`        varchar(20)  NOT NULL COMMENT '编号',
    `name`        varchar(100) NOT NULL COMMENT '名称',
    `short_name`  varchar(20)  NOT NULL COMMENT '简称',
    `parent_id`   varchar(32)           DEFAULT NULL COMMENT '父级ID',
    `available`   tinyint(1) NOT NULL COMMENT '状态',
    `description` varchar(200) NOT NULL DEFAULT '' COMMENT '备注',
    `create_by`   varchar(32)  NOT NULL COMMENT '创建人',
    `create_time` datetime     NOT NULL COMMENT '创建时间',
    `update_by`   varchar(32)  NOT NULL COMMENT '修改人',
    `update_time` datetime     NOT NULL COMMENT '修改时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `code` (`code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`
(
    `id`          varchar(32)  NOT NULL COMMENT 'ID',
    `code`        varchar(20)  NOT NULL COMMENT '编号',
    `name`        varchar(200)          DEFAULT NULL COMMENT '名称（前端使用）',
    `title`       varchar(20)  NOT NULL COMMENT '标题',
    `component`   varchar(200)          DEFAULT NULL COMMENT '组件（前端使用）',
    `parent_id`   varchar(32)           DEFAULT NULL COMMENT '父级ID',
    `path`        varchar(200)          DEFAULT NULL COMMENT '路由路径（前端使用）',
    `no_cache`    tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否缓存（前端使用）',
    `display`     tinyint(3) NOT NULL COMMENT '类型 0-目录 1-菜单 2-功能',
    `hidden`      tinyint(1) DEFAULT '0' COMMENT '是否隐藏（前端使用）',
    `permission`  varchar(200)          DEFAULT NULL COMMENT '权限',
    `is_special`  tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否特殊菜单',
    `available`   tinyint(1) NOT NULL DEFAULT '1' COMMENT '状态',
    `description` varchar(200) NOT NULL DEFAULT '' COMMENT '备注',
    `create_by`   varchar(32)  NOT NULL COMMENT '创建人ID',
    `create_time` datetime     NOT NULL COMMENT '创建时间',
    `update_by`   varchar(32)  NOT NULL COMMENT '修改人ID',
    `update_time` datetime     NOT NULL COMMENT '修改时间',
    PRIMARY KEY (`id`),
    KEY           `code` (`code`,`name`,`title`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统菜单';
INSERT INTO `sys_menu`
VALUES ('1000', '1000', 'System', '系统管理', '', null, '/system', '0', '0', '0', '', '1', '1', '', '1',
        '2021-07-04 00:22:05', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu`
VALUES ('1000001', '1000001', 'Menu', '菜单管理', '/system/menu/index', '1000', '/system/menu', '0', '1', '0',
        'system:menu:query', '1', '1', '', '1', '2021-05-08 18:37:01', '1', '2021-12-09 17:54:42');
INSERT INTO `sys_menu`
VALUES ('1000001001', '1000001001', '', '新增菜单', '', '1000001', '', '0', '2', '0', 'system:menu:add', '1', '1', '', '1',
        '2021-05-12 22:50:27', '1', '2021-12-09 17:54:42');
INSERT INTO `sys_menu`
VALUES ('1000001002', '1000001002', '', '修改菜单', '', '1000001', '', '0', '2', '0', 'system:menu:modify', '1', '1', '',
        '1', '2021-05-12 23:23:33', '1', '2021-12-09 17:54:42');
INSERT INTO `sys_menu`
VALUES ('1000001003', '1000001003', '', '删除菜单', '', '1000001', '', '0', '2', '0', 'system:menu:delete', '1', '1', '',
        '1', '2021-05-12 23:24:36', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu`
VALUES ('1000002', '1000002', 'Dept', '部门管理', '/system/dept/index', '1000', '/dept', '0', '1', '0', 'system:dept:query',
        '1', '1', '', '1', '2021-07-05 01:09:27', '1', '2021-07-05 01:09:27');
INSERT INTO `sys_menu`
VALUES ('1000002001', '1000002001', '', '新增部门', '', '1000002', '', '0', '2', '0', 'system:dept:add', '1', '1', '', '1',
        '2021-06-27 01:33:31', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu`
VALUES ('1000002002', '1000002002', '', '修改部门', '', '1000002', '', '0', '2', '0', 'system:dept:modify', '1', '1', '',
        '1', '2021-06-27 01:33:47', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu`
VALUES ('1000003', '1000003', 'Position', '岗位管理', '/system/position/index', '1000', '/position', '0', '1', '0',
        'system:position:query', '1', '1', '', '1', '2021-07-01 23:26:17', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu`
VALUES ('1000003001', '1000003001', '', '新增岗位', '', '1000003', '', '0', '2', '0', 'system:position:add', '1', '1', '',
        '1', '2021-06-30 00:32:17', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu`
VALUES ('1000003002', '1000003002', '', '修改岗位', '', '1000003', '', '0', '2', '0', 'system:position:modify', '1', '1',
        '', '1', '2021-06-30 00:32:45', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu`
VALUES ('1000004', '1000004', 'Role', '角色管理', '/system/role/index', '1000', '/role', '0', '1', '0', 'system:role:query',
        '1', '1', '', '1', '2021-07-04 00:35:49', '1', '2021-07-04 00:35:49');
INSERT INTO `sys_menu`
VALUES ('1000004001', '1000004001', '', '新增角色', '', '1000004', '', '0', '2', '0', 'system:role:add', '1', '1', '', '1',
        '2021-06-30 00:32:17', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu`
VALUES ('1000004002', '1000004002', '', '修改角色', '', '1000004', '', '0', '2', '0', 'system:role:modify', '1', '1', '',
        '1', '2021-06-30 00:32:45', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu`
VALUES ('1000004003', '1000004003', '', '角色授权', '', '1000004', '', '0', '2', '0', 'system:role:permission', '1', '1',
        '', '1', '2021-06-30 00:32:45', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu`
VALUES ('1000005', '1000005', 'User', '用户管理', '/system/user/index', '1000', '/user', '0', '1', '0', 'system:user:query',
        '1', '1', '', '1', '2021-07-05 01:08:40', '1', '2021-07-05 01:08:40');
INSERT INTO `sys_menu`
VALUES ('1000005001', '1000005001', '', '新增用户', '', '1000005', '', '0', '2', '0', 'system:user:add', '1', '1', '', '1',
        '2021-06-30 00:32:17', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu`
VALUES ('1000005002', '1000005002', '', '修改用户', '', '1000005', '', '0', '2', '0', 'system:user:modify', '1', '1', '',
        '1', '2021-06-30 00:32:45', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu`
VALUES ('1000005003', '1000005003', '', '用户授权', '', '1000005', '', '0', '2', '0', 'system:user:permission', '1', '1',
        '', '1', '2021-06-30 00:32:45', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu`
VALUES ('1000006', '1000006', 'Oplog', '操作日志', '/system/oplog/index', '1000', '/oplog', '0', '1', '0',
        'system:oplog:query', '1', '1', '', '1', '2021-07-05 01:08:40', '1', '2021-07-05 01:08:40');

DROP TABLE IF EXISTS `sys_position`;
CREATE TABLE `sys_position`
(
    `id`          varchar(32)  NOT NULL COMMENT 'ID',
    `code`        varchar(20)  NOT NULL COMMENT '岗位编号',
    `name`        varchar(20)  NOT NULL COMMENT '岗位名称',
    `available`   tinyint(1) NOT NULL COMMENT '状态',
    `description` varchar(200) NOT NULL DEFAULT '' COMMENT '备注',
    `create_by`   varchar(32)  NOT NULL COMMENT '创建人',
    `create_time` datetime     NOT NULL COMMENT '创建时间',
    `update_by`   varchar(32)  NOT NULL COMMENT '修改人',
    `update_time` datetime     NOT NULL COMMENT '修改时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `code` (`code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统岗位';

DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`
(
    `id`          varchar(32)  NOT NULL COMMENT 'ID',
    `code`        varchar(20)  NOT NULL COMMENT '编号',
    `name`        varchar(20)  NOT NULL COMMENT '名称',
    `permission`  varchar(200)          DEFAULT NULL COMMENT '权限',
    `available`   tinyint(1) NOT NULL COMMENT '状态',
    `description` varchar(200) NOT NULL DEFAULT '' COMMENT '备注',
    `create_by`   varchar(32)  NOT NULL COMMENT '创建人',
    `create_time` datetime     NOT NULL COMMENT '创建时间',
    `update_by`   varchar(32)  NOT NULL COMMENT '修改人',
    `update_time` datetime     NOT NULL COMMENT '修改时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `code` (`code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu`
(
    `id`      varchar(32) NOT NULL COMMENT 'ID',
    `role_id` varchar(32) NOT NULL COMMENT '角色ID',
    `menu_id` varchar(32) NOT NULL COMMENT '菜单ID',
    PRIMARY KEY (`id`),
    UNIQUE KEY `role_id, menu_id` (`role_id`,`menu_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`
(
    `id`          varchar(32)  NOT NULL COMMENT 'ID',
    `code`        varchar(20)  NOT NULL COMMENT '编号',
    `name`        varchar(20)  NOT NULL COMMENT '姓名',
    `username`    varchar(30)  NOT NULL COMMENT '用户名',
    `password`    varchar(100) NOT NULL COMMENT '密码',
    `email`       varchar(100)          DEFAULT NULL COMMENT '邮箱',
    `telephone`   varchar(11)           DEFAULT NULL COMMENT '联系电话',
    `gender`      tinyint(3) NOT NULL DEFAULT '0' COMMENT '性别 0-未知 1-男 2-女',
    `available`   tinyint(1) NOT NULL DEFAULT '1' COMMENT '状态 1-在用 0停用',
    `description` varchar(200) NOT NULL DEFAULT '' COMMENT '备注',
    `create_by`   varchar(32)  NOT NULL COMMENT '创建人',
    `create_time` datetime     NOT NULL COMMENT '创建时间',
    `update_by`   varchar(32)  NOT NULL COMMENT '修改人',
    `update_time` datetime     NOT NULL COMMENT '修改时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `code` (`code`),
    UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `sys_user`
VALUES ('1', '001', '系统管理员', 'admin', '$2a$10$9fLyVGkeUNdrce5d.b34YO3UWTAebuM72eZpdf.xJ4qPisGfzZqjq', 'aaa@a.com',
        '13388888888', '0', '1', '', '1', '2021-04-22 22:00:27', '1', '2021-12-09 19:27:23');

DROP TABLE IF EXISTS `sys_user_dept`;
CREATE TABLE `sys_user_dept`
(
    `id`      varchar(32) NOT NULL,
    `user_id` varchar(32) NOT NULL COMMENT '用户ID',
    `dept_id` varchar(32) NOT NULL COMMENT '部门ID',
    PRIMARY KEY (`id`),
    UNIQUE KEY `user_id` (`user_id`,`dept_id`) USING BTREE,
    KEY       `dept_id` (`dept_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `sys_user_position`;
CREATE TABLE `sys_user_position`
(
    `id`          varchar(32) NOT NULL,
    `user_id`     varchar(32) NOT NULL COMMENT '用户ID',
    `position_id` varchar(32) NOT NULL COMMENT '岗位ID',
    PRIMARY KEY (`id`),
    UNIQUE KEY `user_id` (`user_id`,`position_id`) USING BTREE,
    KEY           `position_id` (`position_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`
(
    `id`      varchar(32) NOT NULL COMMENT 'ID',
    `user_id` varchar(32) NOT NULL COMMENT '用户ID',
    `role_id` varchar(32) NOT NULL COMMENT '角色ID',
    PRIMARY KEY (`id`),
    UNIQUE KEY `user_id, role_id` (`user_id`,`role_id`) USING BTREE,
    KEY       `role_id` (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `sys_menu_collect`;
CREATE TABLE `sys_menu_collect` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `user_id` varchar(32) NOT NULL COMMENT '用户ID',
  `menu_id` varchar(32) NOT NULL COMMENT '菜单ID',
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_id, menu_id` (`user_id`,`menu_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
