package com.lio.BlogApi.utils;

import java.util.Random;

import static com.lio.BlogApi.models.constants.Index.NUMBERS;
import static com.lio.BlogApi.models.constants.Index.CHARACTERS;

public class GeneratorUtil {

    public static String generateId(
            String prefix,
            Integer bound) {
        StringBuffer generatedId = new StringBuffer(prefix);

        Random random = new Random();
        boolean isCharTurn = random.nextBoolean();

        for (int i = 0; i < bound; i++) {
            if (isCharTurn) {
                boolean isUpper = random.nextBoolean();
                int randomId = random.nextInt(CHARACTERS.length - 1);
                generatedId.append(
                        isUpper
                                ? Character.toUpperCase(CHARACTERS[randomId])
                                : CHARACTERS[randomId]);
                isCharTurn = random.nextBoolean();
                continue;
            }
            int randomId = random.nextInt(NUMBERS.length - 1);
            generatedId.append(
                    NUMBERS[randomId]);
            isCharTurn = random.nextBoolean();
        }

        return generatedId.toString();
    }

}
