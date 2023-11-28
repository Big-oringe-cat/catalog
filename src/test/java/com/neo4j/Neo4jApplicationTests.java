package com.neo4j;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import org.apache.commons.lang3.StringUtils;
import org.neo4j.driver.*;
import org.neo4j.driver.internal.value.PathValue;
import org.neo4j.driver.types.Relationship;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

@SpringBootTest
public class Neo4jApplicationTests {

    private static void neoOrigin(Result result) {
        List<Map<String, Object>> neoJsonList=new ArrayList<>();

        List<Record> recordList = result.list();
        for(Record recode: recordList){
            Map<String, Object> neoMap = new HashMap<>();
            neoMap.put("keys",recode.keys());
            neoMap.put("length",recode.keys().size());

            List<Map<String,Object>> fieldList=new ArrayList<>();
            Map<String, Integer> fieldLookupMap=new HashMap<String, Integer>();
            for (int i=0; i<recode.keys().size(); i++){
                String key=recode.keys().get(i);
                fieldLookupMap.put(key,i);
                Long start_id=0L;
                Long end_id=0L;
                Map<String, Object> fieldMap = new HashMap<String, Object>();
                PathValue p =(PathValue) recode.get(key);

                Map<Long, Object> nodeMapDict = new HashMap<Long, Object>();
                p.asPath().nodes().forEach((K)->{
                    HashMap<String, Object> nodeMap = new HashMap<String, Object>();
                    nodeMap.put("identity", new HashMap<String, Long>(){{
                        put("low",K.id());
                        put("high",0L);
                    }});
                    nodeMap.put("labels",K.labels());
                    nodeMap.put("properties",K.asMap());
                    nodeMapDict.put(K.id(),nodeMap);
                });
                List<Map<String,Object>> segments=new ArrayList();
                Iterator<Relationship> relationshipIterator = p.asPath().relationships().iterator();
                int count=0;
                while (relationshipIterator.hasNext()){
                    Relationship r = relationshipIterator.next();
                    if (count==0){
                        start_id=r.startNodeId();
                    }
                    count++;
                    end_id=r.endNodeId();
                    Map<String,Object> segmentsMap=new HashMap<String,Object>();
//                    System.out.println(nodeMapDict.get(r.startNodeId()));
//                    System.out.println(nodeMapDict.get(r.endNodeId()));
                    segmentsMap.put("start",nodeMapDict.get(r.startNodeId()));
                    segmentsMap.put("end",nodeMapDict.get(r.endNodeId()));
                    Map<String,Object> relationMap=new HashMap<String,Object>();
                    relationMap.put("identity", new HashMap<String, Long>(){{
                        put("low",r.id());
                        put("high",0L);
                    }});
                    relationMap.put("start", new HashMap<String, Long>(){{
                        put("low",r.startNodeId());
                        put("high",0L);
                    }});
                    relationMap.put("end", new HashMap<String, Long>(){{
                        put("low",r.endNodeId());
                        put("high",0L);
                    }});
                    relationMap.put("type",r.type());
                    relationMap.put("properties",r.asMap());
                    segmentsMap.put("relationship",relationMap);
                    segments.add(segmentsMap);
                }
                fieldMap.put("start",nodeMapDict.get(start_id));
                fieldMap.put("end",nodeMapDict.get(end_id));
                fieldMap.put("segments",segments);
                fieldMap.put("length",segments.size());
                fieldList.add(fieldMap);
            }
            neoMap.put("_fields",fieldList);
            neoMap.put("_fieldLookup",fieldLookupMap);
            neoJsonList.add(neoMap);
        }
        System.out.println(JSONArray.toJSON(neoJsonList));
    }
    private static void neoNew(Result result){
        Map<String, Object> resultMap = new HashMap<>();
        List<Map<String, Object>> edges = new ArrayList<>();
        List<Map<String, Object>> nodes = new ArrayList<>();
        List<Record> recordList = result.list();
        Map<String, Object> nodeMap = new HashMap<String, Object>();
        Map<String, Object> tableMap = new HashMap<String, Object>();
        for(Record recode: recordList){
            Map<String, Object> neoMap = new HashMap<>();
            neoMap.put("keys",recode.keys());
            neoMap.put("length",recode.keys().size());

            List<Map<String,Object>> fieldList=new ArrayList<>();
            Map<String, Integer> fieldLookupMap=new HashMap<String, Integer>();
            for (int i=0; i<recode.keys().size(); i++){
                String key=recode.keys().get(i);
                fieldLookupMap.put(key,i);
                Long start_id=0L;
                Long end_id=0L;
                Map<String, Object> fieldMap = new HashMap<String, Object>();
                PathValue p =(PathValue) recode.get(key);

                Map<Long, Object> nodeMapDict = new HashMap<Long, Object>();
                p.asPath().nodes().forEach((K)->{
                    Map<String, Object> properties = K.asMap();
//                    System.out.println(properties);
                    String table = (String) properties.get("table");
                    String field = (String) properties.get("field");
                    nodeMap.put("identity", new HashMap<String, Long>(){{
                        put("low",K.id());
                        put("high",0L);
                    }});
                    nodeMap.put("labels",K.labels());
                    nodeMap.put("properties",properties);
                    nodeMapDict.put(K.id(),nodeMap);
                    Map<String, String> column  = new HashMap<String, String>(){{
                        put("name",field);
                    }};
                    if (! tableMap.containsKey(table)){
                        Map<String, Object> node = new HashMap<String, Object>();
                        node.put("id",table);
                        node.put("name",table);
                        node.put("type", "Origin");
                        node.put("top",0);
                        node.put("left",0);
                        List<Map<String, String>> columns = new ArrayList<Map<String, String>>(){{
                            add(column);
                        }};
                        node.put("columns", columns);
                        tableMap.put(table,node);
                    }else {
//                        System.out.println(table);
                        Map<String, Object> node = (Map<String, Object>) tableMap.get(table);
//                        System.out.println(node);
                        List<Map<String, String>> columns = (List<Map<String, String>>) node.get("columns");
                        columns.add(column);
                    }
                });
                List<Map<String,Object>> segments=new ArrayList();
                Iterator<Relationship> relationshipIterator = p.asPath().relationships().iterator();
                int count=0;
                // edges
                while (relationshipIterator.hasNext()){
                    Relationship r = relationshipIterator.next();
                    Map<String, Object> edge = new HashMap<>();
                    Map<String, Object> from = new HashMap<>();
                    Map<String, Object> to = new HashMap<>();
                    // from
                    Map<String, Object> startNode = (Map<String, Object>) nodeMapDict.get(r.startNodeId());
                    Map<String, Object> startNodeProperties = (Map<String, Object>) startNode.get("properties");
                    String startTable=(String) startNodeProperties.get("table");
                    String startField=(String) startNodeProperties.get("field");
                    from.put("column",startField);
                    from.put("tbName",startTable);
                    edge.put("from",from);
                    // to
                    Map<String, Object> endNode = (Map<String, Object>) nodeMapDict.get(r.endNodeId());
                    Map<String, Object> endNodeProperties = (Map<String, Object>) endNode.get("properties");
                    String endTable=(String) endNodeProperties.get("table");
                    String endField=(String) endNodeProperties.get("field");
                    to.put("column",endField);
                    to.put("tbName",endTable);
                    edge.put("to",to);
                    // edge
                    edges.add(edge);
                }
            }
        }
        tableMap.forEach((K,V)->{
            nodes.add((Map<String, Object>) V);
        });
        resultMap.put("edges",edges);
        resultMap.put("nodes",nodes);
        System.out.println(JSON.toJSONString(resultMap));
    }
    public static void main (String[] args){

        // 输出10个随机数

        Driver driver = GraphDatabase.driver("bolt://47.99.64.26:9687", AuthTokens.basic("neo4j", "Zaq1Xsw2"));
        Session session = driver.session();
        String cql = "MATCH p=(t:FIELD{import_tid: \"13e97557e23fb6ec00f1ef3327ea3643\"})-[r:FIELD_RELATION*1..5]->() RETURN p\n" +
                "union\n" +
                "MATCH p=()-[r:FIELD_RELATION*1..5]->(t:FIELD{import_tid: \"13e97557e23fb6ec00f1ef3327ea3643\"}) RETURN p";
        Result result = session.run(cql);

//        neoOrigin(result);
        neoNew(result);
        session.close();
        driver.close();
    }
}
