package com.xinda.mapper;

import com.xinda.model.ProductStyleExample;
import com.xinda.model.ProductStyleKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ProductStyleMapper {
    int countByExample(ProductStyleExample example);

    int deleteByExample(ProductStyleExample example);

    int deleteByPrimaryKey(ProductStyleKey key);

    int insert(ProductStyleKey record);

    int insertSelective(ProductStyleKey record);

    List<ProductStyleKey> selectByExample(ProductStyleExample example);

    int updateByExampleSelective(@Param("record") ProductStyleKey record, @Param("example") ProductStyleExample example);

    int updateByExample(@Param("record") ProductStyleKey record, @Param("example") ProductStyleExample example);
}