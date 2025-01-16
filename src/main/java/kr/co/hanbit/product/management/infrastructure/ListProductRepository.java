package kr.co.hanbit.product.management.infrastructure;

import kr.co.hanbit.product.management.domain.EntityNotFoundException;
import kr.co.hanbit.product.management.domain.Product;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

@Repository
//빈으로 등록됨.
public class ListProductRepository {
    //인프라스트럭쳐 계층

    private List<Product> products = new CopyOnWriteArrayList<>();
    //웹애플리케이션은 멀티 스레드이기에 '스레드 세이프' 한 컬렉션 사용필요.
    //ArrayList는 '스레드 세이프'하지 않은 (스레드 안정성이 없는) 컬렉션
    private AtomicLong sequence = new AtomicLong(1L);
    //위와 마찬기지로 스레드 안정성을 위해 AtomicLong 사용
    //ID 기본적으로 1로 초기화
    //인스트럭쳐에서 id 관리

    public Product add(Product product){
        //상품 추가 기능 구현 메소드

        product.setId(sequence.getAndAdd(1L));
        //ID 가져온 후 1씩 증가하고 set

        products.add(product);
        //리스트에 추가.

        return product;
    }

    public Product findByID(Long id){
        //상품 번호로 조회 기능 구현 메소드
        return products.stream()
                .filter(product -> product.sameID(id))
                //filter의 결과가 참인 product만 뽑아냄.
                .findFirst()
                //filter에 걸린 product 중 첫번째에 대한 Optional 객체 반환
                .orElseThrow(() -> new EntityNotFoundException("Product를 찾지 못했습니다."));
                //Optional 객체이기에 바로 쓸 수 없음. orElseThrow()로 Product있으면 Product 반환, 없으면 예외 반환
    }

    public List<Product> findAll(){
        //전체 상품 목록 조회 기능 구현 메소드
        return products;
    }

    public List<Product> findByNameContaining(String name){
        //상품이름 문자열로 검색 기능 구현 메소드
        return products.stream()
                .filter(product ->  product.containsName(name))
                .toList();
    }

    //update 메소드는 스레드 세이프하지는 않음.
    //두가지 동작(동일 id의 객체의 인덱스 반환, 해당 인덱스의 객체를 product로 교체)이기에 동작 사이에 문제 발생 가능
    public Product update(Product product) {
        Integer indexToModify = products.indexOf(product); //product와 동일한 인스턴스(equals)의 인덱스 반환.
        //동등한 것을 찾아야하지만 동일한 것을 찾으면 당연히 동등하기에 equals 사용
        //중요) equals를 오버라이드하여 id만 같다면 동일한 인스턴스로 판단하도록 설정.
        products.set(indexToModify, product); //해당 인덱스 인스턴스에 product를 넣어 인스턴스 자체를 교체해버림.
        //인스턴스 값을 교체하려면 setter를 사용해야하기에 인스턴스 자체 교체로.
        return product;
    }

    public void delete(Long id) {
        Product product = this.findByID(id);
        products.remove(product);
    }
}
