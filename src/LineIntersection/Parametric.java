//package LineIntersection;
//
//import java.awt.Color;
//import java.awt.Graphics;
//import java.awt.Graphics2D;
//import java.io.BufferedReader;
//import java.io.FileReader;
//import java.io.IOException;
//
//import javax.swing.JFrame;
//import javax.swing.JOptionPane;
//
//class Points {
//    int x, y;
//
//    Points(int x, int y) {
//        this.x = x;
//        this.y = y;
//    }
//}
//
//public class Parametric extends JFrame{
//	
//	Parametric(){
//		this.setTitle("Line Intersection: Parametric Test");
//		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		this.setSize(800,800);
//		this.setLocationRelativeTo(null);
//		this.setVisible(true);
//		this.setResizable(false);
//
//	}
//
//	
//	static int doIntersect = 0;
//	static Points p1,p2,q1,q2;
//    static Points findIntersection(Points P1, Points P2, Points Q1, Points Q2) {
//        double x1 = P1.x, y1 = P1.y;
//        double x2 = P2.x, y2 = P2.y;
//        double x3 = Q1.x, y3 = Q1.y;
//        double x4 = Q2.x, y4 = Q2.y;
//
//        double tNum = (x1 - x3) * (y3 - y4) - (y1 - y3) * (x3 - x4);
//        double tDen = (x1 - x2) * (y3 - y4) - (y1 - y2) * (x3 - x4);
//        double t = tNum / tDen;
//
//        if (Math.abs(tDen) < 1e-9) {
//            return null; 
//        }
//
//        
//        int x = (int) (x1 + t * (x2 - x1));
//        int y = (int) (y1 + t * (y2 - y1));
//
//        
//        if (0 <= t && t <= 1) {
//            return new Points(x, y);
//        } else {
//            return null; 
//        }
//    }
//    private void drawGrid(Graphics g) {
//        Graphics2D g2d = (Graphics2D) g;
//        g2d.setColor(Color.LIGHT_GRAY);
//
//        int cellSize = 50;
//
//        for (int x = 0; x < getWidth(); x += cellSize) {
//            g.drawLine(x, 0, x, getHeight());
//        }
//        for (int y = 0; y < getHeight(); y += cellSize) {
//            g.drawLine(0, y, getWidth(), y);
//        }
//    }
//
//	public void paint(Graphics g) {
//		drawGrid(g);
//		try {
//			generatePoints();
//		} catch (NumberFormatException | IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//    	g.setColor(Color.black);
//        g.drawLine(p1.x, p1.y, p2.x, p2.y);
//        g.drawLine(q1.x, q1.y, q2.x, q2.y);
//        g.setColor(Color.blue);
//        g.fillOval(p1.x - 5, p1.y - 5, 10, 10);
//        g.fillOval(p2.x - 5, p2.y - 5, 10, 10);
//        g.fillOval(q1.x - 5, q1.y - 5, 10, 10);
//        g.fillOval(q2.x - 5, q2.y - 5, 10, 10);
//        if(doIntersect == 0) {
//        	JOptionPane.showMessageDialog(this, "Lines Intersect");
//
//        }else {
//        	JOptionPane.showMessageDialog(this, "Lines Donot Intersect");
//
//        }
//    }
//
//	public static void generatePoints() throws NumberFormatException, IOException {
//    	BufferedReader br = new BufferedReader(new FileReader("D:\\FAST\\Fifth Sem\\AL\\Algo Project\\ConvexHulls\\src\\LineIntersection\\pointFile.txt"));
//        int p1_x = Integer.parseInt(br.readLine());
//        int p1_y = Integer.parseInt(br.readLine());
//        int q1_x = Integer.parseInt(br.readLine());
//        int q1_y = Integer.parseInt(br.readLine());
//        int p2_x = Integer.parseInt(br.readLine());
//        int p2_y = Integer.parseInt(br.readLine());
//        int q2_x = Integer.parseInt(br.readLine());
//        int q2_y = Integer.parseInt(br.readLine());
//
//          p1 = new Points(p1_x, p1_y);
//          q1 = new Points(q1_x, q1_y);
//          p2 = new Points(p2_x, p2_y);
//          q2 = new Points(q2_x, q2_y);
//          Points intersection = findIntersection(p1, p2, q1, q2);
//
//	        if (intersection != null) {
//	            System.out.println("Line segments intersect.");
//	        } else {
//	            System.out.println("Line segments do not intersect.");
//	            doIntersect = 1;
//	        }
//	}
//
//    public static void main(String[] args) {
//    	Parametric m = new Parametric();
//    }
//}
