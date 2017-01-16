package cloud.swiftnode.kspam.abstraction.checker;

import cloud.swiftnode.kspam.abstraction.Info;
import cloud.swiftnode.kspam.abstraction.SpamChecker;
import cloud.swiftnode.kspam.util.Lang;
import cloud.swiftnode.kspam.util.Static;
import cloud.swiftnode.kspam.util.URLs;
import org.bukkit.Bukkit;

import java.net.URL;

/**
 * Created by Junhyeong Lim on 2017-01-11.
 */
public class MCBlacklistChecker extends SpamChecker {
    private String lastReason;

    public MCBlacklistChecker(Info info) {
        super(info);
    }

    @Override
    public Result spamCheck() throws Exception {
        lastReason = "없음";
        String uuid = null;

        if (Bukkit.getOnlineMode()) {
            try {
                uuid = info.getUniqueId();
            } catch (IllegalStateException e) {
                // UUID 메서드 호출에 문제가 있을 경우 무시
            }
        } else {
            // 오프라인 서버일 경우 Mojang API 에서 가져옴
            uuid = getUUIDFromMojangAPI(info.getName());
        }

        // uuid 가 null 이 아닐 경우 MCBlacklist 에서 UUID 확인
        if (uuid != null) {
            String[] checkUUIDResult = checkWithType(uuid);
            if (checkUUIDResult[0].equals("DENY")) {
                lastReason = checkUUIDResult[2];
                return Result.DENY;
            } else if (checkUUIDResult[0].equals("ERROR")) {
                return Result.ERROR;
            }
        }

        // uuid 로 확인할 수 없을 경우 MCBlacklist 에서 닉네임과 IP 확인
        String[] checkNameResult = checkWithType(info.getName());
        String[] checkIPResult = checkWithType(info.getIp());
        // 체크 결과 확인
        if (checkNameResult[0].equals("DENY") || checkIPResult[0].equals("DENY")) {
            lastReason = checkNameResult[0].equals("DENY") ? checkNameResult[2] : checkIPResult[2];
            return Result.DENY;
        } else if (checkNameResult[0].equals("PASS") && checkIPResult[0].equals("PASS")) {
            return Result.PASS;
        } else if (checkNameResult[0].equals("ERROR") || checkIPResult[0].equals("ERROR")) {
            // 예외 처리는 BaseSpamExecutor 에서 되기 때문에 예외 throw
            throw new RuntimeException("<MC-Blacklist API> " + (checkNameResult[0].equals("ERROR") ? checkNameResult[1] : checkIPResult[1]));
        }
        return Result.ERROR;
    }

    @Override
    public String denyMsg() {
        return lastReason;
    }

    private String[] checkWithType(String check) {
        String[] result = new String[4];
        try {
            URL url = URLs.MCBLACKLIST_API.toUrl(lastInfo = check);
            String jsonText = Static.readAllText(url);

            boolean error = jsonText.contains("\"error\":true");
            if (error) {
                String errorType = Static.substring(jsonText, "\"error_type\":\"", "\"");
                String errorMsg = decodeUnicode(Static.substring(jsonText, "\"error_msg\":\"", "\""));

                result[0] = "ERROR";
                result[1] = errorType + " - " + errorMsg;

                Static.consoleMsg(Lang.EXCEPTION.builder().single(Lang.Key.EXCEPTION_MESSAGE, "<MC-Blacklist API> " + result[1]).prefix().build());
                return result;
            }

            boolean blacklist = jsonText.contains("\"blacklist\":true");
            if (blacklist) {
                final String date = Static.substring(jsonText, "\"date\":\"", "\"");
                final String reason = decodeUnicode(Static.substring(jsonText, "\"reason\":\"", "\""));
                final String punisher = Static.substring(jsonText, "\"punisher\":\"", "\"");

                result[0] = "DENY";
                result[1] = date;
                result[2] = reason;
                result[3] = punisher;

                return result;
            }
        } catch (Exception ex) {
            result[0] = "ERROR";
            result[1] = ex.getMessage();
            return result;
        }

        result[0] = "PASS";
        return result;
    }

    private String getUUIDFromMojangAPI(String nickName) {
        try {
            URL url = URLs.MOJANG_UUID_API.toUrl(nickName, (System.currentTimeMillis() / 1000) + "");
            String jsonText = Static.readAllText(url);

            if (!jsonText.contains("\"id\":")) {
                return null;
            }

            String uuidWithNoDashes = Static.substring(jsonText, "\"id\":\"", "\"");
            return String.format("%s-%s-%s-%s-%s", uuidWithNoDashes.substring(0, 8), uuidWithNoDashes.substring(8, 12), uuidWithNoDashes.substring(12, 16), uuidWithNoDashes.substring(16, 20), uuidWithNoDashes.substring(20, 32));
        } catch (Exception ex) {
            return null;
        }
    }

    private String decodeUnicode(String unicode) throws Exception {
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