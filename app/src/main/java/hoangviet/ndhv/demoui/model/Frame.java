package hoangviet.ndhv.demoui.model;

public class Frame {
    private int imgFrame;
    private boolean isChooseFrame;

    public Frame(int imgFrame, boolean isChooseFrame) {
        this.imgFrame = imgFrame;
        this.isChooseFrame = isChooseFrame;
    }

    public int getImgFrame() {
        return imgFrame;
    }

    public void setImgFrame(int imgFrame) {
        this.imgFrame = imgFrame;
    }

    public boolean isChooseFrame() {
        return isChooseFrame;
    }

    public void setChooseFrame(boolean chooseFrame) {
        isChooseFrame = chooseFrame;
    }
}
