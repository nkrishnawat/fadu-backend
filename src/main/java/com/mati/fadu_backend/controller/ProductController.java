package com.mati.fadu_backend.controller;

import com.mati.fadu_backend.controller.model.Product;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    public ResponseEntity uploadFiles(
            @RequestPart("product") Product product,
            @RequestPart("files") List<MultipartFile> files) {
        String message = "";
        try {
            List<String> fileNames = new ArrayList<>();

            (files).stream().forEach(file -> {
                //storageService.save(file);
                System.out.println(">> " + file.getOriginalFilename());
                System.out.print(product.toString());
                try {
                    System.out.println("Bytes"+ file.getBytes());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                fileNames.add(file.getOriginalFilename());
            });

            message = "Uploaded the files successfully: " + fileNames;
            return ResponseEntity.status(HttpStatus.OK).body(message);
        } catch (Exception e) {
            message = "Fail to upload files!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
        }
    }
}