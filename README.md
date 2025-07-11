# Restart: 당근마켓 클론 코딩 🥕

Team Restart(재시동)의 Spring 기반 웹 프로젝트로, 챗봇 상담사가 있는 중고 거래 플랫폼을 구현합니다.

## 🚀 About the Project

> 챗봇 상담 기능이 결합된 중고 거래 웹 서비스 클론입니다.

당근마켓을 참고하여 사용자 중심의 중고거래 기능과 AI 챗봇을 통한 실시간 상담 서비스를 제공합니다. Spring 생태계를 활용하며, 팀원 전원이 다양한 역할을 수행합니다.

## 👥 Team Restart

| 이름   | 역할              |
| ------ | ----------------- |
| 유동혁 | 팀장, 백엔드 개발 |
| 안시은 | 백엔드 개발       |
| 이재원 | 백엔드 개발       |

> 팀명 "Restart"는 팀원 이름인 재(원) + 시(은) + 동(혁)의 조합에서 착안되었으며, ‘다시 시작하다’는 의미를 담고 있습니다.

---

## 🗂️ Project Dashboard

📌 이 프로젝트는 **Notion**을 통해 협업과 문서 작업을 관리합니다.  
팀 일정, 회의록, 역할 분담, API 설계 등 실시간 작업 내역은 아래 링크를 통해 확인할 수 있습니다.

- 🔗 [프로젝트 관리용 노션 페이지](https://www.notion.so/225dccc42309801fa0e5d0f4f63691a0)

---

## 🗓️ Timeline

- **2025.07.03 (Thu) ~ 2025.07.23 (Wed)**

---

## 🔧 Tech Stack

- **Backend**: Spring Boot, Spring Security, JPA(Hibernate), PostgreSQL
- **Frontend**: Thymeleaf (템플릿), HTML/CSS
- **Infrastructure**: AWS S3 (이미지 업로드), OAuth(Google), Google Maps API, ChatGPT API

---

## 📌 Features

- 유저 회원가입 / 로그인 (소셜 로그인 포함)
- 상품 등록, 이미지 업로드, 지역 기반 목록 조회
- 챗봇 기반 사용자 상담 (ChatGPT 활용)
- Google Maps 연동을 통한 위치 서비스

---

## 🌐 External API

| API          | 용도 설명                      |
| ------------ | ------------------------------ |
| Google OAuth | 기본 소셜 로그인 기능          |
| Google Maps  | 지역 기반 거래 위치 서비스     |
| ChatGPT API  | 챗봇 상담 기능 구현            |
| AWS S3       | 이미지 업로드 및 파일 URL 저장 |

---

## 🛠 Internal Development

- **RESTful API 직접 설계 및 구현**
- 기존 템플릿 없음 (기획→설계→개발 전 과정을 자체 구성)
- [Figma 시안](https://www.figma.com/file/kSMua8TOVGIIPbNH1jie1Q/%EC%98%A4%EB%A5%B4%EB%AF%B8-2%EA%B8%B0-%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8(%EB%8B%B9%EA%B7%BC%EB%A7%88%EC%BC%93Web-%ED%81%B4%EB%A1%A0%EC%BD%94%EB%94%A9)?type=design&t=0GKxjur6Zlk8Gse3-1)을 기반으로 UI 구성

---

## 🤝 Contribution & Workflow

### 브랜치 전략

- `main`: 배포 브랜치  
- `dev`: 개발 브랜치 → 여기서 `feature/*`, `fix/*`, `style/*` 등으로 파생

```bash
예시: feature/chat-room-api
```

### 커밋 컨벤션

메시지 형식

```txt
<타입>(옵션:모듈): <변경 요약>

<변경 상세 설명> (선택 사항)
```

예시

```txt
feat(login): 로그인 실패 시 메시지 추가

사용자가 로그인에 실패했을 때, 오류 메시지를 UI에 표시하도록 수정함
```

커밋 타입 목록

| 타입     | 설명                                       |
| -------- | ------------------------------------------ |
| feat     | 새로운 기능 추가                           |
| fix      | 버그 수정                                  |
| docs     | 문서 수정 (README, 주석 등)                |
| style    | 코드 스타일 수정 (세미콜론, 들여쓰기 등)   |
| refactor | 코드 리팩토링 (기능 변화 없음)             |
| test     | 테스트 코드 추가 또는 수정                 |
| chore    | 빌드 설정, 패키지 매니저 설정 등 기타 작업 |
| perf     | 성능 개선                                  |

---

## 📄 License

이 프로젝트는 **비영리 학습 목적**으로 진행되며,  
국내에서는 **당근마켓**으로, 해외에서는 **Karrot Market**이라는 이름으로 알려진  
[당근 공식 서비스](https://www.daangn.com)와는 무관합니다.  
코드 및 자료는 자유롭게 참고 가능하지만, 상업적 사용은 삼가주시기 바랍니다.
