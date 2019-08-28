package life.gjj.community.community;

import life.gjj.community.community.mapper.UserMapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@MapperScan(value = "life.gjj.community.community.mapper")
@SpringBootApplication
public class CommunityApplication {
    public static void main(String[] args) {

        SpringApplication.run(CommunityApplication.class, args);
    }

}
