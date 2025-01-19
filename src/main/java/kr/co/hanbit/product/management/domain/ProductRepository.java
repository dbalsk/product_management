package kr.co.hanbit.product.management.domain;

import java.util.List;

//로컬 개발 환경과 서비스 환경에서 사용하는 레포지토리를 다르게 하기위해 추상화 필요
//다른 계층이 인프라스트럭처 계층에 의존하면 안되기에 위 인터페이스를 도메인 계층으로 위치시킴
public interface ProductRepository {
    Product add(Product product);
    Product findById(Long id);
    List<Product> findAll();
    List<Product> findByNameContaining(String name);
    Product update(Product product);
    void delete(Long id);
}
