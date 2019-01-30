import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

import static javafx.css.StyleOrigin.USER_AGENT;

/**
 * @메쏘드명 : DeliveryScanner
 * @작성자 : jwt1029
 * @작성일자 : 2019-01-30
 * @설명 :
 */
public class DeliveryScanner extends TimerTask {
    private List<DeliveryInfo> deliveryInfoList = new ArrayList();

    public void addDeliveryInfo(String carrierId, String trackId) {
        DeliveryInfo info = new DeliveryInfo();
        info.setCarrierId(carrierId);
        info.setTrackId(trackId);
        info.setProgressCount(0);

        deliveryInfoList.add(info);
    }

    @Override
    public void run() {
        try {
            for (DeliveryInfo info : deliveryInfoList) {
                String url = "https://apis.tracker.delivery/carriers/" + info.getCarrierId() + "/tracks/" + info.getTrackId();

                HttpClient client = HttpClientBuilder.create().build();
                HttpGet request = new HttpGet(url);

                // add request header
                request.addHeader("User-Agent", String.valueOf(USER_AGENT));
                HttpResponse response = client.execute(request);

                System.out.println("Response Code : "
                        + response.getStatusLine().getStatusCode());

                BufferedReader rd = new BufferedReader(
                        new InputStreamReader(response.getEntity().getContent()));

                StringBuffer result = new StringBuffer();
                String line;
                while ((line = rd.readLine()) != null) {
                    result.append(line);
                }

                System.out.println("Response Code : " + response.getStatusLine().getStatusCode() );
                // Success
                if(response.getStatusLine().getStatusCode() == 200) {
                    JSONParser jsonParser = new JSONParser();
                    JSONObject jsonObject = (JSONObject) jsonParser.parse(result.toString());

                    JSONArray progresses = (JSONArray) jsonObject.get("progresses");
                    if(progresses.size() > info.getProgressCount()) {
                        System.out.println("상태 업데이트");
                        info.setProgressCount(progresses.size());

                        String caption = Const.CARRIER_ID.getEnumByID(info.getCarrierId()).name() + "택배 진행상태 갱신";
                        String text = "";

                        JSONObject lastProgress = (JSONObject) progresses.get(progresses.size() - 1);

                        JSONObject location = (JSONObject) lastProgress.get("location");
                        String locationName = location.get("name").toString() + "\r\n";
                        text += locationName;

                        JSONObject status = (JSONObject) lastProgress.get("status");
                        String statusText = status.get("text").toString() + "\r\n";
                        text += statusText;

                        text += lastProgress.get("description").toString();


                        if (SystemTray.isSupported()) {
                            MainClass td = new MainClass();
                            try {
                                td.displayTray(caption, text);
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
            }

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
