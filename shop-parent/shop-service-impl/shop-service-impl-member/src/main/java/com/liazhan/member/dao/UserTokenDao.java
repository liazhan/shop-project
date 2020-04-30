package com.liazhan.member.dao;

import com.liazhan.member.dao.entity.UserTokenDO;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @version:V1.0
 * @Description: 用户令牌dao层
 * @author: Liazhan
 * @date 2020/4/29 9:43
 */
public interface UserTokenDao extends JpaRepository<UserTokenDO,Integer> {

    /*
     * 根据用户id、登陆类型、token状态获取用户token记录
     */
    UserTokenDO findByUserIdAndLoginTypeAndStatus(Integer userId, Integer loginType, Integer status);
}
