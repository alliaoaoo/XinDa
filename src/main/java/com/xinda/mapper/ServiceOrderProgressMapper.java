package com.xinda.mapper;

import com.xinda.model.ServiceOrderProgress;
import com.xinda.model.ServiceOrderProgressExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ServiceOrderProgressMapper {
    int countByExample(ServiceOrderProgressExample example);

    int deleteByExample(ServiceOrderProgressExample example);

    int deleteByPrimaryKey(String id);

    int insert(ServiceOrderProgress record);

    int insertSelective(ServiceOrderProgress record);

    List<ServiceOrderProgress> selectByExample(ServiceOrderProgressExample example);

    ServiceOrderProgress selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") ServiceOrderProgress record, @Param("example") ServiceOrderProgressExample example);

    int updateByExample(@Param("record") ServiceOrderProgress record, @Param("example") ServiceOrderProgressExample example);

    int updateByPrimaryKeySelective(ServiceOrderProgress record);

    int updateByPrimaryKey(ServiceOrderProgress record);
}