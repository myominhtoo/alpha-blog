package com.lio.BlogApi.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import static com.lio.BlogApi.models.constants.Index.EMAIL_TEMPLATE_SOURCE_PATH;;

public class TemplateUtil {

    public static String getEmailTemplate(String templateName) throws IOException {
        BufferedReader reader = new BufferedReader(
                new FileReader(new File(EMAIL_TEMPLATE_SOURCE_PATH + "" + templateName)));
        String content = reader.readLine();
        reader.close();
        return content;
    }

}
