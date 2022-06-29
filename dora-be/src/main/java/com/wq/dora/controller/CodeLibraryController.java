package com.wq.dora.controller;

import com.wq.dora.common.Result;
import com.wq.dora.common.ResultGenerator;
import com.wq.dora.service.CodeLibraryService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;


/**
* CodeLibrary控制层代码
* @date 2022/06/26
* @author Wuqian
*/
@RestController
@RequestMapping("/code")
public class CodeLibraryController {
    @Resource
    private CodeLibraryService codeLibraryService;


    /**
    * 获取码值信息
    * @param codeType 主键
    * @return 结果
    */
    @PostMapping("/getLibrary")
    public Result getCodeLibrary(@RequestBody List<String> codeType) {
        return ResultGenerator.genSuccessResult(codeLibraryService.findByCodeType(codeType));
    }

}
