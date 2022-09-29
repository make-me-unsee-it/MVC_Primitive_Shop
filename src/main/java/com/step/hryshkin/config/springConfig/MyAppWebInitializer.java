package com.step.hryshkin.config.springConfig;

import com.step.hryshkin.config.hibernateConfig.RootConfig;
import com.step.hryshkin.config.securityConfig.SecurityConfig;
import com.step.hryshkin.config.thymeleaf.SpringConfig;
import org.h2.server.web.WebServlet;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

/**
 * @author dshargaev
 */

public class MyAppWebInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected void customizeRegistration(ServletRegistration.Dynamic registration) {
        registration.setInitParameter("throwExceptionIfNoHandlerFound", "true");
    }

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{SecurityConfig.class, RootConfig.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[] {SpringConfig.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[] {"/"};
    }


    //don't touch!
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        ServletRegistration.Dynamic servletRegistration = servletContext.addServlet("h2-console", new WebServlet());
        servletRegistration.addMapping("/h2/*");
        super.onStartup(servletContext);
    }
}
