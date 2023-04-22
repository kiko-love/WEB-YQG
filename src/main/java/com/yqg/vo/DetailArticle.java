package com.yqg.vo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author KIKO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetailArticle extends Article{
    User user;
    String[] tagsArray;
    String detailContent;
}
