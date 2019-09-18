package life.gjj.community.community.mapper;

import life.gjj.community.community.model.Comment;
import life.gjj.community.community.model.Question;

public interface CommentExtMapper {
  int incCommentCount(Comment comment);
}