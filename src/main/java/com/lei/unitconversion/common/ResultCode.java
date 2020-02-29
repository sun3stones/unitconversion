package com.lei.unitconversion.common;

public enum ResultCode {

    SUCCESS("0000","操作成功"),
    Param("1000","参数异常"),
    Program("2000","程序异常"),
    DataSource("3000","数据库异常"),
    Bussiness("4000","业务异常");

    private String code;
    private String msg;

    ResultCode(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
