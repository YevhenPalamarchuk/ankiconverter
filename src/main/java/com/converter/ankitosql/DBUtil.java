package com.converter.ankitosql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.PatternSyntaxException;
import static com.converter.ankitosql.Service.*;

public class DBUtil {

	private static Connection conn;

	public DBUtil(String path) {
		connect(path);
	}

	private Connection connect(String path) {
		// SQLite connection string
		path = Service.changeSlashDirection(path);
		String url = "jdbc:sqlite:" + path;

		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}

		try {
			conn = DriverManager.getConnection(url);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return conn;
	}

	public Map<String, String> selectAll() {
		Map<String, String> learningCards = new HashMap<String, String>();
		String sql = "SELECT flds, sfld FROM notes";

		try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

			// loop through the result set
			while (rs.next()) {
				String question = rs.getString("sfld");
				String answer = rs.getString("flds");

				try {
					if (checkTags(answer)) {
						answer = answer.replaceFirst(question, "");
					}

				} catch (PatternSyntaxException e) {
					answer = "";
					continue;
				}
				learningCards.put(question, answer);
			}
			System.out.println(learningCards.size());
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException ex) {
				System.out.println(ex.getMessage());
			}
		}
		return learningCards;
	}

}
