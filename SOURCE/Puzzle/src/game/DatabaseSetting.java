package game;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import game.BestTimes.PlayerTime;

public class DatabaseSetting {
	public static final String URL = "jdbc:mysql://localhost:3305/puzzle";
	public static final String USER = "root";
	public static final String PASSWORD = "deckaa";

	public static final String QUERY_CREATE_BEST_TIMES = "CREATE TABLE player_time (name VARCHAR(128) NOT NULL, best_time INT NOT NULL)";
	public static final String QUERY_ADD_BEST_TIME = "INSERT INTO player_time (name, best_time) VALUES (?, ?)";
	public static final String QUERY_SELECT_BEST_TIMES = "SELECT name, best_time FROM player_time";

	private DatabaseSetting() {

	}

	public static void createrTable() {
		try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
				Statement stmt = con.createStatement()) {
			stmt.execute("drop table player_time");
			stmt.executeUpdate(QUERY_CREATE_BEST_TIMES);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void save(BestTimes bestTimes) {
		try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
				PreparedStatement stmt = con.prepareStatement(QUERY_ADD_BEST_TIME)) {
			stmt.execute("delete from player_time");
			for (PlayerTime playerTime : bestTimes) {
				stmt.setString(1, playerTime.getName());
				stmt.setString(2, Integer.toString(playerTime.getTime()));
				stmt.executeUpdate();
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static BestTimes load() {
		BestTimes bestTime = new BestTimes();
		try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
				Statement stmt = con.createStatement()) {
			ResultSet rs = stmt.executeQuery(QUERY_SELECT_BEST_TIMES);
			while (rs.next()) {
				bestTime.addPlayerTime(rs.getString(1), Integer.parseInt(rs.getString(2)));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return bestTime;
	}
}
