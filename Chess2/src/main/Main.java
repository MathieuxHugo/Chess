package main;

import com.google.inject.Guice;
import com.google.inject.Injector;

import main.ui.JeuDEchec;

public class Main {

	public static void main(String[] args) {
		System.setProperty("java.awt.headless", "false"); //Disables headless
		
        Injector injector = Guice.createInjector(new ChessModule());

		JeuDEchec jeu = injector.getInstance(JeuDEchec.class);
	}

}
