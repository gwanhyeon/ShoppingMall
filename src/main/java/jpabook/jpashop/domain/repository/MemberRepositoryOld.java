package jpabook.jpashop.domain.repository;


import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryOld {
    private final EntityManager em;

    public Long save(Member member){
        EntityManager em = this.em;
        em.persist(member);
        return member.getId();
    }
    public Member findOne(Long id){
         return em.find(Member.class, id);
    }
    public List<Member> findAll(){
        List<Member> select_m_from_member_m = em.createQuery("select m from Member m", Member.class)
                .getResultList();
        return select_m_from_member_m;
    }
    public List<Member> findByName(String name){
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name",name)
                .getResultList();
    }
}
