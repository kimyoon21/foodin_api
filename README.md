# foodin_api

# Environment
SpringBoot=2.1.1.RELEASE

Kotlin=1.2.71

# Build
```
gradle :api:build
```

# Projects 목적 및 기능
- api
    - Rest API
    - Servlet
    - 인증
- core
    - 도메인, enum
    - usecase
- entity
    - enitity
    - jpa
    - data resource 와 관계된 기능

#### Project dependency
core -> entity -> api
이 dependency cycle 을 꺠지 말것


# Notion 적용기 (jira,confluence,asana,evernote 와 비교)

Last Edited: Dec 15, 2018 5:42 PM

Notion 이란?

- 약간은 jira 같고, 약간은 confluence 같다가도, evernote 같기도 한 메모,태스크관리,포스팅 어플리케이션
- 깔끔하고 심플한듯 하면서도, 사용자가 필요하고 갈구하는 모든 기능이 다 들어있는 느낌. 엄청나게 커스터마이징이 가능하다.

그동안의 간지러운 부분들

Jira 와 wiki 의 단점들

 - 느리다, 순서 바꾸기 뭐바꾸기 너무 힘듬, 복잡하고 뷰를 바꾸는게 큰일

Asana 의 아쉬움

 - 내 이슈만 보기 등,, 이슈나 담당자를 지정하고 그에 따른 필터 등이 어려움

Evernote 의 아쉬움

 - 마크다운이나 코드 쓰기 아쉬움. 데이터베이스의 느낌은 없음

이것들을 극복하는 Notion!!!

당연히 되는 기능

 - 페이지 이력

 - 공동 실시간 편집

![](NotionDesktop2018-12-1517-29-02(1)-69ac834e-6273-4955-860f-2cf71726209d.jpg)

데이터베이스 에서도 현재 동료가 어느 페이지를 보고있는지 까지 알 수 있다

신박한것들

 - 템플릿

 - 인라인,링크,

 - 데이터베이스에서 뷰를 마음껏 바꾸기

lock 으로 수정잠금도 가능

 - 언제 어디서나 바로바로 수정 적용

- 쓰레기통에 버리기
- 바로 외부링크 쉐어 (퍼블릭리크, 워크스페이스 셰어)

더 필요한점?

 - 슬랙은 아쉬움 (너무 많이 옴)

 - 앱에서 사용시에 첫페이지가 고정임. 첫페이지 지우면 에러페이지로 랜딩

 - 안드에서 백버튼 누를때 안꺼지고 드로어 열림 좋겠음 (요청함))

마무리