package org.zerock.clubex.security.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

@Log4j2
@Getter
@Setter
@ToString
public class ClubAuthMemberDTO extends User implements OAuth2User { 
    /*
     User를 상속받았기 때문에 
     로그인 인증을 받을 때 ClubAuthMemberDTO가 중간 전달자 역할을 하고
     ClubAuthMemberDTO: JPA로부터 찾은 회원정보와 인증 결과를 담기 위한 클래스
    
     세션은 클라이언트와 서버가 연결되어있다는 끈(약속) 서버가 다운되면 끝나는 것임
     로그인 후 웹 브라우저를 완벽히 종료하면 세션이 날라감(로그인 했다는 증빙이다)
     세션이 연결되어있는 경우에는 세션을 기반으로 전달함  
    */
    private String email;
    private String password;
    private String name;
    private boolean fromSocial;
    private Map<String, Object> attr; // social에서 오는 oAuth 정보
    
    /* 
        생성자가 중요함
        DB로부터 사용자를 초기화하는 생성자1
        첫 번째 생성자 (전달하는 파라미터의 갯수가 다름)
     */
    public ClubAuthMemberDTO(String username, String password, boolean fromSocial,
                             Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.email = username;
        this.fromSocial = fromSocial;
        this.password = password;
        log.info("ClubAuthMemberDTO 생성자 실행");
    }

    /* 
       oAuth로부터 사용자를 초기화하는 생성자2
       Map은 구글로부터 넘어오는 정보를 받기 위한 변수다
    */
    public ClubAuthMemberDTO(String username, String password, boolean fromSocial,
                             Collection<? extends GrantedAuthority> authorities,
                             Map<String, Object> attr) {
        this(username, password, fromSocial, authorities);
        // attr은 구글로부터 넘어온 속성을 의미한다
        this.attr = attr;
    }

    @Override
    public Map<String, Object> getAttributes() {
       return this.attr;
    }

    public void changePassword(String password){
        this.password = password;
        log.info("ClubAuthMemberDTO password change: "+this.password);
    }
}