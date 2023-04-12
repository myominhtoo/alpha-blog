package com.lio.BlogApi.utils;

import org.springframework.web.multipart.MultipartFile;

import static com.lio.BlogApi.models.constants.Index.ALLOWED_IMAGE_EXTENSIONS;
import static com.lio.BlogApi.models.constants.Index.MAX_IMAGE_SIZE;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class FileUtil {

    public static boolean isValidFile(MultipartFile file) throws IOException {
        return ALLOWED_IMAGE_EXTENSIONS.contains(file.getContentType()) &&
                file.getBytes().length <= MAX_IMAGE_SIZE;
    }

    public static boolean uploadFile(MultipartFile file, String fileName, String pathPostfix) {
        try {
            String location = LinkUtil.getAssetsPath(pathPostfix);
            file.transferTo(new File(location + "" + fileName));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static byte[] loadFile(String fileName, String pathPostfix) {
        byte[] result = null;
        try {
            String location = LinkUtil.getAssetsPath(pathPostfix);
            BufferedReader bufferedReader = new BufferedReader(
                    new FileReader(new File(location + "" + fileName)));
            result = bufferedReader.readLine().getBytes();
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

}
