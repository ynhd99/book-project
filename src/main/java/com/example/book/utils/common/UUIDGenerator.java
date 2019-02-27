package com.example.book.utils.common;

import java.util.UUID;

/**
 *  uuid生成
 *  @author chuanli shen
 */
public class UUIDGenerator {

    public static String getUUID(){
        return UUID.randomUUID().toString();
    } 
}
