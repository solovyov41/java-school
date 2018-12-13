package com.tlg.web.config;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import java.nio.charset.StandardCharsets;

public class TlgWebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
    /**
     * The url pattern of the filter mapping.
     */
    private static final String URL_FILTER_PATTERN = "/*";

    /**
     * The URL patterns of the servlet mapping.
     */
    private static final String URL_PATTERN = "/";

    @Nullable
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{TlgWebAppConfig.class};
    }

    @Nullable
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[]{};
    }

    @NonNull
    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

    @Override
    public void onStartup(@NonNull ServletContext servletContext) throws ServletException {
        // Create the dispatcher servlet's Spring application context
        AnnotationConfigWebApplicationContext servletAppContext = new AnnotationConfigWebApplicationContext();
        servletAppContext.register(TlgWebAppConfig.class);
//        servletAppContext.setServletContext(servletContext);

        servletContext.addListener(new ContextLoaderListener(servletAppContext));

        ServletRegistration.Dynamic dispatcher = servletContext.addServlet("DispatcherServlet",
                new DispatcherServlet(servletAppContext));
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping(URL_PATTERN);

        FilterRegistration.Dynamic encodingFilter = servletContext.addFilter("encoding-filter",
                new CharacterEncodingFilter());
        encodingFilter.setInitParameter("encoding", StandardCharsets.UTF_8.name());
        encodingFilter.setInitParameter("forceEncoding", "true");
        encodingFilter.addMappingForUrlPatterns(null, true, URL_FILTER_PATTERN);
    }
}
