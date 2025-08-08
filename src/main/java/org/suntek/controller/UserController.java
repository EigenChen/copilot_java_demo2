package org.suntek.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.suntek.model.dto.UserDto;
import org.suntek.model.dto.UserListQo;
import org.suntek.model.vo.PageResponseResult;
import org.suntek.model.vo.ResponseResult;
import org.suntek.service.UserService;

/**
 * 用户管理控制器
 *
 * @author GitHub Copilot
 * @date 2025-08-06
 */
@RestController
@RequestMapping("/api/user")
@Api(tags = "用户管理模块")
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "查询用户信息")
    @RequestMapping(value = "getUserInfo", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userCode", value = "用户账号", dataType = "String", required = true)
    })
    public ResponseResult<UserDto> getUserInfo(String userCode) {
        return userService.getUserInfo(userCode);
    }

    @ApiOperation(value = "分页查询用户列表")
    @RequestMapping(value = "getUserList", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "keyword", value = "关键字检索", dataType = "String"),
            @ApiImplicitParam(name = "gender", value = "性别筛选", dataType = "String"),
            @ApiImplicitParam(name = "pageNum", value = "分页查询，页码", dataType = "int", defaultValue = "1"),
            @ApiImplicitParam(name = "pageSize", value = "分页查询，每页条数", dataType = "int", defaultValue = "10")
    })
    public PageResponseResult<UserDto> getUserList(String keyword, String gender, Integer pageNum, Integer pageSize) {
        // 构建查询条件对象
        UserListQo userListQo = new UserListQo();
        userListQo.setKeyword(keyword);
        userListQo.setGender(gender);
        userListQo.setPageNum(pageNum);
        userListQo.setPageSize(pageSize);
        
        return userService.getUserList(userListQo);
    }

    @ApiOperation(value = "分页查询用户列表（复杂条件）")
    @RequestMapping(value = "getUserListByCondition", method = RequestMethod.POST)
    public PageResponseResult<UserDto> getUserListByCondition(@RequestBody UserListQo userListQo) {
        return userService.getUserList(userListQo);
    }

    @ApiOperation(value = "添加用户")
    @RequestMapping(value = "addUser", method = RequestMethod.POST)
    public ResponseResult<String> addUser(@RequestBody UserDto userDto) {
        return userService.addUser(userDto);
    }

    @ApiOperation(value = "更新用户信息")
    @RequestMapping(value = "updateUser", method = RequestMethod.POST)
    public ResponseResult<String> updateUser(@RequestBody UserDto userDto) {
        return userService.updateUser(userDto);
    }

    @ApiOperation(value = "删除用户")
    @RequestMapping(value = "deleteUser", method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tid", value = "用户ID", dataType = "Long", required = true)
    })
    public ResponseResult<String> deleteUser(@RequestParam Long tid) {
        return userService.deleteUser(tid);
    }
}
