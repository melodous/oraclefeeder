package oraclefeeder.core.domain;

import java.util.List;

/**
 * Created by Alberto Pascual on 18/09/15.
 */
public class ThreadCacheResult {

    private Boolean isRunning;
    private List<CacheIterateGroup> cacheIterateGroups;

    public synchronized Boolean isRunning() {
        return isRunning;
    }

    public synchronized  void isRunning(Boolean isRunning) {
        this.isRunning = isRunning;
    }

    public synchronized List<CacheIterateGroup> getCacheIterateGroups() {
        return cacheIterateGroups;
    }

    public synchronized void setCacheIterateGroups(List<CacheIterateGroup> cacheIterateGroups) {
        this.cacheIterateGroups = cacheIterateGroups;
    }

    public synchronized void add(CacheIterateGroup cacheIterateGroup){
        this.cacheIterateGroups.add(cacheIterateGroup);
    }
}
