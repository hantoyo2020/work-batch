package com.tes.batch;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.tes.entity.Department;
import com.tes.entity.MonthWorkAggregation;
import com.tes.entity.OnseatTimeline;
import com.tes.util.DataSourceUtils;

public class C3p0Select {

	static String TOTAL_REGULAR_TIME = "regular";
	static String TOTAL_OVER_TIME = "over";

	public static void main(String[] args) throws SQLException, ParseException {

		// 创建dbUtils里面的QueryRunner对象,并获取数据库连接
		QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
		// 在席管理查询sql语句
		String sql = "select *,sum(work_time) as total_time from onseat_timeline where date between ? and ? group by date,emploee_id";

		// 存参数值的数组
		SimpleDateFormat sdfYMD = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdfYMDNObar = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat sdfYM = new SimpleDateFormat("yyyyMM");
		Date today = new Date();
		String strDateYMD = sdfYMD.format(today);

		// 月初日を算出する
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(sdfYMD.parse(strDateYMD));
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
		String minMonthDate = sdfYMD.format(calendar.getTime());

		String strDateYMDNobar = sdfYMDNObar.format(today);
		String strDateYM = sdfYM.format(today);
		Object[] params = { minMonthDate, strDateYMD };
		// 执行查询，并以数组的形式返回查询结果（new BeanListHandler()返回查询到的所有记录，并转成对象）
		List<OnseatTimeline> lsitOnseatTimelines = queryRunner.query(sql,
				new BeanListHandler<OnseatTimeline>(OnseatTimeline.class), params);

		// 月次出勤管理表
		for (OnseatTimeline tmpOnseatTimeline : lsitOnseatTimelines) {
			// 部門表から所定作業時間を取得する
			String sqlGroup = "select start_work_time,end_regular_work_time,predetermined_work_time from department where company_id=? and group_id=? and status=1";
			Object[] paramsGroup = { tmpOnseatTimeline.getCompany_id(), tmpOnseatTimeline.getGroup_id() };
			List<Department> lsitGroup = queryRunner.query(sqlGroup, new BeanListHandler<Department>(Department.class),
					paramsGroup);
			// 必ず存在する情報のため
			int determinedTime = lsitGroup.get(0).getPredetermined_work_time();

			String sql2 = "select * from month_work_aggregation where emploee_id=? and date=?";
			Object[] params2 = { tmpOnseatTimeline.getEmploee_id(), sdfYM };
			MonthWorkAggregation getMonthWorkAggregation = queryRunner.query(sql2,
					new BeanHandler<MonthWorkAggregation>(MonthWorkAggregation.class), params2);
			if (getMonthWorkAggregation == null) {
				// 存在しない場合、挿入する
				String sql3 = "insert into month_work_aggregation(company_id, group_id, date, emploee_id, first_hours, second_hours, third_hours, forth_hours, fifth_hours, sixth_hours, seventh_hours, eighth_hours, ninth_hours, tenth_hours, eleventh_hours, twelfth_hours, thirteenth_hours, fourteenth_hours, fifteenth_hours, sixteenth_hours, seventeenth_hours, eighteenth_hours, ninteenth_hours, twentieth_hours, twenty_first_hours, twenty_second_hours, twenty_third_hours, twenty_forth_hours, twenty_fifth_hours, twenty_sixth_hours, twenty_seventh_hours, twenty_eighth_hours, twenty_ninth_hours, thirtieth_hours, thirty_first_hours, total_regular_time, total_overtime, total_night_work_time)"
						+ " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
				System.out.println("INSERTING.....");
				// 月次出勤エンティティの作成
				MonthWorkAggregation createMonthWorkAggregation = createMonthWorkAggregation(tmpOnseatTimeline,
						strDateYMDNobar, strDateYM, determinedTime);
				Object[] params3 = { createMonthWorkAggregation.getCompany_id(),
						createMonthWorkAggregation.getGroup_id(), createMonthWorkAggregation.getDate(),
						createMonthWorkAggregation.getEmploee_id(), createMonthWorkAggregation.getFirst_hours(),
						createMonthWorkAggregation.getSecond_hours(), createMonthWorkAggregation.getThird_hours(),
						createMonthWorkAggregation.getForth_hours(), createMonthWorkAggregation.getFifth_hours(),
						createMonthWorkAggregation.getSixth_hours(), createMonthWorkAggregation.getSeventh_hours(),
						createMonthWorkAggregation.getEighth_hours(), createMonthWorkAggregation.getNinth_hours(),
						createMonthWorkAggregation.getTenth_hours(), createMonthWorkAggregation.getEleventh_hours(),
						createMonthWorkAggregation.getTwelfth_hours(), createMonthWorkAggregation.getThirteenth_hours(),
						createMonthWorkAggregation.getFourteenth_hours(),
						createMonthWorkAggregation.getFifteenth_hours(),
						createMonthWorkAggregation.getSixteenth_hours(),
						createMonthWorkAggregation.getSeventeenth_hours(),
						createMonthWorkAggregation.getEighteenth_hours(),
						createMonthWorkAggregation.getNinteenth_hours(), createMonthWorkAggregation.getTwelfth_hours(),
						createMonthWorkAggregation.getTwenty_first_hours(),
						createMonthWorkAggregation.getTwenty_second_hours(),
						createMonthWorkAggregation.getTwenty_third_hours(),
						createMonthWorkAggregation.getTwenty_forth_hours(),
						createMonthWorkAggregation.getTwenty_fifth_hours(),
						createMonthWorkAggregation.getTwenty_sixth_hours(),
						createMonthWorkAggregation.getTwenty_seventh_hours(),
						createMonthWorkAggregation.getTwenty_eighth_hours(),
						createMonthWorkAggregation.getTwenty_ninth_hours(),
						createMonthWorkAggregation.getThirtieth_hours(),
						createMonthWorkAggregation.getThirty_first_hours(),
						createMonthWorkAggregation.getTotal_regular_time(),
						createMonthWorkAggregation.getTotal_overtime(),
						createMonthWorkAggregation.getTotal_night_work_time() };
				queryRunner.update(sql3, params3);
				System.out.println("INSERTING.....END");
			} else {
				System.out.println("UPDATEING.....");
				// 存在する場合、更新する
				String sql4 = "update month_work_aggregation set first_hours=?, second_hours=?, third_hours=?, forth_hours=?, fifth_hours=?, sixth_hours=?, seventh_hours=?, eighth_hours=?, ninth_hours=?, tenth_hours=?, eleventh_hours=?, twelfth_hours=?, thirteenth_hours=?, fourteenth_hours=?, fifteenth_hours=?, sixteenth_hours=?, seventeenth_hours=?, eighteenth_hours=?, ninteenth_hours=?, twentieth_hours=?, twenty_first_hours=?, twenty_second_hours=?, twenty_third_hours=?, twenty_forth_hours=?, twenty_fifth_hours=?, twenty_sixth_hours=?, twenty_seventh_hours=?, twenty_eighth_hours=?, twenty_ninth_hours=?, thirtieth_hours=?, thirty_first_hours=?, total_regular_time=?, total_overtime=?, total_night_work_time=? where id=?";
				// 月次出勤エンティティの作成
				MonthWorkAggregation createMonthWorkAggregation = createMonthWorkAggregation(tmpOnseatTimeline,
						strDateYMDNobar, strDateYM, determinedTime);
				Object[] params4 = { createMonthWorkAggregation.getFirst_hours(),
						createMonthWorkAggregation.getSecond_hours(), createMonthWorkAggregation.getThird_hours(),
						createMonthWorkAggregation.getForth_hours(), createMonthWorkAggregation.getFifth_hours(),
						createMonthWorkAggregation.getSixth_hours(), createMonthWorkAggregation.getSeventh_hours(),
						createMonthWorkAggregation.getEighth_hours(), createMonthWorkAggregation.getNinth_hours(),
						createMonthWorkAggregation.getTenth_hours(), createMonthWorkAggregation.getEleventh_hours(),
						createMonthWorkAggregation.getTwelfth_hours(), createMonthWorkAggregation.getThirteenth_hours(),
						createMonthWorkAggregation.getFourteenth_hours(),
						createMonthWorkAggregation.getFifteenth_hours(),
						createMonthWorkAggregation.getSixteenth_hours(),
						createMonthWorkAggregation.getSeventeenth_hours(),
						createMonthWorkAggregation.getEighteenth_hours(),
						createMonthWorkAggregation.getNinteenth_hours(), createMonthWorkAggregation.getTwelfth_hours(),
						createMonthWorkAggregation.getTwenty_first_hours(),
						createMonthWorkAggregation.getTwenty_second_hours(),
						createMonthWorkAggregation.getTwenty_third_hours(),
						createMonthWorkAggregation.getTwenty_forth_hours(),
						createMonthWorkAggregation.getTwenty_fifth_hours(),
						createMonthWorkAggregation.getTwenty_sixth_hours(),
						createMonthWorkAggregation.getTwenty_seventh_hours(),
						createMonthWorkAggregation.getTwenty_eighth_hours(),
						createMonthWorkAggregation.getTwenty_ninth_hours(),
						createMonthWorkAggregation.getThirtieth_hours(),
						createMonthWorkAggregation.getThirty_first_hours(),
						createMonthWorkAggregation.getTotal_regular_time(),
						createMonthWorkAggregation.getTotal_overtime(),
						createMonthWorkAggregation.getTotal_night_work_time(), getMonthWorkAggregation.getId() };
				queryRunner.update(sql4, params4);
				System.out.println("UPDATEING.....END");
			}
		}

		System.out.println("GAME OVER...");
	}

	/**
	 * 月次出勤Entityの新規作成
	 * 
	 * @param tmpOnseatTimeline 在席時間Entity
	 * @param strDateYMD        年月日("yyyy-MM-dd")
	 * @param strDateYM         年月("yyyyMM")
	 * @param determinedTime    所定作業時間
	 * @return 月次出勤Entity
	 */
	private static MonthWorkAggregation createMonthWorkAggregation(OnseatTimeline tmpOnseatTimeline, String strDateYMD,
			String strDateYM, int determinedTime) {
		MonthWorkAggregation monthWorkAggregation = new MonthWorkAggregation();
		// 公司ID
		monthWorkAggregation.setCompany_id(tmpOnseatTimeline.getCompany_id());
		// 部门ID
		monthWorkAggregation.setGroup_id(tmpOnseatTimeline.getGroup_id());
		// 担当者
		monthWorkAggregation.setEmploee_id(tmpOnseatTimeline.getEmploee_id());
		// 年月
		monthWorkAggregation.setDate(strDateYM);
		// 日にちの作業时间
		setDayWorkTime(monthWorkAggregation, tmpOnseatTimeline, strDateYMD);

		// 時間計算
		Map<String, Integer> totalTime = getTotalTime(monthWorkAggregation, determinedTime);
		// 定時内作業合计时间
		monthWorkAggregation.setTotal_regular_time(totalTime.get(TOTAL_REGULAR_TIME));
		// 合計残業時間
		monthWorkAggregation.setTotal_overtime(totalTime.get(TOTAL_OVER_TIME));
//		TODO 仕様決まり次第、設定
		// 合計深夜時間
//monthWorkAggregation.setTotal_night_work_time(null);

		return monthWorkAggregation;
	}

	/**
	 * 定時内作業合计时间計算
	 * 
	 * @param monthWorkAggregation 月次出勤
	 * @param determinedTime       所定作業時間
	 * @return 計算結果
	 */
	private static Map<String, Integer> getTotalTime(MonthWorkAggregation monthWorkAggregation, int determinedTime) {

		Map<String, Integer> result = new HashMap<String, Integer>();

		int d1 = monthWorkAggregation.getFirst_hours() == null ? 0 : monthWorkAggregation.getFirst_hours();
		int d2 = monthWorkAggregation.getSecond_hours() == null ? 0 : monthWorkAggregation.getSecond_hours();
		int d3 = monthWorkAggregation.getThird_hours() == null ? 0 : monthWorkAggregation.getThird_hours();
		int d4 = monthWorkAggregation.getForth_hours() == null ? 0 : monthWorkAggregation.getForth_hours();
		int d5 = monthWorkAggregation.getFifth_hours() == null ? 0 : monthWorkAggregation.getFifth_hours();
		int d6 = monthWorkAggregation.getSixth_hours() == null ? 0 : monthWorkAggregation.getSixth_hours();
		int d7 = monthWorkAggregation.getSeventh_hours() == null ? 0 : monthWorkAggregation.getSeventh_hours();
		int d8 = monthWorkAggregation.getEighth_hours() == null ? 0 : monthWorkAggregation.getEighth_hours();
		int d9 = monthWorkAggregation.getNinth_hours() == null ? 0 : monthWorkAggregation.getNinth_hours();
		int d10 = monthWorkAggregation.getTenth_hours() == null ? 0 : monthWorkAggregation.getTenth_hours();
		int d11 = monthWorkAggregation.getEleventh_hours() == null ? 0 : monthWorkAggregation.getEleventh_hours();
		int d12 = monthWorkAggregation.getTwelfth_hours() == null ? 0 : monthWorkAggregation.getTwelfth_hours();
		int d13 = monthWorkAggregation.getThirteenth_hours() == null ? 0 : monthWorkAggregation.getThirteenth_hours();
		int d14 = monthWorkAggregation.getFourteenth_hours() == null ? 0 : monthWorkAggregation.getFourteenth_hours();
		int d15 = monthWorkAggregation.getFifteenth_hours() == null ? 0 : monthWorkAggregation.getFifteenth_hours();
		int d16 = monthWorkAggregation.getSixteenth_hours() == null ? 0 : monthWorkAggregation.getSixteenth_hours();
		int d17 = monthWorkAggregation.getSeventeenth_hours() == null ? 0 : monthWorkAggregation.getSeventeenth_hours();
		int d18 = monthWorkAggregation.getEighteenth_hours() == null ? 0 : monthWorkAggregation.getEighteenth_hours();
		int d19 = monthWorkAggregation.getNinteenth_hours() == null ? 0 : monthWorkAggregation.getNinteenth_hours();
		int d20 = monthWorkAggregation.getTwentieth_hours() == null ? 0 : monthWorkAggregation.getTwentieth_hours();
		int d21 = monthWorkAggregation.getTwenty_first_hours() == null ? 0
				: monthWorkAggregation.getTwenty_first_hours();
		int d22 = monthWorkAggregation.getTwenty_second_hours() == null ? 0
				: monthWorkAggregation.getTwenty_second_hours();
		int d23 = monthWorkAggregation.getTwenty_third_hours() == null ? 0
				: monthWorkAggregation.getTwenty_third_hours();
		int d24 = monthWorkAggregation.getTwenty_forth_hours() == null ? 0
				: monthWorkAggregation.getTwenty_forth_hours();
		int d25 = monthWorkAggregation.getTwenty_fifth_hours() == null ? 0
				: monthWorkAggregation.getTwenty_fifth_hours();
		int d26 = monthWorkAggregation.getTwenty_sixth_hours() == null ? 0
				: monthWorkAggregation.getTwenty_sixth_hours();
		int d27 = monthWorkAggregation.getTwenty_seventh_hours() == null ? 0
				: monthWorkAggregation.getTwenty_seventh_hours();
		int d28 = monthWorkAggregation.getTwenty_eighth_hours() == null ? 0
				: monthWorkAggregation.getTwenty_eighth_hours();
		int d29 = monthWorkAggregation.getTwenty_ninth_hours() == null ? 0
				: monthWorkAggregation.getTwenty_ninth_hours();
		int d30 = monthWorkAggregation.getThirtieth_hours() == null ? 0 : monthWorkAggregation.getThirtieth_hours();
		int d31 = monthWorkAggregation.getThirty_first_hours() == null ? 0
				: monthWorkAggregation.getThirty_first_hours();
		// 分単位⇒秒単位
		determinedTime = determinedTime * 60;

		// 定時内処理
		int tmpd1 = d1 > determinedTime ? determinedTime : d1;
		int tmpd2 = d2 > determinedTime ? determinedTime : d2;
		int tmpd3 = d3 > determinedTime ? determinedTime : d3;
		int tmpd4 = d4 > determinedTime ? determinedTime : d4;
		int tmpd5 = d5 > determinedTime ? determinedTime : d5;
		int tmpd6 = d6 > determinedTime ? determinedTime : d6;
		int tmpd7 = d7 > determinedTime ? determinedTime : d7;
		int tmpd8 = d8 > determinedTime ? determinedTime : d8;
		int tmpd9 = d9 > determinedTime ? determinedTime : d9;
		int tmpd10 = d10 > determinedTime ? determinedTime : d10;
		int tmpd11 = d11 > determinedTime ? determinedTime : d11;
		int tmpd12 = d12 > determinedTime ? determinedTime : d12;
		int tmpd13 = d13 > determinedTime ? determinedTime : d13;
		int tmpd14 = d14 > determinedTime ? determinedTime : d14;
		int tmpd15 = d15 > determinedTime ? determinedTime : d15;
		int tmpd16 = d16 > determinedTime ? determinedTime : d16;
		int tmpd17 = d17 > determinedTime ? determinedTime : d17;
		int tmpd18 = d18 > determinedTime ? determinedTime : d18;
		int tmpd19 = d19 > determinedTime ? determinedTime : d19;
		int tmpd20 = d20 > determinedTime ? determinedTime : d20;
		int tmpd21 = d21 > determinedTime ? determinedTime : d21;
		int tmpd22 = d22 > determinedTime ? determinedTime : d22;
		int tmpd23 = d23 > determinedTime ? determinedTime : d23;
		int tmpd24 = d24 > determinedTime ? determinedTime : d24;
		int tmpd25 = d25 > determinedTime ? determinedTime : d25;
		int tmpd26 = d26 > determinedTime ? determinedTime : d26;
		int tmpd27 = d27 > determinedTime ? determinedTime : d27;
		int tmpd28 = d28 > determinedTime ? determinedTime : d28;
		int tmpd29 = d29 > determinedTime ? determinedTime : d29;
		int tmpd30 = d30 > determinedTime ? determinedTime : d30;
		int tmpd31 = d31 > determinedTime ? determinedTime : d31;
		int regular = tmpd1 + tmpd2 + tmpd3 + tmpd4 + tmpd5 + tmpd6 + tmpd7 + tmpd8 + tmpd9 + tmpd10 + tmpd11 + tmpd12
				+ tmpd13 + tmpd14 + tmpd15 + tmpd16 + tmpd17 + tmpd18 + tmpd19 + tmpd20 + tmpd21 + tmpd22 + tmpd23
				+ tmpd24 + tmpd25 + tmpd26 + tmpd27 + tmpd28 + tmpd29 + tmpd30 + tmpd31;
		result.put(TOTAL_REGULAR_TIME, regular);

		int o1 = d1 > determinedTime ? d1 - determinedTime : 0;
		int o2 = d2 > determinedTime ? d2 - determinedTime : 0;
		int o3 = d3 > determinedTime ? d3 - determinedTime : 0;
		int o4 = d4 > determinedTime ? d4 - determinedTime : 0;
		int o5 = d5 > determinedTime ? d5 - determinedTime : 0;
		int o6 = d6 > determinedTime ? d6 - determinedTime : 0;
		int o7 = d7 > determinedTime ? d7 - determinedTime : 0;
		int o8 = d8 > determinedTime ? d8 - determinedTime : 0;
		int o9 = d9 > determinedTime ? d9 - determinedTime : 0;
		int o10 = d10 > determinedTime ? d10 - determinedTime : 0;
		int o11 = d11 > determinedTime ? d11 - determinedTime : 0;
		int o12 = d12 > determinedTime ? d12 - determinedTime : 0;
		int o13 = d13 > determinedTime ? d13 - determinedTime : 0;
		int o14 = d14 > determinedTime ? d14 - determinedTime : 0;
		int o15 = d15 > determinedTime ? d15 - determinedTime : 0;
		int o16 = d16 > determinedTime ? d16 - determinedTime : 0;
		int o17 = d17 > determinedTime ? d17 - determinedTime : 0;
		int o18 = d18 > determinedTime ? d18 - determinedTime : 0;
		int o19 = d19 > determinedTime ? d19 - determinedTime : 0;
		int o20 = d20 > determinedTime ? d20 - determinedTime : 0;
		int o21 = d21 > determinedTime ? d21 - determinedTime : 0;
		int o22 = d22 > determinedTime ? d22 - determinedTime : 0;
		int o23 = d23 > determinedTime ? d23 - determinedTime : 0;
		int o24 = d24 > determinedTime ? d24 - determinedTime : 0;
		int o25 = d25 > determinedTime ? d25 - determinedTime : 0;
		int o26 = d26 > determinedTime ? d26 - determinedTime : 0;
		int o27 = d27 > determinedTime ? d27 - determinedTime : 0;
		int o28 = d28 > determinedTime ? d28 - determinedTime : 0;
		int o29 = d29 > determinedTime ? d29 - determinedTime : 0;
		int o30 = d30 > determinedTime ? d30 - determinedTime : 0;
		int o31 = d31 > determinedTime ? d31 - determinedTime : 0;

		int over = o1 + o2 + o3 + o4 + o5 + o6 + o7 + o8 + o9 + o10 + o11 + o12 + o13 + o14 + o15 + o16 + o17 + o18
				+ o19 + o20 + o21 + o22 + o23 + o24 + o25 + o26 + o27 + o28 + o29 + o30 + o31;
		result.put(TOTAL_OVER_TIME, over);

		return result;
	}

	/**
	 * 日にちの作業時間設定
	 * 
	 * @param monthWorkAggregation 月次出勤Entity
	 * @param tmpOnseatTimeline    在席時間Entity
	 * @param strDateYM            年月
	 */
	private static void setDayWorkTime(MonthWorkAggregation monthWorkAggregation, OnseatTimeline tmpOnseatTimeline,
			String strDateYM) {
		int day = Integer.valueOf(strDateYM.substring(6));
		switch (day) {
		case 1:
			monthWorkAggregation.setFirst_hours(tmpOnseatTimeline.getTotal_time());
			break;
		case 2:
			monthWorkAggregation.setSecond_hours(tmpOnseatTimeline.getTotal_time());
			break;
		case 3:
			monthWorkAggregation.setThird_hours(tmpOnseatTimeline.getTotal_time());
			break;
		case 4:
			monthWorkAggregation.setForth_hours(tmpOnseatTimeline.getTotal_time());
			break;
		case 5:
			monthWorkAggregation.setFifth_hours(tmpOnseatTimeline.getTotal_time());
			break;
		case 6:
			monthWorkAggregation.setSixth_hours(tmpOnseatTimeline.getTotal_time());
			break;
		case 7:
			monthWorkAggregation.setSeventh_hours(tmpOnseatTimeline.getTotal_time());
			break;
		case 8:
			monthWorkAggregation.setEighth_hours(tmpOnseatTimeline.getTotal_time());
			break;
		case 9:
			monthWorkAggregation.setNinth_hours(tmpOnseatTimeline.getTotal_time());
			break;
		case 10:
			monthWorkAggregation.setTenth_hours(tmpOnseatTimeline.getTotal_time());
			break;
		case 11:
			monthWorkAggregation.setEleventh_hours(tmpOnseatTimeline.getTotal_time());
			break;
		case 12:
			monthWorkAggregation.setTwelfth_hours(tmpOnseatTimeline.getTotal_time());
			break;
		case 13:
			monthWorkAggregation.setThirteenth_hours(tmpOnseatTimeline.getTotal_time());
			break;
		case 14:
			monthWorkAggregation.setFourteenth_hours(tmpOnseatTimeline.getTotal_time());
			break;
		case 15:
			monthWorkAggregation.setFifteenth_hours(tmpOnseatTimeline.getTotal_time());
			break;
		case 16:
			monthWorkAggregation.setSixteenth_hours(tmpOnseatTimeline.getTotal_time());
			break;
		case 17:
			monthWorkAggregation.setSeventeenth_hours(tmpOnseatTimeline.getTotal_time());
			break;
		case 18:
			monthWorkAggregation.setEighteenth_hours(tmpOnseatTimeline.getTotal_time());
			break;
		case 19:
			monthWorkAggregation.setNinteenth_hours(tmpOnseatTimeline.getTotal_time());
			break;
		case 20:
			monthWorkAggregation.setTwentieth_hours(tmpOnseatTimeline.getTotal_time());
			break;
		case 21:
			monthWorkAggregation.setTwenty_first_hours(tmpOnseatTimeline.getTotal_time());
			break;
		case 22:
			monthWorkAggregation.setTwenty_second_hours(tmpOnseatTimeline.getTotal_time());
			break;
		case 23:
			monthWorkAggregation.setTwenty_third_hours(tmpOnseatTimeline.getTotal_time());
			break;
		case 24:
			monthWorkAggregation.setTwenty_forth_hours(tmpOnseatTimeline.getTotal_time());
			break;
		case 25:
			monthWorkAggregation.setTwenty_fifth_hours(tmpOnseatTimeline.getTotal_time());
			break;
		case 26:
			monthWorkAggregation.setTwenty_sixth_hours(tmpOnseatTimeline.getTotal_time());
			break;
		case 27:
			monthWorkAggregation.setTwenty_seventh_hours(tmpOnseatTimeline.getTotal_time());
			break;
		case 28:
			monthWorkAggregation.setTwenty_eighth_hours(tmpOnseatTimeline.getTotal_time());
			break;
		case 29:
			monthWorkAggregation.setTwenty_ninth_hours(tmpOnseatTimeline.getTotal_time());
			break;
		case 30:
			monthWorkAggregation.setThirtieth_hours(tmpOnseatTimeline.getTotal_time());
			break;
		case 31:
			monthWorkAggregation.setThirty_first_hours(tmpOnseatTimeline.getTotal_time());
			break;
		default:
			break;
		}
	}
}