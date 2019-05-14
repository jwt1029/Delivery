package inven;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;


/**
 * MyWay
 *
 * @author : jwt1029
 * @date : 2019-05-14
 * @description :
 */
public class MyWay {
    public static void main(String[] args) throws IOException {
        String loginPage = "https://member.inven.co.kr/user/scorpio/mlogin";
        String loginUrl = "https://member.inven.co.kr/m/login/dispatch";
        String skillUrl = "http://www.inven.co.kr/member/skill/cast_save.xml.php?dummy=1557792632427";
        String user_id = "jwt1029";
        String password = "jwt3141592";
        String kp = "0";


        Connection.Response loginPageRes = Jsoup.connect(loginPage)
                .method(Connection.Method.GET).execute();
        Document loginPageDoc = loginPageRes.parse();
        String st = loginPageDoc.selectFirst("#stoken").val();
        String session = loginPageRes.cookie("PHPSESSID");

        Connection.Response loginRes= Jsoup.connect(loginUrl)
                .data("user_id", user_id)
                .data("password", password)
                .data("kp", kp)
                .data("st", st)
                .data("wsip", "")
                .data("surl", "http://www.inven.co.kr/")
                .method(Connection.Method.POST)
                .cookie("PHPSESSID", session)
                .execute();

        Document loginDoc = loginRes.parse();
        String M_ID = loginRes.cookie("M_ID");
        String M_INV = loginRes.cookie("M_INV");
        String M_SID = loginRes.cookie("M_SID");

        System.out.println(loginDoc.text());
        System.out.println("M_ID : " + M_ID);
        System.out.println("M_INV : " + M_INV);
        System.out.println("M_SID : " + M_SID);



    }
}
