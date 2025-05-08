## Spring OAuth 로그인 예제

### 개발 환경

- Java 17
- Spring Boot 3.4.5
  - Spring Security
  - Spring Data JPA
  - Spring Web MVC
  - Spring OAuth Client
- Lombok
- H2 Database

### OAuth 설정

#### 카카오

```yaml
spring:
  security:
    oauth2:
      client:
        registration:
          kakao:
            client-name: kakao
            client-id: {REST_API_KEY}
            client-secret: {CLIENT_SECRET}
            client-authentication-method: client_secret_post
            authorization-grant-type: authorization_code
            redirect-uri: http://localhost:8080/login/oauth2/code/kakao
            scope:
              - profile_nickname
              - profile_image
        provider:
          kakao:
            authorization-uri: https://kauth.kakao.com/oauth/authorize?prompt=login
            token-uri: https://kauth.kakao.com/oauth/token
            user-info-uri: https://kapi.kakao.com/v2/user/me
            user-name-attribute: id
```