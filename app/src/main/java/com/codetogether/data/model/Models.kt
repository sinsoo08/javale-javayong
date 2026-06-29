package com.codetogether.data.model

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class Post(
    val id: Int,
    val title: String,
    val content: String,
    val author: String,
    val authorLevel: UserLevel,
    val language: ProgrammingLanguage,
    val category: PostCategory,
    val likeCount: Int,
    val commentCount: Int,
    val viewCount: Int,
    val createdAt: Date = Date(),
    val tags: List<String> = emptyList(),
    val codeSnippet: String? = null
) {
    fun getFormattedDate(): String {
        val sdf = SimpleDateFormat("yyyy.MM.dd HH:mm", Locale.KOREA)
        return sdf.format(createdAt)
    }
}

data class Comment(
    val id: Int,
    val postId: Int,
    val author: String,
    val authorLevel: UserLevel,
    val content: String,
    val likeCount: Int,
    val isAccepted: Boolean = false,
    val createdAt: Date = Date(),
    val codeSnippet: String? = null
) {
    fun getFormattedDate(): String {
        val sdf = SimpleDateFormat("MM.dd HH:mm", Locale.KOREA)
        return sdf.format(createdAt)
    }
}

data class User(
    val id: Int,
    val nickname: String,
    val email: String,
    val level: UserLevel,
    val bio: String = "",
    val favoriteLanguages: List<ProgrammingLanguage> = emptyList(),
    val postCount: Int = 0,
    val answerCount: Int = 0,
    val likeReceived: Int = 0
)

data class CommunityChannel(
    val language: ProgrammingLanguage,
    val memberCount: Int,
    val postCount: Int,
    val description: String
)

enum class ProgrammingLanguage(val displayName: String, val colorHex: String) {
    PYTHON("Python", "#3776AB"),
    JAVA("Java", "#F89820"),
    KOTLIN("Kotlin", "#7F52FF"),
    JAVASCRIPT("JavaScript", "#F7DF1E"),
    CPP("C/C++", "#00599C"),
    SWIFT("Swift", "#FA7343"),
    GO("Go", "#00ADD8"),
    RUST("Rust", "#DEA584"),
    SQL("SQL", "#336791"),
    WEB("HTML/CSS", "#E44D26"),
    GENERAL("전체", "#607D8B")
}

enum class PostCategory(val displayName: String) {
    QUESTION("질문"),
    DISCUSSION("토론"),
    SHARING("공유"),
    REVIEW("코드리뷰"),
    RECRUIT("스터디모집")
}

enum class UserLevel(val displayName: String, val minPoints: Int) {
    BEGINNER("입문자", 0),
    JUNIOR("주니어", 100),
    MID("미드레벨", 500),
    SENIOR("시니어", 2000),
    EXPERT("전문가", 5000)
}
