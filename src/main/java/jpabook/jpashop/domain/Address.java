package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;

@Embeddable         // 내장타입을 포함한것을 뜻한다.
@Getter
public class Address {

    private String city;
    private String street;
    private String zipcode;

    // 함수로 생성시키면 안된다.
    protected Address(){

    }

    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}

