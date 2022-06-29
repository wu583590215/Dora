package com.wq.dora.common;

import java.util.List;

/**
 * @author wuqian
 * @date 2022/6/28
 */
public interface Mapper<T> {
    int deleteByPrimaryKey(Object key);

    int insert(T record);

    T selectByPrimaryKey(Object key);

    List<T> selectAll();

    int updateByPrimaryKey(T record);
}
