package com.east.sea.genie.social.pojo.dto;

import com.east.sea.pojo.dto.PageDTO;
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
public class TgSocialFeedDTO extends PageDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "发布者ID", example = "10086")
    private Long userId;

}
