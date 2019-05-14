package inven;

import java.util.Date;

/**
 * UserInfo
 *
 * @author : jwt1029
 * @date : 2019-05-13
 * @description :
 */
public class UserInfo {
    String userName;
    Date lastScanDate;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getLastScanDate() {
        return lastScanDate;
    }

    public void setLastScanDate(Date lastScanDate) {
        this.lastScanDate = lastScanDate;
    }
}
