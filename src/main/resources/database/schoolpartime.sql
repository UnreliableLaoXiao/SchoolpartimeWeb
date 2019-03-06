/*
Navicat MySQL Data Transfer

Source Server         : MySQL
Source Server Version : 50612
Source Host           : localhost:3306
Source Database       : schoolpartime

Target Server Type    : MYSQL
Target Server Version : 50612
File Encoding         : 65001

Date: 2019-03-06 23:36:29
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for `hibernate_sequence`
-- ----------------------------
DROP TABLE IF EXISTS `hibernate_sequence`;
CREATE TABLE `hibernate_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of hibernate_sequence
-- ----------------------------
INSERT INTO `hibernate_sequence` VALUES ('7');

-- ----------------------------
-- Table structure for `t_user`
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `id` int(11) NOT NULL,
  `password` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `type` int(11) NOT NULL,
  `username` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `verify_psw` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES ('3', '123454', '1', '小黑2', '123');
INSERT INTO `t_user` VALUES ('4', '123454', '1', '小黑1', '123');
INSERT INTO `t_user` VALUES ('5', '123454', '1', '小黑', '123');
INSERT INTO `t_user` VALUES ('6', '123454', '1', '小黑1', '123');
