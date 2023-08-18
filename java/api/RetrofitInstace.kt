
import com.zoltanlorinczi.project_retrofit.api.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitInstance {

    private val retrofit = Retrofit.Builder()
        .baseUrl(BackendConstants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()


    val USER_API_SERVICE: UserApiService by lazy {
        retrofit.create(UserApiService::class.java)
    }
    val TASK_API_SERVICE: TaskApiService by lazy {
        retrofit.create(TaskApiService::class.java)
    }
    val ACTIVITY_API_SERVICE: ActivityApiService by lazy {
        retrofit.create(ActivityApiService::class.java)
    }
    val GROUP_API_SERVICE: GroupApiService by lazy {
        retrofit.create(GroupApiService::class.java)
    }
}