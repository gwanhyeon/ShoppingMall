//package jpabook.jpashop;
//
//
//import jpabook.jpashop.domain.item.Book;
//import lombok.RequiredArgsConstructor;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import javax.persistence.EntityManager;
//import javax.swing.*;
//
//@RunWith(SpringBootTest.class)
//@SpringBootTest
//@RequiredArgsConstructor
//public class ItemUpdateTest {
//
//    @Autowired
//    EntityManager em;
//
//    @Test
//    public void updateTest() throws Exception{
//
//        Book book = em.find(Book.class, 1L);
//
//        // TX
//        book.setName("asdfasdfasdfasdfasf");
//
//        // 준영속 엔티티: 더이상 관리하지 않는 엔티티
//        // JPA를 한번 다녀온 친구들을 준영속 상태의 엔티티라고 말한다.
//
//
//
//
//
//        //given
//
//        //when
//
//        //then
//    }
//}
