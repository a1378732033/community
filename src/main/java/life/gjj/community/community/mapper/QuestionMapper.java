package life.gjj.community.community.mapper;

import life.gjj.community.community.dto.QuestionDTO;
import life.gjj.community.community.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface QuestionMapper {
    @Insert("insert into question (title,description,gmt_create,gmt_modified,creator,tag) " +
            "values (#{title},#{description},#{gmtCreate},#{gmtModified},#{creator},#{tag}) ")
    @Options(useGeneratedKeys = true,keyProperty = "id",keyColumn ="id" )
    public  void create(Question question);
    @Select("select *from question limit #{offset},#{size}")
    List<Question> list(@Param(value = "offset") Integer offset, @Param(value = "size") Integer size);
    @Select("select  count(1) from question")
    Integer count();
}
