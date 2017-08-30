package com.converter.ankitosql;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;


public final class Service {

	public static String ankiFilePath = "";
	public static String outputFilePath = "";

	public static String getCurrentPathToTheFile(String inputString) {
		if (inputString == null || inputString.length() == 0) {
			System.out.println("----------------------------");
			System.out.println("Write in the commandline:");
			System.out.println("java -jar ankitosql.jar AnkiPakageFile.apkg");
			System.exit(0);
		}
		String filePath = "";
		int slashPosition = 0;
		slashPosition = inputString.lastIndexOf("\\");
		if (slashPosition == -1) {
			filePath = CurrentDir();
			filePath = filePath + inputString;
		} else if (slashPosition > 0 && slashPosition < 4) {
			filePath = inputString.substring(slashPosition + 1);
		} else {
			filePath = inputString;
		}
		return filePath;
	}

	private static String CurrentDir() {
		String path = System.getProperty("java.class.path");
		String FileSeparator = (String) System.getProperty("file.separator");
		return path.substring(0, path.lastIndexOf(FileSeparator) + 1);
	}

	public static void printHeader() {
		System.out.println("Converter anki-pakage file to MySQL-sript. Gets questions & answers.");
		System.out.println("(c) Developed by Yevhen Palamarchuk, 2017. All rights reserved.");
		System.out.println("Version: 1.0.0 \n");
		
	}

	public static String changeSlashDirection(String path) {
		return path.replace("\\", "/");
	}

	public static void writeSQLFile(Map<String, String> learningCards) {
		String data = "";
		if (learningCards != null) {
			File file = new File(outputFilePath + "Learning_cards.sql");
			FileWriter fr = null;
			try {
				fr = new FileWriter(file);
				String c = Character.toString((char) 31);
				for (Entry<String, String> entry : learningCards.entrySet()) {

					String question = entry.getKey();
					String answer = entry.getValue().replaceAll(c, "");
					//answer = answer.replaceAll("", "");
					answer = answer.replaceAll("\"", "'");
//					data = "INSERT INTO orlp.card (answer, question) VALUES (\"" + answer
//							+ "\", \"" + question + "\");\n";
					data = "INSERT INTO test.card (question, answer) VALUES (\"" + question
					+ "\", \"" + answer + "\");\n";
//					data = "\"" + question + "\", \"" + answer + "\"\n";
					fr.write(data);
				}
				System.out.println("Created file: " + outputFilePath + "Learning_cards.sql");
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					fr.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static String getFileExtension(String fileName) {
		if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
			return fileName.substring(fileName.lastIndexOf(".") + 1);
		else
			return "";
	}

	public static void deleteFile(String filePath) {
		File file = new File(filePath);
		if (file.exists()) {
			file.delete();
		} else {
			System.err.println("I cannot find '" + file + "' ('" + file.getAbsolutePath() + "')");
		}
	}
	
	public static boolean checkTags(String string) {
		boolean result = true;
		char[] array = string.toCharArray();
		for(int i=0; i<string.length(); i++) {
			if ((array[i] == '<') || (array[i] == '>')) {
				return false;
			}
		}
		return result;
	}
}
