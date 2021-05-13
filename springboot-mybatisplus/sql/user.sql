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

 Date: 13/05/2021 21:18:46
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` bigint(20) NOT NULL COMMENT '主键 ID',
  `user_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '姓名',
  `age` int(11) NULL DEFAULT NULL COMMENT '年龄',
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `last_update_time` datetime(0) NULL DEFAULT NULL COMMENT '最后更新时间',
  `version` bigint(25) NULL DEFAULT NULL COMMENT '版本号',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'Tom', 11, '1111111@qq.com', '2021-05-13 20:58:25', '2021-05-13 20:58:25', 1);
INSERT INTO `user` VALUES (2, 'Hom', 14, '1111111@qq.com', '2021-05-13 20:58:25', '2021-05-13 20:58:25', 1);
INSERT INTO `user` VALUES (3, 'Join', 16, '232132@qq.com', '2021-05-13 20:58:25', '2021-05-13 20:58:25', 1);
INSERT INTO `user` VALUES (4, 'Desk', 11, '424563@qq.com', '2021-05-13 20:58:25', '2021-05-13 20:58:25', 1);
INSERT INTO `user` VALUES (5, 'Bili', 19, '1313213@qq.com', '2021-05-13 20:58:25', '2021-05-13 20:58:25', 1);
INSERT INTO `user` VALUES (1392821377689559042, 'legend', 30, '737796231@qq.com', '2021-05-13 20:58:25', '2021-05-13 20:58:25', 1);
INSERT INTO `user` VALUES (1392826562788720642, 'John', 33, '737796231@qq.com', '2021-05-13 20:58:25', '2021-05-13 21:17:53', 2);
INSERT INTO `user` VALUES (1392827352362381313, 'legend9999', 33, '737796231@qq.com', '2021-05-13 21:01:33', '2021-05-13 21:01:33', 1);
INSERT INTO `user` VALUES (1392831124501172225, 'legend9', 33, '737796231@qq.com', '2021-05-13 21:16:32', '2021-05-13 21:16:32', 1);

SET FOREIGN_KEY_CHECKS = 1;
