/*
SQLyog Community v13.1.6 (64 bit)
MySQL - 8.0.12 : Database - netdisk
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE = ''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS = @@UNIQUE_CHECKS, UNIQUE_CHECKS = 0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS = @@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS = 0 */;
/*!40101 SET @OLD_SQL_MODE = @@SQL_MODE, SQL_MODE = 'NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES = @@SQL_NOTES, SQL_NOTES = 0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS */`netdisk` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `netdisk`;

/*Table structure for table `tb_chunk` */

DROP TABLE IF EXISTS `tb_chunk`;

CREATE TABLE `tb_chunk`
(
    `id`    bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增id',
    `md5`   varchar(64) DEFAULT NULL COMMENT '文件md5',
    `chunk` int(11)     DEFAULT NULL COMMENT '已上传的分片',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='存储已经上传的分片';

/*Data for the table `tb_chunk` */

/*Table structure for table `tb_files` */

DROP TABLE IF EXISTS `tb_files`;

CREATE TABLE `tb_files`
(
    `file_id`         bigint(20) unsigned NOT NULL COMMENT '文件id',
    `real_name`       varchar(255)                                           DEFAULT NULL COMMENT '文件真实名称',
    `encryption_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '文件md5',
    `storage_path`    varchar(255)                                           DEFAULT NULL COMMENT '文件存储路径',
    `extension`       varchar(10)                                            DEFAULT NULL COMMENT '文件扩展名',
    `short_url`       varchar(255)                                           DEFAULT NULL COMMENT '文件短地址',
    `size`            bigint(20)                                             DEFAULT NULL COMMENT '文件大小',
    `file_type`       varchar(10)                                            DEFAULT NULL COMMENT '文件类型',
    `dir`             text CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT '所属文件夹路径',
    `icon`            varchar(20)                                            DEFAULT NULL COMMENT '文件图标',
    `is_hidden`       tinyint(1)          NOT NULL                           DEFAULT '0' COMMENT '是否隐藏（0-未隐藏，1-隐藏）',
    `is_deleted`      tinyint(1)          NOT NULL                           DEFAULT '0' COMMENT '是否已删除（0-未删除，1-已删除）',
    `creator`         varchar(64)                                            DEFAULT NULL COMMENT '创建者',
    `gmt_create`      datetime                                               DEFAULT NULL COMMENT '创建时间',
    `gmt_modified`    datetime                                               DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`file_id`),
    KEY `creator` (`creator`(20)),
    KEY `dir` (`dir`(20)) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  ROW_FORMAT = COMPACT COMMENT ='文件表';

/*Data for the table `tb_files` */

/*Table structure for table `tb_folders` */

DROP TABLE IF EXISTS `tb_folders`;

CREATE TABLE `tb_folders`
(
    `folder_id`    bigint(20)                                      NOT NULL COMMENT '目录id',
    `folder_name`  varchar(255)                                             DEFAULT NULL COMMENT '文件夹名',
    `dir`          text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '文件夹路径',
    `is_hidden`    tinyint(1)                                      NOT NULL DEFAULT '0' COMMENT '是否隐藏（0-未隐藏，1-隐藏）',
    `is_deleted`   tinyint(1)                                               DEFAULT '0' COMMENT '是否删除（0-未删除，1-已删除）',
    `creator`      varchar(64)                                              DEFAULT NULL COMMENT '创建者',
    `gmt_create`   datetime                                                 DEFAULT NULL COMMENT '创建时间',
    `gmt_modified` datetime                                                 DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`folder_id`),
    KEY `creator` (`creator`),
    KEY `dir` (`dir`(20)) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  ROW_FORMAT = COMPACT COMMENT ='目录表';

/*Data for the table `tb_folders` */

/*Table structure for table `tb_friends` */

DROP TABLE IF EXISTS `tb_friends`;

CREATE TABLE `tb_friends`
(
    `id`                    bigint(20) unsigned NOT NULL COMMENT '主键ID',
    `username`              varchar(64) DEFAULT NULL COMMENT '用户名',
    `friend`                varchar(64) DEFAULT NULL COMMENT '好友用户名',
    `user_to_friend_remark` varchar(15) DEFAULT NULL COMMENT '用户给好友的备注',
    `friend_to_user_remark` varchar(15) DEFAULT NULL COMMENT '好友给用户的备注',
    `gmt_create`            datetime    DEFAULT NULL COMMENT '创建时间',
    `gmt_modified`          datetime    DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`),
    KEY `username` (`username`(20)),
    KEY `friend_name` (`friend`(20))
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  ROW_FORMAT = COMPACT COMMENT ='好友表';

/*Data for the table `tb_friends` */

/*Table structure for table `tb_friends_application` */

DROP TABLE IF EXISTS `tb_friends_application`;

CREATE TABLE `tb_friends_application`
(
    `id`           bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `applicant`    varchar(64) DEFAULT NULL COMMENT '申请人',
    `respondent`   varchar(64) DEFAULT NULL COMMENT '被申请人',
    `message`      varchar(30) DEFAULT NULL COMMENT '验证消息',
    `is_agreed`    char(1)     DEFAULT '0' COMMENT '申请状态（0-未处理，1-同意，2-拒绝，默认0）',
    `is_viewed`    tinyint(1)  DEFAULT '0' COMMENT '是否查看（0-未查看，1-已查看，默认0）',
    `gmt_create`   datetime    DEFAULT NULL COMMENT '创建时间',
    `gmt_modified` datetime    DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`),
    KEY `respondent` (`respondent`(20))
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  ROW_FORMAT = COMPACT COMMENT ='好友申请表';

/*Data for the table `tb_friends_application` */

/*Table structure for table `tb_friends_session` */

DROP TABLE IF EXISTS `tb_friends_session`;

CREATE TABLE `tb_friends_session`
(
    `id`                   bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `username`             varchar(64)  DEFAULT NULL COMMENT '用户名',
    `friend`               varchar(64)  DEFAULT NULL COMMENT '好友用户名',
    `title`                varchar(255) DEFAULT NULL COMMENT '显示标题',
    `is_visited_by_user`   tinyint(1)   DEFAULT '1' COMMENT '是否显示(0-不显示，1-显示，默认1,对应username)',
    `is_visited_by_friend` tinyint(1)   DEFAULT '1' COMMENT '是否显示(0-不显示，1-显示，默认1，对应friend)',
    `share_time`           datetime     DEFAULT NULL COMMENT '分享时间',
    `gmt_create`           datetime     DEFAULT NULL COMMENT '创建时间',
    `gmt_modified`         datetime     DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    KEY `username` (`username`(20)),
    KEY `friend` (`friend`(20))
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  ROW_FORMAT = COMPACT COMMENT ='好友会话表';

/*Data for the table `tb_friends_session` */

/*Table structure for table `tb_friends_share` */

DROP TABLE IF EXISTS `tb_friends_share`;

CREATE TABLE `tb_friends_share`
(
    `id`           bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `share_id`     bigint(20)   DEFAULT NULL COMMENT '分享ID',
    `share_title`  varchar(100) DEFAULT NULL COMMENT '分享的标题',
    `distributors` varchar(64)  DEFAULT NULL COMMENT '分享人',
    `receiver`     varchar(64)  DEFAULT NULL COMMENT '接收者',
    `is_viewed`    tinyint(1)   DEFAULT '0' COMMENT '是否查看（0-未查看，1-已查看）',
    `gmt_create`   datetime     DEFAULT NULL COMMENT '分享时间',
    `gmt_modified` datetime     DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    KEY `distributors` (`distributors`(20)),
    KEY `receiver` (`receiver`(20))
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  ROW_FORMAT = COMPACT COMMENT ='好友文件分享表';

/*Data for the table `tb_friends_share` */

/*Table structure for table `tb_friends_share_group` */

DROP TABLE IF EXISTS `tb_friends_share_group`;

CREATE TABLE `tb_friends_share_group`
(
    `id`       bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `share_id` bigint(20) unsigned NOT NULL COMMENT '分享ID',
    `file_id`  bigint(20) unsigned NOT NULL COMMENT '文件(夹)ID',
    PRIMARY KEY (`id`),
    KEY `share_id` (`share_id`, `file_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='好友分享文件关系表';

/*Data for the table `tb_friends_share_group` */

/*Table structure for table `tb_menu` */

DROP TABLE IF EXISTS `tb_menu`;

CREATE TABLE `tb_menu`
(
    `menu_id`      bigint(20) NOT NULL COMMENT '主键ID',
    `menu_title`   varchar(32)                                            DEFAULT NULL COMMENT '菜单标题',
    `icon_class`   varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '菜单图标',
    `path`         varchar(20)                                            DEFAULT NULL COMMENT '菜单路径',
    `component`    varchar(30)                                            DEFAULT NULL COMMENT '组件名称',
    `level`        int(11)                                                DEFAULT NULL COMMENT '菜单层级',
    `parent_id`    bigint(20)                                             DEFAULT NULL COMMENT '父菜单ID',
    `gmt_create`   datetime                                               DEFAULT NULL COMMENT '创建时间',
    `gmt_modified` datetime                                               DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`menu_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='菜单表';

/*Data for the table `tb_menu` */

insert into `tb_menu`(`menu_id`, `menu_title`, `icon_class`, `path`, `component`, `level`, `parent_id`, `gmt_create`,
                      `gmt_modified`)
values (1001, '全部文件', 'el-icon-document-copy', '/', 'AllFile', 1, 0, '2021-03-11 20:25:10', '2021-03-11 20:25:13'),
       (1002, '图片', '', 'image', 'ImageCom', 2, 1001, '2021-03-11 20:26:28', '2021-03-11 20:26:31'),
       (1003, '文档', '', 'doc', 'DocumentCom', 2, 1001, '2021-03-11 20:27:22', '2021-03-11 20:27:25'),
       (1004, '视频', '', 'video', 'VideoCom', 2, 1001, '2021-03-11 20:27:43', '2021-03-11 20:27:46'),
       (1005, '种子', '', 'seed', 'SeedCom', 2, 1001, '2021-03-11 20:28:16', '2021-03-11 20:28:18'),
       (1006, '音乐', '', 'music', 'MusicCom', 2, 1001, '2021-03-11 20:28:37', '2021-03-11 20:28:39'),
       (1007, '其它', '', 'other', 'OtherCom', 2, 1001, '2021-03-11 20:29:11', '2021-03-11 20:29:15'),
       (1008, '我的分享', 'iconfont icon-share', 'myShare', 'MyShare', 1, 0, '2021-03-11 20:58:06', '2021-03-11 20:58:08'),
       (1009, '回收站', 'el-icon-delete', 'recycle', 'Recycle', 1, 0, '2021-03-11 20:59:25', '2021-03-11 20:59:27');

/*Table structure for table `tb_order` */

DROP TABLE IF EXISTS `tb_order`;

CREATE TABLE `tb_order`
(
    `order_no`          bigint(20) unsigned NOT NULL COMMENT '订单号',
    `username`          varchar(64)    DEFAULT NULL COMMENT '用户名',
    `payment_amount`    decimal(20, 2) DEFAULT NULL COMMENT '实际付款金额,单位是元,保留两位小数',
    `payment_method_id` int(11)        DEFAULT NULL COMMENT '支付方式',
    `status`            int(11)        DEFAULT NULL COMMENT '订单状态:0-已取消，10-未付款，20-已付款，40-交易成功，50-交易关闭',
    `group_id`          int(11)             NOT NULL COMMENT '购买商品的ID（角色组ID）',
    `payment_time`      datetime       DEFAULT NULL COMMENT '支付时间',
    `gmt_create`        datetime       DEFAULT NULL COMMENT '创建时间',
    `gmt_modified`      datetime       DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`order_no`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  ROW_FORMAT = COMPACT COMMENT ='订单表';

/*Data for the table `tb_order` */

/*Table structure for table `tb_pay_info` */

DROP TABLE IF EXISTS `tb_pay_info`;

CREATE TABLE `tb_pay_info`
(
    `id`             bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `username`       varchar(64) DEFAULT NULL COMMENT '用户名',
    `order_no`       bigint(20)  DEFAULT NULL COMMENT '订单号',
    `payment_number` varchar(30) DEFAULT NULL COMMENT '支付流水号',
    `payment_status` varchar(20) DEFAULT NULL COMMENT '支付状态',
    `gmt_create`     datetime    DEFAULT NULL COMMENT '创建时间',
    `gmt_modified`   datetime    DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  ROW_FORMAT = COMPACT COMMENT ='支付信息表';

/*Data for the table `tb_pay_info` */

/*Table structure for table `tb_payment_method` */

DROP TABLE IF EXISTS `tb_payment_method`;

CREATE TABLE `tb_payment_method`
(
    `id`             int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `payment_method` varchar(30) DEFAULT NULL COMMENT '支付方式',
    `gmt_create`     datetime    DEFAULT NULL COMMENT '创建时间',
    `gmt_modified`   datetime    DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1004
  DEFAULT CHARSET = utf8 COMMENT ='支付方式表';

/*Data for the table `tb_payment_method` */

insert into `tb_payment_method`(`id`, `payment_method`, `gmt_create`, `gmt_modified`)
values (1001, '支付宝', '2021-01-20 11:49:48', '2021-01-20 11:49:48'),
       (1002, '微信', '2021-01-20 11:49:48', '2021-01-20 11:49:48'),
       (1003, '银联', '2021-01-20 11:49:48', '2021-01-20 11:49:48');

/*Table structure for table `tb_url_share` */

DROP TABLE IF EXISTS `tb_url_share`;

CREATE TABLE `tb_url_share`
(
    `id`             bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `share_id`       bigint(20)   DEFAULT NULL COMMENT '分享ID',
    `share_title`    varchar(100) DEFAULT NULL COMMENT '分享标题',
    `link`           varchar(64)  DEFAULT NULL COMMENT '链接',
    `short_url`      varchar(7)   DEFAULT NULL COMMENT '短地址',
    `file_key`       varchar(4)   DEFAULT NULL COMMENT '提取码',
    `viewed_count`   int(11)      DEFAULT '0' COMMENT '浏览次数',
    `saved_count`    int(11)      DEFAULT '0' COMMENT '保存次数',
    `download_count` int(11)      DEFAULT '0' COMMENT '下载次数',
    `share_type`     tinyint(1)   DEFAULT NULL COMMENT '分享类型(0-公开 1-加密)',
    `username`       varchar(64)         NOT NULL COMMENT '分享者',
    `is_permanent`   tinyint(1)   DEFAULT NULL COMMENT '是否永久(0-永久 1-非永久)',
    `share_status`   int(11)      DEFAULT NULL COMMENT '分享状态(0-有效，1-失效， 2-已删除)',
    `failure_time`   datetime     DEFAULT NULL COMMENT '失效时间',
    `gmt_create`     datetime     DEFAULT NULL COMMENT '分享时间',
    `gmt_modified`   datetime     DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    KEY `username` (`username`(20))
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  ROW_FORMAT = COMPACT COMMENT ='文件分享表';

/*Data for the table `tb_url_share` */

/*Table structure for table `tb_url_share_group` */

DROP TABLE IF EXISTS `tb_url_share_group`;

CREATE TABLE `tb_url_share_group`
(
    `id`       bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `share_id` bigint(20) unsigned NOT NULL COMMENT '分享ID',
    `file_id`  bigint(20) unsigned NOT NULL COMMENT '文件(夹)ID',
    PRIMARY KEY (`id`),
    KEY `share_id` (`share_id`, `file_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='URL分享文件关系表';

/*Data for the table `tb_url_share_group` */

/*Table structure for table `tb_user` */

DROP TABLE IF EXISTS `tb_user`;

CREATE TABLE `tb_user`
(
    `user_id`            bigint(20)  NOT NULL COMMENT '用户ID',
    `group_id`           int(11)              DEFAULT NULL COMMENT '用户组id',
    `username`           varchar(64) NOT NULL DEFAULT '' COMMENT '用户名',
    `password`           varchar(60) NOT NULL COMMENT '用户密码',
    `salt`               varchar(30) NOT NULL COMMENT '盐',
    `status`             tinyint(1)           DEFAULT '1' COMMENT '账号状态（0-停用 1-启用）',
    `nickname`           varchar(12) NOT NULL COMMENT '用户昵称',
    `avatar`             varchar(255)         DEFAULT NULL COMMENT '头像URL',
    `real_name`          varchar(32)          DEFAULT NULL COMMENT '真实姓名',
    `phone`              varchar(11)          DEFAULT NULL COMMENT '手机号码',
    `email`              varchar(32)          DEFAULT NULL COMMENT '邮箱',
    `sex`                char(1)              DEFAULT '0' COMMENT '性别（0-男 1-女 2-未知）',
    `birthday`           date                 DEFAULT NULL COMMENT '生日',
    `used_storage_space` bigint(20)           DEFAULT '0' COMMENT '已用存储空间',
    `is_data_perfect`    tinyint(3) unsigned  DEFAULT '0' COMMENT '资料是否完善（0-未完善，1-已完善）',
    `expiration_time`    datetime             DEFAULT NULL COMMENT '会员过期时间',
    `login_ip`           varchar(50)          DEFAULT NULL COMMENT '最后登录ip',
    `login_date`         datetime             DEFAULT NULL COMMENT '最后登录时间',
    `gmt_create`         datetime    NOT NULL COMMENT '创建时间',
    `gmt_modified`       datetime    NOT NULL COMMENT '更新时间',
    PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  ROW_FORMAT = COMPACT COMMENT ='用户表';

/*Data for the table `tb_user` */

/*Table structure for table `tb_user_groups` */

DROP TABLE IF EXISTS `tb_user_groups`;

CREATE TABLE `tb_user_groups`
(
    `group_id`          int(11)     NOT NULL AUTO_INCREMENT COMMENT '用户组id',
    `auth_name`         varchar(50)          DEFAULT NULL COMMENT '权限名称',
    `group_name`        varchar(32) NOT NULL COMMENT '组名称',
    `parent_id`         int(11)     NOT NULL DEFAULT '0' COMMENT '父组id',
    `max_storage_space` bigint(20)  NOT NULL COMMENT '最大存储空间',
    `max_file_size`     bigint(20)  NOT NULL COMMENT '单个文件最大限制',
    `gmt_create`        datetime             DEFAULT NULL COMMENT '创建时间',
    `gmt_modified`      datetime             DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`group_id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1004
  DEFAULT CHARSET = utf8
  ROW_FORMAT = COMPACT COMMENT ='用户组表';

/*Data for the table `tb_user_groups` */

insert into `tb_user_groups`(`group_id`, `auth_name`, `group_name`, `parent_id`, `max_storage_space`, `max_file_size`,
                             `gmt_create`, `gmt_modified`)
values (1001, 'ROLE_PORTAL', '普通用户', 0, 21474836480, 3221225472, '2021-01-20 19:33:33', '2021-01-20 19:33:33'),
       (1002, 'ROLE_PORTAL', '普通会员', 0, 2199023255552, 5368709120, '2021-01-20 19:33:33', '2021-01-20 19:33:33'),
       (1003, 'ROLE_PORTAL', '超级会员', 0, 5497558138880, 10737418240, '2021-01-20 19:33:33', '2021-01-20 19:33:33');

/*!40101 SET SQL_MODE = @OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS = @OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS = @OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES = @OLD_SQL_NOTES */;
