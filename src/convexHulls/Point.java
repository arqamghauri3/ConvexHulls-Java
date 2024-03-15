package convexHulls;

public class Point 
{ 
    public int x;
	public int y; 
	Point() 
	{ 
		x = 0; 
		y = 0; 
	} 

    public Point(int x, int y){ 
        this.x=x; 
        this.y=y; 
    } 
    double distance(Point p) {
        return Math.sqrt(Math.pow((p.x - this.x), 2) + Math.pow((p.y - this.y), 2));
    }

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	public int compareTo(Point p)
	{
		return Long.compare(x, p.x) != 0
			? Long.compare(x, p.x)
			: Long.compare(y, p.y);
	}


} 