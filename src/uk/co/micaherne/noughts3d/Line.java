package uk.co.micaherne.noughts3d;

import java.util.ArrayList;
import java.util.List;

public class Line {
	
	public List<Square> squares = new ArrayList<Square>();

	@Override
	public String toString() {
		String squaresString = "";
		for(Square s : squares) {
			squaresString += s;
		}
		return "Line [squares=" + squaresString + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((squares == null) ? 0 : squares.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Line other = (Line) obj;
		if (squares == null) {
			if (other.squares != null)
				return false;
		} else if (!squares.equals(other.squares))
			return false;
		return true;
	}

	
	
}