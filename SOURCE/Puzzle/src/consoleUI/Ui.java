package consoleUI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.Thread.State;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import core.GameState;
import core.NumberTilesField;
import game.BestTimes;
import game.DatabaseSetting;
import game.InterFace;
import game.Puzzle;

public class Ui implements InterFace {

	private BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	private NumberTilesField playingField;
	private BestTimes bestTimes;

	public Ui() {
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see consoleUI.InterFace#newGameStarted()
	 */
	@Override
	public void newGameStarted(NumberTilesField playingField) {
		this.playingField = playingField;
		bestTimes = DatabaseSetting.load();
		System.out.println(bestTimes.toString());
		do {
			update();
			processInput();
		} while (true);
	}

	/**
	 * Process input if input is validate by handling function it process
	 * operation
	 */
	private void processInput() {
		String inputText = readLine();
		try {
			handlingInput(inputText);
		} catch (WrongFormatException e) {
			System.err.println(e);
			return;
		}
		if (inputText.equals("w") || inputText.equals("up")) {// hore
			playingField.moveUp();
		} else if (inputText.equals("s") || inputText.equals("down")) {// dole
			playingField.moveDown();
		} else if (inputText.equals("a") || inputText.equals("left")) {// lavo
			playingField.moveToLeft();
		} else if (inputText.equals("d") || inputText.equals("right")) {// pravo
			System.out.println("dolava");
			playingField.moveToRight();
		} else if (inputText.equals("x")) {// pravo
			clc(500);
			playingField.saveGame();
			System.out.println("Aktualny cas hraca: " + Puzzle.getInstance().getPlayingSeconds() + "s");
			System.exit(0);
		} else {
			System.err.println("zli vstup chlpce");
		}
		playingField.isSolved();
		clc(500);
	}

	/**
	 * But new lines number of new lines
	 */
	public void clc(int pocet) {
		for (int i = 0; i < pocet; i++) {
			System.out.println("");
		}
	}

	/*
	 * @see consoleUI.InterFace#update()
	 */
	@Override
	public void update() {
		Puzzle.getInstance();
		System.out.printf("Game state: %4s Player Time: %2ss%n", playingField.getGamteState(),
				Puzzle.getPlayingSeconds());
		System.out.println(playingField.toString());
		playingField.isSolved();
		if (playingField.getGamteState() == GameState.SOLVED) {
			Puzzle.getInstance();
			bestTimes.addPlayerTime("aladar", (int) Puzzle.getPlayingSeconds());
			DatabaseSetting.save(bestTimes);
			System.out.println("Aktualny cas hraca: " + Puzzle.getPlayingSeconds() + "s");
			System.out.println("SI VYHRALLLLLLL");// best time
			System.exit(0);
		}

	}

	public String readLine() {

		try {
			return input.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	private void handlingInput(String input) throws WrongFormatException {

		Matcher matcher = (Pattern.compile("(w){1}|(s){1}|(a){1}|(d){1}|(x){1}|(up)|(down)|(right)|(left)"))
				.matcher(input);
		if (matcher.matches()) {

		} else {
			throw new WrongFormatException("ZLi vstup chlapce");
		}
	}

}
