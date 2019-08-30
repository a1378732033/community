package life.gjj.community.community.controller;

import life.gjj.community.community.dto.QuestionDTO;
import life.gjj.community.community.mapper.QuestionMapper;
import life.gjj.community.community.mapper.UserMapper;
import life.gjj.community.community.model.Question;
import life.gjj.community.community.model.User;
import life.gjj.community.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
public class IndexController {
    @Autowired
    UserMapper userMapper;
    @Autowired
    QuestionService questionService;
    @GetMapping("/")
    public String hell(HttpServletRequest request,Model model){

        Cookie[] cookies = request.getCookies();
        if (cookies!=null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) ;
                {
                    String token = cookie.getValue();
                    User user = userMapper.findByToken(token);
                    if (user != null) {
                        request.getSession().setAttribute("user", user);
                    }
                    break;
                }
            }
        }
        List<QuestionDTO>  questionList=questionService.list();
        model.addAttribute("questions",questionList);
        return "index";
    }
}
