package com.dv.reactivespringboot.udemyreactivespringboot.playground.flux;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode(callSuper = true)
@Data
public class CustomException extends Throwable {

    private String message;

    public CustomException(Throwable e) {
        this.message = e.getMessage();
    }
}
