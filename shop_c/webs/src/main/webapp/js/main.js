function addToCart() {

	const selectedSize = document.querySelector('.sizebutton.selected'); // Lấy size đã chọn
	if (!selectedSize) {
		alert("Vui lòng chọn size trước khi thêm vào giỏ hàng!"); // Hiển thị cảnh báo nếu chưa chọn size
		return;
	}

	const priceElement = document.getElementById("price");
	const quantity = document.getElementById("quantityInput");
	fetch('cart/add.htm?pdid=' + selectedSize.id + '&price=' + priceElement.innerHTML + '&quantity=' + quantity.value, {
		method: 'GET',
		headers: {
			'Content-Type': 'application/json'
		}
	})
		.then(response => response.json())  // Nếu bạn trả về JSON từ server
		.then(data => {
			if (data.status === 'false') {
				console.log('Sản phẩm đã có trong giỏ hàng!!!', data);
				alert('Sản phẩm đã có trong giỏ hàng!!!');
			} else {
				console.log('Thêm sản phẩm thành công: ', data);
				alert('Sản phẩm đã thêm vào giỏ hàng!!!');
			}
			// Bạn có thể thực hiện các thao tác khác như hiển thị thông báo
		})
		.catch(error => console.error('Lỗi:', error));
}

let maxQuantity = 0;  // Biến lưu trữ số lượng tối đa

// Hàm chọn size và cập nhật giá trị số lượng tối đa
function selectSize(button, sizeId, price, quantity) {
	// Deselect tất cả các nút trước đó
	const buttons = document.querySelectorAll('.sizebutton');
	buttons.forEach(btn => {
		btn.classList.remove('selected');
		btn.textContent = btn.textContent.replace(" (Đang chọn)", "");  // Xóa chữ "Đang chọn"
	});

	// Chọn nút hiện tại
	button.classList.add('selected');
	button.textContent += " (Đang chọn)";  // Thêm chữ "Đang chọn" vào nút

	// Cập nhật giá cho size đã chọn
	const priceElement = document.getElementById("price");
	priceElement.textContent = price;  // Cập nhật giá hiển thị

	// Cập nhật số lượng tối đa dựa trên size được chọn
	maxQuantity = parseInt(quantity);  // Thiết lập maxQuantity theo số lượng của sản phẩm
	document.getElementById("quantityInput").value = 1;  // Reset lại số lượng về 1 khi chọn size mới
}

// Hàm thay đổi số lượng (tăng hoặc giảm)
function changeQuantity(action) {
	const selectedSize = document.querySelector('.sizebutton.selected'); // Lấy size đã chọn
	if (!selectedSize) {
		alert("Vui lòng chọn size trước khi thay đổi số lượng!"); // Hiển thị cảnh báo nếu chưa chọn size
		return;
	}

	const inputField = document.getElementById("quantityInput");
	let currentQuantity = parseInt(inputField.value);

	// Kiểm tra hành động (tăng hoặc giảm) và thay đổi giá trị
	if (action === 'plus') {
		if (currentQuantity < maxQuantity) {
			inputField.value = currentQuantity + 1;
		}
	} else if (action === 'minus') {
		if (currentQuantity > 1) {
			inputField.value = currentQuantity - 1;
		}
	}
}

// Kiểm tra tính hợp lệ của giá trị trong input (không cho số âm, chữ, và số 0)
document.getElementById("quantityInput").addEventListener("input", function(event) {
	let value = event.target.value;

	// Kiểm tra giá trị hợp lệ
	if (isNaN(value) || value <= 0) {
		event.target.value = 1;  // Nếu giá trị không hợp lệ, reset về 1
	} else if (parseInt(value) > maxQuantity) {
		event.target.value = maxQuantity;  // Giới hạn không vượt quá số lượng tối đa
	}
});
