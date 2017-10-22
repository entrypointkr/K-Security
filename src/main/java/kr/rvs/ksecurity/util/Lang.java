package kr.rvs.ksecurity.util;

import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Junhyeong Lim on 2017-10-20.
 */
public enum Lang {
    PREFIX("&c[K-Security] &f"),
    INTRO("\n" +
            "  _  __  ____                       _ _         \n" +
            " | |/ / / ___|  ___  ___ _   _ _ __(_) |_ _   _ \n" +
            " | ' /  \\___ \\ / _ \\/ __| | | | '__| | __| | | |\n" +
            " | . \\   ___) |  __/ (__| |_| | |  | | |_| |_| |\n" +
            " |_|\\_\\ |____/ \\___|\\___|\\__,_|_|  |_|\\__|\\__, |\n" +
            "                                          |___/ \n" +
            "   v" + Version.PLUGIN + " by EntryPoint\n"),
    BOT_DETECT("&f당신은 봇으로 인식되었습니다."),
    SHOULD_PING("&f해당 서버를 목록에 추가하여 새로고침 한 후 접속하세요.\n" +
            "주소: &e" + Key.VALUE),
    NEED_UPDATE("새로운 버전 " + Key.VALUE + " 이 있습니다. " + Key.URL),
    NEED_UPDATE_GUI("새로운 버전 " + Key.VALUE + " 이 있습니다. 창을 닫으면 다운로드 주소로 이동합니다."),
    ;

    private final String message;

    Lang(String message) {
        this.message = message;
    }


    @Override
    public String toString() {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public String withPrefix(Object... args) {
        return PREFIX + withoutPrefix(args);
    }

    public String withSpacingPrefix(Object... args) {
        return PREFIX + "\n" + withoutPrefix(args);
    }

    public String withoutPrefix(Object... args) {
        String ret = toString();
        VarargsParser parser = new VarargsParser(args);
        List<VarargsParser.Section> sections = new ArrayList<>(args.length / 2);
        parser.parse(sections::add);

        for (VarargsParser.Section section : sections) {
            ret = ret.replace(section.get(0).toString(), section.get(1).toString());
        }

        return ret;
    }

    public static class Key {
        public static final String VALUE = "%value%";
        public static final String URL = "%url%";
    }
}
