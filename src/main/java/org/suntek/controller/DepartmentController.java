package org.suntek.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.suntek.model.dto.DepartmentDto;
import org.suntek.model.vo.ResponseResult;
import org.suntek.service.DepartmentService;

import java.util.List;

/**
 * 部门管理控制器
 * 
 * @author Claude Sonnet 4
 * @date 2025-08-07
 */
@Api(tags = "部门管理")
@RestController
@RequestMapping("/api/department")
public class DepartmentController {
    
    @Autowired
    private DepartmentService departmentService;
    
    @ApiOperation(value = "获取部门列表")
    @RequestMapping(value = "getDepartmentList", method = RequestMethod.GET)
    public ResponseResult<List<DepartmentDto>> getDepartmentList() {
        return departmentService.getDepartmentList();
    }
    
    @ApiOperation(value = "根据部门编号查询部门信息")
    @RequestMapping(value = "getDepartmentByCode", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "deptCode", value = "部门编号", dataType = "String", required = true)
    })
    public ResponseResult<DepartmentDto> getDepartmentByCode(String deptCode) {
        return departmentService.getDepartmentByCode(deptCode);
    }
    
    @ApiOperation(value = "添加部门")
    @RequestMapping(value = "addDepartment", method = RequestMethod.POST)
    public ResponseResult<String> addDepartment(@RequestBody DepartmentDto departmentDto) {
        return departmentService.addDepartment(departmentDto);
    }
    
    @ApiOperation(value = "更新部门信息")
    @RequestMapping(value = "updateDepartment", method = RequestMethod.POST)
    public ResponseResult<String> updateDepartment(@RequestBody DepartmentDto departmentDto) {
        return departmentService.updateDepartment(departmentDto);
    }
    
    @ApiOperation(value = "删除部门")
    @RequestMapping(value = "deleteDepartment", method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tid", value = "部门ID", dataType = "Long", required = true)
    })
    public ResponseResult<String> deleteDepartment(Long tid) {
        return departmentService.deleteDepartment(tid);
    }
}
