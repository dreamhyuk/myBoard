package study.my_board.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Role {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long id;

    @JsonIgnore
    @OneToMany(mappedBy = "role")
    private List<MemberRole> memberRoles = new ArrayList<>();

    private String name; //역할 이름(ADMIN, USER, etc.)

    public Role(String role_user) {
        this.name = role_user;
    }
}
