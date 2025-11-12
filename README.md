graph LR
    subgraph "External Services"
        Kakao[Kakao API]
        Gemini[Google Gemini API]
    end

    subgraph "Client"
        Client[React.js App<br/>(Client)<br/>localhost:5173]
    end

    subgraph "Your Backend Infrastructure"
        SpringBoot[Spring Boot (Main Hub)<br/>localhost:8080]
        FastAPI[FastAPI (AI Service)<br/>localhost:8000]
        MariaDB[(MariaDB (RDB)<br/>Users, Forums, Diaries)]
        MongoDB[(MongoDB (NoSQL)<br/>Chat Messages)]
    end

    %% --- Client Interactions ---
    Client -- "1. API (로그인, 게시글)" --> SpringBoot
    Client -- "2. WebSocket (실시간 채팅)" --> SpringBoot
    Client -- "3. API (일기 요약)" --> FastAPI

    %% --- Spring Boot (Main Hub) Interactions ---
    SpringBoot -- "JPA (User, Forum, Diary)" --> MariaDB
    SpringBoot -- "MongoRepository (Chat)" --> MongoDB
    SpringBoot -- "OAuth2 인증" --> Kakao
    SpringBoot -- "실시간 AI 채팅" --> Gemini
    
    %% --- FastAPI (AI Service) Interactions ---
    FastAPI -- "채팅 내역 조회" --> MongoDB
    FastAPI -- "AI 일기 요약" --> Gemini
    FastAPI -- "요약본 저장 (Callback)" --> SpringBoot
