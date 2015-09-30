package oraclefeeder.core.domain;

import java.util.List;

/**
 * Created by Alberto Pascual on 21/09/15.
 */
public class CacheResult {

    private String instacesFrom;
    private List<CacheColumn> cacheColumns;

    public String getInstacesFrom() {
        return instacesFrom;
    }

    public void setInstacesFrom(String instacesFrom) {
        this.instacesFrom = instacesFrom;
    }

    public List<CacheColumn> getCacheColumns() {
        return cacheColumns;
    }

    public void setCacheColumns(List<CacheColumn> cacheColumns) {
        this.cacheColumns = cacheColumns;
    }

}
