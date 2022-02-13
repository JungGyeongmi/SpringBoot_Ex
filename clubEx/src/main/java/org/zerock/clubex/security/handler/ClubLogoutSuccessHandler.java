package org.zerock.clubex.security.handler;

import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Log4j2
public class ClubLogoutSuccessHandler implements LogoutSuccessHandler {

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication auth)
        throws IOException, ServletException {
        log.warn("logoutSuccess...");
        log.info("auth.getDetails : "+auth.getDetails());
        if(auth != null && auth.getDetails() !=null) {
            try {
                request.getSession().invalidate();
                log.info("logout invalidate......");
            } catch (Exception e) {e.printStackTrace();}
        }
        response.setStatus(HttpServletResponse.SC_OK);
        response.sendRedirect(request.getContextPath()+"/member/login");
    }
}