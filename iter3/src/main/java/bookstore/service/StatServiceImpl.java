package bookstore.service;

import bookstore.DAO.UserDAO;
import bookstore.entity.Order;
import bookstore.entity.User;
import bookstore.entity.StatInfo;
import bookstore.utility.HibernateUtil;
import com.google.gson.Gson;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import javax.sql.DataSource;
import java.sql.*;
import java.util.*;
import java.util.Date;

/**
 * Created by codeworm on 6/10/16.
 */
@Service
public class StatServiceImpl implements StatService {

    @Autowired
    private DataSource db;

    @Qualifier("userDAOimpl")
    @Autowired
    private UserDAO userDAO;


    private CallableStatement createCall(String proc_name) {
        try {
            Connection con = db.getConnection();
            String sql = "{call " + proc_name + "()}";
            CallableStatement stmt = con.prepareCall(sql);
            return stmt;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private CallableStatement createCallWithDateRange(String proc_name, Date startDate, Date endDate) {
        java.sql.Date sqlStartDate = new java.sql.Date(startDate.getTime());
        java.sql.Date sqlEndDate = new java.sql.Date(endDate.getTime());
        try {
            Connection con = db.getConnection();
            String sql = "{call " + proc_name + "(?, ?)}";
            CallableStatement stmt = con.prepareCall(sql);
            stmt.setDate(1, sqlStartDate);
            stmt.setDate(2, sqlEndDate);
            return stmt;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private List<StatInfo> executeCall(CallableStatement stmt) {
        if (stmt == null) {
            return null;
        }

        try {
            boolean hasResult = stmt.execute();
            if (hasResult) {
                ResultSet rs = stmt.getResultSet();
                List res = new ArrayList();
                while(rs.next()) {
                    StatInfo item = new StatInfo();
                    item.setLabel(rs.getString(1));
                    item.setCents(rs.getInt(2));
                    res.add(item);
                }
                return res;
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String statEncode(List stat) {
        if (stat != null) {
            return new Gson().toJson(stat);
        } else {
            HashMap res = new HashMap<String, Object>();
            res.put("msg", "Some errors occured.");
            return new Gson().toJson(res);
        }
    }

    public String getCategoryStat() {
        CallableStatement stmt = createCall("category_stat");
        List stat = executeCall(stmt);
        return statEncode(stat);
    }

    public String getBookStat() {
        CallableStatement stmt = createCall("book_stat");
        List stat = executeCall(stmt);
        return statEncode(stat);
    }

    public String getDayStat(Date startDate, Date endDate) {
        CallableStatement stmt = createCallWithDateRange("day_stat", startDate, endDate);
        List stat = executeCall(stmt);
        return statEncode(stat);
    }

    public String getMonthStat(Date startDate, Date endDate) {
        CallableStatement stmt = createCallWithDateRange("month_stat", startDate, endDate);
        List stat = executeCall(stmt);
        return statEncode(stat);
    }

    public String getYearStat(Date startDate, Date endDate) {
        CallableStatement stmt = createCallWithDateRange("year_stat", startDate, endDate);
        List stat = executeCall(stmt);
        return statEncode(stat);
    }

    public boolean userStat(String username, ModelMap model) {
        User user = userDAO.get(username);
        if (user == null) {
            return false;
        } else {
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();

            List<Order> orders = session.createQuery("select o from Order o where username=:username order by o.date desc")
                    .setParameter("username", username)
                    .list();

            session.getTransaction().commit();

            int total = 0;
            for (Order o : orders) {
                total += o.getCents();
            }

            model.addAttribute("total_price", total / 100.0);
            model.addAttribute("username", username);
            model.addAttribute("num", orders.size());
            model.addAttribute("orders", orders);

            return true;
        }
    }

}
