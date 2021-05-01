package com.briefing_management.configuration.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result {
    /**
     * 错误码
     */
    private long code;

    /**
     * 提示信息
     */
    private String msg;

    /**
     * 具体的内容
     */
    private Object data;
}
