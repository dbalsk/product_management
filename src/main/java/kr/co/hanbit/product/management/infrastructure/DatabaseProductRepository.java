package kr.co.hanbit.product.management.infrastructure;

import kr.co.hanbit.product.management.domain.Product;
import kr.co.hanbit.product.management.domain.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;

@Repository
@Profile("prod") //사용자에게 서비스가 제공(production)될 때 사용
//@Profile 지정 시 특정 환경에서 특정 클래스의 빈이 생성
public class DatabaseProductRepository implements ProductRepository{

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    //JdbcTemplate 의존성을 NamedParameterJdbcTemplate으로 변경
    //sql 쿼리를 보낼 때 (물음표로 매개변수를 매핑하지 않고) 매개변수 이름을 통해 sql 쿼리와 값을 매핑해줌.

    @Autowired
    public DatabaseProductRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate){
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate; //데이터베이스에 SQL을 전송하기 위해 위 의존성 필요
        //JdbcTemplate보다 JPA를 실무에서 더 많이 사용하나, SQL의 이해도를 올리고 추후에 사용하느 것이 좋음.
    }

    public Product add(Product product){
        //상품 추가 기능 구현 메소드

        KeyHolder keyHolder = new GeneratedKeyHolder();
        //응답에 id를 채워주기위해 사용
        //update의 매개변수로 넘겨줘 응답이 올때 KeyHolder에 id가 담겨옴, 그리고 해당 id를 Product에 지정해줌.

        SqlParameterSource namedParameter = new BeanPropertySqlParameterSource(product);
        //product의 getter를 통해 sql 쿼리의 매개변수를 매핑시켜주는 BeanPropertySqlParameterSource 객체 사용
        //즉, getter 메소드를 직접 사용하지 않고 namedParameter를 인자로 넘겨주기만 하면됨.

        namedParameterJdbcTemplate
                .update("INSERT INTO products (name, price, amount) VALUES (:name, :price, :amount)",
                        namedParameter, keyHolder);

        Long generatedId = keyHolder.getKey().longValue();
        product.setId(generatedId);

        return product;
    }

    public Product findById(Long id){
        //상품 번호로 조회 기능 구현 메소드
        SqlParameterSource namedParameter = new MapSqlParameterSource("id", id);
        //SqlParameterSource로 id를 매핑해주기 위해  MapSqlParameterSource 사용
        //Key-Value 형태를 매핑하는 경우 사용 (BeanPropertySqlParameterSource는 객체를 매핑)

        //3가지 인자 받아야함.
        Product product = namedParameterJdbcTemplate.queryForObject(
                "SELECT id, name, price, amount FROM products WHERE id=:id", //1. sql 쿼리 받기
                namedParameter, //2. namedParamenter 받기
                new BeanPropertyRowMapper<>(Product.class) //3. BeanPropertyRowMapper 받기 (조회된 상품 정보를 Product 인스턴스로 변환)
                //BeanPropertyRowMapper가 변환을 수행하기위해 2가지 과정 필요
                //1. 인자가 없는 product 생성자 필요 2. setter로 각 필드 초기화 필요
        );
        return product;
    }

    public List<Product> findAll(){
        // 전체목록 조회에는 매개변수가 필요없기에 MapSqlParameterSource 필요x
        List<Product> products = namedParameterJdbcTemplate.query(
                "SELECT * FROM products",
                new BeanPropertyRowMapper<>(Product.class)
        );
        return products;
    }

    public List<Product> findByNameContaining(String name){
        //상품이름 문자열로 검색 기능 구현 메소드
        SqlParameterSource namedParameter = new MapSqlParameterSource("name", "%"+name+"%");
        //검색하려는 name 매핑
        //"&"를 앞뒤로 붙여 name이 앞,중간,뒤에 포함되는 경우도 검색 가능 (안붙이면 정확히 일치하는 값 검색)
        List<Product> products = namedParameterJdbcTemplate.query(
                "SELECT * FROM products WHERE name LIKE :name",
                namedParameter,
                new BeanPropertyRowMapper<>(Product.class)
        );
        return products;
    }

    public Product update(Product product) {
        SqlParameterSource namedParameter = new BeanPropertySqlParameterSource(product);

        namedParameterJdbcTemplate.update("UPDATE products SET name=:name, price=:price, amount=:amount WHERE id=:id",
                namedParameter);

        return product;
    }

    public void delete(Long id) {
        SqlParameterSource namedParameter = new MapSqlParameterSource("id", id);

        namedParameterJdbcTemplate.update(
                "DELETE FROM products WHERE id=:id",
                namedParameter
        );
    }
}
