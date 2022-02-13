package org.zerock.clubex.entity;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@Table(name="CLUB_MEMBER_EX")
public class ClubMember extends BaseEntity {

    @Id
    private String email;
    private String password;
    private String name;
    private boolean fromSocial;

    // jpa에서 연결되는 객체가 collection일 경우에
    @ElementCollection(fetch = FetchType.LAZY)
    @Builder.Default
    private Set<ClubMemberRole> roleSet = new HashSet<>();
    // set 의 특징 : 중복허락 x, 순서 O
    // club role의 상수를 가져와서 list로 안 넣는거는 중복을 피하기 위함임
    public void addMemberRole(ClubMemberRole clubMemberRole) {
        roleSet.add(clubMemberRole);
    }
    public void changePassword(String password){
        this.password = password;
    }

}