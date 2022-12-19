package xyz.metropants.springdiscordoauth2.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class DiscordAvatarUtils {

    private static final String DISCORD_CDN_URL = "https://cdn.discordapp.com";

    public static String resolveUserAvatarUrl(String avatar, String userId) {
        if (avatar == null) {
            return null;
        }

        if (avatar.startsWith("a_")) {
            return String.format("%s/avatars/%s/%s.gif", DISCORD_CDN_URL, userId, avatar);
        } else {
            return String.format("%s/avatars/%s/%s.png", DISCORD_CDN_URL, userId, avatar);
        }
    }

    public static String resolveGuildIconUrl(String icon, String guildId) {
        if (icon == null) {
            return null;
        }

        if (icon.startsWith("a_")) {
            return String.format("%s/icons/%s/%s.gif", DISCORD_CDN_URL, guildId, icon);
        } else {
            return String.format("%s/icons/%s/%s.png", DISCORD_CDN_URL, guildId, icon);
        }
    }

}
