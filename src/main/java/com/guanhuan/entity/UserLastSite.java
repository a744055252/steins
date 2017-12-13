package com.guanhuan.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Id;

@Setter
@Getter
@ToString
//@Entity
public class UserLastSite {

    /** id */
    @Id
    private long id;

    /** 用户id */
    private long userId;


}
