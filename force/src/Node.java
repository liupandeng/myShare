public class Node {

	private int id;
	private double x;
	private double y;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    @Override


    public String toString() {
        return "nodes{" +
                "id='" + id + '\'' +
                '}';
    }
}
