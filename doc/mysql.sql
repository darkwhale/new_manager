
create database if not exists `new_api` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;;

-- 管理员
drop table if exists `manager`;
create table `manager`(
    `manager_id` varchar(32) not null,
    `manager_name` varchar(32) not null comment '用户name',
    `password` varchar(32) not null comment '用户密码',
    `create_time` timestamp   not null default current_timestamp comment '创建时间',
    `update_time` timestamp   not null default current_timestamp on update current_timestamp comment '最后修改时间',
    primary key(`manager_id`)
)engine=InnoDB default charset=utf8;

-- 用户
drop table if exists `user`;
create table `user`
(
    `user_id` varchar(32) not null comment '用户id',
    `api_id` varchar(16) not null comment 'api id',
    `person_name` varchar(64) not null comment '用户名',
    `account` varchar(32) not null comment '账号',
    `password` varchar(32) not null comment '密码',
    `apartment` varchar(64) not null comment '部门',
    `create_time` timestamp   not null default current_timestamp comment '创建时间',
    `update_time` timestamp   not null default current_timestamp on update current_timestamp comment '最后修改时间',
    primary key(`user_id`)
)engine=InnoDB default charset=utf8;

-- 应用
drop table if exists `application`;
create table `application`(
    `application_id` int(32) not null auto_increment,
    `application_name` varchar(32) not null comment '应用名',
    `url` varchar(64) not null comment '网址',
    `remark` varchar(1024) not null comment '备注',
    `create_time` timestamp   not null default current_timestamp comment '创建时间',
    `update_time` timestamp   not null default current_timestamp on update current_timestamp comment '最后修改时间',
    primary key (`application_id`),
    unique key `uqe_name` (`application_name`) using btree
)engine=InnoDB default charset=utf8;

-- 接口
drop table if exists `api`;
create table `api` (
    `api_id` int(32) not null auto_increment comment 'id',
    `application_id` int(32) not null comment '应用id',
    `api_name` varchar(64) not null comment 'api名',
    `api_value` varchar(32) not null comment 'api接口',
    `remark` varchar(1024) not null comment '备注',
    `create_time` timestamp   not null default current_timestamp comment '创建时间',
    `update_time` timestamp   not null default current_timestamp on update current_timestamp comment '最后修改时间',
    primary key (`api_id`)
)engine=InnoDB default charset=utf8;

-- 权限
drop table if exists `authority`;
create table `authority`(
    `authority_id` int(32) not null auto_increment,
    `user_id` varchar(32) not null comment '用户id',
    `application_id` int(32) not null comment 'app id',
    `create_time` timestamp   not null default current_timestamp comment '创建时间',
    `update_time` timestamp   not null default current_timestamp on update current_timestamp comment '最后修改时间',
    primary key (`authority_id`)
)engine=InnoDB default charset=utf8;

-- 权限历史
drop table if exists `authority_history`;
create table `authority_history`(
    `history_id` int(32) not null auto_increment,
    `user_id` varchar(32) not null comment '用户id',
    `application_id` varchar(32) not null comment 'app id',
    `action` tinyint(4) not null comment '操作，0添加，1删除',
    `create_time` timestamp   not null default current_timestamp comment '创建时间',
    primary key (`history_id`)
)engine=InnoDB default charset=utf8;

-- 访问日志
drop table if exists `manipulate`;
create table `manipulate` (
    `manipulate_id` int(32) not null auto_increment,
    `user_id` varchar(32) not null comment '用户id',
    `application_id` int(32) not null comment 'application id',
    `time` timestamp not null default current_timestamp comment '创建时间',
    primary key (`manipulate_id`)
)engine=InnoDB default charset=utf8;

-- 权限申请
drop table if exists `apply`;
create table `apply` (
    `apply_id` int(32) not null auto_increment,
    `user_id` varchar(32) not null,
    `application_id` int(32) not null,
    `status` int(4) not null default '0' comment '0:待批准, 1:已批准, 2:已拒绝',
    `legal_time` int(32) not null default '30' comment '有效时间，天',
    `time` timestamp   not null default current_timestamp  on update current_timestamp  comment '创建时间',
    primary key (`apply_id`)
)engine=InnoDB default charset=utf8;
