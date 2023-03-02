package com.densoft.reactiveelasticquerywebclient.config;

import com.densoft.appconfigdata.config.ElasticQueryWebClientConfigData;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.netty.tcp.TcpClient;

@Configuration
public class WebClientConfig {
    private final ElasticQueryWebClientConfigData.WebClient webClient;

    public WebClientConfig(ElasticQueryWebClientConfigData elasticQueryWebClientConfigData) {
        this.webClient = elasticQueryWebClientConfigData.getWebClient();
    }

    @Bean
    WebClient webClient() {
        return WebClient.builder()
                .baseUrl(webClient.getBaseUrl())
                .defaultHeader(HttpHeaders.CONTENT_TYPE, webClient.getContentType())
                .clientConnector(new ReactorClientHttpConnector(HttpClient.from(getTcpClient())))
                .build();
    }

    private TcpClient getTcpClient() {
        return TcpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, webClient.getConnectTimeoutMs())
                .doOnConnected(connection -> {
                    connection.addHandlerLast(new ReadTimeoutHandler(webClient.getReadTimeoutMs()));
                    connection.addHandlerLast(new WriteTimeoutHandler(webClient.getWriteTimeoutMs()));
                });
    }

}
