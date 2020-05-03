package jpabook.jpashop.domain.service;

import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.domain.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    /**
     * 상품 등록
     * @param item
     */
    @Transactional
    public void saveItem(Item item){
        itemRepository.save(item);
    }
    /**
     * 상품 업데이트
     * 영속성 엔티티로 업데이트  -> 실제로 머지보다는 변경/감지 해당 로직을 사용해야 한다!!!
     * 컨트롤러에서 어설프게 엔티티를 생성하지말자!!!!!!!!
     * @param
     */

    @Transactional
    public Item updateItem(Long id, String name, int price, int stockQuantity, String author, String isBn){
        Item findItem = itemRepository.findOne(id);         //영속성 엔티티를 찾아왔음


        /* 실제 로직 처리 더 나은 방법 */
//        findItem.change(name,price,stockQuantity);
        findItem.setName(name);
        findItem.setPrice(price);
        findItem.setStockQuantity(stockQuantity);
        findItem.setAuthor(author);
        findItem.setIsbn(isBn);



        //findItem.change(price,name,stockQuantity);          // * 중요 이런식으로 변경해야한다. 의미있는 메소드로 넣어주고 경해야한다.
        /* 데이터 양이 많아질 경우 dto를 만들어서 처리해도된다. */
        /*
        findItem.setName(itemDto.getName());
        findItem.setPrice(itemDto.getPrice());
        findItem.setStockQuantity(itemDto.getStockQuantity());

         */

        /* 이전방법 */
        /*
        findItem.setPrice(param.getPrice());
        findItem.setName(param.getName());
        findItem.setStockQuantity(param.getStockQuantity());

         */
        return findItem;
        // 따로 값들을 처리해줄 필요가 없음. 트랜잭션이 -> 커밋하고 -> flush 영속성 엔티티 모두 찾는다 -> 바뀐값을 업데이트 쿼리를 자동으로 쳐버린다.

    }

    /**
     * 상품 모두 조회
     ** @return
     */
    public List<Item> findItems(){
        return itemRepository.findAll();
    }

    /**
     * 상품 하나 조회
     * @param itemId
     * @return
     */
    public Item findOne(Long itemId){
        return itemRepository.findOne(itemId);
    }
}
