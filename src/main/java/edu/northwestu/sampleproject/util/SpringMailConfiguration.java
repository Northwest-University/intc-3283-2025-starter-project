package edu.northwestu.sampleproject.util;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;
import java.util.HashSet;
import java.util.List;

@Configuration
public class SpringMailConfiguration {
    private static final int HTML_TEMPLATE_RESOLVER_ORDER = 1;
    private static final String EMAIL_TEMPLATE_ENCODING = "UTF-8";

    @Bean(name = "email_template_engine")
    public TemplateEngine emailTemplateEngine() {
        final SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.addTemplateResolver(this.htmlTemplateResolver());
        return templateEngine;
    }

    private ITemplateResolver htmlTemplateResolver() {
        final ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setOrder(HTML_TEMPLATE_RESOLVER_ORDER);
        templateResolver.setResolvablePatterns(new HashSet<>(List.of("mail/html/*")));
        templateResolver.setPrefix("/templates/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setCharacterEncoding(EMAIL_TEMPLATE_ENCODING);
        templateResolver.setCacheable(true);
        return templateResolver;
    }
}
