package org.suntek.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 部门数据传输对象
 * 
 * @author Claude Sonnet 4
 * @date 2025-08-07
 */
@Data
@ApiModel("部门信息")
public class DepartmentDto {
    
    @ApiModelProperty("主键ID")
    private Long tid;
    
    @ApiModelProperty("部门编号")
    private String deptCode;
    
    @ApiModelProperty("部门名称")
    private String deptName;
    
    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;
    
    @ApiModelProperty("更新时间")
    private LocalDateTime updateTime;
}
