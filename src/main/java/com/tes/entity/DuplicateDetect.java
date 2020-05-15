package com.tes.entity;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * @Author：
 * @Date：2020/05/04
 * @Description：
 */
@Data
public class DuplicateDetect implements Serializable {
    /**
     * 会社ID
     */
    private Integer companyId;

    /**
     * 部門ID
     */
    private Integer groupId;

    /**
     * 重複検知日
     */
    private Date detectDate;

    /**
     * 部門管理者ID
     */
    private Integer groupManagerId;

    /**
     * 検知対象社員ID
     */
    private Integer emploeeId;

    /**
     * 状態
１、管理者未閲覧
２、管理者閲覧済み
     */
    private Byte status;

    /**
     * 管理者からのコメント
     */
    private String comment;

    /**
     * アラートステータス
１、当該社員にアラートする
２、アラートし、当該社員閲覧済み
３、アラートしない
     */
    private Byte commentAlert;

    /**
     * 作成日
     */
    private Date createDate;

    /**
     * 作成者
     */
    private String creator;

    /**
     * 更新日
     */
    private Date updateDate;

    /**
     * 更新者
     */
    private String updater;

    private static final long serialVersionUID = 1L;
}