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

import org.apache.commons.math3.linear.*;

class Points {
    double x;
    double y;

    public Points(double x, double y) {
        this.x = x;
        this.y = y;
    }
}

public class LineIntersectionLinearAlgebra extends JFrame{
	static Points p1,p2,q1,q2;
	public LineIntersectionLinearAlgebra(){
		this.setTitle("Line Intersection: Linear Algebra Test");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(800,800);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.setResizable(false);

	}
	
    public static Points intersectSegsSimple(Points p1, Points p2, Points q1, Points q2) {
        double x1 = p1.x, y1 = p1.y;
        double x2 = p2.x, y2 = p2.y;
        double x3 = q1.x, y3 = q1.y;
        double x4 = q2.x, y4 = q2.y;

        double m1 = (y2 - y1) / (x2 - x1);
        double m2 = (y4 - y3) / (x4 - x3);
        double b1 = y1 - m1 * x1;
        double b2 = y3 - m2 * x3;

        if (m1 == m2) {
            return null; 
        }

        double intersectX = (b2 - b1) / (m1 - m2);
        double intersectY = m1 * intersectX + b1;

        if (isBetween(intersectX, x1, x2) && isBetween(intersectY, y1, y2)
                && isBetween(intersectX, x3, x4) && isBetween(intersectY, y3, y4)) {
            return new Points(intersectX, intersectY);
        }

        return null;
    }
    private static boolean isBetween(double value, double start, double end) {
        return value >= Math.min(start, end) && value <= Math.max(start, end);
    }

	public static ArrayList<Points> pointInput() {
		ArrayList<Points> points = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("D:\\FAST\\Fifth Sem\\AL\\Algo Project\\ConvexHulls\\src\\LineIntersection\\pointFile.txt"))) {
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
    public void paint(Graphics g) {
    	drawLines(g);
    }
    
    public void drawLines(Graphics g) {
    	
    	try {
			generatePoints();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
    	drawGrid(g);
    	g.setColor(Color.black);
        g.drawLine((int)p1.x, (int)p1.y, (int)p2.x, (int)p2.y);
        g.drawLine((int)q1.x, (int)q1.y, (int)q2.x, (int)q2.y);
        g.setColor(Color.blue);
        g.fillOval((int)p1.x - 5, (int)p1.y - 5, 10, 10);
        g.fillOval((int)p2.x - 5, (int)p2.y - 5, 10, 10);
        g.fillOval((int)q1.x - 5, (int)q1.y - 5, 10, 10);
        g.fillOval((int)q2.x - 5, (int)q2.y - 5, 10, 10);

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

    public void generatePoints() {
    	ArrayList<Points> points = pointInput();
    	p1 = points.get(0);
    	p2 = points.get(1);
    	q1 = points.get(2);
    	q2 = points.get(3);
    	Points intersect = intersectSegsSimple(p1,p2,q1,q2);
        if(intersect != null) {
        	JOptionPane.showMessageDialog(this, "Lines Intersect");

        }else {
        	JOptionPane.showMessageDialog(this, "Lines Donot Intersect");

        }
    }
    public static void main(String[] args) {
    	LineIntersectionLinearAlgebra m = new LineIntersectionLinearAlgebra();
    }
}
