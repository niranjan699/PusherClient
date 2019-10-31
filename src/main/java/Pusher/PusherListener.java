package Pusher;

import com.pusher.client.Pusher;
import com.pusher.client.PusherOptions;
import com.pusher.client.channel.Channel;
import com.pusher.client.channel.PusherEvent;
import com.pusher.client.channel.SubscriptionEventListener;
import com.pusher.client.connection.ConnectionEventListener;
import com.pusher.client.connection.ConnectionState;
import com.pusher.client.connection.ConnectionStateChange;
import constants.PusherConstant;

public class PusherListener {


    PusherOptions options = new PusherOptions().setCluster( PusherConstant.PUSHER_CLUSTER_KEY );
    Pusher        pusher  = new Pusher( PusherConstant.PUSHER_APP_KEY, options );



   public void  addConnection() {
        pusher.connect( new ConnectionEventListener( ) {
            public void onConnectionStateChange ( ConnectionStateChange change ) {
                System.out.println( "State changed to " + change.getCurrentState( ) +
                        " from " + change.getPreviousState( ) );
            }

            @Override
            public void onError ( String message, String code, Exception e ) {
                System.out.println( "There was a problem connecting!" );
            }
        }, ConnectionState.ALL );
    }
    // Subscribe to a channel


   public void bind() {
        Channel channel = pusher.subscribe( PusherConstant.CHANNEL_NAME );

       Channel channel2 = pusher.subscribe( PusherConstant.CHANNEL_NAME2 );
// Bind to listen for events called "my-event" sent to "my-channel"

       channel2.bind( "my-event", new SubscriptionEventListener( ) {
           @Override
           public void onEvent ( PusherEvent event ) {
               System.out.println( "Received event with data: " + event.toString( ) );
           }
       } );


        channel.bind( "itemRemoved", new SubscriptionEventListener( ) {
            @Override
            public void onEvent ( PusherEvent event ) {
                System.out.println( "Received event with data: " + event.toString( ) );
            }
        } );
       channel.bind( "itemAdded", new SubscriptionEventListener( ) {
           @Override
           public void onEvent ( PusherEvent event ) {
               System.out.println( "Received event with data: " + event.toString( ) );
           }
       } );
    }
// Disconnect from the service
  public   void  disconnet()
    {
pusher.disconnect();
    }


// Reconnect, with all channel subscriptions and event bindings automatically recreated

  public   void connect(){
pusher.connect();
    }
}
