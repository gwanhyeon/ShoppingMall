package jpabook.jpashop;
import jpabook.jpashop.domain.item.Book;
import lombok.RequiredArgsConstructor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
@RunWith(SpringRunner.class)
@SpringBootTest
@RequiredArgsConstructor
public class ItemUpdateTest {

    @Autowired
    EntityManager em;
    @Test
    public void updateTest() throws Exception{
        Book book = em.find(Book.class, 1L);
        // TX
        book.setName("gwanhyeon kim");
    }
}
