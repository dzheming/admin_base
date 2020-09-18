package cn.org.zmabel.admin.controller;

import cn.hutool.core.util.StrUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @RequestMapping("/hello")
    public String sayHello(@RequestParam(required = false, name = "who") String who) {
        return "Hello " + (StrUtil.isBlank(who) ? "world" : who);
    }

}
