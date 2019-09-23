package life.gjj.community.community.provider;

import com.alibaba.fastjson.JSON;
import life.gjj.community.community.dto.AccessTikenDTO;
import life.gjj.community.community.dto.GitHubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class GitHubProvider {
    //发送Post请求 传入accessTikenDTO 获取accessToken
  public  String getAccessToken(AccessTikenDTO  accessTikenDTO){
      MediaType mediaType = MediaType.get("application/json; charset=utf-8");

      OkHttpClient client = new OkHttpClient();


          RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTikenDTO));
          Request request = new Request.Builder()
                  .url("https://github.com/login/oauth/access_token")
                  .post(body)
                  .build();
          try (Response response = client.newCall(request).execute()) {
              String string = response.body().string();
              String token = string.split("&")[0].split("=")[1];
              return token;
      } catch (Exception e) {
              e.printStackTrace();
          }

      return null;
  }
  //发送get请求 传入accessToken
  public GitHubUser gitHubUser(String accessToken){
      OkHttpClient client = new OkHttpClient();
      Request request = new Request.Builder()
              .url("https://api.github.com/user?access_token="+accessToken)
              .build();
      try {
          Response response = client.newCall(request).execute();
          String string = response.body().string();
          GitHubUser gitHubUser = JSON.parseObject(string, GitHubUser.class);
          return gitHubUser;
      } catch (IOException e) {
          e.printStackTrace();
      }
      return  null;
  }
}
