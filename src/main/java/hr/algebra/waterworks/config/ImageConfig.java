package hr.algebra.waterworks.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ImageConfig {

    @Value("${images.absolute.folder-path}")
    private String imageBaseFolder;

    public String getImageBaseFolder(){
        return this.imageBaseFolder;
    }
}
