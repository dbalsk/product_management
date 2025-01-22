package kr.co.hanbit.product.management.presentation;

import jakarta.validation.constraints.NotNull;
import kr.co.hanbit.product.management.domain.Product;

public class ProductDto {
    //DTO
    //Product에서 분리하여 표현 계층부터 응용 계층까지 역할

    private Long id; //상품번호(식별자)

    @NotNull
    private String name; //상품이름

    @NotNull
    private Integer price; //상품가격

    @NotNull
    private Integer amount; //재고수량

    public ProductDto(){
    }

    public ProductDto(String name, Integer price, Integer amount) {
        this.name = name;
        this.price = price;
        this.amount = amount;
    }

    public ProductDto(Long id, String name, Integer price, Integer amount) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.amount = amount;
    }

    //getter로 HTTP 응답을 줌.
    public Long getId() {

        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Integer getPrice() {
        return price;
    }

    public Integer getAmount() {
        return amount;
    }

    //dto -> 엔티티 변환
    public static Product toEntity(ProductDto productDto){
        Product product = new Product(
                productDto.getId(),
                productDto.getName(),
                productDto.getPrice(),
                productDto.getAmount()
        );

        return product;
    }

    //엔티티 -> dto 변환
    public static ProductDto toDto(Product product){
        ProductDto productDto = new ProductDto(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getAmount()
        );

        return productDto;
    }
}

