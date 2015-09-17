package oraclefeeder.core.domain;

import java.util.Map;

/**
 * Created by Alberto Pascual on 27/08/15.
 */
public class CacheResult {

    private Integer id;
    private Integer totalRows;
    private Map<String,Map<String, String>> result;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTotalRows() {
        return totalRows;
    }

    public void setTotalRows(Integer totalRows) {
        this.totalRows = totalRows;
    }

    public Map<String,Map<String, String>> getResult() {
        return result;
    }

    public void setResult(Map<String,Map<String, String>> result) {
        this.result = result;
    }

}
