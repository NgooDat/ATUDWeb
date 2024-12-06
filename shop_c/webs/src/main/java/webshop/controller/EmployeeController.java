package webshop.controller;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;

import webshop.entity.Product;
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
import webshop.dao.SizeDAO;
import webshop.dao.OriginDAO;
import webshop.dao.MaterialDAO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile; // Để sử dụng MultipartFile

import java.io.File;
import java.io.IOException; // Để xử lý IOException


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
import webshop.entity.Size;
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
    SizeDAO size;
    @Autowired
    ProductDAO productDAO; // Ensure ProductDAO is injecte

  
    @ResponseBody
    @RequestMapping("employee/main")
    public String employeePage() {
        return "employee/main"; // Trả về view "employee.jsp"
    }

 


    @RequestMapping("employee/addproduct")
    public String home(ModelMap model) {
    	System.out.println("Thêm sản phẩm thành công: với GET ");
        // Lấy các thông tin về loại sản phẩm, xuất xứ, thương hiệu và chất liệu
        List<Type> dsTypes = type.getAllTypes(); // Lấy tất cả loại sản phẩm
        List<Size> dsSizes = size.getAllSizes(); // Lấy tất cả kích thước
        List<Origin> dsOrigins = origin.getAllOrigins(); // Lấy tất cả xuất xứ
        List<Brand> dsBrands = brand.getAllBrands(); // Lấy tất cả thương hiệu
        List<Material> dsMaterials = material.getAllMaterials(); // Lấy tất cả chất liệu

        // Thêm các thông tin vào model
        model.addAttribute("types", dsTypes);
        model.addAttribute("sizes", dsSizes);
        model.addAttribute("origins", dsOrigins);
        model.addAttribute("brands", dsBrands);
        model.addAttribute("materials", dsMaterials);
        
     


        // Xử lý thêm logic để lưu sản phẩm vào cơ sở dữ liệu

        // Trả về trang addproduct nếu không có lỗi
        return "employee/addproduct";
    }
    
    @RequestMapping(value = "employee/addproduct", method = RequestMethod.POST)
    public String addProduct(HttpServletRequest request, Model model) {
        System.out.println("Thêm sản phẩm thành công: với POST ");

        // Lấy các thông tin từ form
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String image = request.getParameter("image"); // Lấy tên file từ form
        String typesID = request.getParameter("typesID");
        String originsID = request.getParameter("originsID");
        String brandsID = request.getParameter("brandsID");
        String materialsID = request.getParameter("materialsID");
        
        // Debug: Kiểm tra giá trị lấy được
        System.out.println("Tên sản phẩm: " + name);
        System.out.println("Mô tả sản phẩm: " + description);
        System.out.println("Tên file ảnh: " + image);
        System.out.println("Loại sản phẩm (ID): " + typesID);
        System.out.println("Xuất xứ (ID): " + originsID);
        System.out.println("Thương hiệu (ID): " + brandsID);
        System.out.println("Chất liệu (ID): " + materialsID);

        // Tạo đối tượng từ ID
        Type type = new Type();
        type.setId(Integer.parseInt(typesID));
        
      
        Origin origin = new Origin();
        origin.setId(Integer.parseInt(originsID));

        Brand brand = new Brand();
        brand.setId(Integer.parseInt(brandsID));

        Material material = new Material();
        material.setId(Integer.parseInt(materialsID));

        // Lưu thông tin vào cơ sở dữ liệu
        Product product = new Product();
        product.setName(name);
        product.setDescription(description);
        product.setImage(image); // Chỉ lưu tên file vào cơ sở dữ liệu
        product.setType(type); // Gán đối tượng Type vào sản phẩm
        product.setOrigin(origin); // Gán đối tượng Origin vào sản phẩm
        product.setBrand(brand); // Gán đối tượng Brand vào sản phẩm
        product.setMaterial(material); // Gán đối tượng Material vào sản phẩm
        boolean isSuccess = productDAO.addProduct(product);

        // Xử lý kết quả
        if (isSuccess) {
            model.addAttribute("message", "Sản phẩm đã được thêm thành công!");
            return "employee/addproduct"; // Trả về trang hiển thị thành công
        } else {
            model.addAttribute("message", "Đã xảy ra lỗi khi thêm sản phẩm!");
            return "error"; // Trả về trang hiển thị lỗi
        }
    }






}
