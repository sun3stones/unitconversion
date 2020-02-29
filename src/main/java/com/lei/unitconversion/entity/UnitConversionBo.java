package com.lei.unitconversion.entity;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.lei.unitconversion.utils.FormulaUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
/**
 * 单位换算业务类
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class UnitConversionBo {

    private UnitConversion from;
    private UnitConversion to;
    private BigDecimal fromValue;
    private BigDecimal toValue;
    private boolean midFlag;

    public UnitConversionBo unitConversion(){
        BigDecimal formCoef = from.getUnitCoef();
        BigDecimal toCoef = to.getUnitCoef();
        String formFormula = from.getUnitFormula();
        String toFormula = to.getUnitFormula();
        if (!midFlag){//不需要中间单位
            if (StringUtils.isEmpty(formFormula) && StringUtils.isEmpty(toFormula)){
                //目标值 = 目标系数/转换系数 * 转换值
                toValue = toCoef.divide(formCoef,10, BigDecimal.ROUND_HALF_UP).multiply(fromValue);
            }else if (!StringUtils.isEmpty(formFormula)){//转换单位有系数，目标单位为基本单位
                String formula = formFormula.split(";")[0];
                fromValue = FormulaUtil.calculate(formula,fromValue);
                toValue = toCoef.divide(formCoef,10, BigDecimal.ROUND_HALF_UP).multiply(fromValue);
            }else{//目标单位有系数，转换单位为基本单位
                String formula = toFormula.split(";")[1];
                fromValue = FormulaUtil.calculate(formula,fromValue);
                toValue = toCoef.divide(formCoef,10, BigDecimal.ROUND_HALF_UP).multiply(fromValue);
            }
        }else{//需要中间单位转换
            String formula1 = formFormula.split(";")[0];
            fromValue = FormulaUtil.calculate(formula1,fromValue);
            toValue = toCoef.divide(formCoef,10, BigDecimal.ROUND_HALF_UP).multiply(fromValue);
            String formula2 = toFormula.split(";")[1];
            toValue = FormulaUtil.calculate(formula2,toValue);
            toValue = toCoef.divide(formCoef,10, BigDecimal.ROUND_HALF_UP).multiply(toValue);
        }
        toValue = toValue.stripTrailingZeros();//去掉小数点后面的0
        return this;
    }



}
