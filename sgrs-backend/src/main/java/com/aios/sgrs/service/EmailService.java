
package com.aios.sgrs.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import freemarker.template.Configuration;
import freemarker.template.Template;

import javax.mail.internet.MimeMessage;
import java.time.Year;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Service
public class EmailService {

    private final Configuration freemarkerConfig;

    @Value("${spring.mail.username:default@domain.com}")
    private String fromEmail;

    public EmailService(Configuration freemarkerConfig) {
        this.freemarkerConfig = freemarkerConfig;
    }

    private JavaMailSender createMailSender(String host, int port, String username, String password) {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(host);
        mailSender.setPort(port);
        mailSender.setUsername(username);
        mailSender.setPassword(password);

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "false");

        return mailSender;
    }

    @Async
    public void sendEmail(String toEmail, String subject, String templateName, Map<String, Object> model,
                          String host, int port, String username, String password) throws Exception {
        JavaMailSender mailSender = createMailSender(host, port, username, password);

        Template template = freemarkerConfig.getTemplate(templateName);
        String body = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setTo(toEmail);
        helper.setSubject(subject);
        helper.setFrom(username); // Se usa el remitente dinámico
        helper.setText(body, true);

        mailSender.send(mimeMessage);
    }

    @Async
    public void enviarCorreoUsuario(String email, String nombre, String contrasena, String empresa, String link,
                                    String host, int port, String username, String password) throws Exception {
        Map<String, Object> cuerpo = new HashMap<>();
        cuerpo.put("nombre", nombre);
        cuerpo.put("email", email);
        cuerpo.put("contrasena", contrasena);
        cuerpo.put("link", link);
        cuerpo.put("empresa", empresa);
        cuerpo.put("year", Year.now().getValue());

        String subject = "Bienvenida a Nuestro Servicio";
        String template = "emails/registro-usuario.ftl";

        sendEmail(email, subject, template, cuerpo, host, port, username, password);
    }
}
