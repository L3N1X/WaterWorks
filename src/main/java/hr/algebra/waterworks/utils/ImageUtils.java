package hr.algebra.waterworks.utils;

import org.apache.commons.io.FilenameUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

public class ImageUtils {
    private ImageUtils(){};

    public static String saveImage(MultipartFile multipartFile, String saveFolderPath) throws IOException {
        byte[] imageBytes = multipartFile.getBytes();
        String extention = FilenameUtils.getExtension(multipartFile.getResource().getFilename());
        String saveImageName = UUID.randomUUID().toString() + '.' + extention;
        String saveImagePath = saveFolderPath + '/' + saveImageName;
        FileOutputStream fos = new FileOutputStream(saveImagePath);
        fos.write(imageBytes);
        return saveImageName;
    }
}
