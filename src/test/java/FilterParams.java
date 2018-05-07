public class FilterParams {
    private String field;
    private String rule;
    private String value;

    public FilterParams(String field, String rule, String value) {
        this.field = field;
        this.rule = rule;
        this.value = value;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
