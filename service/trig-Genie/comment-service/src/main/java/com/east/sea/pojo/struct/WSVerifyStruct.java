package com.east.sea.pojo.struct;

import lombok.Data;

/**
 * websocket连接入参(公共)
 * @author tztang
 * @since 2025/04/02
 */
@Data
public abstract class WSVerifyStruct {

    //token
    private String token;

}
