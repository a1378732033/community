package life.gjj.community.community.config;

import life.gjj.community.community.mapper.UserMapper;
import life.gjj.community.community.model.User;
import life.gjj.community.community.model.UserExample;
import life.gjj.community.community.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Service
public class SesstionInterceptor implements HandlerInterceptor {
    @Autowired
    UserMapper userMapper;
    @Autowired
    NotificationService notificationService;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Cookie[] cookies = request.getCookies();
        if (cookies!=null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) ;
                {
                    String token = cookie.getValue();
                    UserExample example = new UserExample();
                    example.createCriteria().andTokenEqualTo(token);
                    List<User> users = userMapper.selectByExample(example);
                    if (users.size()!= 0) {
                        request.getSession().setAttribute("user",users.get(0));
                        Long underadCount = notificationService.underadCount(users.get(0).getId());
                        request.getSession().setAttribute("underadCount",underadCount);
                    }
                    break;
                }
            }
        }
        Cookie jsCookie=new Cookie("JSESSIONID",null);
        jsCookie.setMaxAge(0);
        response.addCookie(jsCookie);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
