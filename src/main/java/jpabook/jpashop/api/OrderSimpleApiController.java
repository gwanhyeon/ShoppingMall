package jpabook.jpashop.api;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.domain.repository.OrderRepository;
import jpabook.jpashop.domain.repository.OrderSearch;
import jpabook.jpashop.domain.repository.OrderSimpleQueryDto;
import jpabook.jpashop.domain.repository.order.simplequery.OrderSimpleQueryRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * X to One
 * order
 * order -> member
 * order -> Delivery
 */
@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {
    private final OrderRepository orderRepository;
    private final OrderSimpleQueryRepository orderSimpleQueryRepository;

    /**
     * 엔티티를 노출하지말
     * @return
     */
    @GetMapping("/api/v1/simple-orders")
    public List<Order> orderV1(){
        List<Order> all = orderRepository.findAllByString(new OrderSearch());
        // EAZY를 처리하는짓은 하지말자 굳이 성능은 같지만 이상한 로직이 되어버린다.
        for (Order order : all) {
            // order.getMember()까지 프록시고 getName할때 실제 객체를 DB에서 가져온다.
            order.getMember().getName();        // Lazy 강제 초기화
            order.getDelivery().getAddress();
        }
        return all;
    }
    /**
     * DTO를 만들어서 주문을 조회해보자. N+1 문제
     */
    @GetMapping("/api/v2/simple-orders")
    public List<SimpleOrderDto> ordersV2(){
        // Order 2개
        List<Order> orders = orderRepository.findAllByString(new OrderSearch());
        /*
        order 조회 -> SQL 1번 실행 -> 결과가 주문수가 2개가 나올 경우 -> 결과적으로 2번루프가 돌게 된다.
        1번 루프: 쿼리 2방
        2번 루프: 쿼리 2방
        이것이 N(orders를 N개를 가져온거 회원, 배송 )+1()의 문제를 가져온다.
        즉, 1 orders 조회, 회원 N개 + 배송 N
        이것은 어떻게 처리할 수 있을까?
        -> 페치조인으로 처리
         */
        List<SimpleOrderDto> result = orders.stream()
                .map(o -> new SimpleOrderDto(o)).collect(Collectors.toList());
        //SimpleOrderDto::new 같은 의미
        return result;
    }

    /**
     * fetch join
     * @return
     */
    @GetMapping("/api/v3/simple-orders")
    public List<SimpleOrderDto> ordersV3(){
        List<Order> orders = orderRepository.findAllWithDelivery();
        List<SimpleOrderDto> ordersList = orders.stream().map(o -> new SimpleOrderDto(o)).collect(Collectors.toList());
        return ordersList;
    }
    @GetMapping("/api/v4/simple-orders")
    public List<OrderSimpleQueryDto> ordersV4(){
        return orderSimpleQueryRepository.findOrderDtos();
    }




    @Data
    static class SimpleOrderDto{
        private Long orderId;
        private String name;
        private LocalDateTime orderDate;
        private OrderStatus orderStatus;
        private Address address;
        public SimpleOrderDto(Order order){
            orderId = order.getId();
            name = order.getMember().getName();     // LAZY 초기화 영속성컨텍스트를 찾아보고 없으면 DB날리는 시점
            orderDate = order.getOrderDate();
            orderStatus = order.getStatus();
            address = order.getDelivery().getAddress();
        }
    }
}
