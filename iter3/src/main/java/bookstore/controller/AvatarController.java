package bookstore.controller;

import bookstore.service.UserService;
import bookstore.utility.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;

/**
 * Created by codeworm on 6/9/16.
 *
 * To show user's avatar
 */
@Controller
public class AvatarController {

    @Qualifier("UserService")
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/avatar")
    public void avatar(@RequestParam String username, HttpServletRequest request, HttpServletResponse response) {
        byte[] buf = userService.getAvatar(username);
        if (buf == null) {
            buf = FileUtil.loadFile(request, "img/default_avatar.png");
        }

        response.setContentType("image/png");
        try {
            OutputStream out = response.getOutputStream();
            out.write(buf);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}