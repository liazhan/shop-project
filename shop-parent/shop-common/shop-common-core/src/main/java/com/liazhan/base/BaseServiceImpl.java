package com.liazhan.base;

import com.liazhan.base.consts.BaseConst;

/**
 * @version:V1.0
 * @Description: 继承该类可以很方便的封装响应体
 * @author: Liazhan
 * @date 2020/4/14 15:40
 */

public class BaseServiceImpl<T> {
    //通用封装
    public BaseResponse<T> getRusult(Integer code,String msg,T data){
        return new BaseResponse<T>(code,msg,data);
    }
    //响应成功封装,无参
    public BaseResponse<T> getResultSuccess(){
        return getRusult(BaseConst.HTTP_RES_CODE_200,BaseConst.HTTP_RES_CODE_200_MSG,null);
    }
    //响应成功封装,需data参数
    public BaseResponse<T> getResultSuccess(T data){
        return getRusult(BaseConst.HTTP_RES_CODE_200,BaseConst.HTTP_RES_CODE_200_MSG,data);
    }
    //响应成功封装,需msg参数
    public BaseResponse<T> getResultSuccess(String msg){
        return getRusult(BaseConst.HTTP_RES_CODE_200,msg,null);
    }
    //响应失败封装,需msg参数
    public BaseResponse<T> getResultError(String msg){
        return getRusult(BaseConst.HTTP_RES_CODE_500,msg,null);
    }
}
