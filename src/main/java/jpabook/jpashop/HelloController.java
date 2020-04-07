package jpabook.jpashop;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {
    @GetMapping("hello")
    public String hello(Model model){
        model.addAttribute("data","hello!!");          // 뷰에다가 넘기는  model 값을 지정해서 준다.
        return "hello";     // hello.html 으로 리턴해준다.

    }

}
