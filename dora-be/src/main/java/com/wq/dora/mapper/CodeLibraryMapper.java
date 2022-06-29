package com.wq.dora.mapper;

import com.wq.dora.entity.CodeLibrary;

import java.util.List;

/**
 * 码值表查询
 * @author wuqian
 * @date 2022/06/03
 */
public interface CodeLibraryMapper {
    List<CodeLibrary> selectAll(List<String> codeType);
}