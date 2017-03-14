package ru.maxon.usermanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import ru.maxon.usermanager.model.User;
import ru.maxon.usermanager.service.UserService;

import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class UserController {

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
    }

    private UserService userService;

    @Autowired(required = true)
    @Qualifier(value = "userService")
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "users",method = RequestMethod.GET)
    public String listUsers(Model model){
        model.addAttribute("user",new User());
        model.addAttribute("listUsers",this.userService.listUsers());

        return "users";
    }

    @RequestMapping(value = "/users/add",method = RequestMethod.POST)
    public String addUser(@ModelAttribute("user") User user){
        if (user.getId()==0)
            userService.addUser(user);
        else
            userService.updateUser(user);
        return "redirect:/users";
    }

    @RequestMapping("/remove/{id}")
    public String removeUser(@PathVariable("id") int id){
        userService.removeUser(id);

        return "redirect:/users";
    }

    @RequestMapping("edit/{id}")
    public String editUser(@PathVariable("id") int id, Model model){
        model.addAttribute("user",userService.getUserById(id));
        model.addAttribute("listUsers",userService.listUsers());

        return "users";
    }

    @RequestMapping("userdata/{id}")
    public String userData(@PathVariable("id") int id, Model model){
        model.addAttribute("user",this.userService.getUserById(id));

        return "userdata";
    }

}
