package com.epam.flyingdutchman.controller.commands.util;

import com.epam.flyingdutchman.util.resources.ConfigurationManager;
import jakarta.servlet.http.Part;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;


public class ImageProcessor {
    private static final Logger logger = LogManager.getLogger();
    private static final String IMAGE_FILE_EXPANSION = ".jpg";

    private ImageProcessor() {
    }

    public static String generateImageFilename() {
        return UUID.randomUUID().toString() + IMAGE_FILE_EXPANSION;
    }

    public static String uploadFile(Part part, String uniqueFilename) {
        String uploadDir = ConfigurationManager.getProperty("dir.uploads");
        try {
            Path path = Paths.get(uploadDir, uniqueFilename);
            part.write(path.toString());
            return ConfigurationManager.getProperty("dir.relativeUploads") + uniqueFilename;
        } catch (IOException e) {
            logger.error("The image didn't write on folder" + e.getMessage());
        }
        return "";
    }
}
