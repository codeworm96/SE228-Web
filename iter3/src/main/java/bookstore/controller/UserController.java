package bookstore.controller;

import bookstore.entity.User;
import bookstore.service.UserService;
import bookstore.utility.FileUtil;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

/**
 * Created by codeworm on 6/10/16.
 */
@Controller
@RequestMapping("/admin/user")
public class UserController {

    @Qualifier("UserService")
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/")
    public String index() {
        return "/WEB-INF/view/admin/user.jsp";
    }

    @RequestMapping(value = "/list")
    @ResponseBody
    public String list(HttpServletRequest request) {
        String tmp;
        int page, rows;
        tmp = request.getParameter("page");
        if (tmp != null) {
            page = Integer.parseInt(tmp);
        } else {
            page = 1;
        }
        tmp = request.getParameter("rows");
        if (tmp != null) {
            rows = Integer.parseInt(tmp);
        } else {
            rows = 10;
        }
        int offset = (page - 1) * rows;

        HashMap res = new HashMap<String, Object>();
        res.put("total", userService.getCount());
        res.put("rows", userService.getList(offset, rows));

        return new Gson().toJson(res);
    }

    @RequestMapping(value = "/remove")
    @ResponseBody
    public String remove(@RequestParam String username) {
        userService.delete(username);

        HashMap res = new HashMap<String, Object>();
        res.put("success", true);
        return new Gson().toJson(res);
    }

    @RequestMapping(value = "/save")
    @ResponseBody
    public String add(@RequestBody String json) {
        Gson gson = new Gson();

        User user = gson.fromJson(json, User.class);
        if (user.getIsAdmin().equals("Y")) {
            user.setIsAdmin("Y");
        } else {
            user.setIsAdmin("N");
        }
        userService.saveOrUpdate(user);

        HashMap res = new HashMap<String, Object>();
        res.put("success", true);
        return gson.toJson(res);
    }

    @RequestMapping(value = "/avatar")
    @ResponseBody
    public String upload_avatar(@RequestParam String username, HttpServletRequest request) {
        byte[] content = FileUtil.getUploadContent(request, "avatar");
        return userService.saveOrUpdateAvatar(username, content);
    }
}
