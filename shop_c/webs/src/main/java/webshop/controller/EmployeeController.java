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
import webshop.dao.OriginDAO;
import webshop.dao.MaterialDAO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
    ProductDAO productDAO; // Ensure ProductDAO is injecte

  

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
        
     


        // Xử lý thêm logic để lưu sản phẩm vào cơ sở dữ liệu

        // Trả về trang addproduct nếu không có lỗi
        return "employee/addproduct";
    }

    @RequestMapping("/employee/add-product")
    public String addProduct(
            @RequestParam("name") String name,
            @RequestParam("image") MultipartFile image,
            @RequestParam("description") String description,
            @RequestParam("typesID") int typesID,
            @RequestParam("originsID") int originsID,
            @RequestParam("brandsID") int brandsID,
            @RequestParam("materialsID") int materialsID,
            HttpServletRequest request,
            HttpSession session,
            Model model
    ) {
       

        try {
            // In thông tin nhận được từ form
            System.out.println("Tên sản phẩm: " + name);
            System.out.println("Ảnh sản phẩm: " + (image.isEmpty() ? "Chưa chọn ảnh" : image.getOriginalFilename()));
            System.out.println("Mô tả sản phẩm: " + description);
            System.out.println("ID Loại sản phẩm: " + typesID);
            System.out.println("ID Xuất xứ: " + originsID);
            System.out.println("ID Thương hiệu: " + brandsID);
            System.out.println("ID Chất liệu: " + materialsID);

            // Kiểm tra và tạo tên tệp duy nhất cho ảnh
            String originalFileName = image.getOriginalFilename();
            String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
            String newFileName = System.currentTimeMillis() + fileExtension;

            // Đường dẫn đến thư mục lưu trữ ảnh
            String uploadDir = "images/products";
            File uploadDirectory = new File(uploadDir);
            if (!uploadDirectory.exists()) {
                uploadDirectory.mkdirs(); // Tạo thư mục nếu chưa tồn tại
            }

            // Tạo tệp và lưu ảnh vào thư mục
            File destFile = new File(uploadDir + "/" + newFileName);
            image.transferTo(destFile); // Lưu ảnh vào thư mục

            // Lấy các đối tượng Type, Origin, Brand, Material từ DB
            Type productType = type.getTypeById(typesID);
            Origin productOrigin = origin.getOriginById(originsID);
            Brand productBrand = brand.getBrandById(brandsID);
            Material productMaterial = material.getMaterialById(materialsID);

            // Tạo đối tượng Product và thiết lập các thuộc tính
            Product product = new Product();
            product.setName(name);
            product.setDescription(description);
            product.setImage(newFileName);  // Lưu tên tệp ảnh vào Product
            product.setType(productType);
            product.setOrigin(productOrigin);
            product.setBrand(productBrand);
            product.setMaterial(productMaterial);

            // Lưu sản phẩm vào cơ sở dữ liệu thông qua ProductDAO
            productDAO.addProduct(product);

            // Thêm thông báo thành công vào model
            model.addAttribute("successMessage", "Sản phẩm đã được thêm thành công!");

            // Trả về trang chính của nhân viên sau khi thêm sản phẩm thành công
            return "employee/main";  // Chuyển hướng đến trang chính của nhân viên
        } catch (Exception e) {
            // Xử lý lỗi và trả về thông báo lỗi
            System.out.println("Lỗi khi thêm sản phẩm: " + e.getMessage());
            model.addAttribute("error", "Đã có lỗi xảy ra khi thêm sản phẩm.");
            return "employee/main";  // Trở lại trang chính của nhân viên nếu có lỗi
        }
    }






}
