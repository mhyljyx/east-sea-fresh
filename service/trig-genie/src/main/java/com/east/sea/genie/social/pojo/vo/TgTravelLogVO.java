package com.east.sea.genie.social.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 游记详情VO
 *
 * @author TraeAI
 * @since 2026-03-03
 */
@Data
@ApiModel(value = "游记详情", description = "游记详情VO")
public class TgTravelLogVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键ID", example = "1")
    private Long id;

    @ApiModelProperty(value = "发布者ID", example = "10086")
    private Long userId;
    
    @ApiModelProperty(value = "发布者昵称", example = "旅行达人")
    private String userName;
    
    @ApiModelProperty(value = "发布者头像", example = "http://oss.com/avatar.jpg")
    private String userAvatar;

    @ApiModelProperty(value = "文本内容", example = "今天天气真好...")
    private String content;

    @ApiModelProperty(value = "图片/视频地址列表", example = "[\"http://oss.com/1.jpg\"]")
    private List<String> mediaUrls;

    @ApiModelProperty(value = "定位信息", example = "杭州")
    private String location;
    
    @ApiModelProperty(value = "点赞数", example = "100")
    private Integer likeCount;
    
    @ApiModelProperty(value = "是否已点赞", example = "true")
    private Boolean isLiked;
    
    @ApiModelProperty(value = "创建时间", example = "2026-03-03 12:00:00")
    private Date createTime;
}
