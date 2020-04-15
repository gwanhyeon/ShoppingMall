package jpabook.jpashop.domain.repository;

import jpabook.jpashop.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor

public class ItemRepository {

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
