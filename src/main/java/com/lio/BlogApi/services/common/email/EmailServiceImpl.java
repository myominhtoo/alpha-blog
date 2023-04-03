package com.lio.BlogApi.services.common.email;

import org.springframework.stereotype.Service;

import com.lio.BlogApi.models.dtos.custom.EmailTemplate;

@Service("emailService")
public class EmailServiceImpl implements EmailService {

    @Override
    public boolean sendEmail(EmailTemplate emailTemplate) {
        return false;
    }

}
