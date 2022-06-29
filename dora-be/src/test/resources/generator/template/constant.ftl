package ${basePackage}.constant;


/**
 * ${codeTypeName}
 * @author ${author}
 * @date ${date}
 */
public enum ${className} {
<#list codeList as code>
   <#if code_has_next>
    _${code.codeNo}("${code.codeNo}","${code.codeName}"),
    <#else>
    _${code.codeNo}("${code.codeNo}","${code.codeName}");
    </#if>
</#list>

    private String code;
    private String name;

    ${className}(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static String getNameByCode(String code) {
        for (${className} value : values()) {
            if (value.getCode().equals(code)) {
                return value.getName();
             }
        }
        return null;
    }
}
