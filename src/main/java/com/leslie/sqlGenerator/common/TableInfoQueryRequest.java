package com.leslie.sqlGenerator.common;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * Table info query request
 *
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class TableInfoQueryRequest extends PageRequest implements Serializable {

    /**
     * name
     */
    private String name;

    /**
     * content
     */
    private String content;

    /**
     * reviewStatus
     */
    private Integer reviewStatus;

    /**
     * userId
     */
    private Long userId;

    private static final long serialVersionUID = 1L;
}