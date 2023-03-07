package com.example.boilerplate.global.redis;

import io.lettuce.core.RedisChannelHandler;
import io.lettuce.core.RedisConnectionStateListener;
import java.net.SocketAddress;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class RedisClient {

    private final LettuceConnectionFactory lettuceConnectionFactory;

    @PostConstruct
    public void setHealthCheck() {
        lettuceConnectionFactory.getRequiredNativeClient()
                                .addListener(new RedisConnectionStateListener() {
                                    @Override
                                    public void onRedisConnected(RedisChannelHandler<?, ?> connection,
                                        SocketAddress socketAddress) {
                                        RedisConnectionStateListener.super.onRedisConnected(connection,
                                            socketAddress);
                                        log.info("Redis 연결 성공");
                                    }

                                    @Override
                                    public void onRedisDisconnected(RedisChannelHandler<?, ?> connection) {
                                        log.error("Redis 연결 끊김");
                                    }

                                    @Override
                                    public void onRedisExceptionCaught(RedisChannelHandler<?, ?> connection,
                                        Throwable cause) {
                                        log.error("Redis 에러 발생");
                                    }
                                });
    }
}
