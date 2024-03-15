package convexHulls;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class QuickHull extends JFrame{
    ArrayList<Point> as;
    public QuickHull() {
		this.setTitle("Convex Hull: QuickHull Approach");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(800,800);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.setResizable(false);
		generatePoints(getGraphics());
	}
	
	ArrayList<Point> points = new ArrayList<>();
	ArrayList<Point> convexHull = new ArrayList<>();

	public ArrayList<Point> QuickHulls(ArrayList<Point> points, Graphics g) {
		int n = points.size();
		if(n < 3)
			return (ArrayList)points.clone();
		
		int minPoint = -1;
		int maxPoint = -1;
        int minX = Integer.MAX_VALUE;
        int maxX = Integer.MIN_VALUE;
        for (int i = 0; i < points.size(); i++)
        {
            if (points.get(i).x < minX)
            {
                minX = points.get(i).x;
                minPoint = i;
            }
            if (points.get(i).x > maxX)
            {
                maxX = points.get(i).x;
                maxPoint = i;
            }
        }
        System.out.println(minX);
        System.out.println(maxX);
        Point A = points.get(minPoint);
        Point B = points.get(maxPoint);
        convexHull.add(A);
        convexHull.add(B);
        drawLine(g,A,B,Color.blue);
        points.remove(A);
        points.remove(B);
        ArrayList<Point> leftSet = new ArrayList<Point>();
        ArrayList<Point> rightSet = new ArrayList<Point>();
        for (int i = 0; i < points.size(); i++)
        {
            Point p = points.get(i);
            if (pointLocation(A, B, p) == -1)
                leftSet.add(p);
            else if (pointLocation(A, B, p) == 1)
                rightSet.add(p);
        }
        hullSet(A, B, rightSet, convexHull,g);
        hullSet(B, A, leftSet, convexHull,g);
 
        return convexHull;
		 
	}
	
    private void hullSet(Point A, Point B, ArrayList<Point> set, ArrayList<Point> hull, Graphics g) {
        int insertPosition = hull.indexOf(B);
        if (set.size() == 0)
            return;
        if (set.size() == 1)
        {
            Point p = set.get(0);
            set.remove(p);
            hull.add(insertPosition, p);
            return;
        }
        
        int dist = Integer.MIN_VALUE;
        int furthestPoint = -1;
        for (int i = 0; i < set.size(); i++)
        {
            Point p = set.get(i);
            int distance = distance(A, B, p);
            if (distance > dist)
            {
                dist = distance;
                furthestPoint = i;
            }
        }
        Point P = set.get(furthestPoint);
        drawLine(g,A,B,Color.blue);
        set.remove(furthestPoint);
        hull.add(insertPosition, P);
        
        ArrayList<Point> leftSetAP = new ArrayList<Point>();
        for (int i = 0; i < set.size(); i++)
        {
            Point M = set.get(i);
            if (pointLocation(A, P, M) == 1)
            {
                leftSetAP.add(M);
            }
        }
 
        ArrayList<Point> leftSetPB = new ArrayList<Point>();
        for (int i = 0; i < set.size(); i++)
        {
            Point M = set.get(i);
            if (pointLocation(P, B, M) == 1)
            {
                leftSetPB.add(M);
            }
        }
        hullSet(A, P, leftSetAP, hull,g);
        hullSet(P, B, leftSetPB, hull,g);
 
    }

		
	private int distance(Point A, Point B, Point P) {
        int ABx = B.x - A.x;
        int ABy = B.y - A.y;
        int num = ABx * (A.y - P.y) - ABy * (A.x - P.x);
        if (num < 0)
            num = -num;
        return num;

	}

	public int pointLocation(Point A, Point B, Point P)
    {
        int cp1 = (B.x - A.x) * (P.y - A.y) - (B.y - A.y) * (P.x - A.x);
        if (cp1 > 0)
            return 1;
        else if (cp1 == 0)
            return 0;
        else
            return -1;
    }
	
	public void paint(Graphics g) {
	}
	
	public ArrayList<Point> pointInput() {
		
        try (BufferedReader reader = new BufferedReader(new FileReader("D:\\FAST\\Fifth Sem\\AL\\Algo Project\\ConvexHulls\\src\\convexHulls\\pointFile.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] coordinates = line.split("\\s+"); 
                if (coordinates.length == 2) {
                    int x = Integer.parseInt(coordinates[0]);
                    int y = Integer.parseInt(coordinates[1]);
                    points.add(new Point(x, y));
                } else {
                    System.err.println("Invalid line: " + line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
		return points;
		
	}
	
	void generatePoints(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        
        AffineTransform transform = new AffineTransform();
        transform.scale(1, -1); 
        transform.translate(0, -getHeight()); 

        g2d.setTransform(transform);

		drawGraph(g);
        ArrayList<Point> points = pointInput();
        for (int i = 0; i < points.size(); i++) {
            int x = points.get(i).x;
            int y = points.get(i).y;
            drawPoint(g, new Point(x, y), Color.blue);
        }
        
        drawConvexHull(g,QuickHulls(points,g),Color.black);
        System.out.println("Convex Hull: ");
        for (int i = 0; i < convexHull.size(); i++) {
			System.out.println(convexHull.get(i).x + " "+convexHull.get(i).y);

		}
	}
	
    private void drawConvexHull(Graphics g, List<Point> convexHull, Color color) {
        for (int i = 0; i < convexHull.size(); i++) {
            Point currentPoint = convexHull.get(i);
            Point nextPoint = convexHull.get((i + 1) % convexHull.size());
            drawLine(g, currentPoint, nextPoint, color);
        }
    }
    private void drawGraph(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.LIGHT_GRAY);

        int tickLength = 5;
        int axisMargin = 10;

        g2d.drawLine(axisMargin, getHeight() / 2, getWidth() - axisMargin, getHeight() / 2);
        for (int x = axisMargin; x < getWidth() - axisMargin; x += 50) {
            g2d.drawLine(x, getHeight() / 2 - tickLength, x, getHeight() / 2 + tickLength);
            g2d.drawString(Integer.toString((x - getWidth() / 2) / 50), x - 5, getHeight() / 2 + 20);
        }

        g2d.drawLine(getWidth() / 2, axisMargin, getWidth() / 2, getHeight() - axisMargin);
        for (int y = axisMargin; y < getHeight() - axisMargin; y += 50) {
            g2d.drawLine(getWidth() / 2 - tickLength, y, getWidth() / 2 + tickLength, y);
            g2d.drawString(Integer.toString(-(y - getHeight() / 2) / 50), getWidth() / 2 - 20, y + 5);
        }
    }

    private void drawLine(Graphics g, Point p1, Point p2, Color color) {
        try {
            Thread.sleep(100); 
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(color);
        g2d.setStroke(new BasicStroke(1f));
        g2d.drawLine(p1.x, p1.y, p2.x, p2.y);
    }
    private void drawPoint(Graphics g, Point point, Color color) {
        g.setColor(color);
        g.fillOval(point.x - 4, point.y - 4, 10, 10);
    }
	public static void main(String[] args) {
		QuickHull q = new QuickHull();


	}

}
