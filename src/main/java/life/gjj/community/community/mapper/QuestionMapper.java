package life.gjj.community.community.mapper;

import life.gjj.community.community.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;

public interface QuestionMapper {
    @Insert("insert into question (title,description,gmt_create,gmt_modified,creator,tag) " +
            "values (#{title},#{description},#{gmtCreate},#{gmtModified},#{creator},#{tag}) ")
    @Options(useGeneratedKeys = true,keyProperty = "id",keyColumn ="id" )
    public  void create(Question question);
}
