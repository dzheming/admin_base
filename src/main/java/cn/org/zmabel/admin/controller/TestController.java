package cn.org.zmabel.admin.controller;

import cn.hutool.json.JSONObject;
import cn.org.zmabel.admin.advice.AdviceException;
import cn.org.zmabel.admin.entity.mapper.UserMapper;
import cn.org.zmabel.admin.entity.model.User;
import cn.org.zmabel.admin.util.JsonResult;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/test")
public class TestController {
    
    @Autowired
    UserMapper mapper;

    @RequestMapping("/advice")
    public String testAdvice(@ModelAttribute("author") String author, Date d) {
        System.out.println(d.getTime());
        throw new AdviceException("-101", author + " test advice exception");
    }
    
    @RequestMapping("/insert")
    public JSONObject testInsert(String username, String password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setName(username);
        user.setAvatar("");
        int result = mapper.addUser(user);
        return JsonResult.success(user.getId());
    }
    
    @RequestMapping("/all")
    public JSONObject testQuery(Integer pageNum, Integer pageSize) {
        if (pageSize == null)
            throw new AdviceException("-102", "page size can not be null");
        PageHelper.startPage(pageNum == null ? 1 : pageNum, pageSize);
        List<User> users = mapper.findAllUsers();
        PageInfo<User> pageInfo = new PageInfo<>(users);
        return JsonResult.success(users);
    }
}
