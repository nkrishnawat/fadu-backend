package com.mati.fadu_backend.controller;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.nio.file.Paths;
import java.util.List;
import java.util.*;

import com.mati.fadu_backend.controller.model.Product;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.io.IOException;

@RestController
@RequestMapping("/product")
public class ProductController {

    @GetMapping(value = "page/{page}/count/{count}/category/{category}/sort/{sortOrder}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Product> readUser(@PathVariable("page") Integer page, @PathVariable("count") Integer count, @PathVariable("category") String category, @PathVariable("sortOrder") String sortOrder){
        List<Product> aList = Arrays.asList(Product.builder()
                .productId(page)
                .productName("")
                .category("")
                .description("")
                .shortDescription("")
                .starRating((count))
                //.s3ImageThumbnailUrl(new String[]{"https://fadu.s3.us-west-1.amazonaws.com/men/shoes/convers.jpeg"})
                //.s3imageUrl(new String[][]{})
                .specifications("")
                .supplier("")
                .category(category)
                .subCategory(sortOrder)
                .build(),

                Product.builder()
                .productId(page)
                .productName("")
                .category("")
                .description("")
                .shortDescription("")
                .starRating((count))
                //.s3ImageThumbnailUrl(new String[]{"https://fadu.s3.us-west-1.amazonaws.com/men/shoes/convers.jpeg"})
                //.s3imageUrl(new String[][]{})
                .specifications("")
                .supplier("")
                .category(category)
                .subCategory(sortOrder)
                .build(),

        Product.builder()
                .productId(page)
                .productName("")
                .category("")
                .description("")
                .shortDescription("")
                .starRating((count))
                //.s3ImageThumbnailUrl(new String[]{"https://fadu.s3.us-west-1.amazonaws.com/men/shoes/convers.jpeg"})
                //.s3imageUrl(new String[][]{})
                .specifications("")
                .supplier("")
                .category(category)
                .subCategory(sortOrder)
                .build());
        return aList;
    }

    @PostMapping("/upload")
    public ResponseEntity uploadFiles(@RequestPart("product") Product product, @RequestPart("files") List<MultipartFile> files) {
        String message = "";
        try {
            List<String> fileNames = new ArrayList<>();
            (files).stream().forEach(file -> {
                System.out.println(">> " + file.getOriginalFilename());
                System.out.print(product.toString());
                try {
                    System.out.println("Bytes"+ file.getBytes());
                    createImageInBucket("fadu_products", file.getOriginalFilename(), file.getBytes());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });

            message = "Uploaded the files successfully: " + fileNames;
            return ResponseEntity.status(HttpStatus.OK).body(message);
        } catch (Exception e) {
            message = "Fail to upload files!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
        }
    }

    private String createImageInBucket(String bucket_name, String file_name, byte[] image_bytes) {
        String key = "";// creatKey();
        PutObjectResult result = null;
        System.out.format("Uploading %s to S3 bucket %s...\n", file_name, bucket_name);
        final AmazonS3 s3 = AmazonS3ClientBuilder.standard().withRegion(Regions.DEFAULT_REGION).build();
        try {
            result = s3.putObject(bucket_name, key, newFile(image_bytes, file_name));
        } catch (AmazonServiceException | IOException e) {
            System.err.println(e.getLocalizedMessage());
        }
        System.out.println("Done!");
        return "s3://" + bucket_name +  "/" + key;
    }

    private File newFile(byte[] arr, String file_name) throws IOException {
        // create the object of ByteArrayInputStream class
        // and initialized it with the byte array.
        ByteArrayInputStream inStreambj = new ByteArrayInputStream(arr);

        // Create a File object to represent the output image file
        File outputImageFile = new File("/tmp", file_name);

        // read image from byte array
        BufferedImage newImage = ImageIO.read(inStreambj);

        // write output image
        ImageIO.write(newImage, "jpg", outputImageFile);

        return outputImageFile;
    }

}