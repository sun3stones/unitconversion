package com.lei.unitconversion.common;


import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "返回类")
public class UnitResult{
    @ApiModelProperty(value = "码值")
    private String code;
    @ApiModelProperty(value = "信息")
    private String msg;
    @ApiModelProperty(value = "数据")
    private Object data;


    public JSONObject toJson(){
        JSONObject json = new JSONObject(true);
        json.put("code",this.code);
        json.put("msg",this.msg);
        json.put("data",this.data);
        return  json;
    }
}
