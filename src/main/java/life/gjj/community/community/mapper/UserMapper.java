package life.gjj.community.community.mapper;


import life.gjj.community.community.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

//@Mapper
public interface UserMapper {
    @Insert("insert into user (name,account_id,token,gmt_create,gmt_modified) values " +
            "(#{name},#{accountId},#{token},#{gmtCreate},#{gmtModified})")
    @Options(useGeneratedKeys = true,keyProperty = "id",keyColumn ="id" )
    public  void insert(User user);
}
