[![Build Status](https://travis-ci.org/EntryPointKR/K-Security.svg?branch=master)](https://travis-ci.org/EntryPointKR/K-Security)

# K-Security
마인크래프트 서버를 위한 국내 최고의 통합 보안 플러그인입니다.

봇 테러를 방지하거나 악성 플러그인 검사, 네트워크 통신 모니터링 등의 기능이 있습니다.

거의 모든 처리가 비동기로 진행되어 매우 가볍습니다.

다운로드
----
공식 버전: https://github.com/EntryPointKR/K-Security/releases/latest

Jenkins 빌드 서버: http://builds.rvs.kr/job/K-Security/

기능
----
### 봇 테러 방지 

[![ScreenShot](https://raw.github.com/EntryPointKR/K-Security/master/pic/fakevideo.png)](https://www.youtube.com/embed/OGWxUQdQXDk)

### 각종 취약점 방지

방지 목록

* 샵키퍼 아이템 복사

* 크래시 색코드

* 트리거 플레이스홀더 사용

* RPGITEM 아이템 복사

* 프리캠 아이템 복사

* PlayerVaults 아이템 복사

본 기능은 1.5.2 버전을 위한 기능이며 상위 버전 (1.8+) 사용자는 config.yml 에서 기능을 꺼주세요.

![alt tag](https://raw.github.com/EntryPointKR/K-Security/master/pic/kanticheat.png)

### 악성 플러그인 검사

![alt tag](https://raw.github.com/EntryPointKR/K-Security/master/pic/ksecvaccine.png)

### 네트워크 모니터링

![alt tag](https://raw.github.com/EntryPointKR/K-Security/master/pic/ksecnetmonitor.png)

### 비인가 권한 취득 차단

![alt tag](https://raw.github.com/EntryPointKR/K-Security/master/pic/ksecoplist.png)

### GUI 환경

![alt tag](https://raw.github.com/EntryPointKR/K-Security/master/pic/ksecgui.png)

### 자가 보호
 
![alt tag](https://raw.github.com/EntryPointKR/K-Security/master/pic/ksecdefence.png)

다운로드
----
https://github.com/EntryPointKR/K-Security/releases/latest

명령어
----
> /ks check 아이피/닉네임

해당 닉네임 또는 아이피의 스팸 여부를 확인합니다.

> /ks firewall

방화벽 모드를 온/오프합니다. 강제모드가 활성화됐을 경우 그 시점 부터 처음 접속하는 유저들의 입장을 거부합니다.

> /ks remove 아이피/닉네임

해당 아이피나 닉네임을 스팸 목록에서 제거합니다. 닉네임의 경우 접속된 플레이어만 가능하며 오프라인일 경우 IP 를 입력하셔야 합니다.

> /ks info

플러그인의 정보를 확인합니다.

> /ks firstkick

처음 접속한 유저의 일회성 추방 기능을 온/오프합니다.

> /ks debug

디버그 모드를 온/오프합니다.

> /ks alert

내부에서 생기는 예외를 알려주는 기능을 온/오프합니다.

> /ks netalert

네트워크 모니터링 기능을 온/오프합니다.

> /ks reload

설정 파일을 다시 불러옵니다.

> /ks listop

OP 허용 목록을 보여줍니다.

> /ks clear

OP 허용 목록을 모두 지웁니다.

> /ks addop 닉네임

OP 허용 목록에 닉네임을 추가합니다.

> /ks remop 닉네임

OP 허용 목록에서 닉네임을 제거합니다.

> /ks show

K-Security GUI 를 엽니다.

설정 파일
------
> debug-mode: false

디버그 모드를 온/오프합니다. 디버그 모드가 활성화 됐을 경우 각 객체들의 작동 로그를 확인할 수 있습니다.

기본값은 비활성화입니다.

> first-login-kick: true

서버에 처음 접속한 유저를 1 회 킥합니다. 해당 옵션은 봇테러 방지를 위한 기능입니다.

기본값은 활성화입니다.

> alert: false

내부에서 발생하는 예외를 알려주는 기능을 온/오프합니다.

기본값: 비활성화

> update-check-period: 1

설정 시간을 주기로 업데이트를 확인합니다.

기본값: 1시간

> network-alert: true

네트워크 모니터링 기능을 온/오프합니다.

기본값: 활성화

> op-list: [NicknameA, NicknameB]

OP 허용 목록입니다.

위에서 닉네임을 추가 또는 제거할 수 있으며

op-list: [] 와 같이 목록이 비었을 경우 기능은 비활성화됩니다.

> net-escape-list: [K-Security]

네트워크 모니터링의 예외 목록입니다.

위에서 플러그인의 이름을 추가 또는 제거할 수 있습니다.

> anticheat: ~~

1.5.2 의 취약점 방지의 기능을 온/오프합니다.

RPGItem 과 PlayerVaults 값은 런타임에 변경할 수 없습니다.

버그 제보
-----
Issue 메뉴를 클릭 후 New Issue 버튼을 눌러 제목과 내용을 작성해주시면 됩니다.

개발 참여/기여
----
K-Security 는 오픈소스 프로젝트로 누구나 개발에 참여할 수 있습니다. 방법은 우측 상단의 Fork 버튼을 눌러 자신의 저장소로 복사한 후 코드를 수정하셔서 Pull request 메뉴, New pull request 버튼을 눌러 내용을 작성해 전송해주시면 됩니다. 

프로그래밍에 익숙하지 않으신 분이라도 의욕만 있다면 환영입니다. 요청한 코드에 버그가 있을 경우 코멘트를 달아 알려드리며 그때 수정해주시면 됩니다.

자신의 API 를 사용하는 Checker 를 추가하고 싶다면 checker 패키지에 SpamChecker 를 상속해 알맞는 Result 를 반환하게 코드를 작성한 후
원하는 프로세서 생성자의 super.setCheckerList() 안에 체커를 추가하시면 됩니다.

동기로 돌아가는 프로세서, 가벼운 작업 위주
> SyncLoginProcessor, SyncJoinProcessor

비동기로 돌아가는 프로세서, 무거운 작업 위주
> AsyncLoginProcessor

클래스 구조 다이어그램
----
~~한국에 이보다 잘만든 플러그인이 있나요~~

![alt tag](https://raw.github.com/EntryPointKR/K-Security/master/pic/diagram.png)
