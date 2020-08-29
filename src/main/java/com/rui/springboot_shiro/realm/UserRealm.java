package com.rui.springboot_shiro.realm;

import com.rui.springboot_shiro.domain.User;
import com.rui.springboot_shiro.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

/***
 *@author : Ray
 *@date :  2020/8/22 18:28
 *description:
 *      自定义Realm
 *  * （1）AuthenticatingRealm：shiro中的用于进行认证的领域，实现doGetAuthentcationInfo方法实现用户登录时的认证逻辑；
 *  * （2）AuthorizingRealm：shiro中用于授权的领域，实现doGetAuthrozitionInfo方法实现用户的授权逻辑，
 *  * AuthorizingRealm继承了AuthenticatingRealm，所以在实际使用中主要用到的就是这个AuthenticatingRealm类；
 *  * （3）AuthenticatingRealm、AuthorizingRealm这两个类都是shiro中提供了一些线程的realm接口
 *  * （4）在与spring整合项目中，shiro的SecurityManager会自动调用这两个方法，从而实现认证和授权，
 *  * 可以结合shiro的CacheManager将认证和授权信息保存在缓存中，这样可以提高系统的处理效率。
 ***/
public class UserRealm extends AuthorizingRealm {

    @Autowired
    private UserService userSerivce;

    /**
     * 执行认证逻辑
     *  如果认证不通过,就不会再去执行授权了
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        // 1.判断用户名
        User user = userSerivce.findByName(token.getUsername());
        if (user == null) {
            return null;
        }
        //2.判断密码
//        这里会把数据库中获取的password和token中的对比,如果相同就允许登陆,不同就抛出IncorrectCredentialsException异常
        return new SimpleAuthenticationInfo(user,user.getPassword(),"");
    }

    /**
     * 执行授权逻辑
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //给资源进行授权
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

        //到数据库查询当前登录用户的授权字符串
        //获取当前登录用户
        Subject subject = SecurityUtils.getSubject();
        User user = (User)subject.getPrincipal();
        User dbUser = userSerivce.findById(user.getId());

        info.addStringPermission(dbUser.getPerms());
        //info.addStringPermission("user:add");
        return info;
    }
}
