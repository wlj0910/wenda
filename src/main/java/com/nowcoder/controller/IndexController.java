package com.nowcoder.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

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
        return "home";
    }

}
