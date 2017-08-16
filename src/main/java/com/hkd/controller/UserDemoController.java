package com.hkd.controller;

import com.github.pagehelper.PageInfo;
import com.hkd.entity.Result;
import com.hkd.entity.UserDemo;
import com.hkd.service.UserDemoService;
import com.hkd.util.ResultUtils;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

/**
 * Created by 1 on 2017-08-15.
 */
@RestController
@RequestMapping()
public class UserDemoController {

    @Autowired
    private UserDemoService userDemoService;

    @PostMapping("add")
    @ApiOperation(value = "添加用户", notes = "添加用户")
    public Result<UserDemo> addUserDemo(@RequestBody UserDemo userDemo) {
        int count = userDemoService.addUser(userDemo);
        if (count > 0) {
            return ResultUtils.success();
        } else {
            return ResultUtils.error(100, "添加失败");
        }
    }

    @DeleteMapping("delete")
    @ApiOperation(value = "根据用户id删除用户", notes = "根据用户id删除用户")
    public Result<UserDemo> delectUserDemo(@ApiParam(value = "删除用户", required = true) @RequestParam int id) {
        int count = userDemoService.deleteUserById(id);
        if (count > 0) {
            return ResultUtils.success();
        } else {
            return ResultUtils.error(100, "删除失败");
        }
    }

    @PutMapping("update")
    @ApiOperation(value = "修改用户", notes = "修改用户")
    public Result<UserDemo> updateUserDemo(@RequestBody UserDemo userDemo) {
        int count = userDemoService.updateUser(userDemo);
        if (count > 0) {
            return ResultUtils.success();
        } else {
            return ResultUtils.error(100, "修改失败");
        }
    }

    @GetMapping(value = "/list")
    @ApiOperation(value = "分页查询全部用户", notes = "分页查询用户")
    public Result<PageInfo<UserDemo>> findUserDemo() {
        PageInfo<UserDemo> pageInfo = userDemoService.findUserDemo();
        return ResultUtils.success(pageInfo);
    }

}
