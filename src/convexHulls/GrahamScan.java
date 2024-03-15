package convexHulls;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import javax.swing.JFrame;
import javax.swing.Timer; 

public class GrahamScan extends JFrame 
{ 
	public GrahamScan() {
		this.setTitle("Convex Hull: Graham Scan Approach");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(800,800);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.setResizable(false);
		generatePoints(getGraphics());
	}
	public ArrayList<Point> pointInput() {
		ArrayList<Point> points = new ArrayList<>();
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

    public void paint(Graphics g){

    }
    private void generatePoints(Graphics g) {
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
        List<Point> hull = getConvexHull(points,g);
        drawConvexHull(g,hull);
        System.out.println("Convex Hull: ");
        for (int i = 0; i < hull.size(); i++) {
			System.out.println(hull.get(i).x + " "+hull.get(i).y);

		}

		

	}
	private void drawConvexHull(Graphics g, List<Point> list) {
        for (int i = 0; i < list.size(); i++) {
            Point currentPoint = list.get(i);
            Point nextPoint = list.get((i + 1) % list.size());
            drawLine(g, currentPoint, nextPoint, Color.black);
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
            Thread.sleep(500);
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

    protected static boolean areAllCollinear(List<Point> points) {

        if(points.size() < 2) {
            return true;
        }

        final Point a = points.get(0);
        final Point b = points.get(1);

        for(int i = 2; i < points.size(); i++) {

            Point c = points.get(i);

            if(orientation(a, b, c) != 0) {
                return false;
            }
        }

        return true;
    }

    public List<Point> getConvexHull(List<Point> points, Graphics g) throws IllegalArgumentException {

        List<Point> sorted = new ArrayList<Point>(getSortedList(points));

        if(sorted.size() < 3) {
            throw new IllegalArgumentException("can only create a convex hull of 3 or more unique points");
        }

        if(areAllCollinear(sorted)) {
            throw new IllegalArgumentException("cannot create a convex hull from collinear points");
        }

        Stack<Point> stack = new Stack<Point>();
        stack.push(sorted.get(0));
        stack.push(sorted.get(1));
        drawLine(g,sorted.get(0),sorted.get(1),Color.red);

        for (int i = 2; i < sorted.size(); i++) {

            Point head = sorted.get(i);
            Point middle = stack.pop();


			while (stack.peek() != null && orientation(stack.peek(), middle, head) <= 0) { 
				middle = stack.pop(); 
				drawLine(g,middle,head,Color.green);

			}
			stack.push(middle);
			stack.push(sorted.get(i));
	        drawLine(g,middle,sorted.get(i),Color.red);

        }

		Point p = stack.pop();
		if (orientation(stack.peek(), p, sorted.get(0)) > 0) {
			stack.push(p); 

		}
        drawLine(g,p,sorted.get(0),Color.red);


        return new ArrayList<Point>(stack);
    }

    protected static Point getLP(List<Point> points) {

        Point lowest = points.get(0);

        for(int i = 1; i < points.size(); i++) {

            Point temp = points.get(i);

            if(temp.y < lowest.y || (temp.y == lowest.y && temp.x < lowest.x)) {
                lowest = temp;
            }
        }

        return lowest;
    }

    
     static List<Point> getSortedList(List<Point> points) {
        final Point lowest = getLP(points);

        Collections.sort(points, new Comparator<Point>() {
            @Override
            public int compare(Point a, Point b) {
                if (a == b || a.equals(b)) {
                    return 0;
                }

                double thetaA = Math.atan2((long) a.y - lowest.y, (long) a.x - lowest.x);
                double thetaB = Math.atan2((long) b.y - lowest.y, (long) b.x - lowest.x);

                if (thetaA < thetaB) {
                    return -1;
                } else if (thetaA > thetaB) {
                    return 1;
                } else {
                    double distanceA = Math.sqrt(((long) lowest.x - a.x) * ((long) lowest.x - a.x)
                            + ((long) lowest.y - a.y) * ((long) lowest.y - a.y));
                    double distanceB = Math.sqrt(((long) lowest.x - b.x) * ((long) lowest.x - b.x)
                            + ((long) lowest.y - b.y) * ((long) lowest.y - b.y));
                    	
                    return Double.compare(distanceA, distanceB);
                }
            }
        });

        return points;
    }

    protected static int orientation(Point a, Point b, Point c) {

        long crossProduct = (((long)b.x - a.x) * ((long)c.y - a.y)) -
                            (((long)b.y - a.y) * ((long)c.x - a.x));

        if(crossProduct > 0) {
            return 1;
        }
        else if(crossProduct < 0) {
            return -1;
        }
        else {
            return 0;
        }
    }

	public static void main(String[] args) 
	{ 
        GrahamScan g =new GrahamScan();
	} 
} 

