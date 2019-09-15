package life.gjj.community.community.controller;

import life.gjj.community.community.dto.AccessTikenDTO;
import life.gjj.community.community.dto.GitHubUser;
import life.gjj.community.community.model.User;
import life.gjj.community.community.provider.GitHubProvider;
import life.gjj.community.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
public class AuthorizeController {
    @Autowired
    UserService userService;
    @Autowired
    GitHubProvider gitHubProvider;
    @Value("${github.cliend.id}")
    private String cliemtId;
    @Value("${github.cliend.secret}")
    private  String cliemtSecret;
    @Value("${github.clidend.uri}")
    private  String redirectUri;
    @GetMapping("/callback")
    public  String callback(@RequestParam(name ="code")String code,
                            @RequestParam(name = "state") String state,
                            HttpServletRequest request,
                            HttpServletResponse response){
        AccessTikenDTO accessTikenDTO = new AccessTikenDTO();
        accessTikenDTO.setClient_id(cliemtId);
        accessTikenDTO.setClient_secret(cliemtSecret);
        accessTikenDTO.setCode(code);
        accessTikenDTO.setRedirect_uri(redirectUri);
        accessTikenDTO.setState(state);
        String accessToken = gitHubProvider.getAccessToken(accessTikenDTO);
        GitHubUser gitHubuser = gitHubProvider.gitHubUser(accessToken);
      if(gitHubuser!=null&&gitHubuser.getId()!=null){
          //登陆成功
          User user=new User();
          String token = UUID.randomUUID().toString();
          user.setToken(token);
          user.setAccountId(String.valueOf(gitHubuser.getId()));
          user.setName(gitHubuser.getName());
          user.setAvatarUrl(gitHubuser.getAvatarUrl());
          userService.creareOrUpdate(user);
          response.addCookie(new Cookie("token",token));
          return "redirect:/";
      }else{
          //登陆失败
          return "redirect:/";
      }
    }
    @GetMapping("/logout")
    public String logout(HttpServletRequest request,
                         HttpServletResponse response){
        request.getSession().removeAttribute("user");
        Cookie cookie=new Cookie("token",null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "redirect:/";
    }

}
