package com.yqg.service;

import com.yqg.vo.UploadResource;

import java.util.List;

public interface IResourceService {

    /**
     * 获取指定id的资源（批量）
     * @param resourceIds
     * @return
     */
    List<UploadResource> getUploadResource(List<Long> resourceIds);

    /**
     * 批量写入资源记录
     * @param list
     * @return
     */
    int insertBatchResources(List<UploadResource> list);

    /**
     * 获取指定id的资源
     * @param fileId
     * @return
     */
    UploadResource getResourcesById(String fileId);
}
