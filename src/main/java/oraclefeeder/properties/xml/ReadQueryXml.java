package oraclefeeder.properties.xml;

import oraclefeeder.exception.OracleFeederException;
import oraclefeeder.properties.xml.mapping.Metric;
import oraclefeeder.properties.xml.mapping.Query;
import oraclefeeder.properties.xml.mapping.metrics.XmlMColumn;
import oraclefeeder.properties.xml.mapping.metrics.XmlMIterateGroup;
import oraclefeeder.properties.xml.mapping.metrics.XmlMMetric;
import oraclefeeder.properties.xml.mapping.metrics.XmlMMetrics;
import oraclefeeder.properties.xml.mapping.metrics.XmlMParameter;
import oraclefeeder.properties.xml.mapping.metrics.XmlMQuery;
import oraclefeeder.properties.xml.mapping.querys.Parameter;
import oraclefeeder.properties.xml.mapping.querys.XmlQColumn;
import oraclefeeder.properties.xml.mapping.querys.XmlQQuery;
import oraclefeeder.properties.xml.mapping.querys.XmlQQuerys;
import oraclefeeder.properties.xml.mapping.querys.XmlQResult;
import oraclefeeder.utils.ParserStatement;
import oraclefeeder.utils.constants.Constants;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


public class ReadQueryXml {

    public ReadQueryXml() { }

    public static List<Metric> getMetricConfig() throws OracleFeederException {
        List<Metric> metrics = new LinkedList<Metric>();
        Metric metric;
        for(XmlMMetric xmlMMetric:getMetrics().getXmlMMetrics()) {
            List<XmlQQuerys> xmlQQuerys = new LinkedList<XmlQQuerys>();
            if(xmlMMetric.getXmlMQueryFile() == null || xmlMMetric.getXmlMQueryFile().getFile() == null) {
                xmlQQuerys.add(getQuerys(Constants.QUERY_DEFAU_XML));
            } else {
                for(String file: xmlMMetric.getXmlMQueryFile().getFile()){
                    xmlQQuerys.add(getQuerys(file));
                }
            }
            for(XmlMIterateGroup xmlMIterateGroup:xmlMMetric.getXmlMIterateGroups().getXmlMIterateGroups()) {
                metric = new Metric();
                ParserStatement parserStatement = new ParserStatement();
                if(xmlMIterateGroup.getXmlMStatement() != null) {
                    parserStatement.setStatement(xmlMIterateGroup.getXmlMStatement().getStatement());
                    metric.setStatement(parserStatement.parser());
                    metric.setCacheTimeSec(Integer.valueOf(xmlMIterateGroup.getXmlMStatement().getCacheTimeSec()));
                    metric.setCache(Boolean.valueOf(xmlMIterateGroup.getXmlMStatement().getCache()));
                }
                metric.setId(Integer.valueOf(xmlMIterateGroup.getId()));
                metric.setPrefix(xmlMIterateGroup.getPrefix());

                metric.setMapPrefixSuffix(xmlMIterateGroup.getXmlMMapPrefixSuffix());
                if(xmlMIterateGroup.getInstanceFrom() != null){
                    metric.setInstanceFrom(xmlMIterateGroup.getInstanceFrom().getInstancesFrom());
                    metric.setInstanceFromType(xmlMIterateGroup.getInstanceFrom().getType());
                }
                if(xmlMIterateGroup.getResult() != null) {
                    Map<String, String> columns = new HashMap<String, String>();
                    for (XmlMColumn xmlMColumn : xmlMIterateGroup.getResult().getXmlMColumns()) {
                        columns.put(xmlMColumn.getName(), xmlMColumn.getType());
                    }
                    metric.setColumns(columns);
                }

                List<Query> querys = new LinkedList<Query>();
                for(XmlMQuery xmlMQuery: xmlMIterateGroup.getXmlMQuerys().getXmlMQueries()){
                    int sizeStatus = querys.size();
                    for(XmlQQuerys xQQuerys: xmlQQuerys){
                        for(XmlQQuery xmlQQuery:xQQuerys.getXmlQQueries()){
                            if(xmlMQuery.getId().equals(xmlQQuery.getId())){
                                Query query = new Query();
                                if(xmlMQuery.getName() != null) {
                                    query.setName(xmlMQuery.getName());
                                } else {
                                    query.setName(xmlQQuery.getId());
                                }
                                parserStatement.setStatement(xmlQQuery.getStatement());
                                query.setStatement(parserStatement.parser());
                                Map<String,String> values = new HashMap<String, String>();
                                for(XmlQResult xmlQResult:xmlQQuery.getXmlQResult()) {
                                    query.setColumnMetricName(xmlQResult.getColumnMetricName());
                                    for(XmlQColumn xmlQColumn:xmlQResult.getXmlQColumns()){
                                        values.put(xmlQColumn.getName(), xmlQColumn.getMetricName());
                                    }
                                    query.setValues(values);
                                }
                                List<Parameter> parameters = new LinkedList<Parameter>();
                                for(XmlMParameter xmlMParameter:xmlMQuery.getXmlMParameters().getXmlMParameter()) {
                                    Parameter parameter = new Parameter();
                                    parameter.setType(xmlMParameter.getType());
                                    parameter.setArgument(xmlMParameter.getArgument());
                                    parameter.setValue(xmlMParameter.getParameter());
                                    parameters.add(parameter);
                                }
                                query.setParameters(parameters);
                                querys.add(query);
                            }
                        }
                    }
                    if(sizeStatus == querys.size()) {
                        //error
                    } else if(sizeStatus > 1){
                        //error
                    } else {

                    }
                    metric.setQuery(querys);
                }
                metrics.add(metric);
            }
        }


        return metrics;
    }

//    public List<XmlConf> getXmlConf() throws OracleFeederException {
//
//        XmlMMetrics xmlMMetrics = this.getMetrics();
//
//        List<XmlConf> xmlConfs = new LinkedList<XmlConf>();
//        for(XmlMMetric xmlMMetric : xmlMMetrics.getXmlMMetrics()){
//            if(xmlMMetric.getXmlMQueryFile() == null || xmlMMetric.getXmlMQueryFile().getFile() == null){
//                XmlQQuerys qs = this.getQuerys(Constants.QUERY_DEFAU_XML);
//                for(XmlQQuery xmlQQuery : qs.getXmlQQueries()){
//                    String queryName = xmlQQuery.getName();
//
//                    for(XmlMIterateGroup xmlMIterateGroup : xmlMMetric.getXmlMIterateGroups().getXmlMIterateGroups()){
//                        XmlConf xmlConf = new XmlConf();
//                        xmlConf.setStatement(xmlMIterateGroup.getXmlMStatement().getStatement());
//                        xmlConf.setColumns(xmlMIterateGroup.getResult().getMetricValuersFrom().getColumns());
//                        if(xmlMIterateGroup.getXmlMMapPrefixSuffix() != null && xmlMIterateGroup.getXmlMMapPrefixSuffix().getFile() != null){
//                            //TODO
//                        } else {
//                            xmlConf.setPrefix(xmlMIterateGroup.getPrefix());
//                        }
//                        for(XmlMQuery xmlMQuery : xmlMIterateGroup.getXmlMQuerys().getXmlMQueries()){
//                            if(queryName.equals(xmlMQuery.getName())){
//                                Map<XmlQQuery, List<String>> queryMap = new HashMap<XmlQQuery, List<String>>();
//                                queryMap.put(xmlQQuery, xmlMQuery.getXmlMParameters().getXmlMParameter());
//                                xmlConf.getQuerys().put(queryName, queryMap);
//                                xmlConfs.add(xmlConf);
//                            }
//                        }
//
//                    }
//                }
//            } else {
//                for (String querysFile : xmlMMetric.getXmlMQueryFile().getFile()) {
//                    //querys.add(this.getQuerys("resources/" + querysFile));
//                }
//            }
//        }
//
//        return xmlConfs;
//    }

    private static XmlQQuerys getQuerys(String file) throws OracleFeederException {
        return unmarshal(new File(file), XmlQQuerys.class);
    }

    private static XmlMMetrics getMetrics() throws OracleFeederException {
        return unmarshal(new File(Constants.METRICS_DEFAULT_XML), XmlMMetrics.class);
    }

    private static<E> E  unmarshal(File file, Class<E> type) throws OracleFeederException {
        JAXBContext jaxbContext;
        Unmarshaller jaxbUnmarshaller;
        Object object;
        try {
            jaxbContext = JAXBContext.newInstance(type);
            jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            object =  jaxbUnmarshaller.unmarshal(file);
        } catch (JAXBException e) {
            throw new OracleFeederException("ReadQueryXml Exception:", e);
        }
        if(object == null){
            throw new OracleFeederException("ReadQueryXml Exception: querys is null");
        } else {
            return type.cast(object);
        }
    }
}