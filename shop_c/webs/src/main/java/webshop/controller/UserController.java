package webshop.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import webshop.dao.AccountDAO;
import webshop.dao.CartDAO;
import webshop.dao.CustomerDAO;
import webshop.dao.OrderDAO;
import webshop.dao.OrderDetailDAO;
import webshop.dao.OrderStatusDAO;
import webshop.dao.ProductDAO;
import webshop.dao.ProductDetailDAO;
import webshop.entity.Account;
import webshop.entity.Cart;
import webshop.entity.Customer;
import webshop.entity.Order;
import webshop.entity.OrderDetail;
import webshop.entity.Product;
import webshop.entity.ProductDetail;

@Controller
public class UserController {

	@Autowired
	AccountDAO accd;
	@Autowired
	CustomerDAO cusd;
	@Autowired
	ProductDAO product;
	@Autowired
	ProductDetailDAO prdd;
	@Autowired
	CartDAO cartd;
	@Autowired
	OrderDAO ordd;
	@Autowired
	OrderDetailDAO ordetail;
	@Autowired
	OrderStatusDAO osd;

	@RequestMapping("home")
	public String home(ModelMap model) {

		List<Product> dsProduct = product.getAllProducts();

		List<ProductDetail> dsDetail = prdd.getAllProductDetails();

		List<Object[]> productInfoList = new ArrayList<Object[]>();

		for (Product p : dsProduct) {
			Double minPrice = dsDetail.stream().filter(detail -> detail.getProduct().getId() == p.getId())
					.mapToDouble(ProductDetail::getPrice).min() // Lấy giá nhỏ nhất
					.orElse(0); // Nếu không tìm thấy, trả về NaN

			productInfoList.add(new Object[] { p.getId(), p.getName(), minPrice, p.getImage() });
		}

		model.addAttribute("productInfo", productInfoList);

		return "user/home";
	}

	@RequestMapping("search")
	public String search(@RequestParam(value = "search", required = false) String search, ModelMap model) {

		List<Product> dsProduct = product.getAllProducts(); // Lấy tất cả sản phẩm
		List<ProductDetail> dsDetail = prdd.getAllProductDetails(); // Lấy chi tiết sản phẩm

		// Lọc sản phẩm dựa trên chuỗi search nếu được cung cấp
		if (search != null && !search.isEmpty()) {
			dsProduct = dsProduct.stream().filter(p -> p.getName().toLowerCase().contains(search.toLowerCase())) // Lọc
																													// sản
																													// phẩm
																													// có
																													// tên
																													// chứa
																													// chuỗi
																													// search
					.collect(Collectors.toList());
		}

		List<Object[]> productInfoList = new ArrayList<>();

		for (Product p : dsProduct) {
			// Tính giá thấp nhất cho sản phẩm
			Double minPrice = dsDetail.stream().filter(detail -> detail.getProduct().getId() == p.getId())
					.mapToDouble(ProductDetail::getPrice).min().orElse(0.0); // Giá mặc định nếu không tìm thấy

			// Thêm thông tin vào danh sách
			productInfoList.add(new Object[] { p.getId(), p.getName(), minPrice, p.getImage() });
		}

		// Gắn danh sách vào Model
		model.addAttribute("productInfo", productInfoList);

		return "user/home";
	}

	@RequestMapping("cart")
	public String cart(HttpSession ses, ModelMap model) {

		String email = (String) ses.getAttribute("user");

		Account acc = accd.getAccountByEmail(email);

		Customer cus = cusd.getCustomerById(acc.getId());

		List<Product> dsProduct = product.getAllProducts();

		List<ProductDetail> dsDetail = prdd.getAllProductDetails();

		List<Cart> dsCart = cartd.getAllCarts();

		int givenCustomerId = cus.getId(); // Thay bằng ID của khách hàng mà bạn muốn lọc

		List<Map<String, Object>> cartDetails = new ArrayList<>();

		for (Cart cart : dsCart) {
			if (cart.getStatus() != 1) {
				// Kiểm tra điều kiện customersID
				if (cart.getCustomer().getId() == givenCustomerId) {
					// Tìm ProductDetail tương ứng với product_detailsID trong Cart
					ProductDetail productDetail = dsDetail.stream()
							.filter(detail -> detail.getId() == cart.getProductDetail().getId()) // so sánh ID
							.findFirst().orElse(null);

					if (productDetail != null) {
						// Tìm Product tương ứng với productID trong ProductDetail
						Product product = dsProduct.stream()
								.filter(p -> p.getId() == productDetail.getProduct().getId()).findFirst().orElse(null);

						if (product != null) {
							// Tính tổng tiền
							int quantity = cart.getQuantity();
							int price = productDetail.getPrice();
							int total = price * quantity;

							// Lưu thông tin vào danh sách
							Map<String, Object> detail = new HashMap<>();
							detail.put("cartid", cart.getID());
							detail.put("status", cart.getStatus());
							detail.put("image", product.getImage());
							detail.put("name", product.getName());
							detail.put("size", productDetail.getSize().getId());
							detail.put("price", price);
							detail.put("quantity", quantity);
							detail.put("maxQuantity", productDetail.getQuantity());
							detail.put("total", total);

							cartDetails.add(detail);
						}
					}
				}
			}
		}

		// Đưa cartDetails vào model để hiển thị trên giao diện
		model.addAttribute("cartDetails", cartDetails);
		return "user/cart";
	}

	@RequestMapping("order")
	public String order(HttpSession ses, ModelMap model) {

		if (ses.getAttribute("user") == null) {
			return "redirect:/login.htm";
		}

		String email = (String) ses.getAttribute("user");

		Account acc = accd.getAccountByEmail(email);

		Customer cus = cusd.getCustomerById(acc.getId());

		List<Product> dsProduct = product.getAllProducts();

		List<ProductDetail> dsDetail = prdd.getAllProductDetails();

		List<Order> dsOrder = ordd.getAllOrders();

		List<OrderDetail> dsOrdDetail = ordetail.getAllOrderDetails();

		int givenCustomerId = cus.getId(); // Thay bằng ID của khách hàng mà bạn muốn lọc

		List<Map<String, Object>> orderDetails = new ArrayList<>();

		for (OrderDetail orderDetail : dsOrdDetail) {
			// Tìm Order tương ứng với ordersID trong OrderDetail
			Order order = dsOrder.stream().filter(o -> o.getId() == orderDetail.getOrder().getId()) // So sánh ID
					.findFirst().orElse(null);

			if (order != null && order.getCustomer().getId() == givenCustomerId) { // Kiểm tra điều kiện customersID
				// Tìm ProductDetail tương ứng với product_detailsID trong OrderDetail
				ProductDetail productDetail = dsDetail.stream()
						.filter(detail -> detail.getId() == orderDetail.getProductDetail().getId()) // So sánh ID
						.findFirst().orElse(null);

				if (productDetail != null) {
					// Tìm Product tương ứng với productsID trong ProductDetail
					Product product = dsProduct.stream().filter(p -> p.getId() == productDetail.getProduct().getId()) // So
																														// sánh
																														// ID
							.findFirst().orElse(null);

					if (product != null) {
						// Tính tổng tiền
						int quantity = orderDetail.getQuantity();
						int price = productDetail.getPrice();
						double shipFee = order.getShipFee();
						double total = price * quantity + shipFee;

						// Lưu thông tin vào danh sách
						Map<String, Object> detail = new HashMap<>();
						detail.put("image", product.getImage()); // Hình ảnh sản phẩm
						detail.put("name", product.getName()); // Tên sản phẩm
						detail.put("size", productDetail.getSize().getId()); // Size
						detail.put("price", price); // Giá
						detail.put("quantity", quantity); // Số lượng
						detail.put("total", total); // Tổng tiền
						detail.put("status", order.getOrderStatus().getStatusName()); // Trạng thái đơn hàng
						detail.put("id", order.getId()); // ID của đơn hàng

						orderDetails.add(detail);
					}
				}
			}
		}

		// Đưa orderDetails vào model để hiển thị trên giao diện
		model.addAttribute("orderDetails", orderDetails);

		return "user/order";
	}

	@RequestMapping("login")
	public String login(HttpSession ses) {

		if (ses.getAttribute("user") != null) {
			return "redirect:/home.htm";
		}
		return "login/login";
	}

	@RequestMapping("error")
	public String error() {
		return "error";
	}

	@RequestMapping("productinfo")
	public String productinfo(HttpServletRequest request) {

		if (request.getParameter("proid") == null) {
			return "redirect:/home.htm";
		}
		int proid = Integer.parseInt(request.getParameter("proid"));

		Product prod = product.getProductById(proid);

		List<ProductDetail> dsDe = prdd.getAllProductDetails();

		List<ProductDetail> toRemove = new ArrayList<>();
		for (ProductDetail d : dsDe) {
			if (d.getProduct().getId() == proid) {
				toRemove.add(d);
			}
		}
		request.setAttribute("productDetail", toRemove);
		request.setAttribute("product", prod);

		return "user/product";
	}

	@RequestMapping("personal")
	public String personal(HttpServletRequest request, HttpSession session) {

		if (session.getAttribute("user") == null) {
			return "redirect:/login.htm";
		}

		String email = (String) session.getAttribute("user");

		Account acc = accd.getAccountByEmail(email);

		Customer cs = null;

		if (acc != null) {
			cs = cusd.getCustomerById(acc.getId());
		}

		if (cs != null) {
			request.setAttribute("acc", acc);
			request.setAttribute("personal", cs);
		}

		return "user/personal";
	}

	@RequestMapping("resetpass")
	public String resetpass(HttpServletRequest request, HttpSession ses) {
		if (ses.getAttribute("user") == null) {
			return "redirect:/login.htm";
		}

		// tiếp tục đổi mk gì đó
		return "login/resetpass";
	}

}
