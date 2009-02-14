package clover;

// we return this class as an order
public class Dest {
	public double destX, destY; // where we're going
	public int orderType = 1; // always a move

	public Dest(double destX, double destY) {
		this.destX = destX;
		this.destY = destY;
	}
}
