package cn.org.zmabel.admin.advice;

import cn.hutool.json.JSONObject;
import cn.org.zmabel.admin.util.JsonResult;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@ControllerAdvice
public class MyControllerAdvice {
    /**
     * 应用到所有@RequestMapping注解方法，在其执行之前初始化数据绑定器
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        CustomDateEditor dateEditor = new CustomDateEditor(df, true);
        binder.registerCustomEditor(Date.class,dateEditor);
    }

    /**
     * 把值绑定到Model中，使全局@RequestMapping可以获取到该值
     */
    @ModelAttribute
    public void addAttributes(Model model) {
        model.addAttribute("author", "zm Abel");
    }

    /**
     * 全局异常捕捉处理
     */
    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public JSONObject errorHandler(Exception ex) {
        return JsonResult.fail("-200", ex.getMessage());
    }

    /**
     * 拦截捕捉自定义异常 AdviceException.class
     */
    @ResponseBody
    @ExceptionHandler(value = AdviceException.class)
    public JSONObject myErrorHandler(AdviceException ex) {
        return JsonResult.other(ex.getState(), ex.getMsg(), null);
    }
}
