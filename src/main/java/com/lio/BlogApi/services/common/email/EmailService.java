package com.lio.BlogApi.services.common.email;

import javax.mail.MessagingException;

import com.lio.BlogApi.models.dtos.custom.EmailTemplate;

public interface EmailService {

    boolean sendEmail(EmailTemplate emailTemplate) throws MessagingException;

}
