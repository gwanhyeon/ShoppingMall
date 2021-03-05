package jpabook.jpashop.domain.service;

import jpabook.jpashop.domain.*;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.domain.repository.MemberRepositoryOld;
import jpabook.jpashop.domain.repository.OrderRepository;
import jpabook.jpashop.domain.repository.OrderSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepositoryOld memberRepository;
    private final ItemService itemService;


    /**
     * 주문
     */

    // 넘어오는 값들을 처리한다.
    @Transactional
    public Long order(Long memberId, Long itemId, int count){

        // 엔티티 조회
        Member member = memberRepository.findOne(memberId);
        Item item = itemService.findOne(itemId);

        // 배송 정보 생성
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());
        delivery.setStatus(DeliveryStatus.READY);

        //주문 상품 생성
        OrderItem orderItem = OrderItem.createOrderItem(item,item.getPrice(),count);

        // protected써서 처리할때
//        OrderItem orderItem1 = new OrderItem();

        // 주문 생성
        Order order = Order.createOrder(member, delivery, orderItem);

        // 주문 저장 order가 모든것들을 관리하게 될때 사용한다. 다른곳에서 쓰이는곳이 있으면 같이쓰지말도록한다.
        orderRepository.save(order);                // cascade 옵션이 붙여져있는데 같이 처리할때 하는것이다 하나의 퍼시스트를 할때 다른 퍼시스트도 같이 조질때

        return order.getId();



    }


    /**
     * 주문 취소
     */

    @Transactional
    public void cancleOrder(Long orderId){

        // 주문 엔티티조회
        Order order = orderRepository.findOne(orderId);
        // 주문 취소
        order.cancel();

    }
    /**
     * 주문 검색
     */

    public List<Order> findOrders(OrderSearch orderSearch){
        return orderRepository.findAll(orderSearch);
    }
}
