package cloud.swiftnode.kspam.abstraction.processor;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * Created by ldmsys on 2017-01-19.
 */

public class JoinMessageProcessor {
    public boolean process(Player player) {
        /*
        K-SPAM 은 AGPL 라이선스이며 개발자, 플러그인 정보, 소스 제공이 의무입니다.
                     밑 메세지 전송 코드를 제거 시 법적 책임을 물을 수 있습니다.
                     본 프로젝트에 기여했을 경우 밑 메세지에 자신의 닉네임을 추가할 수 있습니다.
        */
       player.sendMessage("§c[ K-SPAM ] §f본 서버는 봇 테러 방지 플러그인 §eK-SPAM §f을 사용 중입니다.");
       player.sendMessage("§c[ K-SPAM ] §f기여자: §eEntryPoint, horyu1234");
       player.sendMessage("§c[ K-SPAM ] §fhttps://github.com/EntryPointKR/K-SPAM");
       return true;
    }
}
