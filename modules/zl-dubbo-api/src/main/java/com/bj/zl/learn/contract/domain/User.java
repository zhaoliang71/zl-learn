/**
 * create by Administrator
 * 2019-09-24
 */
package com.bj.zl.learn.contract.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class User implements Serializable {

    private static final long serialVersionUID = -1831393877089600163L;
    private Integer id;

    private String name;

    private int age;
}
