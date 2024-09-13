package main.chess;


public class Coup implements Cloneable{
	private Coordonnee depart;
	private Coordonnee arrivee;
	private Piece pDepart;
	private Piece prise;
	private boolean echec;
	protected Coup(Case c, Case cDest) {
		this.depart=c.getCoordonnee();
		this.arrivee=cDest.getCoordonnee();
		this.pDepart=c.getPiece().clone();
		this.prise=cDest.getPiece();
		if(this.prise!=null) {
			this.prise=this.prise.clone();
		}
	}
	
	protected Coup(Coordonnee depart, Coordonnee arrivee, Piece p, Piece prise) {
		this.depart=depart;
		this.arrivee=arrivee;
		this.pDepart=p.clone();
		if(prise!=null) {
			this.prise=prise.clone();
		}
		else {
			this.prise=null;
		}
	}
	

	public boolean isPrise() {
		return prise!=null;
	}
	public Coordonnee getDepart() {
		return depart;
	}
	public Coordonnee getArrivee() {
		return arrivee;
	}
	public Piece getpDepart() {
		return pDepart.clone();
	}
	public Piece getPrise() {
		if(prise!=null) {
			return prise.clone();
		}
		return prise;
	}
	public void setPrise(Piece prise) {
		this.prise = prise;
	}

	public boolean isEchec() {
		return echec;
	}

	public void setEchec(boolean echec) {
		this.echec = echec;
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}

	@Override
	public String toString() {
		String milieu=this.isPrise()?"x"+this.prise:"->";
		String fin=this.echec?"+":"";
		return this.pDepart.toString()+this.depart+milieu+this.arrivee.toString()+fin;
	}
	
	
	
}
