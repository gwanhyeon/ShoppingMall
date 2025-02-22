package jpabook.jpashop.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@Slf4j
public class HomeController {


    //Logger log = LoggerFactory.getLogger(getClass()); 어노테이션으로 처리할 수 있다.


    @RequestMapping("/")
    public String home(){
        log.info("home Controller");
        return "home";          // home.html 파일을 찾아가게된다.
    }

}
