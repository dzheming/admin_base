package cn.org.zmabel.admin.secruity;

import cn.hutool.json.JSONObject;
import cn.org.zmabel.admin.entity.mapper.UserMapper;
import cn.org.zmabel.admin.entity.model.Token;
import cn.org.zmabel.admin.entity.model.User;
import cn.org.zmabel.admin.util.JsonResult;
import cn.org.zmabel.admin.util.JwtTokenUtil;
import cn.org.zmabel.admin.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    UserMapper userMapper;

    @PostMapping("/login")
    public JSONObject login(@RequestBody User u) {
        User user = userMapper.findByUsername(u.getUsername());
        if (user == null || !user.getPassword().equals(MD5Util.calcMD5(u.getPassword())))
            return JsonResult.fail("-100", "username or password error!");
        Token token = new Token(jwtTokenUtil.getTokenPrefix(), jwtTokenUtil.generateToken(user));
        return JsonResult.success(token);
    }
    
    @RequestMapping("/outLogin")
    public JSONObject outLogin() {
        return JsonResult.success("");
    }
}
