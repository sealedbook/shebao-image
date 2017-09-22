/**
 * Copyright (c) 2017, TalkingData and/or its affiliates. All rights reserved.
 */
package net.vicp.sealedbook.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author shitianshu on 2017/6/21 上午12:35.
 */
@Controller
public class SystemController {

    @ResponseBody
    @RequestMapping("/ruok")
    public String available() {
        return "OK";
    }

}
