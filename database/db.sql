-- MySQL dump 10.13  Distrib 8.0.36, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: quanlycuahang2
-- ------------------------------------------------------
-- Server version	8.0.36

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `chitietpn`
--
CREATE Database quanlycuahang2
use quanlycuahang2
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `chitietpn` (
  `MaPN` varchar(20) NOT NULL,
  `MaSP` varchar(20) NOT NULL,
  `TenSP` varchar(100) DEFAULT NULL,
  `SoLuong` int DEFAULT NULL,
  `DonGia` float DEFAULT NULL,
  `ThanhTien` float DEFAULT NULL,
  PRIMARY KEY (`MaPN`,`MaSP`),
  KEY `MaSP` (`MaSP`),
  CONSTRAINT `fk_chitietpn_phieunhap` FOREIGN KEY (`MaPN`) REFERENCES `phieunhap` (`MaPN`),
  CONSTRAINT `fk_chitietpn_sanpham` FOREIGN KEY (`MaSP`) REFERENCES `sanpham` (`MaSP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chitietpn`
--

LOCK TABLES `chitietpn` WRITE;
/*!40000 ALTER TABLE `chitietpn` DISABLE KEYS */;
INSERT INTO `chitietpn` VALUES ('PN001','CF001','Hạt cà phê Arabica',300,150000,45000000),('PN001','CF002','Hạt cà phê Robusta',200,120000,24000000),('PN002','CF003','Bột cà phê rang xay',200,180000,36000000),('PN003','CF004','Sữa đặc',2000,20000,40000000),('PN004','CF006','Hạt cà phê Culi',300,160000,48000000),('PN005','CF007','Siro caramel',200,80000,16000000),('PN006','CF005','Đường tinh luyện',1500,15000,22500000),('PN007','CF009','Bột ca cao',300,90000,27000000),('PN007','CF013','Kem tươi',100,60000,6000000),('PN008','CF010','Sữa tươi không đường',1200,25000,30000000),('PN008','CF012','Đường phèn',500,20000,10000000),('PN009','CF001','Hạt cà phê Arabica',400,150000,60000000),('PN009','CF002','Hạt cà phê Robusta',100,120000,12000000),('PN010','CF003','Bột cà phê rang xay',100,180000,18000000),('PN010','CF011','Hạt cà phê Moka',50,170000,8500000),('PN011','CF001','Hạt cà phê Arabica',12,150000,1800000),('PN012','CF002','Hạt cà phê Robusta',2,120000,240000),('PN013','CF001','Hạt cà phê Arabica',3,150000,450000),('PN014','CF002','Hạt cà phê Robusta',3,120000,360000),('PN015','CF002','Hạt cà phê Robusta',5,120000,600000);
/*!40000 ALTER TABLE `chitietpn` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `chitietpx`
--

DROP TABLE IF EXISTS `chitietpx`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `chitietpx` (
  `MaPX` varchar(20) NOT NULL,
  `MaSP` varchar(20) NOT NULL,
  `TenSP` varchar(100) DEFAULT NULL,
  `SoLuong` int DEFAULT NULL,
  `DonGia` decimal(15,2) NOT NULL,
  `ThanhTien` decimal(15,2) NOT NULL,
  PRIMARY KEY (`MaPX`,`MaSP`),
  KEY `MaSP` (`MaSP`),
  CONSTRAINT `fk_chitietpx_phieuxuat` FOREIGN KEY (`MaPX`) REFERENCES `phieuxuat` (`MaPX`),
  CONSTRAINT `fk_chitietpx_sanpham` FOREIGN KEY (`MaSP`) REFERENCES `sanpham` (`MaSP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chitietpx`
--

LOCK TABLES `chitietpx` WRITE;
/*!40000 ALTER TABLE `chitietpx` DISABLE KEYS */;
INSERT INTO `chitietpx` VALUES ('PX001','CF001','Hạt cà phê Arabica',2,200000.00,400000.00),('PX002','CF003','Bột cà phê rang xay',1,250000.00,250000.00),('PX003','CF002','Hạt cà phê Robusta',3,170000.00,510000.00),('PX003','CF007','Siro caramel',1,120000.00,120000.00),('PX004','CF010','Sữa tươi không đường',10,35000.00,350000.00),('PX005','CF004','Sữa đặc',8,30000.00,240000.00),('PX006','CF006','Hạt cà phê Culi',2,220000.00,440000.00),('PX006','CF013','Kem tươi',1,90000.00,90000.00),('PX007','CF008','Siro vani',1,125000.00,125000.00),('PX007','CF009','Bột ca cao',2,130000.00,260000.00),('PX008','CF005','Đường tinh luyện',9,20000.00,180000.00),('PX009','CF011','Hạt cà phê Moka',2,230000.00,460000.00),('PX010','CF012','Đường phèn',9,30000.00,270000.00),('PX011','CF001','Hạt cà phê Arabica',3,200000.00,600000.00);
/*!40000 ALTER TABLE `chitietpx` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `khachhang`
--

DROP TABLE IF EXISTS `khachhang`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `khachhang` (
  `MaKH` varchar(20) NOT NULL,
  `HoTen` varchar(50) DEFAULT NULL,
  `DiaChi` varchar(255) DEFAULT NULL,
  `NgayThamGia` date DEFAULT NULL,
  `Email` varchar(100) DEFAULT NULL,
  `SoDT` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`MaKH`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `khachhang`
--

LOCK TABLES `khachhang` WRITE;
/*!40000 ALTER TABLE `khachhang` DISABLE KEYS */;
INSERT INTO `khachhang` VALUES ('KH001','Nguyễn Thị D','12 Đường 3/2, Q10, TP.HCM','2025-01-01','ntd@gmail.com','0911222333'),('KH002','Trần Văn E','34 Đường Lý Thường Kiệt, Q.Tân Bình, TP.HCM','2025-02-01','tve@gmail.com','0988777666'),('KH003','Lê Thị F','56 Đường Cộng Hòa, Q.Tân Phú, TP.HCM','2025-03-01','ltf@gmail.com','0909123456'),('KH004','Phạm Văn G','78 Đường Lê Lợi, Q1, TP.HCM','2025-03-15','pvg@gmail.com','0933333444'),('KH005','Hoàng Thị H','90 Đường Nguyễn Trãi, Q5, TP.HCM','2025-04-01','hth@gmail.com','0944444555'),('KH006','Nguyễn Văn I','23 Đường Võ Thị Sáu, Q3, TP.HCM','2025-04-10','nvi@gmail.com','0955555666'),('KH007','Trần Thị K','45 Đường Cách Mạng Tháng 8, Q3, TP.HCM','2025-04-15','ttk@gmail.com','0966666777'),('KH008','Lê Văn L','67 Đường Trần Hưng Đạo, Q5, TP.HCM','2025-04-20','lvl@gmail.com','0977777888'),('KH009','Phạm Thị M','89 Đường Nguyễn Huệ, Q1, TP.HCM','2025-04-22','ptm@gmail.com','0988888999'),('KH010','Hoàng Văn N','12 Đường Cộng Hòa, Q.Tân Phú, TP.HCM','2025-04-23','hvn@gmail.com','0999999000');
/*!40000 ALTER TABLE `khachhang` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `nhacungcap`
--

DROP TABLE IF EXISTS `nhacungcap`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `nhacungcap` (
  `MaNCC` varchar(20) NOT NULL,
  `TenNCC` varchar(100) DEFAULT NULL,
  `SoDT` varchar(15) DEFAULT NULL,
  `Email` varchar(100) DEFAULT NULL,
  `DiaChi` varchar(255) DEFAULT NULL,
  `TinhTrang` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`MaNCC`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `nhacungcap`
--

LOCK TABLES `nhacungcap` WRITE;
/*!40000 ALTER TABLE `nhacungcap` DISABLE KEYS */;
INSERT INTO `nhacungcap` VALUES ('NCC001','Công ty Cà phê Đà Lạt','0987654321','dalatcoffee@gmail.com','123 Đường Trần Phú, Đà Lạt','Hoạt động'),('NCC002','Công ty Nguyên liệu Sài Gòn','0912345678','saigonnguyenlieu@gmail.com','456 Đường Lê Lợi, Q1, TP.HCM','Hoạt động'),('NCC003','Nhà cung cấp Sữa Vinamilk','0978123456','vinamilk@gmail.com','789 Đường Nguyễn Huệ, Q1, TP.HCM','Hoạt động'),('NCC004','Công ty Cà phê Buôn Ma Thuột','0935123456','buonmathuotcoffee@gmail.com','12 Đường Lê Duẩn, Buôn Ma Thuột','Hoạt động'),('NCC005','Nhà cung cấp Siro Monin','0909123456','moninvn@gmail.com','34 Đường Nguyễn Trãi, Q5, TP.HCM','Hoạt động'),('NCC006','Công ty Đường Việt Nam','0945678901','vietnamsugar@gmail.com','56 Đường Cách Mạng Tháng 8, Q3, TP.HCM','Hoạt động'),('NCC007','Nhà cung cấp Ca cao Đắk Lắk','0967890123','daklakcocoa@gmail.com','78 Đường Nguyễn Huệ, Đắk Lắk','Hoạt động'),('NCC008','Công ty Sữa tươi TH True Milk','0923456789','thtruemilk@gmail.com','90 Đường Võ Thị Sáu, Q3, TP.HCM','Hoạt động');
/*!40000 ALTER TABLE `nhacungcap` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `nhanvien`
--

DROP TABLE IF EXISTS `nhanvien`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `nhanvien` (
  `MaNV` varchar(20) NOT NULL,
  `HoTen` varchar(50) DEFAULT NULL,
  `GioiTinh` varchar(10) DEFAULT NULL,
  `NgaySinh` date DEFAULT NULL,
  `DiaChi` varchar(255) DEFAULT NULL,
  `Email` varchar(50) DEFAULT NULL,
  `SoDT` varchar(15) DEFAULT NULL,
  `ChucVu` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`MaNV`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `nhanvien`
--

LOCK TABLES `nhanvien` WRITE;
/*!40000 ALTER TABLE `nhanvien` DISABLE KEYS */;
INSERT INTO `nhanvien` VALUES ('NV001','Nguyễn Văn A','Nam','1990-05-15','78 Đường Lê Văn Sỹ, Q.Phú Nhuận, TP.HCM','nva@gmail.com','0911111222','Quản Lý'),('NV002','Trần Thị B','Nữ','1995-08-20','90 Đường Nguyễn Thị Minh Khai, Q3, TP.HCM','ttb@gmail.com','0988888999','Kế toán'),('NV003','Lê Văn C','Nam','1992-11-10','12 Đường Lê Quang Định, Q.Bình Thạnh, TP.HCM','lvc@gmail.com','0977777666','Nhân viên '),('NV004','Phạm Thị D','Nữ','1993-03-25','45 Đường Lý Thường Kiệt, Q.Tân Bình, TP.HCM','ptd@gmail.com','0933333444','Nhân viên '),('NV005','Hoàng Văn E','Nam','1991-07-12','67 Đường Cộng Hòa, Q.Tân Phú, TP.HCM','hve@gmail.com','0944444555','Nhân viên '),('NV006','Nguyễn Thị F','Nữ','1996-09-30','89 Đường Nguyễn Huệ, Q1, TP.HCM','ntf@gmail.com','0955555666','Nhân viên '),('NV007','Trần Văn G','Nam','1994-01-20','23 Đường Lê Lợi, Q1, TP.HCM','tvg@gmail.com','0966666777','Nhân viên ');
/*!40000 ALTER TABLE `nhanvien` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `phanquyen`
--

DROP TABLE IF EXISTS `phanquyen`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `phanquyen` (
  `MaQuyen` int NOT NULL,
  `NoiDung` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`MaQuyen`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `phanquyen`
--

LOCK TABLES `phanquyen` WRITE;
/*!40000 ALTER TABLE `phanquyen` DISABLE KEYS */;
INSERT INTO `phanquyen` VALUES (1,'Nhân viên'),(2,'Kế toán'),(3,'Quản lý');
/*!40000 ALTER TABLE `phanquyen` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `phieunhap`
--

DROP TABLE IF EXISTS `phieunhap`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `phieunhap` (
  `MaPN` varchar(20) NOT NULL,
  `MaNCC` varchar(50) DEFAULT NULL,
  `MaNV` varchar(20) DEFAULT NULL,
  `NgayNhap` datetime DEFAULT CURRENT_TIMESTAMP,
  `TongTien` float DEFAULT NULL,
  `TrangThai` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`MaPN`),
  KEY `MaNCC` (`MaNCC`),
  KEY `MaNV` (`MaNV`),
  CONSTRAINT `fk_phieunhap_nhacungcap` FOREIGN KEY (`MaNCC`) REFERENCES `nhacungcap` (`MaNCC`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `phieunhap`
--

LOCK TABLES `phieunhap` WRITE;
/*!40000 ALTER TABLE `phieunhap` DISABLE KEYS */;
INSERT INTO `phieunhap` VALUES ('PN001','NCC001','nv1','2025-04-01 09:00:00',75000000,'Hoàn thành'),('PN002','NCC002','nv1','2025-04-05 14:30:00',36000000,'Hoàn thành'),('PN003','NCC003','nv1','2025-04-10 10:15:00',40000000,'Chưa duyệt'),('PN004','NCC004','nv1','2025-04-12 11:00:00',48000000,'Hoàn thành'),('PN005','NCC005','nv1','2025-04-15 13:45:00',16000000,'Hoàn thành'),('PN006','NCC006','nv1','2025-04-18 08:30:00',22500000,'Chưa duyệt'),('PN007','NCC007','nv2','2025-04-20 15:00:00',27000000,'Hoàn thành'),('PN008','NCC008','nv2','2025-04-22 09:30:00',30000000,'Hoàn thành'),('PN009','NCC001','nv2','2025-04-23 10:00:00',60000000,'Chưa duyệt'),('PN010','NCC002','nv2','2025-04-23 14:00:00',18000000,'Hoàn thành'),('PN011','NCC001','1','2025-04-23 23:44:46',1800000,'Chưa duyệt'),('PN012','NCC003','1','2025-04-23 23:48:34',240000,'Chưa duyệt'),('PN013','NCC001','3','2025-04-24 00:12:49',450000,'Chưa duyệt'),('PN014','NCC001','3','2025-04-24 00:35:05',360000,'Chưa duyệt'),('PN015','NCC001','3','2025-04-24 07:59:33',600000,'Chưa duyệt');
/*!40000 ALTER TABLE `phieunhap` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `phieuxuat`
--

DROP TABLE IF EXISTS `phieuxuat`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `phieuxuat` (
  `MaPX` varchar(20) NOT NULL,
  `MaKH` varchar(255) DEFAULT NULL,
  `MaNV` varchar(255) DEFAULT NULL,
  `ThoiGian` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `TongTien` decimal(15,2) DEFAULT NULL,
  `TrangThai` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`MaPX`),
  KEY `MaKH` (`MaKH`),
  KEY `MaNV` (`MaNV`),
  CONSTRAINT `fk_phieuxuat_khachhang` FOREIGN KEY (`MaKH`) REFERENCES `khachhang` (`MaKH`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `phieuxuat`
--

LOCK TABLES `phieuxuat` WRITE;
/*!40000 ALTER TABLE `phieuxuat` DISABLE KEYS */;
INSERT INTO `phieuxuat` VALUES ('PX001','KH001','nv1','2025-04-01 08:00:00',400000.00,'Hoàn thành'),('PX002','KH002','nv1','2025-04-05 10:30:00',250000.00,'Chưa duyệt'),('PX003','KH003','nv1','2025-04-07 09:15:00',600000.00,'Hoàn thành'),('PX004','KH004','nv1','2025-04-10 11:00:00',350000.00,'Hoàn thành'),('PX005','KH005','nv1','2025-04-12 14:00:00',240000.00,'Chưa duyệt'),('PX006','KH006','nv2','2025-04-15 08:30:00',450000.00,'Hoàn thành'),('PX007','KH007','2','2025-04-18 10:00:00',300000.00,'Hoàn thành'),('PX008','KH008','1','2025-04-20 13:00:00',180000.00,'Chưa duyệt'),('PX009','KH009','nv2','2025-04-22 09:00:00',500000.00,'Hoàn thành'),('PX010','KH010','nv1','2025-04-23 11:30:00',270000.00,'Hoàn thành'),('PX011','KH001','3','2025-04-23 17:36:02',600000.00,'Chưa duyệt');
/*!40000 ALTER TABLE `phieuxuat` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sanpham`
--

DROP TABLE IF EXISTS `sanpham`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sanpham` (
  `MaSP` varchar(20) NOT NULL,
  `TenSP` varchar(100) DEFAULT NULL,
  `SoLuong` int DEFAULT NULL,
  `TinhTrang` varchar(50) DEFAULT NULL,
  `HanSD` date DEFAULT NULL,
  `GiaNhap` float DEFAULT NULL,
  `GiaXuat` float DEFAULT NULL,
  PRIMARY KEY (`MaSP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sanpham`
--

LOCK TABLES `sanpham` WRITE;
/*!40000 ALTER TABLE `sanpham` DISABLE KEYS */;
INSERT INTO `sanpham` VALUES ('CF001','Hạt cà phê Arabica',1000,'Còn hàng','2026-06-01',150000,200000),('CF002','Hạt cà phê Robusta',800,'Còn hàng','2026-05-15',120000,170000),('CF003','Bột cà phê rang xay',600,'Còn hàng','2025-12-31',180000,250000),('CF004','Sữa đặc',2000,'Còn hàng','2025-11-30',20000,30000),('CF005','Đường tinh luyện',1500,'Còn hàng','2026-07-31',15000,20000),('CF006','Hạt cà phê Culi',400,'Còn hàng','2026-04-30',160000,220000),('CF007','Siro caramel',500,'Còn hàng','2025-10-31',80000,120000),('CF008','Siro vani',250,'Còn hàng','2025-10-31',85000,125000),('CF009','Bột ca cao',500,'Còn hàng','2026-03-31',90000,130000),('CF010','Sữa tươi không đường',1200,'Còn hàng','2025-09-30',25000,35000),('CF011','Hạt cà phê Moka',350,'Sắp hết hàng','2026-02-28',170000,230000),('CF012','Đường phèn',700,'Còn hàng','2026-08-31',20000,30000),('CF013','Kem tươi',400,'Còn hàng','2025-08-31',60000,90000);
/*!40000 ALTER TABLE `sanpham` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `taikhoan`
--

DROP TABLE IF EXISTS `taikhoan`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `taikhoan` (
  `MaNV` varchar(20) NOT NULL,
  `MaQuyen` int DEFAULT NULL,
  `username` varchar(50) DEFAULT NULL,
  `pass` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`MaNV`),
  UNIQUE KEY `username` (`username`),
  KEY `MaQuyen` (`MaQuyen`),
  CONSTRAINT `fk_taikhoan_nhanvien` FOREIGN KEY (`MaNV`) REFERENCES `nhanvien` (`MaNV`),
  CONSTRAINT `fk_taikhoan_phanquyen` FOREIGN KEY (`MaQuyen`) REFERENCES `phanquyen` (`MaQuyen`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `taikhoan`
--

LOCK TABLES `taikhoan` WRITE;
/*!40000 ALTER TABLE `taikhoan` DISABLE KEYS */;
INSERT INTO `taikhoan` VALUES ('NV001',3,'3','3'),('NV002',1,'2','2'),('NV003',1,'1','1'),('NV004',1,'nv1','1'),('NV005',1,'nv2','1');
/*!40000 ALTER TABLE `taikhoan` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `trahang`
--

DROP TABLE IF EXISTS `trahang`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `trahang` (
  `MaTraHang` varchar(20) NOT NULL,
  `MaPX` varchar(20) NOT NULL,
  `MaNV` varchar(20) NOT NULL,
  `MaKH` varchar(20) NOT NULL,
  `NgayTra` date NOT NULL,
  `LyDoTra` varchar(255) DEFAULT NULL,
  `TongTienHoanTra` decimal(15,2) DEFAULT NULL,
  `TrangThai` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`MaTraHang`),
  KEY `MaPX` (`MaPX`),
  KEY `MaNV` (`MaNV`),
  KEY `MaKH` (`MaKH`),
  CONSTRAINT `fk_trahang_khachhang` FOREIGN KEY (`MaKH`) REFERENCES `khachhang` (`MaKH`),
  CONSTRAINT `fk_trahang_nhanvien` FOREIGN KEY (`MaNV`) REFERENCES `nhanvien` (`MaNV`),
  CONSTRAINT `fk_trahang_phieuxuat` FOREIGN KEY (`MaPX`) REFERENCES `phieuxuat` (`MaPX`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `trahang`
--

LOCK TABLES `trahang` WRITE;
/*!40000 ALTER TABLE `trahang` DISABLE KEYS */;
INSERT INTO `trahang` VALUES ('TH001','PX002','NV002','KH002','2025-04-06','Sản phẩm lỗi',250000.00,'Chưa duyệt'),('TH002','PX005','NV004','KH005','2025-04-13','Hết hạn sử dụng',240000.00,'Hoàn thành'),('TH003','PX008','NV002','KH008','2025-04-21','Sai sản phẩm',180000.00,'Chưa duyệt'),('TH004','PX003','NV004','KH003','2025-04-08','Sản phẩm hỏng',510000.00,'Hoàn thành'),('TH005','PX006','NV005','KH006','2025-04-16','Không đúng yêu cầu',440000.00,'Chưa duyệt');
/*!40000 ALTER TABLE `trahang` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-04-24 15:59:40
