package kr.co.hanbit.product.management.application;

import kr.co.hanbit.product.management.domain.Product;
import kr.co.hanbit.product.management.domain.ProductRepository;
import kr.co.hanbit.product.management.infrastructure.DatabaseProductRepository;
import kr.co.hanbit.product.management.presentation.ProductDto;
//import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
//단순히 service 이노테이션 추가만으로도 위 클래스는 스프링 프레임워크에 의해 생성되어 관리됨.
//빈으로 등록됨.
public class SimpleProductService {

    private ProductRepository productRepository;
    //private ModelMapper modelMapper; 1/22 - ModelMapper 제거
    private ValidationService validationService;

    @Autowired //의존성을 주입. //단위테스트 시 두 의존성에 목객체가 주입
    SimpleProductService(ProductRepository productRepository, ValidationService validationService){
        this.productRepository = productRepository;
        this.validationService = validationService;
    }

    public ProductDto add(ProductDto productDto){
        Product product = ProductDto.toEntity(productDto); //ProductDto를 Product로 변환
        validationService.checkValid(product); //변환 직후 유효성 검사
        Product savedProduct = productRepository.add(product);
        ProductDto savedProductDto = ProductDto.toDto(savedProduct); //Product를 ProductDto로 변환
        return savedProductDto;
    }

    public ProductDto findById(Long id){
        Product product = productRepository.findById(id);
        ProductDto productDto = ProductDto.toDto(product);
        return productDto;
    }

    public List<ProductDto> findAll(){
        List<Product> products = productRepository.findAll();
        List<ProductDto> productDtos = products.stream()
                //Product 리스트를 ProductDto 리스트로 변환
                .map(product -> ProductDto.toDto(product))
                .toList();
        return productDtos;
    }

    public List<ProductDto> findByNameContaining(String name){
        List<Product> products = productRepository.findByNameContaining(name);
        List<ProductDto> productDtos = products.stream()
                .map(product -> ProductDto.toDto(product))
                .toList();
        return productDtos;
    }

    public ProductDto update(ProductDto productDto){
        Product product = ProductDto.toEntity(productDto);
        Product updatedProduct = productRepository.update(product);
        ProductDto updatedProductDto = ProductDto.toDto(updatedProduct);
        return updatedProductDto;
    }

    public void delete(Long id) {
        productRepository.delete(id);
    }
}
