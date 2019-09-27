package hoangviet.ndhv.demoui;

public class Mirror {
    private int imgMirror;
    private String txtMirror;

    public Mirror(int imgMirror, String txtMirror) {
        this.imgMirror = imgMirror;
        this.txtMirror = txtMirror;
    }

    public int getImgMirror() {
        return imgMirror;
    }

    public void setImgMirror(int imgMirror) {
        this.imgMirror = imgMirror;
    }

    public String getTxtMirror() {
        return txtMirror;
    }

    public void setTxtMirror(String txtMirror) {
        this.txtMirror = txtMirror;
    }
}
