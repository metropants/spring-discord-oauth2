package xyz.metropants.springdiscordoauth2.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import xyz.metropants.springdiscordoauth2.util.DiscordAvatarUtils;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiscordGuildsResponse {

    private long id;
    private String name;
    private String icon;
    private boolean owner;
    private long permissions;

    public String getIcon() {
        if (icon == null) {
            return null;
        }

        return DiscordAvatarUtils.resolveGuildIconUrl(icon, String.valueOf(id));
    }

}
