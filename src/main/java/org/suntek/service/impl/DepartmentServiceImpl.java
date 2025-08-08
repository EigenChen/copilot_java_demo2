package org.suntek.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.suntek.mapper.DepartmentMapper;
import org.suntek.model.dto.DepartmentDto;
import org.suntek.model.entity.DepartmentEntity;
import org.suntek.model.vo.ResponseResult;

import java.util.ArrayList;
import java.util.List;

/**
 * 部门服务实现类
 * 
 * @author Claude Sonnet 4
 * @date 2025-08-07
 */
@Service
@Slf4j
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, DepartmentEntity> implements org.suntek.service.DepartmentService {
    
    @Autowired
    private DepartmentMapper departmentMapper;
    
    @Override
    public ResponseResult<List<DepartmentDto>> getDepartmentList() {
        try {
            log.info("开始查询部门列表");
            List<DepartmentEntity> departmentEntities = departmentMapper.selectList(null);
            List<DepartmentDto> departmentDtos = new ArrayList<>();
            
            for (DepartmentEntity entity : departmentEntities) {
                DepartmentDto dto = new DepartmentDto();
                BeanUtils.copyProperties(entity, dto);
                departmentDtos.add(dto);
            }
            
            log.info("查询部门列表成功，共查询到{}条记录", departmentDtos.size());
            return ResponseResult.success(departmentDtos, "查询部门列表成功");
        } catch (Exception e) {
            log.error("查询部门列表失败", e);
            return ResponseResult.fail("查询部门列表失败");
        }
    }
    
    @Override
    public ResponseResult<DepartmentDto> getDepartmentByCode(String deptCode) {
        try {
            log.info("开始根据部门编号查询部门信息，部门编号：{}", deptCode);
            QueryWrapper<DepartmentEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("DEPT_CODE", deptCode);
            DepartmentEntity departmentEntity = departmentMapper.selectOne(queryWrapper);
            
            if (departmentEntity == null) {
                log.warn("未找到部门编号为{}的部门信息", deptCode);
                return ResponseResult.fail("未找到指定部门信息");
            }
            
            DepartmentDto departmentDto = new DepartmentDto();
            BeanUtils.copyProperties(departmentEntity, departmentDto);
            
            log.info("根据部门编号查询部门信息成功，部门编号：{}", deptCode);
            return ResponseResult.success(departmentDto, "查询部门信息成功");
        } catch (Exception e) {
            log.error("根据部门编号查询部门信息失败，部门编号：{}", deptCode, e);
            return ResponseResult.fail("查询部门信息失败");
        }
    }
    
    @Override
    @Transactional
    public ResponseResult<String> addDepartment(DepartmentDto departmentDto) {
        try {
            log.info("开始添加部门，部门编号：{}", departmentDto.getDeptCode());
            
            // 检查部门编号是否已存在
            QueryWrapper<DepartmentEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("DEPT_CODE", departmentDto.getDeptCode());
            DepartmentEntity existingDepartment = departmentMapper.selectOne(queryWrapper);
            
            if (existingDepartment != null) {
                log.warn("部门编号{}已存在", departmentDto.getDeptCode());
                return ResponseResult.fail("部门编号已存在");
            }
            
            DepartmentEntity departmentEntity = new DepartmentEntity();
            BeanUtils.copyProperties(departmentDto, departmentEntity);
            departmentEntity.setTid(null); // 确保ID为空，让数据库自动生成
            
            int result = departmentMapper.insert(departmentEntity);
            if (result > 0) {
                log.info("添加部门成功，部门编号：{}", departmentDto.getDeptCode());
                return ResponseResult.success("添加部门成功");
            } else {
                log.error("添加部门失败，部门编号：{}", departmentDto.getDeptCode());
                return ResponseResult.fail("添加部门失败");
            }
        } catch (Exception e) {
            log.error("添加部门失败，部门编号：{}", departmentDto.getDeptCode(), e);
            return ResponseResult.fail("添加部门失败");
        }
    }
    
    @Override
    @Transactional
    public ResponseResult<String> updateDepartment(DepartmentDto departmentDto) {
        try {
            log.info("开始更新部门信息，部门ID：{}", departmentDto.getTid());
            
            // 检查部门是否存在
            DepartmentEntity existingDepartment = departmentMapper.selectById(departmentDto.getTid());
            if (existingDepartment == null) {
                log.warn("未找到部门ID为{}的部门信息", departmentDto.getTid());
                return ResponseResult.fail("未找到指定部门信息");
            }
            
            // 检查部门编号是否被其他部门使用
            QueryWrapper<DepartmentEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("DEPT_CODE", departmentDto.getDeptCode());
            queryWrapper.ne("TID", departmentDto.getTid());
            DepartmentEntity duplicateDepartment = departmentMapper.selectOne(queryWrapper);
            
            if (duplicateDepartment != null) {
                log.warn("部门编号{}已被其他部门使用", departmentDto.getDeptCode());
                return ResponseResult.fail("部门编号已被其他部门使用");
            }
            
            DepartmentEntity departmentEntity = new DepartmentEntity();
            BeanUtils.copyProperties(departmentDto, departmentEntity);
            
            int result = departmentMapper.updateById(departmentEntity);
            if (result > 0) {
                log.info("更新部门信息成功，部门ID：{}", departmentDto.getTid());
                return ResponseResult.success("更新部门信息成功");
            } else {
                log.error("更新部门信息失败，部门ID：{}", departmentDto.getTid());
                return ResponseResult.fail("更新部门信息失败");
            }
        } catch (Exception e) {
            log.error("更新部门信息失败，部门ID：{}", departmentDto.getTid(), e);
            return ResponseResult.fail("更新部门信息失败");
        }
    }
    
    @Override
    @Transactional
    public ResponseResult<String> deleteDepartment(Long tid) {
        try {
            log.info("开始删除部门，部门ID：{}", tid);
            
            // 检查部门是否存在
            DepartmentEntity existingDepartment = departmentMapper.selectById(tid);
            if (existingDepartment == null) {
                log.warn("未找到部门ID为{}的部门信息", tid);
                return ResponseResult.fail("未找到指定部门信息");
            }
            
            // TODO: 在实际应用中，应该检查是否有用户属于该部门，如果有则不允许删除
            
            int result = departmentMapper.deleteById(tid);
            if (result > 0) {
                log.info("删除部门成功，部门ID：{}", tid);
                return ResponseResult.success("删除部门成功");
            } else {
                log.error("删除部门失败，部门ID：{}", tid);
                return ResponseResult.fail("删除部门失败");
            }
        } catch (Exception e) {
            log.error("删除部门失败，部门ID：{}", tid, e);
            return ResponseResult.fail("删除部门失败");
        }
    }
}
