package bookstore.service;

import org.springframework.ui.ModelMap;

import java.util.Date;

/**
 * Created by codeworm on 6/10/16.
 */
public interface StatService {
    String getCategoryStat();
    String getBookStat();
    String getDayStat(Date startDate, Date endDate);
    String getMonthStat(Date startDate, Date endDate);
    String getYearStat(Date startDate, Date endDate);
    boolean userStat(String username, ModelMap model);
}
