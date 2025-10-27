package edu.northwestu.sampleproject.util;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

/**
 * Service to send Emails
 */
@Component
public class EmailService {

    public static final String EMAIL_DEFAULT_FROM = "noreply@northwestu.edu";

    private JavaMailSender emailSender;

    @Qualifier("email_template_engine")
    private TemplateEngine templateEngine;


    public EmailService(TemplateEngine templateEngine, JavaMailSender emailSender) {
        this.templateEngine = templateEngine;
        this.emailSender = emailSender;
    }

    /**
     * @param to               Email To
     * @param subject          Email Subject
     * @param templateFilename Email Body Template
     */
    @Async
    public void sendTemplateEmail(final String to, final String subject, final String templateFilename, final Context templateVariableContext) throws MessagingException {
        final MimeMessage mimeMessage = this.emailSender.createMimeMessage();
        final MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
        message.setSubject(subject);
        message.setFrom(EMAIL_DEFAULT_FROM);
        message.setTo(to);
        final String htmlContent = this.templateEngine.process("mail/html/" + templateFilename, templateVariableContext);
        message.setText(htmlContent, true);
        this.emailSender.send(mimeMessage);

    }

}
