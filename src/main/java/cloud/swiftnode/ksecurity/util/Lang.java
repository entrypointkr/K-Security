package cloud.swiftnode.ksecurity.util;

import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;

/**
 * Created by EntryPoint on 2017-01-05.
 */
public enum Lang {
    PREFIX("&a[ K-Security ] &f"),
    ERROR("체커 " + Key.CHECKER_NAME + " 에서 에러가 발생했습니다."),
    DEBUG("&f프로세서 &e" + Key.PROCESSOR_NAME + " &f실행자 &e" + Key.EXECUTOR_NAME + " &f체커 &e" + Key.CHECKER_NAME + " &f정보 &e" + Key.INFO + " &f결과 &e" + Key.CHECKER_RESULT + " &f소요 시간 &e" + Key.TIME),
    DENY(PREFIX + "\n" +
            "&f당신은 K-SPAM 에 의해 차단되었습니다."),
    CONTRIBUTORS("EntryPoint, Horyu1234, ldmsys, WhiteSwan"),
    CACHE_COUNT("&f캐쉬된 데이터: &e" + Key.CACHE_COUNT + " &f개"),
    INTRO("\n" +
            "                                                                   ,,                   \n" +
            "`7MMF' `YMM'      .M\"\"\"bgd                                         db   mm              \n" +
            "  MM   .M'       ,MI    \"Y                                              MM              \n" +
            "  MM .d\"         `MMb.      .gP\"Ya   ,p6\"bo `7MM  `7MM  `7Mb,od8 `7MM mmMMmm `7M'   `MF'\n" +
            "  MMMMM.           `YMMNq. ,M'   Yb 6M'  OO   MM    MM    MM' \"'   MM   MM     VA   ,V  \n" +
            "  MM  VMA        .     `MM 8M\"\"\"\"\"\" 8M        MM    MM    MM       MM   MM      VA ,V   \n" +
            "  MM   `MM.      Mb     dM YM.    , YM.    ,  MM    MM    MM       MM   MM       VVV    \n" +
            ".JMML.   MMb.    P\"Ybmmd\"   `Mbmmd'  YMbmd'   `Mbod\"YML..JMML.   .JMML. `Mbmo    ,V     \n" +
            "                                                                                ,V      \n" +
            "                                                                             OOb\"       \n" +
            "" + PREFIX + "&fv" + Key.KSEC_VERSION + "\n\n" +
            "&fPlugin Contributors &e" + CONTRIBUTORS + "\n" +
            "&fK-SPAM Module DB Powered By &eSwiftnode\n" +
            "\n" +
            Key.MODULES_INFO +
            "\n" +
            "\n" +
            "&f버그 제보/건의 &ehttps://github.com/EntryPointKR/K-Security/issues\n"),
    UPDATE_INFO_NEW("새 버전이 있습니다."),
    NEW_VERSION("&e최신버전: &f" + Key.NEW_VERSION),
    CURRENT_VERSION("&e현재버전: &f" + Key.KSEC_VERSION),
    DOWNLOAD_URL("&e다운로드: &bhttps://github.com/EntryPointKR/K-Security/releases/latest"),
    EXCEPTION("예외가 발생했습니다. 개발자에게 문의해주세요. &bhttps://github.com/EntryPointKR/K-Security/issues &f본 메세지를 끄려면 /ksecurity alert 를 입력해주세요. &e정보: &f" + Key.EXCEPTION_MESSAGE),
    SET("&e" + Key.VALUE + " &f로 설정했습니다."),
    COMMAND_CHECK("&e" + Key.VALUE + " &f의 결과"),
    FIRST_LOGIN_KICK(PREFIX + "\n" +
            "&f30 초 안에 다시 접속해주세요."),
    INVALID_IP("형식에 맞지 않는 IP 입니다."),
    FORCEMODE_ON("강제 모드가 활성화되어 당신은 차단되었습니다. 서버 관리자에게 문의해주세요."),
    PLUGIN_INTRO("&f본 서버는 보안 플러그인 &eK-Security &f를 사용 중입니다."),
    /**
     * 본 프로젝트에 기여했을 경우 밑 메세지에 자신의 닉네임을 추가할 수 있습니다.
     */
    LAW_INFO(" " + PLUGIN_INTRO + "\n" +
            " &f기여자: §e" + CONTRIBUTORS + "\n" +
            " &fhttps://github.com/EntryPointKR/K-Security\n"),
    SMALL_CACHE("캐시 데이터의 수가 적습니다. 데이터의 수가 적으면 성능이 떨어져 의도한 것이 아니라면 &eplugins/K-Security/K-Spam.cache &f파일을 지운 후 리부팅해주세요."),
    SELF_DEFENCE("&c" + Key.PLUGIN_NAME + " 플러그인의 K-Security 비활성화 시도를 차단했습니다."),
    SOCKET_DETECTED("&e" + Key.PLUGIN_NAME + " &f에서 &c소켓 코드&f가 감지되었습니다."),
    SCAN_RESULT("총 &e" + Key.PLUGIN_COUNT + " &f개의 플러그인 검사 완료 &e(" + Key.TIME + "ms)&f, 의심 플러그인 수: " + Key.FIND_COUNT),
    SCAN_WARNING("소켓 코드가 감지된 플러그인들을 정상적인 경로(bukkit.org, spigotmc.org) 에서 다운로드 받았는지 확인하십시오."),
    SCAN_SAFE("위협 요소가 감지되지 않았습니다."),
    SCAN_START("플러그인 간편 검사가 시작됩니다."),
    TRY_NETWORKING("&e" + Key.PLUGIN_NAME + " &7> &f" + Key.VALUE),
    LEGACY_VERSION_DETECT("&c옛 버전의 K-Spam 플러그인 활성화가 감지되어 이를 차단했습니다. 기존 K-Spam 플러그인을 지워주십시오."),
    CMD_USAGE("&6/ks info: &f플러그인의 정보를 봅니다.\n" +
            "&6/ks firewall: &f방화벽 모드를 켭니다. 방화벽 모드 활성화 중엔 처음 들어오는 모든 유저를 추방합니다.\n" +
            "&6/ks check (아이피/닉네임): &f해당 아이피나 닉네임의 플레이어의 스팸 여부를 확인합니다\n" +
            "&6/ks debug: &f개발자용 디버그 모드를 켭니다.\n" +
            "&6/ks firstkick: &f처음 들어오는 유저를 추방합니다. (기본값: 활성화)\n" +
            "&6/ks remove (아이피): &f해당 아이피를 캐쉬 목록에서 제거합니다.\n" +
            "&6/ks netalert: &f네트워크 모니터링을 끕니다.\n" +
            "&6/ks reload: &f설정 파일을 다시 불러옵니다.\n" +
            "&6/ks addop (닉네임): &fOP 허용 목록에 닉네임을 추가합니다.\n" +
            "&6/ks remop (닉네임): &fOP 허용 목록에서 닉네임을 제거합니다.\n" +
            "&6/ks listop: &fOP 허용 목록을 보여줍니다.\n" +
            "&6/ks clearop: &fOP 허용 목록을 초기화합니다."),
    MODULES_INFO(Static.getModulesInfo(false)),
    DEOP("&c비인가 OP 감지 &7> &f" + Key.VALUE),
    CONFIG_RELOADED("설정 파일을 다시 불러왔습니다."),
    ENABLE_MODULE("모듈 &e" + Key.VALUE + " &f활성화"),
    DISABLE_MODULE("모듈 &e" + Key.VALUE + " &f비활성화"),
    LOAD_MODULE("모듈 &e" + Key.VALUE + " &f로드"),
    ADD_OP("닉네임 &e" + Key.VALUE + " &f을(를) &eOP 허용 목록&f에 추가했습니다."),
    REM_OP("닉네임 &e" + Key.VALUE + " &f을(를) &eOP 허용 목록&f에서 제거했습니다."),
    OP_LIST("&eOP 허용 목록: &f" + Key.VALUE),
    FAIL("처리에 실패했습니다."),
    SUCCESS("처리에 성공했습니다."),
    SHVACCINE_DETECT("SHVaccine 플러그인 활성화가 감지되었습니다. K-Security 와 충돌해 문제가 생길 수 있습니다."),
    DAMAGE_DETECT("&cK-Security 가 변조되었습니다.\n" +
            "서버를 종료하시려면 예를, 유지하려면 아니오를 눌러주십시오.\n" +
            "종료 시 플레이어와 월드 데이터를 모두 저장합니다.\n" +
            "유지 시 변조된 객체를 다시 복구시킵니다."),
    DAMAGE_EXCEPTION_DETECT("&cK-Security 의 변조 여부를 확인 중에 예외가 발생했습니다.\n" +
            "내용: " + Key.VALUE + "\n" +
            "외부에서 변조를 시도했을 수 있습니다.\n" +
            "서버를 종료하시려면 예를, 유지하려면 아니오를 눌러주십시오.\n" +
            "종료 시 플레이어와 월드 데이터를 모두 저장합니다.\n" +
            "유지 시 변조된 객체를 다시 복구시킵니다."),
    RPGITEM_DETECT("&cRPGItems &f감지"),
    PV_DETECT("&cPlayerVaults &f감지"),
    EMERGENCY_ENABLE("임시 객체 긴급 활성화"),
    USE_CHEAT("&c" + Key.PLAYER_NAME + " &f이(가) &c" + Key.VALUE + " &f취약점 사용"),
    ;
    private final String target;

    Lang(String msg) {
        this.target = msg;
    }

    @Override
    public String toString() {
        return colorize(target);
    }

    public MessageBuilder builder() {
        return new MessageBuilder(target);
    }

    public static String colorize(String str) {
        return ChatColor.translateAlternateColorCodes('&', str);
    }

    public enum Key {
        CHECKER_NAME("checker-name"),
        PROCESSOR_NAME("processor-name"),
        CHECKER_RESULT("checker-result"),
        CACHE_COUNT("cache-count"),
        KSEC_VERSION("ksecurity-version"),
        NEW_VERSION("new-version"),
        TIME("time"),
        EXCEPTION_MESSAGE("exception-message"),
        VALUE("value"),
        EXECUTOR_NAME("executor-name"),
        INFO("info"),
        PLUGIN_NAME("plugin-name"),
        FIND_COUNT("find-count"),
        PLUGIN_COUNT("plugin-count"),
        MODULES_INFO("modules-info"),
        PLAYER_NAME("player-name"),
        ;
        private final String key;

        Key(String key) {
            this.key = key;
        }

        @Override
        public String toString() {
            return "%" + key + "%";
        }
    }

    public class MessageBuilder {
        private List<Key> keyList = new ArrayList<>();
        private List<Object> valList = new ArrayList<>();
        private String target;

        MessageBuilder(String target) {
            this.target = target;
        }

        public MessageBuilder addKey(Lang.Key... keys) {
            keyList.addAll(Arrays.asList(keys));
            return this;
        }

        public MessageBuilder addVal(Object... vals) {
            valList.addAll(Arrays.asList(vals));
            return this;
        }

        public MessageBuilder single(Lang.Key key, Object val) {
            keyList.add(key);
            valList.add(val);
            return this;
        }

        public MessageBuilder prefix(String prefix) {
            target = prefix + target;
            return this;
        }

        public MessageBuilder prefix() {
            prefix(Lang.PREFIX.toString());
            return this;
        }

        public MessageBuilder addSpace(int how) {
            String space = "";
            for (int i = 0; i < how; i++) {
                space += " ";
            }
            String[] strs = target.split("\n");
            for (int i = 0; i < strs.length; i++) {
                String str = strs[i];
                strs[i] = space + str;
            }
            target = StringUtils.join(strs, "\n");
            return this;
        }

        public String flatBuild() {
            return ChatColor.stripColor(build(false));
        }

        public String build(boolean prefix, int space) {
            if (prefix) {
                prefix();
            }
            for (int i = 0; i < keyList.size(); i++) {
                try {
                    String key = String.valueOf(keyList.get(i));
                    String val = String.valueOf(valList.get(i));
                    if (key == null || val == null) {
                        break;
                    }
                    target = target.replaceAll(key, Matcher.quoteReplacement(val));
                } catch (Exception ex) {
                    throw new RuntimeException(keyList + ", " + valList);
                }
            }
            if (space > 0) {
                addSpace(space);
            }
            return colorize(target);
        }

        public String build(boolean prefix) {
            return build(prefix, 0);
        }

        public String build() {
            return build(true, 0);
        }

        @Override
        public MessageBuilder clone() {
            return new MessageBuilder(target);
        }
    }
}
