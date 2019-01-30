/**
 * @메쏘드명 : Const
 * @작성자 : jwt1029
 * @작성일자 : 2019-01-30
 * @설명 :
 */
public class Const {
    public static enum CARRIER_ID {
        CJ_대한통운("kr.cjlogistics"),
        CU_편의점택배("kr.cupost"),
        GS_Postbox_택배("kr.cvsnet"),
        우체국_택배("kr.epost"),
        한진택배("kr.hanjin"),
        로젠택배("kr.logen"),
        롯데택배("kr.lotte");

        private String id;

        CARRIER_ID(String id) {
            this.id = id;
        }
        public String getID() {
            return this.id;
        }

        public static CARRIER_ID getEnumByID(String id) {
            CARRIER_ID[] var1 = values();
            int var2 = var1.length;

            for(int var3 = 0; var3 < var2; ++var3) {
                CARRIER_ID e = var1[var3];
                if (id.equals(e.id)) {
                    return e;
                }
            }

            return null;
        }
    }
}
