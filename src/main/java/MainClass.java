import java.awt.*;
import java.net.MalformedURLException;
import java.util.Timer;

/**
 * @메쏘드명 : MainClass
 * @작성자 : jwt1029
 * @작성일자 : 2019-01-30
 * @설명 :
 */
public class MainClass {
    public static void main(String[] args) throws AWTException, MalformedURLException {
        final int second = 1000;
        Timer t = new Timer();
        DeliveryScanner deliveryScanner = new DeliveryScanner();

        // Usage
        //deliveryScanner.addDeliveryInfo(Const.CARRIER_ID.롯데택배.getID(),"40123150828");
        //deliveryScanner.addDeliveryInfo(Const.CARRIER_ID.CJ_대한통운.getID(),"33847554905");

        // This task is scheduled to run every 10 seconds

        t.scheduleAtFixedRate(deliveryScanner, 0, 30 * second);

    }

    public void displayTray(String caption, String text) throws AWTException, MalformedURLException {
        //Obtain only one instance of the SystemTray object
        SystemTray tray = SystemTray.getSystemTray();

        //If the icon is a file
        Image image = Toolkit.getDefaultToolkit().createImage("icon.png");
        //Alternative (if the icon is on the classpath):
        //Image image = Toolkit.getDefaultToolkit().createImage(getClass().getResource("icon.png"));

        TrayIcon trayIcon = new TrayIcon(image, "Tray Demo");
        //Let the system resize the image if needed
        trayIcon.setImageAutoSize(true);
        //Set tooltip text for the tray icon
        trayIcon.setToolTip("System tray icon demo");
        tray.add(trayIcon);

        trayIcon.displayMessage(caption, text, TrayIcon.MessageType.INFO);
    }
}
