package com.miaoaotian.sys_back.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> {
    private int code;
    private String msg;
    private T data;

    public Result(int code) {
        this.code = code;
    }

    public Result(int code, T data) {
        this.code = code;
        this.data = data;
    }

    public Result(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static <T> Result<T> success() {
        return new Result<T>(Constant.SUCCESS);
    }

    public static <T> Result<T> success(T data) {
        return new Result<T>(Constant.SUCCESS, data);
    }

    public static <T> Result<T> error() {
        return new Result<T>(Constant.ERROR);
    }

    public static <T> Result<T> error(String msg) {
        return new Result<T>(Constant.ERROR, msg);
    }

    public static <T> Result<T> error(T data) {
        return new Result<T>(Constant.ERROR, data);
    }
}
