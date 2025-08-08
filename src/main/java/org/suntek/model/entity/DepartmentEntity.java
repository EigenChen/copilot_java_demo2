package org.suntek.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 部门实体类
 * 
 * @author Claude Sonnet 4
 * @date 2025-08-07
 */
@Data
@TableName("T_DEPARTMENT")
public class DepartmentEntity {
    
    /**
     * 主键ID
     */
    @TableId(value = "TID", type = IdType.AUTO)
    private Long tid;
    
    /**
     * 部门编号
     */
    private String deptCode;
    
    /**
     * 部门名称
     */
    private String deptName;
    
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}
