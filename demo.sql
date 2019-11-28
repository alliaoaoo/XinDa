/*
 Navicat Premium Data Transfer

 Source Server         : a
 Source Server Type    : MySQL
 Source Server Version : 50727
 Source Host           : localhost:3306
 Source Schema         : demo

 Target Server Type    : MySQL
 Target Server Version : 50727
 File Encoding         : 65001

 Date: 28/11/2019 14:13:56
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for cart
-- ----------------------------
DROP TABLE IF EXISTS `cart`;
CREATE TABLE `cart`  (
  `ID` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `MEMBER_ID` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `PRODUCT_ID` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `SERVICE_ID` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `SERVICE_NAME` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `SERVICE_INFO` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `UNIT_PRICE` int(22) NULL DEFAULT NULL,
  `BUY_NUM` int(22) NULL DEFAULT NULL,
  `TOTAL_PRICE` int(22) NULL DEFAULT NULL,
  `SERVICE_REQUEST` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `PROVIDER_ID` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `UNIT` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cart
-- ----------------------------
INSERT INTO `cart` VALUES ('16e34908a3833d42', '16e2c78f248cfc36', '16e20ec5f19f1016', NULL, 'TEST', 'test', 10002222, 4, 40008888, NULL, '16e123aba8c33940', 'sdfdsfs');
INSERT INTO `cart` VALUES ('16ea04060c73e284', '16e356dbf4e33d43', '16e20ec5f19f1016', NULL, 'TEST', 'test', 10002222, 1, 10002222, NULL, '16e123aba8c33940', 'sdfdsfs');
INSERT INTO `cart` VALUES ('16ea09a4b9e1ab853', '16e69dea81c6d021', '16e20ecd5cdf1017', NULL, 'TEST', 'sfsadf', 100000, 1, 100000, NULL, '16e123aba8c33940', '100000.0');

-- ----------------------------
-- Table structure for member
-- ----------------------------
DROP TABLE IF EXISTS `member`;
CREATE TABLE `member`  (
  `ID` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `NAME` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `GENDER` int(22) NULL DEFAULT NULL,
  `CELLPHONE` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `LOGIN_ID` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `PASSWORD` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `HEAD_IMG` varchar(528) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `REGISTER_TIME` date NULL DEFAULT NULL,
  `EMAIL` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `STATUS` int(22) NULL DEFAULT NULL,
  `LAST_LOGIN_TIME` date NULL DEFAULT NULL,
  `REGION` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `CODE` int(22) NULL DEFAULT NULL,
  UNIQUE INDEX `code`(`CODE`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of member
-- ----------------------------
INSERT INTO `member` VALUES ('16e356dbf4e33d43', 'WASIA', 2, '15504603333', NULL, '060E030E08030C0D0C07000F0908000D', '/uploads/16e356dbf4e33d43/WASIlogin/2019/11/WASIlogin20191109123442996.gif', '2019-11-04', '32121212@12212.com', 2, NULL, '甘肃-兰州市-市辖区', 15183200);
INSERT INTO `member` VALUES ('16e69dea81c6d021', 'afaf', 2, '15504604444', NULL, '0C0004050306060301080D080005000F', '/uploads/16e69dea81c6d021/afaflogin/2019/11/afaflogin2019112511313286.gif', '2019-11-14', 'afafa@g.com', 2, NULL, '新疆-乌鲁木齐市-市辖区', 3004523);

-- ----------------------------
-- Table structure for new_order_message
-- ----------------------------
DROP TABLE IF EXISTS `new_order_message`;
CREATE TABLE `new_order_message`  (
  `order_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '订单id',
  `member_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '客户名字',
  `provider_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '服务商id',
  `member_cellphone` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '客户名字',
  `product_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '服务产品号',
  `product_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '服务名称',
  PRIMARY KEY (`order_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of new_order_message
-- ----------------------------
INSERT INTO `new_order_message` VALUES ('16ea04040d33e282', 'WASIA', '16e123aba8c33940', '15504603333', '16e4b5f331033082', 'TEST');
INSERT INTO `new_order_message` VALUES ('16ea040424b3e283', 'WASIA', '16e123aba8c33940', '15504603333', '16e20ec5f19f1016', 'TEST');
INSERT INTO `new_order_message` VALUES ('16ea09bd7ff1ab857', NULL, '16e123aba8c33940', '15504604444', '16e63a1ad5135d44', 'TESTxxxxaaa');
INSERT INTO `new_order_message` VALUES ('16ea09bd8851ab858', NULL, '16e123aba8c33940', '15504604444', '16e68a9b3bd6d013', 'TEST');
INSERT INTO `new_order_message` VALUES ('16eafa30fec153c16', 'WASIA', '16e69e9c9116d024', '15504603333', '16ea08b65a81ab84', '彼狡童兮，不与我食兮');
INSERT INTO `new_order_message` VALUES ('16eafa3103b153c17', 'WASIA', '16e69e9c9116d024', '15504603333', '16ea08d6b9a1ab85', '弃我去者，昨日之日不可留');
INSERT INTO `new_order_message` VALUES ('16eafa310b1153c18', 'WASIA', '16e69e9c9116d024', '15504603333', '16ea08e78081ab86', '风雨凄凄，鸡鸣喈喈。');

-- ----------------------------
-- Table structure for product_style
-- ----------------------------
DROP TABLE IF EXISTS `product_style`;
CREATE TABLE `product_style`  (
  `style_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `name` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`style_id`, `name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of product_style
-- ----------------------------
INSERT INTO `product_style` VALUES ('style001', '财税服务');
INSERT INTO `product_style` VALUES ('style002', '公司工商 ');
INSERT INTO `product_style` VALUES ('style003', '人事外包');
INSERT INTO `product_style` VALUES ('style004', '其他');

-- ----------------------------
-- Table structure for product_type
-- ----------------------------
DROP TABLE IF EXISTS `product_type`;
CREATE TABLE `product_type`  (
  `type_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `name` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`type_id`, `name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of product_type
-- ----------------------------
INSERT INTO `product_type` VALUES ('type001', '个人服务');
INSERT INTO `product_type` VALUES ('type002', '企业服务');

-- ----------------------------
-- Table structure for provider
-- ----------------------------
DROP TABLE IF EXISTS `provider`;
CREATE TABLE `provider`  (
  `ID` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `NAME` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `PROVIDER_INFO` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `CELLPHONE` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `LOGIN_ID` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `PASSWORD` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `PROVIDER_IMG` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `REGION` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `REGISTER_TIME` date NULL DEFAULT NULL,
  `STATUS` int(22) NULL DEFAULT NULL,
  `AUTH_FILE` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `WEIXIN` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `QQ` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `WORK_TIME` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `EMAIL` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `RECOMMEND` int(22) NULL DEFAULT NULL,
  `LOGINIMG` varchar(2048) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of provider
-- ----------------------------
INSERT INTO `provider` VALUES ('16e123aba8c33940', 'WASI', 'asfdfffaxx', '15504602222', '11233', '0F020407030A0F070C0D01080C010C0B', '/uploads/16e123aba8c33940/null/2019/11/null20191108230614796.jpg', '广西-桂林市-七星区', '2019-10-28', 1, NULL, '45415454544', '1430311604', '周一到周五', '111111@12135.com', NULL, '/uploads/16e123aba8c33940/WASIlogin/2019/11/WASIlogin20191109111616505.gif');
INSERT INTO `provider` VALUES ('16e26098e7ee884', 'aaaaaa', '', '12345678', '', '05050A02030A000A0404070D010C070D', '/uploads/16e26098e7ee884/null/2019/11/null20191101163057422.gif', '宁夏-银川市-市辖区', '2019-11-01', 1, NULL, '', '', '周一到周五', '', NULL, '/uploads/16e26098e7ee884/login/2019/11/login20191112204641770.gif');
INSERT INTO `provider` VALUES ('16e639e4a1e35d40', NULL, NULL, '147258369', NULL, '040F010F0B0B0A030D07070E050C0502', NULL, '河北-石家庄市-市辖区', '2019-11-13', 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `provider` VALUES ('16e69e9c9116d024', 'WASIi', 'ghhghhhhhgfg', '15504504444', '11233', '050A0C0C0D0D0701050D00020E0E0401', '/uploads/16e69e9c9116d024/null/2019/11/null20191114210001948.gif', '宁夏-银川市-市辖区', '2019-11-14', 1, NULL, '45415454544', '1430311604', '周一到周五', '32121212@12212.com', NULL, '/uploads/16e69e9c9116d024/WASIlogin/2019/11/WASIlogin20191114210029206.gif');

-- ----------------------------
-- Table structure for provider_message
-- ----------------------------
DROP TABLE IF EXISTS `provider_message`;
CREATE TABLE `provider_message`  (
  `provider_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '服务商id',
  `message_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '消息id',
  `message_text` varchar(2048) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '消息内容',
  PRIMARY KEY (`message_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of provider_message
-- ----------------------------
INSERT INTO `provider_message` VALUES ('16e639e4a1e35d40', '16e639e4d8c35d41', '请添加服务商信息');
INSERT INTO `provider_message` VALUES ('16e69e9c9116d024', '16e69e9c9906d025', '请添加服务商信息');
INSERT INTO `provider_message` VALUES ('16e123aba8c33940', '16ea095aa1d1ab841', 'TEST已经同意上线');
INSERT INTO `provider_message` VALUES ('16e69e9c9116d024', '16ea095b1791ab842', '大江东去浪淘尽已经同意上线');
INSERT INTO `provider_message` VALUES ('16e69e9c9116d024', '16ea095b4e31ab843', '千古风流人物已经同意上线');
INSERT INTO `provider_message` VALUES ('16e69e9c9116d024', '16ea095b8cb1ab844', '彼狡童兮，不与我言兮已经同意上线');
INSERT INTO `provider_message` VALUES ('16e69e9c9116d024', '16ea095c4211ab845', '彼狡童兮，不与我食兮已经同意上线');
INSERT INTO `provider_message` VALUES ('16e69e9c9116d024', '16ea095c6a71ab846', '弃我去者，昨日之日不可留已经同意上线');
INSERT INTO `provider_message` VALUES ('16e69e9c9116d024', '16ea095c9b71ab847', '风雨凄凄，鸡鸣喈喈。已经同意上线');
INSERT INTO `provider_message` VALUES ('16e69e9c9116d024', '16ea095cbb81ab848', '君不见，黄河之水天上来，奔流到海不复回已经同意上线');
INSERT INTO `provider_message` VALUES ('16e69e9c9116d024', '16ea095d22d1ab849', '君不见，高堂明镜悲白发，朝如青丝暮成雪。已经同意上线');
INSERT INTO `provider_message` VALUES ('16e69e9c9116d024', '16ea095d5781ab850', '人生得意须尽欢，莫使金樽空对月。已经同意上线');

-- ----------------------------
-- Table structure for provider_product
-- ----------------------------
DROP TABLE IF EXISTS `provider_product`;
CREATE TABLE `provider_product`  (
  `PRODUCT_ID` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '平台产品id',
  `PROVIDER_ID` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '服务商id',
  `SERVICE_NAME` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '服务名称',
  `SERVICE_INFO` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '服务简介',
  `SERVICE_IMG` varchar(10240) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '服务产品图片',
  `SERVICE_CONTENT` varchar(10240) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '服务内容',
  `REGION` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '地区',
  `PRICE` int(22) NULL DEFAULT NULL COMMENT '价格',
  `UNIT` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '单位',
  `STATUS` int(22) NULL DEFAULT NULL COMMENT '0下线1申请上线2同意3不同意',
  `RECOMMEND` int(22) NULL DEFAULT NULL COMMENT '是否推荐产品',
  `HIGH_QUALITY` int(22) NULL DEFAULT NULL COMMENT '是否创业必备',
  `CREATE_TIME` date NULL DEFAULT NULL COMMENT '创建时间',
  `STAR` int(22) NULL DEFAULT NULL COMMENT '是否明星产品',
  `style_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '产品类型id',
  `type_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '服务类型id',
  `store_num` int(22) NULL DEFAULT NULL COMMENT '库存',
  PRIMARY KEY (`PRODUCT_ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of provider_product
-- ----------------------------
INSERT INTO `provider_product` VALUES ('16e20ec5f19f1016', '16e123aba8c33940', 'TEST', 'test', '/uploads/16e123aba8c33940/TEST/2019/10/TEST2019103116263290.gif', 'tesst', '青海-西宁市-市辖区', 10002222, 'sdfdsfs', 1, 1, NULL, '2019-10-31', NULL, 'style004', 'type002', 10001);
INSERT INTO `provider_product` VALUES ('16e20ecd5cdf1017', '16e123aba8c33940', 'TEST', 'sfsadf', '/uploads/16e123aba8c33940/TEST/2019/10/TEST20191031162702477.jpg', 'sdfasf', '新疆-伊犁哈萨克自治州-新源县', 100000, '100000.0', 1, 0, NULL, '2019-10-31', NULL, 'style004', 'type002', 10000);
INSERT INTO `provider_product` VALUES ('16e63a1ad5135d44', '16e123aba8c33940', 'TESTxxxxaaa', 'sssss', '/uploads/16e123aba8c33940/TESTxxxxaaa/2019/11/TESTxxxxaaa20191113151930395.gif', 'ssss', '山西-太原市-市辖区', 10002222, 'sdfdsfsaaa', 2, 0, NULL, '2019-11-13', NULL, 'style004', 'type002', 10000);
INSERT INTO `provider_product` VALUES ('16e68a9b3bd6d013', '16e123aba8c33940', 'TEST', 'xx', '/uploads/16e123aba8c33940/TEST/2019/11/TEST20191114144622542.gif', 'xx', '河北-石家庄市-市辖区', 100000, '0000', 2, 0, NULL, '2019-11-14', NULL, 'style004', 'type001', 10000);
INSERT INTO `provider_product` VALUES ('16ea006f28d30d43', '16e69e9c9116d024', '大江东去浪淘尽', '啊', '/uploads/16e69e9c9116d024/大江东去浪淘尽/2019/11/大江东去浪淘尽20191128103313265.gif', '波', '内蒙古-鄂尔多斯市-鄂托克前旗', 1000, 'hjhj', 2, NULL, NULL, NULL, NULL, 'style002', 'type002', 100);
INSERT INTO `provider_product` VALUES ('16ea010721935b81', '16e69e9c9116d024', '千古风流人物', '还看今朝', '/uploads/16e69e9c9116d024/千古风流人物/2019/11/千古风流人物20191128104347905.gif', '百尺竿头', '宁夏-银川市-市辖区', 1000, 'ASI', 2, NULL, NULL, NULL, NULL, 'style001', 'type001', 100);
INSERT INTO `provider_product` VALUES ('16ea08ad6e61ab83', '16e69e9c9116d024', '彼狡童兮，不与我言兮', '维子之故，使我不能餐兮！', '/uploads/16e69e9c9116d024/彼狡童兮，不与我言兮/2019/11/彼狡童兮，不与我言兮2019112810440766.gif', '维子之故，使我不能餐兮！', '新疆-乌鲁木齐市-市辖区', 1000, 'AQI', 2, NULL, NULL, NULL, NULL, 'style003', 'type002', 100);
INSERT INTO `provider_product` VALUES ('16ea08b65a81ab84', '16e69e9c9116d024', '彼狡童兮，不与我食兮', '维子之故，使我不能息兮', '/uploads/16e69e9c9116d024/彼狡童兮，不与我食兮/2019/11/彼狡童兮，不与我食兮20191128110201164.gif', '维子之故，使我不能息兮', '山西-太原市-市辖区', 10000, 'AQSO', 2, NULL, NULL, NULL, NULL, 'style004', 'type002', 100);
INSERT INTO `provider_product` VALUES ('16ea08d6b9a1ab85', '16e69e9c9116d024', '弃我去者，昨日之日不可留', '弃我去者，昨日之日不可留', '/uploads/16e69e9c9116d024/弃我去者，昨日之日不可留/2019/11/弃我去者，昨日之日不可留20191128104421827.gif', '弃我去者，昨日之日不可留', '新疆-和田地区-和田市', 10000, '欧瑞', 2, NULL, NULL, NULL, NULL, 'style003', 'type002', 100);
INSERT INTO `provider_product` VALUES ('16ea08e78081ab86', '16e69e9c9116d024', '风雨凄凄，鸡鸣喈喈。', '风雨凄凄，鸡鸣喈喈。', '/uploads/16e69e9c9116d024/风雨凄凄，鸡鸣喈喈。/2019/11/风雨凄凄，鸡鸣喈喈。20191128104444595.gif', '风雨凄凄，鸡鸣喈喈。', '河北-石家庄市-市辖区', 1000, 'GOI', 2, NULL, NULL, NULL, NULL, 'style004', 'type001', 100);
INSERT INTO `provider_product` VALUES ('16ea092318e1ab837', '16e69e9c9116d024', '君不见，黄河之水天上来，奔流到海不复回', '君不见，黄河之水天上来，奔流到海不复回', '/uploads/16e69e9c9116d024/君不见，黄河之水天上来，奔流到海不复回/2019/11/君不见，黄河之水天上来，奔流到海不复回2019112810445795.gif', '君不见，黄河之水天上来，奔流到海不复回', '宁夏-银川市-市辖区', 1000, 'SFDS', 2, NULL, NULL, NULL, NULL, 'style003', 'type002', 100);
INSERT INTO `provider_product` VALUES ('16ea092e9331ab838', '16e69e9c9116d024', '君不见，高堂明镜悲白发，朝如青丝暮成雪。', '君不见，高堂明镜悲白发，朝如青丝暮成雪。', '/uploads/16e69e9c9116d024/君不见，高堂明镜悲白发，朝如青丝暮成雪。/2019/11/君不见，高堂明镜悲白发，朝如青丝暮成雪。20191128104511322.gif', '君不见，高堂明镜悲白发，朝如青丝暮成雪。', '山西-太原市-市辖区', 10000, 'BNI', 2, NULL, NULL, NULL, NULL, 'style004', 'type002', 100);
INSERT INTO `provider_product` VALUES ('16ea0939abc1ab839', '16e69e9c9116d024', '人生得意须尽欢，莫使金樽空对月。', '人生得意须尽欢，莫使金樽空对月。', '/uploads/16e69e9c9116d024/人生得意须尽欢，莫使金樽空对月。/2019/11/人生得意须尽欢，莫使金樽空对月。20191128104526647.gif', '人生得意须尽欢，莫使金樽空对月。', '新疆-乌鲁木齐市-市辖区', 10000, '人生得意须尽欢，莫使金樽空对月。', 2, NULL, NULL, NULL, NULL, 'style002', 'type002', 100);
INSERT INTO `provider_product` VALUES ('16eafe2555e153c26', '16e69e9c9116d024', '击鼓其镗，踊跃用兵。土国城漕，我独南行。', '击鼓其镗，踊跃用兵。土国城漕，我独南行。', '/uploads/16e69e9c9116d024/击鼓其镗，踊跃用兵。土国城漕，我独南行。/2019/11/击鼓其镗，踊跃用兵。土国城漕，我独南行。20191128104116130.jpg', '击鼓其镗，踊跃用兵。土国城漕，我独南行。', '内蒙古-呼和浩特市-市辖区', 1000, '阿法', 0, NULL, NULL, '2019-11-28', NULL, 'style002', 'type001', 100);
INSERT INTO `provider_product` VALUES ('16eafe3a775153c27', '16e69e9c9116d024', '从孙子仲，平陈与宋。不我以归，忧心有忡。', '从孙子仲，平陈与宋。不我以归，忧心有忡。', '/uploads/16e69e9c9116d024/从孙子仲，平陈与宋。不我以归，忧心有忡。/2019/11/从孙子仲，平陈与宋。不我以归，忧心有忡。20191128104242677.gif', '从孙子仲，平陈与宋。不我以归，忧心有忡。', '内蒙古-呼和浩特市-市辖区', 10000, '100', 0, NULL, NULL, '2019-11-28', NULL, 'style004', 'type002', 100);

-- ----------------------------
-- Table structure for service_judge
-- ----------------------------
DROP TABLE IF EXISTS `service_judge`;
CREATE TABLE `service_judge`  (
  `CONTENT` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '评价内容',
  `JUDGE_TIME` date NULL DEFAULT NULL COMMENT '评价时间',
  `TAGS` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '评价标签',
  `MEMBER_ID` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '会员id',
  `TYPE` int(22) NULL DEFAULT NULL COMMENT '评价类型（1好评2中评3差评）',
  `STATUS` int(22) NULL DEFAULT NULL COMMENT '评价状态（1未评价2已评价）',
  `ID` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `ORDER_ID` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '业务订单id',
  `SERVICE_ID` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '服务订单id',
  `PROVIDER_ID` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '服务商id',
  `SCORE` int(22) NULL DEFAULT NULL COMMENT '评分',
  PRIMARY KEY (`ORDER_ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of service_judge
-- ----------------------------
INSERT INTO `service_judge` VALUES ('aaaaaa', '2019-11-07', NULL, '16e356dbf4e33d43', NULL, 1, '16e45078bcd39041', '16e3f8dfa3b5ac9', NULL, '16e123aba8c33940', NULL);
INSERT INTO `service_judge` VALUES ('lalalalal', '2019-11-08', NULL, '16e356dbf4e33d43', NULL, 1, '16e49830bdf3841', '16e3f8dfa585ac10', NULL, '16e123aba8c33940', NULL);
INSERT INTO `service_judge` VALUES ('吃葡萄不吐葡萄皮', '2019-11-25', NULL, '16e69dea81c6d021', NULL, 1, '16ea0acae1d386c1', '16ea09bd7ff1ab857', NULL, '16e123aba8c33940', NULL);

-- ----------------------------
-- Table structure for service_order
-- ----------------------------
DROP TABLE IF EXISTS `service_order`;
CREATE TABLE `service_order`  (
  `ID` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `MEMBER_ID` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `PRODUCT_ID` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `SERVICE_NAME` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `SERVICE_INFO` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `UNIT_PRICE` int(22) NULL DEFAULT NULL,
  `BUY_NUM` int(22) NULL DEFAULT NULL,
  `TOTAL_PRICE` int(22) NULL DEFAULT NULL,
  `STATUS` int(22) NULL DEFAULT NULL,
  `CREATE_TIME` date NULL DEFAULT NULL,
  `PROVIDER_ID` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `UNIT` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of service_order
-- ----------------------------
INSERT INTO `service_order` VALUES ('16e3f8dfa3b5ac9', '16e356dbf4e33d43', '16e20ec5f19f1016', 'TEST', 'test', 10002222, 1, 10002222, 3, '2019-11-06', '16e123aba8c33940', 'sdfdsfs');
INSERT INTO `service_order` VALUES ('16e3f8dfa585ac10', '16e356dbf4e33d43', '16e20ecd5cdf1017', 'TEST', 'sfsadf', 100000, 2, 200000, 3, '2019-11-06', '16e123aba8c33940', '100000.0');
INSERT INTO `service_order` VALUES ('16e3f9d42345ac15', '16e356dbf4e33d43', '16e20ec5f19f1016', 'AAAA', 'test', 10002222, 23, 230051106, 3, '2019-11-06', '16e123aba8c33940', 'sdfdsfs');
INSERT INTO `service_order` VALUES ('16e450a32db39044', '16e356dbf4e33d43', '16e20ecd5cdf1017', 'TEST', 'sfsadf', 100000, 24, 2400000, 4, '2019-11-07', '16e123aba8c33940', '100000.0');
INSERT INTO `service_order` VALUES ('16e450a336039045', '16e356dbf4e33d43', '16e20ec5f19f1016', 'TEST', 'test', 10002222, 2, 20004444, 3, '2019-11-07', '16e123aba8c33940', 'sdfdsfs');
INSERT INTO `service_order` VALUES ('16e450a339339046', '16e356dbf4e33d43', '16e20edb49cf1018', 'TESTxxxx', 'xxxxx', 100000, 1, 100000, 3, '2019-11-07', '16e123aba8c33940', '0000');
INSERT INTO `service_order` VALUES ('16e4a2a34f21fbc3', '16e356dbf4e33d43', '16e20ec5f19f1016', 'TEST', 'test', 10002222, 3, 30006666, 3, '2019-11-08', '16e123aba8c33940', 'sdfdsfs');
INSERT INTO `service_order` VALUES ('16e4a2a35411fbc4', '16e356dbf4e33d43', '16e20ecd5cdf1017', 'TEST', 'sfsadf', 100000, 4, 400000, 3, '2019-11-08', '16e123aba8c33940', '100000.0');
INSERT INTO `service_order` VALUES ('16e4a2a357a1fbc5', '16e356dbf4e33d43', '16e20edb49cf1018', 'TESTxxxx', 'xxxxx', 100000, 1, 100000, 3, '2019-11-08', '16e123aba8c33940', '0000');
INSERT INTO `service_order` VALUES ('16e63cedbf92b845', '16e356dbf4e33d43', '16e20ec5f19f1016', 'TEST', 'test', 10002222, 15, 150033330, 3, '2019-11-13', '16e123aba8c33940', 'sdfdsfs');
INSERT INTO `service_order` VALUES ('16e63cedc482b846', '16e356dbf4e33d43', '16e20ecd5cdf1017', 'TEST', 'sfsadf', 100000, 16, 1600000, 3, '2019-11-13', '16e123aba8c33940', '100000.0');
INSERT INTO `service_order` VALUES ('16e63ceddf22b847', '16e356dbf4e33d43', '16e4b5f331033082', 'TEST', '', 1000, 21, 21000, 3, '2019-11-13', '16e123aba8c33940', '');
INSERT INTO `service_order` VALUES ('16e63cede632b848', '16e356dbf4e33d43', '16e63a1ad5135d44', 'TESTxxxxaaa', 'sssss', 10002222, 1, 10002222, 3, '2019-11-13', '16e123aba8c33940', 'sdfdsfsaaa');
INSERT INTO `service_order` VALUES ('16e67d518b16d06', '16e356dbf4e33d43', '16e20ec5f19f1016', 'TEST', 'test', 10002222, 1, 10002222, 3, '2019-11-14', '16e123aba8c33940', 'sdfdsfs');
INSERT INTO `service_order` VALUES ('16e67d519496d07', '16e356dbf4e33d43', '16e20ecd5cdf1017', 'TEST', 'sfsadf', 100000, 1, 100000, 3, '2019-11-14', '16e123aba8c33940', '100000.0');
INSERT INTO `service_order` VALUES ('16e67d519a86d08', '16e356dbf4e33d43', '16e20edb49cf1018', 'TESTxxxx', 'xxxxx', 100000, 1, 100000, 3, '2019-11-14', '16e123aba8c33940', '0000');
INSERT INTO `service_order` VALUES ('16ea04040d33e282', '16e356dbf4e33d43', '16e4b5f331033082', 'TEST', '', 1000, 1, 1000, 3, '2019-11-25', '16e123aba8c33940', '');
INSERT INTO `service_order` VALUES ('16ea040424b3e283', '16e356dbf4e33d43', '16e20ec5f19f1016', 'TEST', 'test', 10002222, 2, 20004444, 3, '2019-11-25', '16e123aba8c33940', 'sdfdsfs');
INSERT INTO `service_order` VALUES ('16ea09bd7ff1ab857', '16e69dea81c6d021', '16e63a1ad5135d44', 'TESTxxxxaaa', 'sssss', 10002222, 14, 140031108, 3, '2019-11-25', '16e123aba8c33940', 'sdfdsfsaaa');
INSERT INTO `service_order` VALUES ('16ea09bd8851ab858', '16e69dea81c6d021', '16e68a9b3bd6d013', 'TEST', 'xx', 100000, 13, 1300000, 3, '2019-11-25', '16e123aba8c33940', '0000');
INSERT INTO `service_order` VALUES ('16ea09bd9071ab859', '16e69dea81c6d021', '16ea006f28d30d43', '大江东去浪淘尽', '啊', 1000, 17, 17000, 3, '2019-11-25', '16e69e9c9116d024', 'hjhj');
INSERT INTO `service_order` VALUES ('16eafa30fec153c16', '16e356dbf4e33d43', '16ea08b65a81ab84', '彼狡童兮，不与我食兮', '维子之故，使我不能息兮', 10000, 1, 10000, 2, '2019-11-28', '16e69e9c9116d024', 'AQSO');
INSERT INTO `service_order` VALUES ('16eafa3103b153c17', '16e356dbf4e33d43', '16ea08d6b9a1ab85', '弃我去者，昨日之日不可留', '弃我去者，昨日之日不可留', 10000, 1, 10000, 2, '2019-11-28', '16e69e9c9116d024', '欧瑞');
INSERT INTO `service_order` VALUES ('16eafa310b1153c18', '16e356dbf4e33d43', '16ea08e78081ab86', '风雨凄凄，鸡鸣喈喈。', '风雨凄凄，鸡鸣喈喈。', 1000, 1, 1000, 2, '2019-11-28', '16e69e9c9116d024', 'GOI');

-- ----------------------------
-- Table structure for service_order_progress
-- ----------------------------
DROP TABLE IF EXISTS `service_order_progress`;
CREATE TABLE `service_order_progress`  (
  `ID` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `ACCEPT_TIME` date NULL DEFAULT NULL,
  `COMPLETE_TIME` date NULL DEFAULT NULL,
  `REQUEST_TIME` date NULL DEFAULT NULL,
  `HANDLE_TIME` date NULL DEFAULT NULL,
  `DECLINE_REASON` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of service_order_progress
-- ----------------------------
INSERT INTO `service_order_progress` VALUES ('16e3f9d42345ac15', '2019-11-06', '2019-11-25', '2019-11-06', '2019-11-25', '');
INSERT INTO `service_order_progress` VALUES ('16e450a32db39044', '2019-11-07', '2019-11-25', '2019-11-07', '2019-11-25', '拒绝提供服务');
INSERT INTO `service_order_progress` VALUES ('16e450a336039045', '2019-11-07', '2019-11-14', '2019-11-07', '2019-11-14', '');
INSERT INTO `service_order_progress` VALUES ('16e450a339339046', '2019-11-07', '2019-11-14', '2019-11-07', '2019-11-14', '');
INSERT INTO `service_order_progress` VALUES ('16e4a2a34f21fbc3', '2019-11-08', '2019-11-14', '2019-11-08', '2019-11-14', '');
INSERT INTO `service_order_progress` VALUES ('16e4a2a35411fbc4', '2019-11-08', NULL, '2019-11-08', NULL, NULL);
INSERT INTO `service_order_progress` VALUES ('16e4a2a357a1fbc5', '2019-11-08', '2019-11-14', '2019-11-08', '2019-11-14', '');
INSERT INTO `service_order_progress` VALUES ('16e63cedbf92b845', '2019-11-13', '2019-11-14', '2019-11-13', '2019-11-14', '');
INSERT INTO `service_order_progress` VALUES ('16e63cedc482b846', '2019-11-13', '2019-11-14', '2019-11-13', '2019-11-14', '');
INSERT INTO `service_order_progress` VALUES ('16e63ceddf22b847', '2019-11-13', '2019-11-14', '2019-11-13', '2019-11-14', '');
INSERT INTO `service_order_progress` VALUES ('16e63cede632b848', '2019-11-13', '2019-11-14', '2019-11-13', '2019-11-14', '');
INSERT INTO `service_order_progress` VALUES ('16e67d518b16d06', '2019-11-14', '2019-11-14', '2019-11-14', '2019-11-14', '');
INSERT INTO `service_order_progress` VALUES ('16e67d519496d07', '2019-11-14', '2019-11-14', '2019-11-14', '2019-11-14', '');
INSERT INTO `service_order_progress` VALUES ('16e67d519a86d08', '2019-11-14', '2019-11-14', '2019-11-14', '2019-11-14', '');
INSERT INTO `service_order_progress` VALUES ('16ea04040d33e282', '2019-11-25', '2019-11-25', '2019-11-25', '2019-11-25', '');
INSERT INTO `service_order_progress` VALUES ('16ea040424b3e283', '2019-11-25', '2019-11-25', '2019-11-25', '2019-11-25', '');
INSERT INTO `service_order_progress` VALUES ('16ea09bd7ff1ab857', '2019-11-25', '2019-11-25', '2019-11-25', '2019-11-25', '');
INSERT INTO `service_order_progress` VALUES ('16ea09bd8851ab858', '2019-11-25', '2019-11-25', '2019-11-25', '2019-11-25', '');
INSERT INTO `service_order_progress` VALUES ('16ea09bd9071ab859', '2019-11-25', '2019-11-25', '2019-11-25', '2019-11-25', '');
INSERT INTO `service_order_progress` VALUES ('16eafa30fec153c16', '2019-11-28', NULL, '2019-11-28', NULL, NULL);
INSERT INTO `service_order_progress` VALUES ('16eafa3103b153c17', '2019-11-28', NULL, '2019-11-28', NULL, NULL);
INSERT INTO `service_order_progress` VALUES ('16eafa310b1153c18', '2019-11-28', NULL, '2019-11-28', NULL, NULL);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `user_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '名字',
  `head_img` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '头像',
  `login_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '登录名',
  `password` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码',
  `email` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `cellphone` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手机号',
  `status` int(22) NULL DEFAULT NULL COMMENT '状态（1：有效，2：无效）',
  `org_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '组织结构部门ID',
  `register_time` date NULL DEFAULT NULL COMMENT '注册时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'admin', '/uploads/16e4f3db04f2601/adminlogin/2019/11/adminlogin20191113154346141.png', '3011739322', '0F020407030A0F070C0D01080C010C0B', '301173932x2@qq.com', '15504602222', 1, '16e69fb501c6d026', '2019-10-19');

SET FOREIGN_KEY_CHECKS = 1;
