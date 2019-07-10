package com.nowcoder.controller;

import com.nowcoder.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Controller
public class IndexController {
    @RequestMapping("/")
    @ResponseBody
    public String index(){
        return "Hello NowCoder!";
    }
    @RequestMapping(path={"/profile/{groupId}/{userId}"})
    @ResponseBody
    public String profile(@PathVariable("userId") int userId,
                          @PathVariable("groupId") String groupId,
                          @RequestParam(value="type",defaultValue="1")int type,
                          @RequestParam(value="key",required=false) String key
                          ){
        return String.format("The user is %s / %d, type=%d,key=%s ",groupId,userId,type,key);
    }
    @RequestMapping(path={"/html"},method={RequestMethod.GET})
    public String template(Model model){
        model.addAttribute("toUserName","wlj");
        List<String> colors= Arrays.asList(new String[]{"RED","Green","BLUE"});
        model.addAttribute("colors",colors);
        model.addAttribute("message","I'am glad to see you!");
        model.addAttribute("fromUserName","lc");
        model.addAttribute("time","2019-7-10");
        User user=new User("wlj",24,"China");
        model.addAttribute("user",user);
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("user",user);
        model.addAttribute("map",map);
        return "home";

    }

}
