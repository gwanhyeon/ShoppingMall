package jpabook.jpashop.api;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderItem;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.domain.repository.OrderRepository;
import jpabook.jpashop.domain.repository.OrderSearch;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class OrderApiController {
    private final OrderRepository orderRepository;

    /**
     * 엔티티 노출 경
     * @return
     */
    @GetMapping("/api/v1/orders")
    public List<Order> ordersV1(){
        List<Order> orderList = orderRepository.findAllByString(new OrderSearch());
        for (Order order : orderList) {
            order.getMember().getName();
            order.getDelivery().getAddress();

            List<OrderItem> orderItems = order.getOrderItems();
            orderItems.stream().forEach(o -> o.getItem().getName());
        }
        return orderList;
    }

    /**
     * DTO로 노출
     * @return
     */
    @GetMapping("/api/v2/orders")
    public List<OrderDto> orderV2(){
        List<Order> orderList = orderRepository.findAllByString(new OrderSearch());
        List<OrderDto> result = orderList.stream().map(o -> new OrderDto(o)).collect(Collectors.toList());
        return result;
    }
    @GetMapping("/api/v3/orders")
    public List<OrderDto> orderV3(){
        List<Order> orderList = orderRepository.findAllWithItem();

        for (Order order : orderList) {
            System.out.println("order = " + order + "id = " + order.getId());
        }

        List<OrderDto> result = orderList.stream().map(o -> new OrderDto(o)).collect(Collectors.toList());
        return result;
    }

    @GetMapping("/api/v3.1/orders")
    public List<OrderDto> orderV3_page(
            @RequestParam(value="offset",
                    defaultValue = "0") int offset,@RequestParam(value="limit",defaultValue = "100") int limit){
        // 페이징 영향을 주지 않는 Fetch
        List<Order> orderList = orderRepository.findAllWithDelivery(offset, limit);

        // orderItems는 어떻게 처리해야할까?
        List<OrderDto> result = orderList.stream()
                .map(o -> new OrderDto(o))
                .collect(Collectors.toList());
        return result;
    }




    @Getter
    static class OrderDto{
        private Long orderId;
        private String name;
        private LocalDateTime orderDate;
        private OrderStatus orderStatus;
        private Address address;
        private List<OrderItemDto> orderItems;
        public OrderDto(Order order){
            orderId = order.getId();
            orderStatus = order.getStatus();
            orderDate = order.getOrderDate();
            name = order.getMember().getName();
            address = order.getDelivery().getAddress();
            // 엔티티가 안에 있을 경우 프록시초기화후에 돌리면 orderItems가 나타나게된다.
//            order.getOrderItems().stream().forEach(o -> o.getItem().getName());
            orderItems = order.getOrderItems().stream().map(orderItem -> new OrderItemDto(orderItem)).collect(Collectors.toList());
        }

        @Getter
        static class OrderItemDto{
            private String itemName;
            private int orderPrice;
            private int count;
            public OrderItemDto(OrderItem orderItem) {
                this.itemName = orderItem.getItem().getName();
                this.orderPrice = orderItem.getOrderPrice();
                this.count = orderItem.getCount();
            }
        }
    }
}
