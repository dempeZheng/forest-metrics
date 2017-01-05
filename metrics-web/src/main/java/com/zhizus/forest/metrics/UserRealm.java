package com.zhizus.forest.metrics;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Dempe on 2017/1/5.
 */
public class UserRealm extends AuthorizingRealm {

    //这里因为没有调用后台，直接默认只有一个用户("luoguohui"，"123456")
    private static final String USER_NAME = "luoguohui";
    private static final String PASSWORD = "123456";

    /*
     * 授权
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        Set<String> roleNames = new HashSet<String>();
        Set<String> permissions = new HashSet<String>();
        roleNames.add("administrator");//添加角色
        permissions.add("newPage.jhtml");  //添加权限
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo(roleNames);
        info.setStringPermissions(permissions);
        return info;
    }

    /*
     * 登录验证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(
            AuthenticationToken authcToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
//        if (token.getUsername().equals(USER_NAME)) {
//            return new SimpleAuthenticationInfo(USER_NAME, PASSWORD, getName());
//        } else {
//            throw new AuthenticationException();
//        }
        return new SimpleAuthenticationInfo(token.getUsername(), token.getPassword(), getName());
    }

}
