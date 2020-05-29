package com.liazhan.product.es.entity;
 
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
 
import java.util.Date;
 
/**
 * @version V1.0
 * @description: es商品索引
 * @author: Liazhan
 * @date: 2020/5/25 22:40
 */
@Document(indexName = "products",type = "_doc")
@Data
public class ProductEntity {
    //主键id
    @Id
    private Integer productId;
    //类型id
    private Integer categoryId;
    //商品名称
    private String productName;
    //小标题
    private String subtitle;
    //商品规格
    private String productSpecs;
    //商品价格
    private Double productPrice;
    //商品库存
    private Integer productStock;
    //商品详情
    private String detail;
    //主图
    private String masterPic;
    //乐观锁
    private Integer revision;
    //上下架状态 0下架 1上架
    private Integer publishStatus;
    //审核状态 0未审核 1已审核
    private Integer auditStatus;
    //创建时间
    private Date createdTime;
    //更新时间
    private Date updatedTime;
}