package jp.trident.game.rpg;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Random;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.BitmapFactory;
import android.util.Log;
import jp.trident.game.fw.Collision;
import jp.trident.game.fw.SceneManager;

/**
 * ゲームユーティリティー　シングルトン化
 * 
 * @author wa-rudo
 *
 */
public class GameUtility {
	
	private final String TAG = "GameUtility";
	
	/**
	 * インスタンス
	 */
	static private GameUtility instance = null;
	
	/**
	 * 乱数オブジェクト
	 */
	public Random r = null;
	
	/**
	 * コンテキスト
	 */
	public Context context = null;
	
	/** Nexus7上での画像切り出し表示時のずれを直す */
	public BitmapFactory.Options bitmapfOption = null;
	
	/**
	 * シーンマネージャ
	 */
	public SceneManager sceneManager = null;
	
	/**
	 * コリジョンクラス
	 */
	public Collision collision = null;

	
	
	/**
	 * 一度だけ呼ばれる
	 */
	static {
		instance = new GameUtility();
	}
	
	
	/**
	 * コンストラクタ
	 */
	private GameUtility() {
	}
	
	/**
	 * インスタンスを取得
	 */
	static public GameUtility getInstance() {
		return instance;
	}
	
	/**
	 * 0～maxの中からランダムな整数を得る。
	 *
	 * @param max
	 * @return	乱数値
	 */
	public int getRandom(int max) {
		return r.nextInt(max);
	}
	
	/**
	 * ファイルを読み込む
	 *
	 * @param context  : コンテキスト
	 * @param fileName : ファイル名
	 *
	 * @return data		: ファイルを読み込んだデータ
	 */
	public int[][] fileLoad(Context context, String fileName) {
		int[][] data = null;

		Activity activity = (Activity)context;

		// assetsを取得
		AssetManager assets = activity.getAssets();

		try {
			InputStream in = assets.open(fileName);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			String[] strLineSplit;
			int data_w = 0;
			int data_h = 0;

			// 最初の行を取得して、配列の必要な幅・高さを取得し、生成する
			strLine = br.readLine();

			if(strLine != null) {

				// コンマで区切る
				strLineSplit = strLine.split(",");
				data_w = Integer.parseInt(strLineSplit[0]);
				data_h = Integer.parseInt(strLineSplit[1]);

				data = new int[data_h][data_w];
			}

			// 最後まで、配列に格納する
			int lineCnt = 0;
			int number = 0;
			// １行ずつ文字列を取得していく
			while( (strLine = br.readLine()) != null ) {

				// 「,」で区切る
				strLineSplit = strLine.split(",");

				for(int i = 0; i < strLineSplit.length; i++) {
					number = Integer.parseInt(strLineSplit[i]);
					data[lineCnt][i] = number;
				}
				
				lineCnt ++;
			}
		}
		catch(Exception e) {

			Log.v(TAG, "ファイルを読み込みできませんでした。");
		}

		return data;
	}
	
}
