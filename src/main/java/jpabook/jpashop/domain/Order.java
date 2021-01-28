package jpabook.jpashop.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="orders")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {

    @Id
    @GeneratedValue
    @Column(name="order_id")
    private Long id;
    /*
     다대다 관계 일 경우 하나의 주인을 정해주어야한다!! , FK키를 누가 업데이트를 해야할까? Order
     연관관계가 가장 가까운 경우를 바꾸어준다. 주인은 그대로 두면된다 order는 두자.
     */

    @ManyToOne(fetch = FetchType.LAZY)           // 오더와 멤버는 다대일 관계, 여러개의 주문은 하나의 멤버만 할 수 있다.
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems = new ArrayList<>();

    // casecadetype의 경우 한번에 persist할 경우 여러번 넣는게 아니라 한번만 넣어준다.
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)           // 1:1같은경우 FK를 아무곳에나 넣어도 된다. 주로 Access를 사용하는 경우에 많이 지정한다.주로 Order
    @JoinColumn(name="delivery_id")         // 주인으로 지정
    private Delivery delivery;      // 배달

    // localdatetime Java 8에서는 어노테이션 쓸 필요없음
    private LocalDateTime orderDate;        // 주문 시간

    @Enumerated(EnumType.STRING)       //컬럼이 숫자로 들어간다
    private OrderStatus status;     // Order 상태 (order , cancel)

    // == 연관 관계 메소드 ==/

    public void setMember(Member member){
        this.member = member;
        member.getOrders().add(this);
    }
    public void addOrderItem(OrderItem orderItem){
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void setDelivery(Delivery delivery){
        this.delivery = delivery;
        delivery.setOrder(this);
    }



    // == 생성 메서드 ==//
    /**
     * 생성 지점 메서드 생성
     * @param member
     * @param delivery
     * @param orderItems
     * @return
     */
    public static Order createOrder(Member member, Delivery delivery, OrderItem... orderItems){

        Order order = new Order();
        order.setMember(member);
        order.setDelivery(delivery);

        for(OrderItem orderItem :orderItems){
            order.addOrderItem(orderItem);

        }

        order.setStatus(OrderStatus.ORDER);
        order.setOrderDate(LocalDateTime.now());
        return order;

    }


    // == 비즈니스 로직 ==/
    /**
     * 주문취소
     */
    public  void cancel(){
        if(delivery.getStatus() == DeliveryStatus.COMP){
            throw new IllegalStateException("이미 배송 완료된 상품은 취소가 불가능합니다.");
        }
        this.setStatus(OrderStatus.CANCEL);

        for (OrderItem orderItem: orderItems) {
            orderItem.cancel();
        }
    }
    // == 조회 로직 ==//
    /**
     * 전체 주문 가격 조회
     * @return
     */
    public int getTotalPrice(){
        int totalPrice = 0;
        for(OrderItem orderItem: orderItems){
            totalPrice += orderItem.getTotalPrice();
        }
        return totalPrice;

    }





}
