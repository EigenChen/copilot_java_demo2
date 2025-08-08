package org.suntek.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.suntek.model.dto.DepartmentDto;
import org.suntek.model.entity.DepartmentEntity;
import org.suntek.model.vo.ResponseResult;

import java.util.List;

/**
 * 部门服务接口
 * 
 * @author Claude Sonnet 4
 * @date 2025-08-07
 */
public interface DepartmentService extends IService<DepartmentEntity> {
    
    /**
     * 获取所有部门列表
     * 
     * @return 部门列表
     */
    ResponseResult<List<DepartmentDto>> getDepartmentList();
    
    /**
     * 根据部门编号获取部门信息
     * 
     * @param deptCode 部门编号
     * @return 部门信息
     */
    ResponseResult<DepartmentDto> getDepartmentByCode(String deptCode);
    
    /**
     * 添加部门
     * 
     * @param departmentDto 部门信息
     * @return 操作结果
     */
    ResponseResult<String> addDepartment(DepartmentDto departmentDto);
    
    /**
     * 更新部门信息
     * 
     * @param departmentDto 部门信息
     * @return 操作结果
     */
    ResponseResult<String> updateDepartment(DepartmentDto departmentDto);
    
    /**
     * 删除部门
     * 
     * @param tid 部门ID
     * @return 操作结果
     */
    ResponseResult<String> deleteDepartment(Long tid);
}
