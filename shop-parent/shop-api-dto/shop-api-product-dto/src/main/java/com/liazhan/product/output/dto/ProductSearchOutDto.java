package com.liazhan.product.output.dto;
 
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
 
import java.util.Date;
 
/**
 * @version:V1.0
 * @Description:
 * @author: Liazhan
 * @date 2020/5/24 17:11
 */
@Data
@ApiModel(value = "商品搜索输出实体类")
public class ProductSearchOutDto {
 
    @ApiModelProperty(value = "商品主键ID")
    private Integer productId;
    @ApiModelProperty(value = "类型ID")
    private Integer categoryId;
    @ApiModelProperty(value = "商品名称")
    private String productName;
    @ApiModelProperty(value = "小标题")
    private String subtitle;
    @ApiModelProperty(value = "商品规格")
    private String productSpecs;
    @ApiModelProperty(value = "商品价格")
    private Double productPrice;
    @ApiModelProperty(value = "商品库存")
    private Integer productStock;
    @ApiModelProperty(value = "商品描述")
    private String detail;
    @ApiModelProperty(value = "主图")
    private String masterPic;
    @ApiModelProperty(value = "乐观锁")
    private Integer revision;
    @ApiModelProperty(value = "上下架状态 0下架 1上架")
    private Integer publishStatus;
    @ApiModelProperty(value = "审核状态 0未审核 1已审核")
    private Integer auditStatus;
    @ApiModelProperty(value = "创建时间")
    private Date createdTime;
    @ApiModelProperty(value = "更新时间")
    private Date updatedTime;
}