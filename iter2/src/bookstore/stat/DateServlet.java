package bookstore.stat;

import bookstore.DBServlet;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.text.SimpleDateFormat;
import java.text.Format;
import java.util.Calendar;

/**
 * Created by codeworm on 5/5/16.
 *
 * Statistic by date
 */
@WebServlet("/stat/date")
public class DateServlet extends DBServlet {
    protected void stat(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String sql = "select date, sum(price) as price from `order` WHERE status='paid' GROUP BY date ORDER BY date;";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            DateStruct res = new DateStruct();
            res.day = new ArrayList();
            res.month = new ArrayList();
            res.year = new ArrayList();

            Format day_formatter = new SimpleDateFormat("yyyy-MM-dd");
            Format month_formatter = new SimpleDateFormat("yyyy-MM");
            Format year_formatter = new SimpleDateFormat("yyyy");

            if (rs != null && rs.next()) {
                Date date = rs.getDate("date");
                int price = rs.getInt("price");

                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                int last_year = calendar.get(Calendar.YEAR);
                int last_month = calendar.get(Calendar.MONTH);

                DateInfo d1 = new DateInfo();
                d1.setLabel(day_formatter.format(date));
                d1.setPrice(price);
                res.day.add(d1);

                DateInfo d2 = new DateInfo();
                d2.setLabel(month_formatter.format(date));
                d2.setPrice(price);
                res.month.add(d2);

                DateInfo d3 = new DateInfo();
                d3.setLabel(year_formatter.format(date));
                d3.setPrice(price);
                res.year.add(d3);

                while (rs.next()) {
                    date = rs.getDate("date");
                    price = rs.getInt("price");

                    calendar.setTime(date);
                    int year = calendar.get(Calendar.YEAR);
                    int month = calendar.get(Calendar.MONTH);

                    d1 = new DateInfo();
                    d1.setLabel(day_formatter.format(date));
                    d1.setPrice(price);
                    res.day.add(d1);

                    if (last_year == year && last_month == month) {
                        DateInfo t = res.month.get(res.month.size() - 1);
                        t.setPrice(t.getPrice() + price);
                    } else {
                        d2 = new DateInfo();
                        d2.setLabel(month_formatter.format(date));
                        d2.setPrice(price);
                        res.month.add(d2);
                    }

                    if (last_year == year) {
                        DateInfo t = res.year.get(res.year.size() - 1);
                        t.setPrice(t.getPrice() + price);
                    } else {
                        d3 = new DateInfo();
                        d3.setLabel(year_formatter.format(date));
                        d3.setPrice(price);
                        res.year.add(d3);
                    }

                    last_year = year;
                    last_month = month;
                }
            }

            PrintWriter out = response.getWriter();
            out.println(new Gson().toJson(res));
        } catch (SQLException e){
            e.printStackTrace();

            HashMap res = new HashMap<String, Object>();
            res.put("msg", "Some errors occured.");
            PrintWriter out = response.getWriter();
            out.println(new Gson().toJson(res));
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        stat(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        stat(request, response);
    }
}
