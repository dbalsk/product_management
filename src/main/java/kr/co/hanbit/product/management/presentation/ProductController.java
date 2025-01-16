package kr.co.hanbit.product.management.presentation;

import jakarta.validation.Valid;
import kr.co.hanbit.product.management.application.SimpleProductService;
import kr.co.hanbit.product.management.domain.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {
    //표현 계층 (interface 계층)

    private SimpleProductService simpleProductService;

    @Autowired
    //의존성을 주입받음.
    ProductController(SimpleProductService simpleProductService){
        this.simpleProductService=simpleProductService;
    }

    @RequestMapping(value = "/products", method = RequestMethod.POST)
    public ProductDto createProduct(@Valid @RequestBody ProductDto productDto){
        //추가 기능 api
        return simpleProductService.add(productDto);
    }

    @RequestMapping(value = "/products/{id}", method = RequestMethod.GET)
    public ProductDto findProductById(@PathVariable Long id){
        //id 기준 조회는 특정 자원을 조회하는 것 -> 패스 베리어블 사용
        //상품번호로 조회 기능 api
        return simpleProductService.findById(id);
    }

/*    @RequestMapping(value = "/products", method = RequestMethod.GET)
    public List<ProductDto> findAllProduct(){
        //전체 조회 기능 api -> findProducts에 전체조회 기능과 검색기능을 합쳐 구현.
        return simpleProductService.findAll();
    }*/

    @RequestMapping(value = "/products", method = RequestMethod.GET)
    public List<ProductDto> findProducts(@RequestParam(required = false) String name){
        //특정 문자열 검색은 일종의 필터 조건 -> 쿼리 파라메터 (@RequestParam 사용)
        //전체 조회 기능 api + 상품이름 문자열로 검색 기능 api
        if(null==name){
            //name의 쿼리 파라메터가 넘어오지 않을 경우 전체 조회
            return simpleProductService.findAll();
        }
        return simpleProductService.findByNameContaining(name);
    }

    @RequestMapping(value = "/products/{id}", method = RequestMethod.PUT)
    public ProductDto updateProduct(
            @PathVariable Long id,
            @RequestBody ProductDto productDto
    ){
        //상품 수정 기능 api
        productDto.setId(id); //받아온 객체에 id 설정
        return simpleProductService.update(productDto); //받아온 객체를 원래 id있던 객체에 덮어씌움.
    }

    @RequestMapping(value = "/products/{id}", method = RequestMethod.DELETE)
    public void deleteProduct(@PathVariable Long id){
        //상품 삭제 기능 api
        simpleProductService.delete(id);
    }
}
