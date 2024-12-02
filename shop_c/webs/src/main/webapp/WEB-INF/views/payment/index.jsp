<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Checkout Page</title>
<style>
body {
	font-family: Arial, sans-serif;
	margin: 0;
	padding: 0;
	background-color: #f4f4f4;
}

.container {
	max-width: 800px;
	margin: 50px auto;
	padding: 20px;
	background-color: white;
	box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}

h1 {
	text-align: center;
	color: #333;
}

.form-group {
	margin-bottom: 15px;
}

.form-group label {
	display: block;
	margin-bottom: 5px;
	color: #555;
}

.form-group input, .form-group select {
	width: 100%;
	padding: 10px;
	border: 1px solid #ddd;
	border-radius: 4px;
}

.form-group input[type="submit"] {
	background-color: #28a745;
	color: white;
	border: none;
	cursor: pointer;
}

.form-group input[type="submit"]:hover {
	background-color: #218838;
}

table {
	width: 100%;
	border-collapse: collapse;
	margin-top: 20px;
}

table, th, td {
	border: 1px solid #ddd;
}

th, td {
	padding: 10px;
	text-align: left;
}

th {
	background-color: #f8f8f8;
}
</style>
</head>
<body>
	<div class="container">
		<h1>Thanh toán</h1>
		<div>
			<table>
				<thead>
					<tr>
						<th>Tên sản phẩm</th>
						<th>Kích cỡ</th>
						<th>Số lượng</th>
						<th>Giá</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="selectProduct" items="${selectProducts}">
						<tr id="product${selectProduct.cartid}">
							<td>${selectProduct.name}</td>
							<td>${selectProduct.size}</td>
							<td>${selectProduct.quantity}</td>
							<td>${selectProduct.price * selectProduct.quantity}</td>
						</tr>
						<c:set var="totalAmount"
							value="${totalAmount + (selectProduct.price * selectProduct.quantity)}" />
					</c:forEach>
					<tr>
						<td></td>
						<td></td>
						<td>Tổng cộng:</td>
						<td>${totalAmount}</td>
					</tr>
					<!-- Add more products as needed -->
				</tbody>
			</table>
		</div>
		<br>
		<div>
			<form action="payment/vnpay.htm" method="GET">
				<div class="form-group">
					<label for="name">Tên</label> <input type="text" id="name"
						value="${customer.name}" name="name" required>
				</div>
				<div class="form-group">
					<label for="phone">Số điện thoại</label> <input type="tel"
						value="${customer.phone}" id="phone" name="phone" required>
				</div>
				<div class="form-group">
					<label for="address">Địa chỉ</label> <input type="text" value=""
						id="address" name="address" required>
				</div>
				<div class="form-group">
					<label for="address">Ghi chú</label> <input type="text"
						value="Hàng như con cặc!!!" id="note" name="note" required>
				</div>
				<div class="form-group">
					<label for="payment">Phương thức thanh toán</label> <select
						id="payment" name="payment" required>
						<c:forEach var="paymentMethod" items="${paymentMethods}">
							<option value="${paymentMethod.id}">${paymentMethod.name}</option>
						</c:forEach>
					</select>
				</div>
				<!-- Add hidden input to send totalAmount -->
				<input type="hidden" name="totalAmount" value="${totalAmount}">
				<div class="form-group">
					<input type="submit" value="Đặt hàng">
				</div>
			</form>
		</div>
	</div>
</body>
</html>