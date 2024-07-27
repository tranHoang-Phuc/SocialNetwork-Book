package com.phucth42.identity_service.repository.httpclient;

import com.phucth42.identity_service.dto.request.ExchangeTokenRequest;
import com.phucth42.identity_service.dto.response.ExchangeTokenResponse;
import feign.QueryMap;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name="outbound-identity" , url = "${oauth.uri.token}")
public interface OutboundIdentityClient {
    @PostMapping(value = "/token" , produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    ExchangeTokenResponse exchangeToken(@QueryMap ExchangeTokenRequest request);

}
