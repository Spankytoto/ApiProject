package api.helpers;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateHelper {

    public String getCurrentDate() {
        Date dateNow = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(dateNow);
    }
}
