package com.tes.entity;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * @Author： @Date：2020/05/08 @Description：
 */
@Data
public class Department implements Serializable {
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
	 * 部門名
	 */
	private String group_name;

	/**
	 * 部門管理者ID
	 */
	private Integer group_manager_id;

	/**
	 * 状態 １、使用中 ２、禁止中
	 */
	private Byte status;

	/**
	 * 始業時間
	 */
	private Date startWorkTime;

	/**
	 * 終業時間
	 */
	private Date end_regular_work_time;

	/**
	 * 所定作業時間
	 */
	private Integer predetermined_work_time;
	/**
	 * 備考
	 */
	private String remark;

	/**
	 * 作成日
	 */
	private Date create_date;

	/**
	 * 更新日
	 */
	private Date updateDate;

	private static final long serialVersionUID = 1L;
}