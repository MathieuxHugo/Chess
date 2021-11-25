package chess;

public class Coordonnee implements Comparable{
	public int x;
	public int y;
	public Coordonnee(int x, int y) {
		this.x=x;
		this.y=y;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	@Override
	public String toString() {
		return "x="+x+",y="+y;
	}
	@Override
	public int compareTo(Object o) {
		Coordonnee c=(Coordonnee)o;
		return this.x*Echiquier.taille+this.y-(c.getX()*Echiquier.taille+c.getY());
	}
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Coordonnee) {
			Coordonnee c=(Coordonnee)obj;
			return this.x==c.getX() && this.y==c.getY();
		}
		return false;
	}
	public int differenceX(Coordonnee c) {
		return Math.abs(c.getX()-this.getX());
	}
	public Coordonnee entreX(Coordonnee c) {
		return new Coordonnee((this.getX()+c.getX())/2,c.getY());
	}
	public Coordonnee droite() {
		return new Coordonnee(this.x+1,this.y);
	}
	public Coordonnee gauche() {
		return new Coordonnee(this.x-1,this.y);
	}
}
