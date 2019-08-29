package life.gjj.community.community.controller;

import life.gjj.community.community.mapper.QuestionMapper;
import life.gjj.community.community.mapper.UserMapper;
import life.gjj.community.community.model.Question;
import life.gjj.community.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {
    @GetMapping("/pubilsh")
    public  String pubilsh(){

        return "pubilsh";
    }
    @Autowired
    QuestionMapper questionMapper;
    @Autowired
    UserMapper userMapper;
    @PostMapping("/publish")
    public  String doPubilsh(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("tag") String tag,
            HttpServletRequest request,
            Model model
    ){
        Cookie[] cookies = request.getCookies();
        User user=null;
        if (cookies!=null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) ;
                {
                    String token = cookie.getValue();
                    user= userMapper.findByToken(token);
                    if (user!= null) {
                        request.getSession().setAttribute("user", user);
                    }
                    break;
                }
            }
        }else {
            model.addAttribute("error","请重新登录");
            return "redirect:/";
        }
        Question question = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setCreator(user.getId());
        question.setGmtCreate(System.currentTimeMillis());
        question.setGmtModified(System.currentTimeMillis());
        questionMapper.create(question);
        return "redirect:/";
    }
}
