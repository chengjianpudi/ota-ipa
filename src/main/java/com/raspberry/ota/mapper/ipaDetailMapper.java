package com.raspberry.ota.mapper;

import com.raspberry.ota.model.ipaDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ipaDetailMapper {
    int insert(ipaDetail record);

    int insertSelective(ipaDetail record);

    List<ipaDetail> selectByPackageType(@Param("packageType") String packageType);
}