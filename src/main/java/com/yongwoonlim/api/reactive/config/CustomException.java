package com.yongwoonlim.api.reactive.config;

public class CustomException  extends RuntimeException{
    private static final long serialVersionUID = -5970845585469454688L;

    public CustomException(String type) {
        System.out.println(type + ": throw CustomException!");
    }
}
