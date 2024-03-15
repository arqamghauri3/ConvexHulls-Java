package LineIntersection;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

class Pointss {
    double x, y;

    public Pointss(double x, double y) {
        this.x = x;
        this.y = y;
    }
}

public class LineSlopeIntersection extends JFrame{
	static Pointss p1,p2,q1,q2;
	int doIntersect = 0;
	LineSlopeIntersection(){
		this.setTitle("Line Intersection: Slope Test");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(800,800);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.setResizable(false);

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
        g.drawLine((int)p1.x,(int) p1.y, (int)p2.x,(int) p2.y);
        g.drawLine((int)q1.x, (int)q1.y, (int)q2.x,(int) q2.y);
        g.setColor(Color.blue);
        g.fillOval((int)p1.x - 5, (int)p1.y - 5, 10, 10);
        g.fillOval((int)p2.x - 5, (int)p2.y - 5, 10, 10);
        g.fillOval((int)q1.x - 5, (int)q1.y - 5, 10, 10);
        g.fillOval((int)q2.x - 5, (int)q2.y - 5, 10, 10);
        if(doIntersect == 1) {
        	JOptionPane.showMessageDialog(this, "Lines Intersect");

        }else {
        	JOptionPane.showMessageDialog(this, "Lines Donot Intersect");

        }
    }
	public static ArrayList<Pointss> pointInput() {
		ArrayList<Pointss> points = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("D:\\FAST\\Fifth Sem\\AL\\Algo Project\\ConvexHulls\\src\\LineIntersection\\pointFile.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] coordinates = line.split("\\s+"); 
                if (coordinates.length == 2) {
                    int x = Integer.parseInt(coordinates[0]);
                    int y = Integer.parseInt(coordinates[1]);
                    points.add(new Pointss(x, y));
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
    	ArrayList<Pointss> points = pointInput();
    	p1 = points.get(0);
    	p2 = points.get(1);
    	q1 = points.get(2);
    	q2 = points.get(3);
    	
    	if(slopeIntersection(p1,p2,q1,q2)) {
    		doIntersect = 1;
    	}
    }	
    	

	public static boolean slopeIntersection(Pointss p1,Pointss p2,Pointss q1,Pointss q2) {
		
		double slope1 = (p2.y - p1.y)/(p2.x-p1.x);
		double slope2 = (q2.y - q1.y)/(q2.x-q1.x);
		
		if(slope1 == slope2) {
			return false;
		}
		else {
	        double[] equationLine1 = findLineEquation(p1, p2);
	        double[] equationLine2 = findLineEquation(q1, q2);

	        Pointss intersection = solveEquations(equationLine1[0], -1, -equationLine1[1],
	                                           equationLine2[0], -1, -equationLine2[1]);

	        if (intersection != null) {
	            if(intersection.x>=q1.x && intersection.x<= q2.x) {
		            return true;
	            }
	            else {
	            	return false;
	            }
	        }
	        else {
	        	return false;
	        }
			
		}
	}

    private static double[] findLineEquation(Pointss p1, Pointss p2) {
        double[] equation = new double[2];
        equation[0] = (p2.y - p1.y) / (p2.x - p1.x);  
        equation[1] = p1.y - equation[0] * p1.x;      
        return equation;
    }

    private static Pointss solveEquations(double a1, double b1, double c1, double a2, double b2, double c2) {
        double determinant = a1 * b2 - a2 * b1;

        if (determinant == 0) {
            System.out.println("Lines are parallel. No unique solution.");
            return null;
        }

        double x = (c1 * b2 - c2 * b1) / determinant;
        double y = (a1 * c2 - a2 * c1) / determinant;

        return new Pointss(x, y);
    }

    public static void main(String[] args) {
    	LineSlopeIntersection L = new LineSlopeIntersection();
    }
}
