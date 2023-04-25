package com.yqg.mapper;

import com.yqg.vo.ActionResource;
import com.yqg.vo.UploadResource;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ResourceMapper {
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
}
