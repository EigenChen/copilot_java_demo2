package org.suntek.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.suntek.mapper.UserMapper;
import org.suntek.mapper.DepartmentMapper;
import org.suntek.model.dto.UserDto;
import org.suntek.model.dto.UserListQo;
import org.suntek.model.entity.UserEntity;
import org.suntek.model.entity.DepartmentEntity;
import org.suntek.model.vo.PageResponseResult;
import org.suntek.model.vo.ResponseResult;
import org.suntek.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户服务实现类
 *
 * @author GitHub Copilot
 * @date 2025-08-06
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, UserEntity> implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private DepartmentMapper departmentMapper;

    @Override
    public ResponseResult<UserDto> getUserInfo(String userCode) {
        if (StrUtil.isBlank(userCode)) {
            return ResponseResult.fail("用户账号不能为空");
        }

        QueryWrapper<UserEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("USER_CODE", userCode);
        UserEntity userEntity = userMapper.selectOne(queryWrapper);

        if (userEntity == null) {
            return ResponseResult.fail("用户不存在");
        }

        UserDto userDto = BeanUtil.copyProperties(userEntity, UserDto.class);
        
        // 查询部门信息
        if (userEntity.getDeptId() != null) {
            DepartmentEntity departmentEntity = departmentMapper.selectById(userEntity.getDeptId());
            if (departmentEntity != null) {
                userDto.setDeptName(departmentEntity.getDeptName());
            }
        }
        
        return ResponseResult.success(userDto, "查询成功");
    }

    @Override
    public PageResponseResult<UserDto> getUserList(String keyword, Integer pageNum, Integer pageSize) {
        if (pageNum == null || pageNum <= 0) {
            pageNum = 1;
        }
        if (pageSize == null || pageSize <= 0) {
            pageSize = 10;
        }

        Page<UserEntity> page = new Page<>(pageNum, pageSize);
        QueryWrapper<UserEntity> queryWrapper = new QueryWrapper<>();

        if (StrUtil.isNotBlank(keyword)) {
            queryWrapper.and(wrapper -> wrapper
                    .like("USER_CODE", keyword)
                    .or()
                    .like("USER_NAME", keyword)
                    .or()
                    .like("MOBILE", keyword)
            );
        }

        queryWrapper.orderByDesc("TID");

        IPage<UserEntity> userPage = userMapper.selectPage(page, queryWrapper);

        List<UserDto> userDtoList = userPage.getRecords().stream()
                .map(userEntity -> {
                    UserDto dto = new UserDto();
                    BeanUtil.copyProperties(userEntity, dto);
                    
                    // 查询部门信息
                    if (userEntity.getDeptId() != null) {
                        DepartmentEntity departmentEntity = departmentMapper.selectById(userEntity.getDeptId());
                        if (departmentEntity != null) {
                            dto.setDeptName(departmentEntity.getDeptName());
                        }
                    }
                    
                    return dto;
                })
                .collect(Collectors.toList());

        PageResponseResult<UserDto> result = new PageResponseResult<>();
        result.setTotal(userPage.getTotal());
        result.setData(userDtoList);
        result.setPages((int) userPage.getPages());
        result.setPageNum((int) userPage.getCurrent());
        result.setPageSize((int) userPage.getSize());
        result.setStatusCode(ResponseResult.STATUS_CODE_SUCCESS);
        result.setOpCode(ResponseResult.OP_CODE_SUCCESS);
        result.setOpDesc("查询成功");

        return result;
    }

    @Override
    public PageResponseResult<UserDto> getUserList(UserListQo userListQo) {
        if (userListQo == null) {
            return PageResponseResult.fail("查询条件不能为空");
        }

        Integer pageNum = userListQo.getPageNum();
        Integer pageSize = userListQo.getPageSize();
        if (pageNum == null || pageNum <= 0) {
            pageNum = 1;
        }
        if (pageSize == null || pageSize <= 0) {
            pageSize = 10;
        }

        Page<UserEntity> page = new Page<>(pageNum, pageSize);
        QueryWrapper<UserEntity> queryWrapper = new QueryWrapper<>();

        if (StrUtil.isNotBlank(userListQo.getKeyword())) {
            queryWrapper.and(wrapper -> wrapper
                    .like("USER_CODE", userListQo.getKeyword())
                    .or()
                    .like("USER_NAME", userListQo.getKeyword())
                    .or()
                    .like("MOBILE", userListQo.getKeyword())
            );
        }

        if (StrUtil.isNotBlank(userListQo.getGender())) {
            queryWrapper.eq("GENDER", userListQo.getGender());
        }

        if (StrUtil.isNotBlank(userListQo.getNation())) {
            queryWrapper.eq("NATION", userListQo.getNation());
        }

        queryWrapper.orderByDesc("TID");

        IPage<UserEntity> userPage = userMapper.selectPage(page, queryWrapper);

        List<UserDto> userDtoList = userPage.getRecords().stream()
                .map(userEntity -> {
                    UserDto dto = new UserDto();
                    BeanUtil.copyProperties(userEntity, dto);
                    
                    // 查询部门信息
                    if (userEntity.getDeptId() != null) {
                        DepartmentEntity departmentEntity = departmentMapper.selectById(userEntity.getDeptId());
                        if (departmentEntity != null) {
                            dto.setDeptName(departmentEntity.getDeptName());
                        }
                    }
                    
                    return dto;
                })
                .collect(Collectors.toList());

        PageResponseResult<UserDto> result = new PageResponseResult<>();
        result.setTotal(userPage.getTotal());
        result.setData(userDtoList);
        result.setPages((int) userPage.getPages());
        result.setPageNum((int) userPage.getCurrent());
        result.setPageSize((int) userPage.getSize());
        result.setStatusCode(ResponseResult.STATUS_CODE_SUCCESS);
        result.setOpCode(ResponseResult.OP_CODE_SUCCESS);
        result.setOpDesc("查询成功");

        return result;
    }

    @Override
    @Transactional
    public ResponseResult<String> addUser(UserDto userDto) {
        if (userDto == null) {
            return ResponseResult.fail("用户信息不能为空");
        }

        if (StrUtil.isBlank(userDto.getUserCode())) {
            return ResponseResult.fail("用户账号不能为空");
        }

        if (StrUtil.isBlank(userDto.getUserName())) {
            return ResponseResult.fail("用户姓名不能为空");
        }

        // 检查用户账号是否已存在
        QueryWrapper<UserEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("USER_CODE", userDto.getUserCode());
        UserEntity existUser = userMapper.selectOne(queryWrapper);
        if (existUser != null) {
            return ResponseResult.fail("用户账号已存在");
        }

        // 检查部门是否存在（如果指定了部门ID）
        if (userDto.getDeptId() != null) {
            DepartmentEntity departmentEntity = departmentMapper.selectById(userDto.getDeptId());
            if (departmentEntity == null) {
                return ResponseResult.fail("指定的部门不存在");
            }
        }

        UserEntity userEntity = BeanUtil.copyProperties(userDto, UserEntity.class);

        int result = userMapper.insert(userEntity);
        if (result > 0) {
            log.info("用户添加成功，用户账号：{}", userDto.getUserCode());
            return ResponseResult.success("新增成功");
        } else {
            return ResponseResult.fail("用户添加失败");
        }
    }

    @Override
    @Transactional
    public ResponseResult<String> updateUser(UserDto userDto) {
        if (userDto == null) {
            return ResponseResult.fail("用户信息不能为空");
        }

        if (userDto.getTid() == null) {
            return ResponseResult.fail("用户ID不能为空");
        }

        // 检查用户是否存在
        UserEntity existUser = userMapper.selectById(userDto.getTid());
        if (existUser == null) {
            return ResponseResult.fail("用户不存在");
        }

        // 如果修改了用户账号，检查新账号是否已被其他用户使用
        if (StrUtil.isNotBlank(userDto.getUserCode()) && !userDto.getUserCode().equals(existUser.getUserCode())) {
            QueryWrapper<UserEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("USER_CODE", userDto.getUserCode());
            queryWrapper.ne("TID", userDto.getTid());
            UserEntity duplicateUser = userMapper.selectOne(queryWrapper);
            if (duplicateUser != null) {
                return ResponseResult.fail("用户账号已存在");
            }
        }

        // 检查部门是否存在（如果指定了部门ID）
        if (userDto.getDeptId() != null) {
            DepartmentEntity departmentEntity = departmentMapper.selectById(userDto.getDeptId());
            if (departmentEntity == null) {
                return ResponseResult.fail("指定的部门不存在");
            }
        }

        UserEntity userEntity = BeanUtil.copyProperties(userDto, UserEntity.class);

        int result = userMapper.updateById(userEntity);
        if (result > 0) {
            log.info("用户更新成功，用户ID：{}", userDto.getTid());
            return ResponseResult.success("更新成功");
        } else {
            return ResponseResult.fail("用户更新失败");
        }
    }

    @Override
    @Transactional
    public ResponseResult<String> deleteUser(Long tid) {
        if (tid == null) {
            return ResponseResult.fail("用户ID不能为空");
        }

        // 检查用户是否存在
        UserEntity existUser = userMapper.selectById(tid);
        if (existUser == null) {
            return ResponseResult.fail("用户不存在");
        }

        int result = userMapper.deleteById(tid);
        if (result > 0) {
            log.info("用户删除成功，用户ID：{}", tid);
            return ResponseResult.success("用户删除成功");
        } else {
            return ResponseResult.fail("用户删除失败");
        }
    }
}
