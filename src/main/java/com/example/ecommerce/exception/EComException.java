package com.example.ecommerce.exception;

import lombok.Data;

@Data
public class EComException extends BaseException {
    private String principal;

    public EComException() {
    }

    public EComException(String errorCode) {
        super(errorCode);
    }

    public EComException(String errorCode, String desc) {
        super(errorCode, desc);
    }

    public EComException(String errorCode, Object tag) {
        super(errorCode, tag);
    }
}
