package com.yqg.service.impl;

import com.yqg.R.Result;
import com.yqg.mapper.UserArticleOperationMapper;
import com.yqg.service.IUserArticleOperationService;
import com.yqg.vo.RecommendArticle;
import com.yqg.vo.UserArticleOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.common.FastByIDMap;
import org.apache.mahout.cf.taste.impl.model.GenericDataModel;
import org.apache.mahout.cf.taste.impl.model.GenericPreference;
import org.apache.mahout.cf.taste.impl.model.GenericUserPreferenceArray;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.UncenteredCosineSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.model.PreferenceArray;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author KIKO
 */
@Slf4j
@Service
public class UserArticleOperationServiceImpl implements IUserArticleOperationService {
    @Autowired
    private UserArticleOperationMapper userArticleOperationMapper;
    @Autowired
    private ArticleServiceImpl articleService;

    @Override
    public List<UserArticleOperation> getAllUserPreference() {
        return userArticleOperationMapper.getAllUserPreference();
    }

    /**
     * 数据整合（把获取到的推荐文章ID进行查询再返回JSON数据）
     *
     * @param userId 用户id
     * @param num    推荐数量
     * @return
     */
    public String genRecArticlesIntegration(String userId, int num) throws TasteException {
        //判断userId是否能转换成Integer或者为空
        if (userId == null || userId.equals("") || !userId.matches("[0-9]+")) {
            List<RecommendArticle> articleList = articleService.getRandomArticle(num);
            articleList = convertTagsStringToList(articleList);
            return Result.success(articleList);
        }
        List<Long> recommendList = recommend(Integer.valueOf(userId), num);
        List<RecommendArticle> articleList = null;
        if (recommendList.size() == 0) {
            articleList = articleService.getRandomArticle(num);
        } else {
            articleList = articleService.getArticles(recommendList);
            //随机排列articleList的元素
            articleList = articleList.stream().sorted((a, b) -> Math.random() > 0.5 ? 1 : -1)
                    .collect(Collectors.toList());
            articleList = convertTagsStringToList(articleList);
        }
        return Result.success(articleList);
    }

    /**
     * 转换Tags字符串为数组
     * @param articleList
     * @return
     */
    public static List<RecommendArticle> convertTagsStringToList(List<RecommendArticle> articleList) {
        for (RecommendArticle article : articleList) {
            if (article.getTags() != null) {
                // 将 tags 字符串转换成数组，并赋值给 RecommendArticle 对象的 tags 属性
                article.setTagsArray(article.getTags().split(","));
            } else {
                // 如果 tags 字段为空，则将 tags 属性设置为一个空数组
                article.setTagsArray(new String[0]);
            }
        }
        return articleList;
    }

    /**
     * 返回推荐结果
     *
     * @param userId 用户id需要转换成Integer
     * @param num    推荐数量（数据量足够的情况下）
     * @return
     * @throws TasteException
     */
    public List<Long> recommend(Integer userId, int num) throws TasteException {
        List<UserArticleOperation> userList = userArticleOperationMapper.getAllUserPreference();
        //创建数据模型
        DataModel dataModel = this.createDataModel(userList);
        //获取用户相似程度(使用去中心化余弦相似度，简化了计算，速度更快但准确率会降低)
        UserSimilarity similarity = new UncenteredCosineSimilarity(dataModel);
        //获取用户邻居（该对象代表了一个用户邻居集合，在推荐系统中常用于计算某个用户对某个物品的预测评分）
        UserNeighborhood userNeighborhood = new NearestNUserNeighborhood(2, similarity, dataModel);
        //构建推荐器
        Recommender recommender = new GenericUserBasedRecommender(dataModel, userNeighborhood, similarity);
        //推荐num个(数据量足够的情况下)
        List<RecommendedItem> recommendedItems = recommender.recommend(userId, num);
        List<Long> itemIds = recommendedItems.stream().map(RecommendedItem::getItemID).collect(Collectors.toList());
        return itemIds;
    }

    /**
     * 创建数据模型
     *
     * @param userArticleOperations
     * @return
     */
    private DataModel createDataModel(List<UserArticleOperation> userArticleOperations) {
        //FastByIDMap是Mahout中的一个高效的基于整数键的Map实现。
        // 通常在协同过滤算法中使用，用于存储用户或物品的ID以及它们的相关信息（如评分、向量等）。
        //q:为什么要用FastByIDMap
        //a:因为数据量大，需要高效的存储
        FastByIDMap<PreferenceArray> fastByIdMap = new FastByIDMap<>();
        //q:为什么要用groupingBy
        //a:因为需要将相同用户的数据放到一起，这样才能构建出数据模型
        Map<Integer, List<UserArticleOperation>> map = userArticleOperations.stream()
                .collect(Collectors.groupingBy(UserArticleOperation::getUserId));
        //q:为什么要用values
        //a:因为groupingBy返回的是一个map，map的value才是我们需要的数据
        Collection<List<UserArticleOperation>> list = map.values();
        //q:为什么要用for循环
        //a:因为需要将List<UserArticleOperation>转换成GenericPreference[]
        for (List<UserArticleOperation> userPreferences : list) {
            GenericPreference[] array = new GenericPreference[userPreferences.size()];
            for (int i = 0; i < userPreferences.size(); i++) {
                UserArticleOperation userPreference = userPreferences.get(i);
                GenericPreference item = new GenericPreference(userPreference.getUserId(), userPreference.getArticleId(), userPreference.getValue());
                array[i] = item;
            }
            fastByIdMap.put(array[0].getUserID(), new GenericUserPreferenceArray(Arrays.asList(array)));
        }
        return new GenericDataModel(fastByIdMap);
    }

}
