package useless;

import java.awt.List;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import useless.domain.Directory;
import useless.domain.Image;
import useless.domain.Resource;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Directory classes = new Directory();
		Directory layout = new Directory();
		Directory drawable = new Directory();
		Directory style = new Directory();

		classes.addPath("C:\\Users\\Aleson\\Documents\\gapzero-android\\app\\src\\main\\java\\com\\santander\\app");
		layout.addPath("C:\\Users\\Aleson\\Documents\\gapzero-android\\app\\src\\main\\res\\layout");
		layout.addPath("C:\\Users\\Aleson\\Documents\\gapzero-android\\app\\src\\main\\res\\layout-v21");
		layout.addPath("C:\\Users\\Aleson\\Documents\\gapzero-android\\app\\src\\main\\res\\layout-land");
		drawable.addPath("C:\\Users\\Aleson\\Documents\\gapzero-android\\app\\src\\main\\res\\drawable");
		drawable.addPath("C:\\Users\\Aleson\\Documents\\gapzero-android\\app\\src\\main\\res\\drawable-xhdpi");
		drawable.addPath("C:\\Users\\Aleson\\Documents\\gapzero-android\\app\\src\\main\\res\\drawable-xxhdpi");
		drawable.addPath("C:\\Users\\Aleson\\Documents\\gapzero-android\\app\\src\\main\\res\\drawable-xxxhdpi");
		drawable.addPath("C:\\Users\\Aleson\\Documents\\gapzero-android\\app\\src\\main\\res\\drawable-v21");
		drawable.addPath("C:\\Users\\Aleson\\Documents\\gapzero-android\\app\\src\\main\\res\\drawable-ldpi");
		drawable.addPath("C:\\Users\\Aleson\\Documents\\gapzero-android\\app\\src\\main\\res\\drawable-mdpi");
		drawable.addPath("C:\\Users\\Aleson\\Documents\\gapzero-android\\app\\src\\main\\res\\drawable-hdpi");
		style.addPath("C:\\Users\\Aleson\\Documents\\gapzero-android\\app\\src\\main\\res\\values");
		style.addPath("C:\\Users\\Aleson\\Documents\\gapzero-android\\app\\src\\main\\res\\values-w820dp");
		style.addPath("C:\\Users\\Aleson\\Documents\\gapzero-android\\app\\src\\main\\res\\values-v21");

		FileUtils classesUtil = new FileUtils();
		FileUtils layoutsUtil = new FileUtils();
		FileUtils drawablesUtil = new FileUtils();
		FileUtils styleUtil = new FileUtils();
		FileUtils uselessUtil = new FileUtils();

		ArrayList<File> projectImagesFiles = new ArrayList<File>();
		ArrayList<File> projectJavaFiles = new ArrayList<File>();
		ArrayList<File> projectLayoutFiles = new ArrayList<File>();
		ArrayList<File> projectStyleFiles = new ArrayList<File>();

		ArrayList<Image> images = new ArrayList<Image>();
		ArrayList<Resource> resJava = new ArrayList<Resource>();
		ArrayList<Resource> resLayout = new ArrayList<Resource>();
		ArrayList<Resource> resImages = new ArrayList<Resource>();
		ArrayList<Resource> resStyle = new ArrayList<Resource>();

		long start = System.currentTimeMillis();

		for (String s : drawable.getPath()) {
			projectImagesFiles.addAll(drawablesUtil.walkDirs(s));
		}

		for (File file : projectImagesFiles) {
			Image image = new Image();
			image.setId(file.getName());
			images.add(image);
		}

		for (String s : classes.getPath()) {
			projectJavaFiles.addAll(classesUtil.walkDirsJava(s));
		}
		System.out.println("Classes: " + projectJavaFiles.size());

		for (String s : layout.getPath()) {
			projectLayoutFiles.addAll(drawablesUtil.walkDirs(s));
		}
		System.out.println("Layouts: " + projectLayoutFiles.size());

		for (String s : style.getPath()) {
			projectStyleFiles.addAll(styleUtil.walkDirs(s));
		}
		System.out.println("Styles: " + projectStyleFiles.size());

		for (File file : projectImagesFiles) {
			Resource resource = new Resource();
			resource.setFile(file);
			resource.setName(file.getName());
			resource.setNotFound(true);
			resource.setPath(file.getAbsolutePath());
			resource.setResourceType(null);
			resImages.add(resource);
		}

		for (File file : projectJavaFiles) {
			Resource resource = new Resource();
			resource.setFile(file);
			resource.setName(file.getName());
			resource.setNotFound(true);
			resource.setPath(file.getAbsolutePath());
			resource.setResourceType(null);
			resJava.add(resource);
		}

		for (File file : projectLayoutFiles) {
			Resource resource = new Resource();
			resource.setFile(file);
			resource.setName(file.getName());
			resource.setNotFound(true);
			resource.setPath(file.getAbsolutePath());
			resource.setResourceType(null);
			resLayout.add(resource);
		}

		for (File file : projectStyleFiles) {
			Resource resource = new Resource();
			resource.setFile(file);
			resource.setName(file.getName());
			resource.setNotFound(true);
			resource.setPath(file.getAbsolutePath());
			resource.setResourceType(null);
			resStyle.add(resource);
		}

		ArrayList<Resource> resources = new ArrayList<>();
		for (Resource xml : resImages) {
			if (xml.getFile().getName().contains(".xml"))
				resources.add(xml);
		}
		resources.addAll(resLayout);
		resources.addAll(resJava);
		resources.addAll(resStyle);
		ArrayList<Resource> uselessFiles = new ArrayList<>();
		System.out.println("Searching for useless files: ");
		uselessFiles = uselessUtil.searchUselessFiles(resImages, resources);
		System.out.println("....Done");
		System.out.println(uselessFiles.size());
		String time = new SimpleDateFormat("mm:ss:SSS").format((System.currentTimeMillis() - start));
		System.out.println("Time: " + time);
	}

}
