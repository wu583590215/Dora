package com.wq.dora.service;

import com.wq.dora.entity.CodeLibrary;

import java.util.List;
import java.util.Map;


/**
 * 码值服务类
 * @author wuqian
 * @date 2022/06/03
 */
public interface CodeLibraryService {
    /**
     * 查询代码
     * @param codeType
     * @return
     */
    Map<String, List<CodeLibrary>> findByCodeType(List<String> codeType);
 }
