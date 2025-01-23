package kr.co.hanbit.product.management.application;

import kr.co.hanbit.product.management.domain.Product;
import kr.co.hanbit.product.management.domain.ProductRepository;
import kr.co.hanbit.product.management.presentation.ProductDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import  static org.mockito.Mockito.*; //when(). any() 사용가능
import static org.junit.jupiter.api.Assertions.assertTrue;


@ExtendWith(MockitoExtension.class) //모킹을 사용하여 **단위테스트**
public class SimpleProductServiceUnitTest {
    @Mock //해당 의존성에 목 객체(모의 객체) 주입 (주입만으로는 아무 기능 x)
    private ProductRepository productRepository;
    @Mock
    private ValidationService validationService;

    @InjectMocks //@Mock으로 주입한 목 객체들을 해당 의존성(simpleProductService)내에 있는 의존성에 주입
    //목 객체를 주입받는 대상인 simpleProductService는 (목 객체가 아니기에) 실제 인스턴스 생성 가능
    private  SimpleProductService simpleProductService;

    @Test
    @DisplayName("상품 추가 후에는 추가된 상품이 반환되어야한다")
    void productAddTest (){
        ProductDto productDto = new ProductDto("연필", 300, 20);
        Long PRODUCT_ID = 1L;

        Product product = ProductDto.toEntity(productDto);
        product.setId(PRODUCT_ID);
        //when(A).thenReturn(B) -> 목 객체가 A에 해당하는 동작을 수행할 때 B에 있는 값을 반환
        when(productRepository.add(any())).thenReturn(product); //productRepository에 any(아무값)을 add할 시 product 반환
        //목 객체 행동 정의 완료

        ProductDto savedProductDto = simpleProductService.add(productDto);

        assertTrue(savedProductDto.getId().equals(PRODUCT_ID));
        assertTrue(savedProductDto.getName().equals(productDto.getName()));
        assertTrue(savedProductDto.getPrice().equals(productDto.getPrice()));
        assertTrue(savedProductDto.getAmount().equals(productDto.getAmount()));
    }

}
