-- phpMyAdmin SQL Dump
-- version 4.9.0.1
-- https://www.phpmyadmin.net/
--
-- Máy chủ: 127.0.0.1
-- Thời gian đã tạo: Th5 31, 2020 lúc 02:01 AM
-- Phiên bản máy phục vụ: 10.3.16-MariaDB
-- Phiên bản PHP: 7.3.7

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Cơ sở dữ liệu: `fahasa`
--

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `chucvu`
--

CREATE TABLE `chucvu` (
  `VaiTro` int(11) NOT NULL,
  `ChucVu` varchar(1000) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Đang đổ dữ liệu cho bảng `chucvu`
--

INSERT INTO `chucvu` (`VaiTro`, `ChucVu`) VALUES
(1, 'Admin'),
(2, 'Kế toán'),
(3, 'Thu ngân'),
(4, 'Thủ kho');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `cthoadon`
--

CREATE TABLE `cthoadon` (
  `id` int(11) NOT NULL,
  `TenSP` varchar(1000) NOT NULL,
  `Gia` int(11) NOT NULL,
  `SL` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `hoadon`
--

CREATE TABLE `hoadon` (
  `MaHD` int(11) NOT NULL,
  `HoTenKH` varchar(1000) NOT NULL,
  `SDT` varchar(1000) NOT NULL,
  `NgayDat` varchar(1000) NOT NULL,
  `MaNV` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `loaisp`
--

CREATE TABLE `loaisp` (
  `MaLoai` int(11) NOT NULL,
  `TenLoai` varchar(1000) NOT NULL,
  `Hinh` varchar(1000) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Đang đổ dữ liệu cho bảng `loaisp`
--

INSERT INTO `loaisp` (`MaLoai`, `TenLoai`, `Hinh`) VALUES
(1, 'Sách', 'C:\\\\Users\\\\Administrator\\\\Documents\\\\NetBeansProjects\\\\Fahasa\\\\src\\\\main\\\\java\\\\Forms\\\\resources\\\\LoaiSP\\\\HinhSach.png'),
(2, 'Đồ chơi', 'C:\\\\Users\\\\Administrator\\\\Documents\\\\NetBeansProjects\\\\Fahasa\\\\src\\\\main\\\\java\\\\Forms\\\\resources\\\\LoaiSP\\\\HinhDoChoi.png'),
(3, 'Dụng cụ', 'C:\\\\Users\\\\Administrator\\\\Documents\\\\NetBeansProjects\\\\Fahasa\\\\src\\\\main\\\\java\\\\Forms\\\\resources\\\\LoaiSP\\\\HinhDungCu.png');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `nguoidung`
--

CREATE TABLE `nguoidung` (
  `MaNV` int(11) NOT NULL,
  `TenNV` varchar(1000) NOT NULL,
  `SDT` int(11) NOT NULL,
  `DiaChi` varchar(10000) NOT NULL,
  `VaiTro` varchar(1000) NOT NULL,
  `Email` varchar(256) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Đang đổ dữ liệu cho bảng `nguoidung`
--

INSERT INTO `nguoidung` (`MaNV`, `TenNV`, `SDT`, `DiaChi`, `VaiTro`, `Email`) VALUES
(1, 'admin', 123456789, '...', '1', 'gate.braille@gmail.com'),
(2, 'ketoan', 123456789, '...', '2', ''),
(3, 'thungan', 123456789, '...', '3', ''),
(4, 'thukho', 123456789, '...', '4', '');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `nhacungcap`
--

CREATE TABLE `nhacungcap` (
  `MaNhaCC` int(11) NOT NULL,
  `TenNhaCC` varchar(1000) NOT NULL,
  `Hinh` varchar(1000) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Đang đổ dữ liệu cho bảng `nhacungcap`
--

INSERT INTO `nhacungcap` (`MaNhaCC`, `TenNhaCC`, `Hinh`) VALUES
(1, 'Kim đồng', 'C:\\\\Users\\\\Administrator\\\\Documents\\\\NetBeansProjects\\\\Fahasa\\\\src\\\\main\\\\java\\\\Forms\\\\resources\\\\NhaCC\\\\HinhKimDong.png'),
(2, 'Tomy', 'C:\\\\Users\\\\Administrator\\\\Documents\\\\NetBeansProjects\\\\Fahasa\\\\src\\\\main\\\\java\\\\Forms\\\\resources\\\\NhaCC\\\\HinhTomy.png'),
(3, 'NXB Trẻ', 'C:\\\\Users\\\\Administrator\\\\Documents\\\\NetBeansProjects\\\\Fahasa\\\\src\\\\main\\\\java\\\\Forms\\\\resources\\\\NhaCC\\\\HinhNXBTre.png');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `sp`
--

CREATE TABLE `sp` (
  `MaSP` int(11) NOT NULL,
  `TenSP` varchar(1000) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `Gia` int(11) NOT NULL,
  `MoTa` varchar(1000) NOT NULL,
  `Hinh` varchar(1000) NOT NULL,
  `MaLoai` int(11) NOT NULL,
  `MaNhaCC` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Đang đổ dữ liệu cho bảng `sp`
--

INSERT INTO `sp` (`MaSP`, `TenSP`, `Gia`, `MoTa`, `Hinh`, `MaLoai`, `MaNhaCC`) VALUES
(1, 'Truyện Doremon', 10000, 'Truyện tranh thiếu nhi', 'C:\\\\Users\\\\Administrator\\\\Documents\\\\NetBeansProjects\\\\Fahasa\\\\src\\\\main\\\\java\\\\Forms\\\\resources\\\\SP\\\\TruyenDoremon.png', 1, 1),
(2, 'Rayquaza', 100000, 'Toy', 'C:\\\\Users\\\\Administrator\\\\Documents\\\\NetBeansProjects\\\\Fahasa\\\\src\\\\main\\\\java\\\\Forms\\\\resources\\\\SP\\\\Rayquaza.png', 2, 2),
(3, 'Bộ kỹ thuật lắp ráp', 67000, 'Môn công nghệ', 'C:\\\\Users\\\\Administrator\\\\Documents\\\\NetBeansProjects\\\\Fahasa\\\\src\\\\main\\\\java\\\\Forms\\\\resources\\\\SP\\\\BoKyThuat.png', 3, 3),
(4, 'Đất sét', 12000, 'Môn .....', 'C:\\\\Users\\\\Administrator\\\\Documents\\\\NetBeansProjects\\\\Fahasa\\\\src\\\\main\\\\java\\\\Forms\\\\resources\\\\SP\\\\DatSet.png', 2, 3),
(5, 'Naruto', 10000, 'Truyện', 'C:\\\\Users\\\\Administrator\\\\Documents\\\\NetBeansProjects\\\\Fahasa\\\\src\\\\main\\\\java\\\\Forms\\\\resources\\\\SP\\\\Naruto.png', 1, 1),
(6, 'dochoi', 123456, 'gbfgbf', 'gbgfbf', 3, 1),
(7, 'ga', 123, 'ga', 'ga', 2, 3),
(8, 'sdfdsvd', 123, 'fbfb', 'vbcbcvb', 1, 3),
(9, 'sdfdsvd', 123, 'fbfb', 'vbcbcvb', 1, 2),
(10, 'đồ chơi', 123456, 'mô tả', 'sdfsdf', 1, 1);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `taikhoan`
--

CREATE TABLE `taikhoan` (
  `TK` varchar(1000) NOT NULL,
  `MK` varchar(1000) NOT NULL,
  `MaNV` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Đang đổ dữ liệu cho bảng `taikhoan`
--

INSERT INTO `taikhoan` (`TK`, `MK`, `MaNV`) VALUES
('admin', 'zBZ64', 1),
('ketoan', 'ketoan', 2),
('thukho', 'thukho', 4),
('thungan', 'thungan', 3);

--
-- Chỉ mục cho các bảng đã đổ
--

--
-- Chỉ mục cho bảng `chucvu`
--
ALTER TABLE `chucvu`
  ADD PRIMARY KEY (`VaiTro`);

--
-- Chỉ mục cho bảng `cthoadon`
--
ALTER TABLE `cthoadon`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `hoadon`
--
ALTER TABLE `hoadon`
  ADD PRIMARY KEY (`MaHD`);

--
-- Chỉ mục cho bảng `loaisp`
--
ALTER TABLE `loaisp`
  ADD PRIMARY KEY (`MaLoai`);

--
-- Chỉ mục cho bảng `nguoidung`
--
ALTER TABLE `nguoidung`
  ADD PRIMARY KEY (`MaNV`);

--
-- Chỉ mục cho bảng `nhacungcap`
--
ALTER TABLE `nhacungcap`
  ADD PRIMARY KEY (`MaNhaCC`);

--
-- Chỉ mục cho bảng `sp`
--
ALTER TABLE `sp`
  ADD PRIMARY KEY (`MaSP`);

--
-- Chỉ mục cho bảng `taikhoan`
--
ALTER TABLE `taikhoan`
  ADD PRIMARY KEY (`TK`);

--
-- AUTO_INCREMENT cho các bảng đã đổ
--

--
-- AUTO_INCREMENT cho bảng `chucvu`
--
ALTER TABLE `chucvu`
  MODIFY `VaiTro` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT cho bảng `cthoadon`
--
ALTER TABLE `cthoadon`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `hoadon`
--
ALTER TABLE `hoadon`
  MODIFY `MaHD` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `loaisp`
--
ALTER TABLE `loaisp`
  MODIFY `MaLoai` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT cho bảng `nguoidung`
--
ALTER TABLE `nguoidung`
  MODIFY `MaNV` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT cho bảng `nhacungcap`
--
ALTER TABLE `nhacungcap`
  MODIFY `MaNhaCC` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT cho bảng `sp`
--
ALTER TABLE `sp`
  MODIFY `MaSP` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
