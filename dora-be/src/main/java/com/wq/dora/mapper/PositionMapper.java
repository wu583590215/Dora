package com.wq.dora.mapper;

import com.wq.dora.entity.Position;

import java.util.List;

/**
 * 岗位查询mapper
 * @author wuqian
 * @date 2022/06/03
 */
public interface PositionMapper {
    int deleteByPrimaryKey(String positionNo);

    int insert(Position record);

    Position selectByPrimaryKey(String positionNo);

    List<Position> selectAll(Position position);

    int updateByPrimaryKey(Position record);
}