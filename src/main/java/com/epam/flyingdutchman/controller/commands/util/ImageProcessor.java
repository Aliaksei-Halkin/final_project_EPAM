package com.epam.flyingdutchman.controller.commands.util;

import com.epam.flyingdutchman.util.resources.ConfigurationManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import static com.epam.flyingdutchman.util.constants.Context.REQUEST_IMAGE;

/**
 * The class represents actions with image such as upload image,generate unique name.
 *
 * @author Aliaksei Halkin
 * @version 1.0
 */
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
        String targetDir = ConfigurationManager.getProperty("dir.uploadsTarget");
        try {
            Path pathUpload = Paths.get(uploadDir, uniqueFilename);
            Path pathTarget = Paths.get(targetDir, uniqueFilename);
            part.write(pathUpload.toString());
            part.write(pathTarget.toString());
            return ConfigurationManager.getProperty("dir.relativeUploads") + uniqueFilename;
        } catch (IOException e) {
            logger.error("The image didn't write on folder" + e.getMessage());
        }
        return "";
    }

    public static String uploadImage(HttpServletRequest request) {
        try {
            String uniqueFilename = generateImageFilename();
            return uploadFile(request.getPart(REQUEST_IMAGE), uniqueFilename);
        } catch (IOException | ServletException e) {
            logger.error("Error uploading image", e);
        }
        return "";
    }
}
