package com.tes.entity;

import java.io.Serializable;
import lombok.Data;

/**
 * @Author： @Date：2020/05/11 @Description：
 */
@Data
public class MonthWorkAggregation implements Serializable {
	/**
	 * キー情報
	 */
	private Integer id;

	/**
	 * 会社ID
	 */
	private Integer company_id;

	/**
	 * 部門ID
	 */
	private Integer group_id;

	/**
	 * 日付：年月
	 */
	private String date;

	/**
	 * 社員ID
	 */
	private Integer emploee_id;

	/**
	 * 1日稼働時間（分単位）
	 */
	private Integer first_hours;

	/**
	 * ２日稼働時間（分単位）
	 */
	private Integer second_hours;

	/**
	 * ３日稼働時間（分単位）
	 */
	private Integer third_hours;

	/**
	 * ４日稼働時間（分単位）
	 */
	private Integer forth_hours;

	/**
	 * ５日稼働時間（分単位）
	 */
	private Integer fifth_hours;

	/**
	 * ６日稼働時間（分単位）
	 */
	private Integer sixth_hours;

	/**
	 * ７日稼働時間（分単位）
	 */
	private Integer seventh_hours;

	/**
	 * ８日稼働時間（分単位）
	 */
	private Integer eighth_hours;

	/**
	 * ９日稼働時間（分単位）
	 */
	private Integer ninth_hours;

	/**
	 * １０日稼働時間（分単位）
	 */
	private Integer tenth_hours;

	/**
	 * １１日稼働時間（分単位）
	 */
	private Integer eleventh_hours;

	/**
	 * １２日稼働時間（分単位）
	 */
	private Integer twelfth_hours;

	/**
	 * 13日稼働時間（分単位）
	 */
	private Integer thirteenth_hours;

	/**
	 * 14日稼働時間（分単位）
	 */
	private Integer fourteenth_hours;

	/**
	 * 15日稼働時間（分単位）
	 */
	private Integer fifteenth_hours;

	/**
	 * 16日稼働時間（分単位）
	 */
	private Integer sixteenth_hours;

	/**
	 * 17日稼働時間（分単位）
	 */
	private Integer seventeenth_hours;

	/**
	 * 18日稼働時間（分単位）
	 */
	private Integer eighteenth_hours;

	/**
	 * 19日稼働時間（分単位）
	 */
	private Integer ninteenth_hours;

	/**
	 * 20日稼働時間（分単位）
	 */
	private Integer twentieth_hours;

	/**
	 * 21日稼働時間（分単位）
	 */
	private Integer twenty_first_hours;

	/**
	 * 22日稼働時間（分単位）
	 */
	private Integer twenty_second_hours;

	/**
	 * 23日稼働時間（分単位）
	 */
	private Integer twenty_third_hours;

	/**
	 * 24日稼働時間（分単位）
	 */
	private Integer twenty_forth_hours;

	/**
	 * 25日稼働時間（分単位）
	 */
	private Integer twenty_fifth_hours;

	/**
	 * 26日稼働時間（分単位）
	 */
	private Integer twenty_sixth_hours;

	/**
	 * 27日稼働時間（分単位）
	 */
	private Integer twenty_seventh_hours;

	/**
	 * 28日稼働時間（分単位）
	 */
	private Integer twenty_eighth_hours;

	/**
	 * 29日稼働時間（分単位）
	 */
	private Integer twenty_ninth_hours;

	/**
	 * 30日稼働時間（分単位）
	 */
	private Integer thirtieth_hours;

	/**
	 * 31日稼働時間（分単位）
	 */
	private Integer thirty_first_hours;

	/**
	 * 通常稼働の合計時間
	 */
	private Integer total_regular_time;

	/**
	 * 残業稼働の合計時間
	 */
	private Integer total_overtime;

	/**
	 * 深夜稼働時間の合計
	 */
	private Integer total_night_work_time;

	private static final long serialVersionUID = 1L;
}