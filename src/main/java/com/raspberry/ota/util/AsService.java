package com.raspberry.ota.util;

import cn.hutool.core.util.StrUtil;
import com.raspberry.ota.dao.IpaDetailDao;
import com.raspberry.ota.mapper.ipaTypeMapper;
import com.raspberry.ota.model.ipaDetail;
import com.raspberry.ota.model.ipaType;
import com.sun.tools.javac.code.Type;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.util.ResourceUtils;

import javax.rmi.CORBA.Util;
import java.io.*;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.TimeZone;
import java.util.concurrent.Future;

/**
 * @program: ota
 * @description: 异步线程处理工具
 * @author: Wangshihai
 * @create: 2019-12-31 15:10
 **/
@Component
@Slf4j
@Data
public class AsService {

    @Value("${spring.ota.hostUrl}")
    private String hostUrl;

    @Value("${spring.ota.AppUrl}")
    private String AppUrl;

    @Value("${spring.plist.storePath}")
    private String plistPath;



    @Autowired
    IpaDetailDao ipadDetailService;

    @Autowired
    ipaTypeMapper ipaTypeService;

    @Async
    public Future<String> excuteAsyncTask(String fileName) throws Exception {

        System.out.println("开始做任务");
        long start = System.currentTimeMillis();
        long end = System.currentTimeMillis();
        System.out.println("任务完成");
        System.out.println("完成任务,耗时："+(end - start)+"毫秒");
        praseFileName(fileName);
        return new AsyncResult <>("Task1 accomplished!");
    }

    public void praseFileName(@org.jetbrains.annotations.NotNull String fileName) throws Exception {

        this.createPackageInfo(fileName);
    }

    private void createPackageInfo(String fileName)
    {
        String[] strArr  =   fileName.split("\\_");
        //打包类型
        String packageType = "";
        //打包版本号
        String appVersion  = "";
        //打包时间
        String packageTime = "";
        //打包图标
        String packageIcon = "";
        //打包的显示名字
        String packageDisName = "";
        //bundleId
        String bundleId = "";

        ipaType packageTypeInfo;
        if (strArr.length > 0){
           packageType = strArr[0];
           appVersion = strArr[2].replace(".ipa","");
           packageTime = strArr[1];
        }
        if (StrUtil.isEmpty(packageType) == false){
            List <ipaType> ipaTypes = ipaTypeService.selectByPackageType(packageType);
            if (ipaTypes.isEmpty() == false){
                packageTypeInfo =  ipaTypes.get(0);
                packageIcon = packageTypeInfo.getPackageIcon();
                packageDisName = packageTypeInfo.getDisplayName();
                bundleId = packageTypeInfo.getBundleId();
            }
            else{
                log.error("查询打包类型表内容为空");
            }
        }
        else{
            log.error("解析字符串为空");
        }

        String plistPath =   "/file/plist/" + fileName.replace("ipa","plist");
        String href = "itms-services://?action=download-manifest&url=" + AppUrl + plistPath;

        ipaDetail ipaDetailDto  = new ipaDetail();
        ipaDetailDto.setPackageName(fileName);
        ipaDetailDto.setPackageIcon(packageIcon);
        ipaDetailDto.setPackageType(packageType);
        ipaDetailDto.setDisplayName(packageDisName);
        ipaDetailDto.setDisplayTime("打包时间:"+packageTime);
        ipaDetailDto.setPackageVersion("版本号:"+appVersion);
        ipaDetailDto.setDisplayLinkurl(href);
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Shanghai"));
        ipaDetailDto.setCreateTime(new Date());
        ipadDetailService.insertDetailInfo(ipaDetailDto);
        this.createPlistFileWithInfo(fileName,packageDisName,bundleId,appVersion);

    }

    private void createPlistFileWithInfo(String fileName,
                                         String name,
                                         String bundleId,
                                         String appVersion){
        try {

            String path =  plistPath + "/" + fileName.replace("ipa","plist");
            File file =  new File(path);
            if (!file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException e) {

                }
            }

            String localStorePath = "/file/ios/" + fileName;
            String  serverUrl = AppUrl;
            String plist = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
                    + "<!DOCTYPE plist PUBLIC \"-//Apple//DTD PLIST 1.0//EN\" \"http://www.apple.com/DTDs/PropertyList-1.0.dtd\">\n"
                    + "<plist version=\"1.0\">\n" + "<dict>\n"
                    + "<key>items</key>\n"
                    + "<array>\n"
                    + "<dict>\n"
                    + "<key>assets</key>\n"
                    + "<array>\n"
                    + "<dict>\n"
                    + "<key>kind</key>\n"
                    + "<string>software-package</string>\n"
                    + "<key>url</key>\n"
                    // 之前所上传的ipa文件路径（必须是https，否则无法下载！）
                    + "<string>" + serverUrl + localStorePath + "</string>\n"
                    + "</dict>\n"
                    + "</array>\n"
                    + "<key>metadata</key>\n"
                    + "<dict>\n"
                    + "<key>bundle-identifier</key>\n"
                    // 这个是开发者账号用户名，也可以为空，为空安装时看不到图标，完成之后可以看到
                    + "<string>" + bundleId + "</string>\n"
                    + "<key>bundle-version</key>\n"
                    // 版本号
                    + "<string>"+ appVersion +"</string>\n"
                    + "<key>kind</key>\n"
                    + "<string>software</string>\n"
                    + "<key>subtitle</key>\n"
                    + "<string>下载</string>\n"
                    + "<key>title</key>\n"
                    // 一定要有title，否则无法正常下载
                    + "<string>"+ name +"</string>\n"
                    + "</dict>\n"
                    + "</dict>\n"
                    + "</array>\n"
                    + "</dict>\n"
                    + "</plist>";
            try {
                log.info("开始存储plist文件");
                FileOutputStream output = new FileOutputStream(file);
                OutputStreamWriter writer;
                writer = new OutputStreamWriter(output, "UTF-8");
                writer.write(plist);
                writer.close();
                output.close();
                log.info("结束存储存储plist文件");
            } catch (Exception e) {
                String desc  =  e.getMessage();
                log.error(desc + "存储plist文件失败");
            }

        } catch (Exception e) {
            log.error("plist文件抛出异常");
            e.printStackTrace();
        }
    }

}
