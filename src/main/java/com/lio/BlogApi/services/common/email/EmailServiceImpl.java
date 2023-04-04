package com.lio.BlogApi.services.common.email;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.lio.BlogApi.models.dtos.custom.EmailTemplate;

import lombok.AllArgsConstructor;

@Service("emailService")
@AllArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;

    @Override
    public boolean sendEmail(EmailTemplate emailTemplate) throws MessagingException {
        try {
            MimeMessage mimeMessage = this.javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);

            mimeMessageHelper.setSubject(emailTemplate.getSubject());
            mimeMessageHelper.setFrom("noreplyalphablog@gmail.com");
            mimeMessageHelper.setText(emailTemplate.getContent(), true);
            mimeMessageHelper.setTo(emailTemplate.getMailTo());
            mimeMessage.setSentDate(emailTemplate.getCreatedDate());

            this.javaMailSender.send(mimeMessage);
            return true;
        } catch (MessagingException e) {
            return false;
        }
    }

}
