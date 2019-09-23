package life.gjj.community.community.controller;


import life.gjj.community.community.dto.PaginationDTO;
import life.gjj.community.community.mapper.NotificationMapper;
import life.gjj.community.community.model.User;
import life.gjj.community.community.service.NotificationService;
import life.gjj.community.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ProfileController {
    @Autowired
    QuestionService questionService;
    @Autowired
    NotificationService notificationService;
    @GetMapping("/profile/{action}")
    public  String profile(@PathVariable(name ="action") String action, Model model, HttpServletRequest request,
                           @RequestParam(value = "page",defaultValue = "1")Integer page,
                           @RequestParam(value = "size",defaultValue = "4")Integer size){
        User user = (User)request.getSession().getAttribute("user");
        if (user==null){
            return "redirect:/";
        }
        if ("questions".equals(action)){
            model.addAttribute("section","questions");
            model.addAttribute("sectionName","我的提问");
            PaginationDTO paginationDTO = questionService.list(user.getId(), page, size);
            model.addAttribute("pagination",paginationDTO);
        }else if("repies".equals(action)){
            PaginationDTO paginationDTO=notificationService.list(user.getId(), page, size);
            Long unredCount=notificationService.underadCount(user.getId());
            model.addAttribute("section","repies");
            model.addAttribute("pagination",paginationDTO);
            model.addAttribute("sectionName","最新消息");

        }
        return "profile";
    }
}
