package jpabook.jpashop.domain.repository;

import jpabook.jpashop.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {
    // select m from Member m where m.name = ? By뒤에 나오는것의 이름이 중요하다

    List<Member> findByName(String name);

}
