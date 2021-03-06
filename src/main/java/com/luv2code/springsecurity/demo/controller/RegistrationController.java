package com.luv2code.springsecurity.demo.controller;

import com.luv2code.springsecurity.demo.entity.Role;
import com.luv2code.springsecurity.demo.entity.User;
import com.luv2code.springsecurity.demo.service.RoleService;
import com.luv2code.springsecurity.demo.service.UserService;
import com.luv2code.springsecurity.demo.user.CrmUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@Controller
@RequestMapping("/register")
public class RegistrationController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    private Logger logger = Logger.getLogger(getClass().getName());
    private Map<String, String> roles;

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @GetMapping("/showRegistrationForm")
    public String showMyLoginPage(Model model) {
        model.addAttribute("crmUser", new CrmUser());
        model.addAttribute("roles", roles);
        return "registration-form";
    }

    @PostMapping("/processRegistrationForm")
    public String processRegistrationForm(@Valid @ModelAttribute("crmUser") CrmUser crmUser, BindingResult bindingResult, Model model) {

        String userName = crmUser.getUserName();
        logger.info("Processing registration form for: " + userName);

        // form validation
        if(bindingResult.hasErrors()) {
            return "registration-form";
        }

        // check the database if user already exists
        User existing = userService.findByUserName(userName);
        if (existing != null) {
            model.addAttribute("crmUser", new CrmUser());
            model.addAttribute("roles", roles);
            model.addAttribute("registrationError", "User name already exists.");
            logger.warning("User name already exists.");

            return "registration-form";
        }

        // save user in the database
        userService.save(crmUser);

        logger.info("Successfully created user: " + userName);

        return "registration-confirmation";
    }

    @PostConstruct
    protected void loadRole() {
        List<Role> rolesList = roleService.findAllRoles();

        roles = new LinkedHashMap<String, String>();

        for (Role role: rolesList) {
            String[] splittedName = role.getName().split("_");
            roles.put(role.getName(), splittedName[1].toLowerCase());
        }



    }
}
