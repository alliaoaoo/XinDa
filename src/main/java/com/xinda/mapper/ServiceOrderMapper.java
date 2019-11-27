package com.xinda.mapper;

import com.xinda.model.ServiceOrder;
import com.xinda.model.ServiceOrderExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ServiceOrderMapper {
    int countByExample(ServiceOrderExample example);

    int deleteByExample(ServiceOrderExample example);

    int deleteByPrimaryKey(String id);

    int insert(ServiceOrder record);

    int insertSelective(ServiceOrder record);

    List<ServiceOrder> selectByExample(ServiceOrderExample example);

    ServiceOrder selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") ServiceOrder record, @Param("example") ServiceOrderExample example);

    int updateByExample(@Param("record") ServiceOrder record, @Param("example") ServiceOrderExample example);

    int updateByPrimaryKeySelective(ServiceOrder record);

    int updateByPrimaryKey(ServiceOrder record);
}