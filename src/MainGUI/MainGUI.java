package MainGUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import LineIntersection.LineIntersectOrientation;
import LineIntersection.LineIntersectionCrossProduct;
import LineIntersection.LineIntersectionLinearAlgebra;
import convexHulls.BruteForce;
import convexHulls.GrahamScan;
import convexHulls.JarvisMarch;
import convexHulls.MonotoneChain;
import convexHulls.QuickHull;

public class MainGUI extends JFrame {
    MainGUI() {
        JPanel body = new JPanel(); 
        body.setSize(800,800);
        body.setBackground(Color.black);

        this.setTitle("Convex Hull: Jarvis March Approach");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800, 800);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.add(body);
        this.setVisible(true);

        JLabel ConvexHulls = new JLabel("Convex Hulls");
        JLabel LineIntersection = new JLabel("Line Intersection");
        JLabel PointInput = new JLabel("Point Input");
        
        Font customFont = new Font("Tw Cen MT", Font.BOLD, 35);
        Color customColor = Color.white;

        ConvexHulls.setFont(customFont);
        ConvexHulls.setForeground(customColor);
        ConvexHulls.setHorizontalAlignment(JLabel.CENTER);
        

        LineIntersection.setFont(customFont);
        LineIntersection.setForeground(customColor);
        LineIntersection.setHorizontalAlignment(JLabel.CENTER);

        PointInput.setFont(customFont);
        PointInput.setForeground(customColor);
        PointInput.setHorizontalAlignment(JLabel.CENTER);
        
        int labelWidth = 300;
        int labelHeight = 100;
        ConvexHulls.setBounds(225, 25, labelWidth, labelHeight);
        LineIntersection.setBounds(225, 250, labelWidth, labelHeight);
        PointInput.setBounds(225, 475, labelWidth, labelHeight);
        
        JButton JarvisMarch = new JButton("Jarvis March");
        JButton BruteForce = new JButton("Brute Force");
        JButton GrahamScan = new JButton("Graham Scan");
        JButton QuickHull = new JButton("Quick Hull");
        JButton MonotoneChain = new JButton("Monotone Chain");
        JButton LineOrientation = new JButton("Line Intersection Orientation");
        JButton LineLA = new JButton("Line Intersection Linear Algebra");
        JButton LineDeterminant = new JButton("Line Intersection Cross Product");
        JButton ConvexHullPoints = new JButton("Convex Hull Points");
        JButton LineIntersectionPoints = new JButton("Line Intersection Points");

        JarvisMarch.setBounds(25, 150, 125, 50);
        BruteForce.setBounds(175, 150, 125, 50);
        GrahamScan.setBounds(325, 150, 125, 50);
        QuickHull.setBounds(475, 150, 125, 50);
        MonotoneChain.setBounds(625, 150, 125, 50);
        LineOrientation.setBounds(25, 375, 200, 50);
        LineLA.setBounds(275, 375, 200, 50);
        LineDeterminant.setBounds(525, 375, 225, 50);
        ConvexHullPoints.setBounds(150, 600, 225, 50);
        LineIntersectionPoints.setBounds(400, 600, 225, 50);
        JarvisMarch.addActionListener(createActionListener("Jarvis March"));
        BruteForce.addActionListener(createActionListener("Brute Force"));
        GrahamScan.addActionListener(createActionListener("Graham Scan"));
        QuickHull.addActionListener(createActionListener("Quick Hull"));
        MonotoneChain.addActionListener(createActionListener("Monotone Chain"));
        LineOrientation.addActionListener(createActionListener("Line Intersection Orientation"));
        LineLA.addActionListener(createActionListener("Line Intersection Linear Algebra"));
        LineDeterminant.addActionListener(createActionListener("Line Intersection Determinant"));
        ConvexHullPoints.addActionListener(createActionListener("Convex Hull Points"));
        LineIntersectionPoints.addActionListener(createActionListener("Line Intersection Points"));
        
        body.add(ConvexHulls);
        body.add(LineIntersection);
        body.add(PointInput);
        body.add(JarvisMarch);
        body.add(BruteForce);
        body.add(GrahamScan);
        body.add(QuickHull);
        body.add(MonotoneChain);
        body.add(LineOrientation);
        body.add(LineLA);
        body.add(LineDeterminant);
        body.add(ConvexHullPoints);
        body.add(LineIntersectionPoints);
        
    }

    private ActionListener createActionListener(String buttonName) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (buttonName.equals("Jarvis March")) {
                	setVisible(false);
                	JarvisMarch algorithm =new JarvisMarch();
                	algorithm.setVisible(true);
                    System.out.println("Performing Jarvis March algorithm...");
                    
                } else if (buttonName.equals("Brute Force")) {
                	setVisible(false);
                	BruteForce algorithm =new BruteForce();
                	algorithm.setVisible(true);
                    System.out.println("Performing Brute Force algorithm...");
                    
                } else if (buttonName.equals("Graham Scan")) {
                	setVisible(false);
                	GrahamScan algorithm =new GrahamScan();
                	algorithm.setVisible(true);

                    System.out.println("Performing Graham Scan algorithm...");
                    
                } else if (buttonName.equals("Quick Hull")) {
                	setVisible(false);
                	QuickHull algorithm =new QuickHull();
                	algorithm.setVisible(true);

                    System.out.println("Performing Quick Hull algorithm...");
                    
                } else if (buttonName.equals("Monotone Chain")) {
                	setVisible(false);
                	MonotoneChain algorithm =new MonotoneChain();
                	algorithm.setVisible(true);

                    System.out.println("Performing Monotone Chain algorithm...");
                    
                } else if (buttonName.equals("Line Intersection Orientation")) {
                	setVisible(false);
                	LineIntersectOrientation algorithm =new LineIntersectOrientation();
                	algorithm.setVisible(true);

                    System.out.println("Performing Line Intersection Orientation algorithm...");
                    
                } else if (buttonName.equals("Line Intersection Linear Algebra")) {
                	setVisible(false);
                	LineIntersectionLinearAlgebra algorithm =new LineIntersectionLinearAlgebra();
                	algorithm.setVisible(true);

                    System.out.println("Performing Line Intersection Slope algorithm...");
                    
                } else if (buttonName.equals("Line Intersection Determinant")) {
                	setVisible(false);
                	LineIntersectionCrossProduct algorithm =new LineIntersectionCrossProduct();
                	algorithm.setVisible(true);

                    System.out.println("Performing Line Intersection Determinant algorithm...");
                    
                } else if (buttonName.equals("Convex Hull Points")) {
                	openTextFile("pointFile", "D:\\\\FAST\\\\Fifth Sem\\\\AL\\\\Algo Project\\\\ConvexHulls\\\\src\\\\convexHulls\\\\");
                    System.out.println("Performing Convex Hull Points algorithm...");
                } else if (buttonName.equals("Line Intersection Points")) {
                	openTextFile("pointFile", "D:\\\\FAST\\\\Fifth Sem\\\\AL\\\\Algo Project\\\\ConvexHulls\\\\src\\\\LineIntersection\\\\");
                    System.out.println("Performing Line Intersection Points algorithm...");
                }
            }
        };
    }
    private void openTextFile(String fileName, String customDirectory) {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder("notepad.exe", fileName);
            
            File directory = new File(customDirectory);
            processBuilder.directory(directory);
            
            processBuilder.start();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        MainGUI mainGUI = new MainGUI();
    }
}
