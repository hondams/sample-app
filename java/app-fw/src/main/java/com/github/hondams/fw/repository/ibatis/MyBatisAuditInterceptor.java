package com.github.hondams.fw.repository.ibatis;

import java.util.Date;
import java.util.concurrent.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;

@Intercepts({@Signature(type = Executor.class, method = "update",
        args = {MappedStatement.class, Object.class})})
public class MyBatisAuditInterceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object[] args = invocation.getArgs();
        if (args.length >= 2) {
            Object parameter = args[1];
            if (parameter != null) {
                Long userId = null;// UserContext.getUserId();
                MetaObject meta = SystemMetaObject.forObject(parameter);
                // プロパティ名はエンティティのフィールド名に合わせる（例: updatedBy, updatedAt）
                if (userId != null && meta.hasSetter("updatedBy")) {
                    meta.setValue("updatedBy", userId);
                }
                if (meta.hasSetter("updatedAt")) {
                    meta.setValue("updatedAt", new Date());
                }
            }
        }
        return invocation.proceed();
    }

    @Override
    public void setProperties(java.util.Properties properties) {
        // no-op
    }
}
