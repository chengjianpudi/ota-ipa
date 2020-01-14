package com.raspberry.ota.controller;

import cn.hutool.core.io.resource.ClassPathResource;
import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.StrUtil;
import com.raspberry.ota.dao.IpaDetailDao;
import com.raspberry.ota.util.AsService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * @program: ota
 * @description: 上传文件请求
 * @author: Wangshihai
 * @create: 2020-01-09 15:37
 **/
@RestController
@RequestMapping("/file")
@Slf4j
@Data
public class FileUploadController {

    @Autowired
    AsService asyncService;

    @Autowired
    IpaDetailDao ipadDetailService;

    @Value("${spring.ios.storePath}")
    private String iosPath;

    @Value("${spring.plist.storePath}")
    private String plistPath;


    @PostMapping(value = "/upload",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Dict upload(@RequestParam("file") MultipartFile file){
        if (file.isEmpty()) {
            log.error("文件错误","文件为空");
            return Dict.create().set("code", 400).set("message", "文件内容为空");
        }
        String  fileTempPath = iosPath;


        File isExitFile = new File(fileTempPath);
        if(!isExitFile .exists()) {
            isExitFile.mkdirs();
            log.error("文件路径不存在");
        }


        String fileName = file.getOriginalFilename();
        String rawFileName = StrUtil.subBefore(fileName, ".", true);
        String fileType = StrUtil.subAfter(fileName, ".", true);
        String localFilePath = StrUtil.appendIfMissing(fileTempPath, "/") + rawFileName  + "." + fileType;
        try {
            log.info("路径是:" + localFilePath);
            file.transferTo(new File(localFilePath));
        } catch (IOException e) {
            log.error("【文件上传至本地】失败，绝对路径：{}", fileTempPath);
            log.error(e.getMessage());
            return Dict.create().set("code", 500).set("message", "文件上传失败");
        }
        log.info("【文件上传至本地】绝对路径：{}", localFilePath);
        try {
            asyncService.excuteAsyncTask(fileName);
        } catch (Exception e) {

            e.printStackTrace();

        }
        return Dict.create().set("code", 200).set("message", "上传成功").set("data", Dict.create().set("fileName", fileName).set("filePath", localFilePath));
    }

    @RequestMapping(value="/ios/{ipaName}",method= RequestMethod.GET)
    @ResponseBody
    public void getIpaFile(@PathVariable("ipaName") String ipaName, HttpServletRequest request, HttpServletResponse response){

        String url =  iosPath + "/" + ipaName;
        File file = new File(url);
        if (file.exists() == false){
            log.error("ipa文件为空");
        }
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file);
            byte[] data = new byte[(int) file.length()];
            int length = inputStream.read(data);
            inputStream.close();
            OutputStream stream = response.getOutputStream();
            stream.write(data);
            stream.flush();
            stream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value="/plist/{plistName}",method= RequestMethod.GET)
    @ResponseBody
    public void getPlistFile(@PathVariable("plistName") String plistName, HttpServletRequest request, HttpServletResponse response){

        String url =  plistPath + "/" + plistName;
        File file = new File(url);
        if (file.exists() == false){
            log.error("Plsit文件为空");
        }
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file);
            byte[] data = new byte[(int) file.length()];
            int length = inputStream.read(data);
            inputStream.close();
            OutputStream stream = response.getOutputStream();
            stream.write(data);
            stream.flush();
            stream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
