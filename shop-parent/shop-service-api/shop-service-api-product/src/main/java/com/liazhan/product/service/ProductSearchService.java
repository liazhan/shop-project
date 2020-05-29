package com.liazhan.product.service;
 
import com.alibaba.fastjson.JSONObject;
import com.liazhan.base.BaseResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
 
/**
 * @version V1.0
 * @description:
 * @author: Liazhan
 * @date: 2020/5/25 22:22
 */
@Api(tags = "商品服务搜索相关接口")
public interface ProductSearchService {
 
    @GetMapping("/search")
    @ApiOperation(value = "搜索接口")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",name = "keyword",dataType = "String",required = true,value = "搜索关键字"),
            @ApiImplicitParam(paramType = "query",name = "page",dataType = "Integer",required = false,value = "第几页,从0开始,默认为0"),
            @ApiImplicitParam(paramType = "query",name = "size",dataType = "Integer",required = false,value = "一页几条,默认为10"),
            @ApiImplicitParam(paramType = "query",name = "sort",dataType = "String",required = false,value = "排序相关的信息,比如sort=productId&sort=createTime,desc  表示按照主键正序排列基础上按创建时间倒序排列")
    })
    public BaseResponse<JSONObject> search(String keyword, @PageableDefault(page = 0,value = 10) Pageable pageable);
}