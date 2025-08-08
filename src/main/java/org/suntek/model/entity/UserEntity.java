package org.suntek.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 用户实体类
 *
 * @author GitHub Copilot
 * @date 2025-08-06
 */
@Data
@TableName("T_USER")
public class UserEntity {

    /**
     * 主键ID
     */
    @TableId(value = "TID", type = IdType.AUTO)
    private Long tid;

    /**
     * 用户账号
     */
    private String userCode;

    /**
     * 用户姓名
     */
    private String userName;

    /**
     * 出生日期
     */
    private Date birthDate;

    /**
     * 性别：1-男，2-女
     */
    private String gender;

    /**
     * 民族
     */
    private String nation;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 所属部门ID
     */
    private Long deptId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;
}
