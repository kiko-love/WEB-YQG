package com.yqg.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Administrator
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActionResource extends UploadResource{
    private User user;
    private String[] tagsArray;
}
