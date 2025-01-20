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

    public Product(){
    }

    //데이터베이스에서 Product의 인스턴스 값을 가져와야되는 상황 (아직 id는 필요 x)
    //기술적인 문제로 getter를 반드시 추가해야되는 상황이기에 추가 (허나 꼭 필요한 곳에서만 사용해야됨).
    public Integer getPrice() {
        return price;
    }
    public String getName() {
        return name;
    }
    public Integer getAmount() {
        return amount;
    }
    //update 메소드 사용 시 id에 대한 값 매핑이 필요하여 추가
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    //BeanPropertyRowMapper의 조건 충족을 위해 setter 추가
    public void setName (String name) {
        this.name = name;
    }
    public void setPrice( Integer price) {
        this.price = price;
    }
    public void setAmount( Integer amount) {
        this.amount = amount;
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
