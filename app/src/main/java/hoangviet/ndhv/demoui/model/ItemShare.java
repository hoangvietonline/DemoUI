package hoangviet.ndhv.demoui.model;

public class ItemShare {
    private int ImageShare;
    private String tittleShare;
    private String appName;

    public ItemShare(int imageShare, String tittleShare, String appName) {
        ImageShare = imageShare;
        this.tittleShare = tittleShare;
        this.appName = appName;
    }

    public int getImageShare() {
        return ImageShare;
    }

    public void setImageShare(int imageShare) {
        ImageShare = imageShare;
    }

    public String getTittleShare() {
        return tittleShare;
    }

    public void setTittleShare(String tittleShare) {
        this.tittleShare = tittleShare;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }
}
