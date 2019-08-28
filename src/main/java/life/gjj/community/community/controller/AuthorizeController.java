package life.gjj.community.community.controller;

import life.gjj.community.community.dto.AccessTikenDTO;
import life.gjj.community.community.dto.GitHubUser;
import life.gjj.community.community.provider.GitHubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    @GetMapping("/callback")
    public  String callback(@RequestParam(name ="code")String code,
                            @RequestParam(name = "state") String state){
        AccessTikenDTO accessTikenDTO = new AccessTikenDTO();
        accessTikenDTO.setClient_id(cliemtId);
        accessTikenDTO.setClient_secret(cliemtSecret);
        accessTikenDTO.setCode(code);
        accessTikenDTO.setRedirect_uri(redirectUri);
        accessTikenDTO.setState(state);
        String accessToken = gitHubProvider.getAccessToken(accessTikenDTO);
        GitHubUser user = gitHubProvider.gitHubUser(accessToken);
        System.out.println(user.getName());
        return "index";
    }

}
