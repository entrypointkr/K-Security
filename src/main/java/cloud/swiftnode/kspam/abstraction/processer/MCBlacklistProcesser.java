package cloud.swiftnode.kspam.abstraction.processer;

import cloud.swiftnode.kspam.abstraction.RunnableProcesser;
import cloud.swiftnode.kspam.storage.StaticStorage;
import cloud.swiftnode.kspam.util.*;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by horyu on 2016-12-25.
 */
public class MCBlacklistProcesser extends RunnableProcesser {
    private PlayerJoinEvent event;

    public MCBlacklistProcesser(PlayerJoinEvent event) {
        this.event = event;
    }

    @Override
    public void process() {
        boolean blacklist = false;

        Player player = event.getPlayer();
        if (Material.getMaterial("DOUBLE_PLANT") != null) {
            if (checkWithType(player, player.getUniqueId().toString())) {
                blacklist = true;
            }
        }

        if (!blacklist && (checkWithType(player, player.getName()) || checkWithType(player, player.getAddress().getHostName()))) {
            blacklist = true;
        }

        if (blacklist) {
            if (!StaticStorage.getCachedMCBlacklistSet().contains(player)) {
                StaticStorage.getCachedMCBlacklistSet().add(player);
            }

            new DeleteDataProcesser(player, false).process();
            event.setJoinMessage(null);
        }
    }

    private boolean checkWithType(final Player player, String check) {
        try {
            String url = URLs.MCBLACKLIST_API.toString(check);
            String jsonText = getJSONText(url);
            if (jsonText == null) {
                Static.consoleMsg(Lang.PREFIX + Lang.EXCEPTION.toString("<MC-Blacklist API> JSON 문자열을 가져오는 중 오류가 발생하였습니다."));
                return false;
            }

            boolean error = jsonText.contains("\"error\":true");
            if (error) {
                String errorType = Static.substring(jsonText, "\"error_type\":\"", "\"");
                String errorMsg = decodeUnicode(Static.substring(jsonText, "\"error_msg\":\"", "\""));

                Static.consoleMsg(Lang.PREFIX + Lang.EXCEPTION.toString("<MC-Blacklist API> " + errorType + " - " + errorMsg));
                return false;
            }

            boolean blacklist = jsonText.contains("\"blacklist\":true");
            if (blacklist) {
                final String date = Static.substring(jsonText, "\"date\":\"", "\"");
                final String reason = decodeUnicode(Static.substring(jsonText, "\"reason\":\"", "\""));
                final String punisher = Static.substring(jsonText, "\"punisher\":\"", "\"");

                Static.runTask(new Runnable() {
                    @Override
                    public void run() {
                        player.kickPlayer(Lang.KICK.toString() + "\n\nAPI : MC-Blacklist (http://mc-blacklist.kr/inquire)\n처리된 날짜 : " + date + "\n사유 : " + reason + "\n처리자 : " + punisher);
                    }
                });

                Bukkit.broadcastMessage(Lang.PREFIX + Lang.KICKED.toString(player.getName(), Type.MCBLACKLIST));
                return true;
            }
        } catch (Exception ex) {
            Static.consoleMsg(Lang.PREFIX + Lang.EXCEPTION.toString("<MC-Blacklist API> " + ex.getMessage()));
        }

        return false;
    }

    private String getJSONText(String urlStr) {
        try {
            URLConnection url = new URL(urlStr).openConnection();
            url.setRequestProperty("User-Agent", "K-SPAM v" + StaticStorage.getVersionStorage().getCurrVer());

            StringBuilder sb = new StringBuilder();
            InputStream in = url.getInputStream();

            byte[] data = new byte[1024];
            int size;
            while ((size = in.read(data)) != -1) {
                sb.append(new String(data, 0, size));
            }

            return sb.toString();
        } catch (Exception ex) {
            // Ignore
        }
        return null;
    }

    public String decodeUnicode(String unicode) throws Exception {
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = unicode.indexOf("\\u"); i > -1; i = unicode.indexOf("\\u")) {
            char ch = (char) Integer.parseInt(unicode.substring(i + 2, i + 6), 16);
            stringBuilder.append(unicode.substring(0, i));
            stringBuilder.append(String.valueOf(ch));
            unicode = unicode.substring(i + 6);
        }

        stringBuilder.append(unicode);

        return stringBuilder.toString();
    }
}