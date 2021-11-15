package com.onion.android.app.plex.data.local;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.onion.android.app.plex.data.local.converters.CastConverter;
import com.onion.android.app.plex.data.local.converters.GenreConverter;
import com.onion.android.app.plex.data.local.converters.MediaStreamConverter;
import com.onion.android.app.plex.data.local.converters.MediaSubstitlesConverter;
import com.onion.android.app.plex.data.local.converters.SaisonConverter;
import com.onion.android.app.plex.data.local.converters.VideosConverter;
import com.onion.android.app.plex.data.local.dao.DownloadDao;
import com.onion.android.app.plex.data.local.dao.FavoriteDao;
import com.onion.android.app.plex.data.local.dao.HistoryDao;
import com.onion.android.app.plex.data.local.dao.StreamListDao;
import com.onion.android.app.plex.data.local.entity.Download;
import com.onion.android.app.plex.data.local.entity.History;
import com.onion.android.app.plex.data.local.entity.Media;
import com.onion.android.app.plex.data.local.entity.Stream;

/**
 * Room数据库总处理
 * 这个类用@Database注释，列出数据库中包含的实体以及访问它们的dao,Room
 * @Database 表面该类为数据库
 *  1. entities: 表面所访问的数据表
 *  2. version: 数据库版本
 *  3. exportSchema: 在默认情况下为true，但是当您不想保留版本的历史记录（如仅在内存中的数据库）时，可以对数据库禁用它
 * @TypeConverter 指定Room可以使用的类型转换器
 * 如果您将它放到数据库中，那么该数据库中的所有dao和实体都将能够使用它。
 * 如果您将它放在一个Dao上，那么Dao中的所有方法都将能够使用它。
 * 如果您将它放在一个实体上，该实体的所有字段都可以使用它。
 * 如果你把它放在POJO上，POJO的所有字段都可以使用它。
 * 如果你把它放在一个实体字段，只有那个字段将能够使用它。
 * 如果您将它放在Dao方法上，那么该方法的所有参数都将能够使用它。
 * 如果将其放在Dao方法参数上，则只有该字段能够使用它
 * */
@Database(entities = {Media.class, Download.class, History.class, Stream.class}, version = 2, exportSchema = false)
@TypeConverters({GenreConverter.class,
        CastConverter.class,
        VideosConverter.class,
        SaisonConverter.class,
        MediaSubstitlesConverter.class,
        MediaStreamConverter.class})
public abstract class EasyPlexDataBase extends RoomDatabase {
    public abstract FavoriteDao favoriteDao();
    public abstract DownloadDao progressDao();
    public abstract HistoryDao historyDao();
    public abstract StreamListDao streamListDao();
}
