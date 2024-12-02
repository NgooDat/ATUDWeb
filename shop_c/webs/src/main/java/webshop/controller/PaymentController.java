package webshop.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TimeZone;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.net.URLEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.gson.JsonObject;

import webshop.dao.AccountDAO;
import webshop.dao.CartDAO;
import webshop.dao.CustomerDAO;
import webshop.dao.OrderDAO;
import webshop.dao.OrderDetailDAO;
import webshop.dao.OrderStatusDAO;
import webshop.dao.PaymentMethodDAO;
import webshop.dao.ProductDAO;
import webshop.dao.ProductDetailDAO;
import webshop.entity.Account;
import webshop.entity.Cart;
import webshop.entity.Customer;
import webshop.entity.Order;
import webshop.entity.OrderDetail;
import webshop.entity.OrderDetailId;
import webshop.entity.OrderStatus;
import webshop.entity.Product;
import webshop.entity.ProductDetail;
import webshop.paymentMethod.Config;
import webshop.paymentMethod.PaymentService;

@Controller
@RequestMapping(value = "payment")
public class PaymentController {
	@Autowired
	AccountDAO accountDAO;
	@Autowired
	CustomerDAO customerDAO;
	@Autowired
	ProductDAO productDAO;
	@Autowired
	ProductDetailDAO productDetailDAO;
	@Autowired
	CartDAO cartDAO;
	@Autowired
	PaymentMethodDAO paymentMethodDAO;
	@Autowired
	OrderDAO orderDAO;
	@Autowired
	OrderDetailDAO orderDetailDAO;
	@Autowired
	OrderStatusDAO orderStatusDAO;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public String perform(HttpSession session, ModelMap model) {
		String email = (String) session.getAttribute("user");
		Account account = accountDAO.getAccountByEmail(email);
		Customer customer = customerDAO.getCustomerById(account.getId());

		// Lấy danh sách idCart từ session
		@SuppressWarnings("unchecked")
		List<Integer> selectedCartIds = (List<Integer>) session.getAttribute("selectedCartIds");
		List<Map<String, Object>> selectProducts = new ArrayList<>();
		List<Product> dsProduct = productDAO.getAllProducts();
		List<ProductDetail> dsDetail = productDetailDAO.getAllProductDetails();
		int givenCustomerId = customer.getId();

		for (int idCart : selectedCartIds) {
			Cart cart = cartDAO.getCartById(idCart); // Lấy Cart từ DAO
			if (cart.getCustomer().getId() == givenCustomerId) {
				// Tìm ProductDetail tương ứng với product_detailsID trong Cart
				ProductDetail productDetail = dsDetail.stream()
						.filter(detail -> detail.getId() == cart.getProductDetail().getId()) // so sánh ID
						.findFirst().orElse(null);

				if (productDetail != null) {
					// Tìm Product tương ứng với productID trong ProductDetail
					Product product = dsProduct.stream().filter(p -> p.getId() == productDetail.getProduct().getId())
							.findFirst().orElse(null);

					if (product != null) {
						// Tính tổng tiền
						int quantity = cart.getQuantity();
						int price = productDetail.getPrice();
						int total = price * quantity;

						// Lưu thông tin vào danh sách
						Map<String, Object> selectProduct = new HashMap<>();
						selectProduct.put("cartid", cart.getID());
						selectProduct.put("status", cart.getStatus());
						selectProduct.put("image", product.getImage());
						selectProduct.put("name", product.getName());
						selectProduct.put("size", productDetail.getSize().getId());
						selectProduct.put("price", price);
						selectProduct.put("quantity", quantity);
						selectProduct.put("maxQuantity", productDetail.getQuantity());
						selectProduct.put("total", total);

						selectProducts.add(selectProduct);
					}
				}
			}
		}
		model.addAttribute("customer", customer);
		model.addAttribute("paymentMethods", paymentMethodDAO.getAllPaymentMethods());
		model.addAttribute("selectProducts", selectProducts);
		return "payment/index"; // Trả về view
	}

	@Autowired
	private PaymentService paymentService;
	@Autowired
	private Config config;

	@RequestMapping(value = "/vnpay", method = RequestMethod.GET)
	public String vnpay(@RequestParam("totalAmount") long totalAmount, @RequestParam("address") String address,
			@RequestParam("note") String note, HttpSession session, HttpServletRequest request, Model model) {
		String language = "vn";
		Map<String, Object> OrderInfo = new HashMap<String, Object>();
		OrderInfo.put("totalAmount", totalAmount);
		OrderInfo.put("address", address);
		OrderInfo.put("note", note);
		session.setAttribute("OrderInfo", OrderInfo);
		try {
			// Gọi service tạo URL thanh toán
			JsonObject paymentResponse = paymentService.createPayment(request, totalAmount, "", language);

			// Kiểm tra kết quả trả về
			if (paymentResponse != null && "00".equals(paymentResponse.get("code").getAsString())) {
				// Lấy URL thanh toán
				String paymentUrl = paymentResponse.get("data").getAsString();

				// Chuyển hướng người dùng tới trang thanh toán
				return "redirect:" + paymentUrl;
			} else {
				// Xử lý lỗi nếu có
				model.addAttribute("error", "Không thể tạo thanh toán. Vui lòng thử lại!");
				return "error"; // Trang hiển thị lỗi
			}
		} catch (Exception e) {
			// Log lỗi và hiển thị thông báo lỗi
			e.printStackTrace();
			model.addAttribute("error", "Có lỗi xảy ra: " + e.getMessage());
			return "error"; // Trang hiển thị lỗi
		}
	}

	@RequestMapping(value = "/result", method = RequestMethod.GET)
	public String vnpay(@RequestParam("vnp_Amount") double vnp_Amount,
			@RequestParam("vnp_TransactionStatus") String vnp_TransactionStatus, HttpServletRequest request,
			Model model, HttpSession session) {
		String email = (String) session.getAttribute("user");
		Account account = accountDAO.getAccountByEmail(email);
		Customer customer = customerDAO.getCustomerById(account.getId());
		// Khởi tạo Map để lưu các tham số
		Map<String, String> fields = new HashMap<>();

		try {
			// Lấy tất cả các tham số từ request và mã hóa chúng
			for (Enumeration<?> params = request.getParameterNames(); params.hasMoreElements();) {
				String fieldName = URLEncoder.encode((String) params.nextElement(),
						StandardCharsets.US_ASCII.toString());
				String fieldValue = URLEncoder.encode(request.getParameter(fieldName),
						StandardCharsets.US_ASCII.toString());
				if ((fieldValue != null) && (fieldValue.length() > 0)) {
					fields.put(fieldName, fieldValue);
				}
			}
		} catch (UnsupportedEncodingException e) {
			// Xử lý ngoại lệ nếu có
			model.addAttribute("message", "Lỗi mã hóa: " + e.getMessage());
			return "payment/result"; // Trả về trang lỗi nếu có lỗi mã hóa
		}

		// Lấy giá trị vnp_SecureHash từ request
		String vnp_SecureHash = request.getParameter("vnp_SecureHash");

		// Xóa các tham số không cần thiết khỏi Map
		fields.remove("vnp_SecureHashType");
		fields.remove("vnp_SecureHash");

		// Tạo chữ ký từ các tham số
		String signValue = Config.hashAllFields(fields);

		// So sánh chữ ký đã tạo với vnp_SecureHash nhận được
		if (signValue.equals(vnp_SecureHash)) {
			// Kiểm tra trạng thái giao dịch
			if ("00".equals(vnp_TransactionStatus)) {
				// Prepare current date
				Date currentDate = new Date();

				// Get selected cart IDs
				List<Integer> selectedCartIds = (List<Integer>) session.getAttribute("selectedCartIds");

				// Validate cart selection
				if (selectedCartIds.isEmpty()) {
					throw new IllegalArgumentException("No cart items selected");
				}

				// Prepare order details
				Set<OrderDetail> orderDetails = new HashSet<>();
				double totalProductFee = 0.0;

				for (int idCart : selectedCartIds) {
					// Retrieve cart and validate
					Cart cart = cartDAO.getCartById(idCart);
					if (cart == null || cart.getProductDetail() == null) {
						throw new IllegalStateException("Invalid cart or product detail");
					}

					// Create order detail
					OrderDetail orderDetail = new OrderDetail();
					ProductDetail productDetail = cart.getProductDetail();

					// Set order detail properties
					OrderDetailId orderDetailId = new OrderDetailId();
					orderDetailId.setOrdersID(0); // Will be set after order is saved
					orderDetailId.setProduct_detailsID(productDetail.getId());
					orderDetail.setId(orderDetailId);

					orderDetail.setQuantity(cart.getQuantity());
					orderDetail.setUnitPrice(cart.getTotalPrice() / cart.getQuantity());
					orderDetail.setProductDetail(productDetail);

					// Update cart status
					cart.setStatus(1);
					cartDAO.updateCart(cart);

					// Accumulate order details
					orderDetails.add(orderDetail);
					totalProductFee += cart.getTotalPrice();
				}

				// Retrieve order information from session
				Map<String, Object> orderInfo = (Map<String, Object>) session.getAttribute("OrderInfo");
				if (orderInfo == null) {
					throw new IllegalStateException("Order information not found in session");
				}

				// Normalize payment amount
				vnp_Amount /= 100;

				// Create order
				Order order = new Order();
				order.setAddress((String) orderInfo.get("address"));
				order.setCreateTime(currentDate);
				order.setUpdateTime(currentDate);
				order.setPaymentStatus(1);
				order.setCustomer(customer);
				order.setDescription((String) orderInfo.get("note"));
				order.setOrderStatus(orderStatusDAO.getOrderStatusById(1));
				order.setPaymentMethod(paymentMethodDAO.getPaymentMethodById(1));
				order.setProductFee(vnp_Amount);
				order.setShipFee(16000);
				order.setTotal(vnp_Amount + 16000);

				// Save order first to generate ID
				orderDAO.addOrder(order);

				// Update order details with actual order ID
				for (OrderDetail detail : orderDetails) {
					detail.getId().setOrdersID(order.getId());
					orderDetailDAO.addOrderDetail(detail);
				}

				// Set order details after saving individual details
				order.setOrderDetails(orderDetails);
				orderDAO.updateOrder(order);

				// Clean up session
				session.removeAttribute("selectedCartIds");
				session.removeAttribute("OrderInfo");
				// Thêm thông báo thành công vào model
				model.addAttribute("message", "Thanh toán thành công!");
			} else {
				// Giao dịch không thành công
				model.addAttribute("message", "Thanh toán không thành công!");
			}
		} else {
			// Nếu chữ ký không khớp
			model.addAttribute("message", "Lỗi xác thực chữ ký!");
		}

		return "payment/result"; // Trả về trang kết quả
	}
}