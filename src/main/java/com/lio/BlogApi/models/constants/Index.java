package com.lio.BlogApi.models.constants;

public class Index {

        public static Integer[] NUMBERS = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };
        public static char[] CHARACTERS = {
                        'a', 'b', 'c', 'd', 'e',
                        'f', 'g', 'h', 'i', 'j',
                        'k', 'l', 'm', 'n', 'o',
                        'p', 'q', 'r', 's', 't',
                        'u', 'v', 'w', 'x', 'y',
                        'z'
        };

        public static StringBuffer EMAIL_VERIFICATION_LINK = new StringBuffer(
                        "http://localhost:8080/api/v1/verify-account");

        public static String EMAIL_TEMPLATE_SOURCE_PATH = "src/main/resources/static/mail-templates/";

}
