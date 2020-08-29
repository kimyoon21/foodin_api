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

#### 설명
3개의 모듈로 나눠져있고 각각
- api 단 (컨트롤러)
- core (서비스와 게이트웨이, 도메인)
- entity( 레파지토리와 DB 연동할 엔티티)
역할을 하고 있습니다.
```
하이버네이트의 엔티티 연결을 끊기 위해 모든 데이터는 가져오자마자 domain 으로 변경해서 사용하게 되어있고, 마찬가지로 저장할때도 domain 을 저장 직전에 entity 로 변환합니다.
게이트 웨이를 통해 레파지토리를 호출하기에 추후에 똑같은 역할을 하는 다른 서비스 ( noSQL 이 되든, 쿠팡이 만든 스프링 데이터 리쿼리를 적용하든 앞단에는 영향 없이 entity 부분만 바꾸면 되도록) 를 쉽게 바꿀수 있는 구조로 해놨습니다.
코틀린  JPA 가 기존 코틀린 문법을 깨야하는 부분도 좀 있고 해서 몇가지 라이브러리릍 통해 적용이 되어있고 그 외에도 어노테이션을 통해서 final 변수 기본값이라던가 NoArgs 생성자 같은걸 만든 부분도 있습니다.
```

#### 최초 설치시
- spring, kotlin 버전등을 IDE 에서 알맞게 세팅
- 로컬 Database 세팅 후 schema.sql 쿼리 실행해서 테이블추가하고 기본 클라이언트 추가

### 배포시
- gradle clean 으로 청소
- gradle assemble 로 jar 파일 생
- 터미널을 통해 make_zip_for_beastalk.sh 를 수행해서 .ebextension 과 같이 묶어서 zip 생성
- 해당 zip 파일을 aws beanstalk 에 업로
