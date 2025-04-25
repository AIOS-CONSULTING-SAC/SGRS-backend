package com.aios.sgrs.service;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import freemarker.template.Configuration;
import freemarker.template.Template;

import java.time.Year;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class EmailService {

    private final JavaMailSender mailSender;
    private final Configuration freemarkerConfig;

    @Value("${spring.mail.username}")
    private String fromEmail;

    public EmailService(JavaMailSender mailSender, Configuration freemarkerConfig) {
        this.mailSender = mailSender;
        this.freemarkerConfig = freemarkerConfig;
    }

    @Async
    public void sendEmail(String toEmail, String subject, String templateName, Map<String, Object> model) throws Exception {
        // Cargar la plantilla
        Template template = freemarkerConfig.getTemplate(templateName);

        // Procesar la plantilla
        String body = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);

        // Crear el mensaje
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setTo(toEmail);
        helper.setSubject(subject);
        helper.setFrom(fromEmail);
        helper.setText(body, true); // true para marcarlo como HTML

        // Enviar el correo
        mailSender.send(mimeMessage);
    }

    @Async
    public void enviarCorreoUsuario(String email, String nombre, String contrasena) throws Exception {
        Map<String, Object> cuerpo = new HashMap<>();
        cuerpo.put("nombre", nombre);
        cuerpo.put("email", email);
        cuerpo.put("contrasena", contrasena);
        cuerpo.put("year", Year.now().getValue());
        String subject;
        String template;

        subject = "Bienvenida a Nuestro Servicio";
        template = "registro-usuario.ftl";

        sendEmail(email, subject, template, cuerpo);
    }
}
