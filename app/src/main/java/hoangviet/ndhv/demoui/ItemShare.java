package hoangviet.ndhv.demoui;

public class ItemShare {
    private int ImageShare;
    private String tittleShare;

    public ItemShare(int imageShare, String tittleShare) {
        ImageShare = imageShare;
        this.tittleShare = tittleShare;
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
}
