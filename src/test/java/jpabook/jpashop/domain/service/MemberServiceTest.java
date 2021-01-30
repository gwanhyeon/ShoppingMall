package jpabook.jpashop.domain.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.repository.MemberRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional          // 테스트에 있으면 자동으로 롤백을 진행한다.
public class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;
    @Autowired EntityManager entityManager;

    // 롤백되는 데이터를 보고싶을때는 엔티티매니저를 선언하면 된다
    @Test
//    @Rollback(false)
    public void 회원가입() throws Exception{


        //given
        Member member = new Member();
        member.setName("kim");

         // when
        Long saveId = memberService.join(member);

        // then
//        entityManager.flush();            영속성컨텍스트가 멤버에 들어간다.
        assertEquals(member, memberRepository.findOne(saveId));

    }


    @Test(expected = IllegalStateException.class)
    public void 중복회원예약() throws Exception{
        //given
        Member member1 = new Member();
        member1.setName("kim");

        Member member2 = new Member();
        member2.setName("kim");


        // when
        memberService.join(member1);
        memberService.join(member2);         //예외 발생 처리

        /* 테스트에 주어진 Expected 옵션을 넣어주면된다.*/
//        try{
//            memberService.join(member2);         //예외 발생 처리
//        } catch(IllegalStateException e){
//            return;
//        }
//
//
//        // then
//        fail("예외가 발생해야한다.");

    }

}