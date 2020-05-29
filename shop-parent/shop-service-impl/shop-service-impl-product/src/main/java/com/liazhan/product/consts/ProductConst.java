package com.liazhan.product.consts;
 
/**
 * @version:V1.0
 * @Description: 常量类
 * @author: Liazhan
 * @date 2020/5/28 15:36
 */
public interface ProductConst {
 
    //商品表商品主键列
    String PRODUCT_ID_COLUMN = "productId";
    //商品表商品名称列
    String PRODUCT_NAME_COLUMN = "productName";
    //商品表商品小标题列
    String SUBTITLE_COLUMN = "subtitle";
    //商品表商品描述列
    String DETAIL_COLUMN = "detail";
    //商品表商品上架状态列
    String PUBLISH_STATUS_COLUMN = "publishStatus";
    //商品表商品审核状态列
    String AUDIT_STATUS_COLUMN = "auditStatus";
    //商品表商品创建时间列
    String CREATED_TIME_COLUMN = "createdTime";
    //商品已上架状态
    Integer PUBLISHED = 1;
    //商品已审核状态
    Integer AUDITED = 1;
    //筛选起始时间
    String SCREEN_START_TIME = "2020-05-11 16:00:00";
    //筛选结束时间
    String SCREEN_END_TIME = "2020-05-18 16:00:00";
    //筛选时间格式
    String SCREEN_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    //es关键词高亮前缀
    String HIGHLIGHT_PRETAGS = "<span style=\"color:red\">";
    //es关键词高亮后缀
    String HIGHLIGHT_POSTTAGS = "</span>";
    //高亮字段显示的最长长度,不配置可能导致高亮不全,文章内容缺失等问题  最大高亮分片数800000
    Integer HIGHLIGHT_FRAGMENTSIZE = 800000;
    //从第一个分片获取高亮字段
    Integer HIGHLIGHT_NUMOFFRAGMENTS = 0;
}