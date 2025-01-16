package kr.co.hanbit.product.management.application;

import kr.co.hanbit.product.management.domain.Product;
import kr.co.hanbit.product.management.infrastructure.ListProductRepository;
import kr.co.hanbit.product.management.presentation.ProductDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
//단순히 service 이노테이션 추가만으로도 위 클래스는 스프링 프레임워크에 의해 생성되어 관리됨.
//빈으로 등록됨.
public class SimpleProductService {

    private ListProductRepository listProductRepository;
    private ModelMapper modelMapper;
    private ValidationService validationService;

    @Autowired //의존성을 주입.
    SimpleProductService(ListProductRepository listProductRepository, ModelMapper modelMapper, ValidationService validationService){
        this.listProductRepository = listProductRepository;
        this.modelMapper = modelMapper;
        this.validationService = validationService;
    }

    public ProductDto add(ProductDto productDto){
        Product product = modelMapper.map(productDto, Product.class);
        //ProductDto를 Product로 변환
        //(변화시킬 대상, 변화될 대상.class)
        validationService.checkValid(product); //변환 직후 유효성 검사

        Product savedProduct = listProductRepository.add(product);
        //레포지토리 호출
        ProductDto savedProductDto = modelMapper.map(savedProduct, ProductDto.class);;
        //Product를 ProductDto로 변환
        return savedProductDto;
        //Dto 반환
    }

    public ProductDto findById(Long id){
        Product product = listProductRepository.findByID(id);
        ProductDto productDto = modelMapper.map(product, ProductDto.class);
        return productDto;
    }

    public List<ProductDto> findAll(){
        List<Product> products = listProductRepository.findAll();
        List<ProductDto> productDtos = products.stream()
                //Product 리스트를 ProductDto 리스트로 변환
                .map(product -> modelMapper.map(product, ProductDto.class))
                .toList();
        return productDtos;
    }

    public List<ProductDto> findByNameContaining(String name){
        List<Product> products = listProductRepository.findByNameContaining(name);
        List<ProductDto> productDtos = products.stream()
                .map(product -> modelMapper.map(product, ProductDto.class))
                .toList();
        return productDtos;
    }

    public ProductDto update(ProductDto productDto){
        Product product = modelMapper.map(productDto, Product.class);
        Product updatedProduct = listProductRepository.update(product);
        ProductDto updatedProductDto = modelMapper.map(updatedProduct, ProductDto.class);
        return updatedProductDto;
    }

    public void delete(Long id) {
        listProductRepository.delete(id);
    }
}
