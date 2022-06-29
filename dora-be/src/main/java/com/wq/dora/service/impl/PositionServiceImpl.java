package com.wq.dora.service.impl;

import com.wq.dora.common.ServiceException;
import com.wq.dora.entity.Position;
import com.wq.dora.mapper.PositionMapper;
import com.wq.dora.service.PositionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;


/**
 * 岗位实现类
 * @author wuqian
 * @date 2022/06/03
 */
@Service
@Transactional
public class PositionServiceImpl implements PositionService {
    @Resource
    private PositionMapper doraPositionMapper;

    /**
     * 保存
     * @param doraPosition 实体对象
     */
    @Override
    public void save(Position doraPosition) {
        Position position = doraPositionMapper.selectByPrimaryKey(doraPosition.getPositionNo());
        if (position == null) {
            doraPositionMapper.insert(doraPosition);
        }else {
            throw new ServiceException("存在重复的岗位编号！");
        }
    }

    /**
     * 通过主键删除
     * @param id 主键
     */
    @Override
    public void deleteById(String id) {
        doraPositionMapper.deleteByPrimaryKey(id);
    }

    /**
     * 更新
     * @param doraPosition
     */
    @Override
    public void update(Position doraPosition) {
        doraPositionMapper.updateByPrimaryKey(doraPosition);
    }

    /**
     * 通过主键查询
     * @param id
     * @return
     */
    @Override
    public Position findById(String id) {
        return doraPositionMapper.selectByPrimaryKey(id);
    }

    /**
     * 查询所有
     * @param position
     * @return
     */
    @Override
    public List<Position> findAll(Position position) {
        return doraPositionMapper.selectAll(position);
    }


}
