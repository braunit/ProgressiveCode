package com.progressive.code.decorator.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by abraun on 10/11/2017.
 */
@Controller
public class DemoController {


    @RequestMapping(value="/")
    public String home() {
        return "home";
    }

    @RequestMapping(value="/about")
    public String about() {
        return "about";
    }

}
