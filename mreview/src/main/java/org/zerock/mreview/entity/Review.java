package org.zerock.mreview.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"movie", "member"})
public class Review extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reivewernum;

    @ManyToOne // review를 기준 : 영화 하나에 리뷰 여러개
    private Movie movie;

    @ManyToOne // review기준 : 여러 리뷰를 멤버 한명이 사용 가능
    private  Member member;

    private int grade;
    private String text;

}
