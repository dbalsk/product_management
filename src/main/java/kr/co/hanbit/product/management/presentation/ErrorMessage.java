package kr.co.hanbit.product.management.presentation;

import java.util.List;

public class ErrorMessage {
    //에러메세지를 더 명확하게 나타내기 위해 직접 클래스 정의하여 사용
    private List<String> errors;

    public ErrorMessage(List<String> errors){
        this.errors = errors;
    }

    public List<String> getErrors(){
        return errors;
    }

}
