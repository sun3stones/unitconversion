package com.lei.unitconversion.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;

import com.lei.unitconversion.utils.validinterface.Delete;
import com.lei.unitconversion.utils.validinterface.Insert;
import com.lei.unitconversion.utils.validinterface.Update;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * <p>
 * 
 * </p>
 *
 * @author sunlei
 * @since 2020-02-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="单位对象",description="单位对象")
public class UnitConversion implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 自增主键
     */
    @TableId(value = "unit_id", type = IdType.AUTO)
    @NotNull(message = "主键不能为空", groups = {Update.class,Delete.class})
    @ApiModelProperty(hidden = true)
    private Long unitId;

    /**
     * 单位类型
     */
    @NotEmpty(message = "单位类型不能为空", groups = {Insert.class})
    @ApiModelProperty(value="单位类型",name="unitType",example="length",required = true)
    private String unitType;

    /**
     * 单位名称
     */
    @NotEmpty(message = "单位名称不能为空", groups = {Insert.class})
    @ApiModelProperty(value="单位名称",name="unitName",example="km",required = true)
    private String unitName;

    /**
     * 单位类型（中文）
     */
    @NotEmpty(message = "单位类型（中文）不能为空", groups = {Insert.class})
    @ApiModelProperty(value="单位类型（中文）",name="unitTypeCn",example="长度",required = true)
    private String unitTypeCn;

    /**
     * 单位名称（中文）
     */
    @NotEmpty(message = "单位名称（中文）不能为空", groups = {Insert.class})
    @ApiModelProperty(value="单位名称（中文）",name="unitNameCn",example="千米",required = true)
    private String unitNameCn;

    /**
     * 单位系数
     */
    @NotNull(message = "单位系数不能为空", groups = {Insert.class})
    @Length(max = 20)
    @ApiModelProperty(value="单位系数",name="unitCoef",example="0.01",required = true)
    private BigDecimal unitCoef;

    /**
     * 单位转换公式
     */
    @ApiModelProperty(value="单位转换公式",name="unitFormula",example="X-273.15;X+273.15")
    private String unitFormula;

    /**
     * 是否为中间单位（0否，1是）
     */
    @ApiModelProperty(value="是否为中间单位（0否，1是）",name="midFlag",example="1",required = true,
        notes = "一种类型只能有一个中间单位")
    private Integer midFlag;

    @ApiModelProperty(hidden = true)
    private LocalDateTime createdTime;
    @ApiModelProperty(hidden = true)
    private LocalDateTime modifyTime;


}
