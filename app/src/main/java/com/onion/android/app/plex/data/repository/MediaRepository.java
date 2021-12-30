package com.onion.android.app.plex.data.repository;

import static com.onion.android.app.constants.PlexConstants.PURCHASE_KEY;

import android.annotation.SuppressLint;

import androidx.lifecycle.LiveData;

import com.onion.android.app.plex.data.datasource.genreslist.AnimesGenreListDataSource;
import com.onion.android.app.plex.data.datasource.genreslist.AnimesGenresListDataSourceFactory;
import com.onion.android.app.plex.data.datasource.genreslist.ByGenreListDataSource;
import com.onion.android.app.plex.data.datasource.genreslist.ByGenresListDataSourceFactory;
import com.onion.android.app.plex.data.datasource.genreslist.MoviesGenreListDataSource;
import com.onion.android.app.plex.data.datasource.genreslist.MoviesGenresListDataSourceFactory;
import com.onion.android.app.plex.data.datasource.genreslist.SeriesGenreListDataSource;
import com.onion.android.app.plex.data.datasource.genreslist.SeriesGenresListDataSourceFactory;
import com.onion.android.app.plex.data.datasource.stream.StreamDataSource;
import com.onion.android.app.plex.data.datasource.stream.StreamingDataSourceFactory;
import com.onion.android.app.plex.data.local.dao.DownloadDao;
import com.onion.android.app.plex.data.local.dao.FavoriteDao;
import com.onion.android.app.plex.data.local.dao.HistoryDao;
import com.onion.android.app.plex.data.local.dao.ResumeDao;
import com.onion.android.app.plex.data.local.dao.StreamListDao;
import com.onion.android.app.plex.data.local.entity.Download;
import com.onion.android.app.plex.data.local.entity.History;
import com.onion.android.app.plex.data.local.entity.Media;
import com.onion.android.app.plex.data.local.entity.Stream;
import com.onion.android.app.plex.data.model.MovieResponse;
import com.onion.android.app.plex.data.model.credits.MovieCreditsResponse;
import com.onion.android.app.plex.data.model.episode.EpisodeStream;
import com.onion.android.app.plex.data.model.genres.GenresByID;
import com.onion.android.app.plex.data.model.genres.GenresData;
import com.onion.android.app.plex.data.model.media.Resume;
import com.onion.android.app.plex.data.model.search.SearchResponse;
import com.onion.android.app.plex.data.model.status.Status;
import com.onion.android.app.plex.data.model.stream.MediaStream;
import com.onion.android.app.plex.data.model.substitles.ExternalID;
import com.onion.android.app.plex.data.model.upcoming.Upcoming;
import com.onion.android.app.plex.data.remote.ApiInterface;
import com.onion.android.app.plex.manager.SettingsManager;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;
import retrofit2.Call;
import timber.log.Timber;

@Singleton
public class MediaRepository {
    private final FavoriteDao favoriteDao;
    private final DownloadDao downloadDao;
    private final HistoryDao historyDao;
    private final ResumeDao resumeDao;
    private final StreamListDao streamListDao;
    ApiInterface requestMainApi;

    @Inject
    @Named("imdb")
    ApiInterface requestImdbApi;


    @Inject
    SettingsManager settingsManager;

    @Inject
    @Named("app")
    ApiInterface requestAppApi;


    StreamDataSource streamDataSource;
    MoviesGenreListDataSource moviesGenreListDataSource;
    SeriesGenreListDataSource seriesGenreListDataSource;
    AnimesGenreListDataSource animesGenreListDataSource;
    ByGenreListDataSource byGenreListDataSource;

    @Inject
    @Named("player")
    ApiInterface requestStatusApi;

    @Inject
    MediaRepository(FavoriteDao favoriteDao, DownloadDao downloadDao,
                    ApiInterface requestMainApi, ApiInterface requestImdbApi,
                    HistoryDao historyDao, StreamListDao streamListDao,
                    ResumeDao resumeDao
    ) {
        this.favoriteDao = favoriteDao;
        this.downloadDao = downloadDao;
        this.historyDao = historyDao;
        this.streamListDao = streamListDao;
        this.requestMainApi = requestMainApi;
        this.requestImdbApi = requestImdbApi;
        this.resumeDao = resumeDao;
    }




    public StreamingDataSourceFactory streamingDataSourceFactory(String query) {
        return new StreamingDataSourceFactory(streamDataSource, query,settingsManager);
    }


    public MoviesGenresListDataSourceFactory genresListDataSourceFactory(String query) {
        return new MoviesGenresListDataSourceFactory(moviesGenreListDataSource, query,settingsManager);
    }


    public ByGenresListDataSourceFactory byGenresListDataSourceFactory(String query) {
        return new ByGenresListDataSourceFactory(byGenreListDataSource, query,settingsManager);
    }



    public SeriesGenresListDataSourceFactory seriesGenresListDataSourceFactory(String query) {
        return new SeriesGenresListDataSourceFactory(seriesGenreListDataSource, query,settingsManager);
    }


    public AnimesGenresListDataSourceFactory animesGenresListDataSourceFactory(String query) {
        return new AnimesGenresListDataSourceFactory(animesGenreListDataSource, query,settingsManager);
    }



    // Return Movie By Genre
    public Observable<GenresData> getMovieByGenre(int id, String code, int page) {
        return requestMainApi.getGenreByID(id,code,page);
    }

    public Observable<GenresData> getSerieByGenre(int id,String code,int page) {
        return requestMainApi.getSeriesGenreByID(id,code,page);
    }



    // Return Serie Seasons
    public Observable<MovieResponse> getSerieSeasons(String seasonsId, String code) {
        return requestMainApi.getSerieSeasons(seasonsId,code);
    }



    public Observable<MovieResponse> getAnimeEpisodeDetails(String ep,String code) {
        return requestMainApi.getAnimeEpisodeDetails(ep,code);
    }


    public Observable<MovieResponse> getSerieEpisodeDetails(String ep,String code) {
        return requestMainApi.getSerieEpisodeDetails(ep,code);
    }


    public Observable<MovieResponse> getAnimeSeasons(String seasonsId,String code) {
        return requestMainApi.getAnimeSeasons(seasonsId,code);
    }




    // Return Random Movie
    public Observable<MovieResponse> getMoviRandom() {
        return requestMainApi.getMoviRandom(settingsManager.getSettings().getcode());
    }





    // Return Substitle Episode
    public Observable<EpisodeStream> getEpisodeSubstitle(String tmdb, String code) {
        return requestMainApi.getEpisodeSubstitle(tmdb,code);
    }





    // Return Serie Stream
    public Observable<MediaStream> getSerieStream(String tmdb, String code) {
        return requestMainApi.getSerieStream(tmdb,code);
    }



    public Observable<MediaStream> getAnimeStream(String tmdb,String code) {
        return requestMainApi.getAnimeStream(tmdb,code);
    }



    // Return Serie By Id
    public Observable<Media> getSerie(String serieTmdb) {
        return requestMainApi.getSerieById(serieTmdb, settingsManager.getSettings().getcode());
    }


    public Observable<Media> getLiveTvById(String id,String code) {
        return requestMainApi.getLiveById(id,code);
    }





    // Return Anime By Id
    public Observable<MovieResponse> getAnimes() {
        return requestMainApi.getAnimes(settingsManager.getSettings().getcode());
    }



    // Return Upcoming Movie By Id
    public Observable<Upcoming> getUpcomingById(int movieID, String code) {
        return requestMainApi.getUpcomingMovieDetail(movieID,code);

    }



    // Return Upcoming Movies Lists
    public Observable<MovieResponse> getUpcoming() {
        return requestMainApi.getUpcomingMovies(settingsManager.getSettings().getApiKey());

    }



    // Return Relateds Movies for a movie
    public Observable<MovieResponse> getRelateds(int movieID,String code) {
        return requestMainApi.getRelatedsMovies(movieID,code);

    }



    // Return Casts Lists for  Movie
    public Observable<MovieCreditsResponse> getMovieCredits(int movieID) {
        return requestImdbApi.getMovieCredits(movieID,settingsManager.getSettings().getTmdbApiKey());

    }


    public Observable<ExternalID> getExternalId(String movieID) {
        return requestImdbApi.getSerieExternalID(movieID,settingsManager.getSettings().getTmdbApiKey());

    }


    public Observable<ExternalID> getMovieExternal(String movieID) {
        return requestImdbApi.getMovExternalID(movieID,settingsManager.getSettings().getTmdbApiKey());

    }



    // Return Casts Lists for a Serie
    public Observable<MovieCreditsResponse> getSerieCredits(int movieID) {
        return requestImdbApi.getSerieCredits(movieID,settingsManager.getSettings().getTmdbApiKey());

    }



    public Observable<GenresData> getMovieAllMovies() {
        return requestMainApi.getAllMovies(settingsManager.getSettings().getcode());
    }




    // Return Serie By Genre
    public Observable<GenresData> getSerieByGenre(int id) {
        return requestMainApi.getSerieById(id,settingsManager.getSettings().getcode());
    }


    // Return Serie By Genre
    public Observable<GenresData> getAnimeByGenre(int id) {
        return requestMainApi.getAnimeById(id,settingsManager.getSettings().getcode());
    }


    // Return Serie By Genre
    public Observable<GenresData> getStreamingByGenre(int id,String code) {
        return requestMainApi.getStreamById(id,code);
    }




    // Return Movies Genres
    public Observable<GenresByID> getMoviesGenres() {
        return requestMainApi.getGenreName(settingsManager.getSettings().getApiKey());
    }






    // Return Streamings Genres
    public Observable<GenresByID> getStreamingGenres() {
        return requestMainApi.getStreamingGenresList(settingsManager.getSettings().getApiKey());
    }







    public Observable<Resume> getResumeMovie(String code, int userId, String tmdb, int resumeWindow, int resumePosition, int movieDuration, String deviceId) {
        return requestMainApi.resumeMovie(code,userId,tmdb,resumeWindow,resumePosition,movieDuration,deviceId);
    }



    // Return Anime Details By Id
    public Observable<Resume> getResumeById(String tmdb,String code) {
        return requestMainApi.getResumeById(tmdb,code);
    }




    // Return Movie Detail by Id
    public Observable<Media> getMovie(String tmdb, String code) {
        return requestMainApi.getMovieByTmdb(tmdb,code);
    }



    // Return Popular Series for HomeFragment
    public Observable<MovieResponse> getPopularSeries() {
        return requestMainApi.getSeriesPopular(settingsManager.getSettings().getcode());
    }



    // Return ThisWeek Movies & Series for HomeFragment
    public Observable<MovieResponse> getThisWeek() {
        return requestMainApi.getThisWeekMovies(settingsManager.getSettings().getcode());
    }




    // Return All Movies for HomeFragment
    public Call<GenresData> getAllMovies(String code, int page) {
        return requestMainApi.getAllMoviesCall(code,page);
    }



    public Observable<GenresData> getLatestAdded() {
        return requestMainApi.getLatestMovies(settingsManager.getSettings().getApiKey());
    }
    public Observable<GenresData> getLatestAddedSeries() {
        return requestMainApi.getLatestSeries(settingsManager.getSettings().getApiKey());
    }


    public Observable<GenresData> getLatestAddedAnimes() {
        return requestMainApi.getLatestAnimes(settingsManager.getSettings().getApiKey());
    }




    // Return All Movies for HomeFragment
    public Observable<GenresData> getAllSeries() {
        return requestMainApi.getAllSeries(settingsManager.getSettings().getApiKey());
    }


    public Observable<GenresData> getAllAnimes() {
        return requestMainApi.getAllAnimes(settingsManager.getSettings().getApiKey());
    }


    // Return Popular Movies for HomeFragment
    public Observable<MovieResponse> getPopularMovies() {
        return requestMainApi.getPopularMovies(settingsManager.getSettings().getcode());
    }



    // Return Latest Series for HomeFragment
    public Observable<MovieResponse> getLatestSeries() {
        return requestMainApi.getSeriesRecents(settingsManager.getSettings().getcode());
    }



    // Return Featured Movies for HomeFragment
    public Observable<MovieResponse> getFeatured() {
        return requestMainApi.getMovieFeatured(settingsManager.getSettings().getcode());
    }




    // Return Recommended Series for HomeFragment
    public Observable<MovieResponse> getRecommended() {
        return requestMainApi.getRecommended(settingsManager.getSettings().getcode());
    }



    // Return Choosed Series & Movies for HomeFragment
    public Observable<MovieResponse> getChoosed() {
        return requestMainApi.getChoosed(settingsManager.getSettings().getcode());
    }



    // Return Tranding Movies for HomeFragment
    public Observable<MovieResponse> getTrending() {
        return requestMainApi.getTrending(settingsManager.getSettings().getcode());
    }




    // Return Latest Movies for HomeFragment
    public Observable<MovieResponse> getLatestMovies() {
        return requestMainApi.getMovieLatest(settingsManager.getSettings().getcode());
    }




    // Return Suggested Movies for HomeFragment
    public Observable<MovieResponse> getSuggested() {
        return requestMainApi.getMovieSuggested(settingsManager.getSettings().getcode());
    }



    // Handle Search
    public Observable<SearchResponse> getSearch(String query, String code) {
        return requestMainApi.getSearch(query,code);
    }



    // Return Latest Streaming Channels for HomeFragment
    public Observable<MovieResponse> getLatestStreaming() {
        return requestMainApi.getLatestStreaming(settingsManager.getSettings().getcode());
    }


    public Observable<MovieResponse> getLatestStreamingCategories() {
        return requestMainApi.getLatestStreamingCategories(settingsManager.getSettings().getApiKey());
    }

    public Observable<MovieResponse> getWatchedStreaming() {
        return requestMainApi.getMostWatchedStreaming(settingsManager.getSettings().getApiKey());
    }


    // Return Latest Streaming Channels for HomeFragment
    public Observable<MovieResponse> getFeaturedStreaming() {
        return requestMainApi.getFeaturedStreaming(settingsManager.getSettings().getApiKey());
    }





    // Add Movie or Serie in favorite
    @SuppressLint("TimberArgCount")
    public void addResumeMovie(Download download) {
        Timber.i("Removing to database", download.getTmdbId(), download.getResumePosition());
        downloadDao.saveMediaToFavorite(download);
    }


    // Add Movie or Serie in favorite
    public void addFavorite(Media mediaDetail) {
        favoriteDao.saveMediaToFavorite(mediaDetail);
    }

    // Add Movie or Serie in favorite
    public void addStreamFavorite(Stream stream) {
        streamListDao.saveMediaToFavorite(stream);
    }


    public void addFavorite1(Download mediaDetail) {
        favoriteDao.saveMediaToFavorite1(mediaDetail);
    }


    public void addhistory(History history) {
        historyDao.saveMediaToFavorite(history);
    }



    // Remove Movie or Serie from favorite
    public void removeFavorite(Media mediaDetail) {
        Timber.i("Removing %s to database", mediaDetail.getTitle());
        favoriteDao.deleteMediaFromFavorite(mediaDetail);
    }



    public void removeHistory(History history) {
        Timber.i("Removing %s to database", history.getTitle());
        historyDao.deleteMediaFromHistory(history);
    }


    // Remove Movie or Serie from favorite
    public void removeStreamFavorite(Stream stream) {
        Timber.i("Removing %s to database", stream.getTitle());
        streamListDao.deleteStream(stream);
    }


    // Remove Movie from Download
    public void removeDownload(Download download) {
        Timber.i("Removing %s to database", download.getTitle());
        downloadDao.deleteMediaFromDownload(download);
    }

    public Observable<Status> getPlayer() {
        return requestStatusApi.getPlayer(PURCHASE_KEY);
    }



    // Return Favorite Lists of Movies or Series
    public Flowable<List<Media>> getFavorites() {
        return favoriteDao.getFavoriteMovies();
    }


    public Flowable<List<Stream>> getStreamFavorites() {
        return streamListDao.getFavorite();
    }



    // Return Download Lists of Movies or Series
    public Flowable<List<History>> getwatchHistory() {
        return historyDao.getHistory();
    }


    // Return Download Lists of Movies or Series
    public Flowable<List<History>> getHistoryByTmdb(int imdb) {
        return historyDao.getHistoryByTmdb(imdb);
    }



    // Return Download Lists of Movies or Series
    public Flowable<List<Download>> getDownloads() {
        return downloadDao.getDownloadMovies();
    }


    // Delete All Movies & Series from Favorite Table
    public void deleteAllFromFavorites() {
        favoriteDao.deleteMediaFromFavorite();
    }



    // Delete All History from history Table
    public void deleteAllHistory() {
        historyDao.deleteHistory();
    }




    // Return if the movie or serie is in favorite table
    public LiveData<Media> isFavorite(int movieid) {
        return favoriteDao.isFavoriteMovie(movieid);
    }


    public LiveData<History> isHistory(int movieid) {
        return historyDao.isHistory(movieid);
    }


    public boolean isHistorytv(int movieid) {
        return historyDao.isHistorytv(movieid);
    }



    public boolean isFeaturedFavorite(int movieid) {
        return favoriteDao.isFeaturedFavoriteMovie(movieid);
    }


    public boolean hasHistory(int movieid) {
        return historyDao.hasHistory(movieid);
    }

    public boolean hasHistory3(int movieid) {
        return historyDao.hasHistory3(movieid);
    }


    public LiveData<History> hasHistory2(int movieid) {
        return historyDao.hasHistory2(movieid);
    }


    public boolean isStreamFavorite(int movieid) {
        return streamListDao.isStreamFavoriteMovie(movieid);
    }

    // Add Movie or Serie in favorite
    public void addResume(Resume resume) {
        resumeDao.saveMediaToResume(resume);
    }

}
