package com.abc.app.engine.common.mapper;

import java.util.List;

import com.abc.app.engine.common.entity.NoticeDepartment;
import com.abc.app.engine.common.entity.NoticeLog;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface NoticeLogMapper {

	List<NoticeLog> findNoticeLogList(NoticeLog noticeLog);

	long findNoticeLogListCount(NoticeLog noticeLog);

	NoticeLog findNoticeLogByLogId(long logId);

	List<NoticeDepartment> findNoticeDepartmentListByLogId(long logId);

	long insertNoticeLog(NoticeLog noticeLog);

	long insertNoticeDepartmentLog(NoticeLog noticeLog);

}
