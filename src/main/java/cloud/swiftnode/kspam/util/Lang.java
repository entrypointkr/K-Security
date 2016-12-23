package cloud.swiftnode.kspam.util;

import org.bukkit.ChatColor;

import java.text.MessageFormat;

/**
 * Created by EntryPoint on 2016-12-17.
 */
public enum Lang {
    PREFIX("&c&l[ K-SPAM ] &f"),
    INTRO("\n" +
            "#                                                                  \n" +
            "#  `7MMF' `YMM'      .M\"\"\"\"\"bgd `7MM\"\"\"Mq.   db      `7MMM.    ,MMF'\n" +
            "#    MM   .M'       ,MI    \"Y    MM   `MM. ;MM:       MMMb   dPMM  \n" +
            "#    MM .d\"         `MMb.       MM   ,M9 ,V^MM.      M YM  ,M MM  \n" +
            "#    MMMMM.           `YMMNq.   MMmmdM9 ,M  `MM      M  Mb  M' MM  \n" +
            "#    MM  VMA        .     `MM   MM      AbmmmqMA     M  YM.P'  MM  \n" +
            "#    MM   `MM.      Mb     dM   MM     A'      VML    M  `YM'   MM  \n" +
            "#  .JMML.   MMb.    P\"Ybmmd\"  .JMML. .AMA.   .AMMA..JML. `'  .JMML.\n" +
            "#                                                                  \n\n" +
            " &c[K-SPAM Community Edition] &fv{0}\n\n" +
            " &fPlugin Contributors &eEntryPoint, horyu1234\n" +
            " &fK-SPAM DB Powered By &eSwiftnode\n" +
            " &f버그 제보/건의 &ehttps://github.com/EntryPointKR/K-SPAM/issues\n"),
    NEW_VERSION("새로운 버전이 있습니다."),
    LAST_VERSION("최신버전입니다."),
    VERSION("{0}현재 버전: &e{1}&f\n" +
            "{0}최신 버전: &e{2}&f\n" +
            "{0}다운로드: &ehttps://github.com/EntryPointKR/K-SPAM/releases&f"),
    ERROR("정보 조회 중 에러가 발생했습니다. 검증받지 못한 플레이어들: &e{0}\n" +
            "&f본 메세지를 끄려면 /kspamerror 명령어를 입력해주세요."),
    SWITCH("&e{0} &f로 설정했습니다."),
    NO_PERM("권한이 없습니다."),
    KICKED("&e{0} &f님이 차단되었습니다. API: &e{1}"),
    EXCEPTION("예외가 발생했습니다. 개발자에게 제보해주세요 &ehttps://github.com/EntryPointKR/K-SPAM/issues &f추가 정보: &e{0}"),
    MOTD("&c당신은 K-SPAM 에 의해 차단되었습니다."),
    CHECK("&e{0} &f의 차단 여부: &e{1} &f마지막 사용 API: &e{2}"),
    REMOVE("&e{0} &f이(가) 캐시 목록에서 제거됩니다."),
    CACHE_COUNT("캐쉬된 데이터: &e{0} &f개"),
    ADD("&e{0} &f을 캐시 목록에 추가합니다."),
    KICK("당신의 아이피는 K-SPAM 블랙리스트에 추가되어 있어 \n" +
            "본 서버에 접속하실 수 없습니다.");
    private final String msg;

    Lang(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return toString("");
    }

    public String toString(Object... value) {
        return ChatColor.translateAlternateColorCodes('&',
                MessageFormat.format(msg, value));
    }
}
