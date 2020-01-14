package com.raspberry.ota.mapper;

import com.raspberry.ota.model.ipaDetail;
import com.raspberry.ota.model.ipaType;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ipaTypeMapper {
    int insert(ipaType record);
    int insertSelective(ipaType record);
    List <ipaType> selectByPackageType(@Param("packageType") String packageType);
    List <ipaType> selectAll();

}