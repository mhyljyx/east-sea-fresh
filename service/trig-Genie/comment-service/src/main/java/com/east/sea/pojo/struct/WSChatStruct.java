package com.east.sea.pojo.struct;

import lombok.Data;

/**
 * websocket连接入参
 * @author tztang
 * @since 2025/04/02
 */
@Data
public class WSChatStruct extends WSVerifyStruct {

    //标志
    private String id;

    //类型
    private String type;

}
