package com.leslie.sqlGenerator.core;

import com.leslie.sqlGenerator.core.builder.*;
import com.leslie.sqlGenerator.core.schema.SchemaException;
import com.leslie.sqlGenerator.core.schema.TableSchema.Field;
import com.leslie.sqlGenerator.core.schema.TableSchema;
import com.leslie.sqlGenerator.model.vo.GenerateVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;

/**
 * data generator
 */
public class GeneratorFacade {
    public static GenerateVO generateAll(TableSchema tableSchema) {

        // 校验
        validSchema(tableSchema);
        SqlBuilder sqlBuilder = new SqlBuilder();
        // 构造建表 SQL
        String createSql = sqlBuilder.buildCreateTableSql(tableSchema);
        int mockNum = tableSchema.getMockNum();
        // 生成模拟数据
        List<Map<String, Object>> dataList = DataBuilder.generateData(tableSchema, mockNum);
        // 生成插入 SQL
        String insertSql = sqlBuilder.buildInsertSql(tableSchema, dataList);
        // 生成数据 json
        String dataJson = JsonBuilder.buildJson(dataList);
        // 生成 java 实体代码
        String javaEntityCode = JavaCodeBuilder.buildJavaEntityCode(tableSchema);
        // 生成 java 对象代码
        String javaObjectCode = JavaCodeBuilder.buildJavaObjectCode(tableSchema, dataList);
        // 生成 typescript 类型代码
        String typescriptTypeCode = FrontendCodeBuilder.buildTypeScriptTypeCode(tableSchema);
        // 封装返回
        GenerateVO generateVO = new GenerateVO();
        generateVO.setTableSchema(tableSchema);
        generateVO.setCreateSql(createSql);
        generateVO.setDataList(dataList);
        generateVO.setInsertSql(insertSql);
        generateVO.setDataJson(dataJson);
        generateVO.setJavaEntityCode(javaEntityCode);
        generateVO.setJavaObjectCode(javaObjectCode);
        generateVO.setTypescriptTypeCode(typescriptTypeCode);
        return generateVO;
    }

    public static void validSchema(TableSchema tableSchema) {
        if (tableSchema == null) {
            throw new SchemaException("Data is empty");
        }
        String tableName = tableSchema.getTableName();
        if (StringUtils.isBlank(tableName)) {
            throw new SchemaException("Table name cannot be empty");
        }
        Integer mockNum = tableSchema.getMockNum();
        // 20 are generated by default
        if (tableSchema.getMockNum() == null) {
            tableSchema.setMockNum(20);
            mockNum = 20;
        }
        if (mockNum > 100 || mockNum < 10) {
            throw new SchemaException("Generation number setting error");
        }
        List<Field> fieldList = tableSchema.getFieldList();
        if (CollectionUtils.isEmpty(fieldList)) {
            throw new SchemaException("Field list cannot be empty");
        }
        for (Field field : fieldList) {
            validField(field);
        }
    }

    /**
     * check field
     *
     * @param field
     */
    public static void validField(Field field) {
        String fieldName = field.getFieldName();
        String fieldType = field.getFieldType();
        if (StringUtils.isBlank(fieldName)) {
            throw new SchemaException("Field name cannot be empty");
        }
        if (StringUtils.isBlank(fieldType)) {
            throw new SchemaException("Field type cannot be empty");
        }
    }
}
