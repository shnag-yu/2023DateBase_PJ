package com.example.demo.util;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Result<T> {
    private int code;
    private String msg;
    private T data;

    public Result(T data) {this.data = data;}

    public static Result success() {
        Result result = new Result<>();
        result.setCode(200);
        result.setMsg("成功");
        return result;
    }

    public static <T> Result<T> success(T data) {
        Result result = new Result<>(data);
        result.setCode(200);
        result.setMsg("成功");
        return result;
    }

    public static Result error(int code, String msg){
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }

    public void addError(int code, String massage){
        String str = this.getMsg() == null?"": this.getMsg();
        str += (str.isEmpty()?"":"，");
        this.setCode(code);
        this.setMsg(str + massage);
    }


}
