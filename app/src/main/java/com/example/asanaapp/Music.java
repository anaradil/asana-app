package com.example.asanaapp;

/**
 * Created by Usser on 20.11.2016.
 */
import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.persistence.BackendlessDataQuery;

public class Music
{

    private String ownerId;
    private String objectId;
    private String MusicFile;
    private String Name;


    public String getOwnerId()
    {
        return ownerId;
    }

    public String getObjectId()
    {
        return objectId;
    }

    public String getMusicFile()
    {
        return MusicFile;
    }

    public void setMusicFile( String MusicFile )
    {
        this.MusicFile = MusicFile;
    }

    public String getName()
    {
        return Name;
    }

    public void setName( String Name )
    {
        this.Name = Name;
    }


    public Music save()
    {
        return Backendless.Data.of( Music.class ).save( this );
    }

    public Future<Music> saveAsync()
    {
        if( Backendless.isAndroid() )
        {
            throw new UnsupportedOperationException( "Using this method is restricted in Android" );
        }
        else
        {
            Future<Music> future = new Future<Music>();
            Backendless.Data.of( Music.class ).save( this, future );

            return future;
        }
    }

    public void saveAsync( AsyncCallback<Music> callback )
    {
        Backendless.Data.of( Music.class ).save( this, callback );
    }

    public Long remove()
    {
        return Backendless.Data.of( Music.class ).remove( this );
    }

    public Future<Long> removeAsync()
    {
        if( Backendless.isAndroid() )
        {
            throw new UnsupportedOperationException( "Using this method is restricted in Android" );
        }
        else
        {
            Future<Long> future = new Future<Long>();
            Backendless.Data.of( Music.class ).remove( this, future );

            return future;
        }
    }

    public void removeAsync( AsyncCallback<Long> callback )
    {
        Backendless.Data.of( Music.class ).remove( this, callback );
    }

    public static Music findById( String id )
    {
        return Backendless.Data.of( Music.class ).findById( id );
    }

    public static Future<Music> findByIdAsync( String id )
    {
        if( Backendless.isAndroid() )
        {
            throw new UnsupportedOperationException( "Using this method is restricted in Android" );
        }
        else
        {
            Future<Music> future = new Future<Music>();
            Backendless.Data.of( Music.class ).findById( id, future );

            return future;
        }
    }

    public static void findByIdAsync( String id, AsyncCallback<Music> callback )
    {
        Backendless.Data.of( Music.class ).findById( id, callback );
    }

    public static Music findFirst()
    {
        return Backendless.Data.of( Music.class ).findFirst();
    }

    public static Future<Music> findFirstAsync()
    {
        if( Backendless.isAndroid() )
        {
            throw new UnsupportedOperationException( "Using this method is restricted in Android" );
        }
        else
        {
            Future<Music> future = new Future<Music>();
            Backendless.Data.of( Music.class ).findFirst( future );

            return future;
        }
    }

    public static void findFirstAsync( AsyncCallback<Music> callback )
    {
        Backendless.Data.of( Music.class ).findFirst( callback );
    }

    public static Music findLast()
    {
        return Backendless.Data.of( Music.class ).findLast();
    }

    public static Future<Music> findLastAsync()
    {
        if( Backendless.isAndroid() )
        {
            throw new UnsupportedOperationException( "Using this method is restricted in Android" );
        }
        else
        {
            Future<Music> future = new Future<Music>();
            Backendless.Data.of( Music.class ).findLast( future );

            return future;
        }
    }

    public static void findLastAsync( AsyncCallback<Music> callback )
    {
        Backendless.Data.of( Music.class ).findLast( callback );
    }

    public static BackendlessCollection<Music> find( BackendlessDataQuery query )
    {
        return Backendless.Data.of( Music.class ).find( query );
    }

    public static Future<BackendlessCollection<Music>> findAsync( BackendlessDataQuery query )
    {
        if( Backendless.isAndroid() )
        {
            throw new UnsupportedOperationException( "Using this method is restricted in Android" );
        }
        else
        {
            Future<BackendlessCollection<Music>> future = new Future<BackendlessCollection<Music>>();
            Backendless.Data.of( Music.class ).find( query, future );

            return future;
        }
    }

    public static void findAsync( BackendlessDataQuery query, AsyncCallback<BackendlessCollection<Music>> callback )
    {
        Backendless.Data.of( Music.class ).find( query, callback );
    }
}
