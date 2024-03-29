package life.gjj.community.community.controller;


import life.gjj.community.community.dto.CommentDTO;
import life.gjj.community.community.dto.QuestionDTO;
import life.gjj.community.community.enums.CommentTypeEnum;
import life.gjj.community.community.service.CommentService;
import life.gjj.community.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class QuestionController {
    @Autowired
    QuestionService questionService;
    @Autowired
    CommentService commentService;
    @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id") Long id, Model model){
        QuestionDTO questionDTO=questionService.getById(id);
        List<CommentDTO> comments=commentService.listByTargetId(id, CommentTypeEnum.QUESTION);
        List<QuestionDTO> relatedQuestions=questionService.selectRealated(questionDTO);
        //累加阅读数
          questionService.incView(id);
          model.addAttribute("relatedQuestions",relatedQuestions);
          model.addAttribute("question",questionDTO);
          model.addAttribute("comments",comments);
        return "question";
    }
}
