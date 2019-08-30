package life.gjj.community.community.mapper;


import life.gjj.community.community.model.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Service;

import java.util.List;

//@Mapper
public interface UserMapper {
    @Insert("insert into user (name,account_id,token,gmt_create,gmt_modified,avatar_url) values " +
            "(#{name},#{accountId},#{token},#{gmtCreate},#{gmtModified},#{avatarUrl})")
    @Options(useGeneratedKeys = true,keyProperty = "id",keyColumn ="id" )
    public  void insert(User user);
    @Select("select *from user where token=#{token}")
     User findByToken(@Param("token") String token);
     @Select("select * from user where id=#{id}")
    User findById(@Param("id") Integer id);
}
