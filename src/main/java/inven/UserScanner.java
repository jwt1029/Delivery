package inven;

import delivery.Const;
import delivery.DeliveryInfo;
import inven.MainClass;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.DateUtils;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.*;
import java.util.List;

import static javafx.css.StyleOrigin.USER_AGENT;

/**
 * @메쏘드명 : UserScanner
 * @작성자 : jwt1029
 * @작성일자 : 2019-01-30
 * @설명 :
 */
public class UserScanner extends TimerTask {
    private List<UserInfo> userInfoList = new ArrayList();

    public void addUserInfo(String userName) throws UnsupportedEncodingException {
        UserInfo info = new UserInfo();
        info.setUserName(URLEncoder.encode(userName, "UTF-8"));
        info.setLastScanDate(new Date());

        userInfoList.add(info);
    }

    @Override
    public void run() {
        try {
            for (UserInfo info : userInfoList) {
                boolean newArticleExist = false;
                String url = "http://m.inven.co.kr/board/powerbbs.php?come_idx=2299&stype=nickname&svalue=" + info.getUserName();

                Document doc = Jsoup.connect(url).get();
                Elements newArticles = doc.select("li.articleSubject");
                for (Element newArticle : newArticles) {
                    Element postDateSpan = newArticle.selectFirst("span.postdate");
                    String postDateString = postDateSpan.selectFirst("[title]").attributes().get("title");

                    String title = newArticle.selectFirst(".title").text();

                    Date postDate = DateUtils.parseDate(postDateString, new String[]{"yyyy-MM-dd HH:mm"});
                    Calendar c = Calendar.getInstance();
                    c.setTime(postDate);
                    c.add(Calendar.HOUR, -9);

                    postDate = c.getTime();

                    if(postDate.compareTo(info.getLastScanDate()) > 0) {
                        newArticleExist = true;
                        if (SystemTray.isSupported()) {
                            MainClass td = new MainClass();
                            try {
                                td.displayTray("[새 글 알림] " +  URLDecoder.decode(info.getUserName(), "UTF-8") + " (" + postDateString + ")", title);
                            } catch (AWTException e) {
                                e.printStackTrace();
                            } catch (MalformedURLException e) {
                                e.printStackTrace();
                            }
                        } else {
                            System.err.println("System tray not supported!");
                        }
                    }

                }

                if(newArticleExist) {
                    info.setLastScanDate(new Date());
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
