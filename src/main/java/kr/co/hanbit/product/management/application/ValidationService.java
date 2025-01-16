package kr.co.hanbit.product.management.application;

import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service //빈으로 등록
@Validated //valid 붙은 메소드 매개변수 유효성 검사
public class ValidationService {
    public <T> void checkValid(@Valid T validationTarget){
        //인자를 담아 호출만으로도 유효성 검증이 이뤄지기에 아무것도 없음.
    }
}
