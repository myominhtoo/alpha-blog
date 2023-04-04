package com.lio.BlogApi.utils;

import static com.lio.BlogApi.models.constants.Index.EMAIL_VERIFICATION_LINK;

import com.lio.BlogApi.models.enums.SecretWord;;

public class LinkUtil {

    public static String getVerificationLink(
            String accountEmail,
            String code) {
        EMAIL_VERIFICATION_LINK
                .append("?" + SecretWord.EMAIL + "=" + accountEmail + "&" + SecretWord.CODE + "=" + code);
        return EMAIL_VERIFICATION_LINK.toString();
    }

}
