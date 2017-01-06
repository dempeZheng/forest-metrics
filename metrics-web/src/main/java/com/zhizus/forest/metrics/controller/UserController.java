package com.zhizus.forest.metrics.controller;

import com.zhizus.forest.metrics.Constants;
import com.zhizus.forest.metrics.bean.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.*;

/**
 * Created by Dempe on 2017/1/5.
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @RequestMapping(value = "/index")
    public String index() {
        return "login";
    }

    @RequestMapping(value = "/login.do", method = RequestMethod.POST)
    public String login(@RequestParam String name, String pwd, Model model, HttpServletRequest request, HttpServletResponse response, HttpServlet servlet) {
        try {
            Subject subject = SecurityUtils.getSubject();
            // 已登陆则 跳到首页
            if (subject.isAuthenticated()) {
                return "redirect:/metric/index.do";
            }



            // 身份验证
            subject.login(new UsernamePasswordToken(name, pwd));
            // 验证成功在Session中保存用户信息
//            final User authUserInfo = userService.selectByUsername(user.getUsername());
            User user = new User();
            user.setName(name);
            user.setPwd(pwd);
            request.getSession().setAttribute("user", user);
            Cookie cookie = new Cookie(Constants.COOKIE_USER_NAME, user.getName());
            cookie.setMaxAge(Constants.WEEK_TTL);
            response.addCookie(cookie);

        } catch (AuthenticationException e) {
            // 身份验证失败
            model.addAttribute("error", "用户名或密码错误 ！");
            return "login";
        }
        return "redirect:/metric/index.do";
    }

    /**
     * 用户退出登录
     */
    @RequestMapping(value = "/logout.do", method = RequestMethod.GET)
    public String logout(HttpSession session) {
        session.removeAttribute("user");
        // 登出操作
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "login";
    }
}
