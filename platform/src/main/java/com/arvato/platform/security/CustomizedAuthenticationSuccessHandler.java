package com.arvato.platform.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: pang008
 * Date: 3/4/14
 * Time: 2:30 PM
 * To change this template use File | Settings | File Templates.
 */
public class CustomizedAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private static Logger logger = LoggerFactory.getLogger(CustomizedAuthenticationSuccessHandler.class);

//    @Autowired
//    private OmsCredentialService credentialService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws ServletException, IOException {
        logger.debug("determine to clear login exceptions of current account.");
//        credentialService.markLoginExceptions(authentication.getPrincipal().toString(), "0");
        logger.debug("goto target url:/determine");
        setDefaultTargetUrl("/determine");
        super.onAuthenticationSuccess(request, response, authentication);
    }
}
