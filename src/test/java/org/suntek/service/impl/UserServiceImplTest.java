package org.suntek.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.suntek.mapper.DepartmentMapper;
import org.suntek.mapper.UserMapper;
import org.suntek.model.dto.UserDto;
import org.suntek.model.entity.DepartmentEntity;
import org.suntek.model.entity.UserEntity;
import org.suntek.model.vo.ResponseResult;

import java.time.LocalDateTime;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * 用户服务实现类单元测试（包含部门信息集成测试）
 * 
 * @author Claude Sonnet 4
 * @date 2025-08-07
 */
@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserMapper userMapper;

    @Mock
    private DepartmentMapper departmentMapper;

    @InjectMocks
    private UserServiceImpl userService;

    private UserDto userDto;
    private UserEntity userEntity;
    private DepartmentEntity departmentEntity;

    @BeforeEach
    void setUp() {
        // 初始化部门测试数据
        departmentEntity = new DepartmentEntity();
        departmentEntity.setTid(1L);
        departmentEntity.setDeptCode("IT001");
        departmentEntity.setDeptName("信息技术部");
        departmentEntity.setCreateTime(LocalDateTime.now());
        departmentEntity.setUpdateTime(LocalDateTime.now());

        // 初始化用户测试数据
        userDto = new UserDto();
        userDto.setUserCode("USER001");
        userDto.setUserName("张三");
        userDto.setGender("1");
        userDto.setMobile("13800138000");
        userDto.setDeptId(1L);

        userEntity = new UserEntity();
        userEntity.setTid(1L);
        userEntity.setUserCode("USER001");
        userEntity.setUserName("张三");
        userEntity.setGender("1");
        userEntity.setMobile("13800138000");
        userEntity.setDeptId(1L);
        userEntity.setCreateTime(new Date());
        userEntity.setUpdateTime(new Date());
    }

    /**
     * 测试获取用户信息 - 包含部门信息
     */
    @Test
    void testGetUserInfo_WithDepartmentInfo() {
        // 准备测试数据
        when(userMapper.selectOne(any())).thenReturn(userEntity);
        when(departmentMapper.selectById(1L)).thenReturn(departmentEntity);

        // 执行测试
        ResponseResult<UserDto> result = userService.getUserInfo("USER001");

        // 验证结果
        assertNotNull(result);
        assertEquals(ResponseResult.STATUS_CODE_SUCCESS, result.getStatusCode());
        assertEquals("查询成功", result.getOpDesc());
        assertNotNull(result.getData());
        assertEquals("USER001", result.getData().getUserCode());
        assertEquals("张三", result.getData().getUserName());
        assertEquals(1L, result.getData().getDeptId());
        assertEquals("信息技术部", result.getData().getDeptName());

        // 验证方法调用
        verify(userMapper, times(1)).selectOne(any());
        verify(departmentMapper, times(1)).selectById(1L);
    }

    /**
     * 测试获取用户信息 - 用户不存在
     */
    @Test
    void testGetUserInfo_UserNotFound() {
        // 准备测试数据
        when(userMapper.selectOne(any())).thenReturn(null);

        // 执行测试
        ResponseResult<UserDto> result = userService.getUserInfo("NOTEXIST");

        // 验证结果
        assertNotNull(result);
        assertEquals(ResponseResult.STATUS_CODE_FAIL, result.getStatusCode());
        assertEquals("用户不存在", result.getOpDesc());

        // 验证方法调用
        verify(userMapper, times(1)).selectOne(any());
        verify(departmentMapper, never()).selectById(any());
    }

    /**
     * 测试获取用户信息 - 用户无部门
     */
    @Test
    void testGetUserInfo_WithoutDepartment() {
        // 准备测试数据 - 用户没有部门ID
        userEntity.setDeptId(null);
        when(userMapper.selectOne(any())).thenReturn(userEntity);

        // 执行测试
        ResponseResult<UserDto> result = userService.getUserInfo("USER001");

        // 验证结果
        assertNotNull(result);
        assertEquals(ResponseResult.STATUS_CODE_SUCCESS, result.getStatusCode());
        assertEquals("查询成功", result.getOpDesc());
        assertNotNull(result.getData());
        assertEquals("USER001", result.getData().getUserCode());
        assertEquals("张三", result.getData().getUserName());
        assertNull(result.getData().getDeptId());
        assertNull(result.getData().getDeptName());

        // 验证方法调用
        verify(userMapper, times(1)).selectOne(any());
        verify(departmentMapper, never()).selectById(any());
    }

    /**
     * 测试获取用户信息 - 部门不存在
     */
    @Test
    void testGetUserInfo_DepartmentNotFound() {
        // 准备测试数据
        when(userMapper.selectOne(any())).thenReturn(userEntity);
        when(departmentMapper.selectById(1L)).thenReturn(null);

        // 执行测试
        ResponseResult<UserDto> result = userService.getUserInfo("USER001");

        // 验证结果
        assertNotNull(result);
        assertEquals(ResponseResult.STATUS_CODE_SUCCESS, result.getStatusCode());
        assertEquals("查询成功", result.getOpDesc());
        assertNotNull(result.getData());
        assertEquals("USER001", result.getData().getUserCode());
        assertEquals("张三", result.getData().getUserName());
        assertEquals(1L, result.getData().getDeptId());
        assertNull(result.getData().getDeptName()); // 部门不存在时，部门名称为null

        // 验证方法调用
        verify(userMapper, times(1)).selectOne(any());
        verify(departmentMapper, times(1)).selectById(1L);
    }

    /**
     * 测试新增用户 - 指定部门ID
     */
    @Test
    void testAddUser_WithDepartmentId() {
        // 准备测试数据
        when(userMapper.selectOne(any())).thenReturn(null); // 用户编号不存在
        when(departmentMapper.selectById(1L)).thenReturn(departmentEntity); // 部门存在
        when(userMapper.insert(any(UserEntity.class))).thenReturn(1);

        // 执行测试
        ResponseResult<String> result = userService.addUser(userDto);

        // 验证结果
        assertNotNull(result);
        assertEquals(ResponseResult.STATUS_CODE_SUCCESS, result.getStatusCode());
        assertEquals("新增成功", result.getOpDesc());

        // 验证方法调用
        verify(userMapper, times(1)).selectOne(any());
        verify(departmentMapper, times(1)).selectById(1L);
        verify(userMapper, times(1)).insert(any(UserEntity.class));
    }

    /**
     * 测试新增用户 - 部门不存在
     */
    @Test
    void testAddUser_DepartmentNotFound() {
        // 准备测试数据
        when(userMapper.selectOne(any())).thenReturn(null); // 用户编号不存在
        when(departmentMapper.selectById(1L)).thenReturn(null); // 部门不存在

        // 执行测试
        ResponseResult<String> result = userService.addUser(userDto);

        // 验证结果
        assertNotNull(result);
        assertEquals(ResponseResult.STATUS_CODE_FAIL, result.getStatusCode());
        assertEquals("指定的部门不存在", result.getOpDesc());

        // 验证方法调用
        verify(userMapper, times(1)).selectOne(any());
        verify(departmentMapper, times(1)).selectById(1L);
        verify(userMapper, never()).insert(any(UserEntity.class));
    }

    /**
     * 测试新增用户 - 不指定部门ID
     */
    @Test
    void testAddUser_WithoutDepartmentId() {
        // 准备测试数据
        userDto.setDeptId(null);
        when(userMapper.selectOne(any())).thenReturn(null); // 用户编号不存在
        when(userMapper.insert(any(UserEntity.class))).thenReturn(1);

        // 执行测试
        ResponseResult<String> result = userService.addUser(userDto);

        // 验证结果
        assertNotNull(result);
        assertEquals(ResponseResult.STATUS_CODE_SUCCESS, result.getStatusCode());
        assertEquals("新增成功", result.getOpDesc());

        // 验证方法调用
        verify(userMapper, times(1)).selectOne(any());
        verify(departmentMapper, never()).selectById(any());
        verify(userMapper, times(1)).insert(any(UserEntity.class));
    }
}
