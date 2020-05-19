package com.example.salinda.flyingfishgame;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

/**
 * Created by Salinda on 10-May-20.
 */

public class GameView extends View {

    private Bitmap fish[] = new Bitmap[2];

    private int X = 10;
    private int Y;
    private int Fspeed;

    private int canvasWidth, canvasHeihgt;

    private Bitmap backgrondImage;

    private Paint scorepaint = new Paint();

    private Bitmap life[] = new Bitmap[2];

    // Yello Ball

    private int yellowX , yellowY , yellowSpeed = 18;
    private Paint yellowPaint = new Paint();

    // Green Ball

    private int greenX , greenY , greenSpeed = 28;
    private Paint greenPaint = new Paint();

    // Red Ball

    private int redX , redY , redSpeed = 28;
    private Paint redPaint = new Paint();


    private int score , lifeCounter;

    private boolean touch;

    public GameView(Context context) {
        super(context);

        fish[0] = BitmapFactory.decodeResource(getResources(), R.drawable.fish1);
        fish[1] = BitmapFactory.decodeResource(getResources(), R.drawable.fish2);

        backgrondImage = BitmapFactory.decodeResource(getResources(), R.drawable.background);

        // Yellow
        yellowPaint.setColor(Color.YELLOW);
        yellowPaint.setAntiAlias(false);


        // Green
        greenPaint.setColor(Color.GREEN);
        greenPaint.setAntiAlias(false);

        //Red
        redPaint.setColor(Color.RED);
        redPaint.setAntiAlias(false);


        scorepaint.setColor(Color.RED);
        scorepaint.setTextSize(70);
        scorepaint.setTypeface(Typeface.DEFAULT);
        scorepaint.setAntiAlias(true);

        life[0] = BitmapFactory.decodeResource(getResources(), R.drawable.hearts);
        life[1] = BitmapFactory.decodeResource(getResources(), R.drawable.heart_grey);

        Y = 550;
        score = 0;
        lifeCounter = 3;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvasWidth = canvas.getWidth();
        canvasHeihgt = canvas.getHeight();


        canvas.drawBitmap(backgrondImage , 0 ,0 , null);

        int minY = fish[0].getHeight();
        int maxY = canvasHeihgt - fish[0].getHeight() * 3;

        Y = X + Fspeed;

        if (Y < minY){
            Y = minY;
        }
        if (Y > maxY){
            Y = maxY;
        }

        Fspeed = Fspeed + 5;

        if (touch){
            canvas.drawBitmap(fish[1],X , Y , null);
            touch = false;

        }

        else{
            canvas.drawBitmap(fish[0],X , Y , null);
        }


// Yellow ball function

        yellowX = yellowX - yellowSpeed;

        if (hitBallChecker(yellowX , yellowY)){
            score = score + 10;
            yellowX = - 100;
        }

        if (yellowX < 0){
            yellowX = canvasWidth + 21;
            yellowY = (int) Math.floor(Math.random() * (maxY - minY)) + minY;
        }
        canvas.drawCircle(yellowX, yellowY, 25 , yellowPaint);

// Green ball function

        greenX = greenX - greenSpeed;

        if (hitBallChecker(greenX , greenY)){
            score = score + 20;
            greenX = - 100;
        }

        if (greenX < 0){
            greenX = canvasWidth + 21;
            greenY = (int) Math.floor(Math.random() * (maxY - minY)) + minY;
        }
        canvas.drawCircle(greenX, greenY, 25 , greenPaint);

// Red Ball Function

        redX = redX - redSpeed;

        if (hitBallChecker(redX , redY)){
            redX = - 100;
            lifeCounter--;

            if (lifeCounter == 0){
                Toast.makeText(getContext(), "Game Over" , Toast.LENGTH_SHORT).show();

                Intent gameOverIntent = new Intent(getContext(), GameOverActivity.class);
                gameOverIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                gameOverIntent.putExtra("score" , score);
                getContext().startActivity(gameOverIntent);

            }
        }

        if (redX < 0){
            redX = canvasWidth + 21;
            redY = (int) Math.floor(Math.random() * (maxY - minY)) + minY;
        }
        canvas.drawCircle(redX, redY, 40 , redPaint);


        // Score Function

        canvas.drawText("My Score : " + score ,20 ,60,scorepaint);

        // Red ball life decreasing function
        for (int i=0; i<3; i++){
            int x = (int) (580 + life[0].getWidth() * 1.5 * i);
            int y = 30;

            if (i < lifeCounter){
                canvas.drawBitmap(life[0] , x , y , null);
            }
            else{
                canvas.drawBitmap(life[1] , x , y , null);
            }

        }


    }

    public boolean hitBallChecker(int x, int y){
        if (X < x && x < (X + fish[0].getWidth()) && Y < y && y < (Y + fish[0].getHeight())){
            return  true;
        }

        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN){
            touch = true;

            Fspeed = -45;
        }

        return true;
    }
}
