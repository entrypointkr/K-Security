package cloud.swiftnode.kvaccine.util;

import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;

/**
 * Created by EntryPoint on 2017-01-05.
 */
public enum Lang {
    PREFIX("&a[ K-Vaccine ] &f"),
    ERROR("체커 " + Key.CHECKER_NAME + " 에서 에러가 발생했습니다."),
    DEBUG("&f프로세서 &e" + Key.PROCESSOR_NAME + " &f실행자 &e" + Key.EXECUTOR_NAME + " &f체커 &e" + Key.CHECKER_NAME + " &f정보 &e" + Key.INFO + " &f결과 &e" + Key.CHECKER_RESULT + " &f소요 시간 &e" + Key.TIME),
    DENY("&a[ K-Vaccine ]\n" +
            "&f당신은 K-Vaccine 에 의해 차단되었습니다."),
    CACHE_COUNT("&f캐쉬된 데이터: &e" + Key.CACHE_COUNT + " &f개"),
    INTRO("\n" +
            "                                                                               \n" +
            "`7MMF' `YMM'     `7MMF'   `7MF'                        db                      \n" +
            "  MM   .M'         `MA     ,V                                                  \n" +
            "  MM .d\"            VM:   ,V ,6\"Yb.  ,p6\"bo   ,p6\"bo `7MM  `7MMpMMMb.  .gP\"Ya  \n" +
            "  MMMMM.             MM.  M'8)   MM 6M'  OO  6M'  OO   MM    MM    MM ,M'   Yb \n" +
            "  MM  VMA            `MM A'  ,pm9MM 8M       8M        MM    MM    MM 8M\"\"\"\"\"\" \n" +
            "  MM   `MM.           :MM;  8M   MM YM.    , YM.    ,  MM    MM    MM YM.    , \n" +
            ".JMML.   MMb.          VF   `Moo9^Yo.YMbmd'   YMbmd' .JMML..JMML  JMML.`Mbmmd' \n" +
            "                                                                                 \n" +
            " &c[K-Vaccine] &fv" + Key.KSPAM_VERSION + "\n\n" +
            " &fPlugin Contributors &eEntryPoint, horyu1234\n" +
            " &fK-SPAM DB Powered By &eSwiftnode\n" +
            " &f버그 제보/건의 &ehttps://github.com/EntryPointKR/K-SPAM/issues\n"),
    UPDATE_INFO_NEW("새 버전이 있습니다."),
    NEW_VERSION("&e최신버전: &f" + Key.NEW_VERSION),
    CURRENT_VERSION("&e현재버전: &f" + Key.KSPAM_VERSION),
    DOWNLOAD_URL("&e다운로드: &bhttps://github.com/EntryPointKR/K-SPAM/releases/latest"),
    EXCEPTION("예외가 발생했습니다. 개발자에게 문의해주세요. &bhttps://github.com/EntryPointKR/K-SPAM/issues &f본 메세지를 끄려면 /kvaccine alert 를 입력해주세요. &e정보: &f" + Key.EXCEPTION_MESSAGE),
    SET("&e" + Key.VALUE + " &f로 설정했습니다."),
    COMMAND_CHECK("&e" + Key.VALUE + " &f의 결과"),
    FIRST_LOGIN_KICK("&a[ K-Vaccine ]\n" +
            "&f30 초 안에 다시 접속해주세요."),
    INVALID_IP("형식에 맞지 않는 IP 입니다."),
    FORCEMODE_ON("강제 모드가 활성화되어 당신은 차단되었습니다. 서버 관리자에게 문의해주세요."),
    /**
     * 본 프로젝트에 기여했을 경우 밑 메세지에 자신의 닉네임을 추가할 수 있습니다.
     */
    LAW_INFO("&a[ K-Vaccine ] &f본 서버는 봇 테러 방지 플러그인 &eK-Vaccine &f을 사용 중입니다.\n" +
             "&a[ K-Vaccine ] &f기여자: §eEntryPoint, horyu1234\n" +
             "&a[ K-Vaccine ] &fhttps://github.com/EntryPointKR/K-SPAM\n"),
    SMALL_CACHE("캐시 데이터의 수가 적습니다. 데이터의 수가 적으면 성능이 떨어져 의도한 것이 아니라면 &eplugins/K-Vaccine/K-Spam.cache &f파일을 지운 후 리부팅해주세요."),
    SELF_DEFENCE("K-Vaccine 플러그인의 비활성화 시도를 차단했습니다."),
    SOCKET_DETECTED("&e" + Key.PLUGIN_NAME + " &f에서 &c소켓 사용&f이 감지되었습니다."),
    SCAN_RESULT("총 &e" + Key.PLUGIN_COUNT + " &f개의 플러그인 검사 완료, 의심 플러그인 수: " + Key.FIND_COUNT),
    SCAN_WARNING("소켓 사용이 감지된 플러그인들을 정상적인 경로(bukkit.org, spigotmc.org) 에서 다운로드 받았는지 확인하십시오."),
    SCAN_SAFE("위협 요소가 감지되지 않았습니다."),
    ;
    private final String msg;

    Lang(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return colorize(msg);
    }

    public MessageBuilder builder() {
        return new MessageBuilder(msg);
    }

    public String colorize(String str) {
        return ChatColor.translateAlternateColorCodes('&', str);
    }

    public enum Key {
        CHECKER_NAME("checker-name"),
        PROCESSOR_NAME("processor-name"),
        CHECKER_RESULT("checker-result"),
        CACHE_COUNT("cache-count"),
        KSPAM_VERSION("kvaccine-version"),
        NEW_VERSION("new-version"),
        TIME("time"),
        EXCEPTION_MESSAGE("exception-message"),
        VALUE("value"),
        EXECUTOR_NAME("executor-name"),
        INFO("info"),
        PLUGIN_NAME("plugin-name"),
        FIND_COUNT("find-count"),
        PLUGIN_COUNT("plugin-count"),
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

        public String build(boolean prefix) {
            if (prefix) {
                prefix();
            }
            String msg = target;
            for (int i = 0; i < keyList.size(); i++) {
                try {
                    String key = String.valueOf(keyList.get(i));
                    String val = String.valueOf(valList.get(i));
                    if (key == null || val == null) {
                        break;
                    }
                    msg = msg.replaceAll(key, Matcher.quoteReplacement(val));
                } catch (Exception ex) {
                    throw new RuntimeException(keyList + ", " + valList);
                }
            }
            return colorize(msg);
        }

        public String build() {
            return build(true);
        }
    }
}
