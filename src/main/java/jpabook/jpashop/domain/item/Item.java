package jpabook.jpashop.domain.item;

import jpabook.jpashop.domain.Category;
import jpabook.jpashop.exception.NotEnoughStockException;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

//@BatchSize(size = 100)
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
//조인드: 가장 정규화된스타일 // 싱글테이블 : 한테이블에 다 때렵박음
//테이블 퍼 클래스 : 상속되는 클래스대로 나옴
@Getter @Setter
public abstract class Item {

    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();

    //==비즈니스 로직== //

    //재고 수량 증가
    public void addStock(int quantity){
        this.stockQuantity += quantity;
    }

    public void removeStock(int quantity){
        int resStock = this.stockQuantity -= quantity;
        if(resStock < 0){
            throw  new NotEnoughStockException("need more stock");
        }
        this.stockQuantity = resStock;
    }
}
