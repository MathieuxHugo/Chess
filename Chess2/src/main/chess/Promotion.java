package main.chess;

public class Promotion extends Coup {
	private Piece promo;
	public static enum Choix{
		Reine,Tour,Fou,Cavalier;
	}
	protected Promotion(Coordonnee depart, Coordonnee arrivee, Pion p, Piece prise) {
		super(depart, arrivee, p, prise);
		// TODO Auto-generated constructor stub
	}
	private Promotion(Coordonnee depart, Coordonnee arrivee, Piece p, Piece prise, Piece prom) {
		super(depart, arrivee, p, prise);
		this.promo=prom.clone();
		// TODO Auto-generated constructor stub
	}
	public void choixPromo(Choix choix) {
		switch(choix) {
		case Cavalier:
			this.promo=new Cavalier(this.getpDepart().isBlanc());
			break;
		case Fou:
			this.promo=new Fou(this.getpDepart().isBlanc());
			break;
		case Reine:
			this.promo=new Reine(this.getpDepart().isBlanc());
			break;
		case Tour:
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

}
