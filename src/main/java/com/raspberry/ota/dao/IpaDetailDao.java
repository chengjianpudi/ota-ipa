package com.raspberry.ota.dao;

import com.raspberry.ota.mapper.ipaDetailMapper;
import com.raspberry.ota.mapper.ipaTypeMapper;
import com.raspberry.ota.model.ipaDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @program: ota
 * @description: 网络请求
 * @author: Wangshihai
 * @create: 2019-12-30 13:58
 **/
@Repository
public class IpaDetailDao {

    @Autowired
    private ipaDetailMapper detailMapper;

    public List<ipaDetail> findByType(String  pacageType){
       return detailMapper.selectByPackageType(pacageType);
    }

    public void insertDetailInfo(ipaDetail ipaDetailDto){
        detailMapper.insert(ipaDetailDto);
    }


}
