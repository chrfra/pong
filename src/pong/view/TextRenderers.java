package pong.view;

import java.awt.Color;
import java.awt.Font;
import java.util.HashMap;

import pong.model.Const;

import com.jogamp.opengl.util.awt.TextRenderer;

public class TextRenderers {

	public static TextRenderer menu = new TextRenderer(new Font("MenuFont", Font.PLAIN, 72),true,false);
	public static TextRenderer scoreScreen = new TextRenderer(new Font("ScorescreenFont", Font.PLAIN, 32));
	public static TextRenderer gameScore = new TextRenderer(new Font("GameScoreFont", Font.PLAIN, 18));
	public static TextRenderer fps = new TextRenderer(new Font("fpsFont", Font.PLAIN, 12));
	
	public static HashMap<Integer, TextRenderer> getTextRenderers(){
		HashMap<Integer, TextRenderer> tr = new HashMap<Integer, TextRenderer>();
		tr.put(Const.FONT_FPS, fps);
		tr.put(Const.FONT_GAMESCORE, gameScore);
		tr.put(Const.FONT_MENU, menu);
		tr.put(Const.FONT_SCORESCREEN, scoreScreen);
		return tr;
	}
	
	
}
