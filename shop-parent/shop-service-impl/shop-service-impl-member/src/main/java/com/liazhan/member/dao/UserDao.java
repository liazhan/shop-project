package com.liazhan.member.dao;

import com.liazhan.member.dao.entity.UserDO;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @version:V1.0
 * @Description: 用户dao层
 * @author: Liazhan
 * @date 2020/4/21 10:58
 */
public interface UserDao extends JpaRepository<UserDO,Integer> {
    /**
     * 根据手机号查询用户是否存在
     */
    boolean existsByPhone(String phone);
}
