package cleaner;

import java.awt.List;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import cleaner.domain.Directory;
import cleaner.domain.Resource;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Directory directoryClass = new Directory();
		Directory directoryLayout = new Directory();
		Directory directoryRes = new Directory();

		directoryClass.setPath("C:/Users/Aleson/Documents/gapzero-android/app/src/main/java/com");
		directoryLayout.setPath("C:/Users/Aleson/Documents/gapzero-android/app/src/main/java/com");
		// directoryRes.setPath("/Users/Santander/Projetos/Release17/gapzero-android/app/src/main/res/drawable");
		directoryRes.setPath("C:/Users/Aleson/Documents/gapzero-android/app/src/main/java/com");

		FileUtils res = new FileUtils();
		FileUtils classes = new FileUtils();

		ArrayList<File> project = new ArrayList<File>();
		project.addAll(classes.walkDirs(directoryClass.getPath()));
		project.addAll(classes.walkDirs(directoryLayout.getPath()));
		System.out.println("Classes and Layouts: "+project.size());

		long start = System.currentTimeMillis();

		ArrayList<Resource> filesName = new ArrayList<Resource>();
		filesName.addAll(res.getResource(directoryRes.getPath()));
		System.out.println("Resources: "+filesName.size());
		ArrayList<Resource> search = res.search(filesName, project);

		int notFoundFiles = 0;

		for (Resource resource : search) {
			if (resource.isNotFound()) {
				System.out.println(resource.getFile().getName());
				notFoundFiles++;
			}
		}
		System.out.println("Useless files:" + notFoundFiles);
		String time = new SimpleDateFormat("mm:ss:SSS").format((System.currentTimeMillis() - start));
		System.out.println("Time: " + time);
	}

}
