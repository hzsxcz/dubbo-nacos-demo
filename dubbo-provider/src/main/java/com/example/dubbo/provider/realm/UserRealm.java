package com.example.dubbo.provider.realm;

import com.example.dubbo.api.User;
import com.example.dubbo.provider.mapper.UserMapper;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

public class UserRealm extends AuthorizingRealm {

    @Autowired
    private UserMapper userMapper;

    /** 授权（暂不细分角色权限） */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        return new SimpleAuthorizationInfo();
    }

    /** 认证：根据 username 查库比对密码 */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
            throws AuthenticationException {
        String username = (String) token.getPrincipal();
        User user = userMapper.findByUsername(username);
        if (user == null) {
            throw new UnknownAccountException("用户不存在: " + username);
        }
        return new SimpleAuthenticationInfo(user, user.getPassword(), getName());
    }
}