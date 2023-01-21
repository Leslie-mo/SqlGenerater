package com.leslie.sqlGenerator.core.builder;


import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.leslie.sqlGenerator.core.generator.DataGenerator;
import com.leslie.sqlGenerator.core.generator.DataGeneratorFactory;
import com.leslie.sqlGenerator.core.schema.*;
import com.leslie.sqlGenerator.core.schema.TableSchema.Field;
import com.leslie.sqlGenerator.model.enums.MockTypeEnum;

import java.util.*;

/**
 * Data builder
 *
 */
public class DataBuilder {

    /**
     * generate Data
     *
     * @param tableSchema
     * @param rowNum
     * @return
     */
    public static List<Map<String, Object>> generateData(TableSchema tableSchema, int rowNum) {
        List<TableSchema.Field> fieldList = tableSchema.getFieldList();
        // 初始化结果数据
        List<Map<String, Object>> resultList = new ArrayList<>(rowNum);
        for (int i = 0; i < rowNum; i++) {
            resultList.add(new HashMap<>());
        }
        // 依次生成每一列
        for (Field field : fieldList) {
            MockTypeEnum mockTypeEnum = Optional.ofNullable(MockTypeEnum.getEnumByValue(field.getMockType()))
                    .orElse(MockTypeEnum.NONE);
            DataGenerator dataGenerator = DataGeneratorFactory.getGenerator(mockTypeEnum);
            List<String> mockDataList = dataGenerator.doGenerate(field, rowNum);
            String fieldName = field.getFieldName();
            // 填充结果列表
            if (CollectionUtils.isNotEmpty(mockDataList)) {
                for (int i = 0; i < rowNum; i++) {
                    resultList.get(i).put(fieldName, mockDataList.get(i));
                }
            }
        }
        return resultList;
    }
}
