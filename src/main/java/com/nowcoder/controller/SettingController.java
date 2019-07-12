package com.nowcoder.controller;

import com.nowcoder.service.WendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
@Controller
public class SettingController {
    //WendaService wendaService=new WendaService();
    @Autowired
    WendaService wendaService;//享元模式

    @RequestMapping(path={"/setting"},method={RequestMethod.GET})
    @ResponseBody
    public String setting(){
        return "Setting OK!"+wendaService.getMessage(2);
    }
}
