package com.leslie.sqlGenerator.core.generator;

import com.leslie.sqlGenerator.core.schema.TableSchema.Field;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 固定值数据生成器
 *
 */
public class FixedDataGenerator implements DataGenerator {

    @Override
    public List<String> doGenerate(Field field, int rowNum) {
        String mockParams = field.getMockParams();
        if (StringUtils.isBlank(mockParams)) {
            mockParams = "6";
        }
        List<String> list = new ArrayList<>(rowNum);
        for (int i = 0; i < rowNum; i++) {
            list.add(mockParams);
        }
        return list;
    }
}
