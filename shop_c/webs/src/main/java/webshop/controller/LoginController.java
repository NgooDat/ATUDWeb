package webshop.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import webshop.dao.AccountDAO;
import webshop.dao.RuleDAO;
import webshop.entity.Account;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
public class LoginController {
    @Autowired
    private AccountDAO accountDAO;
    @Autowired
    private RuleDAO rule;

    @RequestMapping("/login-auth")
    public String login(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            HttpServletRequest request,
            @RequestParam(value = "remember", required = false) String remember,
            HttpSession session,
            Model model
    ) {
    	
    	

        Optional<Account> account = accountDAO.findAccountByUsernameAndPassword(username, password);
        session = request.getSession(false);
        if (account.isPresent()) {
        	
        	Account acc = accountDAO.getAccountByEmail(username);
        	int rules = acc.getRule().getId();
        	//phân quyền các kiểu
        	//sau này có Mã hóa sau session + pass
            session.setAttribute("user", username);
            
            

            return "redirect:/home.htm"; 
        } else {
 
            model.addAttribute("message", "Tên đăng nhập hoặc mật khẩu không đúng!");
            return "login/login"; 
        }
    }
    
    @RequestMapping("logout")
	public String logout(HttpServletRequest request, HttpSession session) {
	    if (session != null) {
	        session.invalidate();
	    }
	    return "redirect:/login.htm";
	}
}