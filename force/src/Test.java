import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class Test {
    public static void main(String[] args) throws IOException {
        JDBCGetData jd = new JDBCGetData();
        Map<String,Object> map = jd.queryDate();
//       map 随机生成的json数据：
        JSONObject jo3 = JSONObject.fromObject(map);
        System.out.println("随机生成的json数据：");
        System.out.println(jo3.toString());
//        nodes集中有100个点:
        Set<Node> nodes= (Set)map.get("nodes");
        System.out.println("nodes集中有100个点：");
        JSONArray jo = JSONArray.fromObject(nodes);
        System.out.println(jo.toString());
//        edges集中有100条边：
        Set<Link> links= (Set)map.get("links");
        System.out.println("links集中有100条边：");
        JSONArray jo4 = JSONArray.fromObject(links);
        System.out.println(jo4.toString());

        Spring sp = new Spring();
        List<Node> lNodes = new ArrayList<Node>(nodes); //Set---List
        List<Link> lEdges = new ArrayList<Link>(links);
        //1.set Node(x,y) , Random 随机分布初始节点位置
        //canvas size 1024*768
        double start_x, start_y, initSize = 40.0;
        for (Node node:lNodes) {
            start_x = 0 + 1024 *0.5;
            start_y = 0 + 768 * 0.5;
            node.setX(start_x + initSize * (Math.random() -0.5));
            node.setY(start_y + initSize * (Math.random() - 0.5));
        }
        System.out.println(" 随机分布初始节点位置：");
        JSONArray jo7 = JSONArray.fromObject(nodes);
        System.out.println(jo7.toString());
//布局过后更新的点reSetNodes
        List<Node> reSetNodes = sp.springLayout(lNodes,lEdges);
        //4.反复2,3步 迭代300次
        for(int i=0; i<300; i++){
            reSetNodes = sp.springLayout(reSetNodes,lEdges);
        }
//        布局更新后的点集：
        System.out.println("布局更新后的点集：");
        JSONArray jo6 = JSONArray.fromObject(reSetNodes);
        System.out.println(jo6.toString());
//        最终布局json：
        System.out.println("最终布局json：");
//        for(Node node:reSetNodes){
//            System.out.println("id:"+node.getId()+"  x="+node.getX()+"   y="+node.getY());
//        }
        Map<String , Object> map1 = new HashMap<String, Object>();
        map1.put("nodes", reSetNodes);
        map1.put("links", links);

        JSONObject jo5 = JSONObject .fromObject(map1);
        System.out.println(jo5.toString());
//        JSONArray jo1 = JSONArray.fromObject(new HashSet<Node>(reSetNodes));
//        System.out.println("json的形式"+jo1.toString());
        FileWriter fw = new FileWriter("E:\\Users\\Administrator\\IdeaProjects\\forceDir\\force\\web\\ai.json");
        BufferedWriter bufw = new BufferedWriter(fw);
        bufw.write(jo5.toString());
        bufw.flush();
        bufw.close();


    }
}