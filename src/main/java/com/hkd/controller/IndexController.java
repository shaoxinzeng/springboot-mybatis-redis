package com.hkd.controller;

import com.hkd.entity.Result;
import com.hkd.entity.vo.UserRequestVo;
import com.hkd.service.IndexService;
import com.hkd.util.ResultUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * User: xuedaiyao
 * Date: 2017/8/18
 * Time: 11:41
 * Description: 首页Controller
 */
@RestController
@RequestMapping("index")
@Api(value = "首页接口",description = "首页接口")
public class IndexController {

    @Autowired
    private IndexService indexService;

    @GetMapping("/show")
    @ApiOperation(value = "首页展示@xuedaiyao",notes = "首页展示")
    public Result show( UserRequestVo userRequestVo){
       return ResultUtils.success(indexService.show(userRequestVo));
    }

    @GetMapping("detail")
    @ApiOperation(value = "服务费明细@xuedaiyao",notes = "管理费（借款本金*2.5%）+咨询费（借款本金*10%借款本金*10%）")
    public Result getServiceFeeDetail(@ApiParam(value = "借款期数",required = true)@RequestParam Integer periods,
                                      @ApiParam(value = "借款金额",required = true) @RequestParam Integer moneyAmount){
        return ResultUtils.success(indexService.getServiceFeeDetail(periods,moneyAmount));
    }


}

