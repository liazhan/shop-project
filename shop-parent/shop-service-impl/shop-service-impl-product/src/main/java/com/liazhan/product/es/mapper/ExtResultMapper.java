package com.liazhan.product.es.mapper;
 
import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import org.apache.commons.beanutils.PropertyUtils;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.get.MultiGetItemResponse;
import org.elasticsearch.action.get.MultiGetResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.common.document.DocumentField;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.ElasticsearchException;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.ScriptedField;
import org.springframework.data.elasticsearch.core.AbstractResultMapper;
import org.springframework.data.elasticsearch.core.DefaultEntityMapper;
import org.springframework.data.elasticsearch.core.EntityMapper;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;
import org.springframework.data.elasticsearch.core.mapping.ElasticsearchPersistentEntity;
import org.springframework.data.elasticsearch.core.mapping.ElasticsearchPersistentProperty;
import org.springframework.data.elasticsearch.core.mapping.SimpleElasticsearchMappingContext;
import org.springframework.data.mapping.PersistentPropertyAccessor;
import org.springframework.data.mapping.context.MappingContext;
import org.springframework.data.mapping.model.ConvertingPropertyAccessor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
 
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.Charset;
import java.util.*;
 
/**
 * @version:V1.0
 * @Description: ResultMapper用来将es文档转换为Java对象，由于SpringDataElasticsearch默认的映射类
 *               DefaultResultMapper不支持高亮,这里我们自定义一个,步骤如下：
 *               1.在idea中按Ctrl+Shift+Alt+N快捷键搜索DefaultResultMapper类
 *               2.复制DefaultResultMapper类,修改类名为ExtResultMapper,构造方法名称也改一下
 *               3.新增方法populateHighLightedFields,作用是将高亮的内容赋值给需要转换的Java对象中
 *                 该方法需要添加commons-beanutils依赖
 *               4.在mapResults方法中调用我们新增的方法populateHighLightedFields
 * @author: Liazhan
 * @date 2020/5/28 14:25
 */
@Component
public class ExtResultMapper extends AbstractResultMapper {
 
    private final MappingContext<? extends ElasticsearchPersistentEntity<?>, ElasticsearchPersistentProperty> mappingContext;
    private final ConversionService conversionService;
 
    public ExtResultMapper() {
        this((MappingContext)(new SimpleElasticsearchMappingContext()));
    }
 
    public ExtResultMapper(MappingContext<? extends ElasticsearchPersistentEntity<?>, ElasticsearchPersistentProperty> mappingContext) {
        this(mappingContext, initEntityMapper(mappingContext));
    }
 
    public ExtResultMapper(EntityMapper entityMapper) {
        this(new SimpleElasticsearchMappingContext(), entityMapper);
    }
 
    public ExtResultMapper(MappingContext<? extends ElasticsearchPersistentEntity<?>, ElasticsearchPersistentProperty> mappingContext, @Nullable EntityMapper entityMapper) {
        super(entityMapper != null ? entityMapper : initEntityMapper(mappingContext));
        this.conversionService = new DefaultConversionService();
        this.mappingContext = mappingContext;
    }
 
    private static EntityMapper initEntityMapper(MappingContext<? extends ElasticsearchPersistentEntity<?>, ElasticsearchPersistentProperty> mappingContext) {
        Assert.notNull(mappingContext, "MappingContext must not be null!");
        return new DefaultEntityMapper(mappingContext);
    }
 
    public <T> AggregatedPage<T> mapResults(SearchResponse response, Class<T> clazz, Pageable pageable) {
        long totalHits = response.getHits().getTotalHits();
        float maxScore = response.getHits().getMaxScore();
        List<T> results = new ArrayList();
        Iterator var8 = response.getHits().iterator();
 
        while(var8.hasNext()) {
            SearchHit hit = (SearchHit)var8.next();
            if (hit != null) {
                T result = null;
                String hitSourceAsString = hit.getSourceAsString();
                if (!StringUtils.isEmpty(hitSourceAsString)) {
                    result = this.mapEntity(hitSourceAsString, clazz);
                } else {
                    result = this.mapEntity(hit.getFields().values(), clazz);
                }
 
                this.setPersistentEntityId(result, hit.getId(), clazz);
                this.setPersistentEntityVersion(result, hit.getVersion(), clazz);
                this.setPersistentEntityScore(result, hit.getScore(), clazz);
                this.populateScriptFields(result, hit);
 
                //在这里调用我们新增的方法,支持高亮查询
                this.populateHighLightedFields(result,hit.getHighlightFields());
 
                results.add(result);
            }
        }
 
        return new AggregatedPageImpl(results, pageable, totalHits, response.getAggregations(), response.getScrollId(), maxScore);
    }
 
    /**
     * 这是我们新增的方法，将高亮的内容赋值给需要转换的Java对象中
     * @param result
     * @param highlightFields
     * @param <T>
     */
    private <T>  void populateHighLightedFields(T result, Map<String, HighlightField> highlightFields) {
        for (HighlightField field : highlightFields.values()) {
            try {
                PropertyUtils.setProperty(result, field.getName(), concat(field.fragments()));
            } catch (InvocationTargetException | IllegalAccessException | NoSuchMethodException e) {
                throw new ElasticsearchException("failed to set highlighted value for field: " + field.getName()
                        + " with value: " + Arrays.toString(field.getFragments()), e);
            }
        }
    }
 
    /**
     * 这也是新增的方法，供populateHighLightedFields方法调用
     * @param texts
     * @return
     */
    private String concat(Text[] texts) {
        StringBuffer sb = new StringBuffer();
        for (Text text : texts) {
            sb.append(text.toString());
        }
        return sb.toString();
    }
 
    private <T> void populateScriptFields(T result, SearchHit hit) {
        if (hit.getFields() != null && !hit.getFields().isEmpty() && result != null) {
            Field[] var3 = result.getClass().getDeclaredFields();
            int var4 = var3.length;
 
            for(int var5 = 0; var5 < var4; ++var5) {
                Field field = var3[var5];
                ScriptedField scriptedField = (ScriptedField)field.getAnnotation(ScriptedField.class);
                if (scriptedField != null) {
                    String name = scriptedField.name().isEmpty() ? field.getName() : scriptedField.name();
                    DocumentField searchHitField = (DocumentField)hit.getFields().get(name);
                    if (searchHitField != null) {
                        field.setAccessible(true);
 
                        try {
                            field.set(result, searchHitField.getValue());
                        } catch (IllegalArgumentException var11) {
                            throw new ElasticsearchException("failed to set scripted field: " + name + " with value: " + searchHitField.getValue(), var11);
                        } catch (IllegalAccessException var12) {
                            throw new ElasticsearchException("failed to access scripted field: " + name, var12);
                        }
                    }
                }
            }
        }
 
    }
 
    private <T> T mapEntity(Collection<DocumentField> values, Class<T> clazz) {
        return this.mapEntity(this.buildJSONFromFields(values), clazz);
    }
 
    private String buildJSONFromFields(Collection<DocumentField> values) {
        JsonFactory nodeFactory = new JsonFactory();
 
        try {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            JsonGenerator generator = nodeFactory.createGenerator(stream, JsonEncoding.UTF8);
            generator.writeStartObject();
            Iterator var5 = values.iterator();
 
            while(true) {
                while(var5.hasNext()) {
                    DocumentField value = (DocumentField)var5.next();
                    if (value.getValues().size() > 1) {
                        generator.writeArrayFieldStart(value.getName());
                        Iterator var7 = value.getValues().iterator();
 
                        while(var7.hasNext()) {
                            Object val = var7.next();
                            generator.writeObject(val);
                        }
 
                        generator.writeEndArray();
                    } else {
                        generator.writeObjectField(value.getName(), value.getValue());
                    }
                }
 
                generator.writeEndObject();
                generator.flush();
                return new String(stream.toByteArray(), Charset.forName("UTF-8"));
            }
        } catch (IOException var9) {
            return null;
        }
    }
 
    public <T> T mapResult(GetResponse response, Class<T> clazz) {
        T result = this.mapEntity(response.getSourceAsString(), clazz);
        if (result != null) {
            this.setPersistentEntityId(result, response.getId(), clazz);
            this.setPersistentEntityVersion(result, response.getVersion(), clazz);
        }
 
        return result;
    }
 
    public <T> List<T> mapResults(MultiGetResponse responses, Class<T> clazz) {
        List<T> list = new ArrayList();
        MultiGetItemResponse[] var4 = responses.getResponses();
        int var5 = var4.length;
 
        for(int var6 = 0; var6 < var5; ++var6) {
            MultiGetItemResponse response = var4[var6];
            if (!response.isFailed() && response.getResponse().isExists()) {
                T result = this.mapEntity(response.getResponse().getSourceAsString(), clazz);
                this.setPersistentEntityId(result, response.getResponse().getId(), clazz);
                this.setPersistentEntityVersion(result, response.getResponse().getVersion(), clazz);
                list.add(result);
            }
        }
 
        return list;
    }
 
    private <T> void setPersistentEntityId(T result, String id, Class<T> clazz) {
        if (clazz.isAnnotationPresent(Document.class)) {
            ElasticsearchPersistentEntity<?> persistentEntity = (ElasticsearchPersistentEntity)this.mappingContext.getRequiredPersistentEntity(clazz);
            ElasticsearchPersistentProperty idProperty = (ElasticsearchPersistentProperty)persistentEntity.getIdProperty();
            PersistentPropertyAccessor<T> accessor = new ConvertingPropertyAccessor(persistentEntity.getPropertyAccessor(result), this.conversionService);
            if (idProperty != null && idProperty.getType().isAssignableFrom(String.class)) {
                accessor.setProperty(idProperty, id);
            }
        }
 
    }
 
    private <T> void setPersistentEntityVersion(T result, long version, Class<T> clazz) {
        if (clazz.isAnnotationPresent(Document.class)) {
            ElasticsearchPersistentEntity<?> persistentEntity = (ElasticsearchPersistentEntity)this.mappingContext.getPersistentEntity(clazz);
            ElasticsearchPersistentProperty versionProperty = persistentEntity.getVersionProperty();
            if (versionProperty != null && versionProperty.getType().isAssignableFrom(Long.class)) {
                Assert.isTrue(version != -1L, "Version in response is -1");
                persistentEntity.getPropertyAccessor(result).setProperty(versionProperty, version);
            }
        }
 
    }
 
    private <T> void setPersistentEntityScore(T result, float score, Class<T> clazz) {
        if (clazz.isAnnotationPresent(Document.class)) {
            ElasticsearchPersistentEntity<?> entity = (ElasticsearchPersistentEntity)this.mappingContext.getRequiredPersistentEntity(clazz);
            if (!entity.hasScoreProperty()) {
                return;
            }
 
            entity.getPropertyAccessor(result).setProperty(entity.getScoreProperty(), score);
        }
 
    }
}