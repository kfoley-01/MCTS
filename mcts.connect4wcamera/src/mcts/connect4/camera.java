package mcts.connect4;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.imgproc.Moments;
import org.opencv.videoio.VideoCapture;
import org.opencv.core.Scalar;



public class camera {
	static frame w;
	camera(connect4 g) throws InterruptedException{
		
		  w = new frame();
		  w.setLocationRelativeTo(null);
		  MethodCall_camera call = g.getds_camera().methodCalls.take();
		  
		  call.setResult(this);
		  OpenWebcam();	
	}
  
  
  private static void OpenWebcam() {
	  System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	  
	    VideoCapture video = new VideoCapture(0);
	 
	    
	    if(!video.isOpened()) {
	    	System.out.println("Camera not plugged in");
	    	System.exit(0);
	    }
	    Mat f = new Mat();
	    while (true){
	        video.read(f);
	        showResult(f);
	       
	    }
	    
	
}


 static  Point centroid = new Point();
  
  
public static void showResult(Mat org) {
		Mat img= new Mat();
	      
	      Imgproc.line(org, new Point(640*1/7,0), new Point(640*1/7,480), new Scalar(0,0,0),30);
	      Imgproc.line(org, new Point(640*2/7,0), new Point(640*2/7,480), new Scalar(0,0,0),30);
	      Imgproc.line(org, new Point(640*3/7,0), new Point(640*3/7,480), new Scalar(0,0,0),30);
	      Imgproc.line(org, new Point(640*4/7,0), new Point(640*4/7,480), new Scalar(0,0,0),30);
	      Imgproc.line(org, new Point(640*5/7,0), new Point(640*5/7,480), new Scalar(0,0,0),30);
	      Imgproc.line(org, new Point(640*6/7,0), new Point(640*6/7,480), new Scalar(0,0,0),30);

      Imgproc.resize(org, img, new Size(640, 480));	
      Imgproc.cvtColor(img, img, Imgproc.COLOR_BGR2HSV);
      Imgproc.GaussianBlur(img, img, new Size(5, 5), 0);
      Mat dst = new Mat();
      
      Core.inRange(img,new Scalar(0, 100 ,20 ), new Scalar(10, 255, 255), dst);
      Mat dst2 = new Mat();
      Core.inRange(img,new Scalar(170, 50 ,50 ), new Scalar(180, 255, 255), dst2);
      Mat dst3 = new Mat();
      Core.add(dst, dst2, dst3);
      MatOfByte m = new MatOfByte();
      
      if(Core.sumElems(dst3).equals(new Scalar(0.0, 0.0, 0.0, 0.0))==false) {
      ArrayList<MatOfPoint> contours = new ArrayList<MatOfPoint>();
      
      Imgproc.findContours(dst3, contours, dst3, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);
      
      
      double maxVal = 0;
      int maxValIdx = 0;
      
      for (int contourIdx = 0; contourIdx < contours.size(); contourIdx++)
      {
          double contourArea = Imgproc.contourArea(contours.get(contourIdx));
          if (maxVal < contourArea)
          {
              maxVal = contourArea;
              maxValIdx = contourIdx;
          }
      }
      
    
      contours.get(maxValIdx).toArray();

      
      Imgproc.drawContours(img, contours, maxValIdx, new Scalar(50,255,255),5);
      MatOfPoint mop = new MatOfPoint();
      mop=contours.get(maxValIdx);
      Moments  moments = Imgproc.moments(mop);
      

      centroid.x = moments.get_m10() / moments.get_m00();
      centroid.y = moments.get_m01() / moments.get_m00();
      Imgproc.drawMarker(img, centroid, new Scalar(255,255,255),Imgproc.MARKER_CROSS,30,10);
      
      
     

      
      Imgcodecs.imencode(".jpg", img, m);  
      byte[] byteArray = m.toArray();
      BufferedImage bufImage = null;
      try {
          InputStream in = new ByteArrayInputStream(byteArray);
          bufImage = ImageIO.read(in);
          try {
			w.addPic(bufImage);
		} catch (Exception e) {
			e.printStackTrace();
		}
          
          
      }

              catch (Exception e) {
          e.printStackTrace();
              }
      

      
     }
     else {
    	 System.out.println("no red");
    	 centroid.x=1000;
         Imgcodecs.imencode(".jpg", org, m);  
         byte[] byteArray = m.toArray();
         BufferedImage bufImage = null;
         try {
             InputStream in = new ByteArrayInputStream(byteArray);
             bufImage = ImageIO.read(in);
             try {
   			w.addPic(bufImage);
   		} catch (Exception e) {
   			e.printStackTrace();
   		}
             
             
         }

                 catch (Exception e) {
             e.printStackTrace();
                 }
         
     }
      try {
		Thread.sleep(100);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

      
         }




static int track;
static int count=0;
private static double getValue(Point centroid) {
	int result=0;
	
	double x = centroid.x;
	return  7*(x/640);
	
}




private static int calcInput(Point centroid) throws InterruptedException {
	int in =20;
	while(in==20) {
	 in = (int) getValue(centroid);
	 System.out.println("stuck");
	}
	System.out.println("here234");
	Thread.sleep(250);
	
	int out=20;
	
	if(track==in) {
		count++;
		System.out.println("count = "+count);
	}
	else 
	{
		track=in;
		
		count=0;
	}
	
	if(count>8) {
		out=in;
		//System.out.println("out "+out);
		track=100;
		return out;
	}
	
	return 20;
}

private static int getoutput(Point centroid) throws InterruptedException {
	int out=20;
		out =calcInput(centroid);
	return out;
}



public int output() throws InterruptedException {
	
	return getoutput(centroid);
}
}
