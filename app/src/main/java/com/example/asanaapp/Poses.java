package com.example.asanaapp;

/**
 * Created by Usser on 13.11.2016.
 */
import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.persistence.BackendlessDataQuery;

public class Poses
{
    private Integer Difficulty;
    private String Images;
    private String objectId;
    private String Sequence;
    private String Poses;
    private String ownerId;
    public Integer getDifficulty()
    {
        return Difficulty;
    }

    public void setDifficulty( Integer Difficulty )
    {
        this.Difficulty = Difficulty;
    }

    public String getImages()
    {
        return Images;
    }

    public void setImages( String Images )
    {
        this.Images = Images;
    }

    public String getObjectId()
    {
        return objectId;
    }

    public String getSequence()
    {
        return Sequence;
    }

    public void setSequence( String Sequence )
    {
        this.Sequence = Sequence;
    }

    public String getPoses()
    {
        return Poses;
    }

    public void setPoses( String Poses )
    {
        this.Poses = Poses;
    }

    public String getOwnerId()
    {
        return ownerId;
    }


    @Override
    public boolean equals(Object obj) {
        try {
            if (getObjectId().equals(((PoseObject) obj).getObjectId())) {
                return true;
            }
            return false;
        } catch(Exception e) {
            if (getObjectId().equals(((Poses) obj).getObjectId())) {
                return true;
            }
            return false;
        }
    }

    public Poses save()
    {
        return Backendless.Data.of( Poses.class ).save( this );
    }

    public Future<Poses> saveAsync()
    {
        if( Backendless.isAndroid() )
        {
            throw new UnsupportedOperationException( "Using this method is restricted in Android" );
        }
        else
        {
            Future<Poses> future = new Future<Poses>();
            Backendless.Data.of( Poses.class ).save( this, future );

            return future;
        }
    }

    public void saveAsync( AsyncCallback<Poses> callback )
    {
        Backendless.Data.of( Poses.class ).save( this, callback );
    }

    public Long remove()
    {
        return Backendless.Data.of( Poses.class ).remove( this );
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
            Backendless.Data.of( Poses.class ).remove( this, future );

            return future;
        }
    }

    public void removeAsync( AsyncCallback<Long> callback )
    {
        Backendless.Data.of( Poses.class ).remove( this, callback );
    }

    public static Poses findById( String id )
    {
        return Backendless.Data.of( Poses.class ).findById( id );
    }

    public static Future<Poses> findByIdAsync( String id )
    {
        if( Backendless.isAndroid() )
        {
            throw new UnsupportedOperationException( "Using this method is restricted in Android" );
        }
        else
        {
            Future<Poses> future = new Future<Poses>();
            Backendless.Data.of( Poses.class ).findById( id, future );

            return future;
        }
    }

    public static void findByIdAsync( String id, AsyncCallback<Poses> callback )
    {
        Backendless.Data.of( Poses.class ).findById( id, callback );
    }

    public static Poses findFirst()
    {
        return Backendless.Data.of( Poses.class ).findFirst();
    }

    public static Future<Poses> findFirstAsync()
    {
        if( Backendless.isAndroid() )
        {
            throw new UnsupportedOperationException( "Using this method is restricted in Android" );
        }
        else
        {
            Future<Poses> future = new Future<Poses>();
            Backendless.Data.of( Poses.class ).findFirst( future );

            return future;
        }
    }

    public static void findFirstAsync( AsyncCallback<Poses> callback )
    {
        Backendless.Data.of( Poses.class ).findFirst( callback );
    }

    public static Poses findLast()
    {
        return Backendless.Data.of( Poses.class ).findLast();
    }

    public static Future<Poses> findLastAsync()
    {
        if( Backendless.isAndroid() )
        {
            throw new UnsupportedOperationException( "Using this method is restricted in Android" );
        }
        else
        {
            Future<Poses> future = new Future<Poses>();
            Backendless.Data.of( Poses.class ).findLast( future );

            return future;
        }
    }

    public static void findLastAsync( AsyncCallback<Poses> callback )
    {
        Backendless.Data.of( Poses.class ).findLast( callback );
    }

    public static BackendlessCollection<Poses> find( BackendlessDataQuery query )
    {
        return Backendless.Data.of( Poses.class ).find( query );
    }

    public static Future<BackendlessCollection<Poses>> findAsync( BackendlessDataQuery query )
    {
        if( Backendless.isAndroid() )
        {
            throw new UnsupportedOperationException( "Using this method is restricted in Android" );
        }
        else
        {
            Future<BackendlessCollection<Poses>> future = new Future<BackendlessCollection<Poses>>();
            Backendless.Data.of( Poses.class ).find( query, future );

            return future;
        }
    }

    public static void findAsync( BackendlessDataQuery query, AsyncCallback<BackendlessCollection<Poses>> callback )
    {
        Backendless.Data.of( Poses.class ).find( query, callback );
    }
}