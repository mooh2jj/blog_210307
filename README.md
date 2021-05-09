# blog_210307
데어프로그래밍 블로그프로젝트 다시시작!

## 시작을 알리는 데어프로그래밍 프로그램 blog로 스프링부트를 알수 있데 되었다.

이런걸 배움
```java
package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String username;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "member")     // 읽기 전용됨
    private List<Order> orders = new ArrayList<>();

}

```
