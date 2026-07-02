package com.codetogether.data.repository

import com.codetogether.data.model.*
import java.util.Calendar

object SampleDataRepository {

    // mutableListOf 로 변경 → 게시글 추가/삭제 가능
    private val posts: MutableList<Post> = mutableListOf()
    private val comments: MutableList<Comment> = mutableListOf()
    private var nextPostId = 9

    init {
        posts.addAll(initialPosts())
        comments.addAll(initialComments())
    }

    fun getPosts(language: ProgrammingLanguage? = null, category: PostCategory? = null): List<Post> {
        return posts.filter { post ->
            (language == null || language == ProgrammingLanguage.GENERAL || post.language == language) &&
            (category == null || post.category == category)
        }.sortedByDescending { it.createdAt } // 최신순 정렬
    }

    fun getPostById(id: Int): Post? = posts.find { it.id == id }

    fun getCommentsByPostId(postId: Int): List<Comment> =
        comments.filter { it.postId == postId }

    fun getCommunityChannels(): List<CommunityChannel> {
        return ProgrammingLanguage.values()
            .filter { it != ProgrammingLanguage.GENERAL }
            .map { lang ->
                CommunityChannel(
                    language = lang,
                    memberCount = when (lang) {
                        ProgrammingLanguage.PYTHON -> 1842
                        ProgrammingLanguage.JAVA -> 1523
                        ProgrammingLanguage.KOTLIN -> 987
                        ProgrammingLanguage.JAVASCRIPT -> 1654
                        else -> 300
                    },
                    postCount = posts.count { it.language == lang },
                    description = "${lang.displayName} 관련 질문, 공유, 토론을 나눠요"
                )
            }
    }

    fun getCurrentUser(): User = currentUser

    fun getPopularPosts(): List<Post> =
        posts.sortedByDescending { it.likeCount + it.viewCount }.take(5)

    fun getRecentPosts(): List<Post> =
        posts.sortedByDescending { it.createdAt }.take(10)

    // ★ 새 게시글 추가 함수
    fun addPost(
        title: String,
        content: String,
        language: ProgrammingLanguage,
        category: PostCategory,
        codeSnippet: String? = null,
        tags: List<String> = emptyList()
    ): Post {
        val newPost = Post(
            id = nextPostId++,
            title = title,
            content = content,
            author = currentUser.nickname,
            authorLevel = currentUser.level,
            language = language,
            category = category,
            likeCount = 0,
            commentCount = 0,
            viewCount = 0,
            createdAt = java.util.Date(),
            tags = tags,
            codeSnippet = codeSnippet?.ifBlank { null }
        )
        posts.add(0, newPost) // 맨 위에 추가
        return newPost
    }

    // ★ 댓글 추가 함수
    fun addComment(postId: Int, content: String, codeSnippet: String? = null): Comment {
        val newComment = Comment(
            id = comments.size + 1,
            postId = postId,
            author = currentUser.nickname,
            authorLevel = currentUser.level,
            content = content,
            likeCount = 0,
            codeSnippet = codeSnippet?.ifBlank { null }
        )
        comments.add(newComment)

        // 댓글 수 업데이트
        val postIndex = posts.indexOfFirst { it.id == postId }
        if (postIndex >= 0) {
            val post = posts[postIndex]
            posts[postIndex] = post.copy(commentCount = post.commentCount + 1)
        }
        return newComment
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

    private val cal = Calendar.getInstance()
    private fun daysAgo(days: Int): java.util.Date {
        val c = Calendar.getInstance()
        c.add(Calendar.DAY_OF_YEAR, -days)
        return c.time
    }

    private fun initialPosts() = listOf(
        Post(
            id = 1,
            title = "파이썬 리스트 컴프리헨션이 뭔가요?",
            content = "파이썬을 처음 공부하는데 리스트 컴프리헨션이라는 게 자꾸 나오네요. 일반 for문이랑 무슨 차이가 있는지 모르겠어요. 쉽게 설명해 주실 수 있을까요?",
            author = "python_newbie", authorLevel = UserLevel.BEGINNER,
            language = ProgrammingLanguage.PYTHON, category = PostCategory.QUESTION,
            likeCount = 24, commentCount = 8, viewCount = 312,
            createdAt = daysAgo(1), tags = listOf("list", "comprehension", "기초"),
            codeSnippet = "# 일반 for문\nresult = []\nfor i in range(10):\n    result.append(i * 2)\n\n# 이게 리스트 컴프리헨션인가요?\nresult = [i * 2 for i in range(10)]"
        ),
        Post(
            id = 2,
            title = "코틀린 코루틴 vs Java 스레드 어떤 게 나은가요?",
            content = "안드로이드 개발을 배우고 있는데 비동기 처리 방법으로 코루틴과 스레드 중 어떤 걸 사용해야 할지 고민입니다.",
            author = "android_dev", authorLevel = UserLevel.JUNIOR,
            language = ProgrammingLanguage.KOTLIN, category = PostCategory.DISCUSSION,
            likeCount = 47, commentCount = 15, viewCount = 892,
            createdAt = daysAgo(2), tags = listOf("coroutine", "thread", "async")
        ),
        Post(
            id = 3,
            title = "JavaScript 비동기 처리 정리 (Promise, async/await)",
            content = "자바스크립트 비동기 처리 방식을 공부하면서 정리한 내용을 공유합니다.",
            author = "js_master", authorLevel = UserLevel.MID,
            language = ProgrammingLanguage.JAVASCRIPT, category = PostCategory.SHARING,
            likeCount = 89, commentCount = 23, viewCount = 1543,
            createdAt = daysAgo(3), tags = listOf("Promise", "async", "await"),
            codeSnippet = "// async/await 방식\nasync function getData() {\n  try {\n    const res = await fetch('/api/data');\n    const data = await res.json();\n    console.log(data);\n  } catch (err) {\n    console.error(err);\n  }\n}"
        ),
        Post(
            id = 4,
            title = "Java NullPointerException 해결 도와주세요",
            content = "Spring Boot 프로젝트에서 계속 NullPointerException이 발생합니다.",
            author = "spring_beginner", authorLevel = UserLevel.BEGINNER,
            language = ProgrammingLanguage.JAVA, category = PostCategory.QUESTION,
            likeCount = 13, commentCount = 6, viewCount = 245,
            createdAt = daysAgo(1), tags = listOf("Spring Boot", "NullPointer"),
            codeSnippet = "@Service\npublic class UserService {\n    private UserRepository userRepository; // @Autowired 빠짐!\n}"
        ),
        Post(
            id = 5,
            title = "C++ 포인터 기초 스터디 모집합니다",
            content = "C++ 포인터와 메모리 관리를 함께 공부할 분들 모집합니다! 매주 토요일 온라인으로 진행합니다.",
            author = "cpp_study", authorLevel = UserLevel.JUNIOR,
            language = ProgrammingLanguage.CPP, category = PostCategory.RECRUIT,
            likeCount = 31, commentCount = 18, viewCount = 567,
            createdAt = daysAgo(4), tags = listOf("스터디", "포인터")
        ),
        Post(
            id = 6,
            title = "SQL 인덱스 언제 어떻게 걸어야 하나요?",
            content = "데이터베이스 쿼리 최적화를 공부 중인데 인덱스를 언제 어떤 컬럼에 걸어야 하는지 헷갈립니다.",
            author = "db_newbie", authorLevel = UserLevel.BEGINNER,
            language = ProgrammingLanguage.SQL, category = PostCategory.QUESTION,
            likeCount = 38, commentCount = 11, viewCount = 678,
            createdAt = daysAgo(3), tags = listOf("INDEX", "쿼리최적화", "MySQL")
        ),
        Post(
            id = 7,
            title = "Go 언어로 REST API 서버 만들기 후기",
            content = "사이드 프로젝트로 Go 언어를 처음 사용해서 REST API 서버를 만들어봤습니다.",
            author = "gopher", authorLevel = UserLevel.MID,
            language = ProgrammingLanguage.GO, category = PostCategory.SHARING,
            likeCount = 56, commentCount = 12, viewCount = 987,
            createdAt = daysAgo(5), tags = listOf("Go", "REST API", "gin")
        ),
        Post(
            id = 8,
            title = "이 파이썬 코드 더 파이썬답게 리뷰 부탁드립니다",
            content = "알고리즘 문제를 풀었는데 코드가 너무 지저분한 것 같아요.",
            author = "algo_study", authorLevel = UserLevel.BEGINNER,
            language = ProgrammingLanguage.PYTHON, category = PostCategory.REVIEW,
            likeCount = 19, commentCount = 7, viewCount = 334,
            createdAt = daysAgo(2), tags = listOf("코드리뷰", "알고리즘"),
            codeSnippet = "def find_max(nums):\n    max_val = nums[0]\n    for i in range(len(nums)):\n        if nums[i] > max_val:\n            max_val = nums[i]\n    return max_val"
        )
    )

    private fun initialComments() = listOf(
        Comment(
            id = 1, postId = 1, author = "python_expert",
            authorLevel = UserLevel.SENIOR,
            content = "리스트 컴프리헨션은 리스트를 더 간결하고 파이썬답게 만드는 방법입니다.",
            likeCount = 15, isAccepted = true,
            codeSnippet = "squares = [x**2 for x in range(10)]\neven_squares = [x**2 for x in range(10) if x % 2 == 0]"
        ),
        Comment(
            id = 2, postId = 1, author = "senior_dev",
            authorLevel = UserLevel.MID,
            content = "간단한 변환은 컴프리헨션, 복잡한 로직은 일반 for문이 가독성에 좋습니다.",
            likeCount = 8
        ),
        Comment(
            id = 3, postId = 4, author = "spring_master",
            authorLevel = UserLevel.SENIOR,
            content = "@Autowired 어노테이션이 없어서 DI가 안 되고 있습니다. 생성자 주입 방식을 권장드립니다.",
            likeCount = 22, isAccepted = true,
            codeSnippet = "@Service\npublic class UserService {\n    private final UserRepository userRepository;\n    public UserService(UserRepository userRepository) {\n        this.userRepository = userRepository;\n    }\n}"
        )
    )
}
