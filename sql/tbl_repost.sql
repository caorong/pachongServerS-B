/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50528
Source Host           : localhost:3306
Source Database       : weibo

Target Server Type    : MYSQL
Target Server Version : 50528
File Encoding         : 65001

Date: 2013-01-02 22:30:05
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `tbl_repost`
-- ----------------------------
DROP TABLE IF EXISTS `tbl_repost`;
CREATE TABLE `tbl_repost` (
  `id` varchar(32) NOT NULL DEFAULT '' COMMENT '32位唯一ID',
  `wid` varchar(30) DEFAULT NULL COMMENT '微博id',
  `uid` varchar(30) DEFAULT NULL COMMENT '微博原作者id',
  `fatherwid` varchar(30) DEFAULT NULL COMMENT '树节点里的父weibo wid',
  `selfwid` varchar(30) DEFAULT NULL COMMENT '树节点里的自己的weibo wid',
  `selfuid` varchar(30) DEFAULT NULL COMMENT '树节点里的自己userInfoId uid',
  `deepth` varchar(2) DEFAULT NULL COMMENT '深度',
  `text` varchar(300) DEFAULT NULL COMMENT '转载时的评论',
  `authorfansflag` varchar(1) DEFAULT NULL COMMENT '是否关注了作者条件 1为可能关注 0为不关注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;