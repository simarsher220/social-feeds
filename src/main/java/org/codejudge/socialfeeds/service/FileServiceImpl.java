package org.codejudge.socialfeeds.service;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.extern.slf4j.Slf4j;
import org.codejudge.socialfeeds.common.CoreProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

@Service
@Slf4j
public class FileServiceImpl implements FileService {

    @Autowired
    private CoreProperties coreProperties;

    @Override
    public String uploadToS3(MultipartFile multipartFile) throws Exception {
        File file = convertMultiPartToFile(multipartFile);
        ObjectMetadata data = new ObjectMetadata();
        data.setContentType(multipartFile.getContentType());
        data.setContentLength(multipartFile.getSize());
        log.info("uploading the multipart file to S3 bucket...");
        String uploadUrl = null;
        if (isValid(file)) {
            log.info("uploading file with name: " + file.getName() + " to the bucket : " + coreProperties.getBucketName());
            AmazonS3 s3Client = getS3Client();
            try {
                s3Client.putObject(new PutObjectRequest(coreProperties.getBucketName(), file.getName(), file).withCannedAcl(CannedAccessControlList.PublicRead));
                uploadUrl = String.valueOf(s3Client.getUrl(coreProperties.getBucketName(), file.getName()));
                file.delete();
            } catch (Exception e) {
                throw new Exception("error uploading the file : ", e);
            }
            return uploadUrl;
        }
        log.error("zip file is invalid! file: " + file);
        return null;
    }

    private String generateFileName(MultipartFile multiPart) {
        return new Date().getTime() + "-" + multiPart.getOriginalFilename().replace(" ", "_");
    }

    @Override
    public void downloadFromS3(String fileKey, String destination) throws Exception {
//        log.info("downloading the file from S3 bucket...");
//        AWSCredentials credentials = new BasicAWSCredentials(coreProperties.getAccessKey(), coreProperties.getSecretKey());
//        AmazonS3 s3client = getS3Client();
//        S3Object object = null;
//        object = s3client.getObject(new GetObjectRequest(coreProperties.getBucketName(), fileKey));
//        log.info("file downloaded: " + fileKey);
//        String finalFilePath = destination + EvaluatorUtility.getFileNameFromKey(fileKey);
//        Files.copy(object.getObjectContent(), Paths.get(finalFilePath));
//        log.info("file copied to: " + finalFilePath);
    }

    private AmazonS3 getS3Client() {
        AWSCredentials credentials = new BasicAWSCredentials(coreProperties.getAccessKey(), coreProperties.getSecretKey());
        AmazonS3 s3client = AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(coreProperties.getRegion())
                .build();
        return s3client;
    }

    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }

    private boolean isValid(File file) {
        return file != null;
    }
}
