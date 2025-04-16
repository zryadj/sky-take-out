package com.sky.utils;

import io.minio.*;
import io.minio.errors.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@Slf4j
public class MinioUtil {

    private String endpoint;
    private String accessKey;
    private String secretKey;
    private String bucketName;

    /**
     * 上传文件
     */
    public String upload(MultipartFile file) {
        try {
            MinioClient minioClient = MinioClient.builder()
                    .endpoint(endpoint)
                    .credentials(accessKey, secretKey)
                    .build();

            // 检查桶是否存在
            checkBucketExists(minioClient);

            // 构造文件名：比如 2025/04/14/uuid.jpg
            String objectName = buildFileName(file.getOriginalFilename());


            // 上传
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectName)
                            .stream(file.getInputStream(), file.getSize(), -1)
                            .contentType(file.getContentType())
                            .build()
            );

            String fileUrl = String.format("%s/%s/%s", endpoint, bucketName, objectName);
            log.info("上传成功：{}", fileUrl);
            return fileUrl;

        } catch (Exception e) {
            log.error("上传失败: {}", e.getMessage(), e);
            throw new RuntimeException("MinIO 上传失败", e);
        }
    }

    /**
     * 构造文件名：日期路径 + uuid + 后缀名
     */
    public String buildFileName(String originalFilename) {
        String datePath = LocalDate.now().toString(); // 2025-04-14
        String uuid = UUID.randomUUID().toString().replace("-", "");
        String ext = originalFilename.substring(originalFilename.lastIndexOf("."));
        return String.format("%s/%s%s", datePath, uuid, ext);
    }

    /**
     * 检查桶是否存在，不存在则抛出异常（你也可以改成自动创建）
     */
    public void checkBucketExists(MinioClient client) throws Exception {
        boolean exists = client.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
        if (!exists) {
            client.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
        }
    }
}
