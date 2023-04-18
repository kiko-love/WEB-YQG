package com.yqg.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author KIKO
 */
public class FileTypeUtils {

    private static final Map<String, String> FILE_TYPE_MAP = new HashMap<>();

    static {
        // 文本文件
        FILE_TYPE_MAP.put("txt", "text");
        FILE_TYPE_MAP.put("doc", "text");
        FILE_TYPE_MAP.put("docx", "text");
        FILE_TYPE_MAP.put("pages", "text");
        FILE_TYPE_MAP.put("odt", "text");

        // 图像文件
        FILE_TYPE_MAP.put("jpg", "image");
        FILE_TYPE_MAP.put("jpeg", "image");
        FILE_TYPE_MAP.put("png", "image");
        FILE_TYPE_MAP.put("bmp", "image");
        FILE_TYPE_MAP.put("gif", "image");
        FILE_TYPE_MAP.put("tiff", "image");

        // 视频文件
        FILE_TYPE_MAP.put("avi", "video");
        FILE_TYPE_MAP.put("mp4", "video");
        FILE_TYPE_MAP.put("mov", "video");
        FILE_TYPE_MAP.put("wmv", "video");
        FILE_TYPE_MAP.put("flv", "video");
        FILE_TYPE_MAP.put("mkv", "video");

        // 音频文件
        FILE_TYPE_MAP.put("mp3", "audio");
        FILE_TYPE_MAP.put("wav", "audio");
        FILE_TYPE_MAP.put("aac", "audio");
        FILE_TYPE_MAP.put("wma", "audio");
        FILE_TYPE_MAP.put("flac", "audio");

        // 压缩文件
        FILE_TYPE_MAP.put("zip", "compressed");
        FILE_TYPE_MAP.put("rar", "compressed");
        FILE_TYPE_MAP.put("7z", "compressed");
        FILE_TYPE_MAP.put("tar.gz", "compressed");
        FILE_TYPE_MAP.put("tar", "compressed");
    }

    public static String getFileType(String filename) {
        String suffix = getFileSuffix(filename);
        if (suffix.isEmpty()) {
            return "unknown";
        }

        String fileType = FILE_TYPE_MAP.get(suffix.toLowerCase());
        return fileType != null ? fileType : "other";
    }

    private static String getFileSuffix(String filename) {
        int lastDotIndex = filename.lastIndexOf('.');
        return lastDotIndex >= 0 ? filename.substring(lastDotIndex + 1) : "";
    }
}

