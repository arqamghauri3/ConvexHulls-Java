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

// GeometryUtils.java
 public class LineIntersectionCrossProduct extends JFrame{
	 static Point p1;
	 static Point p2;
	 static Point q1;
	 static Point q2;
	 static int doIntersect = 0;
	 public LineIntersectionCrossProduct(){
			this.setTitle("Line Intersection: Cross Product");
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			this.setSize(800,800);
			this.setLocationRelativeTo(null);
			this.setVisible(true);
			this.setResizable(false);
	 }
		    public void paint(Graphics g) {
		    	drawGrid(g);
		    	try {
					generatePoints();
				} catch (NumberFormatException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
		    	g.setColor(Color.black);
		        g.drawLine(p1.x, p1.y, p2.x, p2.y);
		        g.drawLine(q1.x, q1.y, q2.x, q2.y);
		        g.setColor(Color.blue);
		        g.fillOval(p1.x - 5, p1.y - 5, 10, 10);
		        g.fillOval(p2.x - 5, p2.y - 5, 10, 10);
		        g.fillOval(q1.x - 5, q1.y - 5, 10, 10);
		        g.fillOval(q2.x - 5, q2.y - 5, 10, 10);
		        if(doIntersect == 0) {
		        	JOptionPane.showMessageDialog(this, "Lines Intersect");

		        }else {
		        	JOptionPane.showMessageDialog(this, "Lines Donot Intersect");

		        }
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

	 
	    public static void main(String[] args) {
	    	LineIntersectionCrossProduct n = new LineIntersectionCrossProduct();
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

		public static void generatePoints() throws NumberFormatException, IOException {
		    	ArrayList<Point> points = pointInput();
		    	p1 = points.get(0);
		    	p2 = points.get(1);
		    	q1 = points.get(2);
		    	q2 = points.get(3);
		        if (LineIntersectionCrossProduct.doIntersect(p1, p2, q1, q2)) {
		            System.out.println("Line segments intersect.");
		        } else {
		            System.out.println("Line segments do not intersect.");
		            doIntersect = 1;
		        }
		}

	    private static int crossProduct(Point p1, Point p2, Point p3) {
	        return (p2.x - p1.x) * (p3.y - p1.y) - (p2.y - p1.y) * (p3.x - p1.x);
	    }

	    private static boolean doIntersect(Point p1, Point p2, Point p3, Point p4) {
	        
	        int cross1 = crossProduct(p1, p2, p3);
	        int cross2 = crossProduct(p1, p2, p4);
	        int cross3 = crossProduct(p3, p4, p1);
	        int cross4 = crossProduct(p3, p4, p2);

	        return ((cross1 > 0 && cross2 < 0) || (cross1 < 0 && cross2 > 0))
	                && ((cross3 > 0 && cross4 < 0) || (cross3 < 0 && cross4 > 0));
	    }
}

