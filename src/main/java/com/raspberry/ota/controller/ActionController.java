package com.raspberry.ota.controller;

import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.StrUtil;
import com.raspberry.ota.dao.IpaDetailDao;
import com.raspberry.ota.mapper.ipaTypeMapper;
import com.raspberry.ota.model.ipaDetail;
import com.raspberry.ota.model.ipaType;
import com.raspberry.ota.util.AsService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;


/**
 * @program: ota
 * @description: 网络请求
 * @author: Wangshihai
 * @create: 2019-12-30 13:52
 **/
@Controller
@RequestMapping("/ota")
@Slf4j
@Data
public class ActionController {

    @Autowired
    IpaDetailDao detailDao;

    @Autowired
    IpaDetailDao ipadDetailService;

    @Autowired
    private ipaTypeMapper typeMapper;

    @RequestMapping("/findbyTye")
    public void  findByType()
    {
        List <ipaDetail> detailList = detailDao.findByType("XFConsultantMain");
        if (detailList.size() == 0 ){
            log.error("查询数据为空");
        }
        else{
            log.info( "返回的个数为:" +   String.valueOf(detailList.size()));
        }
    }

    @RequestMapping("/index")
    public String index(Model model) {

        List<ipaType> list  = typeMapper.selectAll();
        if (list.isEmpty() || list.size() == 0){
            log.error("查询数据库为空");
        }
        else{
            model.addAttribute("pakcagelist",list);
        }
        return "index";
    }

    @RequestMapping(value = "/ios",method = RequestMethod.GET)
    public String mainForders(Model model, HttpServletRequest request)
    {
        String option  =  request.getParameter("option");
        if ( option == null ||  option.length() == 0){
            option = "XKHygea";
            log.error("不存在打包数据");
        }
        else{
            List<ipaDetail>packageList =  ipadDetailService.findByType(option);
            ArrayList <ipaDetail> list =(ArrayList <ipaDetail>) packageList;
            model.addAttribute("linklistPatient",list);
        }
        return "ipaList";
    }

}
