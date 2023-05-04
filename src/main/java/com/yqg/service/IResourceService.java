package com.yqg.service;

import com.yqg.vo.ActionResource;
import com.yqg.vo.UploadResource;

import java.util.List;

/**
 * @author Administrator
 */
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

    /**
     * 获取所有资源
     * @return
     */
    List<ActionResource> getResourcesList();

    /**
     * 根据标签获取资源
     * @param tag
     * @return
     */
    List<ActionResource> getResourceByTag(String tag);
    /**
     * 获取热门资源
     * @return
     */
    List<UploadResource> getHotResource();

    /**
     * 更新资源审核状态
     * @param fileId
     * @param status
     * @return
     */
    int updateStatusById(String fileId, Integer status);

    /**
     * 删除资源
     * @param fileId
     * @return
     */
    int deleteResourceById(String fileId);

    /**
     * 获取指定key的资源
     * @param key
     * @return
     */
    List<UploadResource> getResourcesByKey(String key);
}
