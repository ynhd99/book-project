package com.example.room.entity;

import lombok.Data;

/**
 * @author yangna
 * @date 2019/3/19
 */
@Data
public class Student {
    public Student(String name, String cla, String college) {
        this.name = name;
        this.cla = cla;
        this.college = college;
    }

    private String name;
    private String cla;
    private String college;
}
