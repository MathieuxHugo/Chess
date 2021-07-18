package chess;

public class PriseEnPassant extends Coup {
	private Coordonnee cPrise;
	public PriseEnPassant(Case c, Case cDest, Case prise) {
		super(c, cDest);
		this.cPrise=prise.getCoordonnee();
		this.setPrise(prise.getPiece());
	}
	public Coordonnee getcPrise() {
		return cPrise;
	}
	protected void setcPrise(Coordonnee cPrise) {
		this.cPrise = cPrise;
	}
	@Override
	public boolean isPrise() {
		return true;
	} 
	

}
