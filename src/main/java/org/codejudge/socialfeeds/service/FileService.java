package org.codejudge.socialfeeds.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {

    public String uploadToS3(MultipartFile multipartFile) throws Exception;
    public void downloadFromS3(String fileKey, String destination) throws Exception;
}
