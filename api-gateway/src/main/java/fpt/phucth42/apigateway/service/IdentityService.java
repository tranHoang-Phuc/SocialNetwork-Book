package fpt.phucth42.apigateway.service;

import fpt.phucth42.apigateway.dto.request.IntrospectRequest;
import fpt.phucth42.apigateway.dto.response.ApiResponse;
import fpt.phucth42.apigateway.dto.response.IntrospectResponse;
import fpt.phucth42.apigateway.repository.httpclient.IdentityClient;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class IdentityService {
    IdentityClient identityClient;

    public Mono<ApiResponse<IntrospectResponse>> introspectToken(String token) {
        return identityClient.introspectToken(
                IntrospectRequest.builder()
                        .token(token)
                        .build());
    }
}
