package com.xinda.mapper;

import com.xinda.model.NewOrderMessage;
import com.xinda.model.NewOrderMessageExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface NewOrderMessageMapper {
    int countByExample(NewOrderMessageExample example);

    int deleteByExample(NewOrderMessageExample example);

    int deleteByPrimaryKey(String orderId);

    int insert(NewOrderMessage record);

    int insertSelective(NewOrderMessage record);

    List<NewOrderMessage> selectByExample(NewOrderMessageExample example);

    NewOrderMessage selectByPrimaryKey(String orderId);

    int updateByExampleSelective(@Param("record") NewOrderMessage record, @Param("example") NewOrderMessageExample example);

    int updateByExample(@Param("record") NewOrderMessage record, @Param("example") NewOrderMessageExample example);

    int updateByPrimaryKeySelective(NewOrderMessage record);

    int updateByPrimaryKey(NewOrderMessage record);
}