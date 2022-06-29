package ${basePackage}.service.impl;

import ${basePackage}.mapper.${modelNameUpperCamel}Mapper;
import ${basePackage}.entity.${modelNameUpperCamel};
import ${basePackage}.service.${modelNameUpperCamel}Service;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;



/**
 * ${modelNameUpperCamel}服务实现类
 * @date ${date}
 * @author ${author}
 */
@Service
@Transactional
public class ${modelNameUpperCamel}ServiceImpl implements ${modelNameUpperCamel}Service {
    @Resource
    private ${modelNameUpperCamel}Mapper ${modelNameLowerCamel}Mapper;


    /**
    * 保存
    * @param ${modelNameLowerCamel} 实体对象
    */
    @Override
    public void save(${modelNameUpperCamel} ${modelNameLowerCamel}) {
        ${modelNameLowerCamel}Mapper.insert(${modelNameLowerCamel});
    }

    /**
    * 用主键删除
    * @param id 主键
    */
    @Override
    public void deleteById(${keyType} id) {
        ${modelNameLowerCamel}Mapper.deleteByPrimaryKey(id);
    }

    /**
    * 更新
    * @param ${modelNameLowerCamel} 实体对象
    */
    @Override
    public void update(${modelNameUpperCamel} ${modelNameLowerCamel}) {
        ${modelNameLowerCamel}Mapper.updateByPrimaryKey(${modelNameLowerCamel});
    }

    /**
    * 通过主键查询
    * @param id
    * @return 实体对象
    */
    @Override
    public ${modelNameUpperCamel} findById(${keyType} id) {
        return ${modelNameLowerCamel}Mapper.selectByPrimaryKey(id);
    }

    /**
    * 查询所有
    * @return 实体对象列表
    */
    @Override
    public List<${modelNameUpperCamel}> findAll() {
        return ${modelNameLowerCamel}Mapper.selectAll();
    }


}
