package com.catalog.vo;

import lombok.Data;

/**
 * @Copyright: Shanghai Definesys Company.All rights reserved.
 * @Description:
 * @Author: miaowei
 * @Since: 2023/03/27
 */
@Data
public class DiscoverTableVoRequest {

   private String id;
    private String tableComment;
    private String tableCommentEng;
    private String category;
    private String updateFrequency;
}
