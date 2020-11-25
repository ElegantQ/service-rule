package com.hqq.ruleservice.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by huqiaoqian on 2020/11/18
 */
@Data
@ApiModel
public class RuleDto implements Serializable {
    private static final long serialVersionUID = -1122480004086250673L;

    @ApiModelProperty("租户id")
    private long userId;

    @ApiModelProperty("规则名称")
    private String name;

//    @ApiModelProperty("设备名字")
//    private String deviceName;

    @ApiModelProperty("规则是否激活，0：激活；1：关闭")
    private int status;

    @ApiModelProperty("规则内容")
    private String content;


}
