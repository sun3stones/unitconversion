/*

Source Server         : mysql

Source Host           : localhost:3306
Source Database       : unitconversion

Target Server Type    : MYSQL
Target Server Version : 80019
File Encoding         : 65001

*/

-- ----------------------------
-- Table structure for unit_conversion
-- ----------------------------
DROP TABLE IF EXISTS `unit_conversion`;
CREATE TABLE `unit_conversion` (
  `unit_id` bigint NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `unit_type` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '单位类型',
  `unit_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '单位名称',
  `unit_type_cn` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '单位类型（中文）',
  `unit_name_cn` varchar(20) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL DEFAULT '' COMMENT '单位名称（中文）',
  `unit_coef` decimal(20,10) NOT NULL DEFAULT '1.0000000000' COMMENT '单位系数',
  `unit_formula` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT '' COMMENT '单位转换公式',
  `mid_flag` int NOT NULL DEFAULT '0' COMMENT '是否为中间单位（0否，1是）',
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`unit_id`),
  UNIQUE KEY `unit_type_name_inx` (`unit_type`,`unit_name`) COMMENT '单位类型名称唯一键'
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of unit_conversion
-- ----------------------------
INSERT INTO `unit_conversion` VALUES ('18', 'length', 'm', '长度', '米', '1.0000000000', '', '1', '2020-02-22 22:50:00', '2020-02-22 22:54:50');
INSERT INTO `unit_conversion` VALUES ('19', 'length', 'km', '长度', '千米', '0.0010000000', '', '0', '2020-02-23 10:10:18', '2020-02-23 10:10:18');
INSERT INTO `unit_conversion` VALUES ('21', 'length', 'n mile', '长度', '海里', '0.0005399568', '', '0', '2020-02-23 11:57:11', '2020-02-23 12:02:58');
INSERT INTO `unit_conversion` VALUES ('22', 'temp', 'c', '温度', '摄氏度', '1.0000000000', '', '1', '2020-02-23 16:16:53', '2020-02-23 16:16:53');
INSERT INTO `unit_conversion` VALUES ('23', 'temp', 'k', '温度', '开尔文', '1.0000000000', 'X-273.15;X+273.15', '0', '2020-02-23 16:19:50', '2020-02-23 16:19:50');
INSERT INTO `unit_conversion` VALUES ('24', 'temp', 'f', '温度', '华氏度', '1.0000000000', '(X-32)*5/9;X*9/5+32', '0', '2020-02-23 16:41:30', '2020-02-23 16:55:30');
