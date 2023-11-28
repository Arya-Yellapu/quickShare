package com.test.quickshare.Controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Downloader {

	@GetMapping("/download")
	public ResponseEntity<InputStreamResource> getFile(@RequestParam String mail, @RequestParam String file)
			throws FileNotFoundException {
		InputStreamResource ir = new InputStreamResource(
				new FileInputStream("D:" + File.separator + mail + File.separator + file));
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachments;filename=" + file);
		headers.add(HttpHeaders.CONTENT_TYPE, "application/octet-stream");
		return ResponseEntity.ok().headers(headers).body(ir);
	}
}
