package fpt.phucth42.apigateway.repository.httpclient;


import fpt.phucth42.apigateway.dto.request.IntrospectRequest;
import fpt.phucth42.apigateway.dto.response.ApiResponse;
import fpt.phucth42.apigateway.dto.response.IntrospectResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.PostExchange;
import reactor.core.publisher.Mono;

public interface IdentityClient {
    @PostExchange(value = "/auth/introspect", contentType = MediaType.APPLICATION_JSON_VALUE)
    Mono<ApiResponse<IntrospectResponse>> introspectToken(@RequestBody IntrospectRequest request);
}
