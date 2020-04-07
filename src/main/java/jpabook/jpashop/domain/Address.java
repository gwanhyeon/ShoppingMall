package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;

@Embeddable         // 내장타입을 포함한것을 뜻한다.
@Getter
@Setter
public class Address {

    private String city;
    private String street;
    private String zipcode;

}
