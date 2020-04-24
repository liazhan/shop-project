package com.liazhan.member.dao.entity;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

/**
 * @version:V1.0
 * @Description: 用户表
 * @author: Liazhan
 * @date 2020/4/21 10:58
 */
@Entity(name="u_user")
@Data
//动态insert与update，过滤null值
@DynamicInsert
@DynamicUpdate
//监听，配合注释@CreatedDate和@LastModifiedDate,当insert时会自动获取创建时间，修改时自动更新修改时间，需要在启动类添加注释@EnableJpaAuditing
@EntityListeners(AuditingEntityListener.class)
public class UserDO {
    /**
     * 用户id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;
    /**
     * 用户名称
     */
    private String userName;
    /**
     * 用户手机号
     */
    private String phone;
    /**
     * 用户邮箱号
     */
    private String email;
    /**
     * 用户密码
     */
    private String password;
    /**
     * 用户性别
     */
    private Integer sex;
    /**
     * 用户年龄
     */
    private Integer age;
    /**
     * 用户头像
     */
    private String picUrl;
    /**
     * 用户状态 1=正常,2=冻结
     */
    private Integer status;
    /**
     * 创建时间
     */
    @CreatedDate
    private Date createTime;
    /**
     * 修改时间
     */
    @LastModifiedDate
    private Date updateTime;
}
