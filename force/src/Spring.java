import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Spring {
    public List<Node> springLayout(List<Node> nodes,List<Link> links) {
        //2计算每次迭代局部区域内两两节点间的斥力所产生的单位位移（一般为正值）
        int area = 1024 * 768;
        // k为每个点所占面积开根号
        double k = Math.sqrt(area / (double)nodes.size());
        double  diffx, diffy, diff;
        Map<Integer,Double> dispx = new HashMap<Integer,Double>();//点和横坐标的更新量

        Map<Integer,Double> dispy = new HashMap<Integer,Double>();//点和纵坐标的更新量

            
        int ejectfactor = 6;
//遍历每个点 计算其他点和当前点的距离从而判断当前点的坐标值更新量，两点越近，当前点坐标值更新量越小
//250以外的点视为对当前点没有斥力
        for (int v = 0; v < nodes.size(); v++) {//遍历针对每一个点（当前点）
//            不懂  为什么要设为0
            dispx.put(nodes.get(v).getId(), 0.0);//获取与指定下标的项的ID 并设横坐标更新量为0
            dispy.put(nodes.get(v).getId(), 0.0);//获取与指定下标的项的ID 并设纵坐标更新量为0
            for (int u = 0; u < nodes.size();  u++) {//遍历每一个点
                if (u != v) {
                    diffx = nodes.get(v).getX() - nodes.get(u).getX();//两点横坐标之差
                    diffy = nodes.get(v).getY() - nodes.get(u).getY();//两点纵坐标之差

                    diff = Math.sqrt(diffx * diffx + diffy * diffy);//两点之间的距离
                 
                    if (diff < 30)                  //如果两点距离之差为30则设距离影响因素为5
                        ejectfactor = 5;//不同距离影响因素

                    if (diff > 0 && diff < 250) {
                        int id = nodes.get(v).getId(); //获取当前点的id
// 不懂：1.两点越近 更新量越大才对
//
//当前点的横坐标更新量为：当前点的横坐标的更新量+横坐标之差/两点距离平方*每个点所占面积*影响因素（30以内为5,30-250为6）
//两点之间越近坐标值更新量越小
                        dispx.put(id,dispx.get(id) + diffx / diff * k * k / diff * ejectfactor);
                        //类似的更新纵坐标
                        dispy.put(id,dispy.get(id) + diffy / diff * k * k / diff* ejectfactor);
                    }
                }
            }
        }
        //3. 计算每次迭代每条边的引力对两端节点所产生的单位位移（一般为负值）
        int condensefactor = 3;
        Node visnodeS = null, visnodeE = null;
        
        for (int e = 0; e < links.size(); e++) {//遍历每条边
            int eStartID = links.get(e).getSource();//获取与指定下标的边的源点的id
            int eEndID = links.get(e).getTarget();//获取与指定下标的边的目标点的id
            visnodeS = getNodeById(nodes,eStartID);//获取源点对象
            visnodeE = getNodeById(nodes,eEndID);//获取目标点对象

            diffx = visnodeS.getX() - visnodeE.getX(); //边的两端点的横坐标之差
            diffy = visnodeS.getY() - visnodeE.getY();//边的两端点的纵坐标之差
            diff = Math.sqrt(diffx * diffx + diffy * diffy);//边的两端点的距离

//更新边两端点的坐标的更新量  ，两点受边的引力作用必定一个坐标更新量减少，一个增加
//源点的横坐标更新量为：当前值-边的两端点的横坐标之差*边的两端点的距离/每点所占面积开根号*3（边影响）
//目标点的横坐标更新量为：当前值+边的两端点的横坐标之差*边的两端点的距离/每点所占面积开根号*3（边影响）
            dispx.put(eStartID,dispx.get(eStartID) - diffx * diff / k * condensefactor);
            dispy.put(eStartID,dispy.get(eStartID) - diffy * diff / k* condensefactor);
            dispx.put(eEndID,dispx.get(eEndID) + diffx * diff / k * condensefactor);
            dispy.put(eEndID,dispy.get(eEndID) + diffy * diff / k * condensefactor);
        }
     
        //set x,y
        int maxt = 4 ,maxty = 3;
        for (int v = 0; v < nodes.size(); v++) {//遍历每个点
            Node node = nodes.get(v);           //根据角标获取点
            Double dx = dispx.get(node.getId());//获取点的横坐标更新量
            Double dy = dispy.get(node.getId());//获取点的纵坐标更新量
            int disppx = (int) Math.floor(dx);
            //最大（最接近正无穷大）浮点值，该值小于等于该参数，
            // 并等于某个整数。
            int disppy = (int) Math.floor(dy);
            if (disppx < -maxt)
                disppx = -maxt;
            if (disppx > maxt)
                disppx = maxt;
            if (disppy < -maxty)
                disppy = -maxty;
            if (disppy > maxty)
                disppy = maxty;
            
            node.setX((node.getX()+disppx));//更新点的坐标为：原有坐标值+更新值
            node.setY((node.getY()+disppy));
        }    
        return nodes;
    }
/*
   根据指定id获取对应节点
 */
    private Node getNodeById(List<Node> nodes,int id){
        for(Node node:nodes){
            if(node.getId()==(id)){
                return node;
            }
        }
        return null;
    }
}
