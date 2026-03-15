package com.liubo.domain.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author 68
 * 2026/3/8 13:36
 */
public interface IRagService {
    void storeRagFile(String name, String tag, List<MultipartFile> files);
}
