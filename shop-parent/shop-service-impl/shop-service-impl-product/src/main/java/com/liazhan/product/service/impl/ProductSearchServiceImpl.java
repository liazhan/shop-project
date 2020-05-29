package com.liazhan.product.service.impl;
 
import com.alibaba.fastjson.JSONObject;
import com.liazhan.base.BaseResponse;
import com.liazhan.base.BaseServiceImpl;
import com.liazhan.product.consts.ProductConst;
import com.liazhan.product.es.entity.ProductEntity;
import com.liazhan.product.es.mapper.ExtResultMapper;
import com.liazhan.product.output.dto.ProductSearchOutDto;
import com.liazhan.product.service.ProductSearchService;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.web.bind.annotation.RestController;
 
import java.util.List;
 
/**
 * @version V1.0
 * @description: 商品服务搜索相关接口实现类
 * @author: Liazhan
 * @date: 2020/5/25 22:35
 */
@RestController
public class ProductSearchServiceImpl extends BaseServiceImpl<JSONObject> implements ProductSearchService {
    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;
    @Autowired
    private ExtResultMapper extResultMapper;
 
    @Override
    public BaseResponse<JSONObject> search(String keyword, Pageable pageable) {
        /*
        1.拼接查询条件
        根据关键字模糊搜索 productName、subtitle、detail列;
        审核状态为审核通过,上架状态为已上架;
        范围搜索创建时间,由于时区问题elasticsearch里的时间少了8小时，因此这里搜索日期条件要减8小时
        */
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        boolQuery.must(QueryBuilders.multiMatchQuery(keyword,
                        ProductConst.PRODUCT_NAME_COLUMN,
                        ProductConst.SUBTITLE_COLUMN,
                        ProductConst.DETAIL_COLUMN))
                .must(QueryBuilders.termQuery(ProductConst.PUBLISH_STATUS_COLUMN,
                        ProductConst.PUBLISHED))
                .must(QueryBuilders.termQuery(ProductConst.AUDIT_STATUS_COLUMN,
                        ProductConst.AUDITED))
                .must(QueryBuilders.rangeQuery(ProductConst.CREATED_TIME_COLUMN)
                        .format(ProductConst.SCREEN_TIME_FORMAT)
                        .from(ProductConst.SCREEN_START_TIME)
                        .to(ProductConst.SCREEN_END_TIME));
 
        /*2.配置关键字高亮 */
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        //需要高亮的字段
        highlightBuilder.field(ProductConst.PRODUCT_NAME_COLUMN);
        highlightBuilder.field(ProductConst.SUBTITLE_COLUMN);
        highlightBuilder.field(ProductConst.DETAIL_COLUMN);
        //多个字段该项设置为false
        highlightBuilder.requireFieldMatch(false);
        //高亮前缀
        highlightBuilder.preTags(ProductConst.HIGHLIGHT_PRETAGS);
        //高亮后缀
        highlightBuilder.postTags(ProductConst.HIGHLIGHT_POSTTAGS);
        //高亮字段显示的最长长度,不配置可能导致高亮不全,文章内容缺失等问题
        highlightBuilder.fragmentSize(ProductConst.HIGHLIGHT_FRAGMENTSIZE);
        //从第一个分片获取高亮字段
        highlightBuilder.numOfFragments(ProductConst.HIGHLIGHT_NUMOFFRAGMENTS);
 
        /*3.若前端没传排序参数,则根据主键和创建时间降序排列*/
        Sort sort = pageable.getSort();
        if(sort.isUnsorted()) {
            Sort.Order order1 = new Sort.Order(Sort.Direction.DESC, ProductConst.PRODUCT_ID_COLUMN);
            Sort.Order order2 = new Sort.Order(Sort.Direction.DESC, ProductConst.CREATED_TIME_COLUMN);
            sort = Sort.by(order1, order2);
        }
 
        /*4.重新组装Pageable*/
        int pageNumber = pageable.getPageNumber();  //第几页
        int pageSize = pageable.getPageSize();  //一页几条
        pageable = PageRequest.of(pageNumber,pageSize,sort);
 
        /*5.调用es接口查询*/
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(boolQuery)
                .withHighlightBuilder(highlightBuilder)
                /*
                高亮也可以通过这种方式来设置,好处是不同的列可以有不同的配置
                .withHighlightFields(
                        new HighlightBuilder.Field("productName").preTags("<span style=\"color:red\">").postTags("</span>").fragmentSize(800000).numOfFragments(0),
                        new HighlightBuilder.Field("subtitle").preTags("<span style=\"color:red\">").postTags("</span>").fragmentSize(800000).numOfFragments(0),
                        new HighlightBuilder.Field("detail").preTags("<span style=\"color:red\">").postTags("</span>").fragmentSize(800000).numOfFragments(0)
                )*/
                .withPageable(pageable)
                .build();
        AggregatedPage<ProductEntity> page = elasticsearchTemplate.queryForPage(searchQuery, ProductEntity.class, extResultMapper);
 
        /*6.获取集合数据*/
        List<ProductEntity> content = page.getContent();
 
        /*7.将entity转为dto*/
        DefaultMapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
        List<ProductSearchOutDto> productSearchOutDtoList = mapperFactory.getMapperFacade().mapAsList(content, ProductSearchOutDto.class);
 
        /*8.返回*/
        JSONObject result = new JSONObject();
        result.put("productList",productSearchOutDtoList);  //商品列表
        result.put("totalElements",page.getTotalElements());  //商品总数
        result.put("totalPages",page.getTotalPages()); //总页数
        return getResultSuccess(result);
    }
}