package convexHulls;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Label;
import java.awt.geom.AffineTransform;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFrame;

public class BruteForce extends JFrame{
	
	public BruteForce(){
		this.setTitle("Convex Hull: Brute Force Approach");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(800,800);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.setResizable(false);
		generatePoints(getGraphics());
	}
	ArrayList<Point> points = new ArrayList<>();
	static ArrayList<Line> convexHullLines = new ArrayList<>();
	static ArrayList<Point> convexHull = new ArrayList<>();
	public void BruteForceRun(ArrayList<Point> points,Graphics g) {
		for(int i = 0; i < points.size() - 1; i++){
			for(int j = i+1; j < points.size(); j++){
				boolean isConvexHullEdge = true;
				int initialOrientation  = 0;
				Point p1 = points.get(i);
				Point p2 = points.get(j);
				int a = p2.y - p1.y;
				int b = p1.x - p2.x;
				int c = p1.x*p2.y - p1.y*p2.x;
				for(int k = 0; k < points.size(); k++){
					Point p3 = points.get(k);
					drawLine(g,p1,p2,Color.red);
					drawLine(g,p1,p3,Color.red);
					drawLine(g,p2,p3,Color.red);
					int orientationResult = a*p3.x + b*p3.y - c;
					if(initialOrientation  != 0){
						if(orientationResult > 0 && initialOrientation  < 0){
							isConvexHullEdge = false;
							break;
						} else if (orientationResult < 0 && initialOrientation  > 0){
							isConvexHullEdge = false;
							break;
						}
						
					} else { 
						if(orientationResult > 0){
							initialOrientation  = 1;
						} else if (orientationResult < 0){
							initialOrientation  = -1;
						} else {

						}
					}
				}
				if(isConvexHullEdge){
	                    convexHull.add(p1);
	                    convexHull.add(p2);
		                convexHullLines.add(new Line(p1, p2));
				}
			}
		}		
	}

		public void paint(Graphics g) {
		}
	    private void drawPoint(Graphics g, Point point, Color color) {
	        Graphics2D g2d = (Graphics2D) g;
	        g2d.setColor(color);
	        g2d.fillOval(point.x - 5, point.y - 5, 10, 10);
	    }

	    private void drawLine(Graphics g, Point p1, Point p2, Color color) {
	        try {
	            Thread.sleep(10); 
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }

	        g.setColor(color);
	        g.drawLine(p1.x, p1.y, p2.x, p2.y);
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

	    private void drawConvexHull(Graphics g) {
			for (int i = 0; i < convexHullLines.size(); i++){
				Point p1 = convexHullLines.get(i).p1;
				Point p2 = convexHullLines.get(i).p2;
				drawLine(g,p1,p2,Color.black);
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

		void generatePoints(Graphics g) {
	        Graphics2D g2d = (Graphics2D) g;

	        
	        AffineTransform transform = new AffineTransform();
	        transform.scale(1, -1); 
	        transform.translate(0, -getHeight()); 

	        g2d.setTransform(transform);

			drawGraph(g);

	        points = pointInput();
	        for (int i = 0; i < points.size(); i++) {
	            int x = points.get(i).x;
	            int y = points.get(i).y;
	            drawPoint(g, new Point(x, y), Color.blue);
	        }
	        BruteForceRun(points,g);
	        drawConvexHull(g);
	        System.out.println("Convex Hull: ");
	        for (int i = 0; i < convexHull.size(); i++) {
				System.out.println(convexHull.get(i).x + " "+convexHull.get(i).y);

			}
		}
		public static void main(String[] args) {
			BruteForce F = new BruteForce();
		}

}

