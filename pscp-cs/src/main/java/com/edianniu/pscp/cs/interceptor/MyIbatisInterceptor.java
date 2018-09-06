/**
 *
 */
package com.edianniu.pscp.cs.interceptor;

import com.edianniu.pscp.cs.domain.BaseDo;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author cyl
 */
@Intercepts({@Signature(type = Executor.class, method = "update", args = {
        MappedStatement.class, Object.class})})
/*
 * @Intercepts({
 * 
 * @Signature(type = Executor.class, method = "update", args = {
 * BaseEntity.class, Object.class }),
 * 
 * @Signature(type = Executor.class, method = "query", args = {
 * BaseEntity.class, Object.class, RowBounds.class, ResultHandler.class }) })
 */
public class MyIbatisInterceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object[] args = invocation.getArgs();
        if (args != null && args.length == 2) {
            if (args[0] instanceof MappedStatement) {
                MappedStatement ms = (MappedStatement) args[0];
                SqlCommandType sqlCommandType = ms.getSqlCommandType();
                String loginName = "系统";
                // TODO 登录用户信息未处理
//                if (ShiroUtils.isLogin()) {
//                    loginName = ShiroUtils.getUserEntity().getLoginName();
//                }
                if (sqlCommandType == SqlCommandType.INSERT) {
                    if (args[1] instanceof BaseDo) {
                        BaseDo BaseEntity = (BaseDo) args[1];
                        BaseEntity.setCreateTime(new Date());
                        BaseEntity.setCreateUser(loginName);
                    } else if (args[1] instanceof Map) {
                        Map map = (Map) args[1];
                        mappSpecialKey(map);
                        map.put("createUser", "'" + loginName + "'");
                        map.put("createTime", new Date());
                        map.put("deleted", new Integer(0));
                    } else {

                    }
                } else if (sqlCommandType == SqlCommandType.UPDATE) {
                    if (args[1] instanceof BaseDo) {
                        BaseDo BaseEntity = (BaseDo) args[1];
                        BaseEntity.setModifiedTime(new Date());
                        BaseEntity.setModifiedUser(loginName);
                    } else if (args[1] instanceof Map) {
                        Map map = (Map) args[1];
                        mappSpecialKey(map);
                        map.put("modifiedUser", "'" + loginName + "'");
                        map.put("modifiedTime", new Date());
                    } else {
                        // TODO
                    }
                } else if (sqlCommandType == SqlCommandType.DELETE) {
                    if (args[1] instanceof BaseDo) {
                        BaseDo BaseEntity = (BaseDo) args[1];
                        BaseEntity.setModifiedTime(new Date());
                        BaseEntity.setModifiedUser(loginName);
                    } else if (args[1] instanceof Map) {
                        Map map = (Map) args[1];
                        mappSpecialKey(map);
                        map.put("modifiedUser", "'" + loginName + "'");
                        map.put("modifiedTime", new Date());
                    } else {
                        Map<String, Object> map = new HashMap<>();
                        map.put("value", args[1]);
                        map.put("modifiedUser", "'" + loginName + "'");
                        map.put("modifiedTime", new Date());
                        args[1] = map;
                    }
                }
            }
        }
        return invocation.proceed();
    }

    private void mappSpecialKey(Map map) {
        if (map.containsKey("array") && map.get("array") != null) {
            map.put("array", map.get("array"));
        }
        if (map.containsKey("list") && map.get("list") != null) {
            map.put("list", map.get("list"));
        }
        if (map.containsKey("collection") && map.get("collection") != null) {
            map.put("collection", map.get("collection"));
        }
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties arg0) {
    }

}
