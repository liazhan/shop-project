package com.liazhan.core.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.interceptor.DefaultTransactionAttribute;

/**
 * @version:V1.0
 * @Description: redis与数据库事务工具类
 * @author: Liazhan
 * @date 2020/4/29 11:09
 */
@Component
//多例
@Scope(ConfigurableListableBeanFactory.SCOPE_PROTOTYPE)
public class RedisAndDBTransactionUtil {
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private PlatformTransactionManager transactionManager;

    /**
     * 开启事务  采用默认传播行为
     * @return
     */
    public TransactionStatus begin(){
        //手动开启数据库事务
        TransactionStatus transaction = transactionManager.getTransaction(new DefaultTransactionAttribute());
        redisUtil.begin();
        return transaction;
    }

    /**
     * 提交事务
     * @param transactionStatus
     * @throws Exception
     */
    public void commit(TransactionStatus transactionStatus) throws Exception {
        if(transactionStatus==null){
            throw new Exception("transactionStatus is null");
        }
        //redis与数据库事务会同时提交
        transactionManager.commit(transactionStatus);
    }

    /**
     * 回滚事务
     * @param transactionStatus
     * @throws Exception
     */
    public void rollback(TransactionStatus transactionStatus) throws Exception {
        if(transactionStatus==null){
            throw new Exception("transactionStatus is null");
        }
        //redis与数据库事务会同时回滚
        transactionManager.rollback(transactionStatus);
    }
}
