package main.chess;

public class Promotion extends Coup {
	private Piece promo;
	protected Promotion(Coordonnee depart, Coordonnee arrivee, Pion p, Piece prise) {
		super(depart, arrivee, p, prise);
		// TODO Auto-generated constructor stub
	}
	private Promotion(Coordonnee depart, Coordonnee arrivee, Piece p, Piece prise, Piece prom) {
		super(depart, arrivee, p, prise);
		this.promo=prom.clone();
		// TODO Auto-generated constructor stub
	}
	public void choixPromo(String choix) {
		switch(choix) {
		case Piece.CAVALIER:
			this.promo=new Cavalier(this.getpDepart().isBlanc());
			break;
		case Piece.FOU:
			this.promo=new Fou(this.getpDepart().isBlanc());
			break;
		case Piece.REINE:
			this.promo=new Reine(this.getpDepart().isBlanc());
			break;
		case Piece.TOUR:
			this.promo=new Tour(this.getpDepart().isBlanc());
			break;
		default:
			break;
		}
	}
	
	
	public Piece getPromo() {
		return promo;
	}

	@Override
	public Promotion clone() {
		return new Promotion(this.getDepart(),this.getArrivee(),this.getpDepart(),this.getPrise(),this.getPromo());
	}
	@Override
	public String toString() {
	    return super.toString()+"="+this.promo.toString();
	}
	
	

}
