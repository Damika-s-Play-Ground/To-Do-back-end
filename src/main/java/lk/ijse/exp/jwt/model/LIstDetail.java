package lk.ijse.exp.jwt.model;

/**
 * @author : Damika Anupama Nanayakkara <damikaanupama@gmail.com>
 * @since : 03/01/2021
 **/
public class LIstDetail {
    private String uid;
    private String lid;

    public LIstDetail() {
    }

    public LIstDetail(String uid, String lid) {
        this.uid = uid;
        this.lid = lid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getLid() {
        return lid;
    }

    public void setLid(String lid) {
        this.lid = lid;
    }

    @Override
    public String toString() {
        return "LIstDetail{" +
                "uid='" + uid + '\'' +
                ", lid='" + lid + '\'' +
                '}';
    }
}
