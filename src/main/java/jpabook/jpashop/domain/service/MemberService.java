package jpabook.jpashop.domain.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.repository.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)         // 전체를 리드온니로 먹히고 회원가입시에만 읽기전용이 아닌것으로 사용할때 저렇게 쓴다.
//@AllArgsConstructor
@RequiredArgsConstructor            // final있는 필드의 생성자만 생성시켜준다. 이것이 조금더 나은 방법이다.
//데이터 변경은 트랙잭션이 반드시 있어야한다. 다른 트랜잭션이 여러개있지만 스프링꺼 쓰는게 좋음
public class MemberService {

    private final MemberRepository memberRepository;          // 레파지토리에 있는것을 필드인젝션을 해준다. final로 권장한다-> 컴파일시점을 알 수 있음.


//    // 생성자 인젝션으로 넣어주는것이 좋다.
//    @Autowired          // 생성자 하나만있는경우 생성자를 자동으로 인젝션시켜준다.
//    public MemberService(MemberRepository memberRepository) {
//        this.memberRepository = memberRepository;
//    }




    /**
     * 회원가입 readonly = true 넣으면 안됨.
     */
    @Transactional
    public Long join(Member member){

        validateDuplicateMember(member); // 중복회원 체크
        memberRepository.save(member);
        return member.getId();          // 항상 값이 있다는게 보장이 된다.
    }

    //중복 회원 체크
    private void validateDuplicateMember(Member member) {
        // EXCEPTION
        List<Member> findMembers = memberRepository.findByName(member.getName());           // 유니크 제약조건을 먹어주는게 동시회원가입을 막을 수 있다.

        if(!findMembers.isEmpty()){
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    /**z
     * 회원 전체 조회
     */

//    @Transactional(readOnly = true)
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    /**
     * 회원 단건 조회 트랜잭션을 넣어주면 읽기전용 트랜잭션이기때문에 성능상 좋아짐
     */
//    @Transactional(readOnly = true)
    public Member findOne(Long memberId){
        return memberRepository.findOne(memberId);
    }

    @Transactional
    public void update(Long id, String name) {
        // 현재 영속상태
        Member member = memberRepository.findOne(id);
        member.setName(name);
    }
}
