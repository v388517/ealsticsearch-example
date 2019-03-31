package com.example.config;

import com.example.pojo.User;
import com.example.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 认证类
 */
public class UserRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;
    /**
     * 授权
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        System.out.println("进行授权......");
        Subject subject = SecurityUtils.getSubject();
        User user= (User) subject.getPrincipal();
        //给资源授权
        SimpleAuthorizationInfo simpleAuthorizationInfo=new SimpleAuthorizationInfo();
       // simpleAuthorizationInfo.addStringPermission("admin");
        /**
         * 设置角色为user
         * 资源权限为user:two
         */
        simpleAuthorizationInfo.addRole("user");
        simpleAuthorizationInfo.addStringPermission("user:two");
        return simpleAuthorizationInfo;
    }

    /**
     * 执行认证逻辑
     * 登录
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.out.println("认证");
        //shiro判断逻辑
        UsernamePasswordToken user= (UsernamePasswordToken) token;
        User realUser=new User();
        realUser.setUsername(user.getUsername());
        realUser.setPassword(String.copyValueOf(user.getPassword()));
        User newUser=userService.findByUsername(realUser);

        if (newUser==null){
            //shiro会抛出UnknownAccountException异常
            return  null;
        }


        return new SimpleAuthenticationInfo(newUser,newUser.getPassword(),getName());
    }
}
