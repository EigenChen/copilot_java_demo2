package org.suntek.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.suntek.mapper.DepartmentMapper;
import org.suntek.model.dto.DepartmentDto;
import org.suntek.model.entity.DepartmentEntity;
import org.suntek.model.vo.ResponseResult;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * 部门服务实现类单元测试
 * 
 * @author Claude Sonnet 4
 * @date 2025-08-07
 */
@ExtendWith(MockitoExtension.class)
class DepartmentServiceImplTest {

    @Mock
    private DepartmentMapper departmentMapper;

    @InjectMocks
    private DepartmentServiceImpl departmentService;

    private DepartmentDto departmentDto;
    private DepartmentEntity departmentEntity;

    @BeforeEach
    void setUp() {
        // 初始化测试数据
        departmentDto = new DepartmentDto();
        departmentDto.setDeptCode("IT001");
        departmentDto.setDeptName("信息技术部");

        departmentEntity = new DepartmentEntity();
        departmentEntity.setTid(1L);
        departmentEntity.setDeptCode("IT001");
        departmentEntity.setDeptName("信息技术部");
        departmentEntity.setCreateTime(LocalDateTime.now());
        departmentEntity.setUpdateTime(LocalDateTime.now());
    }

    /**
     * 测试获取部门列表 - 成功场景
     */
    @Test
    void testGetDepartmentList_Success() {
        // 准备测试数据
        List<DepartmentEntity> departmentList = Arrays.asList(
            departmentEntity,
            createDepartmentEntity(2L, "HR001", "人力资源部")
        );
        when(departmentMapper.selectList(any())).thenReturn(departmentList);

        // 执行测试
        ResponseResult<List<DepartmentDto>> result = departmentService.getDepartmentList();

        // 验证结果
        assertNotNull(result);
        assertEquals(ResponseResult.STATUS_CODE_SUCCESS, result.getStatusCode());
        assertEquals("查询部门列表成功", result.getOpDesc());
        assertNotNull(result.getData());
        assertEquals(2, result.getData().size());
        assertEquals("IT001", result.getData().get(0).getDeptCode());
        assertEquals("信息技术部", result.getData().get(0).getDeptName());

        // 验证方法调用
        verify(departmentMapper, times(1)).selectList(any());
    }

    /**
     * 测试获取部门列表 - 空列表场景
     */
    @Test
    void testGetDepartmentList_EmptyList() {
        // 准备测试数据
        when(departmentMapper.selectList(any())).thenReturn(Arrays.asList());

        // 执行测试
        ResponseResult<List<DepartmentDto>> result = departmentService.getDepartmentList();

        // 验证结果
        assertNotNull(result);
        assertEquals(ResponseResult.STATUS_CODE_SUCCESS, result.getStatusCode());
        assertEquals("查询部门列表成功", result.getOpDesc());
        assertNotNull(result.getData());
        assertEquals(0, result.getData().size());

        // 验证方法调用
        verify(departmentMapper, times(1)).selectList(any());
    }

    /**
     * 测试根据部门编号查询 - 成功场景
     */
    @Test
    void testGetDepartmentByCode_Success() {
        // 准备测试数据
        when(departmentMapper.selectOne(any())).thenReturn(departmentEntity);

        // 执行测试
        ResponseResult<DepartmentDto> result = departmentService.getDepartmentByCode("IT001");

        // 验证结果
        assertNotNull(result);
        assertEquals(ResponseResult.STATUS_CODE_SUCCESS, result.getStatusCode());
        assertEquals("查询部门信息成功", result.getOpDesc());
        assertNotNull(result.getData());
        assertEquals("IT001", result.getData().getDeptCode());
        assertEquals("信息技术部", result.getData().getDeptName());

        // 验证方法调用
        verify(departmentMapper, times(1)).selectOne(any());
    }

    /**
     * 测试根据部门编号查询 - 未找到部门
     */
    @Test
    void testGetDepartmentByCode_NotFound() {
        // 准备测试数据
        when(departmentMapper.selectOne(any())).thenReturn(null);

        // 执行测试
        ResponseResult<DepartmentDto> result = departmentService.getDepartmentByCode("NOTEXIST");

        // 验证结果
        assertNotNull(result);
        assertEquals(ResponseResult.STATUS_CODE_FAIL, result.getStatusCode());
        assertEquals("未找到指定部门信息", result.getOpDesc());

        // 验证方法调用
        verify(departmentMapper, times(1)).selectOne(any());
    }

    /**
     * 测试新增部门 - 成功场景
     */
    @Test
    void testAddDepartment_Success() {
        // 准备测试数据
        when(departmentMapper.selectOne(any())).thenReturn(null); // 部门编号不存在
        when(departmentMapper.insert(any(DepartmentEntity.class))).thenReturn(1);

        // 执行测试
        ResponseResult<String> result = departmentService.addDepartment(departmentDto);

        // 验证结果
        assertNotNull(result);
        assertEquals(ResponseResult.STATUS_CODE_SUCCESS, result.getStatusCode());
        assertEquals("添加部门成功", result.getOpDesc());

        // 验证方法调用
        verify(departmentMapper, times(1)).selectOne(any());
        verify(departmentMapper, times(1)).insert(any(DepartmentEntity.class));
    }

    /**
     * 测试新增部门 - 部门编号已存在
     */
    @Test
    void testAddDepartment_DeptCodeExists() {
        // 准备测试数据
        DepartmentEntity existingDepartment = new DepartmentEntity();
        existingDepartment.setTid(2L);
        existingDepartment.setDeptCode("IT001");
        existingDepartment.setDeptName("已存在的部门");
        
        when(departmentMapper.selectOne(any())).thenReturn(existingDepartment);

        // 执行测试
        ResponseResult<String> result = departmentService.addDepartment(departmentDto);

        // 验证结果
        assertNotNull(result);
        assertEquals(ResponseResult.STATUS_CODE_FAIL, result.getStatusCode());
        assertEquals("部门编号已存在", result.getOpDesc());

        // 验证方法调用
        verify(departmentMapper, times(1)).selectOne(any());
        verify(departmentMapper, never()).insert(any(DepartmentEntity.class));
    }

    /**
     * 测试更新部门 - 成功场景
     */
    @Test
    void testUpdateDepartment_Success() {
        // 准备测试数据
        DepartmentEntity mockDepartmentEntity = new DepartmentEntity();
        mockDepartmentEntity.setTid(1L);
        mockDepartmentEntity.setDeptCode("OTHER001");
        mockDepartmentEntity.setDeptName("其他部门");

        when(departmentMapper.selectOne(any())).thenReturn(null); // 部门不存在
        when(departmentMapper.selectById(1L)).thenReturn(mockDepartmentEntity);
        when(departmentMapper.updateById(any(DepartmentEntity.class))).thenReturn(1);

        departmentDto.setTid(1L);

        // 执行测试
        ResponseResult<String> result = departmentService.updateDepartment(departmentDto);

        // 验证结果
        assertNotNull(result);
        assertEquals(ResponseResult.STATUS_CODE_SUCCESS, result.getStatusCode());
        assertEquals("更新部门信息成功", result.getOpDesc());

        // 验证方法调用
        verify(departmentMapper, times(1)).selectOne(any());
        verify(departmentMapper, times(1)).selectById(1L);
        verify(departmentMapper, times(1)).updateById(any(DepartmentEntity.class));
    }

    /**
     * 测试更新部门 - 部门编号已存在（其他部门使用）
     */
    @Test
    void testUpdateDepartment_DeptCodeExistsInOtherDept() {
        // 准备测试数据
        DepartmentEntity existingDepartment = new DepartmentEntity();
        existingDepartment.setTid(2L); // 不同的部门ID
        existingDepartment.setDeptCode("IT001");
        existingDepartment.setDeptName("已存在的部门");

        when(departmentMapper.selectOne(any())).thenReturn(existingDepartment); // 部门已存在

        departmentDto.setTid(1L);

        // 执行测试
        ResponseResult<String> result = departmentService.updateDepartment(departmentDto);

        // 验证结果
        assertNotNull(result);
        assertEquals(ResponseResult.STATUS_CODE_FAIL, result.getStatusCode());
        assertEquals("部门编号已被其他部门使用", result.getOpDesc());

        // 验证方法调用
        verify(departmentMapper, times(1)).selectOne(any());
        verify(departmentMapper, never()).updateById(any(DepartmentEntity.class));
    }

    /**
     * 测试删除部门 - 成功场景
     */
    @Test
    void testDeleteDepartment_Success() {
        // 准备测试数据
        when(departmentMapper.deleteById(1L)).thenReturn(1);

        // 执行测试
        ResponseResult<String> result = departmentService.deleteDepartment(1L);

        // 验证结果
        assertNotNull(result);
        assertEquals(ResponseResult.STATUS_CODE_SUCCESS, result.getStatusCode());
        assertEquals("删除部门成功", result.getOpDesc());

        // 验证方法调用
        verify(departmentMapper, times(1)).deleteById(1L);
    }

    /**
     * 测试删除部门 - 删除失败
     */
    @Test
    void testDeleteDepartment_Failed() {
        // 准备测试数据
        when(departmentMapper.deleteById(1L)).thenReturn(0);

        // 执行测试
        ResponseResult<String> result = departmentService.deleteDepartment(1L);

        // 验证结果
        assertNotNull(result);
        assertEquals(ResponseResult.STATUS_CODE_FAIL, result.getStatusCode());
        assertEquals("删除失败", result.getOpDesc());

        // 验证方法调用
        verify(departmentMapper, times(1)).deleteById(1L);
    }

    /**
     * 创建部门实体的辅助方法
     */
    private DepartmentEntity createDepartmentEntity(Long deptId, String deptCode, String deptName) {
        DepartmentEntity entity = new DepartmentEntity();
        entity.setTid(deptId);
        entity.setDeptCode(deptCode);
        entity.setDeptName(deptName);
        entity.setCreateTime(LocalDateTime.now());
        entity.setUpdateTime(LocalDateTime.now());
        return entity;
    }
}