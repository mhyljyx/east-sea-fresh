package com.east.sea.genie.social.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;

/**
 * 游记发布DTO
 *
 * @author TraeAI
 * @since 2026-03-03
 */
@Data
@ApiModel(value = "游记发布参数", description = "游记发布DTO")
public class TgTravelLogDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotBlank(message = "内容不能为空")
    @ApiModelProperty(value = "文本内容", required = true, example = "今天天气真好，去西湖玩了一圈！")
    private String content;

    @ApiModelProperty(value = "图片/视频地址列表", example = "[\"http://oss.com/1.jpg\", \"http://oss.com/2.mp4\"]")
    private List<String> mediaUrls;

    @ApiModelProperty(value = "定位信息", example = "浙江省杭州市西湖区")
    private String location;
}
