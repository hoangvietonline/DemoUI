package hoangviet.ndhv.demoui;

public class Mirror {
    private int imgMirror;
    private String txtMirror;
    private boolean click;

    public Mirror(int imgMirror, String txtMirror) {
        this.imgMirror = imgMirror;
        this.txtMirror = txtMirror;
    }

    public Mirror(int imgMirror, String txtMirror, boolean click) {
        this.imgMirror = imgMirror;
        this.txtMirror = txtMirror;
        this.click = click;
    }

    public boolean isClick() {
        return click;
    }

    public void setClick(boolean click) {
        this.click = click;
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
