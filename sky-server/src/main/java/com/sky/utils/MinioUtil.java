//package com.sky.utils;
//
//import com.sky.properties.MinioProperties;
//import io.minio.BucketExistsArgs;
//import io.minio.MakeBucketArgs;
//import io.minio.MinioClient;
//import io.minio.PutObjectArgs;
//import io.minio.errors.MinioException;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Component;
//import org.springframework.web.multipart.MultipartFile;
//
//import javax.annotation.PostConstruct;
//import java.io.InputStream;
//import java.util.UUID;
//
//@Component
//@RequiredArgsConstructor
//public class MinioUtil {
//
//    private final MinioProperties minioProperties;
//    private MinioClient minioClient;
//
//    @PostConstruct
//    public void init() {
//        minioClient = MinioClient.builder()
//                .endpoint(minioProperties.getEndpoint())
//                .credentials(minioProperties.getAccessKey(), minioProperties.getSecretKey())
//                .build();
//    }
//
//    /**
//     * 上传文件
//     * @param file MultipartFile 文件
//     * @return 文件访问路径
//     */
//    public String uploadFile(MultipartFile file) {
//        try {
//            String bucketName = minioProperties.getBucketName();
//
//            // 检查 Bucket 是否存在，不存在就创建
//            boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
//            if (!found) {
//                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
//            }
//
//            // 构造文件名
//            String originalFilename = file.getOriginalFilename();
//            String fileName = UUID.randomUUID() + "-" + originalFilename;
//
//            // 上传文件
//            try (InputStream inputStream = file.getInputStream()) {
//                minioClient.putObject(PutObjectArgs.builder()
//                        .bucket(bucketName)
//                        .object(fileName)
//                        .stream(inputStream, file.getSize(), -1)
//                        .contentType(file.getContentType())
//                        .build());
//            }
//
//            return minioProperties.getEndpoint() + "/" + bucketName + "/" + fileName;
//
//        } catch (MinioException e) {
//            throw new RuntimeException("MinIO 上传失败：" + e.getMessage(), e);
//        } catch (Exception e) {
//            throw new RuntimeException("上传异常：" + e.getMessage(), e);
//        }
//    }
//}
