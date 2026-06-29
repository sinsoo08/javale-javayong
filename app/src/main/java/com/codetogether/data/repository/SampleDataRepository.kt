package com.codetogether.data.repository

import com.codetogether.data.model.*
import java.util.Calendar

object SampleDataRepository {

    fun getPosts(language: ProgrammingLanguage? = null, category: PostCategory? = null): List<Post> {
        val all = samplePosts
        return all.filter { post ->
            (language == null || language == ProgrammingLanguage.GENERAL || post.language == language) &&
            (category == null || post.category == category)
        }
    }

    fun getPostById(id: Int): Post? = samplePosts.find { it.id == id }

    fun getCommentsByPostId(postId: Int): List<Comment> =
        sampleComments.filter { it.postId == postId }

    fun getCommunityChannels(): List<CommunityChannel> = sampleChannels

    fun getCurrentUser(): User = currentUser

    fun getPopularPosts(): List<Post> =
        samplePosts.sortedByDescending { it.likeCount + it.viewCount }.take(5)

    fun getRecentPosts(): List<Post> =
        samplePosts.sortedByDescending { it.createdAt }.take(10)

    private val cal = Calendar.getInstance()

    private fun daysAgo(days: Int): java.util.Date {
        cal.time = java.util.Date()
        cal.add(Calendar.DAY_OF_YEAR, -days)
        return cal.time
    }

    private val currentUser = User(
        id = 1,
        nickname = "코딩초보",
        email = "user@example.com",
        level = UserLevel.BEGINNER,
        bio = "파이썬을 공부하고 있는 대학생입니다.",
        favoriteLanguages = listOf(ProgrammingLanguage.PYTHON, ProgrammingLanguage.JAVASCRIPT),
        postCount = 5,
        answerCount = 3,
        likeReceived = 12
    )

    private val samplePosts = listOf(
        Post(
            id = 1,
            title = "파이썬 리스트 컴프리헨션이 뭔가요?",
            content = "파이썬을 처음 공부하는데 리스트 컴프리헨션이라는 게 자꾸 나오네요. 일반 for문이랑 무슨 차이가 있는지 모르겠어요. 쉽게 설명해 주실 수 있을까요?",
            author = "python_newbie",
            authorLevel = UserLevel.BEGINNER,
            language = ProgrammingLanguage.PYTHON,
            category = PostCategory.QUESTION,
            likeCount = 24,
            commentCount = 8,
            viewCount = 312,
            createdAt = daysAgo(1),
            tags = listOf("list", "comprehension", "기초"),
            codeSnippet = "# 일반 for문\nresult = []\nfor i in range(10):\n    result.append(i * 2)\n\n# 이게 리스트 컴프리헨션인가요?\nresult = [i * 2 for i in range(10)]"
        ),
        Post(
            id = 2,
            title = "코틀린 코루틴 vs Java 스레드 어떤 게 나은가요?",
            content = "안드로이드 개발을 배우고 있는데 비동기 처리 방법으로 코루틴과 스레드 중 어떤 걸 사용해야 할지 고민입니다. 실제 프로젝트에서는 어떻게 사용하시나요?",
            author = "android_dev",
            authorLevel = UserLevel.JUNIOR,
            language = ProgrammingLanguage.KOTLIN,
            category = PostCategory.DISCUSSION,
            likeCount = 47,
            commentCount = 15,
            viewCount = 892,
            createdAt = daysAgo(2),
            tags = listOf("coroutine", "thread", "async", "android")
        ),
        Post(
            id = 3,
            title = "JavaScript 비동기 처리 정리 (Promise, async/await)",
            content = "자바스크립트 비동기 처리 방식을 공부하면서 정리한 내용을 공유합니다. callback 지옥부터 Promise, async/await까지 단계별로 설명했습니다.",
            author = "js_master",
            authorLevel = UserLevel.MID,
            language = ProgrammingLanguage.JAVASCRIPT,
            category = PostCategory.SHARING,
            likeCount = 89,
            commentCount = 23,
            viewCount = 1543,
            createdAt = daysAgo(3),
            tags = listOf("Promise", "async", "await", "callback"),
            codeSnippet = "// Promise 방식\nfetch('/api/data')\n  .then(res => res.json())\n  .then(data => console.log(data))\n  .catch(err => console.error(err));\n\n// async/await 방식\nasync function getData() {\n  try {\n    const res = await fetch('/api/data');\n    const data = await res.json();\n    console.log(data);\n  } catch (err) {\n    console.error(err);\n  }\n}"
        ),
        Post(
            id = 4,
            title = "Java NullPointerException 해결 도와주세요",
            content = "Spring Boot 프로젝트에서 계속 NullPointerException이 발생합니다. 서비스 계층에서 레포지토리를 주입받는 부분인데 원인을 모르겠어요.",
            author = "spring_beginner",
            authorLevel = UserLevel.BEGINNER,
            language = ProgrammingLanguage.JAVA,
            category = PostCategory.QUESTION,
            likeCount = 13,
            commentCount = 6,
            viewCount = 245,
            createdAt = daysAgo(1),
            tags = listOf("Spring Boot", "NullPointer", "DI"),
            codeSnippet = "@Service\npublic class UserService {\n    // @Autowired가 빠진 건가요?\n    private UserRepository userRepository;\n    \n    public User findById(Long id) {\n        return userRepository.findById(id).get(); // 여기서 NPE 발생\n    }\n}"
        ),
        Post(
            id = 5,
            title = "C++ 포인터 기초 스터디 모집합니다",
            content = "C++ 포인터와 메모리 관리를 함께 공부할 분들 모집합니다! 매주 토요일 온라인으로 진행하며, 이론과 실습을 병행할 예정입니다.",
            author = "cpp_study",
            authorLevel = UserLevel.JUNIOR,
            language = ProgrammingLanguage.CPP,
            category = PostCategory.RECRUIT,
            likeCount = 31,
            commentCount = 18,
            viewCount = 567,
            createdAt = daysAgo(4),
            tags = listOf("스터디", "포인터", "메모리")
        ),
        Post(
            id = 6,
            title = "이 파이썬 코드 더 파이썬답게 리뷰 부탁드립니다",
            content = "알고리즘 문제를 풀었는데 코드가 너무 지저분한 것 같아요. 더 파이썬다운 방식으로 개선할 수 있는 부분을 알려주세요.",
            author = "algo_study",
            authorLevel = UserLevel.BEGINNER,
            language = ProgrammingLanguage.PYTHON,
            category = PostCategory.REVIEW,
            likeCount = 19,
            commentCount = 7,
            viewCount = 334,
            createdAt = daysAgo(2),
            tags = listOf("코드리뷰", "알고리즘", "pythonic"),
            codeSnippet = "def find_max(nums):\n    max_val = nums[0]\n    for i in range(len(nums)):\n        if nums[i] > max_val:\n            max_val = nums[i]\n    return max_val"
        ),
        Post(
            id = 7,
            title = "Go 언어로 REST API 서버 만들기 후기",
            content = "사이드 프로젝트로 Go 언어를 처음 사용해서 REST API 서버를 만들어봤습니다. 장단점과 배운 점을 공유합니다.",
            author = "gopher",
            authorLevel = UserLevel.MID,
            language = ProgrammingLanguage.GO,
            category = PostCategory.SHARING,
            likeCount = 56,
            commentCount = 12,
            viewCount = 987,
            createdAt = daysAgo(5),
            tags = listOf("Go", "REST API", "gin", "후기")
        ),
        Post(
            id = 8,
            title = "SQL 인덱스 언제 어떻게 걸어야 하나요?",
            content = "데이터베이스 쿼리 최적화를 공부 중인데 인덱스를 언제 어떤 컬럼에 걸어야 하는지 헷갈립니다. 실무 기준으로 설명해 주실 분 계신가요?",
            author = "db_newbie",
            authorLevel = UserLevel.BEGINNER,
            language = ProgrammingLanguage.SQL,
            category = PostCategory.QUESTION,
            likeCount = 38,
            commentCount = 11,
            viewCount = 678,
            createdAt = daysAgo(3),
            tags = listOf("INDEX", "쿼리최적화", "MySQL")
        )
    )

    private val sampleComments = listOf(
        Comment(
            id = 1, postId = 1, author = "python_expert",
            authorLevel = UserLevel.SENIOR,
            content = "리스트 컴프리헨션은 리스트를 더 간결하고 파이썬답게 만드는 방법입니다. 성능도 일반 for문보다 약간 빠른 편이에요.",
            likeCount = 15, isAccepted = true,
            codeSnippet = "# 숫자 제곱 리스트 만들기\n# for문 방식\nsquares = []\nfor x in range(10):\n    squares.append(x**2)\n\n# 컴프리헨션 방식 (훨씬 간결!)\nsquares = [x**2 for x in range(10)]\n\n# 조건도 추가 가능\neven_squares = [x**2 for x in range(10) if x % 2 == 0]"
        ),
        Comment(
            id = 2, postId = 1, author = "senior_dev",
            authorLevel = UserLevel.MID,
            content = "가독성 측면에서 간단한 변환은 컴프리헨션을 쓰고, 복잡한 로직이 필요하면 일반 for문이 더 좋습니다.",
            likeCount = 8
        ),
        Comment(
            id = 3, postId = 4, author = "spring_master",
            authorLevel = UserLevel.SENIOR,
            content = "@Autowired 어노테이션이 없어서 DI가 안 되고 있습니다. 또는 생성자 주입 방식을 권장드립니다.",
            likeCount = 22, isAccepted = true,
            codeSnippet = "@Service\npublic class UserService {\n    private final UserRepository userRepository;\n    \n    // 생성자 주입 (권장)\n    public UserService(UserRepository userRepository) {\n        this.userRepository = userRepository;\n    }\n}"
        )
    )

    private val sampleChannels = ProgrammingLanguage.values()
        .filter { it != ProgrammingLanguage.GENERAL }
        .map { lang ->
            val postCount = samplePosts.count { it.language == lang }
            CommunityChannel(
                language = lang,
                memberCount = (50..2000).random(),
                postCount = postCount,
                description = "${lang.displayName} 관련 질문, 공유, 토론을 나눠요"
            )
        }
}
