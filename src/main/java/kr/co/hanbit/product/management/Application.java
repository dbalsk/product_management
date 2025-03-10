package kr.co.hanbit.product.management;

//import org.modelmapper.ModelMapper;
//import org.modelmapper.config.Configuration;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;


import javax.sql.DataSource; //데이터 베이스
import java.sql.Connection;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {

		SpringApplication.run(Application.class, args);
	}

/*	1/22 ModelMapper 제거
	@Bean
	//ModelMapper 클래스의 인스턴스 생성하고 빈으로 등록하는 코드.
	public ModelMapper modelMapper(){
		ModelMapper modelMapper = new ModelMapper();

		//setter없이도 Product와 ProductDto를 변환하기위해 추가.
		//ModelMapper가 private인 필드에 리플렉션 API로 접근하여 변환할 수 있게됨.
		modelMapper.getConfiguration()
				.setFieldAccessLevel(Configuration.AccessLevel.PRIVATE)
				.setFieldMatchingEnabled(true);

		return modelMapper;
	}*/

	@Bean
	@Profile("prod") //데이터베이스 사용 시에만 수행
	public ApplicationRunner runner(DataSource dataSource) {
		return args -> {
			//실행할 코드
			Connection connection = dataSource.getConnection();
		};
	}
}
