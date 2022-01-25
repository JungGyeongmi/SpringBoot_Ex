package org.zerock.mreview.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "moive")
public class MovieImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long inum; // PK

    private String uuid; // UUID

    private String imgName;

    private String path;

    @ManyToOne(fetch= FetchType.LAZY) // LAZY 타입으로 연결
    private Movie movie;
}
