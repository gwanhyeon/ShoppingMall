package jpabook.jpashop.domain.service;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.domain.repository.OrderRepository;
import jpabook.jpashop.exception.NotEnoughStockException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
 public class OrderServiceTest {

    @Autowired
    EntityManager em;
    @Autowired
    OrderService orderService;
    @Autowired
    OrderRepository orderRepository;
    @Test
    public void 상품주문() throws Exception{
        Member member = createMember();

        Book book = createBook("KGH", 10000, 10);


        int orderCount = 2;
        //when


        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

        //then

        Order getOrder = orderRepository.findOne(orderId);

        assertEquals("상품 주문시 상태는 ORDER", OrderStatus.ORDER, getOrder.getStatus());
        assertEquals("주문한 상품 종류 수가 정확해야 한다.",1,getOrder.getOrderItems().size());
        assertEquals("주문 가격은 가격 * 수량이다", 10000*orderCount, getOrder.getTotalPrice());
        assertEquals("주문 수량만큼 재고가 줄어야한다.", 8, book.getStockQuantity());

    }

    private Book createBook(String name, int price, int stockQuantity) {
        Book book = new Book();
        book.setName(name);
        book.setPrice(price);
        book.setStockQuantity(stockQuantity);
        em.persist(book);
        return book;
    }

    private Member createMember() {
        //given
        Member member = new Member();
        member.setName("회원1");
        member.setAddress(new Address("서울","강가","123-123"));
        em.persist(member);
        return member;
    }


    @Test(expected = NotEnoughStockException.class)
    public void 상품주문_재고수량초과() throws Exception{
        //given

        Member member = createMember();
        Item item = createBook( "시골 JPA", 10000, 10);

        int orderCount = 11;

        //when

        orderService.order(member.getId(), item.getId(), orderCount);
        //then
        fail("재고 수량 예외가 발생합니다.");
    }

    // 커멘트 + 쉬프트 + T 테스트 단위 옮겨 다닐 수 있음, 파라미터들 커멘드 옵션 M 메서드로 만들어준다., 커멘드 옵션 P 파라미터를 자동으로 지정해줄 수 있다.
    // 커멘드 + 옵션 + V 자동으로 파라미터 선언을 만들어준다.

    @Test
    public void 주문취소() throws Exception{
        //given

        Member member =createMember();
        Book item = createBook("시골 JPA", 10000, 10);

        int orderCount = 2;
        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);
        //when

        orderService.cancleOrder(orderId);

        //then

        Order getOrder = orderRepository.findOne(orderId);

        assertEquals("주문 취소시에는 cancle이다", OrderStatus.CANCEL, getOrder.getStatus());
        assertEquals("주문이 취소된 상품은 그만큼 재고가 증가해야한다", 10, item.getStockQuantity());


    }

}