package com.hqq.ruleservice.model.domain;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by huqiaoqian on 2020/11/18
 */
@Data
public class Scene implements Serializable {

    private static final long serialVersionUID = 8454336624128482137L;
    private long sceneId;

    private String name;

    private String description;

    private BigDecimal longitude;//经度

    private BigDecimal latitude;//纬度

    private int status;//场景是否启用，0：启用；1：禁用
}
