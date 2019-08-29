package life.gjj.community.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PublishController {
    @GetMapping("/pubilsh")
    public  String pubilsh(){

        return "pubilsh";
    }
}
