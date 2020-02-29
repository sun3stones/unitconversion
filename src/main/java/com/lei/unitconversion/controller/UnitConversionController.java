package com.lei.unitconversion.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.lei.unitconversion.common.ResultCode;
import com.lei.unitconversion.common.UnitResult;
import com.lei.unitconversion.entity.UnitConversion;
import com.lei.unitconversion.entity.UnitConversionBo;
import com.lei.unitconversion.service.IUnitConversionService;
import com.lei.unitconversion.utils.validinterface.Delete;
import com.lei.unitconversion.utils.validinterface.Insert;
import com.lei.unitconversion.utils.validinterface.Update;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.List;

@RestController
@Api(value = "单位换算控制层",description = "单位换算控制层")
public class UnitConversionController {

    private final static Logger LOGGER = LoggerFactory.getLogger(UnitConversionController.class);

    @Autowired
    private IUnitConversionService conversionService;

    @PostMapping("saveUnit")
    @ApiOperation(value = "新增一个单位")
    public JSONObject saveUnit(@Validated({Insert.class}) UnitConversion unitConversion){
        UnitResult result = new UnitResult();
        if (StringUtils.isNotEmpty(unitConversion.getUnitFormula())){
            unitConversion.setUnitFormula(unitConversion.getUnitFormula().replaceAll("%","/").replaceAll("@","+"));
        }
        if (conversionService.save(unitConversion)){
            result.setCode(ResultCode.SUCCESS.getCode());
            result.setMsg(ResultCode.SUCCESS.getMsg());
        }else{
            result.setCode(ResultCode.Program.getCode());
            result.setMsg(ResultCode.Program.getMsg());
        }
        return result.toJson();
    }

    @GetMapping("removeUnit")
    @ApiOperation(value = "根据主键id移除一个单位")
    @ApiImplicitParam(value="主键id",name="unitId",example="1",required = true,paramType = "path")
    public JSONObject removeUnit(@Validated({Delete.class}) Integer unitId){
        UnitResult result = new UnitResult();
        if (conversionService.removeById(unitId)){
            result.setCode(ResultCode.SUCCESS.getCode());
            result.setMsg(ResultCode.SUCCESS.getMsg());
        }else{
            result.setCode(ResultCode.Program.getCode());
            result.setMsg(ResultCode.Program.getMsg());
        }
        return result.toJson();
    }
    @PostMapping("updateUnit")
    @ApiOperation(value = "根据id更新一个单位")
    public JSONObject updateUnit(@Validated({Update.class}) UnitConversion unitConversion){
        UnitResult result = new UnitResult();
        if (StringUtils.isNotEmpty(unitConversion.getUnitFormula())){
            unitConversion.setUnitFormula(unitConversion.getUnitFormula().replaceAll("%","/").replaceAll("@","+"));
        }
        if (conversionService.updateById(unitConversion)){
            result.setCode(ResultCode.SUCCESS.getCode());
            result.setMsg(ResultCode.SUCCESS.getMsg());
        }else{
            result.setCode(ResultCode.Program.getCode());
            result.setMsg(ResultCode.Program.getMsg());
        }
        return result.toJson();
    }
    @GetMapping("queryUnit")
    @ApiOperation(value = "按条件查询单位")
    public JSONObject queryUnit(UnitConversion unitConversion){
        UnitResult result = new UnitResult();
        QueryWrapper queryWrapper = new QueryWrapper();
        if(unitConversion != null){
            if(StringUtils.checkValNotNull(unitConversion.getUnitId())){
                queryWrapper.eq("unit_id",unitConversion.getUnitId());
            }
            if(StringUtils.checkValNotNull(unitConversion.getUnitType())){
                queryWrapper.eq("unit_type",unitConversion.getUnitType());
            }
            if(StringUtils.checkValNotNull(unitConversion.getUnitName())){
                queryWrapper.eq("unit_name",unitConversion.getUnitName());
            }
            if(StringUtils.checkValNotNull(unitConversion.getUnitTypeCn())){
                queryWrapper.eq("unit_type_cn",unitConversion.getUnitTypeCn());
            }
            if(StringUtils.checkValNotNull(unitConversion.getUnitNameCn())){
                queryWrapper.eq("unit_name_cn",unitConversion.getUnitNameCn());
            }
        }
        List list = conversionService.list(queryWrapper);
        result.setCode(ResultCode.SUCCESS.getCode());
        result.setMsg(ResultCode.SUCCESS.getMsg());
        result.setData(list);
        return result.toJson();
    }

    @GetMapping("unitConversion")
    @ApiOperation(value = "单位换算")
    @ApiImplicitParams({
        @ApiImplicitParam(value="单位类型",name="unitType",example="temp",required = true,paramType = "query"),
        @ApiImplicitParam(value="转换单位名称", name = "unitNameFrom", example = "f", required = true,paramType = "query"),
        @ApiImplicitParam(value="目标单位名称",name="unitNameTo",example="k",required = true,paramType = "query"),
        @ApiImplicitParam(value="转换单位值",name="unitValueFrom",example="1",required = true,paramType = "query")
    })
    public JSONObject unitConversion(@NotEmpty(message = "单位类型不能为空") String unitType,
                                     @NotEmpty(message = "转换单位名称不能为空") String unitNameFrom,
                                     @NotEmpty(message = "目标单位名称不能为空") String unitNameTo,
                                     @NotNull(message = "转换单位值不能为空") BigDecimal unitValueFrom){
        UnitResult result = new UnitResult();
        QueryWrapper queryWrapper1 = new QueryWrapper();
        queryWrapper1.eq("unit_type",unitType);
        queryWrapper1.eq("unit_name",unitNameFrom);
        UnitConversion from = conversionService.getOne(queryWrapper1);
        if(from == null){
            result.setCode(ResultCode.Bussiness.getCode());
            result.setMsg("转换单位不存在");
            return result.toJson();
        }
        QueryWrapper queryWrapper2 = new QueryWrapper();
        queryWrapper2.eq("unit_type",unitType);
        queryWrapper2.eq("unit_name",unitNameTo);
        UnitConversion to = conversionService.getOne(queryWrapper2);
        if(to == null){
            result.setCode(ResultCode.Bussiness.getCode());
            result.setMsg("目标单位不存在");
            return result.toJson();
        }
        UnitConversionBo bo = new UnitConversionBo();
        bo.setFrom(from);
        bo.setTo(to);
        bo.setFromValue(unitValueFrom);
        //需要中间单位转换的情况
        //转换单位和目标单位存在转换公式，并且都不是中间单位
        if((StringUtils.isNotEmpty(from.getUnitFormula())
                || StringUtils.isNotEmpty(to.getUnitFormula()))
                && 0 == from.getMidFlag() && 0 == to.getMidFlag()){
            bo.setMidFlag(true);
        }
        bo = bo.unitConversion();
        LOGGER.info("单位转换具体参数:{}",JSONObject.toJSON(bo));
        result.setCode(ResultCode.SUCCESS.getCode());
        result.setMsg(ResultCode.SUCCESS.getMsg());
        result.setData(bo.getToValue().toPlainString());
        return result.toJson();
    }
}
