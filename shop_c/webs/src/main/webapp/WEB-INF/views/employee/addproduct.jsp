<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Thêm sản phẩm</title>
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
        padding: 80px 20px;
        max-width: 600px;
        margin: auto;
    }
    form {
        background-color: #f9f9f9;
        padding: 20px;
        border-radius: 8px;
        box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
    }
    form label {
        font-weight: bold;
        display: block;
        margin: 10px 0 5px;
    }
    form input, form textarea, form select, form button {
        width: 100%;
        padding: 10px;
        margin-bottom: 15px;
        border: 1px solid #ccc;
        border-radius: 5px;
        font-size: 16px;
    }
    form textarea {
        resize: vertical;
        height: 100px;
    }
    form button {
        background-color: #007BFF;
        color: white;
        border: none;
        cursor: pointer;
        font-size: 16px;
        font-weight: bold;
    }
    form button:hover {
        background-color: #0056b3;
    }
</style>
</head>
<body>
    <!-- Navbar -->
    <div class="navbar">
        <a href="/shop_c/home.htm">Trang chủ</a>
        <a href="/shop_c/employee/addproduct.htm">Thêm sản phẩm</a>
        <a href="/employee/manageProducts">Quản lý sản phẩm</a>
        <a href="/logout">Đăng xuất</a>
    </div>

    <!-- Content -->
    <div class="content">
        <h1>Thêm sản phẩm mới</h1>
<form action="/shop_c/employee/addproduct.htm" method="POST" enctype="multipart/form-data">
            <label for="name">Tên sản phẩm:</label>
            <input type="text" id="name" name="name" placeholder="Nhập tên sản phẩm" required>

            <label for="image">Hình ảnh:</label>
            <input type="file" id="image" name="image" accept="image/*" required>

            <label for="description">Mô tả sản phẩm:</label>
            <textarea id="description" name="description" placeholder="Nhập mô tả sản phẩm" required></textarea>

            <label for="typesID">Loại sản phẩm:</label>
            <select id="typesID" name="typesID" required>
                <option value="" disabled selected>Chọn loại sản phẩm</option>
                <c:forEach var="type" items="${types}">
                    <option value="${type.id}">${type.name}</option>
                    
                    123123
                </c:forEach>
            </select>

            <label for="originsID">Xuất xứ:</label>
            <select id="originsID" name="originsID" required>
                <option value="" disabled selected>Chọn xuất xứ</option>
                <c:forEach var="origin" items="${origins}">
                    <option value="${origin.id}">${origin.name}</option>
                </c:forEach>
            </select>

            <label for="brandsID">Thương hiệu:</label>
            <select id="brandsID" name="brandsID" required>
                <option value="" disabled selected>Chọn thương hiệu</option>
                <c:forEach var="brand" items="${brands}">
                    <option value="${brand.id}">${brand.name}</option>
                </c:forEach>
            </select>

            <label for="materialsID">Chất liệu:</label>
            <select id="materialsID" name="materialsID" required>
                <option value="" disabled selected>Chọn chất liệu</option>
                <c:forEach var="material" items="${materials}">
                    <option value="${material.id}">${material.name}</option>
                </c:forEach>
            </select>

            <button type="submit">Thêm sản phẩm</button>
        </form>
    </div>
</body>
</html>
