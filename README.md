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
