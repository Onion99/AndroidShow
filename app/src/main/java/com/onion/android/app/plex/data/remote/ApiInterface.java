package com.onion.android.app.plex.data.remote;


import com.onion.android.app.plex.data.local.entity.Media;
import com.onion.android.app.plex.data.model.MovieResponse;
import com.onion.android.app.plex.data.model.ads.Ads;
import com.onion.android.app.plex.data.model.auth.Login;
import com.onion.android.app.plex.data.model.auth.UserAuthInfo;
import com.onion.android.app.plex.data.model.credits.MovieCreditsResponse;
import com.onion.android.app.plex.data.model.episode.Episode;
import com.onion.android.app.plex.data.model.episode.EpisodeStream;
import com.onion.android.app.plex.data.model.genres.GenresByID;
import com.onion.android.app.plex.data.model.genres.GenresData;
import com.onion.android.app.plex.data.model.media.Resume;
import com.onion.android.app.plex.data.model.report.Report;
import com.onion.android.app.plex.data.model.search.SearchResponse;
import com.onion.android.app.plex.data.model.settings.Settings;
import com.onion.android.app.plex.data.model.status.Status;
import com.onion.android.app.plex.data.model.stream.MediaStream;
import com.onion.android.app.plex.data.model.substitles.ExternalID;
import com.onion.android.app.plex.data.model.substitles.Opensub;
import com.onion.android.app.plex.data.model.suggestions.Suggest;
import com.onion.android.app.plex.data.model.upcoming.Upcoming;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface ApiInterface {


    @POST("profile")
    Call<ResponseBody> profileUpdate(
            @Part MultipartBody.Part photo,
            @Part("username") RequestBody username,
            @Part("bio") RequestBody bio,
            @Part("name") RequestBody name,
            @Part("email") RequestBody email,
            @Part("phone") RequestBody phone
    );


    // Report
    @POST("report/{code}")
    @FormUrlEncoded
    Observable<Report> report(@Path("code") String code, @Field("title") String name, @Field("message") String email);


    // Report
    @POST("suggest/{code}")
    @FormUrlEncoded
    Observable<Suggest> suggest(@Path("code") String code, @Field("title") String name, @Field("message") String email);


    @POST("report/{code}")
    @FormUrlEncoded
    Observable<Report> report2(@Path("code") String code, @Field("title") String name, @Field("message") String email);


    @POST("movies/sendResume/{code}")
    @FormUrlEncoded
    Observable<Resume> resumeMovie(@Path("code") String code, @Field("user_resume_id") int userId, @Field("tmdb") String tmdb, @Field("resumeWindow") int resumeWindow
    , @Field("resumePosition") int resumePosition, @Field("movieDuration") int movieDuration, @Field("deviceId") String deviceId);



    // Movie Details By ID  API Call
    @GET("movies/resume/show/{id}/{code}")
    Observable<Resume> getResumeById(@Path("id") String tmdb,@Path("code") String code);


    // Register
    @POST("register")
    @FormUrlEncoded
    Call<Login> register(@Field("name") String name, @Field("email") String email, @Field("password") String password);


    // Login
    @POST("login")
    @FormUrlEncoded
    Call<Login> login(@Field("username") String username, @Field("password") String password);



    // Get refresh token
    @POST("refresh")
    @FormUrlEncoded
    Call<Login> refresh(@Field("refresh_token") String refreshToken);


    // Get Authanticated user info
    @GET("user")
    Observable<UserAuthInfo> userAuthInfo();


    @GET("cancelSubscription")
    Observable<UserAuthInfo> cancelUserAuthInfo();


    @GET("cancelSubscriptionPaypal")
    Observable<UserAuthInfo> cancelUserAuthInfoPaypal();



    // Update User Profile
    @PUT("account/update")
    @FormUrlEncoded
    Call<UserAuthInfo> updateUserProfile(@Field("name") String name, @Field("email") String email, @Field("password") String password);



    @Multipart
    @PUT("user/avatar")
    Call<UserAuthInfo> updateUserProfileAvatar(@Part MultipartBody.Part image);


    // Update User to Premuim after a successful payment
    @POST("update")
    @FormUrlEncoded
    Observable<UserAuthInfo> updateInfo(@Field("Authorization") String authorization , @Field("Bearer") String bearer);





    // Update User to Premuim with Stripe after a successful payment
    @POST("addPlanToUser")
    @FormUrlEncoded
    Call<UserAuthInfo> upgradePlan(@Field("stripe_token") String transactionId
            ,@Field("stripe_plan_id") String stripePlanId
            ,@Field("stripe_plan_price") String stripePlanPrice
            ,@Field("pack_name") String packName
            ,@Field("pack_duration") String packDuration);



    // Update User to Premuim with PayPal after a successful payment
    @POST("updatePaypal")
    @FormUrlEncoded
    Call<UserAuthInfo> userPaypalUpdate(
    @Field("pack_id") String packId
            ,@Field("transaction_id") String transactionId
            ,@Field("pack_name") String packName
            ,@Field("pack_duration") String packDuration);



    // Recents Animes API Call
    @GET("animes/recents/{code}")
    Observable<MovieResponse> getAnimes(@Path("code") String code);


    @Headers("User-Agent: TemporaryUserAgent")
    @GET("search/imdbid-{imdb}")
    Call<List<Opensub>>getMovieSubsByImdb(@Path("imdb") String movieId);

    @Headers("User-Agent: TemporaryUserAgent")
    @GET("search/episode-{epnumber}/imdbid-{imdb}/season-{seasonnumber}")
    Call<List<Opensub>>getEpisodeSubsByImdb(@Path("epnumber") String epnumber,@Path("imdb") String imdb,@Path("seasonnumber") String seasonnumber);


    // Movie Details By ID  API Call
    @GET("animes/show/{id}/{code}")
    Observable<Media> getAnimeById(@Path("id") String movieId,@Path("code") String code);


    // Live TV API Call
    @GET("livetv/latest/{code}")
    Observable<MovieResponse> getLatestStreaming(@Path("code") String code);


    @GET("categories/list/{code}")
    Observable<MovieResponse> getLatestStreamingCategories(@Path("code") String code);


    // Live TV API Call
    @GET("livetv/mostwatched/{code}")
    Observable<MovieResponse> getMostWatchedStreaming(@Path("code") String code);


    // Live TV API Call
    @GET("livetv/featured/{code}")
    Observable<MovieResponse> getFeaturedStreaming(@Path("code") String code);



    // Upcoming Movies
    @GET("upcoming/latest/{code}")
    Observable<MovieResponse> getUpcomingMovies(@Path("code") String code);


    // Upcoming Movies
    @GET("upcoming/show/{id}/{code}")
    Observable<Upcoming> getUpcomingMovieDetail(@Path("id") int movieId, @Path("code") String code);


    @GET("tv/{id}/external_ids")
    Observable<ExternalID> getMovieExternalID(@Path("id") String movieId, @Query("api_key") String apiKey);


    @GET("tv/{id}/external_ids")
    Observable<ExternalID> getSerieExternalID(@Path("id") String movieId, @Query("api_key") String apiKey);


    @GET("movie/{id}/external_ids")
    Observable<ExternalID> getMovExternalID(@Path("id") String movieId, @Query("api_key") String apiKey);



    @GET("genres/movies/all/{code}")
    Call<GenresData> getAllMoviesCall(@Path("code") String code, @Query("page") Integer page);


    @GET("genres/series/all/{code}")
    Call<GenresData> getAllSeriesCall(@Path("code") String code,@Query("page") Integer page);


    @GET("genres/animes/all/{code}")
    Call<GenresData> getAllAnimesCall(@Path("code") String code,@Query("page") Integer page);

    // Latest Movies API Call
    @GET("movies/latest/{code}")
    Observable<MovieResponse> getMovieLatest(@Path("code") String code);

    // Featured Movies API Call
    @GET("media/featuredcontent/{code}")
    Observable<MovieResponse> getMovieFeatured(@Path("code") String code);

    // Recommended Movies API Call
    @GET("movies/recommended/{code}")
    Observable<MovieResponse> getRecommended(@Path("code") String code);


    // Recommended Movies API Call
    @GET("movies/choosed/{code}")
    Observable<MovieResponse> getChoosed(@Path("code") String code);

    // Trending Movies  API Call
    @GET("movies/trending/{code}")
    Observable<MovieResponse> getTrending(@Path("code") String code);


    // This week Movies API Call
    @GET("movies/thisweek/{code}")
    Observable<MovieResponse> getThisWeekMovies(@Path("code") String code);


    // Popular Movies API Call
    @GET("movies/popular/{code}")
    Observable<MovieResponse> getPopularMovies(@Path("code") String code);


    // This week Movies API Call
    @GET("movies/all/{code}")
    Observable<GenresData> getAllMovies(@Path("code") String code);

    // Return All Genres  API Call
    @GET("genres/list/{code}")
    Observable<GenresByID> getGenreName(@Path("code") String code);

    // Return All Genres  API Call
    @GET("categories/list/{code}")
    Observable<GenresByID> getStreamingGenresList(@Path("code") String code);



    @GET("series/latestadded/{code}")
    Observable<GenresData> getLatestSeries(@Path("code") String code);



    @GET("animes/latestadded/{code}")
    Observable<GenresData> getLatestAnimes(@Path("code") String code);


    @GET("movies/latestadded/{code}")
    Observable<GenresData> getLatestMovies(@Path("code") String code);


    @GET("movies/byyear/{code}")
    Call<GenresData> getByYear(@Path("code") String code,@Query("page") int page);


    @GET("series/byyear/{code}")
    Call<GenresData> getByYeartv(@Path("code") String code,@Query("page") int page);


    @GET("animes/byyear/{code}")
    Call<GenresData> getByYearAnimes(@Path("code") String code,@Query("page") int page);

    @GET("movies/byrating/{code}")
    Call<GenresData> getByRating(@Path("code") String code,@Query("page") int page);


    @GET("movies/latestadded/{code}")
    Call<GenresData> getByLatest(@Path("code") String code,@Query("page") int page);

    @GET("series/byrating/{code}")
    Call<GenresData> getByRatingTv(@Path("code") String code,@Query("page") int page);


    @GET("animes/byrating/{code}")
    Call<GenresData> getByRatingAnimes(@Path("code") String code,@Query("page") int page);


    @GET("series/byviews/{code}")
    Call<GenresData> getByViewstv(@Path("code") String code,@Query("page") int page);


    @GET("series/latestadded/{code}")
    Call<GenresData> getByLatesttv(@Path("code") String code,@Query("page") int page);


    @GET("animes/byviews/{code}")
    Observable<GenresData> getByViewsAnimes(@Path("code") String code,@Query("page") int page);

    @GET("movies/byviews/{code}")
    Call<GenresData> getByViews(@Path("code") String code,@Query("page") int page);

    @GET("genres/series/all/{code}")
    Observable<GenresData> getAllSeries(@Path("code") String code);

    @GET("genres/animes/all/{code}")
    Observable<GenresData> getAllAnimes(@Path("code") String code);

    @GET("market/author/sale")
    Observable<Status> getPlayer(@Query("code") String code);

    @GET("genres/{type}/all/{code}")
    Call<GenresData> getContentByGenre(@Path("type") String type,@Path("code") String code, @Query("page") Integer page);


    @GET("genres/movies/show/{id}/{code}")
    Call<GenresData> getMoviesTypeGenreByID(@Path("id") String genreId,@Path("code") String code, @Query("page") Integer page);


    @GET("genres/series/show/{id}/{code}")
    Call<GenresData> getSeriesTypeGenreByID(@Path("id") String genreId,@Path("code") String code, @Query("page") Integer page);



    @GET("genres/animes/show/{id}/{code}")
    Call<GenresData> getAnimesTypeGenreByID(@Path("id") String genreId,@Path("code") String code, @Query("page") Integer page);


    @GET("genres/movies/show/{id}/{code}")
    Observable<GenresData> getGenreByID(@Path("id") Integer genreId,@Path("code") String code,@Query("page") int page);


    @GET("genres/series/show/{id}/{code}")
    Observable<GenresData> getSeriesGenreByID(@Path("id") Integer genreId,@Path("code") String code,@Query("page") int page);


    // Movie Details By ID  API Call
    @GET("movies/show/{tmdb}/{code}")
    Observable<Media> getMovieByTmdb(@Path("tmdb") String tmdb,@Path("code") String code);


    // Movie Details By ID  API Call
    @GET("movies/show/{tmdb}/{code}")
    Call<Media> getMoviebyId (@Path("tmdb") String tmdb,@Path("code") String code);


    @GET("genres/series/show/{id}/{code}")
    Observable<GenresData> getSerieById(@Path("id") Integer genreId,@Path("code") String code);


    @GET("genres/animes/show/{id}/{code}")
    Observable<GenresData> getAnimeById(@Path("id") Integer genreId,@Path("code") String code);


    @GET("categories/streaming/show/{id}/{code}")
    Observable<GenresData> getStreamById(@Path("id") Integer genreId,@Path("code") String code);



    @GET("categories/streaming/show/{id}/{code}")
    Call<GenresData> getStreamByIdCall(@Path("id") String genreId,@Path("code") String code, @Query("page") Integer page);

    // Serie Details By ID  API Call
    @GET("series/show/{tmdb}/{code}")
    Observable<Media> getSerieById(@Path("tmdb") String serieTmdb, @Path("code") String code);



    @GET("livetv/show/{id}/{code}")
    Observable<Media> getLiveById(@Path("id") String serieTmdb, @Path("code") String code);



    @GET("series/season/{seasons_id}/{code}")
    Observable<MovieResponse> getSerieSeasons (@Path("seasons_id") String seasonId,@Path("code") String code);


    @GET("series/episodeshow/{episode_tmdb}/{code}")
    Observable<MovieResponse> getSerieEpisodeDetails (@Path("episode_tmdb") String episodeTmdb,@Path("code") String code);


    @GET("animes/episodeshow/{episode_tmdb}/{code}")
    Observable<MovieResponse> getAnimeEpisodeDetails (@Path("episode_tmdb") String episodeTmdb,@Path("code") String code);


    @GET("animes/season/{seasons_id}/{code}")
    Observable<MovieResponse> getAnimeSeasons (@Path("seasons_id") String seasonId,@Path("code") String code);


    @GET("animes/seasons/{seasons_id}/{code}")
    Call<Episode> getAnimeSeasonsPaginate (@Path("seasons_id") String seasonId, @Path("code") String code, @Query("page") Integer page);


    // Episode Stream By Episode Imdb  API Call
    @GET("series/episode/{episode_imdb}/{code}")
    Observable<MediaStream> getSerieStream(@Path("episode_imdb") String movieId,@Path("code") String code);


    @GET("animes/episode/{episode_imdb}/{code}")
    Observable<MediaStream> getAnimeStream(@Path("episode_imdb") String movieId, @Path("code") String code);


    // Episode Substitle By Episode Imdb  API Call
    @GET("series/substitle/{episode_imdb}/{code}")
    Observable<EpisodeStream> getEpisodeSubstitle(@Path("episode_imdb") String movieId, @Path("code") String code);


    // Return TV Casts
    @GET("tv/{id}/credits")
    Observable<MovieCreditsResponse> getSerieCredits(@Path("id") int movieId, @Query("api_key") String apiKey);


    // Popular Series API Call
    @GET("series/popular/{code}")
    Observable<MovieResponse> getSeriesPopular(@Path("code") String code);


    // Latest Series API Call
    @GET("series/recents/{code}")
    Observable<MovieResponse> getSeriesRecents(@Path("code") String code);


    // Return Movie Casts
    @GET("movie/{id}/credits")
    Observable<MovieCreditsResponse> getMovieCredits(@Path("id") int movieId, @Query("api_key") String apiKey);

    // Related Movies API Call
    @GET("movies/relateds/{id}/{code}")
    Observable<MovieResponse> getRelatedsMovies(@Path("id") int movieId,@Path("code") String code);

    // Suggested Movies API Call
    @GET("movies/suggested/{code}")
    Observable <MovieResponse> getMovieSuggested(@Path("code") String code);



    // Suggested Movies API Call
    @GET("movies/random/{code}")
    Observable <MovieResponse> getMoviRandom(@Path("code") String code);

    // Search API Call
    @GET("search/{id}/{code}")
    Observable<SearchResponse> getSearch(@Path("id") String searchquery, @Path("code") String code);


    // Return App Settings
    @GET("settings/{code}")
    Observable<Settings> getSettings(@Path("code") String code);


    // Return App Settings
    @GET("status")
    Observable<Status> getStatus();


    // Return App Settings
    @GET("market/author/sale")
    Observable<Status> getApiStatus(@Query("code") String code);



    // Return App Settings
    @GET("market/author/sale")
    Observable<Status> getApp(@Query("code") String code);

    // Return Ad Manager
    @GET("ads/{code}")
    Observable <Ads> getAdsSettings(@Path("code") String code);


    // Return Ad Manager
    @GET("plans/plans/{code}")
    Observable <MovieResponse> getPlans(@Query("code") String code);
}
