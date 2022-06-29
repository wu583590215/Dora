package com.wq.dora.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

@Data
public class CodeLibrary {
    @JSONField(serialize = false)
    private String codeType;

    private String codeNo;

    private String codeName;


}