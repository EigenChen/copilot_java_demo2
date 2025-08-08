package org.suntek.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 用户数据传输对象
 *
 * @author GitHub Copilot
 * @date 2025-08-06
 */
@Data
@ApiModel("用户信息")
public class UserDto {

    @ApiModelProperty("主键ID")
    private Long tid;

    @ApiModelProperty("用户账号")
    private String userCode;

    @ApiModelProperty("用户姓名")
    private String userName;

    @ApiModelProperty("出生日期")
    private Date birthDate;

    @ApiModelProperty("性别：1-男，2-女")
    private String gender;

    @ApiModelProperty("民族")
    private String nation;

    @ApiModelProperty("手机号")
    private String mobile;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("所属部门ID")
    private Long deptId;

    @ApiModelProperty("所属部门名称")
    private String deptName;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("更新时间")
    private Date updateTime;
}
