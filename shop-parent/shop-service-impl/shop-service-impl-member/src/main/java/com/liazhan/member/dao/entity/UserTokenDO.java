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
 * @Description: 用户令牌表实体类
 * @author: Liazhan
 * @date 2020/4/29 9:25
 */
@Data
@Entity(name = "u_user_token")
@DynamicInsert
@DynamicUpdate
@EntityListeners(AuditingEntityListener.class)
public class UserTokenDO {
    /*
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userTokenId;
    /*
     * 令牌
     */
    private String token;
    /*
     * 登陆方式 1=pc,2=android,3=ios
     */
    private Integer loginType;
    /*
     * 设备信息
     */
    private String deviceInfor;
    /*
     * token状态 0=失效 1=有效
     */
    private Integer status;
    /*
     * 用户id
     */
    private Integer userId;
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
