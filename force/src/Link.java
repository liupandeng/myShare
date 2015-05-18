/**
 * Created by Administrator on 2015/5/12.
 */
public class Link{
    private int source;
    private int target;

    public int getSource() {
        return source;
    }

    public void setSource(int source) {
        this.source = source;
    }

    public int getTarget() {
        return target;
    }

    public void setTarget(int target) {
        this.target = target;
    }

    @Override
    public String toString() {
        return "Link{" +
                "source='" + source + '\'' +
                ", target='" + target + '\'' +
                '}';
    }
}

