package webshop.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import webshop.dao.AccountDAO;
import webshop.dao.CartDAO;
import webshop.dao.CustomerDAO;
import webshop.dao.ProductDAO;
import webshop.dao.ProductDetailDAO;
import webshop.entity.Account;
import webshop.entity.Cart;
import webshop.entity.Customer;
import webshop.entity.Product;
import webshop.entity.ProductDetail;

@Controller
@RequestMapping(value = "cart")
public class CartController {
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

	@RequestMapping("")
	public String cart(HttpSession session, ModelMap model) {
		
		

		List<Cart> dsCart;
		int givenCustomerId;
		String email = (String) session.getAttribute("user");

		if (email != null) {
			Account account = accountDAO.getAccountByEmail(email);
			Customer customer = customerDAO.getCustomerByAccountID(account.getId());
			givenCustomerId = customer.getId();
			dsCart = cartDAO.getAllCarts();
		} else {
			givenCustomerId = 0;
			dsCart = (List<Cart>) session.getAttribute("carts");
		}

		List<ProductDetail> dsDetail = productDetailDAO.getAllProductDetails();
		List<Product> dsProduct = productDAO.getAllProducts();

		// Thay bằng ID của khách hàng mà bạn muốn lọc

		List<Map<String, Object>> cartDetails = new ArrayList<>();

		if (dsCart != null) {
			for (Cart cart : dsCart) {
				if (cart.getStatus() == 0) {
					// Kiểm tra điều kiện customersID
					if (cart.getCustomer().getId() == givenCustomerId) {
						// Tìm ProductDetail tương ứng với product_detailsID trong Cart
						ProductDetail productDetail = dsDetail.stream()
								.filter(detail -> detail.getId() == cart.getProductDetail().getId()) // so sánh ID
								.findFirst().orElse(null);

						if (productDetail != null) {
							// Tìm Product tương ứng với productID trong ProductDetail
							Product product = dsProduct.stream()
									.filter(p -> p.getId() == productDetail.getProduct().getId()).findFirst()
									.orElse(null);

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
								detail.put("productId", productDetail.getProduct().getId());
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
		}

		// Đưa cartDetails vào model để hiển thị trên giao diện
		model.addAttribute("cartDetails", cartDetails);
		return "user/cart";
	}

	@ResponseBody // Để trả về dữ liệu trực tiếp (JSON hoặc string)
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String insert(@RequestParam("price") int price, @RequestParam("pdid") int pdid,
			@RequestParam("quantity") int quantity, HttpSession session) {

		ProductDetail productDetail = productDetailDAO.getProductDetailById(pdid);
		if (productDetail == null) {
			return "{\"message\": \"Không tồn tại sản phẩm này!\", \"status\": \"false\"}";
		}

		String email = (String) session.getAttribute("user");
		List<Cart> carts;

		if (email != null) {
			Account account = accountDAO.getAccountByEmail(email);
			Customer customer = customerDAO.getCustomerByAccountID(account.getId());
			productDetail = productDetailDAO.getProductDetailById(pdid);
			carts = cartDAO.getCartsByCustomerId(customer.getId());

			for (Cart cart : carts) {
				if (cart.getProductDetail().getId() == pdid && cart.getStatus() == 0) {
					cart.setQuantity(cart.getQuantity() + quantity);
					cartDAO.updateCart(cart);

					carts = cartDAO.getCartsByCustomerId(customer.getId());
					session.setAttribute("carts", carts);
					return "{\"message\": \"Sản phẩm đã có trong giỏ hàng!\", \"status\": \"false\"}";
				}
			}

			Cart cart = new Cart(quantity, 0, quantity * price, customer, productDetail);

			cartDAO.createCart(cart);
			carts = cartDAO.getCartsByCustomerId(customer.getId());
		} else {
			carts = (List<Cart>) session.getAttribute("carts");
			if (carts == null) {
				carts = new ArrayList<>();
			} else {
				for (Cart cart : carts) {
					if (cart.getProductDetail().getId() == pdid && cart.getStatus() == 0) {
						cart.setQuantity(cart.getQuantity() + quantity);

						session.setAttribute("carts", carts);
						return "{\"message\": \"Sản phẩm đã có trong giỏ hàng!\", \"status\": \"false\"}";
					}
				}
			}
			Customer customer = new Customer(0);
			Cart cart = new Cart(carts.size() + 1, quantity, 0, quantity * price, customer, productDetail);
			// Thêm sản phẩm vào giỏ
			carts.add(cart);
		}

		session.setAttribute("carts", carts);
		return "{\"message\": \"Sản phẩm đã được thêm vào giỏ hàng!\", \"status\": \"true\"}";
	}

	@ResponseBody // Để trả về dữ liệu trực tiếp (JSON hoặc string)
	@RequestMapping(value = "/del", method = RequestMethod.GET)
	public String delete(@RequestParam("idCart") int idCart, HttpSession session) {

		String email = (String) session.getAttribute("user");
		List<Cart> carts = (List<Cart>) session.getAttribute("carts");
		if (email != null) {
			cartDAO.deleteCart(idCart);
		} else {
			if (carts != null) {
				carts.removeIf(cart -> cart.getID() == idCart);

				session.setAttribute("carts", carts);
			}
		}

		return "{\"message\": \"Xóa giỏ hàng thành công!\", \"status\": \"success\"}";
	}

	@ResponseBody // Để trả về dữ liệu trực tiếp (JSON hoặc string)
	@RequestMapping(value = "/upd", method = RequestMethod.GET)
	public String update(@RequestParam("idCart") int idCart, @RequestParam("quantity") int quantity,
			@RequestParam("total") int total, HttpSession session) {
		String email = (String) session.getAttribute("user");

		if (email != null) {
			Cart cart = cartDAO.getCartById(idCart);
			cart.setQuantity(quantity);
			cart.setTotalPrice(total);
			cartDAO.updateCart(cart);
		} else {
			List<Cart> carts = (List<Cart>) session.getAttribute("carts");
			if (carts != null) {
				Cart cart = carts.stream().filter(c -> c.getID() == idCart).findFirst().orElse(null);

				if (cart != null) {
					cart.setQuantity(quantity);
					cart.setTotalPrice(total);
				}
			}
		}

		return "{\"message\": \"Sửa số lượng thành công!\", \"status\": \"success\"}";
	}

	@RequestMapping(value = "/selected", method = RequestMethod.POST)
	public ResponseEntity<Void> saveSelectedCartIds(@RequestBody Map<String, List<Integer>> data, HttpSession session) {
		// Lấy danh sách idCart từ request
		List<Integer> idCart = data.get("idCart");
		if (idCart != null && !idCart.isEmpty()) {
			// Lưu danh sách vào HttpSession
			session.setAttribute("selectedCartIds", idCart);
			return new ResponseEntity<>(HttpStatus.OK); // Trả về trạng thái 200 OK
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // Trả về trạng thái 400 Bad Request
	}

}