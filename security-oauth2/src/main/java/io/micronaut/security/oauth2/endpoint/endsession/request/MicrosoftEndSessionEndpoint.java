/*
 * Copyright 2017-2023 original authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.micronaut.security.oauth2.endpoint.endsession.request;

import io.micronaut.core.annotation.Internal;
import io.micronaut.http.HttpRequest;
import io.micronaut.security.authentication.Authentication;
import io.micronaut.security.oauth2.client.OpenIdProviderMetadata;
import io.micronaut.security.oauth2.configuration.OauthClientConfiguration;
import io.micronaut.security.oauth2.endpoint.endsession.response.EndSessionCallbackUrlBuilder;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * An {@link EndSessionEndpoint} which redirects to the Microsoft end session endpoint obtained via the OpenID Connect Configuration.
 * It adds two query value parameters {@code post_logout_redirect_uri} and {@code logout_hint}.
 * {@code post_logout_redirect_uri} The URL that the user is redirected to after successfully signing out. If the parameter isn't included, the user is shown a generic message that's generated by the Microsoft identity platform. This URL must match one of the redirect URIs registered for your application in the app registration portal.
 * {@code logout_hint} Enables sign-out to occur without prompting the user to select an account. To use logout_hint, enable the login_hint optional claim in your client application and use the value of the login_hint optional claim as the logout_hint parameter.
 * <a href="https://learn.microsoft.com/en-us/entra/identity-platform/v2-protocols-oidc#send-a-sign-out-request">Send a sign-out request</a>
 */
@Internal
final class MicrosoftEndSessionEndpoint extends AbstractEndSessionRequest {
    private static final String PARAM_POST_LOGOUT_REDIRECT_URI = "post_logout_redirect_uri";
    private static final String CLAIM_LOGIN_HINT = "login_hint";
    private static final String CLAIM_LOGOUT_HINT = "logout_hint";

    /**
     * @param endSessionCallbackUrlBuilder The end session callback URL builder
     * @param clientConfiguration The client configuration
     * @param providerMetadata The provider metadata supplier
     */
    MicrosoftEndSessionEndpoint(EndSessionCallbackUrlBuilder endSessionCallbackUrlBuilder,
                                  OauthClientConfiguration clientConfiguration,
                                  Supplier<OpenIdProviderMetadata> providerMetadata) {
        super(endSessionCallbackUrlBuilder, clientConfiguration, providerMetadata);
    }

    @Override
    protected String getUrl() {
        return providerMetadataSupplier.get().getEndSessionEndpoint();
    }

    @Override
    protected Map<String, Object> getArguments(HttpRequest<?> originating,
                                               Authentication authentication) {
        Map<String, Object> arguments = new HashMap<>();
        arguments.put(PARAM_POST_LOGOUT_REDIRECT_URI, getRedirectUri(originating));
        Object loginHint = authentication.getAttributes().get(CLAIM_LOGIN_HINT);
        if (loginHint != null) {
            arguments.put(CLAIM_LOGOUT_HINT, loginHint);
        }
        return arguments;
    }
}


