package live.movement;


import live.base.GameEntity;
import live.base.SimplePanel;
import org.newdawn.slick.*;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

public class MovementPanel extends SimplePanel {

    private static final Color MeasureLineColor = Color.lightGray;
    private static final float MeasureLineLength = 3;


    long startTime;




    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        super.init(gameContainer,stateBasedGame);
        startTime = System.currentTimeMillis();
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {

        renderBackground(gameContainer,stateBasedGame,graphics);

        drawMeasureLines(graphics);

        graphics.translate(FRAME_WIDTH, (float) height + FRAME_WIDTH);
        graphics.scale(1, -1);

        renderEtities(gameContainer,stateBasedGame,graphics);
    }


    private void drawMeasureLines(Graphics graphics) {
        graphics.setColor(MeasureLineColor);

        for (int i = 0; i < width; i += 50) {
            drawXLine(graphics,i);
            drawYLine(graphics,i);
        }
    }

    private void drawYLine(Graphics graphics, int i) {
        graphics.drawLine((float)FRAME_WIDTH-MeasureLineLength, (float) height + FRAME_WIDTH-i ,(float) (FRAME_WIDTH+MeasureLineLength),(float) height + FRAME_WIDTH-i );
        graphics.drawString(""+i,FRAME_WIDTH-graphics.getFont().getWidth(""+i)-2*MeasureLineLength,(float) height + FRAME_WIDTH - i-graphics.getFont().getHeight("i")/2);
    }

    private void drawXLine(Graphics graphics, int i) {
        graphics.drawLine(i+ FRAME_WIDTH,(float) (height+FRAME_WIDTH-MeasureLineLength),i+FRAME_WIDTH,(float)(height+FRAME_WIDTH+MeasureLineLength));
        graphics.drawString(""+i,i+ FRAME_WIDTH-graphics.getFont().getWidth(""+i)/2,(float) height + FRAME_WIDTH + 2*MeasureLineLength);
    }



    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int time) throws SlickException {
        if (System.currentTimeMillis()-startTime<5000)
            return;

        super.update(gameContainer,stateBasedGame,time);
    }


}
