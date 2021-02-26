package jpabook.jpashop.domain.repository.order.simplequery;

import jpabook.jpashop.domain.repository.OrderRepository;
import jpabook.jpashop.domain.repository.OrderSimpleQueryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderSimpleQueryRepository {

    private final EntityManager em;
    /**
     *  반환할때 dto로 변경해서 반환하는 방법
     *  V4가 좋다고 할 수 없다. 트레이드오프가 존재한다. => 외부의 모습을 건드리지 않는 상태이다.
     *  로직 재활용하기가 어렵다. 코드 더러움.
     * @return
     */
    public List<jpabook.jpashop.domain.repository.OrderSimpleQueryDto> findOrderDtos() {
        return em.createQuery(
                "select new jpabook.jpashop.domain.repository.order.simplequery.OrderSimpleQueryDto(" +
                        "o.id, m.name, o.orderDate, o.status, d.address) " +
                        "from Order o " +
                        "join o.member m " +
                        "join o.delivery d", OrderSimpleQueryDto.class)
                .getResultList();
    }

}
