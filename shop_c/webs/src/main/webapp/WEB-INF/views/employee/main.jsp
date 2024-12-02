<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Employee Dashboard</title>
<style>
    body {
        font-family: Arial, sans-serif;
        margin: 0;
        padding: 0;
    }
    .navbar {
        background-color: #333;
        overflow: hidden;
        position: fixed;
        top: 0;
        width: 100%;
        z-index: 1000;
    }
    .navbar a {
        float: left;
        display: block;
        color: white;
        text-align: center;
        padding: 14px 20px;
        text-decoration: none;
    }
    .navbar a:hover {
        background-color: #ddd;
        color: black;
    }
    .content {
        padding: 60px 20px; /* Offset cho Navbar cố định */
    }
    .card {
        background-color: #f9f9f9;
        margin: 20px;
        padding: 20px;
        box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
        border-radius: 8px;
        text-align: center;
    }
    .card a {
        text-decoration: none;
        color: #007BFF;
        font-weight: bold;
    }
    .card a:hover {
        color: #0056b3;
    }
</style>
</head>
<body>
    <!-- Navbar -->
    <div class="navbar">
        <a href="/shop_c/home.htm">Trang chủ</a>
        <a href="/employee/addproduct">Thêm sản phẩm</a>
        <a href="/employee/manageProducts">Quản lý sản phẩm</a>
        <a href="/logout">Đăng xuất</a>
    </div>

    <!-- Main Content -->
    <div class="content">
        <h1>Chào mừng đến trang nhân viên</h1>
        <p>Hãy chọn một chức năng để thao tác:</p>
	
        <div class="card">
            <h2>Thêm sản phẩm</h2>
            <p>Thêm các sản phẩm mới vào cửa hàng.</p>
            <a href="/shop_c/employee/addproduct.htm">Thực hiện ngay</a>
        </div>

        <div class="card">
            <h2>Quản lý sản phẩm</h2>
            <p>Kiểm tra, chỉnh sửa hoặc xóa sản phẩm hiện có.</p>
            <a href="/employee/manageProducts">Thực hiện ngay</a>
        </div>
    </div>
</body>
</html>
