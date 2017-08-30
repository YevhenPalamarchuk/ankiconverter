package com.converter.ankitosql;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import static com.converter.ankitosql.Service.*;

public class ZipManager {

	private static String zipFileName = "";

	private static final int BUFFER_SIZE = 1024;

	public ZipManager(String inputFilePath) {
		zipFileName = inputFilePath;
	}

	void unZip() {
		byte[] buffer = new byte[BUFFER_SIZE];

		// Creating catalog to which we will unpack files
		final String dstDirectory = destinationDirectory(zipFileName);
		final File dstDir = new File(dstDirectory);
		if (!dstDir.exists()) {
			dstDir.mkdir();
		}
		ZipInputStream zis = null;
		try {
			// Getting ZIP archive content
			zis = new ZipInputStream(new FileInputStream(zipFileName));
			ZipEntry ze = zis.getNextEntry();
			String nextFileName;
			while (ze != null) {
				nextFileName = ze.getName();

				String extension = getFileExtension(nextFileName);

				if (extension.equals("anki2")) {
					outputFilePath = dstDirectory + File.separator;
					File nextFile = new File(outputFilePath + nextFileName);
					ankiFilePath = nextFile.getAbsolutePath();
					System.out.println("Successfully unpacked: " + ankiFilePath);
					// If have a catalog - we should create it.
					if (ze.isDirectory()) {
						nextFile.mkdir();
					} else {
						// Creating all parent catalogs
						new File(nextFile.getParent()).mkdirs();
						// Writing file content
						try (FileOutputStream fos = new FileOutputStream(nextFile)) {
							int length;
							while ((length = zis.read(buffer)) > 0) {
								fos.write(buffer, 0, length);
							}
						}
					}
					break;
				}

				ze = zis.getNextEntry();
			}

		} catch (FileNotFoundException ex) {
			Logger.getLogger(ZipManager.class.getName()).log(Level.SEVERE, null, ex);
		} catch (IOException ex) {
			Logger.getLogger(ZipManager.class.getName()).log(Level.SEVERE, null, ex);
		} finally {
			try {
				zis.closeEntry();
				zis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private String destinationDirectory(final String srcZip) {
		return srcZip.substring(0, srcZip.lastIndexOf("."));
	}

}