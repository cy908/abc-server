package com.abc.app.engine.common.service.impl;

import java.util.List;

import com.abc.app.engine.common.entity.Notice;
import com.abc.app.engine.common.entity.NoticeDepartment;
import com.abc.app.engine.common.entity.NoticeLog;
import com.abc.app.engine.common.mapper.NoticeLogMapper;
import com.abc.app.engine.common.service.NoticeLogService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class NoticeLogServiceImpl implements NoticeLogService {

	@Autowired
	private NoticeLogMapper noticeLogMapper;

	@Override
	public List<NoticeLog> findNoticeLogList(NoticeLog noticeLog) {
		Notice notice = noticeLog.getNotice();
		if (notice != null) {
			notice.setSearch(StringUtils.trimToNull(notice.getSearch()));
		}
		return noticeLogMapper.findNoticeLogList(noticeLog);
	}

	@Override
	public long findNoticeLogListCount(NoticeLog noticeLog) {
		Notice notice = noticeLog.getNotice();
		if (notice != null) {
			notice.setSearch(StringUtils.trimToNull(notice.getSearch()));
		}
		return noticeLogMapper.findNoticeLogListCount(noticeLog);
	}

	@Override
	public NoticeLog findNoticeLogByLogId(long logId) {
		NoticeLog noticeLog = noticeLogMapper.findNoticeLogByLogId(logId);
		if (noticeLog != null) {
			List<NoticeDepartment> list = noticeLogMapper.findNoticeDepartmentListByLogId(logId);
			noticeLog.getNotice().setDepartments(list);
		}
		return noticeLog;
	}

}
