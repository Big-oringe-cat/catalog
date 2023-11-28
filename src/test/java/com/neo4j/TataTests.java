package com.neo4j;



import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;

import org.neo4j.driver.*;
import org.neo4j.driver.internal.value.PathValue;
import org.neo4j.driver.types.Path;
import org.neo4j.driver.types.Relationship;
import org.springframework.boot.test.context.SpringBootTest;
import springfox.documentation.spring.web.json.Json;

import java.util.*;

@SpringBootTest
class TataTests {

    public static void main(String[] args) {
        Driver driver = GraphDatabase.driver("bolt://47.99.64.26:9687", AuthTokens.basic("neo4j", "Zaq1Xsw2"));
        Session session = driver.session();
        String cql ="MATCH p=(t:FIELD{import_tid: \"13e97557e23fb6ec00f1ef3327ea3643\"})-[r:FIELD_RELATION*1..5]->() RETURN p\n" +
                "union\n" +
                "MATCH p=()-[r:FIELD_RELATION*1..5]->(t:FIELD{import_tid: \"13e97557e23fb6ec00f1ef3327ea3643\"}) RETURN p";
        String cql2="MATCH p=()-[r:TABLE_RELATION{id:\"56ff4527af40aa8bd612bdf635de3854\"}]->() RETURN p";
        Result result = session.run(cql);

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
        session.close();
        driver.close();
    }
}

