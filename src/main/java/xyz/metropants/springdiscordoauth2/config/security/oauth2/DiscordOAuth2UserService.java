package xyz.metropants.springdiscordoauth2.config.security.oauth2;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import xyz.metropants.springdiscordoauth2.payload.response.DiscordGuildsResponse;

import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class DiscordOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private static final String DISCORD_API_URL = "https://discord.com/api/v10";

    private final RestTemplate template;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest request) throws OAuth2AuthenticationException {
        String userInfoUrl = request.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUri();

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", request.getAccessToken().getTokenValue()));

        ParameterizedTypeReference<Map<String, Object>> userReference = new ParameterizedTypeReference<>() {};
        ResponseEntity<Map<String, Object>> userExchange = this.template.exchange(userInfoUrl, HttpMethod.GET, new HttpEntity<>(headers), userReference);
        Map<String, Object> attributes = userExchange.getBody();

        ParameterizedTypeReference<Set<DiscordGuildsResponse>> guildsReference = new ParameterizedTypeReference<>() {};
        ResponseEntity<Set<DiscordGuildsResponse>> guildsExchange = this.template.exchange(DISCORD_API_URL + "/users/@me/guilds", HttpMethod.GET, new HttpEntity<>(headers), guildsReference);
        Set<DiscordGuildsResponse> guilds = guildsExchange.getBody();

        return new DiscordOAuth2User(attributes, guilds);
    }

}
