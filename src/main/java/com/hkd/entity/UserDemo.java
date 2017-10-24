package com.hkd.entity;

/**
 * Created by 1 on 2017-08-15.
 */

import javax.persistence.*;

/**
 * Created by 1 on 2017-08-15.
 * 测试用户实体
 */
@Table(name = "user_demo")
public class UserDemo {

    /**
     * 主键id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 用户姓名
     */
    @Column(name = "name")
    private String name;

    /**
     * 年龄
     */
    @Column(name = "age")
    private Integer age;

    /**
     * 备注
     */
    @Column(name = "rank")
    private String rank;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    @Override
    public String toString() {
        return "UserDemo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", rank='" + rank + '\'' +
                '}';
    }
}