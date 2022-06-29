package ${basePackage}.service;
import ${basePackage}.entity.${modelNameUpperCamel};
import java.util.List;



/**
 * ${modelNameUpperCamel}服务类
 * @date ${date}
 * @author ${author}
 */
public interface ${modelNameUpperCamel}Service {

    /**
    * 保存
    * @param model 实体对象
    */
    void save(${modelNameUpperCamel} model);

    /**
    * 用主键删除
    * @param id 主键
    */
    void deleteById(${keyType} id);

    /**
    * 更新
    * @param model 实体对象
    */
    void update(${modelNameUpperCamel} model);

    /**
    * 通过主键查询
    * @param id
    * @return 实体对象
    */
    ${modelNameUpperCamel} findById(${keyType} id);

    /**
    * 查询所有
    * @return 实体对象列表
    */
    List<${modelNameUpperCamel}> findAll();
 }
