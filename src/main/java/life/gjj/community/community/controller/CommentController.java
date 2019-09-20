package life.gjj.community.community.controller;
import life.gjj.community.community.dto.CommentCreateDTO;
import life.gjj.community.community.dto.CommentDTO;
import life.gjj.community.community.dto.ResultDTO;
import life.gjj.community.community.enums.CommentTypeEnum;
import life.gjj.community.community.exception.CustomizeErrorCode;
import life.gjj.community.community.model.Comment;
import life.gjj.community.community.model.User;
import life.gjj.community.community.service.CommentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class CommentController {
    @Autowired
    CommentService commentService;
    @ResponseBody
    @PostMapping("/comment")
    public Object post(@RequestBody CommentCreateDTO commentCreateDTO,
                       HttpServletRequest request){
        User user = (User)request.getSession().getAttribute("user");
        if (user==null){
            return ResultDTO.error(CustomizeErrorCode.NO_LOGIN);
        }
        if ( commentCreateDTO==null || StringUtils.isBlank(commentCreateDTO.getContent())){
            return  ResultDTO.error(CustomizeErrorCode.CONTENT_IS_ENPY);
        }
        Comment comment = new Comment();
        comment.setParentId(commentCreateDTO.getParentId());
        comment.setContent(commentCreateDTO.getContent());
        comment.setType(commentCreateDTO.getType());
        comment.setGmtModified(System.currentTimeMillis());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setCommentor(user.getId());
        comment.setLikeCount(0L);
        commentService.insert(comment,user);
        return ResultDTO.okOf();
    }
    @ResponseBody
    @GetMapping("/comment/{id}")
    public ResultDTO<List> comments(@PathVariable(name = "id") Long id){
        List<CommentDTO> commentDTOS = commentService.listByTargetId(id, CommentTypeEnum.COMMENT);
        return ResultDTO.okOf(commentDTOS);
    }
}
