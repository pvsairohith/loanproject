package com.example.cmscart.controllers;

import java.net.http.WebSocket.Listener;
import java.util.List;

import javax.persistence.criteria.ListJoin;
import javax.servlet.http.HttpSession;

import com.example.cmscart.models.Emailservice;
import com.example.cmscart.models.Loginrepository;
import com.example.cmscart.models.Pagerepository;
import com.example.cmscart.models.Registerrepostiory;
import com.example.cmscart.models.Userrepository;
import com.example.cmscart.models.data.Apply;
import com.example.cmscart.models.data.Login;
import com.example.cmscart.models.data.Page;
import com.example.cmscart.models.data.Register;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class HomeController {

    @Autowired
	private Emailservice emailService;
    
    @Autowired
    private Pagerepository pagerepository;

    @Autowired
    private Userrepository userrepository;

    @Autowired
    private Loginrepository loginrepository;

    @Autowired
    private Registerrepostiory registerrepostiory;
    @GetMapping("/home")
    public String home(Model model,Register regi)
    {
        Page page = pagerepository.findBySlug("home");
        model.addAttribute("pages", page);


        List<Apply> appli = userrepository.findAll();
        model.addAttribute("full",appli);
        
        
        return "home";
    }
    
    @ModelAttribute
    public void sharedData(Model model)
    {
        List<Page> pages = pagerepository.findAll();
        model.addAttribute("cpages", pages);
    }

    @GetMapping("/apply")
    public String apply(Model model,@ModelAttribute Apply appli)
    {
        Page page = pagerepository.findBySlug("apply");
        model.addAttribute("apply",page);
        
        model.addAttribute("form",appli);

        return "application";
    }

    @PostMapping("/apply")
    public String apply(@Validated Apply appli,BindingResult bindingResult,RedirectAttributes redirectAttributes,Model model,@ModelAttribute Login login)
    {
        Page page = pagerepository.findBySlug("balance");
    model.addAttribute("pages", page);
    List<Apply> appli1 = userrepository.findAll();
    model.addAttribute("full",appli1);
        userrepository.save(appli);
        redirectAttributes.addFlashAttribute("message","Form submitted");
        redirectAttributes.addFlashAttribute("alertclass", "alert-sucess");
        String email = appli.getEmail();
        model.addAttribute("email", email);
        
       // emailService.sendSimpleMessage(email, "Welcome to Our Family", "Your have applied successfully. your can check the staus of application");

        return "balance1";
    }

    @GetMapping("/about-us")
    public String about(Model model)
    {
        Page page= pagerepository.findBySlug("about-us");
        model.addAttribute("pages",page);

        return "aboutus";
    }

    @GetMapping("/")
    public String home()
    {
        return "first";
    }

    @GetMapping("/register")
    public String register(Model model,@ModelAttribute Register register)
    {
        
        model.addAttribute("register", register);
        return "register";
    }
    @PostMapping("/register")
    public String register(@Validated Register register,BindingResult bindingResult,Model model,RedirectAttributes redirectAttributes)
    {
        Page page = pagerepository.findBySlug("home");
        model.addAttribute("pages", page);

        List<Apply> appli = userrepository.findAll();
        model.addAttribute("full",appli);
        redirectAttributes.addFlashAttribute("message","go to login page");
        redirectAttributes.addFlashAttribute("alertclass", "alert-sucess");


        registerrepostiory.save(register);
        return "register";
    }
    @GetMapping("/login")
    public String login(Model model,@ModelAttribute Login login)
    {
        model.addAttribute("login", login);
        
        

        return "login";
    }

    

 @PostMapping("/login") 
 public String loginvalidation(@ModelAttribute Login login,HttpSession session,Model model) 
 { 
    loginrepository.save(login);
     
    Page page = pagerepository.findBySlug("home");
    model.addAttribute("pages", page);
    List<Apply> appli = userrepository.findAll();
    model.addAttribute("full",appli);
    String email = login.getEmail();
    model.addAttribute("email", email);
   

     List<Register> reg = registerrepostiory.findAll();
     for(Register x: reg)
     {
         if(x.getEmail().equals(login.getEmail()) && x.getPassword().equals(login.getPassword()))
         {
             return "home";
         }
     }

  
    return null;
 }

 @GetMapping("/plans")
 public String plan()
 {
     return "plans";
 }
 @GetMapping("/balance")
 public String balance(Model model,@ModelAttribute Login log)
 {
    Page page = pagerepository.findBySlug("balance");
    model.addAttribute("pages", page);
    List<Apply> appli1 = userrepository.findAll();
    model.addAttribute("full",appli1);
    model.addAttribute("full1",appli1);

    return "balance";
 }
@PostMapping("/balance")
public String balance(@Validated Login log,Model model,Register register)
{
    Page page = pagerepository.findBySlug("balance");
    model.addAttribute("pages", page);
    List<Apply> appli1 = userrepository.findAll();
    model.addAttribute("full",appli1);
    String p = log.getEmail1();
    model.addAttribute("email", p);
    return "balance";
}
 
 


}
