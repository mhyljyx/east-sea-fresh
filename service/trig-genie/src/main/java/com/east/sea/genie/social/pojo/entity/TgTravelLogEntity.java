package com.east.sea.genie.social.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.east.sea.pojo.entity.BaseTablePropertyEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 游记/动态实体
 *
 * @author TraeAI
 * @since 2026-03-03
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("tg_travel_log")
public class TgTravelLogEntity extends BaseTablePropertyEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 发布者ID
     */
    private Long userId;

    /**
     * 文本内容
     */
    private String content;

    /**
     * 图片/视频地址列表 (JSON格式)
     */
    private String mediaUrls;

    /**
     * 定位信息
     */
    private String location;
}
