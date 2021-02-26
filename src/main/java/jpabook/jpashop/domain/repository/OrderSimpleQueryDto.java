package jpabook.jpashop.domain.repository;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public
class OrderSimpleQueryDto{
    private Long orderId;
    private String name;
    private LocalDateTime orderDate;
    private OrderStatus orderStatus;
    private Address address;
    public OrderSimpleQueryDto(Order order){
        orderId = order.getId();
        name = order.getMember().getName();     // LAZY 초기화 영속성컨텍스트를 찾아보고 없으면 DB날리는 시점
        orderDate = order.getOrderDate();
        orderStatus = order.getStatus();
        address = order.getDelivery().getAddress();
    }
    public OrderSimpleQueryDto(Long orderId, String name, LocalDateTime orderDate, OrderStatus orderStatus, Address address){
        this.orderId = orderId;
        this.name = name;     // LAZY 초기화 영속성컨텍스트를 찾아보고 없으면 DB날리는 시점
        this.orderDate =orderDate;
        this.orderStatus = orderStatus;
        this.address = address;
    }
}