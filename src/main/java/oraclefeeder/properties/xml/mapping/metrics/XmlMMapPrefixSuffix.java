package oraclefeeder.properties.xml.mapping.metrics;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by Alberto Pascual on 12/08/15.
 */
@XmlRootElement(name="MapPrefixSuffix")
public class XmlMMapPrefixSuffix {

    private String from;
    private String file;
    private String map;
    private String reloadFreq;

    public String getFrom() {
        return from;
    }

    @XmlElement(name="From")
    public void setFrom(String from) {
        this.from = from;
    }

    public String getFile() {
        return file;
    }

    @XmlElement(name="File")
    public void setFile(String file) {
        this.file = file;
    }

    public String getMap() {
        return map;
    }

    @XmlElement(name="Map")
    public void setMap(String map) {
        this.map = map;
    }

    public String getReloadFreq() {
        return reloadFreq;
    }

    @XmlElement(name="ReloadFreq")
    public void setReloadFreq(String reloadFreq) {
        this.reloadFreq = reloadFreq;
    }
}
