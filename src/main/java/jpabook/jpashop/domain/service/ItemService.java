package jpabook.jpashop.domain.service;

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
