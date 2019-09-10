package life.gjj.community.community.service;

import life.gjj.community.community.mapper.UserMapper;
import life.gjj.community.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserMapper userMapper;

    public void creareOrUpdate(User user) {
     User dbUser=userMapper.findByAcountId(user.getAccountId());
     if (dbUser==null){
         user.setGmtCreate(System.currentTimeMillis());
         user.setGmtModified(user.getGmtCreate());
          userMapper.insert(user);
     }else {
         dbUser.setGmtModified(System.currentTimeMillis());
         dbUser.setAvatarUrl(user.getAvatarUrl());
         dbUser.setName(user.getName());
         dbUser.setToken(user.getToken());
        userMapper.update(dbUser);
     }

    }
}
