package hoangviet.ndhv.demoui.model;

public class FilterData  {
    private String filterName;
    private String rule;
    private int filterId;
    private boolean chooseFilter;

    public FilterData(String filterName, String rule, int filterId, boolean chooseFilter) {
        this.filterName = filterName;
        this.rule = rule;
        this.filterId = filterId;
        this.chooseFilter = chooseFilter;
    }

    public String getFilterName() {
        return filterName;
    }

    public void setFilterName(String filterName) {
        this.filterName = filterName;
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    public int getFilterId() {
        return filterId;
    }

    public void setFilterId(int filterId) {
        this.filterId = filterId;
    }

    public boolean isChooseFilter() {
        return chooseFilter;
    }

    public void setChooseFilter(boolean chooseFilter) {
        this.chooseFilter = chooseFilter;
    }
}
