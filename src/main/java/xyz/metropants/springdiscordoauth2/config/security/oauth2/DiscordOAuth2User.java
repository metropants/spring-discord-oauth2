package xyz.metropants.springdiscordoauth2.config.security.oauth2;

import com.fasterxml.jackson.annotation.JsonIgnore;
import net.dv8tion.jda.api.Permission;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;
import xyz.metropants.springdiscordoauth2.payload.response.DiscordGuildsResponse;
import xyz.metropants.springdiscordoauth2.util.DiscordAvatarUtils;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public record DiscordOAuth2User(Map<String, Object> attributes, Set<DiscordGuildsResponse> guilds) implements OAuth2User {

    public String getId() {
        return (String) this.attributes.get("id");
    }

    public String getUsername() {
        return (String) this.attributes.get("username");
    }

    public String getAvatar() {
        String avatar = (String) this.attributes.get("avatar");
        return DiscordAvatarUtils.resolveUserAvatarUrl(avatar, this.getId());
    }

    public String getDiscriminator() {
        return (String) this.attributes.get("discriminator");
    }

    public Set<DiscordGuildsResponse> getGuilds() {
        return this.guilds.stream()
                .filter(guild -> guild.isOwner() || Permission.getPermissions(guild.getPermissions()).contains(Permission.ADMINISTRATOR))
                .collect(Collectors.toSet());
    }

    @Override
    @JsonIgnore
    public Map<String, Object> getAttributes() {
        return this.attributes;
    }

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("USER"));
    }

    @Override
    @JsonIgnore
    public String getName() {
        return (String) this.attributes.get("username");
    }

}
