package org.suntek.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用户列表查询条件对象
 *
 * @author GitHub Copilot
 * @date 2025-08-06
 */
@Data
@ApiModel("用户列表查询条件")
public class UserListQo {

    @ApiModelProperty("关键字检索（用户账号、姓名、手机号）")
    private String keyword;

    @ApiModelProperty("性别：1-男，2-女")
    private String gender;

    @ApiModelProperty("民族")
    private String nation;

    @ApiModelProperty(value = "分页查询，页码", example = "1")
    private Integer pageNum = 1;

    @ApiModelProperty(value = "分页查询，每页条数", example = "10")
    private Integer pageSize = 10;
}
