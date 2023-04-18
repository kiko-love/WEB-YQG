package com.yqg.service;

import com.yqg.vo.UserArticleOperation;

import java.util.List;

/**
 * @author KIKO
 */
public interface IUserArticleOperationService {
    /**
     * 获取所有用户的行为记录
     * @return
     */
    List<UserArticleOperation> getAllUserPreference();
}
