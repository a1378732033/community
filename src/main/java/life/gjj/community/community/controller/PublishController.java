package life.gjj.community.community.controller;
import life.gjj.community.community.dto.QuestionDTO;
import life.gjj.community.community.mapper.QuestionMapper;
import life.gjj.community.community.model.Question;
import life.gjj.community.community.model.User;
import life.gjj.community.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {
    @Autowired
    QuestionService questionService;
    @GetMapping("/pubilsh/{id}")
    public String edit(@PathVariable(name= "id") Integer id,Model model){
        QuestionDTO question = questionService.getById(id);
        model.addAttribute("title",question.getTitle());
        model.addAttribute("description",question.getDescription());
        model.addAttribute("tag",question.getTag());
        model.addAttribute("id",question.getId());
        return "pubilsh";
    }
    @GetMapping("/pubilsh")
    public  String pubilsh(){

        return "pubilsh";
    }
    @PostMapping("/publish")
    public  String doPubilsh(
            @RequestParam( value = "title",required = false) String title,
            @RequestParam(value = "description",required = false) String description,
            @RequestParam(value = "tag",required = false) String tag,
            @RequestParam(value = "id",required = false) Integer id,
            HttpServletRequest request,
            Model model
    ){
        model.addAttribute("title",title);
        model.addAttribute("description",description);
        model.addAttribute("tag",tag);
        if (title==null||title==""){
            model.addAttribute("error","标题不能为空");
            return "pubilsh";
        }
        if (description==null||description==""){
            model.addAttribute("error","内容不能为空");
            return "pubilsh";
        }
        if (tag==null||tag==""){
            model.addAttribute("error","请添加标签");
            return "pubilsh";
        }
        User user = (User)request.getSession().getAttribute("user");
        if (user==null){
            model.addAttribute("error","请重新登录");
            return "pubilsh";
        }
        Question question = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setCreator(user.getId());
//        question.setGmtCreate(System.currentTimeMillis());
//        question.setGmtModified(System.currentTimeMillis());
        question.setId(id);
        questionService.createOrUpdate(question);
//        questionMapper.create(question);
        return "redirect:/";
    }
}
