package com.tianyu.caches;

import lombok.Data;

@Data
public class PushInfoCache {
    private String tableId;
    private String gameId;
    private long expireDt;
    private String msgJson;
}
