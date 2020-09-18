package cn.org.zmabel.admin.controller;

import cn.hutool.json.JSONObject;
import cn.org.zmabel.admin.entity.mapper.UserMapper;
import cn.org.zmabel.admin.entity.model.User;
import cn.org.zmabel.admin.util.JwtTokenUtil;
import cn.org.zmabel.admin.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    
    @Autowired
    UserMapper userMapper;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @RequestMapping("/userinfo")
    @ResponseBody
    public ResponseEntity<JSONObject> getUser(HttpServletRequest servletRequest) {
        Map<String, Object> claims = jwtTokenUtil.getClaimsFromToken(servletRequest);
        if (claims == null) {
            return new ResponseEntity<>(JsonResult.fail("-100", "error token"), HttpStatus.UNAUTHORIZED);
        }
        String username = (String) claims.get("username");
        User user = userMapper.findByUsername(username);
        if (user == null) {
            return new ResponseEntity<>(JsonResult.fail("-100","error token"), HttpStatus.UNAUTHORIZED);
        }
        user.setPassword("");
        return new ResponseEntity<>(JsonResult.success(user), HttpStatus.OK);
    }
    
    @RequestMapping("/queryAllUser")
    public ResponseEntity<JSONObject> queryAllUser(HttpServletRequest servletRequest) {
        Map<String, Object> claims = jwtTokenUtil.getClaimsFromToken(servletRequest);
        if (claims == null) {
            return new ResponseEntity<>(JsonResult.fail("-100", "error token"), HttpStatus.UNAUTHORIZED);
        }
        List<User> users = userMapper.findAllUsers();
        return new ResponseEntity<>(JsonResult.success(users), HttpStatus.OK);
    }
}
