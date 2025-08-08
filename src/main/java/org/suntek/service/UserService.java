package org.suntek.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.suntek.model.dto.UserDto;
import org.suntek.model.dto.UserListQo;
import org.suntek.model.entity.UserEntity;
import org.suntek.model.vo.PageResponseResult;
import org.suntek.model.vo.ResponseResult;

/**
 * 用户服务接口
 *
 * @author GitHub Copilot
 * @date 2025-08-06
 */
public interface UserService extends IService<UserEntity> {

    /**
     * 根据用户账号查询用户信息
     *
     * @param userCode 用户账号
     * @return 用户信息
     */
    ResponseResult<UserDto> getUserInfo(String userCode);

    /**
     * 分页查询用户列表
     *
     * @param keyword 关键字检索
     * @param pageNum 页码
     * @param pageSize 每页条数
     * @return 用户列表
     */
    PageResponseResult<UserDto> getUserList(String keyword, Integer pageNum, Integer pageSize);

    /**
     * 分页查询用户列表
     *
     * @param userListQo 查询条件
     * @return 用户列表
     */
    PageResponseResult<UserDto> getUserList(UserListQo userListQo);

    /**
     * 添加用户
     *
     * @param userDto 用户信息
     * @return 操作结果
     */
    ResponseResult<String> addUser(UserDto userDto);

    /**
     * 更新用户信息
     *
     * @param userDto 用户信息
     * @return 操作结果
     */
    ResponseResult<String> updateUser(UserDto userDto);

    /**
     * 删除用户
     *
     * @param tid 用户ID
     * @return 操作结果
     */
    ResponseResult<String> deleteUser(Long tid);
}
