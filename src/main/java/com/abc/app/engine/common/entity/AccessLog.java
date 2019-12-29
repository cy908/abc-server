package com.abc.app.engine.common.entity;

import java.io.Serializable;
import java.util.Date;

import com.abc.app.engine.common.util.PageInfo;
import com.abc.app.engine.common.util.SearchInfo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 访问日志
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AccessLog implements PageInfo, SearchInfo, Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private Date accessTime;
    private String accessIP;
    private String accessUser;
    private String accessURI;
    private String accessUA;

    // 扩展
    private Long pageIndex;
    private Long pageSize;

    // 参数
    private String search;
    private Date startTime;
    private Date endTime;

}