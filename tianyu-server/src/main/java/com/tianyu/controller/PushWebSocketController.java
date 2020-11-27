package com.tianyu.controller;

import com.alibaba.fastjson.JSON;
import com.tianyu.caches.CachesSocket;
import com.tianyu.caches.PushInfoCache;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.websocket.Session;
import java.io.IOException;

/**
 * @author luobing
 */
@Slf4j
@RestController
@RequestMapping("/pushWebSocket")
public class PushWebSocketController {

	/**
	 * 推送
	 *
	 * @param param
	 * @return
	 */
	@PostMapping("/pushMessage")
	public void pushMessage(@Valid @RequestBody Params.PushMessageInput param) throws IOException {
		log.debug("==> param: {}", param);
		final String gameId = param.getGameId();
		final String tableId = param.getTableId();
		final long expireDt = param.getExpireDt();
		final String msgJson = JSON.toJSONString(param.getMsgData());
		final String token = param.getToken();

		final Session session = WebSocketController.sessionPool.get(token);

		log.info("==> PushWebSocketController::pushMessage() param: {}, msgData: {}", param, msgJson);
		session.getAsyncRemote().sendText(msgJson);

		//发送记录缓存起来
		PushInfoCache pushInfoCache = new PushInfoCache();
		pushInfoCache.setGameId(gameId);
		pushInfoCache.setTableId(tableId);
		pushInfoCache.setExpireDt(System.currentTimeMillis() + expireDt);
		pushInfoCache.setMsgJson(msgJson);
		CachesSocket.setPushInfoCache(token, pushInfoCache);
	}

	@Data
	public static class Params {
		@Data
		public static class PushMessageInput {
			@NotBlank
			@ApiModelProperty(value = "websocketToken")
			private String token;

			@NotBlank
			@ApiModelProperty(value = "桌子ID")
			private String tableId;


			@NotBlank
			@ApiModelProperty(value = "游戏ID")
			private String gameId;

			@NotNull
			@ApiModelProperty(value = "推送消息")
			private Object msgData;

			@ApiModelProperty(value = "客户端未连接上时,服务器保存多久,等客户端连接后立刻推送(单位毫秒默认20秒)")
			private long expireDt = 20 * 1000;
		}
	}
}
