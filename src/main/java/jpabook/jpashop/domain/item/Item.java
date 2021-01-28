package jpabook.jpashop.domain.item;

import jpabook.jpashop.domain.Category;
import jpabook.jpashop.exception.NotEnoughStockException;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)           // 싱글 테이블 전략
@DiscriminatorColumn(name = "dtype")                            //  구분될때 값 지정하는것
public abstract class Item {
    @Id
    @GeneratedValue
    @Column(name="item_id")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;
    private String author;
    private String isbn;

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<Category>();

    /* 상품 늘리고 줄이고 비즈니스 로직 */
    /**
     * 재고수량 늘리고
     * @param orderQuantity
     */
    public void addStock(int orderQuantity){
        this.stockQuantity += orderQuantity;
    }

    /**
     * 재고수량 줄이고
     * @param orderQuantity
     */
    public void removeStock(int orderQuantity){
        int restStock = this.stockQuantity - orderQuantity;
        if(restStock < 0){
            throw new NotEnoughStockException("Need More Stock");
        }
        this.stockQuantity = restStock;
    }

}
