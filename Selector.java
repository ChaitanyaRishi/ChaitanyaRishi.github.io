 package code.model;

import java.awt.Point;
import java.util.HashSet;

public class Selector {

	private Point _selectedFirst;
	private Point _selectedSecond;
	private Board _board;
	
	public Selector(Board b) {
		_board = b;
		clearSelections();
	}
	
	
	public void select(Point p) {
		if (_selectedFirst == null) {
			_selectedFirst = p;
		}
		else {
			_selectedSecond = p;
			
			if (adjacent(_selectedFirst, _selectedSecond)) {
				HashSet<Point> points = _board.exchange(_selectedFirst, _selectedSecond);
				if (!(points.size()>0)) {
				     _board.exchange(_selectedFirst, _selectedSecond);
				} else {
				//	_board.exchange(_selectedFirst, _selectedSecond);
					do{
//						try {
//							Thread.sleep(500);
//						} catch (InterruptedException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
						_board.removeMatches(points);
						points = _board.match();
					} while(points.size()>0);
					
				}
			}
			clearSelections();
			
			//_board.possibleMoves();
		}
	}
	
	public Point selectedFirst() {
		return _selectedFirst;
	}

	private boolean adjacent(Point p, Point q) {
		return Math.abs(p.x-q.x) + Math.abs(p.y-q.y) == 1;
	}

	private void clearSelections() {
		_selectedFirst = null;
		_selectedSecond = null;
	}

}
