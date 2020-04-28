package com.akat.quiz.services.impl;

import com.akat.quiz.services.interfaces.IMicroserviceService;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.netty.tcp.TcpClient;

import java.util.concurrent.TimeUnit;

@Service
public class MicroserviceService implements IMicroserviceService {
    @Autowired
    WebClient.Builder webClientBuilder;

    @Override
    public <T> T makeWebClientRequest(String url, Class<T> responseType) {

        /**
         * RestTemplate is deprecated, so use WebClient it has tons of settings
         * https://www.baeldung.com/spring-5-webclient
         * for async example goes to
         * https://www.baeldung.com/spring-webclient-resttemplate
         * */
        TcpClient tcpClient = TcpClient
                .create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                .doOnConnected(connection -> {
                    connection.addHandlerLast(new ReadTimeoutHandler(5000, TimeUnit.MILLISECONDS));
                    connection.addHandlerLast(new WriteTimeoutHandler(5000, TimeUnit.MILLISECONDS));
                });

        WebClient webClient = webClientBuilder
                .clientConnector(new ReactorClientHttpConnector(HttpClient.from(tcpClient)))
                .build();

        return webClient
                .get()
                .uri(url)
                .retrieve()
                .bodyToMono(responseType)
                .block();//make request synchronous
    }
}
