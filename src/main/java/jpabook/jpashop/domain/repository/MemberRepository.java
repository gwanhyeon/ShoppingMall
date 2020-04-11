package jpabook.jpashop.domain.repository;


import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository         //자동으로 스프링빈으로 관리하게 해준다
@RequiredArgsConstructor
public class MemberRepository {
    private final EntityManager em;
//    @PersistenceContext         // Autowired로 대신 사용할 수 있다 스프링 부트가 지원해주는것임. 원래는 펄시스턴트만 사용해야함.
//    private EntityManager em;
//
//
//    public MemberRepository(EntityManager em) {
//        this.em = em;
//    }

    public void save(Member member){
        EntityManager em = this.em;
        em.persist(member);
    }

    public Member findOne(Long id){
        Member member = em.find(Member.class, id);
        return member;
    }

    // 모두 찾기
    public List<Member> findAll(){
        //엔티티 객체에 대한 쿼리를 준다라고 생각을 하면된다.
        List<Member> select_m_from_member_m = em.createQuery("select m from Member m", Member.class)
                .getResultList();
        return select_m_from_member_m;
    }

    // 이름 으로 조회
    public List<Member> findByName(String name){
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name",name)
                .getResultList();

    }

}
