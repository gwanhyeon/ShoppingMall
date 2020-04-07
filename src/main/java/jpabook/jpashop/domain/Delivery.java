package jpabook.jpashop.domain;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.item.DeliveryStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Delivery {

    @Id @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;

    @OneToOne(mappedBy = "delivery")        // 주인과 매핑
    private Order order;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING)       //컬럼이 숫자로 들어간다
    private DeliveryStatus status;      // READY, COMP 0 , 1 중간 다른 상태가 생기면 망한다. ORDINAL절대  쓰지마라
}
