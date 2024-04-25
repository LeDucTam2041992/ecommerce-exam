package com.example.ecommerce.exception;

public interface ErrorCode {
    String INTERNAL_SERVER_ERROR = "ECOM-10-001";
    String PRODUCT_NOT_FOUND = "ECOM-10-002";
    String ORDER_NOT_FOUND = "ECOM-10-003";
    String PRODUCT_QUANTITY_NOT_ENOUGH = "ECOM-10-004";
    String ORDER_CUSTOMER_NAME_INVALID = "ECOM-10-005";;
}
