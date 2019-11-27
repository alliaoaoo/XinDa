package com.xinda.mapper;

import com.xinda.model.ProviderMessage;
import com.xinda.model.ProviderMessageExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ProviderMessageMapper {
    int countByExample(ProviderMessageExample example);

    int deleteByExample(ProviderMessageExample example);

    int deleteByPrimaryKey(String messageId);

    int insert(ProviderMessage record);

    int insertSelective(ProviderMessage record);

    List<ProviderMessage> selectByExample(ProviderMessageExample example);

    ProviderMessage selectByPrimaryKey(String messageId);

    int updateByExampleSelective(@Param("record") ProviderMessage record, @Param("example") ProviderMessageExample example);

    int updateByExample(@Param("record") ProviderMessage record, @Param("example") ProviderMessageExample example);

    int updateByPrimaryKeySelective(ProviderMessage record);

    int updateByPrimaryKey(ProviderMessage record);
}