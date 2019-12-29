package com.abc.app.engine.common.service;

import java.util.List;

import com.abc.app.engine.common.entity.NoticeLog;

public interface NoticeLogService {

	List<NoticeLog> findNoticeLogList(NoticeLog noticeLog);

	long findNoticeLogListCount(NoticeLog noticeLog);

	NoticeLog findNoticeLogByLogId(long logId);

}
