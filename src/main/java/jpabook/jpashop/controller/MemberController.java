package jpabook.jpashop.controller;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/members/new")
    public String createForm(Model model){          // 여기서 모델은
        model.addAttribute("memberForm", new MemberForm());
        return "members/createMemberForm";

    }

    @PostMapping("/members/new")
    public String create(@Valid MemberForm memberForm, BindingResult result){         //Valid해서 값들을 체킹하고 넘어간다. notEmpty같은 것들

        if(result.hasErrors()){
            return "members/createMemberForm";         // 실패 처리
        }

        Address address = new Address(memberForm.getCity(), memberForm.getStreet(), memberForm.getZipcode());

        Member member = new Member();
        member.setName(memberForm.getName());
        member.setAddress(address);

        memberService.join(member);
        return "redirect:/";            //재로딩하면 안좋기때문에 리다이렉트로 보내버린다.

    }
    // 엔티티를 만들때는 API를 반환하면 안된다. 필드가 그대로 추가되어버린다.  패스워드가 그대로 노출할 수 있고, API 스펙이 달라질 수 있다.


    @GetMapping("/members")
    public String list(Model model){
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "members/memberList";

    }

}
