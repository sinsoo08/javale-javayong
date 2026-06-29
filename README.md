# CodeTogether - 코딩 학습자 커뮤니티 앱

> **스마트문화앱콘텐츠제작 앱 제안서 (신지수)** 기반으로 제작된 안드로이드 앱

---

## 📱 앱 소개

CodeTogether는 코딩을 학습하는 초보자부터 주니어 개발자까지, 함께 소통하고 성장하는 **프로그래밍 커뮤니티 플랫폼**입니다.

### 주요 대상
- 코딩을 처음 배우는 학생
- 독학으로 프로그래밍을 학습하는 일반 사용자
- 취미로 개발을 배우는 사용자
- 비전공자 및 초보 개발자

---

## ✨ 주요 기능

| 기능 | 설명 |
|------|------|
| 🏠 **홈 피드** | 인기/최신 게시글 확인 및 통계 |
| 💬 **언어별 커뮤니티** | Python, Java, Kotlin 등 채널별 소통 |
| ❓ **질문/답변** | 코드 스니펫 포함한 상세 Q&A |
| ✅ **채택 답변** | 검증된 답변 표시 시스템 |
| 📝 **게시글 작성** | 카테고리/언어 분류 + 코드 첨부 |
| 👤 **프로필** | 레벨 시스템, 활동 통계 |

---

## 🗂️ 프로젝트 구조

```
CodeTogether/
├── app/src/main/
│   ├── java/com/codetogether/
│   │   ├── data/
│   │   │   ├── model/
│   │   │   │   └── Models.kt          # Post, Comment, User, Enum 모델
│   │   │   └── repository/
│   │   │       └── SampleDataRepository.kt  # 샘플 데이터
│   │   └── ui/
│   │       ├── MainActivity.kt        # 하단 내비게이션
│   │       ├── home/
│   │       │   └── HomeFragment.kt    # 홈 화면
│   │       ├── community/
│   │       │   ├── CommunityFragment.kt   # 언어별 채널 목록
│   │       │   └── ChannelAdapter.kt
│   │       ├── question/
│   │       │   ├── PostAdapter.kt     # 게시글 리스트
│   │       │   ├── CommentAdapter.kt  # 댓글/답변 리스트
│   │       │   ├── QuestionListActivity.kt
│   │       │   ├── QuestionDetailActivity.kt
│   │       │   └── AskQuestionActivity.kt
│   │       └── profile/
│   │           └── ProfileFragment.kt
│   └── res/
│       ├── layout/        # XML 레이아웃 파일들
│       ├── navigation/    # 내비게이션 그래프
│       ├── menu/          # 하단 메뉴
│       ├── drawable/      # 배경, 아이콘 Drawable
│       └── values/        # colors, strings, themes
```

---

## 🔧 안드로이드 스튜디오에서 열기

1. 안드로이드 스튜디오 실행 → **File > Open**
2. `CodeTogether` 폴더 선택
3. Gradle 동기화 완료 대기
4. 에뮬레이터 또는 기기 연결 후 **Run** ▶

### 요구사항
- Android Studio Hedgehog (2023.1.1) 이상
- Gradle 8.4+
- minSdk 26 (Android 8.0)
- targetSdk 34 (Android 14)
- Kotlin 1.9.23

---

## 🎨 UI 구성

### 화면 흐름
```
MainActivity (하단 내비게이션)
├── HomeFragment          → 홈 피드
│   └── [게시글 클릭]  → QuestionDetailActivity
├── CommunityFragment     → 언어별 채널
│   └── [채널 클릭]    → QuestionListActivity
│       ├── [게시글]   → QuestionDetailActivity
│       └── [+ FAB]    → AskQuestionActivity
└── ProfileFragment       → 내 정보
```

### 컬러 팔레트
- **Primary**: `#4A6CF7` (블루 퍼플)
- **Background**: `#F5F6FA` (라이트 그레이)
- **Code BG**: `#1E1E2E` (다크 테마)

---

## 📈 수익 모델 (제안서 기반)
- 광고 서비스
- 프리미엄 기능 제공
- 맞춤형 학습 콘텐츠 추천
- 기업 및 교육기관 연계 서비스

---

## 🚀 향후 개선 계획
- [ ] Firebase 백엔드 연동 (실시간 DB)
- [ ] 회원가입 / 로그인 구현
- [ ] 코드 에디터 통합 (Syntax Highlighting)
- [ ] 푸시 알림 (답변 채택, 좋아요)
- [ ] 검색 기능
- [ ] 다크 모드 지원
