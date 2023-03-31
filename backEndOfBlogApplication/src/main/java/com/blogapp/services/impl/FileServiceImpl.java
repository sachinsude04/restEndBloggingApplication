package com.blogapp.services.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.blogapp.services.FileService;

@Service
public class FileServiceImpl implements FileService{

	@Override
	public String uploadImage(String path, MultipartFile file) throws IOException {
		
		String name =file.getOriginalFilename();
		//abc.png
		
		//random name generate file
		String randomID=UUID.randomUUID().toString();
		String fileName1=randomID.concat(name.substring(name.lastIndexOf(".")));
		
		//Fullpath
		String filePath= path+File.separator+fileName1;
		
		//create Folder if not created
		File f=new File(path);
		if(!f.exists()) {
			f.mkdir();
		}
		
		
		
		//file copy
		
		Files.copy(file.getInputStream(), Paths.get(filePath));
		return name;
		
	}

	@Override
	public InputStream getResource(String path, String FileName) throws FileNotFoundException {
		String fullPath=path+File.separator+FileName;
		InputStream is=new FileInputStream(fullPath);
		
		return is;
	}

}
