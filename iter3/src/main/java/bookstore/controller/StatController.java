package bookstore.controller;

import bookstore.service.StatService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

@Controller
@RequestMapping(value = "/stat")
public class StatController {

    @Autowired
    private StatService statService;

    @RequestMapping(value = "/")
    public String index() {
        return "/WEB-INF/view/stat/index.jsp";
    }

    @RequestMapping(value = "/category")
    @ResponseBody
    public String category() {
        return statService.getCategoryStat();
    }

    @RequestMapping(value = "/book")
    @ResponseBody
    public String book() {
        return statService.getBookStat();
    }

    @RequestMapping(value = "/date")
    public String date(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
                       @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,
                       ModelMap model) {
        if (startDate == null || endDate == null) {
            model.addAttribute("msg", "日期填写有误");
            return "/WEB-INF/view/error.jsp";
        } else {
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            model.addAttribute("startDate", formatter.format(startDate));
            model.addAttribute("endDate", formatter.format(endDate));
            return "/WEB-INF/view/stat/date.jsp";
        }
    }

    @RequestMapping(value = "/date/day")
    @ResponseBody
    public String day(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
                      @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
        if (startDate == null || endDate == null) {
            HashMap res = new HashMap<String, Object>();
            res.put("msg", "invalid date");
            return new Gson().toJson(res);
        } else {
            return statService.getDayStat(startDate, endDate);
        }
    }

    @RequestMapping(value = "/date/month")
    @ResponseBody
    public String month(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
                        @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
        if (startDate == null || endDate == null) {
            HashMap res = new HashMap<String, Object>();
            res.put("msg", "invalid date");
            return new Gson().toJson(res);
        } else {
            return statService.getMonthStat(startDate, endDate);
        }
    }

    @RequestMapping(value = "/date/year")
    @ResponseBody
    public String year(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
                       @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
        if (startDate == null || endDate == null) {
            HashMap res = new HashMap<String, Object>();
            res.put("msg", "invalid date");
            return new Gson().toJson(res);
        } else {
            return statService.getYearStat(startDate, endDate);
        }
    }

    @RequestMapping(value = "/user")
    public String user(@RequestParam String username, ModelMap model) {
        if (statService.userStat(username, model)) {
            return "/WEB-INF/view/stat/user.jsp";
        } else {
            model.addAttribute("msg", "用户不存在");
            return "/WEB-INF/view/error.jsp";
        }
    }

}