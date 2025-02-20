/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 100432 (10.4.32-MariaDB)
 Source Host           : localhost:3306
 Source Schema         : quanlimaytinh

 Target Server Type    : MySQL
 Target Server Version : 100432 (10.4.32-MariaDB)
 File Encoding         : 65001

 Date: 20/02/2025 09:25:36
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for account
-- ----------------------------
DROP TABLE IF EXISTS `account`;
CREATE TABLE `account`  (
  `fullName` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `userName` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `password` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `role` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `status` int NULL DEFAULT NULL,
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`userName`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of account
-- ----------------------------
INSERT INTO `account` VALUES ('Admin', 'admin', '$2a$12$Y87zSnx.tpFvieylSeXuo.agjb7swi3UVnoo6KVMY9xP5STj4zJhm', 'Admin', 1, 'sinhbaoreact2003@gmail.com');
INSERT INTO `account` VALUES ('Hoàng Gia Bảo', 'bobo', '$2a$12$PhiTGBbHjHoB3dbS6BmCC.rzdMCBqDrdK9Y8Ae8GPcKe1RpHiWARO', 'Nhân viên xuất', 1, 'hgiabao2k3@gmail.com');
INSERT INTO `account` VALUES ('nguyễn văn hiệp', 'nttml123', '$2a$12$gjTgaV9hdgDvDOfRx9lgOutuoh7Ej6MGqbQPtAadxyAMMuleLfhqq', 'Nhân viên xuất', 1, 'hjeprua1216@gmail.com');
INSERT INTO `account` VALUES ('Trần Nhật Sinh', 'sinhsinh1122', '$2a$12$89As1J0AB0yrqGjnQUHtpevc6voGyvzAd8OvzkS1vGDo3YPO2P.Ia', 'Nhân viên nhập', 1, 'transinh342@gmail.com');
INSERT INTO `account` VALUES ('Nguyễn Thiên Ân', 'thienan', '$2a$12$myOaq0kATMzNkbxgzQEkPu8ht2K0pXOGzZMZo6nSBowq6EyoLo7tS', 'Quản lý kho', 1, 'a11611112003@gmail.com');

-- ----------------------------
-- Table structure for chitietphieunhap
-- ----------------------------
DROP TABLE IF EXISTS `chitietphieunhap`;
CREATE TABLE `chitietphieunhap`  (
  `maPhieu` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `maMay` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `soLuong` int NULL DEFAULT NULL,
  `donGia` double NULL DEFAULT NULL,
  PRIMARY KEY (`maPhieu`, `maMay`) USING BTREE,
  INDEX `FK_ChiTietPhieuNhap_MayTinh`(`maMay` ASC) USING BTREE,
  CONSTRAINT `FK_ChiTietPhieuNhap_MayTinh` FOREIGN KEY (`maMay`) REFERENCES `maytinh` (`maMay`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_ChiTietPhieuNhap_PhieuNhap` FOREIGN KEY (`maPhieu`) REFERENCES `phieunhap` (`maPhieu`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of chitietphieunhap
-- ----------------------------
INSERT INTO `chitietphieunhap` VALUES ('PN1', 'LP10', 1, 23490000);
INSERT INTO `chitietphieunhap` VALUES ('PN1', 'LP19', 1, 19490000);
INSERT INTO `chitietphieunhap` VALUES ('PN10', 'LP16', 1, 22990000);
INSERT INTO `chitietphieunhap` VALUES ('PN10', 'LP22', 1, 23490000);
INSERT INTO `chitietphieunhap` VALUES ('PN10', 'LP9', 4, 16490000);
INSERT INTO `chitietphieunhap` VALUES ('PN11', 'LP17', 1, 23190000);
INSERT INTO `chitietphieunhap` VALUES ('PN11', 'LP25', 1, 18390000);
INSERT INTO `chitietphieunhap` VALUES ('PN11', 'PC1', 8, 7090000);
INSERT INTO `chitietphieunhap` VALUES ('PN12', 'LP24', 1, 21490000);
INSERT INTO `chitietphieunhap` VALUES ('PN12', 'LP8', 1, 18390000);
INSERT INTO `chitietphieunhap` VALUES ('PN13', 'LP24', 1, 21490000);
INSERT INTO `chitietphieunhap` VALUES ('PN13', 'LP6', 1, 17490000);
INSERT INTO `chitietphieunhap` VALUES ('PN14', 'LP19', 1, 19490000);
INSERT INTO `chitietphieunhap` VALUES ('PN14', 'LP20', 1, 20790000);
INSERT INTO `chitietphieunhap` VALUES ('PN14', 'LP4', 1, 10690000);
INSERT INTO `chitietphieunhap` VALUES ('PN15', 'LP14', 1, 22490000);
INSERT INTO `chitietphieunhap` VALUES ('PN15', 'LP6', 1, 17490000);
INSERT INTO `chitietphieunhap` VALUES ('PN16', 'LP17', 1, 23190000);
INSERT INTO `chitietphieunhap` VALUES ('PN16', 'LP5', 1, 19290000);
INSERT INTO `chitietphieunhap` VALUES ('PN16', 'PC06', 1, 9690000);
INSERT INTO `chitietphieunhap` VALUES ('PN17', 'LP19', 1, 19490000);
INSERT INTO `chitietphieunhap` VALUES ('PN17', 'LP4', 1, 10690000);
INSERT INTO `chitietphieunhap` VALUES ('PN18', 'LP15', 1, 25190000);
INSERT INTO `chitietphieunhap` VALUES ('PN18', 'LP5', 1, 19290000);
INSERT INTO `chitietphieunhap` VALUES ('PN18', 'LP6', 1, 17490000);
INSERT INTO `chitietphieunhap` VALUES ('PN18', 'PC06', 1, 9690000);
INSERT INTO `chitietphieunhap` VALUES ('PN18', 'PC1', 1, 7090000);
INSERT INTO `chitietphieunhap` VALUES ('PN19', 'LP18', 1, 24990000);
INSERT INTO `chitietphieunhap` VALUES ('PN19', 'LP24', 1, 21490000);
INSERT INTO `chitietphieunhap` VALUES ('PN19', 'LP4', 1, 10690000);
INSERT INTO `chitietphieunhap` VALUES ('PN19', 'PC06', 1, 9690000);
INSERT INTO `chitietphieunhap` VALUES ('PN2', 'LP20', 1, 20790000);
INSERT INTO `chitietphieunhap` VALUES ('PN2', 'LP21', 1, 25990000);
INSERT INTO `chitietphieunhap` VALUES ('PN20', 'LP16', 1, 22990000);
INSERT INTO `chitietphieunhap` VALUES ('PN20', 'LP25', 10, 18390000);
INSERT INTO `chitietphieunhap` VALUES ('PN20', 'LP5', 1, 19290000);
INSERT INTO `chitietphieunhap` VALUES ('PN20', 'PC1', 1, 7090000);
INSERT INTO `chitietphieunhap` VALUES ('PN21', 'LP16', 45, 22990000);
INSERT INTO `chitietphieunhap` VALUES ('PN21', 'LP8', 25, 18390000);
INSERT INTO `chitietphieunhap` VALUES ('PN22', 'LP10', 1, 23490000);
INSERT INTO `chitietphieunhap` VALUES ('PN22', 'LP15', 1, 25190000);
INSERT INTO `chitietphieunhap` VALUES ('PN22', 'LP22', 1, 23490000);
INSERT INTO `chitietphieunhap` VALUES ('PN22', 'LP6', 1, 17490000);
INSERT INTO `chitietphieunhap` VALUES ('PN23', 'LP19', 2, 19490000);
INSERT INTO `chitietphieunhap` VALUES ('PN23', 'LP5', 2, 19290000);
INSERT INTO `chitietphieunhap` VALUES ('PN23', 'LP7', 2, 17490000);
INSERT INTO `chitietphieunhap` VALUES ('PN24', 'PC7', 20, 116990000);
INSERT INTO `chitietphieunhap` VALUES ('PN25', 'LP14', 1, 22490000);
INSERT INTO `chitietphieunhap` VALUES ('PN26', 'LP15', 2, 25190000);
INSERT INTO `chitietphieunhap` VALUES ('PN27', 'LP5', 10, 19290000);
INSERT INTO `chitietphieunhap` VALUES ('PN28', 'LP15', 2, 25190000);
INSERT INTO `chitietphieunhap` VALUES ('PN28', 'LP24', 1, 21490000);
INSERT INTO `chitietphieunhap` VALUES ('PN29', 'LP4', 1, 10690000);
INSERT INTO `chitietphieunhap` VALUES ('PN3', 'LP15', 1, 25190000);
INSERT INTO `chitietphieunhap` VALUES ('PN3', 'LP22', 1, 23490000);
INSERT INTO `chitietphieunhap` VALUES ('PN3', 'LP25', 1, 18390000);
INSERT INTO `chitietphieunhap` VALUES ('PN3', 'LP4', 2, 10690000);
INSERT INTO `chitietphieunhap` VALUES ('PN30', 'LP16', 1, 22990000);
INSERT INTO `chitietphieunhap` VALUES ('PN30', 'LP24', 1, 21490000);
INSERT INTO `chitietphieunhap` VALUES ('PN30', 'LP3', 1, 15000000);
INSERT INTO `chitietphieunhap` VALUES ('PN31', 'LP14', 1, 22490000);
INSERT INTO `chitietphieunhap` VALUES ('PN31', 'LP25', 1, 18390000);
INSERT INTO `chitietphieunhap` VALUES ('PN31', 'LP7', 1, 17490000);
INSERT INTO `chitietphieunhap` VALUES ('PN32', 'LP18', 1, 24990000);
INSERT INTO `chitietphieunhap` VALUES ('PN32', 'LP5', 1, 19290000);
INSERT INTO `chitietphieunhap` VALUES ('PN32', 'LP9', 1, 16490000);
INSERT INTO `chitietphieunhap` VALUES ('PN32', 'PC2', 1, 8290000);
INSERT INTO `chitietphieunhap` VALUES ('PN32', 'PC7', 1, 11200000);
INSERT INTO `chitietphieunhap` VALUES ('PN33', 'LP14', 1, 22490000);
INSERT INTO `chitietphieunhap` VALUES ('PN33', 'LP22', 1, 23490000);
INSERT INTO `chitietphieunhap` VALUES ('PN33', 'LP3', 1, 15000000);
INSERT INTO `chitietphieunhap` VALUES ('PN33', 'PC1', 1, 7090000);
INSERT INTO `chitietphieunhap` VALUES ('PN34', 'LP25', 7, 18390000);
INSERT INTO `chitietphieunhap` VALUES ('PN34', 'LP6', 20, 17490000);
INSERT INTO `chitietphieunhap` VALUES ('PN34', 'LP8', 1, 18390000);
INSERT INTO `chitietphieunhap` VALUES ('PN34', 'PC2', 2, 8290000);
INSERT INTO `chitietphieunhap` VALUES ('PN35', 'LP24', 1, 21490000);
INSERT INTO `chitietphieunhap` VALUES ('PN35', 'LP4', 1, 10690000);
INSERT INTO `chitietphieunhap` VALUES ('PN35', 'LP8', 1, 18390000);
INSERT INTO `chitietphieunhap` VALUES ('PN35', 'PC5', 1, 9190000);
INSERT INTO `chitietphieunhap` VALUES ('PN36', 'LP19', 20, 19490000);
INSERT INTO `chitietphieunhap` VALUES ('PN37', 'LP19', 1, 19490000);
INSERT INTO `chitietphieunhap` VALUES ('PN37', 'LP4', 1, 10690000);
INSERT INTO `chitietphieunhap` VALUES ('PN37', 'LP8', 3, 18390000);
INSERT INTO `chitietphieunhap` VALUES ('PN38', 'LP23', 20, 15690000);
INSERT INTO `chitietphieunhap` VALUES ('PN38', 'LP6', 30, 17490000);
INSERT INTO `chitietphieunhap` VALUES ('PN4', 'LP5', 1, 19290000);
INSERT INTO `chitietphieunhap` VALUES ('PN4', 'LP7', 1, 17490000);
INSERT INTO `chitietphieunhap` VALUES ('PN4', 'LP9', 1, 16490000);
INSERT INTO `chitietphieunhap` VALUES ('PN5', 'LP4', 3, 10690000);
INSERT INTO `chitietphieunhap` VALUES ('PN6', 'LP17', 1, 23190000);
INSERT INTO `chitietphieunhap` VALUES ('PN6', 'LP3', 1, 15000000);
INSERT INTO `chitietphieunhap` VALUES ('PN7', 'LP15', 1, 25190000);
INSERT INTO `chitietphieunhap` VALUES ('PN7', 'LP25', 1, 18390000);
INSERT INTO `chitietphieunhap` VALUES ('PN7', 'LP8', 5, 18390000);
INSERT INTO `chitietphieunhap` VALUES ('PN8', 'LP19', 1, 19490000);
INSERT INTO `chitietphieunhap` VALUES ('PN8', 'LP4', 1, 10690000);
INSERT INTO `chitietphieunhap` VALUES ('PN8', 'LP9', 1, 16490000);
INSERT INTO `chitietphieunhap` VALUES ('PN9', 'LP21', 1, 25990000);
INSERT INTO `chitietphieunhap` VALUES ('PN9', 'LP6', 1, 17490000);

-- ----------------------------
-- Table structure for chitietphieuxuat
-- ----------------------------
DROP TABLE IF EXISTS `chitietphieuxuat`;
CREATE TABLE `chitietphieuxuat`  (
  `maPhieu` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `maMay` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `soLuong` int NULL DEFAULT NULL,
  `donGia` double NULL DEFAULT NULL,
  PRIMARY KEY (`maPhieu`, `maMay`) USING BTREE,
  INDEX `FK_ChiTietPhieuXuat_MayTinh`(`maMay` ASC) USING BTREE,
  CONSTRAINT `FK_ChiTietPhieuXuat_MayTinh` FOREIGN KEY (`maMay`) REFERENCES `maytinh` (`maMay`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_ChiTietPhieuXuat_PhieuXuat` FOREIGN KEY (`maPhieu`) REFERENCES `phieuxuat` (`maPhieu`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of chitietphieuxuat
-- ----------------------------
INSERT INTO `chitietphieuxuat` VALUES ('PX1', 'LP10', 1, 23490000);
INSERT INTO `chitietphieuxuat` VALUES ('PX1', 'LP19', 13, 19490000);
INSERT INTO `chitietphieuxuat` VALUES ('PX1', 'LP3', 1, 15000000);
INSERT INTO `chitietphieuxuat` VALUES ('PX10', 'LP20', 1, 20790000);
INSERT INTO `chitietphieuxuat` VALUES ('PX10', 'LP4', 1, 10690000);
INSERT INTO `chitietphieuxuat` VALUES ('PX10', 'LP8', 1, 18390000);
INSERT INTO `chitietphieuxuat` VALUES ('PX10', 'PC06', 1, 9690000);
INSERT INTO `chitietphieuxuat` VALUES ('PX10', 'PC2', 1, 8290000);
INSERT INTO `chitietphieuxuat` VALUES ('PX12', 'LP20', 1, 20790000);
INSERT INTO `chitietphieuxuat` VALUES ('PX12', 'LP6', 1, 17490000);
INSERT INTO `chitietphieuxuat` VALUES ('PX12', 'PC1', 1, 7090000);
INSERT INTO `chitietphieuxuat` VALUES ('PX13', 'LP18', 1, 24990000);
INSERT INTO `chitietphieuxuat` VALUES ('PX13', 'LP5', 2, 19290000);
INSERT INTO `chitietphieuxuat` VALUES ('PX13', 'LP6', 1, 17490000);
INSERT INTO `chitietphieuxuat` VALUES ('PX13', 'PC1', 4, 7090000);
INSERT INTO `chitietphieuxuat` VALUES ('PX14', 'LP20', 1, 20790000);
INSERT INTO `chitietphieuxuat` VALUES ('PX14', 'LP24', 1, 21490000);
INSERT INTO `chitietphieuxuat` VALUES ('PX14', 'LP8', 1, 18390000);
INSERT INTO `chitietphieuxuat` VALUES ('PX14', 'PC06', 1, 9690000);
INSERT INTO `chitietphieuxuat` VALUES ('PX14', 'PC2', 1, 8290000);
INSERT INTO `chitietphieuxuat` VALUES ('PX15', 'LP17', 1, 23190000);
INSERT INTO `chitietphieuxuat` VALUES ('PX15', 'LP20', 1, 20790000);
INSERT INTO `chitietphieuxuat` VALUES ('PX15', 'LP5', 1, 19290000);
INSERT INTO `chitietphieuxuat` VALUES ('PX15', 'LP9', 1, 16490000);
INSERT INTO `chitietphieuxuat` VALUES ('PX15', 'PC1', 1, 7090000);
INSERT INTO `chitietphieuxuat` VALUES ('PX16', 'LP14', 4, 22490000);
INSERT INTO `chitietphieuxuat` VALUES ('PX16', 'LP20', 1, 20790000);
INSERT INTO `chitietphieuxuat` VALUES ('PX16', 'LP21', 1, 25990000);
INSERT INTO `chitietphieuxuat` VALUES ('PX17', 'LP21', 2, 25990000);
INSERT INTO `chitietphieuxuat` VALUES ('PX18', 'LP16', 5, 22990000);
INSERT INTO `chitietphieuxuat` VALUES ('PX18', 'LP8', 2, 18390000);
INSERT INTO `chitietphieuxuat` VALUES ('PX19', 'LP18', 1, 24990000);
INSERT INTO `chitietphieuxuat` VALUES ('PX19', 'LP23', 1, 15690000);
INSERT INTO `chitietphieuxuat` VALUES ('PX19', 'PC06', 1, 9690000);
INSERT INTO `chitietphieuxuat` VALUES ('PX19', 'PC3', 1, 8990000);
INSERT INTO `chitietphieuxuat` VALUES ('PX2', 'LP21', 1, 25990000);
INSERT INTO `chitietphieuxuat` VALUES ('PX2', 'LP6', 2, 17490000);
INSERT INTO `chitietphieuxuat` VALUES ('PX2', 'PC06', 1, 9690000);
INSERT INTO `chitietphieuxuat` VALUES ('PX20', 'LP6', 2, 17490000);
INSERT INTO `chitietphieuxuat` VALUES ('PX20', 'LP9', 1, 16490000);
INSERT INTO `chitietphieuxuat` VALUES ('PX20', 'PC06', 1, 9690000);
INSERT INTO `chitietphieuxuat` VALUES ('PX20', 'PC3', 2, 8990000);
INSERT INTO `chitietphieuxuat` VALUES ('PX21', 'LP23', 1, 15690000);
INSERT INTO `chitietphieuxuat` VALUES ('PX21', 'LP7', 1, 17490000);
INSERT INTO `chitietphieuxuat` VALUES ('PX21', 'PC06', 2, 9690000);
INSERT INTO `chitietphieuxuat` VALUES ('PX21', 'PC2', 1, 8290000);
INSERT INTO `chitietphieuxuat` VALUES ('PX22', 'LP5', 1, 19290000);
INSERT INTO `chitietphieuxuat` VALUES ('PX22', 'LP9', 1, 16490000);
INSERT INTO `chitietphieuxuat` VALUES ('PX23', 'LP23', 1, 15690000);
INSERT INTO `chitietphieuxuat` VALUES ('PX23', 'PC06', 1, 9690000);
INSERT INTO `chitietphieuxuat` VALUES ('PX23', 'PC1', 1, 7090000);
INSERT INTO `chitietphieuxuat` VALUES ('PX23', 'PC3', 1, 8990000);
INSERT INTO `chitietphieuxuat` VALUES ('PX24', 'LP19', 1, 19490000);
INSERT INTO `chitietphieuxuat` VALUES ('PX24', 'LP8', 1, 18390000);
INSERT INTO `chitietphieuxuat` VALUES ('PX24', 'LP9', 1, 16490000);
INSERT INTO `chitietphieuxuat` VALUES ('PX24', 'PC3', 1, 8990000);
INSERT INTO `chitietphieuxuat` VALUES ('PX25', 'LP19', 1, 19490000);
INSERT INTO `chitietphieuxuat` VALUES ('PX25', 'LP7', 2, 17490000);
INSERT INTO `chitietphieuxuat` VALUES ('PX25', 'PC1', 1, 7090000);
INSERT INTO `chitietphieuxuat` VALUES ('PX26', 'LP19', 1, 19490000);
INSERT INTO `chitietphieuxuat` VALUES ('PX26', 'LP22', 1, 23490000);
INSERT INTO `chitietphieuxuat` VALUES ('PX26', 'LP3', 1, 15000000);
INSERT INTO `chitietphieuxuat` VALUES ('PX26', 'LP5', 1, 19290000);
INSERT INTO `chitietphieuxuat` VALUES ('PX26', 'LP6', 1, 17490000);
INSERT INTO `chitietphieuxuat` VALUES ('PX3', 'LP22', 1, 23490000);
INSERT INTO `chitietphieuxuat` VALUES ('PX3', 'LP4', 1, 10690000);
INSERT INTO `chitietphieuxuat` VALUES ('PX3', 'LP8', 1, 18390000);
INSERT INTO `chitietphieuxuat` VALUES ('PX4', 'LP17', 1, 23190000);
INSERT INTO `chitietphieuxuat` VALUES ('PX4', 'LP6', 1, 17490000);
INSERT INTO `chitietphieuxuat` VALUES ('PX4', 'LP7', 1, 17490000);
INSERT INTO `chitietphieuxuat` VALUES ('PX5', 'LP16', 1, 22990000);
INSERT INTO `chitietphieuxuat` VALUES ('PX5', 'LP5', 1, 19290000);
INSERT INTO `chitietphieuxuat` VALUES ('PX5', 'LP7', 1, 17490000);
INSERT INTO `chitietphieuxuat` VALUES ('PX5', 'LP9', 1, 16490000);
INSERT INTO `chitietphieuxuat` VALUES ('PX5', 'PC3', 1, 8990000);
INSERT INTO `chitietphieuxuat` VALUES ('PX6', 'LP12', 1, 13090000);
INSERT INTO `chitietphieuxuat` VALUES ('PX6', 'LP13', 1, 9990000);
INSERT INTO `chitietphieuxuat` VALUES ('PX6', 'LP15', 1, 25190000);
INSERT INTO `chitietphieuxuat` VALUES ('PX6', 'LP17', 1, 23190000);
INSERT INTO `chitietphieuxuat` VALUES ('PX7', 'LP21', 1, 25990000);
INSERT INTO `chitietphieuxuat` VALUES ('PX7', 'LP5', 1, 19290000);
INSERT INTO `chitietphieuxuat` VALUES ('PX7', 'PC1', 1, 7090000);
INSERT INTO `chitietphieuxuat` VALUES ('PX8', 'LP24', 1, 21490000);
INSERT INTO `chitietphieuxuat` VALUES ('PX8', 'LP3', 1, 15000000);
INSERT INTO `chitietphieuxuat` VALUES ('PX8', 'LP5', 1, 19290000);
INSERT INTO `chitietphieuxuat` VALUES ('PX8', 'LP8', 1, 18390000);
INSERT INTO `chitietphieuxuat` VALUES ('PX8', 'PC1', 1, 7090000);
INSERT INTO `chitietphieuxuat` VALUES ('PX9', 'LP21', 1, 25990000);
INSERT INTO `chitietphieuxuat` VALUES ('PX9', 'LP22', 1, 23490000);
INSERT INTO `chitietphieuxuat` VALUES ('PX9', 'LP4', 1, 10690000);

-- ----------------------------
-- Table structure for maytinh
-- ----------------------------
DROP TABLE IF EXISTS `maytinh`;
CREATE TABLE `maytinh`  (
  `maMay` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `tenMay` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `soLuong` int NOT NULL DEFAULT 0,
  `tenCpu` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0',
  `ram` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0',
  `cardManHInh` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `gia` double NOT NULL DEFAULT 0,
  `mainBoard` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `congSuatNguon` int NULL DEFAULT NULL,
  `loaiMay` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `rom` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `kichThuocMan` double NULL DEFAULT NULL,
  `dungLuongPin` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `xuatXu` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `trangThai` int NULL DEFAULT NULL,
  PRIMARY KEY (`maMay`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of maytinh
-- ----------------------------
INSERT INTO `maytinh` VALUES ('LP10', 'Laptop Lenovo IdeaPad Gaming 3', 36, 'Intel Core i5 12500H', '16 GB', 'NVIDIA GeForce RTX 3050', 23490000, NULL, NULL, 'Laptop', '512 GB', 15.6, '4 Cell', 'Trung Quốc', 0);
INSERT INTO `maytinh` VALUES ('LP12', 'Laptop MSI Modern 14 B11MOU-1028VN', 23, 'Intel Core i3 115G4', '8 GB', 'Intel UHD Graphics', 13090000, NULL, NULL, 'Laptop', '256 GB', 14, '3 Cell', 'Trung Quốc', 0);
INSERT INTO `maytinh` VALUES ('LP13', 'Laptop HP 15s-fq2663TU', 19, 'Intel Core i3 1115G4', '4 GB', 'Intel UHD Graphics', 9990000, NULL, NULL, 'Laptop', '256 GB', 15.6, '3 Cell', 'Trung Quốc', 1);
INSERT INTO `maytinh` VALUES ('LP14', 'Laptop Lenovo IdeaPad 5 Pro 16IAH7', 3, 'Intel Core i5 12500H', '16 GB', 'Intel Iris Xe Graphics', 22490000, NULL, NULL, 'Laptop', '512 GB', 16, '4 Cell', 'Trung Quốc', 1);
INSERT INTO `maytinh` VALUES ('LP15', 'Laptop Lenovo IdeaPad 5 Pro 16IAH7', 28, 'Intel Core i7 12700H', '16 GB', 'Intel Iris Xe Graphics', 25190000, NULL, NULL, 'Laptop', '512 GB', 16, '75 Wh', 'Trung Quốc', 1);
INSERT INTO `maytinh` VALUES ('LP16', 'Laptop Acer Nitro Gaming AN515-57-54MV', 62, 'Intel Core i5 11400H', '8', 'NVIDIA GeForce RTX 3050', 22990000, NULL, NULL, 'Laptop', '512 GB', 15.6, '4 Cell ', 'Trung Quốc', 1);
INSERT INTO `maytinh` VALUES ('LP17', 'Laptop MSI Gaming Katana GF66 12UCK-815VN', 22, 'Intel Core i5 12450H', '8 GB', 'Intel UHD Graphics', 23190000, NULL, NULL, 'Laptop', '512 GB', 15.6, '53.5 Wh', 'Trung Quốc', 1);
INSERT INTO `maytinh` VALUES ('LP18', 'Laptop Asus TUF Gaming FX517ZC-HN077W', 23, 'Intel Core i5 12450H', '8 GB', 'NVIDIA GeForce RTX 3050', 24990000, NULL, NULL, 'Laptop', '512 GB', 15.6, '4 Cell', 'Trung Quốc', 1);
INSERT INTO `maytinh` VALUES ('LP19', 'Laptop HP Gaming Victus 16-e0175AX', 18, 'AMD Ryzen 5 5600H', '8 GB', 'NVIDIA GeForce RTX 3050 Ti', 19490000, NULL, NULL, 'Laptop', '512 GB', 16.1, '4 Cell', 'Trung Quốc', 1);
INSERT INTO `maytinh` VALUES ('LP20', 'Laptop MSI GF63 Thin 11UC-444VN', 19, 'Intel Core i5 11400H', '8 GB', 'NVIDIA GeForce RTX 3050', 20790000, NULL, NULL, 'Laptop', '512 GB', 15.6, '3 Cell', 'Trung Quốc', 1);
INSERT INTO `maytinh` VALUES ('LP21', 'Laptop Asus TUF Gaming FX517ZE-HN045W', 16, 'Intel Core i5 12450H', '8 GB', 'NVIDIA GeForce RTX 3050 Ti', 25990000, NULL, NULL, 'Laptop', '512 GB', 15.6, '4 Cell', 'Trung Quốc', 1);
INSERT INTO `maytinh` VALUES ('LP22', 'Laptop Lenovo Yoga Slim 7 Pro 14IHU5O', 20, 'Intel Core i5 11300H', '16 GB', 'Intel Iris Xe Graphics', 23490000, NULL, NULL, 'Laptop', '512 GB', 14, '4 Cell ', 'Trung Quốc', 1);
INSERT INTO `maytinh` VALUES ('LP23', 'Laptop Gigabyte U4 UD-50VN823SO', 37, 'Intel Core i5 1155G7', '16 GB', 'Intel Iris Xe Graphics', 15690000, NULL, NULL, 'Laptop', '512 GB', 14, '36 Wh', 'Trung Quốc', 1);
INSERT INTO `maytinh` VALUES ('LP24', 'Laptop Dell Vostro V5410 i5', 34, 'Intel Core i5 11320H', '8 GB', 'Intel Iris Xe Graphics', 21490000, NULL, NULL, 'Laptop', '512 GB', 14, '4 Cell', 'Trung Quốc', 1);
INSERT INTO `maytinh` VALUES ('LP25', 'Laptop MSI Gaming GF63 Thin 11SC-666VN', 53, 'Intel Core i5 11400H', '8 GB', 'NVIDIA GeForce GTX 1650', 18390000, NULL, NULL, 'Laptop', '512 GB', 15.6, '3 Cell', 'Trung Quốc', 1);
INSERT INTO `maytinh` VALUES ('LP3', 'Lenovo ThinkPad E14', 83, 'Intel Core i5 11G352', '8GB', 'OnBoard', 15000000, NULL, NULL, 'Laptop', '521GB', 14, '45Wh', 'Trung Quốc', 1);
INSERT INTO `maytinh` VALUES ('LP4', 'Lenovo Ideapad 3 15ITL6', 118, 'Intel Core i3 1115G4', '8GB', 'Onboard', 10690000, NULL, NULL, 'Laptop', '512GB', 15.6, '35Wh', 'Trung Quốc', 1);
INSERT INTO `maytinh` VALUES ('LP5', 'Gigabyte Gaming G5 GD', 11, 'Intel Core i5 11400H', '16GB', 'NVIDIA GeForce RTX 3050 4GB', 19290000, NULL, NULL, 'Laptop', '512GB', 15.6, '50Wh', 'Trung Quốc', 1);
INSERT INTO `maytinh` VALUES ('LP6', 'MSI Gaming GF63 Thin 11SC-1090VN', 90, 'Intel Core i5 11400H', '8GB', 'NVIDIA GeForce GTX 1650 4GB', 17490000, NULL, NULL, 'Laptop', '512GB', 15.6, '50Wh', 'Trung Quốc', 1);
INSERT INTO `maytinh` VALUES ('LP7', 'Laptop Asus TUF Gaming FX506LHB-HN188W', 19, 'Intel Core i5 10300H', '8 GB', 'NVIDIA GeForce GTX 1650', 17490000, NULL, NULL, 'Laptop', '512 GB', 15.6, '3 Cell', 'Trung Quốc', 1);
INSERT INTO `maytinh` VALUES ('LP8', 'Laptop MSI Gaming GF63 Thin 11SC-1090VN', 60, 'Intel Core i5 11400H', '8 GB', 'NVIDIA GeForce GTX 1650 4GB', 18390000, NULL, NULL, 'Laptop', '512 GB', 15.6, '3 Cell', 'Trung Quốc', 1);
INSERT INTO `maytinh` VALUES ('LP9', 'Laptop Asus TUF Gaming FA506IHRB-HN019W', 25, 'AMD Ryzen 5 4600H', '8 GB', 'NVIDIA GeForce GTX 1650', 16490000, NULL, NULL, 'Laptop', '512 GB', 12, '3 cell', 'Trung Quốc', 1);
INSERT INTO `maytinh` VALUES ('PC06', 'PC E-Power Office 08', 19, 'Intel Core i5 11400', '16 GB', 'Intel UHD Graphics 730', 9690000, 'Intel H510', 9690000, 'PC - Lắp ráp', '240 GB', NULL, NULL, 'Việt Nam', 1);
INSERT INTO `maytinh` VALUES ('PC1', 'PC E-Power Office 04', 16, 'Intel Core i3 10105', '8GB', 'Intel HD Graphics 630', 7090000, 'Intel H510', 0, 'Laptop', '240GB', NULL, NULL, 'Việt Nam', 1);
INSERT INTO `maytinh` VALUES ('PC2', 'PC E-Power Office 05', 30, 'Intel Core i5 10400', '8 GB', 'Intel UHD Graphics 630', 8290000, 'Intel H510', 300, 'PC - Lắp ráp', '8 GB', NULL, NULL, 'Việt Nam', 1);
INSERT INTO `maytinh` VALUES ('PC3', 'PC E-Power Office 07', 19, 'Intel Core i5 11400', '8 GB', 'Intel UHD Graphics 730', 8990000, 'Intel H510', 8990000, 'PC - Lắp ráp', '240 GB', NULL, NULL, 'Việt Nam', 1);
INSERT INTO `maytinh` VALUES ('PC30', 'ASUS Vivobook', 1, ' Ryzen 7 5800H ', '16GB', 'GTX 3060', 25000000, NULL, NULL, 'Laptop', '512GB', 24, '3000', 'Việt Nam', 1);
INSERT INTO `maytinh` VALUES ('PC4', 'PC Gaming E-Power G1650', 71, 'Intel Core i3 10100F', '8 GB', 'Intel UHD Graphics 730', 11990000, 'Intel H510', 300, 'PC - Lắp ráp', '240 GB', NULL, NULL, 'Việt Nam', 1);
INSERT INTO `maytinh` VALUES ('PC5', 'PC E-Power Office 06', 33, 'Intel Core i5 10400', '16 GB', 'Intel HD Graphics 630', 9190000, 'Intel H510', 200, 'PC - Lắp ráp', '240 GB', NULL, NULL, 'Việt Nam', 1);
INSERT INTO `maytinh` VALUES ('PC7', 'PC Acer Aspire AS-XC780 DT.B8ASV.006', 21, ' Intel Core i5-7400', '4GB', ' Intel HD Graphics 630 / GeForce GT 720 2GB', 11200000, 'Intel H510', 300, 'PC - Lắp ráp', '1TB', NULL, NULL, 'Việt Nam', 1);

-- ----------------------------
-- Table structure for nhacungcap
-- ----------------------------
DROP TABLE IF EXISTS `nhacungcap`;
CREATE TABLE `nhacungcap`  (
  `maNhaCungCap` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `tenNhaCungCap` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `Sdt` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `diaChi` varchar(150) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`maNhaCungCap`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of nhacungcap
-- ----------------------------
INSERT INTO `nhacungcap` VALUES ('ANPHAT', 'Công Ty TNHH Điều Khiển Tự Động An Phát', '02835109735', '86/21 Phan Tây Hồ, P. 7, Q. Phú Nhuận TP. Hồ Chí Minh');
INSERT INTO `nhacungcap` VALUES ('CODO', 'Công Ty TNHH Thương Mại Dịch Vụ Hoàng Cố Đô', '02838115345', '622/16/5 Cộng Hòa, Phường 13, Quận Tân Bình, TP HCM		');
INSERT INTO `nhacungcap` VALUES ('FPT', 'Công Ty Cổ Phần Bán Lẻ Kỹ Thuật Số FPT', '02873023456', '261 - 263 Khánh Hội, P2, Q4, TP. Hồ Chí Minh');
INSERT INTO `nhacungcap` VALUES ('HACOM', 'Công ty Cổ phần đầu tư công nghệ HACOM', '1900 1903', 'Số 129 - 131, phố Lê Thanh Nghị, Phường Đồng Tâm, Quận Hai Bà Trưng, Hà Nội');
INSERT INTO `nhacungcap` VALUES ('HOANGPHAT', 'Công Ty TNHH Thương Mại Hoàng Phát Hải Phòng', '02253250888', 'Số 4, Lô 2A Lê Hồng Phong, Ngô Quyền, Tp. Hải Phòng');
INSERT INTO `nhacungcap` VALUES ('PHONGVU', 'Công ty cổ phần dịch vụ - thương mại Phong Vũ', '0967567567', 'Tầng 5, Số 117-119-121 Nguyễn Du, Phường Bến Thành, Quận 1, Thành Phố Hồ Chí Minh');
INSERT INTO `nhacungcap` VALUES ('TGDĐ', 'Công ty cổ phần Thế Giới Di Động', '028 38125960', '128 Trần Quang Khải, P. Tân Định, Q.1, TP.Hồ Chí Minh');
INSERT INTO `nhacungcap` VALUES ('VIETSTARS', 'Công ty CP Công nghệ Thương mại Dịch vụ Vietstars', '090 469 0212', ' Số 109 Lê Thanh Nghị  TP Hải dương');

-- ----------------------------
-- Table structure for phieunhap
-- ----------------------------
DROP TABLE IF EXISTS `phieunhap`;
CREATE TABLE `phieunhap`  (
  `maPhieu` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `thoiGianTao` timestamp NULL DEFAULT NULL,
  `nguoiTao` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `maNhaCungCap` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `tongTien` double NOT NULL,
  PRIMARY KEY (`maPhieu`) USING BTREE,
  INDEX `FK_PhieuNhap_NhaCungCap`(`maNhaCungCap` ASC) USING BTREE,
  INDEX `FK_PhieuNhap_Account`(`nguoiTao` ASC) USING BTREE,
  CONSTRAINT `FK_PhieuNhap_Account` FOREIGN KEY (`nguoiTao`) REFERENCES `account` (`userName`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_PhieuNhap_NhaCungCap` FOREIGN KEY (`maNhaCungCap`) REFERENCES `nhacungcap` (`maNhaCungCap`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of phieunhap
-- ----------------------------
INSERT INTO `phieunhap` VALUES ('PN1', '2022-12-01 20:59:09', 'admin', 'FPT', 42980000);
INSERT INTO `phieunhap` VALUES ('PN10', '2022-12-08 01:04:19', 'admin', 'HOANGPHAT', 112440000);
INSERT INTO `phieunhap` VALUES ('PN11', '2022-12-08 01:48:21', 'admin', 'FPT', 98300000);
INSERT INTO `phieunhap` VALUES ('PN12', '2022-12-16 09:19:48', 'admin', 'HACOM', 39880000);
INSERT INTO `phieunhap` VALUES ('PN13', '2022-12-16 09:19:36', 'admin', 'PHONGVU', 38980000);
INSERT INTO `phieunhap` VALUES ('PN14', '2022-12-08 19:28:57', 'admin', 'FPT', 50970000);
INSERT INTO `phieunhap` VALUES ('PN15', '2022-12-08 19:36:25', 'admin', 'FPT', 39980000);
INSERT INTO `phieunhap` VALUES ('PN16', '2022-12-08 23:30:48', 'admin', 'HOANGPHAT', 52170000);
INSERT INTO `phieunhap` VALUES ('PN17', '2022-12-09 21:29:43', 'admin', 'FPT', 30180000);
INSERT INTO `phieunhap` VALUES ('PN18', '2022-12-10 00:08:19', 'admin', 'FPT', 78750000);
INSERT INTO `phieunhap` VALUES ('PN19', '2022-12-12 14:09:21', 'admin', 'PHONGVU', 66860000);
INSERT INTO `phieunhap` VALUES ('PN2', '2022-12-01 20:59:23', 'admin', 'FPT', 46780000);
INSERT INTO `phieunhap` VALUES ('PN20', '2022-12-14 02:46:37', 'admin', 'PHONGVU', 233270000);
INSERT INTO `phieunhap` VALUES ('PN21', '2022-12-14 18:54:21', 'admin', 'PHONGVU', 1536180000);
INSERT INTO `phieunhap` VALUES ('PN22', '2022-12-14 19:32:09', 'admin', 'FPT', 89660000);
INSERT INTO `phieunhap` VALUES ('PN23', '2022-12-14 21:28:52', 'admin', 'FPT', 112540000);
INSERT INTO `phieunhap` VALUES ('PN24', '2022-12-14 21:41:23', 'admin', 'FPT', 2339800000);
INSERT INTO `phieunhap` VALUES ('PN25', '2022-12-14 22:18:25', 'admin', 'ANPHAT', 22490000);
INSERT INTO `phieunhap` VALUES ('PN26', '2022-12-15 03:01:37', 'admin', 'ANPHAT', 50380000);
INSERT INTO `phieunhap` VALUES ('PN27', '2022-12-15 03:02:31', 'admin', 'ANPHAT', 192900000);
INSERT INTO `phieunhap` VALUES ('PN28', '2022-12-15 17:43:36', 'admin', 'ANPHAT', 71870000);
INSERT INTO `phieunhap` VALUES ('PN29', '2022-12-16 09:19:55', 'admin', 'CODO', 10690000);
INSERT INTO `phieunhap` VALUES ('PN3', '2022-12-03 10:58:18', 'admin', 'FPT', 88450000);
INSERT INTO `phieunhap` VALUES ('PN30', '2022-12-16 06:13:22', 'admin', 'ANPHAT', 59480000);
INSERT INTO `phieunhap` VALUES ('PN31', '2022-12-16 06:14:59', 'sinhsinh1122', 'ANPHAT', 58370000);
INSERT INTO `phieunhap` VALUES ('PN32', '2022-12-16 09:19:27', 'admin', 'HOANGPHAT', 80260000);
INSERT INTO `phieunhap` VALUES ('PN33', '2022-12-16 20:09:45', 'sinhsinh1122', 'VIETSTARS', 68070000);
INSERT INTO `phieunhap` VALUES ('PN34', '2022-12-16 22:31:14', 'admin', 'HOANGPHAT', 513500000);
INSERT INTO `phieunhap` VALUES ('PN35', '2022-12-16 22:36:48', 'admin', 'HOANGPHAT', 59760000);
INSERT INTO `phieunhap` VALUES ('PN36', '2022-12-16 22:40:31', 'admin', 'ANPHAT', 389800000);
INSERT INTO `phieunhap` VALUES ('PN37', '2022-12-17 08:02:09', 'admin', 'ANPHAT', 85350000);
INSERT INTO `phieunhap` VALUES ('PN38', '2022-12-17 08:08:48', 'admin', 'ANPHAT', 838500000);
INSERT INTO `phieunhap` VALUES ('PN4', '2022-12-03 10:58:37', 'admin', 'TGDĐ', 53270000);
INSERT INTO `phieunhap` VALUES ('PN5', '2022-12-07 00:51:02', 'admin', 'FPT', 32070000);
INSERT INTO `phieunhap` VALUES ('PN6', '2022-12-07 01:50:32', 'admin', 'FPT', 38190000);
INSERT INTO `phieunhap` VALUES ('PN7', '2022-12-07 01:59:43', 'admin', 'PHONGVU', 135530000);
INSERT INTO `phieunhap` VALUES ('PN8', '2022-12-07 02:15:01', 'admin', 'FPT', 46670000);
INSERT INTO `phieunhap` VALUES ('PN9', '2022-12-07 02:20:44', 'admin', 'FPT', 43480000);

-- ----------------------------
-- Table structure for phieuxuat
-- ----------------------------
DROP TABLE IF EXISTS `phieuxuat`;
CREATE TABLE `phieuxuat`  (
  `maPhieu` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `thoiGianTao` timestamp NOT NULL DEFAULT current_timestamp ON UPDATE CURRENT_TIMESTAMP,
  `nguoiTao` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `tongTien` double NOT NULL,
  PRIMARY KEY (`maPhieu`) USING BTREE,
  INDEX `FK_PhieuXuat_Account`(`nguoiTao` ASC) USING BTREE,
  CONSTRAINT `FK_PhieuXuat_Account` FOREIGN KEY (`nguoiTao`) REFERENCES `account` (`userName`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of phieuxuat
-- ----------------------------
INSERT INTO `phieuxuat` VALUES ('PX1', '2022-12-01 21:10:44', 'admin', 291860000);
INSERT INTO `phieuxuat` VALUES ('PX10', '2022-12-08 01:41:08', 'admin', 57160000);
INSERT INTO `phieuxuat` VALUES ('PX12', '2022-12-08 02:06:56', 'admin', 45370000);
INSERT INTO `phieuxuat` VALUES ('PX13', '2022-12-08 19:31:48', 'admin', 109420000);
INSERT INTO `phieuxuat` VALUES ('PX14', '2022-12-08 23:30:10', 'admin', 78650000);
INSERT INTO `phieuxuat` VALUES ('PX15', '2022-12-13 05:31:09', 'admin', 86850000);
INSERT INTO `phieuxuat` VALUES ('PX16', '2022-12-14 22:04:47', 'admin', 136740000);
INSERT INTO `phieuxuat` VALUES ('PX17', '2022-12-14 22:34:07', 'admin', 51980000);
INSERT INTO `phieuxuat` VALUES ('PX18', '2022-12-16 03:32:57', 'admin', 151730000);
INSERT INTO `phieuxuat` VALUES ('PX19', '2022-12-16 09:20:15', 'Admin', 59360000);
INSERT INTO `phieuxuat` VALUES ('PX2', '2022-12-04 23:45:43', 'admin', 70660000);
INSERT INTO `phieuxuat` VALUES ('PX20', '2022-12-16 20:26:33', 'Admin', 79140000);
INSERT INTO `phieuxuat` VALUES ('PX21', '2022-12-16 20:36:43', 'Admin', 60850000);
INSERT INTO `phieuxuat` VALUES ('PX22', '2022-12-16 23:39:41', 'Admin', 35780000);
INSERT INTO `phieuxuat` VALUES ('PX23', '2022-12-17 00:18:54', 'Admin', 41460000);
INSERT INTO `phieuxuat` VALUES ('PX24', '2022-12-17 00:25:10', 'Admin', 63360000);
INSERT INTO `phieuxuat` VALUES ('PX25', '2022-12-17 01:51:31', 'Admin', 92550000);
INSERT INTO `phieuxuat` VALUES ('PX26', '2022-12-17 07:19:47', 'Admin', 94760000);
INSERT INTO `phieuxuat` VALUES ('PX3', '2022-12-04 23:45:52', 'admin', 89350000);
INSERT INTO `phieuxuat` VALUES ('PX4', '2022-12-04 23:45:59', 'admin', 58170000);
INSERT INTO `phieuxuat` VALUES ('PX5', '2022-12-07 02:10:13', 'admin', 85250000);
INSERT INTO `phieuxuat` VALUES ('PX6', '2022-12-07 02:19:12', 'admin', 71460000);
INSERT INTO `phieuxuat` VALUES ('PX7', '2022-12-07 02:25:43', 'admin', 52370000);
INSERT INTO `phieuxuat` VALUES ('PX8', '2022-12-08 01:39:54', 'admin', 39880000);
INSERT INTO `phieuxuat` VALUES ('PX9', '2022-12-08 01:40:22', 'admin', 36680000);

SET FOREIGN_KEY_CHECKS = 1;
