package game;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Formatter;
import java.util.Iterator;
import java.util.List;

public class BestTimes implements Serializable, Iterable<BestTimes.PlayerTime> {

	private List<PlayerTime> playerTimes = new ArrayList<PlayerTime>();

	public Iterator<PlayerTime> iterator() {
		return playerTimes.iterator();
	}

	public void addPlayerTime(String name, int time) {
		playerTimes.add(new PlayerTime(name, time));
		Collections.sort(playerTimes);
	}

	/**
	 * Returns a string representation of the object.
	 * 
	 * @return a string representation of the object
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder();
		Iterator<PlayerTime> it = iterator();// bez toho vracia len objekty
		while (it.hasNext()) {
			sb.append(it.next());
		}
		return sb.toString();
	}

	public static void load() {

	}

	public static class PlayerTime implements Comparable<PlayerTime> {
		/** Player name. */
		private final String name;

		/** Playing time in seconds. */
		private final int time;

		/**
		 * Constructor.
		 * 
		 * @param name
		 *            player name
		 * @param time
		 *            playing game time in seconds
		 */
		public PlayerTime(String name, int time) {
			this.name = name;
			this.time = time;
		}

		public String getName() {
			return name;
		}

		public int getTime() {
			return time;
		}

		@Override
		public int compareTo(PlayerTime o) {
			if (this.time > o.getTime()) {
				return 1;
			} else if (this.time == o.getTime()) {
				return 0;
			} else {
				return -1;
			}
		}

		@Override
		public String toString() {
			Formatter formatter = new Formatter();
			formatter.format("%s : %2s %n", this.getName(), this.getTime());
			return formatter.toString();
		}

	}

}
