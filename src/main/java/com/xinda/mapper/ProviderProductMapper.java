package com.xinda.mapper;

import com.xinda.model.ProviderProduct;
import com.xinda.model.ProviderProductExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ProviderProductMapper {
    int countByExample(ProviderProductExample example);

    int deleteByExample(ProviderProductExample example);

    int deleteByPrimaryKey(String productId);

    int insert(ProviderProduct record);

    int insertSelective(ProviderProduct record);

    List<ProviderProduct> selectByExample(ProviderProductExample example);

    ProviderProduct selectByPrimaryKey(String productId);

    int updateByExampleSelective(@Param("record") ProviderProduct record, @Param("example") ProviderProductExample example);

    int updateByExample(@Param("record") ProviderProduct record, @Param("example") ProviderProductExample example);

    int updateByPrimaryKeySelective(ProviderProduct record);

    int updateByPrimaryKey(ProviderProduct record);
}