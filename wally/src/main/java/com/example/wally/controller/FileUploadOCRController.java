package com.example.wally.controller;

import com.example.wally.converter.TransactionConverter;
import com.example.wally.domain.Transaction;
import com.example.wally.repository.SimpleUserRepository;
import com.example.wally.repository.TransactionRepository;
import com.example.wally.service.SimpleUserService;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.persistence.Access;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("upload")
public class FileUploadOCRController {

    @Autowired
    SimpleUserRepository simpleUserRepository;

    @Autowired
    SimpleUserService simpleUserService;

    @RequestMapping(path = "simpleUserId={simpleUserId}", method = RequestMethod.POST)
    public String uploadFileDoOCR(@PathVariable("simpleUserId") Long simpleUserId, @RequestParam("file") MultipartFile file) throws IOException, TesseractException {

        byte[] bytes = file.getBytes();
        Path path = Paths.get("D:\\Licenta\\Application\\Backend\\BachelorThesis\\wally\\resources\\" + file.getOriginalFilename());
        Files.write(path, bytes);

        String imageString = "";
        File convFile = convert(file);

        Tesseract tesseract = new Tesseract();
        tesseract.setDatapath("D:\\Licenta\\Application\\Backend\\BachelorThesis\\wally\\tessdata");

        Date now = new Date();

        try {
            imageString = tesseract.doOCR(convFile);

            Pattern regexPatt = Pattern.compile("Total.*\\d");
            Matcher matcher = regexPatt.matcher(imageString);
            if (matcher.find())
            {
                String totalu = matcher.group(0).split(" ")[1];
                Double totalSum = Double.parseDouble(totalu);
                System.out.println(totalSum);

               // this.simpleUserService.addTransaction(this.simpleUserRepository.findById(simpleUserId).get().getID(), "Receipt", true, "Shopping", totalSum, now);
            }
            else
            {
                throw new Exception("No match found.");
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        return imageString;
    }


    public static File convert(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        convFile.createNewFile();
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }
}
