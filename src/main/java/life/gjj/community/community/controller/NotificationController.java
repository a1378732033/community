package life.gjj.community.community.controller;

import life.gjj.community.community.dto.NotificationDTO;
import life.gjj.community.community.dto.PaginationDTO;
import life.gjj.community.community.enums.NotificationTypeEnum;
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
public class NotificationController {
    @Autowired
    QuestionService questionService;
    @Autowired
    NotificationService notificationService;
    @GetMapping("/notification/{id}")
    public  String profile(@PathVariable(name ="id") Long id, HttpServletRequest request
                          ){
        User user = (User)request.getSession().getAttribute("user");
        if (user==null){
            return "redirect:/";
        }
       NotificationDTO notificationDTO=notificationService.read(id,user);
      if (NotificationTypeEnum.REPLY_COMMENT.getType()==notificationDTO.getType()||
      NotificationTypeEnum.REPLY_QUESTION.getType()==notificationDTO.getType()){
          return "redirect:/question/"+notificationDTO.getOuterid();
      }else {
          return "redirect:/";
      }
    }
}
