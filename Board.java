package code.model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Random;

public class Board {
	private ArrayList<ArrayList<String>> _board;
	private ArrayList<String> _colorFileNames;
	private Random _rand;
	
	private static int MAX_COLORS = 6; // max possible is 6

	public Board(int rows, int cols) {
		_board = new ArrayList<ArrayList<String>>();
		_rand = new Random();
		_colorFileNames = new ArrayList<String>();
		int matchSize = 0;
		HashSet<Point> points;
		//do {
		for (int i=0; i<MAX_COLORS; i=i+1) {
			_colorFileNames.add("Images/Tile-"+i+".png");
		}
		for (int r=0; r<rows; r=r+1) {
			ArrayList<String> row = new ArrayList<String>();
			for (int c=0; c<cols; c=c+1) {
				row.add(_colorFileNames.get(_rand.nextInt(_colorFileNames.size())));
			}
			_board.add(row);
		}
//		match();
		
		do{
		points = match();
		removeMatches(points);
		matchSize = points.size();
		//System.out.println(matchSize + " "+ possibleMoves());
		}while(match().size()>0 && possibleMoves()>0);
		
	}

	public int rows() { return _board.size(); }
	public int cols() { return _board.get(0).size(); }

	public String get(Point p) {
		return _board.get(p.x).get(p.y);
	}

	private String set(Point p, String s) {
		return _board.get(p.x).set(p.y, s);
	}

	public HashSet<Point> exchange(Point p, Point q) {
		String temp = get(p);
		set(p, get(q));
		set(q, temp);
		HashSet<Point> points = match();
		if (points.size() > 0) {
//			System.out.println("The board has a match.");
			return points;
		}
		else {
//			System.out.println("The board has no match.");
			return points;
		}
	}
	
	public HashSet<Point> match() {
		return match(3);
	}

	private HashSet<Point> match(int runLength) {
		HashSet<Point> matches = verticalMatch(runLength);
		matches.addAll(horizontalMatch(runLength));
		return matches;
	}

	private HashSet<Point> horizontalMatch(int runLength) {
		HashSet<Point> matches = new HashSet<Point>();
		int minCol = 0;
		int maxCol = cols() - runLength;
		int runLength2 = runLength;
		for (int r = 0; r < rows(); r = r + 1) {
			for (int c = minCol; c <= maxCol; c = c + 1) {  // The cols we can START checking in
				HashSet<String> values = new HashSet<String>();
				HashSet<Point> points = new HashSet<Point>();
				HashSet<String> newValues = new HashSet<String>();
				HashSet<Point> newPoints = new HashSet<Point>();
				
				runLength2 = runLength;
				for (int offset = 0; offset < runLength; offset = offset + 1) {
				    Point p = new Point(r,c+offset);
					points.add(p);
					String s = get(p);
					values.add(s);
				}
				
//				if(values.size()==1){
//					newValues.addAll(values);
//					newPoints.addAll(points);
//					while(newValues.size()==1){
//						runLength2++;
//						for (int offset = 0; offset < runLength2 && c+offset < cols(); offset = offset + 1) {
//						    Point p = new Point(r,c+offset);
//							newPoints.add(p);
//							String s = get(p);
//							newValues.add(s);
//						}
//					}
//				}
				
					if (values.size() == 1) { 
						matches.addAll(points);
//						for(int cc = cols()-1; cc >= 0; cc--){
//							LinkedHashSet<Point> newColumnPoints = new LinkedHashSet<Point>();
//							int base = 0;
//							for (int rr = rows()-1; rr >= 0; rr--){
//								Point p = new Point(rr,cc);
//								if(!points.contains(p)){
//									base++;
//									newColumnPoints.add(p);
//								}
//							}
//							Iterator<Point> i = newColumnPoints.iterator();
//							for (int ss = rows()-1; ss >= 0; ss--){
//								Point p = new Point(ss,cc);
//								if(ss >= (rows()-base)){
//									set(p, get(i.next()));
//								} else {
//									set(p, _colorFileNames.get(_rand.nextInt(_colorFileNames.size())));
//								}
//							}
//						}
				}
			}

		}
		return matches;
	}

	public void removeMatches(HashSet<Point> points){
		for(int cc = cols()-1; cc >= 0; cc--){
			LinkedHashSet<Point> newColumnPoints = new LinkedHashSet<Point>();
			int base = 0;
			for (int rr = rows()-1; rr >= 0; rr--){
				Point p = new Point(rr,cc);
				if(!points.contains(p)){
					base++;
					newColumnPoints.add(p);
				}
			}
			Iterator<Point> i = newColumnPoints.iterator();
			for (int ss = rows()-1; ss >= 0; ss--){
				Point p = new Point(ss,cc);
				if(ss >= (rows()-base)){
					set(p, get(i.next()));
				} else {
					set(p, _colorFileNames.get(_rand.nextInt(_colorFileNames.size())));
				}
			}
		}
	}

	private HashSet<Point> verticalMatch(int runLength) {
		System.out.println("");
		HashSet<Point> matches = new HashSet<Point>();
		int minRow = 0;
		int maxRow = rows() - runLength;
		for (int c = 0; c < cols(); c = c + 1) {
			for (int r = minRow; r <= maxRow; r = r + 1) {  // The rows we can START checking in
				HashSet<String> values = new HashSet<String>();
				HashSet<Point> points = new HashSet<Point>();
				for (int offset = 0; offset < runLength; offset = offset + 1) {
					Point p = new Point(r+offset,c);
					points.add(p);
					String s = get(p);
					values.add(s);
				}
				if (values.size() == 1) { 
					matches.addAll(points);
//					LinkedHashSet<Point> newColumnPoints = new LinkedHashSet<Point>();
//					int base = 0;
//					for (int rr = rows()-1; rr >= 0; rr--){
//						Point p = new Point(rr,c);
//						if(!points.contains(p)){
//							base++;
//							newColumnPoints.add(p);
//						}
//					}
//					Iterator<Point> i = newColumnPoints.iterator();
//					for (int ss = rows()-1; ss >= 0; ss--){
//						Point p = new Point(ss,c);
//						if(ss >= (rows()-base)){
//							set(p, get(i.next()));
//						} else {
//							set(p, _colorFileNames.get(_rand.nextInt(_colorFileNames.size())));
//						}
//					}
				}
			}
		}
		return matches;
	}
		public int possibleMoves(){
			boolean ret = false;
			int moveCount = 0;
			
			for(int i=1;i<rows()-1;i++){
				for(int j=1;j<cols()-1;j++){
					moveCount += exchange(new Point(i,j), new Point(i-1,j)).size() > 0 ? 1 : 0;
					exchange(new Point(i,j), new Point(i-1,j));
					moveCount += exchange(new Point(i,j), new Point(i+1,j)).size() > 0 ? 1 : 0;
					exchange(new Point(i,j), new Point(i+1,j));
					moveCount += exchange(new Point(i,j), new Point(i,j-1)).size() > 0 ? 1 : 0;
					exchange(new Point(i,j), new Point(i,j-1));
					moveCount += exchange(new Point(i,j), new Point(i,j+1)).size() > 0 ? 1 : 0;
					exchange(new Point(i,j), new Point(i,j+1));
				}
			}
//			System.out.println("Moves possible : " + (moveCount>0) + " "+ moveCount);
			return moveCount;
		}

}
