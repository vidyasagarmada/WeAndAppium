package com.dxc.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * This class is used to archive folder
 * 
 * @author Vidyasagar Mada
 *
 */
public class ZipUtil {
	private static final int DEFAULT_BUFFER_SIZE = 1024 * 4;

	/**
	 * This method is used to archive folder
	 * 
	 * @author Vidyasagar Mada
	 * @param fileToZip
	 * @param excludeContainingFolder
	 * @return archivedFile
	 * @throws IOException
	 */
	public String zipFile(String fileToZip, boolean excludeContainingFolder) throws IOException {
		String archivedFile = fileToZip + ".zip";
		ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(archivedFile));
		File srcFile = new File(fileToZip);
		if (excludeContainingFolder && srcFile.isDirectory()) {
			for (String fileName : srcFile.list()) {
				addToZip("", fileToZip + "/" + fileName, zipOut);
			}
		} else {
			addToZip("", fileToZip, zipOut);
		}
		zipOut.flush();
		zipOut.close();
		System.out.println("Successfully created " + archivedFile);
		return archivedFile;
	}

	/**
	 * This method will add files to archive
	 * 
	 * @author Vidyasagar Mada
	 * @param path
	 * @param srcFile
	 * @param zipOut
	 * @throws IOException
	 */
	private static void addToZip(String path, String srcFile, ZipOutputStream zipOut) throws IOException {
		File file = new File(srcFile);
		String filePath = "".equals(path) ? file.getName() : path + "/" + file.getName();
		if (file.isDirectory()) {
			for (String fileName : file.list()) {
				addToZip(filePath, srcFile + "/" + fileName, zipOut);
			}
		} else {
			zipOut.putNextEntry(new ZipEntry(filePath));
			FileInputStream in = new FileInputStream(srcFile);
			byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
			int len;
			while ((len = in.read(buffer)) != -1) {
				zipOut.write(buffer, 0, len);
			}
			in.close();
		}
	}
}