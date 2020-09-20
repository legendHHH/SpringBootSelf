/*
 Navicat Premium Data Transfer

 Source Server         : MySQL5.6
 Source Server Type    : MySQL
 Source Server Version : 50640
 Source Host           : localhost:3306
 Source Schema         : mybatis-plus

 Target Server Type    : MySQL
 Target Server Version : 50640
 File Encoding         : 65001

 Date: 20/09/2020 14:56:54
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for mp_demo
-- ----------------------------
DROP TABLE IF EXISTS `mp_demo`;
CREATE TABLE `mp_demo`  (
  `t_id` bigint(32) NOT NULL AUTO_INCREMENT,
  `name` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `remark` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`t_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1245925645931909122 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '测试' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of mp_demo
-- ----------------------------
INSERT INTO `mp_demo` VALUES (1, 'string', 'string');
INSERT INTO `mp_demo` VALUES (2, '2', '2');
INSERT INTO `mp_demo` VALUES (1245743575297372162, 'legend', '测试');
INSERT INTO `mp_demo` VALUES (1245743752842260481, 'legend', '测试XXXX');
INSERT INTO `mp_demo` VALUES (1245925518156632065, 'string', 'string');
INSERT INTO `mp_demo` VALUES (1245925645931909121, 'string', 'string');

SET FOREIGN_KEY_CHECKS = 1;
