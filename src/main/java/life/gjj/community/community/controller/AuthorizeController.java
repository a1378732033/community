package life.gjj.community.community.controller;

import life.gjj.community.community.dto.AccessTikenDTO;
import life.gjj.community.community.dto.GitHubUser;
import life.gjj.community.community.mapper.UserMapper;
import life.gjj.community.community.model.User;
import life.gjj.community.community.provider.GitHubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@Controller
public class AuthorizeController {
    @Autowired
    GitHubProvider gitHubProvider;
    @Value("${github.cliend.id}")
    private String cliemtId;
    @Value("${github.cliend.secret}")
    private  String cliemtSecret;
    @Value("${github.clidend.uri}")
    private  String redirectUri;
    @Autowired
    UserMapper userMapper;
    @GetMapping("/callback")
    public  String callback(@RequestParam(name ="code")String code,
                            @RequestParam(name = "state") String state,
                            HttpServletRequest request){
        AccessTikenDTO accessTikenDTO = new AccessTikenDTO();
        accessTikenDTO.setClient_id(cliemtId);
        accessTikenDTO.setClient_secret(cliemtSecret);
        accessTikenDTO.setCode(code);
        accessTikenDTO.setRedirect_uri(redirectUri);
        accessTikenDTO.setState(state);
        String accessToken = gitHubProvider.getAccessToken(accessTikenDTO);
        GitHubUser gitHubuser = gitHubProvider.gitHubUser(accessToken);
      if(gitHubuser!=null){
          //登陆成功
          User user=new User();
          user.setToken(UUID.randomUUID().toString());
          user.setAccountId(String.valueOf(gitHubuser.getId()));
          user.setName(gitHubuser.getName());
          user.setGmtCreate(System.currentTimeMillis());
          user.setGmtModified(user.getGmtCreate());
          userMapper.insert(user);
          request.getSession().setAttribute("user",gitHubuser);
          return "redirect:/";
      }else{
          //登陆失败
          return "redirect:/";
      }
    }

}
