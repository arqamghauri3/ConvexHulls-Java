package convexHulls;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import javax.swing.JFrame;

class Points implements Comparable<Points> {
    long x, y;

    public Points(long x, long y) {
        this.x = x;
        this.y = y;
    }

    public int compareTo(Points p) {
        return Long.compare(x, p.x) != 0
                ? Long.compare(x, p.x)
                : Long.compare(y, p.y);
    }
}

public class MonotoneChain extends JFrame{
	
	public MonotoneChain() {
		this.setTitle("Convex Hull: Monotone Approach");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(800,800);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.setResizable(false);
		generatePoints(getGraphics());
	}
    static long crossProduct(Points O, Points A, Points B) {
        return (A.x - O.x) * (B.y - O.y)
                - (A.y - O.y) * (B.x - O.x);
    }

     ArrayList<Points> convexHull(ArrayList<Points> A, Graphics g) {
        int n = A.size(), k = 0;

        if (n <= 3)
            return A;

        ArrayList<Points> ans = new ArrayList<>(2 * n);

        Collections.sort(A);

        for (int i = 0; i < n; ++i) {
            while (k >= 2&& crossProduct(ans.get(k - 2),ans.get(k - 1), A.get(i))<= 0) {
                drawLine(g,ans.get(k - 2),ans.get(k - 1),Color.red);
                ans.remove(--k);
            }
            ans.add(A.get(i));
            k++;
        }

        for (int i = n - 2, t = k; i >= 0; --i) {
            while (k > t&& crossProduct(ans.get(k - 2),ans.get(k - 1), A.get(i))<= 0) {
                drawLine(g,ans.get(k - 2),ans.get(k - 1),Color.red);
                ans.remove(--k);
            }
            ans.add(A.get(i));
            k++;
        }

        ans.remove(ans.size() - 1);

        return ans;
    }
    public void paint(Graphics g){
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
	public ArrayList<Points> pointInput() {
		ArrayList<Points> points = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("D:\\FAST\\Fifth Sem\\AL\\Algo Project\\ConvexHulls\\src\\convexHulls\\pointFile.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] coordinates = line.split("\\s+"); 
                if (coordinates.length == 2) {
                    int x = Integer.parseInt(coordinates[0]);
                    int y = Integer.parseInt(coordinates[1]);
                    points.add(new Points(x, y));
                } else {
                    System.err.println("Invalid line: " + line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
		return points;
		}

    public void generatePoints(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        
        AffineTransform transform = new AffineTransform();
        transform.scale(1, -1); 
        transform.translate(0, -getHeight()); 

        g2d.setTransform(transform);

    	drawGraph(g);
    	ArrayList<Points> points = pointInput();
        for (int i = 0; i < points.size(); i++) {
            int x = (int) points.get(i).x;
            int y = (int) points.get(i).y;
            drawPoint(g, new Point(x, y), Color.blue);
        }
        List<Points> hull = convexHull(points,g);
        drawConvexHull(g,hull);
        
        System.out.println("Convex Hull: ");
        for (int i = 0; i < hull.size(); i++) {
			System.out.println(hull.get(i).x + " "+hull.get(i).y);

		}

    }
    	private void drawConvexHull(Graphics g, List<Points> list) {
            for (int i = 0; i < list.size(); i++) {
                Points currentPoint = list.get(i);
                Points nextPoint = list.get((i + 1) % list.size());
                drawLine(g, currentPoint, nextPoint, Color.black);
            }
        }

        private void drawLine(Graphics g, Points p1, Points p2, Color color) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Graphics2D g2d = (Graphics2D) g;
            g2d.setColor(color);
            g2d.setStroke(new BasicStroke(1f));
            g2d.drawLine((int)p1.x, (int)p1.y, (int)p2.x, (int)p2.y);
        }
        private void drawPoint(Graphics g, Point point, Color color) {
            g.setColor(color);
            g.fillOval(point.x - 4, point.y - 4, 10, 10);
        }

    public static void main(String[] args) {
    	MonotoneChain monotoneChain = new MonotoneChain();
    	
    }
}
