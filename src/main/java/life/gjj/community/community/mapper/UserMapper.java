package life.gjj.community.community.mapper;


import life.gjj.community.community.model.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

//@Mapper
public interface UserMapper {
    @Insert("insert into user (name,account_id,token,gmt_create,gmt_modified) values " +
            "(#{name},#{accountId},#{token},#{gmtCreate},#{gmtModified})")
    @Options(useGeneratedKeys = true,keyProperty = "id",keyColumn ="id" )
    public  void insert(User user);
    @Select("select *from user where token=#{token}")
     User findByToken(@Param("token") String token);
}
