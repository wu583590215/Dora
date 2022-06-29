import freemarker.template.TemplateExceptionHandler;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.wq.dora.common.ProjectConstant.*;

/**
 * 代码生成器，根据数据表名称生成对应的Model、Mapper、Service、Controller简化开发。
 */
public class ConstantGenerator {
    //JDBC配置，请修改为你项目的实际配置
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/dora";
    private static final String JDBC_USERNAME = "root";
    private static final String JDBC_PASSWORD = "wu1994821";
    private static final String JDBC_DIVER_CLASS_NAME = "com.mysql.jdbc.Driver";
    private static final String CURRENT_SCHEMA = "dora";

    private static final String PROJECT_PATH = System.getProperty("user.dir")+"/dora-be";//项目在硬盘上的基础路径
    private static final String TEMPLATE_FILE_PATH = PROJECT_PATH + "/src/test/resources/generator/template";//模板位置

    private static final String JAVA_PATH = "/src/main/java"; //java文件路径
    private static final String RESOURCES_PATH = "/src/main/resources";//资源文件路径
    private static final String DATE = new SimpleDateFormat("yyyy/MM/dd").format(new Date());//@date

    private static final String PACKAGE_PATH_CONSTANT = packageConvertPath(CONSTATANT_PACKAGE);//生成的Service存放路径


    public static void main(String[] args) throws Exception {
        generatorConstant("Sex","Sex");
    }

    private static void generatorConstant(String codeType,String className) throws Exception{
        Class.forName(JDBC_DIVER_CLASS_NAME);
        Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD);
        Statement statement = conn.createStatement();
        String codeTypeName = "";
        Map<String, Object> data = new HashMap<>();
        data.put("className", className);
        data.put("basePackage", BASE_PACKAGE);
        data.put("author", "wuqian");
        data.put("date", DATE);

        ResultSet rs1 = statement.executeQuery("select * from dora_code_catalog where code_type = '" + codeType + "'");
        while (rs1.next()) {
            codeTypeName = rs1.getString("code_name");
            data.put("codeTypeName", codeTypeName);
        }


        ResultSet rs = statement.executeQuery("select * from dora_code_library where code_type = '" + codeType + "'");
        List<Map> tempList = new ArrayList<>();
        while (rs.next()) {
            Map<String, String> tempMap = new HashMap<>();
            String codeNo = rs.getString("code_no");
            String codeName = rs.getString("code_name");
            tempMap.put("codeNo", codeNo);
            tempMap.put("codeName", codeName);
            tempList.add(tempMap);
        }
        data.put("codeList", tempList);

        freemarker.template.Configuration cfg = getConfiguration();


        File file = new File(PROJECT_PATH + JAVA_PATH + PACKAGE_PATH_CONSTANT + className + ".java");
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        cfg.getTemplate("constant.ftl").process(data,
                new FileWriter(file));

        rs.close();
        statement.close();
        conn.close();


    }

    private static freemarker.template.Configuration getConfiguration() throws IOException {
        freemarker.template.Configuration cfg = new freemarker.template.Configuration(freemarker.template.Configuration.VERSION_2_3_23);
        cfg.setDirectoryForTemplateLoading(new File(TEMPLATE_FILE_PATH));
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.IGNORE_HANDLER);
        return cfg;
    }

    private static String packageConvertPath(String packageName) {
        return String.format("/%s/", packageName.contains(".") ? packageName.replaceAll("\\.", "/") : packageName);
    }

}
