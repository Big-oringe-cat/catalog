package com.catalog.config;

import org.neo4j.driver.*;
import org.neo4j.driver.internal.value.PathValue;
import org.neo4j.driver.types.Node;
import org.neo4j.driver.types.Relationship;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.*;
import java.util.stream.Collectors;

@Configuration
public class Neo4jConfig {
    int per_column_height = 20; //每个字段占用高度
    int per_char_weight = 5;  // 每个字符占用长度
    int maxHeight = 1000;  // 画布最大高度
    @Value("${neo.url}")
    private String url;
    @Value("${neo.username}")
    private String username;
    @Value("${neo.password}")
    private String password;


    @Bean
    public Session Neo4jConfig(){
        Driver driver = GraphDatabase.driver(url, AuthTokens.basic(username, password));
        Session session = driver.session();
        System.out.println("neo start");
        return session;
    }


    public Result search(String cql){
        Result result = Neo4jConfig().run(cql);
        return result;
    }


    public List<Map<String, Object>> neoOrigin(Result result) {
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
        return neoJsonList;
    }
    public Map<String, Object> neoNew(Result result){
        Map<String, Object> resultMap = new HashMap<>();

        List<Record> recordList = result.list();
        Map<String, Object> tableMap = new HashMap<String, Object>();
        Map<Long, Object> nodeMapDict = new HashMap<Long, Object>();
        List<Node> tmp_nodelist = new ArrayList<>();
        List<Relationship> tmp_relationlist = new ArrayList<>();
        for(Record recode: recordList){
            Map<String, Object> neoMap = new HashMap<>();
            neoMap.put("keys",recode.keys());
            neoMap.put("length",recode.keys().size());

            List<Map<String,Object>> fieldList=new ArrayList<>();
            Map<String, Integer> fieldLookupMap=new HashMap<String, Integer>();
            for (int i=0; i<recode.keys().size(); i++){
                String key=recode.keys().get(i);
                fieldLookupMap.put(key,i);
                PathValue p =(PathValue) recode.get(key);
                p.asPath().nodes().forEach((n)->{
                    tmp_nodelist.add(n);
                });
                p.asPath().relationships().forEach((r)->{
                    tmp_relationlist.add(r);
                });
            }
        }
        tmp_nodelist.forEach((K)->{
            // 预处理关系节点数据 nodeMapDict
            Map<String, Object> properties = K.asMap();
//                    System.out.println(properties);
            String table = (String) properties.get("table");
            String field = (String) properties.get("field");
            Map<String, Object> nodeMap = new HashMap<String, Object>();
            nodeMap.put("identity", new HashMap<String, Long>(){{
                put("low",K.id());
                put("high",0L);
            }});
            nodeMap.put("labels",K.labels());
            nodeMap.put("properties",properties);
            nodeMapDict.put(K.id(),nodeMap);
            // 预处理column结构
            Map<String, String> column  = new HashMap<String, String>(){{
                put("name",field);
            }};
            // tableMap若不存在该表名，则初始化数据，若存在，则插入column
            if (! tableMap.containsKey(table)){
                // 预处理表节点数据 tableMap
                Map<String, Object> node = new HashMap<String, Object>();
                node.put("id",table);
                node.put("name",table);
                node.put("in",0);
                node.put("out",0);
                HashSet<Map<String, String>> columns= new HashSet<Map<String, String>>();
                columns.add(column);
                node.put("columns", columns);
                tableMap.put(table,node);
            }else {
                // 相同表下的columns写入同一tableMap
                Map<String, Object> node = (Map<String, Object>) tableMap.get(table);
                HashSet<Map<String, String>> columns = (HashSet<Map<String, String>>) node.get("columns");
                columns.add(column);
            }
        });
        // edges
        List<Map<String, Object>> edges = createEdges(tmp_relationlist, tableMap, nodeMapDict);
        // nodes
        List<Map<String, Object>> nodes = topologicalSort(tableMap, edges);
        resultMap.put("edges",edges);
        resultMap.put("nodes",nodes);
        return resultMap;
    }

    // 生成关系edges
    public List<Map<String, Object>> createEdges(List<Relationship> relationlist,
                                           Map<String, Object> tableMap,
                                           Map<Long, Object> nodeMapDict){
        List<Map<String, Object>> edges = relationlist.stream().map((r)->{
            Map<String, Object> edge = new HashMap<>();
            // from,计算出度
            Map<String, Object> from = new HashMap<>();
            Map<String, Object> startNode = (Map<String, Object>) nodeMapDict.get(r.startNodeId());
            Map<String, Object> startNodeProperties = (Map<String, Object>) startNode.get("properties");
            String startTable=(String) startNodeProperties.get("table");
            if (tableMap.containsKey(startTable)){
                Map<String, Object> node = (Map<String, Object>) tableMap.get(startTable);
                node.put("out", (Integer) node.get("out") +1);
            }
            String startField=(String) startNodeProperties.get("field");
            from.put("column",startField);
            from.put("tbName",startTable);
            edge.put("from",from);
            // to,计算入度
            Map<String, Object> to = new HashMap<>();
            Map<String, Object> endNode = (Map<String, Object>) nodeMapDict.get(r.endNodeId());
            Map<String, Object> endNodeProperties = (Map<String, Object>) endNode.get("properties");
            String endTable=(String) endNodeProperties.get("table");

            if (tableMap.containsKey(endTable)){
                Map<String, Object> node = (Map<String, Object>) tableMap.get(endTable);
                node.put("in", (Integer) node.get("in") +1);
            }
            String endField=(String) endNodeProperties.get("field");
            to.put("column",endField);
            to.put("tbName",endTable);
            edge.put("to",to);
            return edge;
        }).collect(Collectors.toList());
        return edges;
    }


    // 有向无环图拓扑排序算法,并计算坐标top,left
    public List<Map<String, Object>> topologicalSort(Map<String, Object> tableMap, List<Map<String, Object>> edges) {
        // 2.根据入度,出度判断节点类型
        Map<String, Integer> inDegree = new HashMap<>();
        Map<String, Integer> outDegree = new HashMap<>();
        tableMap.forEach((K,V)->{
            Map<String, Object> node=(Map<String, Object>) V;
            Integer in = (Integer) node.getOrDefault("in",0);
            Integer out = (Integer) node.getOrDefault("out",0);
            if (in == 0 && out > 0){
                node.put("type", "Origin");
            }else if (in > 0 && out == 0){
                node.put("type", "RS");
            }else if (in > 0 && out > 0){
                node.put("type", "Middle");
            }
            inDegree.put((String) node.get("name"), (Integer) node.get("in"));
            outDegree.put((String) node.get("name"), (Integer) node.get("out"));
        });
        // 3.找到入度为0的节点
        Queue<Map<String, Object>> queue = new LinkedList<>();
        for (Map.Entry<String, Integer> entry : inDegree.entrySet()) {
            if (entry.getValue() == 0) {
                queue.offer((Map<String, Object>) tableMap.get(entry.getKey()));
            }
        }
        // 4.拓扑排序
        List<Map<String, Object>> result = new ArrayList<>();
        while (!queue.isEmpty()) {
            Map<String, Object> node = queue.poll();
            result.add(node);
            for (Map<String, Object> edge : edges) {
                Map<String, Object> from = (Map<String, Object>) edge.get("from");
                String fromId = (String) from.get("tbName");
                if (fromId.equals(node.get("name"))) {
                    Map<String, Object> to = (Map<String, Object>) edge.get("to");
                    String toId = (String) to.get("tbName");
                    inDegree.put(toId, inDegree.get(toId) - 1);
                    if (inDegree.get(toId) == 0) {
                        queue.offer((Map<String, Object>) tableMap.get(toId));
                    }
                }
            }
        }
        // 5.计算坐标，左上角为起点（0，0）,从上到下从左到右排序，Origin与RS节点各自独占一排，Middle节点超过最大高度则另起一列
        int top = 0; // 起始Y轴
        int left = 0; // 起始X轴
        int maxLeft = 0; // 每列最大宽度
        int maxTop = 0;  // 每列最大高度
        String maxType = "Origin";
        for (Map<String, Object> node : result) {
            String nodeType =(String) node.get("type");
            String name =(String) node.get("name");
            HashSet<Map<String, String>> columns = (HashSet<Map<String, String>>) node.get("columns");
            int table_height=columns.size() * per_column_height + 100;
            int table_weight=name.length() * per_char_weight + 100;
            if (table_weight>maxLeft){
                maxLeft = table_weight;
            }
            // 节点类型改变则换列
            if (! nodeType.equals(maxType)){
                if (top > maxTop){
                    maxTop = top;
                }
                maxType = nodeType;
                top = 0;
                left += maxLeft;
            }
            // Middle节点超过画布最大高度换行
            else if (nodeType.equals("Middle") && top > maxHeight && top > maxTop){
                top = 0;
                left += maxLeft;
            }
            node.put("y", top);
            node.put("x", left);
            node.remove("in");
            node.remove("out");
            top += table_height;
        }
        return result;
    }
}
