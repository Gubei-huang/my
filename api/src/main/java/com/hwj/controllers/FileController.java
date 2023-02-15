package com.hwj.controllers;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.UUID;
@CrossOrigin
@RestController
@RequestMapping("/file")
public class FileController {
    @PostMapping(value = "/upload",produces = {"text/plain;charset=UTF-8"})
    public String upload(@RequestParam("file") MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
        String dirPath = "D:\\ruanjian\\Tomcat\\apache-tomcat-9.0.52\\webapps\\images\\";
        File filePath = new File(dirPath);
        if (!filePath.exists()) {
            filePath.mkdirs();
        }
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        String flag = UUID.randomUUID().toString();
        String rootFilePath = dirPath + flag + suffix;
        file.transferTo(new File(rootFilePath));
        String newFileName = flag + suffix ;
        System.out.println("http://localhost:8080/images/"+newFileName);
        return "http://localhost:8080/images/"+newFileName;
    }


}
