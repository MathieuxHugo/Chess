package main.chess;

public class Roque extends Coup {
	private Coordonnee departTour, arriveeTour;
	private Tour tour;
	protected Roque(Case depart, Case arrivee, Case departTour, Case arriveeTour) {
		super(depart, arrivee);
		this.arriveeTour=arriveeTour.getCoordonnee();
		this.departTour=departTour.getCoordonnee();
		this.tour=(Tour)departTour.getPiece().clone();
	}
	public Coordonnee getDepartTour() {
		return departTour;
	}
	protected void setDepartTour(Coordonnee departTour) {
		this.departTour = departTour;
	}
	public Coordonnee getArriveeTour() {
		return arriveeTour;
	}
	protected void setArriveeTour(Coordonnee arriveeTour) {
		this.arriveeTour = arriveeTour;
	}
	public Piece getTour() {
		return tour.clone();
	}
	protected void setTour(Tour tour) {
		this.tour = tour;
	}
	
	

}
