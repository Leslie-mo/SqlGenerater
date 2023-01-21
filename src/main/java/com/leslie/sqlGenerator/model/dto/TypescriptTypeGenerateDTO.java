package com.leslie.sqlGenerator.model.dto;

import lombok.Data;

import java.util.List;

/**
 * Typescript 类型生成封装类
 *

 */
@Data
public class TypescriptTypeGenerateDTO {

    /**
     * 类名
     */
    private String className;

    /**
     * 类注释
     */
    private String classComment;

    /**
     * 列信息列表
     */
    private List<FieldDTO> fieldList;

    /**
     * 列信息
     */
    @Data
    public static class FieldDTO {

        /**
         * 字段名
         */
        private String fieldName;

        /**
         * Typescript 类型
         */
        private String typescriptType;

        /**
         * 字段注释
         */
        private String comment;
    }

}
