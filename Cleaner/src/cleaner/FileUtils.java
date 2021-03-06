package cleaner;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import cleaner.domain.Resource;

public class FileUtils {

	ArrayList<File> files = new ArrayList();

	public ArrayList<File> walkDirs(String path) {

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

	public ArrayList<Resource> search(List<Resource> resource, List<File> dir) {

		int i = 0;
		int checkedFiles = 0;
		long lines = 0;
		
		ArrayList<Resource> resourceFiles = new ArrayList<Resource>();
		
		Scanner input = new Scanner(System.in);
		for (int j = 0; j < resource.size(); j++) {
			
			resource.get(j).setNotFound(true);
			
			for (int k = 0; k < dir.size(); k++) {
				try {
					input = new Scanner(dir.get(k));
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				lines = 0;
				while (input.hasNextLine()) {
					String line = input.nextLine();
//					System.out.println("Arquivo: "+dir.get(k).getName()+" Linha: "+ line);
					if (line.contains(resource.get(j).getName())) {
						resource.get(j).setNotFound(false);
//						System.out.println(resource.get(j).getName());
						break;
					}
					lines++;
				}
			}
			resourceFiles.add(resource.get(j));
			input.close();
			checkedFiles++;
			System.out.println("Checking files: "+ checkedFiles +"/"+resource.size());
		}
		return resourceFiles;
	}
}
