package com.tes.batch;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * @Author：
 * @Date：2020/05/08
 * @Description：
 */
@Data
public class Company implements Serializable {
    /**
     * 会社ID
     */
    private Integer company_id;

    /**
     * 会社名
     */
    private String company_name;

    /**
     * 責任者名前、必ず社員管理表に存在するわけではない
     */
    private String company_manager;

    /**
     * 電話番号の間に「ー」の入力は可能のため、文字列型にする
     */
    private String managerTel;

    /**
     * メールアドレス
     */
    private String managerMail;

    /**
     * 住所
     */
    private String companyAddress;

    /**
     * 1，試用中
2，購入済み、使用中
3，期限切れ、使用中
4，期限切れ、停止中
5，禁止使用中
     */
    private Byte status;

    /**
     * 購入プラン
     */
    private Integer boughtPlanId;

    /**
     * イベント追加アカウント数
     */
    private Integer eventPlusAccount;

    /**
     * イベント追加使用月数
     */
    private Integer eventPlusMonth;

    /**
     * イベント追加保存期間
     */
    private Integer evenPlusRetentionPeriod;

    /**
     * イベント追加使用ディスク容量
     */
    private Integer eventPlusDisk;

    /**
     * 使用可能な機能
     */
    private String targetFunction;

    /**
     * 備考
     */
    private String remark;

    /**
     * 作成日付
     */
    private Date createDate;

    /**
     * 更新日付
     */
    private Date updateDate;

    private static final long serialVersionUID = 1L;
}