/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50528
Source Host           : localhost:3306
Source Database       : weibo

Target Server Type    : MYSQL
Target Server Version : 50528
File Encoding         : 65001

Date: 2013-01-02 22:30:55
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `tbl_status`
-- ----------------------------
DROP TABLE IF EXISTS `tbl_status`;
CREATE TABLE `tbl_status` (
  `wid` varchar(30) NOT NULL DEFAULT '',
  `uid` varchar(30) DEFAULT NULL COMMENT '作者信息',
  `createdAt` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `text` varchar(300) DEFAULT NULL COMMENT '微博内容',
  `url` varchar(100) DEFAULT NULL COMMENT '微博来源连接',
  `relationShip` varchar(10) DEFAULT NULL COMMENT '微博来源-是否关注(no=nofollow)',
  `name` varchar(60) DEFAULT NULL COMMENT '微博来源-来源文案名称',
  `favorited` varchar(1) DEFAULT NULL COMMENT '是否已收藏',
  `truncated` varchar(1) DEFAULT NULL COMMENT '是否被缩短',
  `thumbnailPic` varchar(150) DEFAULT NULL COMMENT '微博内容中的图片的缩略地址',
  `bmiddlePic` varchar(150) DEFAULT NULL COMMENT '中型图片',
  `originalPic` varchar(150) DEFAULT NULL COMMENT '原始图片',
  `geo` varchar(100) DEFAULT NULL COMMENT '地理信息，保存经纬度，没有时不返回此字段',
  `latitude` double DEFAULT NULL COMMENT '纬度',
  `longitude` double DEFAULT NULL COMMENT '经度',
  `repostsCount` varchar(10) DEFAULT NULL COMMENT '转发数',
  `commentsCount` varchar(10) DEFAULT NULL COMMENT '评论数',
  `attitudescount` varchar(10) DEFAULT NULL COMMENT '表态数"赞"',
  `repostsFlag` varchar(1) DEFAULT NULL COMMENT '是否对转发进行过深度操作flag',
  `commentsFlag` varchar(1) DEFAULT NULL COMMENT '是否对评论态度做过操作 flag',
  `commentGoodCount` varchar(10) DEFAULT NULL COMMENT '好评数',
  `commentBadCount` varchar(10) DEFAULT NULL COMMENT '坏评数',
  PRIMARY KEY (`wid`),
  KEY `sys_w001` (`wid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

