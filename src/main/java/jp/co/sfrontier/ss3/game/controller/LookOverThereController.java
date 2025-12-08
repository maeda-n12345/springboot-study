package jp.co.sfrontier.ss3.game.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import jp.co.sfrontier.ss3.game.common.Direction;
import jp.co.sfrontier.ss3.game.common.ResultCode;
import jp.co.sfrontier.ss3.game.service.lookoverthere.LookOverTherePlayService;
import jp.co.sfrontier.ss3.game.service.lookoverthere.value.Player;
import jp.co.sfrontier.ss3.game.value.LookOverThereMatchHistory;
import jp.co.sfrontier.ss3.game.value.LookOverThereMatchResult;

/**
 * 「あっちむいてほい」のリクエストを受け取るためのコントローラークラス<br>
 * <br>
 */
@Controller
@RequestMapping("/lookoverthere")
public class LookOverThereController {

	private static final Logger logger = LoggerFactory.getLogger(LookOverThereController.class);

	@Autowired
	private LookOverTherePlayService lookOverTherePlayService;

	/**
	 *  「あっちむいてほい」の初期画面を表示する<br>
	 *  <br>
	 */
	@GetMapping("/play")
	public ModelAndView play() {
		logger.info("「あっちむいてほい」の初期画面");

		// ★ サービスから履歴（DTO のリスト）を取得
		List<LookOverThereMatchHistory> recentHistory = lookOverTherePlayService.getHistory();

		ModelAndView mav = new ModelAndView("lookoverthere/play");
		mav.addObject("name", "山田太郎");
		// ★ 取得した履歴を "historyList" という名前で画面に渡す
		mav.addObject("historyList", recentHistory);

		return mav;
	}


	/**
	 * ゲームを実行する<br>
	 * <br>
	 */
	@PostMapping("/play")
	@ResponseBody
	public LookOverThereMatchResult play(@RequestParam(name = "d", required = true) String direction) {
		// 入力チェック
		// ・ユーザーの指定した方向が正しいかどうか？
		if (validate()) {
			// 入力エラーがあったので処理を中断する
			LookOverThereMatchResult result = new LookOverThereMatchResult();
			result.setResultCode(ResultCode.INPUT_ERROR);

			return result;
		}

		Player player = new Player();
		player.setId(Long.valueOf(10L));
		player.setPlayerName("山田太郎");
		player.setDirection(Direction.get(Integer.valueOf(direction)));

		// 基本的なユーザ情報（ログイン情報）をサービスに渡すためのEntityクラスに変換する
		// TODO ログイン情報の取り出しは共通で実装されているのでそちらを後で使う

		// 対戦する
		lookOverTherePlayService.fight(player);

		// 対戦結果を返す
		// TODO ダミーデータ
		LookOverThereMatchResult result = new LookOverThereMatchResult();
		result.setResultCode(ResultCode.OK);
		result.setWin(player.isWin());
		return result;
	}

	/**
	 * 入力チェックを行う。<br>
	 * <br>
	 * @return 入力エラーがあった場合、<code>true</code>を返す。
	 */
	private boolean validate() {
		// TODO 入力チェックの実装
		return false;
	}
}
