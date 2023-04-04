package com.lio.BlogApi.services.common.email;

import com.lio.BlogApi.models.dtos.custom.EmailTemplate;

public interface EmailService {

    boolean sendEmail(String mailTo, EmailTemplate emailTemplate);

}
