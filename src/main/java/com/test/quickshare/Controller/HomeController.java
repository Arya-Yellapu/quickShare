package com.test.quickshare.Controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.test.quickshare.Repository.UsersData;
import com.test.quickshare.Repository.UsersRepo;

@Controller
public class HomeController {

	@Autowired
	UsersRepo repo;

	Map<String, String> otpMap = new HashMap<>();

	@Autowired
	JavaMailSender sender;

	@Value("${spring.mail.username")
	private String fromAddress;

	@RequestMapping("/")
	public ModelAndView getHome() throws IOException {
		ModelAndView m1 = new ModelAndView("home");
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String link = null;
		String mail = auth.getName();
		if (auth.toString().startsWith("OAuth2AuthenticationToken")) {

			String s = auth.getPrincipal().toString();
			String[] arr = s.split(",");
			String firstName = arr[15].substring(6, 10);
			String lastName = arr[17].substring(13);
			String email = arr[19].substring(7, 30);
			mail = email;

			String pictureLink = arr[12].substring(9);
			link = pictureLink;
			repo.createOAuthUsers(firstName, lastName, email);

		}
		UsersData ud = repo.getUserData(mail);
		if (ud.getPicturePath().equalsIgnoreCase("empty")) {
			m1.addObject("path", "/images/smiley.gif");
		} else if (ud.getPicturePath().equalsIgnoreCase("Google")) {
			m1.addObject("path", link);
		}
		m1.addObject("mail", mail);
		UsersData data = repo.getUserData(mail);
		m1.addObject("name", data.getFirstName());
		String path = "D:" + File.separator + mail;
		File f = new File(path);
		m1.addObject("actualSpace", Math.abs(FileUtils.sizeOfDirectory(f) / 1073741824));
		String[] arr = f.list();
		if (arr.length != 0) {
			m1.addObject("list", arr);
		}
		return m1;
	}

	@RequestMapping("/login")
	public ModelAndView getLogin() {
		return new ModelAndView("login");
	}

	@RequestMapping("/registrationPage")
	public ModelAndView getRegistrationPage() {
		return new ModelAndView("registrationPage");
	}

	@RequestMapping("/register")
	public ModelAndView registerAndReturn(@RequestParam String firstName, @RequestParam String lastName,
			@RequestParam String Mail, @RequestParam String password) {
		ModelAndView m1 = null;
		if (firstName.isBlank() && !lastName.isBlank() && !Mail.isBlank() && !password.isBlank()) {
			m1 = new ModelAndView("registrationPage");
			m1.addObject("message", "First Name cannot be empty!");
		} else if (!firstName.isBlank() && lastName.isBlank() && !Mail.isBlank() && !password.isBlank()) {
			m1 = new ModelAndView("registrationPage");
			m1.addObject("message", "Last Name cannot be empty!");
		} else if (!firstName.isBlank() && !lastName.isBlank() && Mail.isBlank() && !password.isBlank()) {
			m1 = new ModelAndView("registrationPage");
			m1.addObject("message", "Mail cannot be empty!");
		} else if (!firstName.isBlank() && !lastName.isBlank() && !Mail.isBlank() && password.isBlank()) {
			m1 = new ModelAndView("registrationPage");
			m1.addObject("message", "password cannot be empty!");
		} else if (!firstName.isBlank() && !lastName.isBlank() && !Mail.isBlank() && !password.isBlank()) {
			if (repo.getUser(Mail) != null) {
				m1 = new ModelAndView("login");
				m1.addObject("message", "User already exists");
			} else {
				int val = (int) (Math.random() * 9000 + 1000);
				String otp = LocalDateTime.now().toString() + "=" + String.valueOf(val);
				if (otpMap.containsKey(Mail)) {
					otpMap.replace(Mail, otp);
				} else {
					otpMap.put(Mail, otp);
				}
				SimpleMailMessage message = new SimpleMailMessage();
				message.setFrom(fromAddress);
				message.setTo(Mail);
				message.setSubject("OTP for Quickshare");
				message.setText("Your OTP is " + val);
				sender.send(message);

				m1 = new ModelAndView("otpScreen");
				m1.addObject("firstName", firstName);
				m1.addObject("lastName", lastName);
				m1.addObject("Mail", Mail);
				m1.addObject("Password", password);
			}
		} else {
			m1 = new ModelAndView("registrationPage");
			m1.addObject("message", "Something went wrong, please try again later");
		}
		return m1;
	}

	@RequestMapping("/otpValidation")
	public ModelAndView validateOTP(@RequestParam String firstName, @RequestParam String lastName,
			@RequestParam String Mail, @RequestParam String password, @RequestParam String otp) {
		ModelAndView m1 = null;
		LocalDateTime current = LocalDateTime.now();
		String val = otpMap.get(Mail);
		String[] arr = val.split("=");
		if (arr[1].equalsIgnoreCase(otp)
				&& Math.abs(current.getMinute() - (LocalDateTime.parse(arr[0]).getMinute())) < 5) {
			int flag = repo.createUsers(firstName, lastName, Mail, password);
			if (flag == 0) {
				m1 = new ModelAndView("login");
				m1.addObject("message", "Please Sign In Now");
				otpMap.remove(Mail);
			} else if (flag == -1) {
				m1 = new ModelAndView("login");
				m1.addObject("message", "Something went wrong, please try again later");
				otpMap.remove(Mail);
			} else if (flag == -2) {
				m1 = new ModelAndView("login");
				m1.addObject("message", "User already exists");
				otpMap.remove(Mail);
			}

		} else {
			m1 = new ModelAndView("otpScreen");
			m1.addObject("firstName", firstName);
			m1.addObject("lastName", lastName);
			m1.addObject("Mail", Mail);
			m1.addObject("Password", password);
			m1.addObject("message", "OTP Mismatch! Please enter the OTP again");
		}
		return m1;
	}

	@RequestMapping("/resetPassword")
	public ModelAndView resetPassword() {
		return new ModelAndView("reset");
	}

	@RequestMapping("/reset")
	public ModelAndView reset(@RequestParam String mail) {
		ModelAndView m1 = null;
		if (repo.getUser(mail) == null) {
			m1 = new ModelAndView("reset");
			m1.addObject("message", "User does not exists");
		}

		else {
			int val = (int) (Math.random() * 9000 + 1000);
			String otp = LocalDateTime.now().toString() + "=" + String.valueOf(val);
			if (otpMap.containsKey(mail)) {
				otpMap.replace(mail, otp);
			} else {
				otpMap.put(mail, otp);
			}
			SimpleMailMessage message = new SimpleMailMessage();
			message.setFrom(fromAddress);
			message.setTo(mail);
			message.setSubject("OTP for Quickshare");
			message.setText("Your OTP is " + val);
			sender.send(message);

			m1 = new ModelAndView("otpReset");
			m1.addObject("Mail", mail);
		}
		return m1;
	}

	@RequestMapping("/otpResetValidation")
	public ModelAndView validaterestOTP(@RequestParam String Mail, @RequestParam String otp) {
		ModelAndView m1 = null;
		LocalDateTime current = LocalDateTime.now();
		String val = otpMap.get(Mail);
		String[] arr = val.split("=");
		if (arr[1].equalsIgnoreCase(otp)
				&& Math.abs(current.getMinute() - (LocalDateTime.parse(arr[0]).getMinute())) < 5) {
			m1 = new ModelAndView("passwordReset");
			m1.addObject("Mail", Mail);
			otpMap.remove(Mail);
		} else {
			m1 = new ModelAndView("otpReset");
			m1.addObject("Mail", Mail);
			m1.addObject("message", "OTP Mismatch! Please enter the OTP again");
		}
		return m1;
	}

	@RequestMapping("/passwordAction")
	public ModelAndView passwordReset(@RequestParam String Mail, @RequestParam String password,
			@RequestParam String passwordConfirm) {
		ModelAndView m1 = null;
		if (!password.equalsIgnoreCase(passwordConfirm)) {
			m1 = new ModelAndView("passwordReset");
			m1.addObject("Mail", Mail);
			m1.addObject("message", "Passwords do not match");
		} else {
			int flag = repo.resetPassword(Mail, password);
			if (flag == 0) {
				m1 = new ModelAndView("login");
				m1.addObject("message", "Please Sign In Now");
			} else if (flag == -1) {
				m1 = new ModelAndView("login");
				m1.addObject("message", "Something went wrong, please try again later");
			}
		}
		return m1;
	}

	public boolean checkFileExistence(String fileName) {
		File f = new File(fileName);
		return f.exists();
	}

	@RequestMapping("/upload")
	public ModelAndView uploadFile(@RequestParam String mail, @RequestParam MultipartFile[] fname) throws IOException {
		long size = 0;
		for (MultipartFile file : fname) {
			size += file.getSize();
		}
		size = Math.abs(size) / 1073741824;
		if (size + Math.abs(FileUtils.sizeOfDirectory(new File("D:" + File.separator + mail)) / 1073741824) <= 5) {
			for (MultipartFile file : fname) {
				int index = 1;
				String path = "D:" + File.separator + mail;
				String name = path + File.separator + file.getOriginalFilename();
				File f = new File(path);
				File f1 = new File(name);
				if (!f.exists()) {
					f.mkdirs();
				}
				try {
					if (!f1.exists()) {
						f1.createNewFile();
					} else {
						while (checkFileExistence(name)) {
							String[] arr = file.getOriginalFilename().split("\\.");
							String temp = "";
							for (int i = 0; i < arr.length - 1; i++) {
								temp = temp + arr[i];
								temp = temp + ".";
							}
							temp = temp.trim();
							name = path + File.separator + temp.substring(0, temp.length() - 1) + "("
									+ String.valueOf(index++) + ")." + arr[arr.length - 1];
						}
						f1 = new File(name);
						f1.createNewFile();
					}
					BufferedInputStream bis = new BufferedInputStream(file.getInputStream());
					BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(f1));

					byte[] buffer = new byte[50 * 1024 * 1024];
					int read = 0;
					while ((read = bis.read(buffer)) != -1) {
						bos.write(buffer, 0, read);
					}

					bis.close();
					bos.close();

				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return getHome();
	}

	@RequestMapping("/delete")
	public ModelAndView deleteFile(@RequestParam String mail, @RequestParam String file) throws IOException {
		File f = new File("D:" + File.separator + mail + File.separator + file);
		f.delete();
		return getHome();
	}

}
