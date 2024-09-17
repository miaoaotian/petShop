package com.miaoaotian.sys_back.controller;
import com.miaoaotian.sys_back.model.*;
import com.miaoaotian.sys_back.model.vo.AppointmentTimeVO;
import com.miaoaotian.sys_back.service.*;
import com.miaoaotian.sys_back.utils.Constant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/user")
@Slf4j
public class UserController {
    private UserService userService;
    private PetService petService;
    private FoodService foodService;
    private AppointmentsService appointmentsService;
    private StaffService staffService;
    private MembersService membersService;
    @Autowired
    public void setMembersService(MembersService membersService) {
        this.membersService = membersService;
    }
    @Autowired
    public void setStaffService(StaffService staffService) {
        this.staffService = staffService;
    }
    @Autowired
    public void setAppointmentsService(AppointmentsService appointmentsService) {
        this.appointmentsService = appointmentsService;
    }
    @Autowired
    public void setFoodService(FoodService foodService) {
        this.foodService = foodService;
    }
    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
    @Autowired
    public void setPetService(PetService petService) {
        this.petService = petService;
    }

    @PostMapping("/login")
    public String login(@RequestParam String phone, @RequestParam String password, HttpSession session) {
        Member member = userService.login(phone, password);
        if (member != null) {
            session.setAttribute("user", member);
            return "redirect:/user/pet";
        } else {
            return "redirect:/user/me";
        }
    }
    @PostMapping("/register")
    public String register(@RequestParam("name") String name, @RequestParam("phone") String phone,
                           @RequestParam("password") String password, @RequestParam("address") String address,
                           @RequestParam("email") String email,@RequestParam("sex") Integer sex) {
        Member member = new Member();
        member.setName(name);
        member.setPhone(phone);
        member.setPass(password);
        member.setAddress(address);
        member.setEmail(email);
        member.setSex(sex);
        membersService.addMember(member);
        return "redirect:/user/me";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/user/me";
    }
    @PostMapping("/changePass")
    public String changePass(@RequestParam("oldPass") String oldPass, @RequestParam("newPass") String newPass, HttpSession session) {
        Member member = (Member) session.getAttribute("user");
        String check = member.getPass();
        if(check == null) {
            String pass = DigestUtils.md5DigestAsHex(newPass.getBytes()).toUpperCase() + Constant.pass_end;
            member.setPass(pass);
            log.info("member:"+member);
            membersService.updateMember(member);
        } else {
            String oldPassMd5 = DigestUtils.md5DigestAsHex(oldPass.getBytes()).toUpperCase() + Constant.pass_end;
            log.info("oldPass:"+oldPassMd5);
            log.info("check:"+check);
            if (!check.equals(oldPassMd5)) {
                return "redirect:/user/me";
            }
            String newPassMd5 = DigestUtils.md5DigestAsHex(newPass.getBytes()).toUpperCase() + Constant.pass_end;
            member.setPass(newPassMd5);
            log.info("member:"+member);
            membersService.updateMember(member);
        }
        return "redirect:/user/me";
    }
    @GetMapping("/pet")
    public String showPetPage(HttpSession session, Model model) {
        if (session.getAttribute("user") == null) {
            return "redirect:/user/me";
        }
        List<Pet> pets = petService.findAllPets();
        model.addAttribute("currentPage", "pet");
        model.addAttribute("pets", pets);
        return "pet";
    }

    @GetMapping("/appointment")
    public String showAppointmentPage(HttpSession session, Model model) {
        if (session.getAttribute("user") == null) {
            return "redirect:/user/me";
        }

        List<Staff> staffs = staffService.getDoctor();
        model.addAttribute("currentPage", "appointment");
        model.addAttribute("staffs", staffs);
        return "appointment";
    }


    @GetMapping("/food")
    public String showFoodPage(HttpSession session, Model model) {
        if (session.getAttribute("user") == null) {
            return "redirect:/user/me";
        }
        List<Food> foods = foodService.findAllNoPage();
        model.addAttribute("currentPage", "food");
        model.addAttribute("foods", foods);
        return "food";
    }

    @GetMapping("/me")
    public String showProfilePage(HttpSession session, Model model) {
        model.addAttribute("currentPage", "me");
        return "me";
    }
    @PostMapping("/buy-pet")
    public String buyPet(@RequestParam("petId") Integer petId,@RequestParam("price") Integer price, HttpSession session) {
        Member member = (Member) session.getAttribute("user");
        userService.buyPet(petId, price, member.getId());
        return "pet";
    }
    @PostMapping("/buy-food")
    public String buyFood(@RequestParam("id") Integer id, @RequestParam("nums") Integer nums,@RequestParam("price") Integer price, HttpSession session) {
        Member member = (Member) session.getAttribute("user");
//        log.info("食品数量："+nums);
        userService.buyFood(id, nums, price, member.getId());
        return "food";
    }
    @GetMapping ("/getAppointmentBySid")
    @ResponseBody
    public List<AppointmentTimeVO> getAppointmentBySid(@RequestParam("sid") Integer sid) {
        return appointmentsService.getAppointment(sid);
    }
    @PostMapping("/bookAppointment")
    public String bookAppointment(@RequestParam("sid") Integer sid, @RequestParam("date") String date,HttpSession session) {
        Member member = (Member) session.getAttribute("user");
        appointmentsService.setAppointment(sid, date, member.getId());
        return "redirect:/user/appointment";
    }
}
