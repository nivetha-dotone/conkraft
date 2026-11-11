package com.wfd.dot1.cwfm.service;

import java.io.IOException;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

public interface PublicSelfRegistrationService {

	long submit(String token, Map<String, String> posted, Map<String, MultipartFile> uploads) throws IOException;
}