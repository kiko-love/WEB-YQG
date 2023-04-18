package com.yqg.mapper;

import com.yqg.vo.UploadResource;

import java.util.List;

public interface ResourceMapper {
    /**
     * 获取指定id的资源（批量）
     * @param resourceIds
     * @return
     */
    List<UploadResource> getUploadResource(List<Long> resourceIds);
}
