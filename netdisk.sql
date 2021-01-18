/*
SQLyog Community v13.1.6 (64 bit)
MySQL - 8.0.12 : Database - netdisk
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`netdisk` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `netdisk`;

/*Table structure for table `tb_files` */

DROP TABLE IF EXISTS `tb_files`;

CREATE TABLE `tb_files` (
  `file_id` bigint(20) NOT NULL COMMENT '文件id',
  `file_real_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '文件真实名称',
  `file_encryption_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '加密后的名称',
  `file_storage_path` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '文件存储路径',
  `file_extension` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '文件扩展名',
  `file_short_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '文件短地址',
  `file_size` bigint(20) DEFAULT NULL COMMENT '文件大小',
  `username` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '所有者',
  `folder_id` int(11) DEFAULT NULL COMMENT '所属文件夹id',
  `in_recycle` int(1) NOT NULL DEFAULT '1' COMMENT '是否已删除（1-未删除，2-已删除）',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`file_id`),
  KEY `folder_id` (`folder_id`) USING BTREE,
  KEY `username` (`username`) USING BTREE,
  CONSTRAINT `tb_files_ibfk_1` FOREIGN KEY (`folder_id`) REFERENCES `tb_folders` (`folder_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `tb_files_ibfk_2` FOREIGN KEY (`username`) REFERENCES `tb_user` (`username`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='文件表';

/*Table structure for table `tb_folders` */

DROP TABLE IF EXISTS `tb_folders`;

CREATE TABLE `tb_folders` (
  `folder_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '目录id',
  `parent_id` int(11) NOT NULL COMMENT '上级目录id',
  `folder_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '文件夹名',
  `in_recycle` int(1) DEFAULT '1' COMMENT '是否删除（1-未删除，2-已删除）',
  `username` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '所属者',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`folder_id`),
  KEY `username` (`username`) USING BTREE,
  CONSTRAINT `tb_folders_ibfk_1` FOREIGN KEY (`username`) REFERENCES `tb_user` (`username`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1001 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='目录表';

/*Table structure for table `tb_friends` */

DROP TABLE IF EXISTS `tb_friends`;

CREATE TABLE `tb_friends` (
  `username` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名',
  `friendname` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '好友名',
  `user_to_frind_remark` varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '用户给好友的备注',
  `frind_to_user_remark` varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '好友给用户的备注',
  PRIMARY KEY (`username`,`friendname`) USING BTREE,
  KEY `friendname` (`friendname`) USING BTREE,
  CONSTRAINT `tb_friends_ibfk_1` FOREIGN KEY (`username`) REFERENCES `tb_user` (`username`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `tb_friends_ibfk_2` FOREIGN KEY (`friendname`) REFERENCES `tb_user` (`username`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='好友表';

/*Table structure for table `tb_friends_application` */

DROP TABLE IF EXISTS `tb_friends_application`;

CREATE TABLE `tb_friends_application` (
  `applicant` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '申请人',
  `respondent` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '被申请人',
  `messages` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '验证消息',
  `state` int(1) DEFAULT '0' COMMENT '申请状态（0-未同意，1-已同意，默认0）',
  `is_view` int(1) DEFAULT '0' COMMENT '是否查看（0-未查看，1-已查看，默认0）',
  `created_time` datetime DEFAULT NULL,
  PRIMARY KEY (`applicant`,`respondent`) USING BTREE,
  KEY `respondent` (`respondent`) USING BTREE,
  CONSTRAINT `tb_friends_application_ibfk_1` FOREIGN KEY (`applicant`) REFERENCES `tb_user` (`username`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `tb_friends_application_ibfk_2` FOREIGN KEY (`respondent`) REFERENCES `tb_user` (`username`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='好友申请表';

/*Table structure for table `tb_friends_session` */

DROP TABLE IF EXISTS `tb_friends_session`;

CREATE TABLE `tb_friends_session` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '用户名',
  `friend` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '好友',
  `new_title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '最新分享文件名',
  `new_sharing_time` datetime DEFAULT NULL COMMENT '最新分享时间',
  `visited_user` int(1) DEFAULT '1' COMMENT '是否显示(0-不显示，1-显示，默认1,对应username)',
  `visited_friend` int(1) DEFAULT '1' COMMENT '是否显示(0-不显示，1-显示，默认1，对应friend)',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `username` (`username`) USING BTREE,
  KEY `friend` (`friend`) USING BTREE,
  CONSTRAINT `tb_friends_session_ibfk_1` FOREIGN KEY (`username`) REFERENCES `tb_user` (`username`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `tb_friends_session_ibfk_2` FOREIGN KEY (`friend`) REFERENCES `tb_user` (`username`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='好友会话表';

/*Table structure for table `tb_friends_share` */

DROP TABLE IF EXISTS `tb_friends_share`;

CREATE TABLE `tb_friends_share` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `file_id_group` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '文件ID组',
  `title` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '分享标题',
  `distributors` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '分享人',
  `receiver` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '接收者',
  `is_view` int(1) DEFAULT '0' COMMENT '是否查看（0-未查看，1-已查看，默认0）',
  `sharing_time` datetime DEFAULT NULL COMMENT '分享时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `distributors` (`distributors`) USING BTREE,
  KEY `receiver` (`receiver`) USING BTREE,
  CONSTRAINT `tb_friends_share_ibfk_1` FOREIGN KEY (`distributors`) REFERENCES `tb_user` (`username`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `tb_friends_share_ibfk_2` FOREIGN KEY (`receiver`) REFERENCES `tb_user` (`username`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='好友文件分享表';

/*Table structure for table `tb_order` */

DROP TABLE IF EXISTS `tb_order`;

CREATE TABLE `tb_order` (
  `order_no` bigint(20) NOT NULL COMMENT '订单号',
  `username` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '用户名',
  `group_id` int(11) DEFAULT NULL,
  `payment` decimal(20,2) DEFAULT NULL COMMENT '实际付款金额,单位是元,保留两位小数',
  `payment_type` int(4) DEFAULT NULL COMMENT '支付类型,1-在线支付',
  `status` int(10) DEFAULT NULL COMMENT '订单状态:0-已取消-10-未付款，20-已付款，40-交易成功，50-交易关闭',
  `payment_time` datetime DEFAULT NULL COMMENT '支付时间',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`order_no`) USING BTREE,
  UNIQUE KEY `order_no_index` (`order_no`) USING BTREE,
  KEY `username` (`username`) USING BTREE,
  KEY `groupid` (`group_id`),
  CONSTRAINT `tb_order_ibfk_1` FOREIGN KEY (`username`) REFERENCES `tb_user` (`username`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `tb_order_ibfk_2` FOREIGN KEY (`group_id`) REFERENCES `tb_user_groups` (`group_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='订单表';

/*Table structure for table `tb_pay_info` */

DROP TABLE IF EXISTS `tb_pay_info`;

CREATE TABLE `tb_pay_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '用户名',
  `order_no` bigint(20) DEFAULT NULL COMMENT '订单号',
  `pay_platform` int(10) DEFAULT NULL COMMENT '支付平台:1-支付宝,2-微信',
  `platform_number` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '支付宝支付流水号',
  `platform_status` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '支付宝支付状态',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `username` (`username`) USING BTREE,
  KEY `order_no` (`order_no`) USING BTREE,
  CONSTRAINT `tb_pay_info_ibfk_1` FOREIGN KEY (`username`) REFERENCES `tb_user` (`username`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `tb_pay_info_ibfk_2` FOREIGN KEY (`order_no`) REFERENCES `tb_order` (`order_no`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='支付信息表';

/*Table structure for table `tb_url_share` */

DROP TABLE IF EXISTS `tb_url_share`;

CREATE TABLE `tb_url_share` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `file_id_group` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '文件ID组',
  `title` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '分享标题',
  `link` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '链接',
  `short_url` varchar(7) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '短地址',
  `file_key` varchar(4) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '提取码',
  `viewed` int(11) DEFAULT '0' COMMENT '浏览次数',
  `save_times` int(11) DEFAULT '0' COMMENT '保存次数',
  `downloads` int(11) DEFAULT '0' COMMENT '下载次数',
  `sharing_time` datetime DEFAULT NULL COMMENT '分享时间',
  `failure_time` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '失效时间',
  `type` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '分享类型',
  `username` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '分享者',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `username` (`username`) USING BTREE,
  CONSTRAINT `tb_url_share_ibfk_1` FOREIGN KEY (`username`) REFERENCES `tb_user` (`username`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='文件分享表';

/*Table structure for table `tb_user` */

DROP TABLE IF EXISTS `tb_user`;

CREATE TABLE `tb_user` (
  `username` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '用户名',
  `password` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户密码',
  `salt` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '盐',
  `nickname` varchar(12) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '昵称',
  `real_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '真实姓名',
  `phone` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '手机号码',
  `email` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '邮箱',
  `sex` varchar(4) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '性别',
  `birthday` datetime DEFAULT NULL COMMENT '生日',
  `group_id` int(11) DEFAULT NULL COMMENT '用户组id',
  `used_storage_space` bigint(20) DEFAULT NULL COMMENT '已用存储空间',
  `is_data_perfect` int(1) DEFAULT '0' COMMENT '资料是否完善（0-未完善，1-已完善）',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`username`) USING BTREE,
  KEY `groupid` (`group_id`),
  CONSTRAINT `tb_user_ibfk_1` FOREIGN KEY (`group_id`) REFERENCES `tb_user_groups` (`group_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='用户表';

/*Table structure for table `tb_user_groups` */

DROP TABLE IF EXISTS `tb_user_groups`;

CREATE TABLE `tb_user_groups` (
  `group_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户组id',
  `group_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '组名称',
  `parent_id` int(11) NOT NULL DEFAULT '0' COMMENT '父组id',
  `max_storage_space` bigint(20) NOT NULL COMMENT '最大存储空间',
  `max_file_size` bigint(20) DEFAULT NULL COMMENT '单个文件最大限制',
  PRIMARY KEY (`group_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1004 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='用户组表';

/*Data for the table `tb_user_groups` */

insert  into `tb_user_groups`(`group_id`,`group_name`,`parent_id`,`max_storage_space`,`max_file_size`) values
(1001,'普通用户',0,21474836480,3221225472),
(1002,'普通会员',0,2199023255552,5368709120),
(1003,'超级会员',0,5497558138880,10737418240);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
