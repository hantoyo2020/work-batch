package com.tes.entity;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * @Author： @Date：2020/05/11 @Description：
 */
@Data
public class OnseatTimeline implements Serializable {
	/**
	 * 
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
	 * 社員ID
	 */
	private Integer emploee_id;

	/**
	 * 日付：年月日
	 */
	private Date date;

	/**
	 * 在席開始時間
	 */
	private Date start_time;

	/**
	 * 在席終了時間
	 */
	private Date end_time;

	/**
	 * 作業時間
	 */
	private int work_time;

	/**
	 * 作成日
	 */
	private Date create_date;

	/**
	 * 作成者
	 */
	private Integer creator;

	/**
	 * 更新日
	 */
	private Date update_date;

	/**
	 * 更新者
	 */
	private String updater;

	/**
	 * トータル作業時間
	 */
	private Integer total_time;
	
	private static final long serialVersionUID = 1L;
}