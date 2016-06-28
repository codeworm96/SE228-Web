package bookstore.utility;

/**
 * Created by codeworm on 6/9/16.
 *
 * Utility about order's status
 */
public class OrderStatusConst {
    public static final int ACCEPTED = 0;
    public static final int PAID = 1;

    public static String to_str(int status) {
        if (status == ACCEPTED) {
            return "待付款";
        } else if (status == PAID) {
            return "已付款";
        } else {
            return "未知";
        }
    }
}
