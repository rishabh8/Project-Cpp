package Media_Player;

import javafx.scene.image.Image;
import java.net.URL;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

public class Movie extends Application {

    public boolean status = true;
    
    
   // @Override
    @Override
    public void start(Stage stage) {
        
	try{
            JFileChooser chooser= new JFileChooser();
            FileFilter mp3filter = new FileTypeFilter(".mp3", "MP3 File");
            FileFilter mp4filter = new FileTypeFilter(".mp4", "MP4 File");
            FileFilter avifilter = new FileTypeFilter(".avi", "AVI File type");
            
            
            chooser.addChoosableFileFilter(mp3filter);
            chooser.addChoosableFileFilter(mp4filter);
            chooser.addChoosableFileFilter(avifilter);
            
            int option = chooser.showOpenDialog(null);
            String strurl =new String();
            if(option == JFileChooser.APPROVE_OPTION)
            {
                try
                {
                    URL url = chooser.getSelectedFile().toURL();
                    strurl= url.toString();
                }
                catch(Exception e){}
            }
            else
            {
                System.exit(0);
            }
          
     final Image playimage =new Image(Movie.class.getResourceAsStream("playbutton.png"));
     final Image pauseimage =new Image(Movie.class.getResourceAsStream("pausebutton.png"));
     
     final ImageView imageViewPlay = new ImageView(playimage);
     final ImageView imageViewPause = new ImageView(pauseimage);
     
     Scene scene = new Scene(new Group(), 540, 209,Color.BLACK);
     stage.setScene(scene);
     stage.setTitle("Interstellar Media Player ");
     stage.show();
     HBox hbox = new HBox(100);
    
     //Adding Button
     Button btn = new Button();
     btn.setText("Pause");
     StackPane stackplay = new StackPane();
     stackplay.setAlignment(Pos.BASELINE_CENTER);
     stackplay.getChildren().add(btn);
     btn.setGraphic(imageViewPlay);
     
     Button btnstop = new Button();
     btnstop.setText("Stop");
     StackPane stackstop = new StackPane();
     stackstop.setAlignment(Pos.BASELINE_CENTER);
     stackstop.getChildren().add(btnstop);
     
     Button btnfullscreen = new Button("FullScreen");
     stackstop.setAlignment(Pos.CENTER_RIGHT);
     stackstop.getChildren().add(btnfullscreen);
     
     hbox.getChildren().addAll(stackplay,stackstop);
     
    //Adding Source to Media Player
     String source =strurl;
     Media media = new Media(source);

     MediaPlayer player = new MediaPlayer(media);
     MediaView mediaView = new MediaView(player);
     
     final Timeline sidein = new Timeline();
     final Timeline sideout = new Timeline();
     
     ((Group) scene.getRoot()).setOnMouseEntered((Event event) -> {
         sidein.play();
            });
     ((Group) scene.getRoot()).setOnMouseExited((Event event) -> {
         sideout.play();
            });
     final VBox vbox = new VBox();
     final Slider slider = new Slider();     
     vbox.getChildren().add(slider);
     
            /*HBox hbox = new HBox(2);
            int bands= player.getAudioSpectrumNumBands();
            Rectangle[] rect = new Rectangle[bands];
            
            for(int i=0;i<rect.length;i++)
            {
                rect[i]=new Rectangle();
                rect[i].setFill(Color.GREENYELLOW);
                hbox.getChildren().add(rect[i]);
            }*/
        
     vbox.getChildren().add(hbox);
     vbox.getChildren().add(stackplay);
     vbox.getChildren().add(stackstop);
     
            
           
     ((Group) scene.getRoot()).getChildren().add(mediaView);
     ((Group) scene.getRoot()).getChildren().add(vbox);
     
     
     
     
     player.play();
     btn.setText("Pause");
     btn.setGraphic(imageViewPause);
          
     player.setOnReady(() -> {
         int w= player.getMedia().getWidth();
         int h = player.getMedia().getHeight();
         hbox.setMinWidth(w);
         
         /* int bandwidth = w/rect.length;
         
         for(Rectangle r:rect)
         {
         r.setWidth(bandwidth);
         r.setHeight(2);
         }*/
         
         stage.setMinWidth(w);
         stage.setMinHeight(h);
         
         vbox.setMinSize(w, 100);
         vbox.setTranslateY(h-100);
         
         
         slider.setMin(0.0);
         slider.setValue(0.0);
         slider.setMax(player.getTotalDuration().toSeconds());
         
         sideout.getKeyFrames().addAll(
                 new KeyFrame(new Duration(0),
                         new KeyValue(vbox.translateYProperty(),h-100),
                         new KeyValue(vbox.opacityProperty(),0.9)
                 ),
                 new KeyFrame(new Duration(300),
                         new KeyValue(vbox.translateYProperty(),h),
                         new KeyValue(vbox.opacityProperty(),0.0))
         );
         sidein.getKeyFrames().addAll(
                 new KeyFrame(new Duration(0),
                         new KeyValue(vbox.translateYProperty(),h),
                         new KeyValue(vbox.opacityProperty(),0.0)
                 ),
                 new KeyFrame(new Duration(300),
                         new KeyValue(vbox.translateYProperty(),h-100),
                         new KeyValue(vbox.opacityProperty(),0.9))
         );
    });
     
     player.currentTimeProperty().addListener(new ChangeListener<Duration>(){

                @Override
                public void changed(ObservableValue<? extends Duration> observable, Duration duration, Duration current) 
                {
                    slider.setValue(current.toSeconds());
                    
                }
         
     });
     
     slider.setOnMouseClicked(new EventHandler<MouseEvent>(){

         @Override
         public void handle(MouseEvent event) {
             player.seek(Duration.seconds(slider.getValue()));
         }
         
     });

/*     player.setAudioSpectrumListener(new AudioSpectrumListener() {

         @Override
         public void spectrumDataUpdate(double timestamp, double duration, float[] magnitudes, float[] phases) {
            for(int i=0;i<rect.length;i++)
            {
                double h= magnitudes[i]+60;
                if(h>2)
                {
                    rect[i].setHeight(h);
                }
            }
         }
     });
  */   
     
     
     
     btn.setOnAction(new EventHandler<ActionEvent>(){

         @Override
         public void handle(ActionEvent event) {
             Status sta = player.getStatus();
             if(sta == Status.STOPPED || sta == Status.HALTED)
             {   
                btn.setText(""+"Pause");
                btn.setGraphic(imageViewPause);
                player.play();
                
             }
             
             if(sta == Status.PAUSED)
             {
                 btn.setText(""+"Pause");
                 player.play();
                 btn.setGraphic(imageViewPause);
             }
             if(sta == Status.PLAYING)
             {
                 btn.setText(""+"Play");
                 player.pause();
                 btn.setGraphic(imageViewPlay);
                 
             }
             
         }
         
     });
     
      
     
     btnstop.setOnAction(new EventHandler<ActionEvent>() {

         @Override
         public void handle(ActionEvent event) {
             player.stop();
             Status sta = player.getStatus();
             
             if(sta == Status.STOPPED)
             {
                btn.setGraphic(imageViewPlay);
                btn.setText(""+"Play");
             }
             
             
             
         }
     });
     
	}catch(Exception e){e.printStackTrace();}
 }

    
    public static void main(String[] args) {
        launch(args);
    }
    
}