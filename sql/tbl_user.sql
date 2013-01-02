/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50528
Source Host           : localhost:3306
Source Database       : weibo

Target Server Type    : MYSQL
Target Server Version : 50528
File Encoding         : 65001

Date: 2013-01-02 22:31:00
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `tbl_user`
-- ----------------------------
DROP TABLE IF EXISTS `tbl_user`;
CREATE TABLE `tbl_user` (
  `uid` varchar(30) NOT NULL,
  `screenName` varchar(30) NOT NULL COMMENT '微博昵称',
  `name` varchar(30) DEFAULT NULL COMMENT '友好显示名称，如Bill Gates,名称中间的空格正常显示(此特性暂不支持)',
  `province` varchar(10) DEFAULT NULL COMMENT '省份编码（参考省份编码表）',
  `city` varchar(10) DEFAULT NULL COMMENT '城市编码（参考城市编码表）',
  `location` varchar(100) DEFAULT NULL COMMENT '地址',
  `description` varchar(300) DEFAULT NULL COMMENT '个人描述',
  `url` varchar(150) DEFAULT NULL COMMENT '用户博客地址',
  `profileImageUrl` varchar(150) DEFAULT NULL COMMENT '自定义图像',
  `userDomain` varchar(60) DEFAULT NULL COMMENT '用户个性化URL',
  `gender` varchar(2) DEFAULT NULL COMMENT '性别,m--男，f--女,n--未知',
  `followersCount` varchar(10) DEFAULT NULL COMMENT '粉丝数',
  `friendsCount` varchar(10) DEFAULT NULL COMMENT '关注数',
  `statusesCount` varchar(10) DEFAULT NULL COMMENT '微博数',
  `favouritesCount` varchar(10) DEFAULT NULL COMMENT '收藏数',
  `createdAt` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `following` varchar(2) DEFAULT NULL COMMENT '保留字段,是否已关注(此特性暂不支持)',
  `verified` varchar(2) DEFAULT NULL COMMENT '加V标示，是否微博认证用户',
  `verifiedType` varchar(20) DEFAULT NULL COMMENT '认证类型',
  `allowAllActMsg` varchar(1) DEFAULT NULL COMMENT '是否允许所有人给我发私信',
  `allowAllComment` varchar(1) DEFAULT NULL COMMENT '是否允许所有人对我的微博进行评论',
  `followMe` varchar(1) DEFAULT NULL COMMENT '此用户是否关注我',
  `avatarLarge` varchar(150) DEFAULT NULL COMMENT '大头像地址',
  `biFollowersCount` varchar(10) DEFAULT NULL COMMENT '互粉数',
  `remark` varchar(300) DEFAULT NULL COMMENT '备注信息，在查询用户关系时提供此字段。',
  `lang` varchar(10) DEFAULT NULL COMMENT '用户语言版本',
  `verifiedReason` varchar(100) DEFAULT NULL COMMENT '认证原因',
  `weihao` varchar(20) DEFAULT NULL COMMENT '微號',
  `type` varchar(10) DEFAULT NULL COMMENT '该user的type',
  PRIMARY KEY (`uid`),
  KEY `sys_u001` (`uid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

