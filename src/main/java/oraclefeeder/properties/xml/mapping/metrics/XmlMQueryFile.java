package oraclefeeder.properties.xml.mapping.metrics;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by Alberto Pascual on 12/08/15.
 */
@XmlRootElement(name="QueryFile")
public class XmlMQueryFile {

    List<String> file;


    public List<String> getFile() {
        return file;
    }

    @XmlElement(name="file")
    public void setFile(List<String> file) {
        this.file = file;
    }
}
