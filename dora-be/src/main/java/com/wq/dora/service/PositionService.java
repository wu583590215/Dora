package com.wq.dora.service;
import com.wq.dora.entity.Position;

import java.util.List;


/**
 * 岗位服务类
 * @author wuqian
 * @date 2022/06/03
 */
public interface PositionService  {
    /**
     * 保存
     * @param model 实体对象
     */
    void save(Position model);

    /**
     * 用主键删除
     * @param id 主键
     */
    void deleteById(String id);

    /**
     * 更新
     * @param model 实体对象
     */
    void update(Position model);

    /**
     * 通过主键查询
     * @param id
     * @return
     */
    Position findById(String id);

    /**
     * 查询所有
     * @return
     */
    List<Position> findAll(Position position);
}
