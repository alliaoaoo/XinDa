package com.xinda.mapper;

import com.xinda.model.ProductTypeExample;
import com.xinda.model.ProductTypeKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ProductTypeMapper {
    int countByExample(ProductTypeExample example);

    int deleteByExample(ProductTypeExample example);

    int deleteByPrimaryKey(ProductTypeKey key);

    int insert(ProductTypeKey record);

    int insertSelective(ProductTypeKey record);

    List<ProductTypeKey> selectByExample(ProductTypeExample example);

    int updateByExampleSelective(@Param("record") ProductTypeKey record, @Param("example") ProductTypeExample example);

    int updateByExample(@Param("record") ProductTypeKey record, @Param("example") ProductTypeExample example);
}