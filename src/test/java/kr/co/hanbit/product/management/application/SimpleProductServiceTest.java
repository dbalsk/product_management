package kr.co.hanbit.product.management.application;

import kr.co.hanbit.product.management.domain.EntityNotFoundException;
import kr.co.hanbit.product.management.presentation.ProductDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
//import static으로 클래스 이름 생략하게 해줌
//그래서 Assertions.assertTrue가 아닌 assertTrue 즉 메소드명만으로도 사용 가능

@SpringBootTest //스프링 컨테이너가 뜨는 통합 테스트 (실행까지 시간이 조금 더 걸리는 단점은 있음)
@ActiveProfiles("prod") //테스트 코드에서 사용할 profile 지정
class SimpleProductServiceTest {

    @Autowired //simpleProductService 의존성 주입
    SimpleProductService  simpleProductService;

    @Transactional //트랜잭셔널한 처리를 위해 사용, 테스트 코드에 사용할 경우 테스트 후 커밋이 아닌 롤백해줌.
    // -> 테스트 코드에서 추가한 데이터가 실제로 데이터베이스에는 반영x
    @Test //테스트 코드라는 의미
    @DisplayName("상품을 추가한 후 id로 조회하면 해당 상품이 조회되어야 한다.") //이름 지정
    void productAddAndFindByIdTest (){
        ProductDto productDto = new ProductDto("연필", 300, 20); //테스트기에 생성자가 아닌 필드에 바로 주입

        ProductDto savedProductDto = simpleProductService.add(productDto);
        Long savedProductId = savedProductDto.getId();

        ProductDto foundProductDto = simpleProductService.findById(savedProductId);

        //동등성 비교를 위해 equals 메소드 사용 (데이터베이스의 findById는 완전히 새로운 Product 인스턴스를 생성하는 구조이기에 동일성 비교 시(==) 테스트 실패)
        assertTrue(savedProductDto.getId().equals(foundProductDto.getId()));
        assertTrue(savedProductDto.getName().equals(foundProductDto.getName()));
        assertTrue(savedProductDto.getPrice().equals(foundProductDto.getPrice()));
        assertTrue(savedProductDto.getAmount().equals(foundProductDto.getAmount()));
    }

    @Test
    @DisplayName("존재하지 않는 상품 id로 조회하면 EntityNotFoundException이 발생해야한다.")
    void findProductNotExistIdTest(){
        Long notExistId = -1L;

        //assertThrows apthem -> 2개의 인자필요
        //1. 어떤 예외가 발생해야하는지 2. 예외가 발생해야할 코드 (람다표현식으로)
        assertThrows(EntityNotFoundException.class, ()->{
           simpleProductService.findById(notExistId);
        });
    }
}