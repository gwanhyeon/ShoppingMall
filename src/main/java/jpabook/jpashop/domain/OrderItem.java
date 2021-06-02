package jpabook.jpashop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jpabook.jpashop.domain.item.Item;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
@Entity
@Table(name="order_item")
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem {
    @Id
    @GeneratedValue
    @Column(name="order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="item_id")
    private Item item;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="order_id")
    private Order order;

    private int orderPrice;
    private int count;

    public static OrderItem createOrderItem(Item item, int orderPrice, int count){
        OrderItem orderItems = new OrderItem();
        orderItems.setItem(item);
        orderItems.setOrderPrice(orderPrice);
        orderItems.setCount(count);
        item.removeStock(count);
        return orderItems;
    }

    public void cancel() {
        getItem().addStock(count);      //주문수량만큼 늘려준다.
    }

    public int getTotalPrice() {
        return getOrderPrice() * getCount();
    }
}