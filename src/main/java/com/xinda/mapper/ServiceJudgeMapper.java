package com.xinda.mapper;

import com.xinda.model.ServiceJudge;
import com.xinda.model.ServiceJudgeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ServiceJudgeMapper {
    int countByExample(ServiceJudgeExample example);

    int deleteByExample(ServiceJudgeExample example);

    int deleteByPrimaryKey(String orderId);

    int insert(ServiceJudge record);

    int insertSelective(ServiceJudge record);

    List<ServiceJudge> selectByExample(ServiceJudgeExample example);

    ServiceJudge selectByPrimaryKey(String orderId);

    int updateByExampleSelective(@Param("record") ServiceJudge record, @Param("example") ServiceJudgeExample example);

    int updateByExample(@Param("record") ServiceJudge record, @Param("example") ServiceJudgeExample example);

    int updateByPrimaryKeySelective(ServiceJudge record);

    int updateByPrimaryKey(ServiceJudge record);
}