package oraclefeeder.core.domain;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class CacheIterateGroup {

    private Integer id;
    private Integer totalQuerys;
    private String prefix;
    private Map<String,CacheResult> cacheResult = new HashMap<String, CacheResult>();
    private List<CacheResult> cacheResultList = new LinkedList<CacheResult>();

    public void convertMapToList(){
        this.cacheResultList = new LinkedList<CacheResult>(this.cacheResult.values());
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTotalQuerys() {
        return totalQuerys;
    }

    public void setTotalQuerys(Integer totalQuerys) {
        this.totalQuerys = totalQuerys;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public Map<String, CacheResult> getCacheResult() {
        return cacheResult;
    }

    public List<CacheResult> getCacheResultList() {
        return cacheResultList;
    }

    public void setCacheResultList(List<CacheResult> cacheResultList) {
        this.cacheResultList = cacheResultList;
    }
}
