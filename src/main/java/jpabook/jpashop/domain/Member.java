package jpabook.jpashop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Member {

    @Id
    @GeneratedValue
    @Column(name="member_id")
    private Long id;

    @NotEmpty
    private String name;

    @Embedded // 내장타입을 포함한것을 뜻한다.
    private Address address;

    // 오더와 멤버는 다대일 관계, 다대다 관계 일 경우 하나의 주인을 정해주어야한다!! 주인님 찾아라
    // 엔티티직접노출:양방향연관 관계시에 하나는 json ignore를 해줘야아한다.
    @JsonIgnore
    @OneToMany(mappedBy =  "member")  // 1:다 하나의 멤버가 여러개의 주문
    private List<Order> orders = new ArrayList<>();
}

