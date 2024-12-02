package webshop.controller;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.web.bind.annotation.*;
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
import webshop.dao.RuleDAO;
import webshop.dao.BrandDAO;
import webshop.dao.TypeDAO;
import webshop.dao.OriginDAO;
import webshop.dao.MaterialDAO;

import webshop.entity.Account;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;
import java.util.ArrayList;
import org.springframework.ui.ModelMap;
import webshop.entity.Brand;
import webshop.entity.Type;
import webshop.entity.Origin;
import webshop.entity.Material;
import webshop.entity.Product;




@Controller

public class EmployeeController {
    
    @Autowired
    BrandDAO brand;
    @Autowired
    TypeDAO type;
    @Autowired
    OriginDAO origin;
    @Autowired
    MaterialDAO material;

    @Autowired
    public ProductDAO productDAO;

    @RequestMapping("employee/main")
    public String employeePage() {
        return "employee/main"; // Trả về view "employee.jsp"
    }

    @RequestMapping("employee/addproduct")
    public String home(ModelMap model) {
        // Lấy các thông tin về loại sản phẩm, xuất xứ, thương hiệu và chất liệu
        List<Type> dsTypes = type.getAllTypes(); // Lấy tất cả loại sản phẩm
        List<Origin> dsOrigins = origin.getAllOrigins(); // Lấy tất cả xuất xứ
        List<Brand> dsBrands = brand.getAllBrands(); // Lấy tất cả thương hiệu
        List<Material> dsMaterials = material.getAllMaterials(); // Lấy tất cả chất liệu

        // Thêm các thông tin vào model
        model.addAttribute("types", dsTypes);
        model.addAttribute("origins", dsOrigins);
        model.addAttribute("brands", dsBrands);
        model.addAttribute("materials", dsMaterials);

        return "employee/addproduct"; // Trả về view "employee/addproduct.jsp"
    }

    @RequestMapping(value = "employee/addproduct", method = RequestMethod.POST)
    public String addProduct(@ModelAttribute Product product, Model model) {
        try {
            // In ra các giá trị trường từ form
            System.out.println("Product Name: " + product.getName());
            System.out.println("Product Image: " + product.getImage());
            System.out.println("Product Description: " + product.getDescription());
            System.out.println("Product Type ID: " + product.getType());
            System.out.println("Product Origin ID: " + product.getOrigin());
            System.out.println("Product Brand ID: " + product.getBrand());
            System.out.println("Product Material ID: " + product.getMaterial());

            // Lưu sản phẩm vào cơ sở dữ liệu
            if (productDAO.addProduct(product)) {
                model.addAttribute("message", "Sản phẩm đã được thêm thành công!");
            } else {
                model.addAttribute("message", "Lỗi khi thêm sản phẩm!");
            }

            // Chuyển hướng đến trang kết quả
            return "employee/result";  // Trả về trang kết quả "employee/result.jsp"
        } catch (Exception e) {
            model.addAttribute("message", "Có lỗi xảy ra khi thêm sản phẩm: " + e.getMessage());
            return "employee/result"; // Trả về trang kết quả với lỗi
        }
    }
}
