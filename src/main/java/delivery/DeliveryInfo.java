package delivery;

/**
 * @메쏘드명 : DeliveryInfo
 * @작성자 : jwt1029
 * @작성일자 : 2019-01-30
 * @설명 :
 */
public class DeliveryInfo {
    private String carrierId;
    private String trackId;
    private Integer progressCount;

    public String getCarrierId() {
        return carrierId;
    }

    public void setCarrierId(String carrierId) {
        this.carrierId = carrierId;
    }

    public String getTrackId() {
        return trackId;
    }

    public void setTrackId(String trackId) {
        this.trackId = trackId;
    }

    public Integer getProgressCount() {
        return progressCount;
    }

    public void setProgressCount(Integer progressCount) {
        this.progressCount = progressCount;
    }
}
