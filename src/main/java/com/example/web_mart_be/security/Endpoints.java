package com.example.web_mart_be.security;

public class Endpoints {
    public static final String font_end_host = "http://localhost:3000";
    public static final String[] PUBLIC_GET = {
            "/products",
            "/products/**",
            "/product-type",
            "/product-type/**",
            "/images",
            "/images/**",
            "/cart-items",
            "/cart-items/**",
            "/users/**",
    };
    public static final String[] PUBLIC_POST = {
            "/user/register",
            "/user/authenticate",
            "/cart-item/add-item",
            "/order/**",
            "/review/add-review/**",
            "/feedback/add-feedback",
            "/favorite-book/add-book",
            "/vnpay/create-payment/**",
            "/review/get-review/**",
    };

    public static final String[] PUBLIC_PUT = {
            "/cart-item/**",
            "/cart-items/**",
            "/users/**",
            "/user/update-profile",
            "/user/change-password",
            "/user/forgot-password",
            "/user/change-avatar",
            "/order/update-order",
            "/order/cancel-order",
            "/review/update-review"
    };

    public static final String[] PUBLIC_DELETE = {
            "/cart-items/**",
            "/favorite-book/delete-book",
    };

    public static final String[] ADMIN_ENDPOINT = {
            "/users",
            "/users/**",
            "/cart-items/**",
            "/books",
            "/books/**",
            "/book/add-book/**",
            "/user/add-user/**",
            "/feedbacks/**",
            "/cart-items/**",
            "/cart-item/**",
            "/orders/**",
            "/order/**",
            "/order-detail/**",
            "/roles/**",
            "/favorite-book/**",
            "/favorite-books/**",
            "/review/**",
            "/book/get-total/**",
            "/feedbacks/search/countBy/**",
            "/**",

    };
}
