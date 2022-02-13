package org.zerock.clubex.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClubMemberDTO {
  private String username;
  private String email;
  private String name;
  private String password;
  private boolean fromSocial;
  private LocalDateTime modDate;
  private LocalDateTime regDate;

  @Builder.Default
  private Set<String> roleSet = new HashSet<>();

}

/* 
 @Data
 @Builder
 @NoArgsConstructor
 @AllArgsConstructor
 public class ClubMemberDTO {
     private String email;
     private String password;
     private String name;
    private boolean fromSocial;
    private LocalDateTime regDate, modDate;
 }
*/
