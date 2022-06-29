package com.wq.dora.service.impl;

import com.wq.dora.entity.CodeLibrary;
import com.wq.dora.mapper.CodeLibraryMapper;
import com.wq.dora.service.CodeLibraryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * 码值表实现类
 * @author wuqian
 * @date 2022/06/03
 */
@Service
@Transactional
public class CodeLibraryServiceImpl implements CodeLibraryService {
    @Resource
    private CodeLibraryMapper doraCodeLibraryMapper;

    /**
     * 查询代码
     *
     * @param codeType
     * @return
     */
    @Override
    public Map<String, List<CodeLibrary>> findByCodeType(List<String> codeType) {
        List<CodeLibrary> codeLibraries = doraCodeLibraryMapper.selectAll(codeType);
        return codeLibraries.stream()
                .collect(Collectors.groupingBy(CodeLibrary::getCodeType));

    }

}
