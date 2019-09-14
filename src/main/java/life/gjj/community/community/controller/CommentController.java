package life.gjj.community.community.controller;
import life.gjj.community.community.dto.CommentDTO;
import life.gjj.community.community.dto.ResultDTO;
import life.gjj.community.community.exception.CustomizeErrorCode;
import life.gjj.community.community.mapper.CommentMapper;
import life.gjj.community.community.model.Comment;
import life.gjj.community.community.model.User;
import life.gjj.community.community.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
public class CommentController {
    @Autowired
    CommentService commentService;
    @ResponseBody
    @PostMapping("/comment")
    public Object post(@RequestBody CommentDTO commentDTO,
                       HttpServletRequest request){
        User user = (User)request.getSession().getAttribute("user");
        if (user==null){
            return ResultDTO.error(CustomizeErrorCode.NO_LOGIN);
        }
        Comment comment = new Comment();
        comment.setParentId(commentDTO.getParentId());
        comment.setContent(commentDTO.getContent());
        comment.setType(commentDTO.getType());
        comment.setGmtModified(System.currentTimeMillis());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setCommentor(user.getId());
        comment.setLikeCount(0L);
       commentService.insert(comment);
        Map<String,Object> map=new HashMap();
        map.put("message","成功");
        return ResultDTO.okOf();

    }
}
