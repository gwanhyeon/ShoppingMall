package jpabook.jpashop.domain.repository;

import jpabook.jpashop.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@RequiredArgsConstructor

public class ItemRepository {

    @PersistenceContext
    private final EntityManager em;

    /**
     * 상품 저장
     * @param item
     */
    public void save(Item item){

        if(item.getId() == null){
            em.persist(item);       // 새로 생성 할 때
        }else {
            em.merge(item);         // 업데이트 라고 생각하면 된다.(완전업데이트는 아님) 웹 어플리케이션에서 말해주신다고헀음.
            // 병합은 준영속 상태의 엔티티를 영속 상태로 변경할 때 사용하는 기능이다.
            // 주의할점: 여기서온 item은 영속성 컨텍스트로 바뀌지않는다 . ITEM merge가 영속성 컨텍스트를 관리하는 애다.
            // 병합할때 주의해야할점: 변경 감지 기능을 사용하면 원하는 속성만 선택해서 변경할 수 있지만, 병합을 사용하면 모든 속성이 변경된다.
        }
    }

    /**
     * 상품 하나 조회
     * @param id
     * @return
     */
    public Item findOne(Long id){
        return em.find(Item.class, id);
    }

    /**
     * 상품 모두 조회
     * @return
     */
    public List<Item> findAll(){
        return em.createQuery("select i from Item i", Item.class)
                .getResultList();

    }
}
