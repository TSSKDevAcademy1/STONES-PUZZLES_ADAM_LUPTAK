package game;

import core.NumberTilesField;
import swingUI.SwingGui;

import static java.lang.System.out;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.mysql.jdbc.Buffer;

import consoleUI.Ui;
import consoleUI.WrongFormatException;

public class Puzzle {
	private static long startMillis;
	public static long getStartMillis() {
		return startMillis;
	}

	public static void setStartMillis(long startMillis) {
		Puzzle.startMillis = startMillis;
	}

	private static Puzzle instance = null;
	private static InterFace ie;
	private static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

	/**
	 * here is constraints, only one instace of Puzzle can be initialized
	 */
	public static Puzzle getInstance() {
		if (instance == null) {
			instance = new Puzzle();
		}
		return instance;
	}

	public static long getPlayingSeconds() {
		return ((int) System.currentTimeMillis() - (int) startMillis) / 1000;
	}

	private Puzzle() {
		instance = this;
		startMillis = System.currentTimeMillis();
		NumberTilesField playingField = NumberTilesField.load();
		ie.newGameStarted(playingField);
	}

	public static void main(String[] args) {
		while (ie == null) {
			setupInterface();
		}
		new Puzzle();
	}

	public static void setupInterface() {
		System.out.println("Vyberte si druh interfacu  bud Consol alebo GUI 1/2");
		String inputText = readLine();
		try {
			handlingInput(inputText);
		} catch (WrongFormatException e) {
			System.err.println(e);
			return;
		}
		switch (inputText) {
		case "1":
			ie = new Ui();
			break;
		case "2":
			ie = new SwingGui();
			break;
		default:
			return;
		}
	}

	public static String readLine() {
		String inputText;
		try {
			inputText = input.readLine();
			return inputText;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("musis zadat srapvne cislo");
		}
		return null;
	}

	private static void handlingInput(String input) throws WrongFormatException {
		Matcher matcher = (Pattern.compile("\\d")).matcher(input);
		if (matcher.matches()) {

		} else {
			throw new WrongFormatException("ZLi vstup chlapce");
		}
	}

}
