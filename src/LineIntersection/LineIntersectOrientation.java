package LineIntersection;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class LineIntersectOrientation extends JFrame
{ 
    static Point p1, p2, q1, q2;
    static int doIntersect = 0;
    public LineIntersectOrientation() {
		this.setTitle("Line Intersection: Orientation Test");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(800,800);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.setResizable(false);
    }

    static boolean onSegment(Point p, Point q, Point r) 
    { 
        if (r.x <= Math.max(p.x, q.x) && r.x >= Math.min(p.x, q.x) && 
            r.y <= Math.max(p.y, q.y) && r.y >= Math.min(p.y, q.y)) 
            return true; 

        return false; 
    } 

    static int orientation(Point p, Point q, Point r) 
    { 
        int val = (q.y - p.y) * (r.x - q.x) - (q.x - p.x) * (r.y - q.y); 

        if (val == 0) return 0; 

        return (val > 0) ? 1 : 2; 
    } 

    static boolean doIntersect(Point p1, Point q1, Point p2, Point q2) 
    { 
        int o1 = orientation(p1, q1, p2); 
        int o2 = orientation(p1, q1, q2); 
        int o3 = orientation(p2, q2, p1); 
        int o4 = orientation(p2, q2, q1); 

        if (o1 != o2 && o3 != o4) 
            return true; 

        if (o1 == 0 && onSegment(p1, q1, p2)) return true; 

        if (o2 == 0 && onSegment(p1, q1, q2)) return true; 

        if (o3 == 0 && onSegment(p2, q2, p1)) return true; 

        if (o4 == 0 && onSegment(p2, q2, q1)) return true; 

        return false; 
    } 
    private void drawGrid(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.LIGHT_GRAY);

        int cellSize = 50;

        for (int x = 0; x < getWidth(); x += cellSize) {
            g.drawLine(x, 0, x, getHeight());
        }
        for (int y = 0; y < getHeight(); y += cellSize) {
            g.drawLine(0, y, getWidth(), y);
        }
    }


    public void paint(Graphics g) {
    	try {
			generatePoints();
		} catch (NumberFormatException | IOException e) {
			
			e.printStackTrace();
		}
    	drawGrid(g);
    	g.setColor(Color.black);
        g.drawLine(p1.x, p1.y, p2.x, p2.y);
        g.drawLine(q1.x, q1.y, q2.x, q2.y);
        g.setColor(Color.blue);
        g.fillOval(p1.x - 5, p1.y - 5, 10, 10);
        g.fillOval(p2.x - 5, p2.y - 5, 10, 10);
        g.fillOval(q1.x - 5, q1.y - 5, 10, 10);
        g.fillOval(q2.x - 5, q2.y - 5, 10, 10);
        if(doIntersect == 1) {
        	JOptionPane.showMessageDialog(this, "Lines Intersect");

        }else {
        	JOptionPane.showMessageDialog(this, "Lines Donot");

        }
    }
	public static ArrayList<Point> pointInput() {
		ArrayList<Point> points = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("D:\\FAST\\Fifth Sem\\AL\\Algo Project\\ConvexHulls\\src\\LineIntersection\\pointFile.txt"))) {
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

    public void generatePoints() throws NumberFormatException, IOException {
    	ArrayList<Point> points = pointInput();
    	p1 = points.get(0);
    	p2 = points.get(1);
    	q1 = points.get(2);
    	q2 = points.get(3);

    	if(doIntersect(p1, p2,q1 , q2)) {
            System.out.println("Lines Intersect"); 
            doIntersect = 1;
        }
        else {
            System.out.println("Lines Dont Intersect"); 
            doIntersect = 0;
        }
	
    	
    	
    }
    public static void main(String[] args)  
    { 
        LineIntersectOrientation a = new LineIntersectOrientation();
    } 
}
