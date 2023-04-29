package com.lio.BlogApi;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Calendar;

import com.lio.BlogApi.services.common.jwtToken.JwtTokenService;
import com.lio.BlogApi.services.common.jwtToken.JwtTokenServiceImpl;
import com.lio.BlogApi.utils.LinkUtil;
import com.lio.BlogApi.utils.TextUtil;

import static com.lio.BlogApi.models.constants.Index.CATEGORY_IMAGE_PATH;
import static com.lio.BlogApi.models.constants.Index.BLOG_IMAGE_PATH;

public class Test {
    public static void main(String[] args) throws IOException {

        // BufferedReader bufferedReader = new BufferedReader(
        // new FileReader(new
        // File("src/main/resources/static/mail-templates/mail-verification.txt")));
        // try {
        // System.out.println(bufferedReader.readLine().replace("[[name]]", "Myo Min
        // Htoo"));
        // } catch (Exception e) {
        // // TODO Auto-generated catch block
        // e.printStackTrace();
        // }
        // }
        // bufferedReader.close();
         //JwtTokenService tokenService = new JwtTokenServiceImpl();
         //System.out.print(tokenService.getTokenPayload(null));
         //System.out.print(tokenService.getTokenPayload(null));
        // System.out.println(TextUtil.makeKeyword("react native is very hot now"));
        // System.out.print(System.getProperties().getProperty("java.class.path").split(";")[0].replace("target\\classes",
        // "assets"));
        // System.out.println(new
        // File(LinkUtil.getAssetsPath(CATEGORY_IMAGE_PATH)).isDirectory());
        System.out.println(new StringBuffer().toString().isEmpty());

        System.out.println(args[0]);
    }
}
