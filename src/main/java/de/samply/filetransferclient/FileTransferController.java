package de.samply.filetransferclient;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;

@RestController
public class FileTransferController {

    private Path transferFilesDirectory;


    @GetMapping("/helloWorld")
    public String helloWorld (){
        return "Hello World!";
    }

    @PostMapping("/transfer")
    public String transferFile (@RequestParam("file") MultipartFile multipartFile){

        return "Multipart file uploaded!";
    }

}
