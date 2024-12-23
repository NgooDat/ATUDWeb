package webshop.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import java.util.List;

import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import webshop.dao.AccountDAO;
import webshop.dao.CartDAO;
import webshop.dao.CustomerDAO;
import webshop.dao.OrderDAO;
import webshop.dao.OrderDetailDAO;
import webshop.dao.OrderStatusDAO;
import webshop.dao.ProductDAO;
import webshop.dao.ProductDetailDAO;
import webshop.entity.Account;

import webshop.entity.Customer;

import webshop.entity.Product;
import webshop.entity.ProductDetail;
import webshop.security.Authentication;
import webshop.security.Base64Aes;
import webshop.security.Bcrypt;
import org.apache.commons.text.StringEscapeUtils;

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
	private String base64 = "";

	@RequestMapping("home")
	public String home(ModelMap model, HttpSession ses, 
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		int auth = Authentication.redirectAuthen(request, response);
		if(auth == 2 ) return "redirect:emhome.htm";
		if(auth == 1 ) return "redirect:adhome.htm";

		List<Product> dsProduct = product.getAllProducts();

		List<ProductDetail> dsDetail = prdd.getAllProductDetails();

		List<Object[]> productInfoList = new ArrayList<Object[]>();

		for (Product p : dsProduct) {
			Double minPrice = dsDetail.stream().filter(detail -> detail.getProduct().getId() == p.getId())
					.mapToDouble(ProductDetail::getPrice).min() // Lấy giá nhỏ nhất
					.orElse(0); // Nếu không tìm thấy, trả về NaN
			base64 = Base64Aes.encrypt(Integer.toString(p.getId()));

			productInfoList.add(new Object[] { p.getId(), p.getName(), minPrice, p.getImage(), base64 });
		}

		model.addAttribute("message", "Sản phẩm nổi bật");
		model.addAttribute("productInfo", productInfoList);

		return "user/home";
	}

	@RequestMapping(value = "search", method = RequestMethod.POST)
	public String search(@RequestParam(value = "search",  required = false) String search, ModelMap model
			,HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		int auth = Authentication.redirectAuthen(request, response);
		if(auth == 2 ) return "redirect:emhome.htm";
		if(auth == 1 ) return "redirect:adhome.htm";


		List<Product> dsProduct = product.getAllProducts(); // Lấy tất cả sản phẩm
		List<ProductDetail> dsDetail = prdd.getAllProductDetails(); // Lấy chi tiết sản phẩm

		// Lọc sản phẩm dựa trên chuỗi search nếu được cung cấp
		if (search != null && !search.isEmpty()) {
			dsProduct = dsProduct.stream().filter(p -> p.getName().toLowerCase().contains(search.toLowerCase()))
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
		model.addAttribute("message", "Sản phẩm cần tìm");
		model.addAttribute("productInfo", productInfoList);

		return "user/home";
	}
	
	@RequestMapping("y7ui32457e43856754i6856i78567y4632894348467845968568998")
	public String adlogin(HttpSession ses, HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		boolean log = Authentication.isLogin(request, response);
	    if(log) {
	    	return "redirect:home.htm";
	    }

//		if (ses.getAttribute("user") != null) {
//			return "redirect:/home.htm";
//			
//		}
		return "login/adminlogin";
	}
	
	@RequestMapping("8tr734eurfjb saeyrt6d6yuk345r89esiufjnHGBHGVYGHJGKGH")
	public String emlologin(HttpSession ses, HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		boolean log = Authentication.isLogin(request, response);
	    if(log) {
	    	return "redirect:home.htm";
	    }

//		if (ses.getAttribute("user") != null) {
//			return "redirect:/home.htm";
//			
//		}
		return "login/emlogin";
	}

	@RequestMapping("login")
	public String login(HttpSession ses, HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		boolean log = Authentication.isLogin(request, response);
	    if(log) {
	    	return "redirect:home.htm";
	    }

//		if (ses.getAttribute("user") != null) {
//			return "redirect:/home.htm";
//			
//		}
		return "login/login";
	}

	@RequestMapping("error")
	public String error() {
		return "error";
	}
	
	
	

	@RequestMapping(value="productinfo")
	public String productinfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (request.getParameter("proid") == null) {
			return "redirect:/home.htm";
		}
		String base64 = Base64Aes.decrypt(request.getParameter("proid"));
		int proid = Integer.parseInt(base64);
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
	public String personal(HttpServletRequest request, HttpSession session,
			HttpServletResponse response) throws Exception {
		
		boolean log = Authentication.isLogin(request, response);
	    if(!log) {
	    	return "redirect:login.htm";
	    }
		String email = (String) session.getAttribute("user");

		Account acc = accd.getAccountByEmail(email);
		

		Customer cs = null;

		if (acc != null) {
			cs = cusd.getCustomerByAccountID(acc.getId());
			acc.setEmail(Base64Aes.decrypt(email));
		}

		if (cs != null) {
			cs.setPhone(Base64Aes.decrypt(cs.getPhone()));
			request.setAttribute("acc", acc);
			request.setAttribute("personal", cs);
		}else {
			return "redirect:error.htm";
		}

		return "user/personal";
	}

	@RequestMapping(value = "/updateProfile", method = RequestMethod.POST)
	public String updateProfile(
	    @RequestParam("name") String name,
	    @RequestParam("phone") String phone,
	    @RequestParam("email") String email,
	    @RequestParam(value = "file", required = false) MultipartFile file,
	    HttpSession session,
	    HttpServletRequest request,
	    HttpServletResponse response) throws Exception
	 {
		name = StringEscapeUtils.escapeHtml4(name);
	    phone = StringEscapeUtils.escapeHtml4(phone);
	    email = StringEscapeUtils.escapeHtml4(email);
		
		boolean log = Authentication.isLogin(request, response);
	    if(!log) {
	    	return "redirect:login.htm";
	    }
	    // Kiểm tra người dùng đã đăng nhập
	    String userEmail = (String) session.getAttribute("user");
	    if (userEmail == null) {
	        return "redirect:/login.htm";
	    }

	    // Lấy tài khoản và khách hàng từ cơ sở dữ liệu
	    Account acc = accd.getAccountByEmail(userEmail);
	    if (acc == null) {
	        request.setAttribute("errorMessage", "Không tìm thấy tài khoản.");
	        return "user/personal";
	    }

	    Customer customer = cusd.getCustomerById(acc.getId());
	    if (customer == null) {
	        request.setAttribute("errorMessage", "Không tìm thấy thông tin khách hàng.");
	        return "user/personal";
	    }

	    // Cập nhật thông tin
	    customer.setName(name);
	    customer.setPhone(Base64Aes.encrypt(phone));
	    acc.setEmail(Base64Aes.encrypt(email));

	    // Xử lý upload ảnh (nếu có)
	    if (file != null && !file.isEmpty()) {
	        try {
	        	String timestamp = new java.text.SimpleDateFormat("yyyyMMdd_HHmmss").format(new java.util.Date());
	            String fileName = timestamp + file.getOriginalFilename();
	            System.out.println(fileName);
	            String uploadDir = request.getServletContext().getRealPath("/images/avatar/");
	            File uploadFile = new File(uploadDir, fileName);
	            file.transferTo(uploadFile);

	            // Cập nhật đường dẫn ảnh đại diện
	            customer.setImage("images/avatar/" + fileName);
	        } catch (Exception e) {
	            request.setAttribute("errorMessage", "Lỗi khi upload ảnh: " + e.getMessage());
	            return "user/personal";
	        }
	    }

	    // Lưu vào cơ sở dữ liệu
	    cusd.updateCustomer(customer);
	    accd.updateAccount(acc);

	    // Gửi thông báo thành công
	    request.setAttribute("successMessage", "Cập nhật thông tin thành công!");
	    return "redirect:/personal.htm";
	}
	
	@RequestMapping(value = "resetpass", method = RequestMethod.POST)
	public String changePassword(
	        @RequestParam("currentPassword") String currentPassword,
	        @RequestParam("newPassword") String newPassword,
	        @RequestParam("confirmPassword") String confirmPassword,
	        HttpSession session,
	        ModelMap model,
	        HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		boolean log = Authentication.isLogin(request, response);
	    if(!log) {
	    	return "redirect:login.htm";
	    }
	    // Kiểm tra người dùng đã đăng nhập
	    String userEmail = (String) session.getAttribute("user");


	    // Lấy thông tin tài khoản từ cơ sở dữ liệu
	    Account acc = accd.getAccountByEmail(userEmail);
	    if (acc == null) {
	        model.addAttribute("errorMessage", "Tài khoản không tồn tại.");
	        return "login/resetpass";
	    }
	    
	    boolean flag = false;

	    // Kiểm tra mật khẩu cũ
	    if (!Bcrypt.matches(currentPassword, acc.getPassword())) { // Nếu cần, thêm mã hóa bcrypt để so sánh
	        
	        flag = true;
	        if(session.getAttribute("count")==null) {
				session.setAttribute("count", 1);
			}else {
				int count = (int)session.getAttribute("count");
				if(count == 2) {
					session.invalidate();
					return "redirect:login.htm";
				}
				count = count + 1;
				session.setAttribute("count", count);
			}
	        model.addAttribute("errorMessage", "Mật khẩu cũ không đúng.");
	        
	    }

	    // Kiểm tra mật khẩu mới và xác nhận mật khẩu
	    else if (!newPassword.equals(confirmPassword)) {
	        model.addAttribute("errorMessage", "Mật khẩu xác nhận không khớp.");
	        flag = true;
	    }
	    
	    //Kiểm tra mk mạnh
	    else if(!Bcrypt.isStrongPassword(confirmPassword)) {
	    	model.addAttribute("errorMessage", "Mật khẩu phải trên 8 ký tự, có chữ hoa chữ thường, số và ký tự đặc biệt!");
	    	flag = true;
	    }
	    
	    if(flag) {
	    	model.addAttribute("pass1", currentPassword);
	        model.addAttribute("pass2", newPassword);
	        model.addAttribute("pass3", confirmPassword);
	        return "login/resetpass";
	    }

	    // Cập nhật mật khẩu mới
	    acc.setPassword(Bcrypt.encodePassword(confirmPassword)); // Nếu cần, mã hóa mật khẩu bằng bcrypt trước khi lưu
	    boolean isUpdated = accd.updateAccount(acc);

	    if (isUpdated) {
	        model.addAttribute("successMessage", "Đổi mật khẩu thành công!");
	        return "redirect:home.htm";
	    } else {
	        model.addAttribute("errorMessage", "Có lỗi xảy ra khi đổi mật khẩu.");
	    }

	    // Quay lại trang đổi mật khẩu với thông báo
	    return "login/resetpass";
	}


	@RequestMapping(value = "resetpass", method = RequestMethod.GET)
	public String showResetPasswordForm(HttpSession session, HttpServletRequest request, 
			HttpServletResponse response) throws IOException {
		boolean log = Authentication.isLogin(request, response);
	    if(!log) {
	    	return "redirect:login.htm";
	    }
	    return "login/resetpass";
	}

}


