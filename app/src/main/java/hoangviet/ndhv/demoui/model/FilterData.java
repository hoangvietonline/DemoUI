package hoangviet.ndhv.demoui.model;

import android.graphics.Bitmap;

public class FilterData {
    private String filterName;
    private String filterPath;
    private String rule;
    private String filterThumb;
    private Bitmap thumbBitmap;
    private boolean chooseFilter;

    public FilterData() {
    }

    public FilterData(String filterName, String filterPath, String rule, Bitmap thumbBitmap, boolean chooseFilter) {
        this.filterName = filterName;
        this.filterPath = filterPath;
        this.rule = rule;
        this.thumbBitmap = thumbBitmap;
        this.chooseFilter = chooseFilter;
    }

    public FilterData(String filterName, String filterPath, String rule, String filterThumb) {
        this.filterName = filterName;
        this.filterPath = filterPath;
        this.rule = rule;
        this.filterThumb = filterThumb;
    }

    public String getFilterName() {
        return filterName;
    }

    public void setFilterName(String filterName) {
        this.filterName = filterName;
    }

    public String getFilterPath() {
        return filterPath;
    }

    public void setFilterPath(String filterPath) {
        this.filterPath = filterPath;
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    public Bitmap getThumbBitmap() {
        return thumbBitmap;
    }

    public void setThumbBitmap(Bitmap thumbBitmap) {
        this.thumbBitmap = thumbBitmap;
    }

    public boolean isChooseFilter() {
        return chooseFilter;
    }

    public void setChooseFilter(boolean chooseFilter) {
        this.chooseFilter = chooseFilter;
    }

    public String getFilterThumb() {
        return filterThumb;
    }

    public void setFilterThumb(String filterThumb) {
        this.filterThumb = filterThumb;
    }
}
