package convexHulls;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JarvisMarch extends JFrame {


    public JarvisMarch() {
		this.setTitle("Convex Hull: Jarvis March Approach");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(800,800);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.setResizable(false);
		generatePoints(getGraphics());
    }


    private boolean direction(Point a, Point b, Point c) {
        return ((b.x - a.x) * (c.y - a.y) - (b.y - a.y) * (c.x - a.x)) < 0;
    }

    private Point findLeftmostPoint(List<Point> points) {
        int leftmostX = Integer.MAX_VALUE;
        Point leftmostPoint = null;
        for (Point point : points) {
            if (point.x < leftmostX) {
                leftmostX = point.x;
                leftmostPoint = point;
            }
        }
        return leftmostPoint;
    }

    private void drawPoint(Graphics g, Point point, Color color) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(color);
        g2d.fillOval(point.x - 5, point.y - 5, 10, 10);
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

        g.setColor(color);
        g.drawLine(p1.x, p1.y, p2.x, p2.y);
    }

    private void drawConvexHull(Graphics g, List<Point> convexHull) {
        for (int i = 0; i < convexHull.size(); i++) {
            Point currentPoint = convexHull.get(i);
            Point nextPoint = convexHull.get((i + 1) % convexHull.size());
            drawLine(g, currentPoint, nextPoint, Color.black);
        }
        
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
        Point leftmostPoint = findLeftmostPoint(points);
        drawPoint(g, leftmostPoint, Color.black);

        List<Point> convexHull = new ArrayList<>();
        convexHull.add(leftmostPoint);

        while (true) {
            Point currentPoint = convexHull.get(convexHull.size() - 1);
            Point nextPoint = points.get((points.indexOf(currentPoint) + 1) % points.size());

            for (Point checkPoint : points) {
                drawLine(g, currentPoint, checkPoint, Color.RED);
                if (direction(currentPoint, nextPoint, checkPoint)) {
                    nextPoint = checkPoint;
                }
            }

            drawLine(g, currentPoint, nextPoint, Color.black);
            convexHull.add(nextPoint);

            if (nextPoint.equals(leftmostPoint)) {
                break;
            }
        }

        drawConvexHull(g, convexHull);
        System.out.println("Convex Hull: ");
        for (int i = 0; i < convexHull.size(); i++) {
			System.out.println(convexHull.get(i).x + " "+convexHull.get(i).y);

		}
    }

    @Override
    public void paint(Graphics g) {

    }

    public static void main(String[] args) {
    	JarvisMarch j = new JarvisMarch();
    }
}
