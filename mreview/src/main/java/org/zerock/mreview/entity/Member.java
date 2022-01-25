package org.zerock.mreview.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name ="m_member")
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mid;

    private String id;
    private String pw;
    private String email;
    private String nickname;

}
