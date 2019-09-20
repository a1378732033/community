package life.gjj.community.community.mapper;

import life.gjj.community.community.model.Question;
import life.gjj.community.community.model.QuestionExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface QuestionExtMapper {
  int incView(Question record);
  int incCommentCount(Question record);
  List<Question> selectRelate(Question question);
}