package com.yqg.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.yqg.R.Result;
import com.yqg.mapper.CommentMapper;
import com.yqg.service.ICommentService;
import com.yqg.vo.ActionComment;
import com.yqg.vo.Comment;
import com.yqg.vo.Topic;
import jakarta.annotation.Resource;
import org.apache.catalina.connector.Response;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 */
@Service
public class CommentServiceImpl implements ICommentService {
    public static final int URL_COMPONENTS_MINIMUM_LENGTH = 3;

    @Resource
    private CommentMapper commentMapper;

    public String addNewComment(Comment comment) {

        comment.setCommentId(String.valueOf(IdUtil
                .getSnowflake(1, 1).nextId()));
        comment.setCreateTime(String.valueOf(DateUtil.current()));
        comment.setIsDeleted(0);
        comment.setCommentCount(0);
        comment.setLikeCount(0);
        int i = commentMapper.addComment(comment);
        if (i > 0) {
            return Result.success(null);
        } else {
            return Result.error("评论失败");
        }

    }

    public String getAllComments() {
        List<ActionComment> commentList = commentMapper.getCommentList();
        if (commentList == null || commentList.size() == 0) {
            return Result.error("暂无评论");
        }
        return Result.success(commentList);
    }

    public String deleteMyComment(String commentId) {
        int i = commentMapper.deleteComment(commentId);
        if (i > 0) {
            return Result.success(null);
        } else {
            return Result.error("删除失败");
        }
    }

    @Override
    public int addComment(Comment comment) {
        return commentMapper.addComment(comment);
    }

    @Override
    public List<Topic> getTopicList() {
        return commentMapper.getTopicList();
    }

    @Override
    public List<ActionComment> getCommentList() {
        return commentMapper.getCommentList();
    }

    @Override
    public int deleteComment(String commentId) {
        return commentMapper.deleteComment(commentId);
    }

    @Override
    public List<ActionComment> getCommentListByHot() {
        return commentMapper.getCommentListByHot();
    }

    @Override
    public List<ActionComment> getCommentListByTopic(String topic) {
        return commentMapper.getCommentListByTopic(topic);
    }

    public String getCommentByTopic(String topic) {
        List<ActionComment> commentList = commentMapper.getCommentListByTopic(topic);
        if (commentList == null || commentList.size() == 0) {
            return Result.error("暂无评论");
        }
        return Result.success(commentList);
    }
    public String getHotCommentList() {
        List<ActionComment> commentList = commentMapper.getCommentListByHot();
        if (commentList == null || commentList.size() == 0) {
            return Result.error("暂无评论");
        }
        return Result.success(commentList);
    }

    public String getUrlInfo(String url) throws IOException {
        Map<String, String> websiteData = new HashMap<>(16);
        String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.3";
        try {
            Connection.Request request = Jsoup.connect(url)
                    .userAgent(userAgent)
                    .request();
            request.followRedirects(false);
            Connection.Response response = Jsoup.connect(request.url().toString())
                    .cookies(request.cookies())
                    .headers(request.headers())
                    .method(request.method())
                    .data(request.data())
                    .execute();
            Document document = response.parse();
            String title = document.title();
            String domain = getDomainFromUrl(url);
            String favicon = getFaviconFromDocument(document, domain);
            websiteData.put("title", title);
            websiteData.put("domain", domain);
            websiteData.put("favicon", favicon);
        } catch (
                Exception e) {
            e.printStackTrace();
        }
        if (websiteData.size() == 0) {
            return Result.error("获取失败");
        }
        return Result.success(websiteData);
    }

    public static String getDomainFromUrl(String url) {
        String[] parts = url.split("/");
        String domain = "";
        if (parts.length >= URL_COMPONENTS_MINIMUM_LENGTH) {
            domain = parts[2];
        }
        return domain;
    }

    public static String getFaviconFromDocument(Document document, String domain) {
        Elements linkTags = document.select("link[href]");
        String faviconUrl = "";
        for (Element tag : linkTags) {
            String rel = tag.attr("rel");
            if (rel.contains("shortcut") || rel.contains("icon")) {
                faviconUrl = tag.absUrl("href");
                break;
            }
        }
        if ("".equals(faviconUrl)) {
            faviconUrl = "https://www.google.com/s2/favicons?domain=" + domain;
        }
        return faviconUrl;
    }

}
