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

    @Embedded
    private Address address;

    @JsonIgnore //회원조회시 회원정보만 출력하고 싶은데 주문정보까지 같이 포함될경우 ignore를 통해 무시함
    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();
}
