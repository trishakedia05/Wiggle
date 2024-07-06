package com.plcoding.repository.likes

import com.plcoding.data.models.Comment
import com.plcoding.data.models.Like
import com.plcoding.data.models.Post
import com.plcoding.data.models.User
import com.plcoding.data.util.ParentType
import com.plcoding.util.Constants
import org.litote.kmongo.and
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq
import org.litote.kmongo.setValue

class LikesRepositoryImpl (
    db: CoroutineDatabase
) :LikesRepository {
    val likes = db.getCollection<Like>()
    val users = db.getCollection<User>()
    private val comments = db.getCollection<Comment>()
    private val posts = db.getCollection<Post>()

    override suspend fun likeParent(userId: String, parentId: String, parentType: Int)
    : Boolean
    {
        val doesUserExist = users.findOneById(userId) != null
        //print("\n $doesUserExist \n")
        return if(doesUserExist) {
            when(parentType) {
                ParentType.Post.type -> {
                    val post = posts.findOneById(parentId) ?: return false
                    print("\n post $post \n")
                    posts.updateOneById(
                        id = parentId,
                        update = setValue(Post::likeCount, post.likeCount + 1)
                    )
                }
                ParentType.Comment.type -> {
                    val comment = comments.findOneById(parentId) ?: return false
                    print("\n comment $comment \n")
                    comments.updateOneById(
                        id = parentId,
                        update = setValue(Comment::likeCount, comment.likeCount + 1)
                    )
                print(comments.findOneById(parentId))
                }
            }
            likes.insertOne(Like(userId, parentId, parentType, System.currentTimeMillis()))
            true
        }
        else{ false }
        }

    override suspend fun unlikeParent(userId: String, parentId: String, parentType: Int): Boolean {
        val doesUserExist = users.findOneById(userId) != null
        return if(doesUserExist) {
            when(parentType) {
                ParentType.Post.type -> {
                    val post = posts.findOneById(parentId) ?: return false
                    posts.updateOneById(
                        id = parentId,
                        update = setValue(Post::likeCount, (post.likeCount - 1).coerceAtLeast(0))
                    )
                }
                ParentType.Comment.type -> {
                    val comment = comments.findOneById(parentId) ?: return false
                    comments.updateOneById(
                        id = parentId,
                        update = setValue(Comment::likeCount, (comment.likeCount - 1).coerceAtLeast(0))
                    )
                }
            }
            likes.deleteOne(
                and(
                    Like::userId eq userId,
                    Like::parentId eq parentId
                )
            )
            true
        }
        else{ false }
    }

    override suspend fun deleteLikesForParent(parentId: String){
        likes.deleteMany(Like::parentId eq parentId)
    }

    override suspend fun getLikesForParent(parentId: String,page: Int,pageSize: Int): List<Like> {
        return likes
            .find(Like::parentId eq parentId)
            .skip(page*pageSize)
            .limit(pageSize)
            .descendingSort(Like::timestamp)
            .toList()
    }
}
