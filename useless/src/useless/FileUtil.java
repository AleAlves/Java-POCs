package useless;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.commons.io.FileUtils;

import useless.domain.Resource;

public class FileUtil {

	ArrayList<File> files = new ArrayList();

	public ArrayList<File> walkDirsJava(String path) {
		File root = new File(path);
		File[] list = root.listFiles();

		if (list == null)
			return null;

		for (File f : list) {
			if (f.isDirectory()) {
				walkDirsJava(f.getAbsolutePath());
			} else {
				File file = new File(f.getAbsoluteFile().toString());
				if (file.isFile()) {
					files.add(file);
				}
			}
		}
		return files;
	}

	public ArrayList<File> walkDirs(String path) {
		if (files != null && !files.isEmpty()) {
			files.clear();
		}
		File root = new File(path);
		File[] list = root.listFiles();

		if (list == null)
			return null;

		for (File f : list) {
			if (f.isDirectory()) {
				walkDirs(f.getAbsolutePath());
			} else {
				File file = new File(f.getAbsoluteFile().toString());
				if (file.isFile()) {
					files.add(file);
				}
			}
		}
		return files;
	}

	ArrayList<Resource> filesName = new ArrayList();

	public ArrayList<Resource> getResource(String path) {

		File root = new File(path);
		File[] list = root.listFiles();

		if (list == null)
			return null;

		for (File f : list) {
			if (f.isDirectory()) {
				getResource(f.getAbsolutePath());
			} else {
				String fileName = f.getName().toString();
				try {
					fileName = fileName.substring(0, fileName.lastIndexOf('.'));
				} catch (Exception e) {
					System.out.println(e);
				}
				if (fileName != null) {
					Resource resource = new Resource();
					resource.setPath(f.getAbsolutePath());
					resource.setName(fileName);
					resource.setNotFound(false);
					resource.setFile(f);
					filesName.add(resource);
				}
			}
		}
		return filesName;
	}

	public ArrayList<Resource> searchUselessFiles(List<Resource> resource, List<Resource> filesToSearch) {

		int i = 0;
		int checkedFiles = 0;
		int notFoundCount = 0;
		ArrayList<Resource> resourceFiles = new ArrayList<Resource>();
		for (Resource resourceFile : resource) {
			boolean found = false;
			checkedFiles++;
			System.out.println(checkedFiles + "/" + resource.size());
			for (Resource searchingFile : filesToSearch) {

				if (!found) {
					try {
						BufferedReader br = new BufferedReader(new FileReader(searchingFile.getFile()));
						try {
							StringBuilder sb = new StringBuilder();
							String line = br.readLine();

							while (line != null) {
								line = br.readLine();
								if (line.contains(resourceFile.getName())) {
									found = true;
									break;
								}
							}
							String everything = sb.toString();
							System.out.print(everything);
						} finally {
							br.close();
						}
					} catch (Exception e) {

					}
				}
			}
			if (!found) {
				resourceFiles.add(resourceFile);
				saveReport(resourceFile.getFile());
				notFoundCount++;
			}
		}

		for (Resource r : resourceFiles) {
			System.out.println("Not found:" + r.getName());
		}
		System.out.println("Checked files: " + checkedFiles + " Not found: " + notFoundCount);
		return resourceFiles;
	}

	public void saveReport(File file) {
		File mediaStorageDir = new File("D:/temp/useless/");
		if (!mediaStorageDir.exists()) {
			mediaStorageDir.mkdirs();
		}
		File source = new File(file.getPath());
		File dest = new File("D:/temp/useless/" + file.getName());
		try {
			FileUtils.copyFile(source, dest);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}