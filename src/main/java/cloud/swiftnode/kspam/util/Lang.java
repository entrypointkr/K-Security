package cloud.swiftnode.kspam.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by EntryPoint on 2017-01-05.
 */
public enum Lang {
    ERROR("체커 " + Key.CHECKER_NAME + " 에서 에러가 발생했습니다."),
    DEBUG("프로세서 " + Key.PROCESSOR_NAME + " 체커 " + Key.CHECKER_NAME + " 결과 " + Key.CHECKER_RESULT),
    DENY("&c[ K-SPAM ]\n" +
            "&f당신은 K-SPAM 에 의해 차단되었습니다.")
    ;
    private final String msg;

    Lang(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return msg;
    }

    public MessageBuilder builder() {
        return new MessageBuilder(msg);
    }

    public enum Key {
        CHECKER_NAME("checker-name"),
        PROCESSOR_NAME("processor-name"),
        CHECKER_RESULT("checker-result")
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
        private List<String> valList = new ArrayList<>();
        private String target;

        MessageBuilder(String target) {
            this.target = target;
        }

        public MessageBuilder addKey(Lang.Key... keys) {
            keyList.addAll(Arrays.asList(keys));
            return this;
        }

        public MessageBuilder addVal(String... vals) {
            valList.addAll(Arrays.asList(vals));
            return this;
        }

        public MessageBuilder single(Lang.Key key, String val) {
            keyList.add(key);
            valList.add(val);
            return this;
        }

        public String build() {
            String msg = target;
            for (int i = 0; i < keyList.size(); i++) {
                String key = keyList.get(i).toString();
                String val = valList.get(i);
                if (key == null || val == null) {
                    break;
                }
                msg = msg.replaceAll(key, val);
            }
            return msg;
        }
    }
}
