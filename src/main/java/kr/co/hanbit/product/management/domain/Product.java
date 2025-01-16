package kr.co.hanbit.product.management.domain;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import kr.co.hanbit.product.management.presentation.ProductDto;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Objects;

//도메인 객체 (도메인 계층)
public class Product {
    //엔티티: 도메인 객체이면서 ID를 가지는 존재

    private Long id; //상품번호(식별자)

    @Size(min = 1, max = 100)
    private String name; //상품이름

    @Max(1_000_000)
    @Min(0)
    private Integer price; //상품가격

    @Max(1_000_000)
    @Min(0)
    private Integer amount; //재고수량

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean sameID(Long id){
        return this.id.equals(id);
    }

    public Boolean containsName(String name){
        //객체지향적 코드를 위해 getter로 값을 보내지 않음
        //getter를 쓰면 값이 여러 곳(listProductRepository)에 중복 사용된 절차지향적 코드
        return this.name.contains(name);
    }

    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id); //id가 같다면 같은 Product로 판단.
    }
}
