package kr.co.hanbit.product.management.domain;

import kr.co.hanbit.product.management.presentation.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;

public class EntityNotFoundException extends RuntimeException{
    //레포지토리의 findById 메소드에서 id에 해당하는 Product 객체를 찾지 못할 때 발생하는 예외를 처리
    //위 예외 클래스는 모든 계층에 의해 사용될 수 있기에 도메인 계층에 위치시킴

    public EntityNotFoundException(String message){
        super(message);
    }


}
