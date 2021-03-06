package useless;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import useless.domain.Resource;

public class FileUtils {

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

	public void saveReport(File file) {
		File mediaStorageDir = new File("D:/ImagensInuteis");
		if (!mediaStorageDir.exists()) {
			mediaStorageDir.mkdirs();
			System.out.println("Sei la");
		}
		else {
			System.out.println("Sei");
		}
			FileOutputStream out;
		try {
			out = new FileOutputStream(file);
			try {
				out.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
		System.out.println(resource.size() + " - " + filesToSearch.size());
		for (Resource resourceFile : resource) {
			boolean found = false;
			checkedFiles++;
			System.out.println(checkedFiles + "/" + resource.size());
			for (Resource searchingFile : filesToSearch) {
				if (!found) {
					try (BufferedReader br = new BufferedReader(new FileReader(searchingFile.getFile()))) {
						for (String line; (line = br.readLine()) != null;) {
							if (line.contains(resourceFile.getName())) {
								found = true;
								break;
							}
						}
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			if (!found) {
				resourceFiles.add(resourceFile);
				this.saveReport(resourceFile.getFile());
				notFoundCount++;
			}
		}

		for (Resource r : resourceFiles) {
			System.out.println("Not found:" + r.getName());
		}
		System.out.println("Checked files: " + checkedFiles + " Not found: " + notFoundCount);
		return resourceFiles;
	}
}
