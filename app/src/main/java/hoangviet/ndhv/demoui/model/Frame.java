package hoangviet.ndhv.demoui.model;

public class Frame {
    private String nameFrame;
    private String thubFrame;
    private String pathFrame;
    private boolean isChooseFrame;

    public Frame(String nameFrame, String thubFrame, String pathFrame, boolean isChooseFrame) {
        this.nameFrame = nameFrame;
        this.thubFrame = thubFrame;
        this.pathFrame = pathFrame;
        this.isChooseFrame = isChooseFrame;
    }

    public Frame() {
    }

    public String getNameFrame() {
        return nameFrame;
    }

    public void setNameFrame(String nameFrame) {
        this.nameFrame = nameFrame;
    }

    public String getThubFrame() {
        return thubFrame;
    }

    public void setThubFrame(String thubFrame) {
        this.thubFrame = thubFrame;
    }

    public String getPathFrame() {
        return pathFrame;
    }

    public void setPathFrame(String pathFrame) {
        this.pathFrame = pathFrame;
    }

    public boolean isChooseFrame() {
        return isChooseFrame;
    }

    public void setChooseFrame(boolean chooseFrame) {
        isChooseFrame = chooseFrame;
    }
}
