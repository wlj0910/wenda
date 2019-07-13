package com.nowcoder.controller;

import com.nowcoder.aspect.LogAspect;
import com.nowcoder.model.userTest;
import com.nowcoder.service.WendaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
public class IndexController {
    private static final Logger logger= LoggerFactory.getLogger(IndexController.class);
    //WendaService wendaService=new WendaService();
    @Autowired
    WendaService wendaService;  //享元模式

    @RequestMapping("/")
    @ResponseBody
    public String index(HttpSession httpSession){
        logger.info("visit home  "+new Date());
        return "Setting OK!"+wendaService.getMessage(1);
        //return "Hello nowcoder!"+httpSession.getAttribute("msg");
    }
    @RequestMapping(path={"/profile/{groupId}/{userId}"})
    @ResponseBody
    public String profile(@PathVariable("groupId") String groupId,
                          @PathVariable("userId") int userId,
                          @RequestParam(value="type",defaultValue="1")int type,
                          @RequestParam(value="key",required=false) String key
                          ){
        return String.format("The user is %s / %d, type=%d,key=%s ",groupId,userId,type,key);
    }
    @RequestMapping(path={"/vm"},method={RequestMethod.GET})
    public String templateVM(Model model){
        model.addAttribute("value","Happy");
        return "testVM";
    }
    @RequestMapping(path={"/html"},method={RequestMethod.GET})
    public String template(Model model){
        model.addAttribute("toUserName","wlj");
        List<String> colors= Arrays.asList(new String[]{"RED","Green","BLUE"});
        model.addAttribute("colors",colors);
        model.addAttribute("message","I'am glad to see you!");
        model.addAttribute("fromUserName","lc");
        model.addAttribute("time","2019-7-10");
        userTest user=new userTest("wlj",24,"China");
        model.addAttribute("user",user);
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("user",user);
        model.addAttribute("map",map);
        return "home";
    }
    @RequestMapping(path={"/request"}, method={RequestMethod.GET})
    @ResponseBody
    public String request(Model model, HttpServletResponse response,
                          HttpServletRequest request,
                          HttpSession httpSession,
                          @CookieValue("JSESSIONID") String sessionId){
        StringBuilder sb=new StringBuilder();
        sb.append("COOKIEVALUE:"+sessionId+"<br>");//登录注册需要用到。
        Enumeration<String> headerNames=request.getHeaderNames();
        while(headerNames.hasMoreElements()){
            String name=headerNames.nextElement();
            sb.append(name+":"+request.getHeader(name)+"<br>");
        }
        if(request.getCookies()!=null){
            for(Cookie cookie:request.getCookies()){
                sb.append("Cookie:"+cookie.getName()+",value:"+cookie.getValue());
            }
        }
        sb.append(request.getMethod()+"<br>");
        sb.append(request.getQueryString()+"<br>");
        sb.append(request.getPathInfo()+"<br>");
        sb.append(request.getRequestURL()+"<br>");
        sb.append(request.getRequestURI()+"<br>");
        response.addHeader("nowcoderId","hello");
        response.addCookie(new Cookie("username","nowcoder"));
        //response.getOutputStream().wwrite(new byte[]{});  new byte[]中存放验证码图片生成的二进制流
        return sb.toString();
    }

    @RequestMapping("/redirect/{code}")
    public String redirect(@PathVariable("code") int code,
                           HttpSession httpSession){
        httpSession.setAttribute("msg","jump from redirect");
        return "redirect:/";
    }
    @RequestMapping(path="/redirect1/{code1}",method={RequestMethod.GET})
    public RedirectView redirect1(@PathVariable("code") int code,
                                  HttpSession httpSession){
        httpSession.setAttribute("msg","jump from redirect");
        RedirectView red=new RedirectView("/",true);
        if(code==301){
            red.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
        }
        return red;
    }

    @RequestMapping(path={"/admin"},method={RequestMethod.GET})
    @ResponseBody
    public String admin(@RequestParam("key") String key){
        if("admin".equals(key)){
            return "hello admin";
        }
        throw new IllegalArgumentException("参数不对");
    }

    @ExceptionHandler()
    @ResponseBody
    public String error(Exception e){
        return "error"+e.getMessage();
    }

}
